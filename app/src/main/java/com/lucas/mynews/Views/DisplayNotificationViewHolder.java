package com.lucas.mynews.Views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.lucas.mynews.Controllers.Activities.DisplayNotification;
import com.lucas.mynews.Models.Search.Doc;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.SharedPref;
import com.lucas.mynews.Utils.UtilsSingleton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayNotificationViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.activity_display_notification_item_title) TextView textViewTitle;
    @BindView(R.id.activity_display_notification_item_date) TextView textViewDate;
    @BindView(R.id.activity_display_notification_item_image) ImageView imageView;

    UtilsSingleton utils = UtilsSingleton.getInstance();
    private Context context;

    public DisplayNotificationViewHolder (View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        SharedPref.init(context);
    }

    public void updateWithDisplayNotificationArticles(Doc articles, RequestManager glide){
        this.textViewTitle.setText(articles.getHeadline().getMain());
        this.textViewDate.setText(utils.getGoodFormatDate(articles.getPubDate()));

        if (articles.getMultimedia().isEmpty() == false){
            glide.load("https://static01.nyt.com/" + articles.getMultimedia().get(0).getUrl()).into(imageView);
        }
        else {
            glide.load("https://images-eu.ssl-images-amazon.com/images/I/615KWjAew4L.png").into(imageView);
        }
    }
}
