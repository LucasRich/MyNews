package com.lucas.mynews.Views;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lucas.mynews.Models.MostPopular.MostPopularArticle;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.UtilsFunction;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MostPopularViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_display_article_item_title) TextView textViewTitle;
    @BindView(R.id.fragment_display_article_item_date) TextView textViewDate;
    @BindView(R.id.fragment_display_article_item_section) TextView textViewSection;
    @BindView(R.id.fragment_display_article_item_image) ImageView imageView;


    public MostPopularViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithMostPopularArticles(MostPopularArticle articles){
        this.textViewTitle.setText(articles.getTitle());
        this.textViewDate.setText(UtilsFunction.getGoodFormatDate(articles.getPublishedDate()));
        this.textViewSection.setText(articles.getSection());

        if (articles.getMedia().get(0).getMediaMetadata() != null && articles.getMedia().get(0).getMediaMetadata().isEmpty() == false){
            Glide.with(imageView).load(articles.getMedia().get(0).getMediaMetadata().get(0).getUrl()).into(imageView);
        }else {
            Glide.with(itemView).load("https://images-eu.ssl-images-amazon.com/images/I/615KWjAew4L.png").into(imageView);
        }
    }
}