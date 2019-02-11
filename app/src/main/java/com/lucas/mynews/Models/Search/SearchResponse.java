package com.lucas.mynews.Models.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
    public List<SearchArticle> getResult() {
        return results;
    }

    @SerializedName("results")
    @Expose
    private List<SearchArticle> results = null;
}
