package in.karan.suman.newsapp.Interface;

import in.karan.suman.newsapp.Common.Common;
import in.karan.suman.newsapp.Model.News;
import in.karan.suman.newsapp.Model.WebSite;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Suman on 04-Dec-17.
 */


public interface NewsService {
  @GET("v1/sources?language=en&apiKey="+ Common.API_KEY)
  Call<WebSite> getSources();

  @GET
  Call<News> getNewestArticles(@Url String url);
}

