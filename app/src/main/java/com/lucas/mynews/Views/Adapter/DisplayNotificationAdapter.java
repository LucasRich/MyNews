package com.lucas.mynews.Views.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.lucas.mynews.Models.Search.Doc;
import com.lucas.mynews.R;
import com.lucas.mynews.Views.DisplayNotificationViewHolder;
import com.lucas.mynews.Views.SearchViewHolder;

import java.util.List;

public class DisplayNotificationAdapter extends RecyclerView.Adapter<DisplayNotificationViewHolder> {

    private List<Doc> articles;
    private RequestManager glide;

    // CONSTRUCTOR
    public DisplayNotificationAdapter(List<Doc> articles, RequestManager glide) {
        this.articles = articles;
        this.glide = glide;
    }

    @Override
    public DisplayNotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_display_notification_item, parent, false);

        return new DisplayNotificationViewHolder(view);
    }

    // UPDATE VIEW HOLDER
    @Override
    public void onBindViewHolder(DisplayNotificationViewHolder viewHolder, int position) {
        viewHolder.updateWithDisplayNotificationArticles(this.articles.get(position), this.glide);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.articles.size();
    }

    public Doc getArticle(int position){
        return this.articles.get(position);
    }
}
