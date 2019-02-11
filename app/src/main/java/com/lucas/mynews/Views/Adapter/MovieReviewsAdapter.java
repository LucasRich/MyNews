package com.lucas.mynews.Views.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.lucas.mynews.Models.MostPopular.MostPopularArticle;
import com.lucas.mynews.Models.MovieReviews.MovieReviewsArticle;
import com.lucas.mynews.R;
import com.lucas.mynews.Views.MostPopularViewHolder;
import com.lucas.mynews.Views.MovieReviewsViewHolder;

import java.util.List;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsViewHolder>{

    // FOR DATA
    private List<MovieReviewsArticle> articles;
    private RequestManager glide;

    // CONSTRUCTOR
    public MovieReviewsAdapter(List<MovieReviewsArticle> articles, RequestManager glide) {
        this.articles = articles;
        this.glide = glide;
    }

    @Override
    public MovieReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_movie_reviews_item, parent, false);

        return new MovieReviewsViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH A GITHUBUSER
    @Override
    public void onBindViewHolder(MovieReviewsViewHolder viewHolder, int position) {
        viewHolder.updateWithMovieReviewsArticles(this.articles.get(position), this.glide);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.articles.size();
    }

    public MovieReviewsArticle getArticle(int position){
        return this.articles.get(position);
    }
}

