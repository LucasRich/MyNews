package com.lucas.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.lucas.mynews.Models.MovieReviews.MovieReviewsArticle;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.UtilsSingleton;

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

    UtilsSingleton utils = UtilsSingleton.getInstance();

    public MovieReviewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithMovieReviewsArticles(MovieReviewsArticle articles, RequestManager glide){
        this.textViewTitle.setText(articles.getDisplayTitle());
        this.textViewDate.setText(utils.getGoodFormatDate(articles.getPublicationDate()));
        this.textViewSection.setText("Movie > Reviews");

        if (articles.getMultimedia() != null && articles.getMultimedia().getSrc().isEmpty() == false){
            glide.load(articles.getMultimedia().getSrc()).into(imageView);
        }else {
            glide.load("https://images-eu.ssl-images-amazon.com/images/I/615KWjAew4L.png").into(imageView);
        }
    }
}