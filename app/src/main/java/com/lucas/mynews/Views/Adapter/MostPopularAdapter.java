package com.lucas.mynews.Views.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
        private RequestManager glide;

        // CONSTRUCTOR
        public MostPopularAdapter(List<MostPopularArticle> articles, RequestManager glide) {
            this.articles = articles;
            this.glide = glide;
        }

        @Override
        public MostPopularViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.fragment_most_popular_item, parent, false);

            return new MostPopularViewHolder(view);
        }

    // UPDATE VIEW HOLDER
        @Override
        public void onBindViewHolder(MostPopularViewHolder viewHolder, int position) {
            viewHolder.updateWithMostPopularArticles(this.articles.get(position), this.glide);
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

