package in.karan.suman.newsapp.Remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Suman on 04-Dec-17.
 */

public class RetrofitClient {

    private static Retrofit retrofit=null;
    public static Retrofit getClient(String baseUrl)
    {
        if(retrofit==null)
        {
            retrofit=new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}