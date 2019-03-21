package com.lucas.mynews.Views.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucas.mynews.Models.TopStories.TopStoriesArticle;
import com.lucas.mynews.R;
import com.lucas.mynews.Views.TopStoriesViewHolder;

import java.util.List;

public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesViewHolder> {

    // FOR DATA
    private List<TopStoriesArticle> articles;

    // CONSTRUCTOR
    public TopStoriesAdapter(List<TopStoriesArticle> articles) {
        this.articles = articles;
    }

    @Override
    public TopStoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_display_article_item, parent, false);

        return new TopStoriesViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A GITHUBUSER
    @Override
    public void onBindViewHolder(TopStoriesViewHolder viewHolder, int position) {
        viewHolder.updateWithTopStoriesArticles(this.articles.get(position));
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.articles.size();
    }

    public TopStoriesArticle getArticle(int position){
        return this.articles.get(position);
    }
}
