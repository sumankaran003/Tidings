package in.karan.suman.newsapp;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import dmax.dialog.SpotsDialog;

public class DetailActivity extends AppCompatActivity {


    private AdView mAdView;
        WebView webView;
          AlertDialog dialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_detail);
            MobileAds.initialize(getApplicationContext(),"ca-app-pub-2787843623003554~5619540617");
            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

          dialog = new SpotsDialog(this);
          dialog.show();
            //WebView
            webView = (WebView)findViewById(R.id.webView);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebChromeClient(new WebChromeClient());
            webView.setWebViewClient(new WebViewClient(){

                //press Ctrl+O


                @Override
                public void onPageCommitVisible(WebView view, String url) {
                    super.onPageCommitVisible(view, url);
                    dialog.dismiss();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    dialog.dismiss();
                }
            });

            if(getIntent() != null)
            {
                if(!getIntent().getStringExtra("webURL").isEmpty())
                    webView.loadUrl(getIntent().getStringExtra("webURL"));
            }
        }
    }
