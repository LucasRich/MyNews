package com.lucas.mynews.Models.MovieReviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Multimedia {

    @SerializedName("src")
    @Expose
    private String src;

    public String getSrc() {
        return src;
    }
}