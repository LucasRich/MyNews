package com.lucas.mynews.Models.MostPopular;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MostPopularResponse {

    public List<MostPopularArticle> getResult() {
        return results;
    }

    @SerializedName("results")
    @Expose
    private List<MostPopularArticle> results = null;
}
