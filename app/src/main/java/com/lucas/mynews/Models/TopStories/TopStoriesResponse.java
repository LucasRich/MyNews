package com.lucas.mynews.Models.TopStories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.lucas.mynews.Models.TopStories.TopStoriesArticle;

import java.util.List;

public class TopStoriesResponse {

    public List<TopStoriesArticle> getResult() {
        return results;
    }

    @SerializedName("results")
    @Expose
    private List<TopStoriesArticle> results = null;
}
