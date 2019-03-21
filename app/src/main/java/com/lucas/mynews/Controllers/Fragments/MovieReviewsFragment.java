package com.lucas.mynews.Controllers.Fragments;

import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;

import com.lucas.mynews.Controllers.Activities.MainActivity;
import com.lucas.mynews.Models.MovieReviews.MovieReviewsArticle;
import com.lucas.mynews.Models.MovieReviews.MovieReviewsResponse;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.Constant;
import com.lucas.mynews.Utils.ItemClickSupport;
import com.lucas.mynews.Utils.NyTimeStreams;
import com.lucas.mynews.Utils.UtilsFunction;
import com.lucas.mynews.Views.Adapter.MovieReviewsAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class MovieReviewsFragment extends BaseFragment {

    private List<MovieReviewsArticle> articles;
    private MovieReviewsAdapter adapter;

    public static MovieReviewsFragment newInstance() {
        return (new MovieReviewsFragment());
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_display_article;
    }

    @Override
    protected void launchConfiguration() {
        this.configureRecyclerView();
        this.executeHttpRequestWithRetrofit();
        this.configureOnClickRecyclerView();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureRecyclerView(){
        this.articles = new ArrayList<>();
        this.adapter = new MovieReviewsAdapter(this.articles);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_display_article_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    MovieReviewsArticle dlArticle = adapter.getArticle(position);
                    goToWebView(UtilsFunction.getGoodFormatUrl(dlArticle.getLink().getUrl()));
                });
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    @Override
    protected void executeHttpRequestWithRetrofit(){
        this.disposable = NyTimeStreams.streamFetchMovieReviewsArticles(Constant.movieReviewsType, Constant.apiKey)
                .subscribeWith(new DisposableObserver<MovieReviewsResponse>(){
                    @Override
                    public void onNext(MovieReviewsResponse response) {
                        Log.e("TAG","On Next");

                        List<MovieReviewsArticle> dlArticles = response.getResult();
                        updateUI(dlArticles);
                    }

                    @Override public void onError(Throwable e) { Log.e("TAG","On Error"+Log.getStackTraceString(e)); }

                    @Override public void onComplete() { Log.e("TAG","On Complete !!"); }
                });
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(List<MovieReviewsArticle> dlArticles){
        swipeRefreshLayout.setRefreshing(false);
        articles.clear();
        articles.addAll(dlArticles);
        adapter.notifyDataSetChanged();
    }
}
