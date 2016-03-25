package in.rajasudhan.popularmovies;

import in.rajasudhan.popularmovies.models.MoviesResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by raja sudhan on 3/25/2016.
 */

public class MovieDBService {


    public static final String API_BASE_URL = "http://api.themoviedb.org/3/";
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }

    public interface MovieDBClient{
        @GET("discover/movie?api_key="+BuildConfig.MOVIE_DB_API_KEY)
        Call<MoviesResponse> getMovies(@Query("page")String pageNo,@Query("sort_by")String sortBy);

    }
}