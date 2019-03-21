package com.lucas.mynews.Views.Adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucas.mynews.Models.Search.Doc;
import com.lucas.mynews.R;
import com.lucas.mynews.Views.SearchViewHolder;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {

    private List<Doc> articles;

    // CONSTRUCTOR
    public SearchAdapter(List<Doc> articles) {
        this.articles = articles;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_search_article_item, parent, false);

        return new SearchViewHolder(view);
    }

    // UPDATE VIEW HOLDER
    @Override
    public void onBindViewHolder(SearchViewHolder viewHolder, int position) {
        viewHolder.updateWithSearchArticles(this.articles.get(position));
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
