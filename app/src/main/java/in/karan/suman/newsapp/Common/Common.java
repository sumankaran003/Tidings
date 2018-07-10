package in.karan.suman.newsapp.Common;

import in.karan.suman.newsapp.Interface.IconBetterIdeaService;
import in.karan.suman.newsapp.Interface.NewsService;
import in.karan.suman.newsapp.Remote.IconBetterIdeaClient;
import in.karan.suman.newsapp.Remote.RetrofitClient;

/**
 * Created by Suman on 04-Dec-17.
 */



public class Common {
    private static final String BASE_URL="https://newsapi.org/";

    public  static final String API_KEY="527e7b10cd0f424c836e2c47fe3f4365";

    public static NewsService getNewsService()
    {
        return RetrofitClient.getClient(BASE_URL).create(NewsService.class);
    }

    public static IconBetterIdeaService getIconService()
    {
        return IconBetterIdeaClient.getClient().create(IconBetterIdeaService.class);
    }

    //https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=a7072d9c2ad9495a8dd5cb58a7fd30df
    public static String getAPIUrl(String source,String sortBy,String apiKEY)
    {
        StringBuilder apiUrl = new StringBuilder("https://newsapi.org/v2/top-headlines?sources=");
        return apiUrl.append(source)
                .append("&apiKey=")
                .append(apiKEY)
                .toString();
    }


}

