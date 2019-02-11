package com.lucas.mynews.Models.MostPopular;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostPopularArticle {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("section")
    @Expose
    private String section;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    @SerializedName("media")
    @Expose
    private List<Medium> media = null;

    public String getUrl() {
        return url;
    }

    public String getSection() {
        return section;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public List<Medium> getMedia() {
        return media;
    }
}

