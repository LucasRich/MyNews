package com.lucas.mynews.Models.MovieReviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieReviewsResponse {
    public List<MovieReviewsArticle> getResult() {
        return results;
    }

    @SerializedName("results")
    @Expose
    private List<MovieReviewsArticle> results = null;
}
