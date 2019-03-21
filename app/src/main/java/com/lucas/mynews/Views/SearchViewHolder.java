package com.lucas.mynews.Views;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lucas.mynews.Models.Search.Doc;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.UtilsFunction;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_display_article_item_title) TextView textViewTitle;
    @BindView(R.id.fragment_display_article_item_date) TextView textViewDate;
    @BindView(R.id.fragment_display_article_item_image) ImageView imageView;


    public SearchViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithSearchArticles(Doc articles){
        this.textViewTitle.setText(articles.getHeadline().getMain());
        this.textViewDate.setText(UtilsFunction.getGoodFormatDate(articles.getPubDate()));

        if (!articles.getMultimedia().isEmpty()){
            Glide.with(itemView).load("https://static01.nyt.com/" + articles.getMultimedia().get(0).getUrl()).into(imageView);
        }
        else {
            Glide.with(itemView).load("https://images-eu.ssl-images-amazon.com/images/I/615KWjAew4L.png").into(imageView);
        }
    }
}
