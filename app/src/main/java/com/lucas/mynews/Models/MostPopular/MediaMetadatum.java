package com.lucas.mynews.Models.MostPopular;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MediaMetadatum {

    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }
}