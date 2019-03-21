package com.lucas.mynews.Views.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.lucas.mynews.Models.MostPopular.MostPopularArticle;
import com.lucas.mynews.R;
import com.lucas.mynews.Views.MostPopularViewHolder;

import java.util.List;

public class MostPopularAdapter extends RecyclerView.Adapter<MostPopularViewHolder>{

        // FOR DATA
        private List<MostPopularArticle> articles;

        // CONSTRUCTOR
        public MostPopularAdapter(List<MostPopularArticle> articles) {
            this.articles = articles;
        }

        @NonNull
        @Override
        public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.fragment_display_article_item, parent, false);

            return new MostPopularViewHolder(view);
        }

        // UPDATE VIEW HOLDER
        @Override
        public void onBindViewHolder(@NonNull MostPopularViewHolder viewHolder, int position) {
            viewHolder.updateWithMostPopularArticles(this.articles.get(position));
        }

        // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
        @Override
        public int getItemCount() {
            return this.articles.size();
        }

        public MostPopularArticle getArticle(int position){
            return this.articles.get(position);
        }
    }

