package com.lucas.mynews.Utils;

import com.lucas.mynews.Models.MostPopular.MostPopularResponse;
import com.lucas.mynews.Models.MovieReviews.MovieReviewsResponse;
import com.lucas.mynews.Models.Search.SearchResponse;
import com.lucas.mynews.Models.TopStories.TopStoriesResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NyTimeStreams {

    public static Observable<TopStoriesResponse> streamFetchTopStoriesArticles(String section, String apiKey){
        NyTimeService nyTimeService = NyTimeService.retrofit.create(NyTimeService.class);
        return nyTimeService.getTopStoriesArticle(section, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<MostPopularResponse> streamFetchMostPopularArticles(int period, String apiKey){
        NyTimeService nyTimeService = NyTimeService.retrofit.create(NyTimeService.class);
        return nyTimeService.getMostPopularArticle(period, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<MovieReviewsResponse> streamFetchMovieReviewsArticles(String type, String apiKey){
        NyTimeService nyTimeService = NyTimeService.retrofit.create(NyTimeService.class);
        return nyTimeService.getMovieReviewsArticle(type, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public static Observable<SearchResponse> streamFetchSearchArticles(String article, String beginDate, String endDate, String apiKey){
        NyTimeService nyTimeService = NyTimeService.retrofit.create(NyTimeService.class);
        //return nyTimeService.getSearchArticle(article, arts, business, entrepreneurs, politics, sports, travel, beginDate, endDate, apiKey)
        return nyTimeService.getSearchArticle(article, beginDate, endDate, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
