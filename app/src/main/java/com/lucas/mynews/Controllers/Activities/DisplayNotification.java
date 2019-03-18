package com.lucas.mynews.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.lucas.mynews.Models.Search.Doc;
import com.lucas.mynews.Models.Search.SearchResponse;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.ItemClickSupport;
import com.lucas.mynews.Utils.NyTimeStreams;
import com.lucas.mynews.Utils.SharedPref;
import com.lucas.mynews.Utils.UtilsSingleton;
import com.lucas.mynews.Views.Adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class DisplayNotification extends AppCompatActivity {

    @BindView(R.id.activity_display_notification_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.activity_display_notification_swipe_container) SwipeRefreshLayout swipeRefreshLayout;

    private Disposable disposable;
    private List<Doc> articles;
    private SearchAdapter adapter;
    private String beginDate;
    private String endDate;
    private String articleSearch;
    private int nbArticle;

    UtilsSingleton utils = UtilsSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notification);
        ButterKnife.bind(this);
        SharedPref.init(getApplicationContext());

        // START CONFIGURATION
        this.configureToolbar();
        this.configureRecyclerView();
        this.configureSwipeRefreshLayout();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureRecyclerView(){
        this.articles = new ArrayList<>();
        this.adapter = new SearchAdapter(this.articles, Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configureToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                articles.clear();
                executeHttpRequestWithRetrofit();
            }
        });
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.activity_display_notification_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Doc dlArticle = adapter.getArticle(position);

                        Intent myIntent = new Intent(DisplayNotification.this, WebViewDisplayNotification.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("url", dlArticle.getWebUrl());

                        myIntent.putExtras(bundle);
                        startActivity(myIntent);
                    }
                });
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        this.disposable = NyTimeStreams.streamFetchSearchArticles(SharedPref.read(SharedPref.notificationArticleSearch, ""),
                utils.getCurrentDate(), endDate, "CMCk9Nz5BAjNKu5cF8nkDmoMzd3EOJST")

                .subscribeWith(new DisposableObserver<SearchResponse>(){
                    @Override
                    public void onNext(SearchResponse response) {
                        Log.e("TAG","On Next");
                        List<Doc> dlArticles = response.getResponse().getDocs();
                        updateUI(dlArticles);
                    }

                    @Override public void onError(Throwable e) { Log.e("TAG","On Error"+Log.getStackTraceString(e)); }

                    @Override public void onComplete() { Log.e("TAG","On Complete !!"); }
                });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(List<Doc> dlArticles){
        swipeRefreshLayout.setRefreshing(false);
        articles.clear();
        articles.addAll(dlArticles);
        adapter.notifyDataSetChanged();
    }
}
