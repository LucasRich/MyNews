package com.lucas.mynews.Controllers.Fragments;


import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;

import com.lucas.mynews.Controllers.Activities.MainActivity;
import com.lucas.mynews.Models.NyTimesApiResponse;
import com.lucas.mynews.Models.TopStories.TopStoriesArticle;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.Constant;
import com.lucas.mynews.Utils.ItemClickSupport;
import com.lucas.mynews.Utils.NyTimeStreams;
import com.lucas.mynews.Views.Adapter.TopStoriesAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class TopStoriesFragment extends BaseFragment {

    private List<TopStoriesArticle> articles;
    private TopStoriesAdapter adapter;

    public static TopStoriesFragment newInstance() {
        return (new TopStoriesFragment());
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
        this.adapter = new TopStoriesAdapter(this.articles);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_display_article_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    TopStoriesArticle dlArticle = adapter.getArticle(position);
                    goToWebView(dlArticle.getUrl());
                });
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    @Override
    protected void executeHttpRequestWithRetrofit(){
        this.disposable = NyTimeStreams.streamFetchTopStoriesArticles(Constant.topStoriesSection, Constant.apiKey)
                .subscribeWith(new DisposableObserver<NyTimesApiResponse>(){
                    @Override
                    public void onNext(NyTimesApiResponse response) {
                        Log.e("TAG","On Next");

                        List<TopStoriesArticle> dlArticles = response.getResultsTopStories();
                        updateUI(dlArticles);
                    }

                    @Override public void onError(Throwable e) { Log.e("TAG","On Error"+Log.getStackTraceString(e)); }

                    @Override public void onComplete() { Log.e("TAG","On Complete !!"); }
                });
    }

    // ------------------
    //  UPDATE UI
    // ------------------

    private void updateUI(List<TopStoriesArticle> dlArticles){
        swipeRefreshLayout.setRefreshing(false);
        articles.clear();
        articles.addAll(dlArticles);
        adapter.notifyDataSetChanged();
    }
}
