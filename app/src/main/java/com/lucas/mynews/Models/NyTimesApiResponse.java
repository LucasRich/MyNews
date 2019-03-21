package com.lucas.mynews.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lucas.mynews.Models.MostPopular.MostPopularArticle;
import com.lucas.mynews.Models.MovieReviews.MovieReviewsArticle;
import com.lucas.mynews.Models.Search.Response;
import com.lucas.mynews.Models.TopStories.TopStoriesArticle;

import java.util.List;

public class NyTimesApiResponse {

    @SerializedName("results")
    @Expose
    private List<TopStoriesArticle> resultsTopStories = null;
    private List<MovieReviewsArticle> resultsMovieReviews = null;
    private List<MostPopularArticle> resultsMostPopular = null;


    @SerializedName("response")
    @Expose
    private Response responseSearchArticle;


    public List<TopStoriesArticle> getResultsTopStories() {
        return resultsTopStories;
    }

    public List<MovieReviewsArticle> getResultsMovieReviews() {
        return resultsMovieReviews;
    }

    public List<MostPopularArticle> getResultsMostPopular() {
        return resultsMostPopular;
    }

    public Response getResponseSearchArticle() {
        return responseSearchArticle;
    }

}
