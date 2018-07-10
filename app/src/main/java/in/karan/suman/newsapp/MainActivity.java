package in.karan.suman.newsapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;

import dmax.dialog.SpotsDialog;
import in.karan.suman.newsapp.Adapter.ListSourceAdapter;
import in.karan.suman.newsapp.Common.Common;
import in.karan.suman.newsapp.Interface.NewsService;
import in.karan.suman.newsapp.Model.WebSite;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SwipeRefreshLayout swipeLayout;
    private AdView mAdView;
    RecyclerView listWebsite;
    RecyclerView.LayoutManager layoutManager;
    NewsService mService;
    ListSourceAdapter adapter;
    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MobileAds.initialize(getApplicationContext(),"ca-app-pub-2787843623003554~5619540617");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Paper.init(this);

        mService = Common.getNewsService();

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadWebsiteSource(true);
            }
        });

        listWebsite= (RecyclerView) findViewById(R.id.list_source);
        listWebsite.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        listWebsite.setLayoutManager(layoutManager);

        dialog=new SpotsDialog(this);

        loadWebsiteSource(false);
    }

    private void loadWebsiteSource(boolean isRefreshed)
    {
       if(!isRefreshed)
       {
           String cache = Paper.book().read("cache");
           if(cache !=null && !cache.isEmpty() && !cache.equals("null"))
           {
               WebSite website = new Gson().fromJson(cache,WebSite.class);
               adapter=new ListSourceAdapter(getBaseContext(),website);
               adapter.notifyDataSetChanged();
               listWebsite.setAdapter(adapter);

           }
           else   //if not have cache
           {


               dialog.show();

               //Fetch new data
               mService.getSources().enqueue(new Callback<WebSite>() {
                   @Override
                   public void onResponse(Call<WebSite> call, Response<WebSite> response) {

                       adapter = new ListSourceAdapter(getBaseContext(),response.body());
                       adapter.notifyDataSetChanged();
                       listWebsite.setAdapter(adapter);

                       //Save to cache
                       Paper.book().write("cache",new Gson().toJson(response.body()));



                       dialog.dismiss();

                   }

                   @Override
                   public void onFailure(Call<WebSite> call, Throwable t) {

                   }
               });
           }
       }
       //if from swipe to Refresh
       else
       {

           swipeLayout.setRefreshing(true);

           //Fetch new data
           mService.getSources().enqueue(new Callback<WebSite>() {
               @Override
               public void onResponse(Call<WebSite> call, Response<WebSite> response) {

                   adapter = new ListSourceAdapter(getBaseContext(),response.body());
                   adapter.notifyDataSetChanged();
                   listWebsite.setAdapter(adapter);

                   //Save to cache
                   Paper.book().write("cache",new Gson().toJson(response.body()));

                   //dismiss refresh progressing
                   swipeLayout.setRefreshing(false);

               }

               @Override
               public void onFailure(Call<WebSite> call, Throwable t) {

               }
           });




       }
    }
}


















