package com.lucas.mynews.Views;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lucas.mynews.Models.MovieReviews.MovieReviewsArticle;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.UtilsFunction;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieReviewsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_display_article_item_title) TextView textViewTitle;
    @BindView(R.id.fragment_display_article_item_date) TextView textViewDate;
    @BindView(R.id.fragment_display_article_item_section) TextView textViewSection;
    @BindView(R.id.fragment_display_article_item_image) ImageView imageView;

    public MovieReviewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithMovieReviewsArticles(MovieReviewsArticle articles){
        this.textViewTitle.setText(articles.getDisplayTitle());
        this.textViewDate.setText(UtilsFunction.getGoodFormatDate(articles.getPublicationDate()));
        this.textViewSection.setText("Movie > Reviews");

        if (articles.getMultimedia() != null && !articles.getMultimedia().getSrc().isEmpty()){
            Glide.with(itemView).load(articles.getMultimedia().getSrc()).into(imageView);
        } else {
            Glide.with(itemView).load("https://images-eu.ssl-images-amazon.com/images/I/615KWjAew4L.png").into(imageView);
        }
    }
}