package com.lucas.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.lucas.mynews.Models.MovieReviews.MovieReviewsArticle;
import com.lucas.mynews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieReviewsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_movie_reviews_item_title) TextView textViewTitle;
    @BindView(R.id.fragment_movie_reviews_item_date) TextView textViewDate;
    @BindView(R.id.fragment_movie_reviews_item_section) TextView textViewSection;
    @BindView(R.id.fragment_movie_reviews_item_image) ImageView imageView;

    public MovieReviewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithMovieReviewsArticles(MovieReviewsArticle articles, RequestManager glide){
        this.textViewTitle.setText(articles.getDisplayTitle());
        this.textViewDate.setText(getGoodFormatDate(articles.getPublicationDate()));
        this.textViewSection.setText("Movie > Reviews");

        if (articles.getMultimedia() != null && articles.getMultimedia().getSrc().isEmpty() == false){
            glide.load(articles.getMultimedia().getSrc()).into(imageView);
        }else {
            glide.load("https://images-eu.ssl-images-amazon.com/images/I/615KWjAew4L.png").into(imageView);
        }
    }

    private String getGoodFormatDate(String publishedDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yy-mm-dd");
        String dateInString = publishedDate;

        try {
            Date date = formatter.parse(dateInString.replaceAll("Z$", "+0000"));
            publishedDate = formatter.format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        StringBuilder myName = new StringBuilder(publishedDate);
        myName.setCharAt(2, '/');
        myName.setCharAt(5, '/');
        publishedDate = myName.toString();

        return publishedDate;
    }
}