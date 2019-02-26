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
import android.widget.TextView;

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

        this.configureToolbar();
        this.configureRecyclerView();
        this.configureSwipeRefreshLayout();
        this.configureOnClickRecyclerView();
        this.executeHttpRequestWithRetrofit();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // 3.1 - Reset list
        this.articles = new ArrayList<>();
        // 3.2 - Create adapter passing the list of articles
        this.adapter = new SearchAdapter(this.articles, Glide.with(this));
        // 3.3 - Attach the adapter to the recyclerview to populate items
        this.recyclerView.setAdapter(this.adapter);
        // 3.4 - Set layout manager to position the items
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    private void configureToolbar(){
        //Get the toolbar (Serialise)
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Set the toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        this.disposable = NyTimeStreams.streamFetchSearchArticles(SharedPref.read(SharedPref.notificationArticleSearch, ""), beginDate, endDate,
                                                            "CMCk9Nz5BAjNKu5cF8nkDmoMzd3EOJST")

                .subscribeWith(new DisposableObserver<SearchResponse>(){
                    @Override
                    public void onNext(SearchResponse response) {
                        Log.e("TAG","On Next");
                        List<Doc> dlArticles = response.getResponse().getDocs();
                        updateUI(dlArticles);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG","On Error"+Log.getStackTraceString(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG","On Complete !!");
                    }
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
