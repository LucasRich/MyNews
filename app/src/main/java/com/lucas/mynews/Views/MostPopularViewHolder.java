package com.lucas.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.lucas.mynews.Models.MostPopular.MostPopularArticle;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.UtilsSingleton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MostPopularViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_most_popular_item_title) TextView textViewTitle;
    @BindView(R.id.fragment_most_popular_item_date) TextView textViewDate;
    @BindView(R.id.fragment_most_popular_item_section) TextView textViewSection;
    @BindView(R.id.fragment_most_popular_item_image) ImageView imageView;

    UtilsSingleton utils = UtilsSingleton.getInstance();

    public MostPopularViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithMostPopularArticles(MostPopularArticle articles, RequestManager glide){
        this.textViewTitle.setText(articles.getTitle());
        this.textViewDate.setText(utils.getGoodFormatDate(articles.getPublishedDate()));
        this.textViewSection.setText(articles.getSection());

        if (articles.getMedia().get(0).getMediaMetadata() != null && articles.getMedia().get(0).getMediaMetadata().isEmpty() == false){
            glide.load(articles.getMedia().get(0).getMediaMetadata().get(0).getUrl()).into(imageView);
        }else {
            glide.load("https://images-eu.ssl-images-amazon.com/images/I/615KWjAew4L.png").into(imageView);
        }
    }
}