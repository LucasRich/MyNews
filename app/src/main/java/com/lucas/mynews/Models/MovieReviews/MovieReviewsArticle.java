package com.lucas.mynews.Models.MovieReviews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieReviewsArticle {

    @SerializedName("display_title")
    @Expose
    private String displayTitle;
    @SerializedName("publication_date")
    @Expose
    private String publicationDate;
    @SerializedName("link")
    @Expose
    private Link link;
    @SerializedName("multimedia")
    @Expose
    private Multimedia multimedia;

    public String getDisplayTitle() {
        return displayTitle;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public Link getLink() {
        return link;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

}
