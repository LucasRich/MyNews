package com.lucas.mynews.Models.TopStories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopStoriesMedia {

    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

}
