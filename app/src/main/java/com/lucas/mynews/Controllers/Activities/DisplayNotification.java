package com.lucas.mynews.Controllers.Activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;

import com.lucas.mynews.Models.NyTimesApiResponse;
import com.lucas.mynews.Models.Search.Doc;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.Constant;
import com.lucas.mynews.Utils.ItemClickSupport;
import com.lucas.mynews.Utils.NyTimeStreams;
import com.lucas.mynews.Utils.SharedPref;
import com.lucas.mynews.Utils.UtilsFunction;
import com.lucas.mynews.Views.Adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        this.adapter = new SearchAdapter(this.articles);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configureToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(() -> {
            articles.clear();
            executeHttpRequestWithRetrofit();
        });
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.activity_display_notification_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Doc dlArticle = adapter.getArticle(position);

                    Intent myIntent = new Intent(DisplayNotification.this, WebViewActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putString(Constant.bundleKeyUrl, dlArticle.getWebUrl());

                    myIntent.putExtras(bundle);
                    startActivity(myIntent);
                });
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        this.disposable = NyTimeStreams.streamFetchSearchArticles(SharedPref.read(SharedPref.notificationArticleSearch, ""),
                UtilsFunction.getCurrentDate(), UtilsFunction.getCurrentDate(), Constant.apiKey)

                .subscribeWith(new DisposableObserver<NyTimesApiResponse>(){
                    @Override
                    public void onNext(NyTimesApiResponse response) {
                        Log.e("TAG","On Next");
                        List<Doc> dlArticles = response.getResponseSearchArticle().getDocs();
                        updateUI(dlArticles);
                    }

                    @Override public void onError(Throwable e) { Log.e("TAG","On Error"+Log.getStackTraceString(e)); }

                    @Override public void onComplete() { Log.e("TAG","On Complete !!"); }
                });
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
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
