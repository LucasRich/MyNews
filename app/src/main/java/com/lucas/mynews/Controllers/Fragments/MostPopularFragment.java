package com.lucas.mynews.Controllers.Fragments;

import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;

import com.lucas.mynews.Controllers.Activities.MainActivity;
import com.lucas.mynews.Models.MostPopular.MostPopularArticle;
import com.lucas.mynews.Models.MostPopular.MostPopularResponse;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.Constant;
import com.lucas.mynews.Utils.ItemClickSupport;
import com.lucas.mynews.Utils.NyTimeStreams;
import com.lucas.mynews.Views.Adapter.MostPopularAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class MostPopularFragment extends BaseFragment {

    private List<MostPopularArticle> articles;
    private MostPopularAdapter adapter;

    public static MostPopularFragment newInstance() {
        return (new MostPopularFragment());
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_display_article;
    }

    @Override
    protected void launchConfiguration() {
        this.configureRecyclerView();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureRecyclerView(){
        this.articles = new ArrayList<>();
        this.adapter = new MostPopularAdapter(this.articles);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_display_article_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    MostPopularArticle dlArticle = adapter.getArticle(position);
                    goToWebView(dlArticle.getUrl());
                });
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    @Override
    protected void executeHttpRequestWithRetrofit(){
        this.disposable = NyTimeStreams.streamFetchMostPopularArticles(Constant.mostPopularPeriod, Constant.apiKey)
                .subscribeWith(new DisposableObserver<MostPopularResponse>(){
                    @Override
                    public void onNext(MostPopularResponse response) {
                        Log.e("TAG","On Next");

                        List<MostPopularArticle> dlArticles = response.getResult();
                        updateUI(dlArticles);
                    }

                    @Override public void onError(Throwable e) { Log.e("TAG","On Error"+Log.getStackTraceString(e)); }

                    @Override public void onComplete() { Log.e("TAG","On Complete !!"); }
                });
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(List<MostPopularArticle> dlArticles){
        swipeRefreshLayout.setRefreshing(false);
        articles.clear();
        articles.addAll(dlArticles);
        adapter.notifyDataSetChanged();
    }
}
