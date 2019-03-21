package com.lucas.mynews.Utils;

import com.lucas.mynews.Models.MostPopular.MostPopularResponse;
import com.lucas.mynews.Models.MovieReviews.MovieReviewsResponse;
import com.lucas.mynews.Models.NyTimesApiResponse;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NyTimeService {
    @GET("topstories/v2/{section}.json")
    Observable<NyTimesApiResponse> getTopStoriesArticle(@Path("section") String section,
                                                        @Query("api-key") String apiKey);

    @GET("mostpopular/v2/viewed/{period}.json")
    Observable<MostPopularResponse> getMostPopularArticle(@Path("period") int period,
                                                          @Query("api-key") String apiKey);

    @GET("movies/v2/reviews/{type}.json")
    Observable<MovieReviewsResponse> getMovieReviewsArticle(@Path("type") String type,
                                                            @Query("api-key") String apiKey);

    @GET("search/v2/articlesearch.json")
    Observable<NyTimesApiResponse> getSearchArticle(
                                                @Query("q") String article,
                                                @Query("begin_date") String beginDate,
                                                @Query("end_date") String endDate,
                                                @Query("api-key") String apiKey);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}