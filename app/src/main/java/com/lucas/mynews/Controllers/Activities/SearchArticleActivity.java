package com.lucas.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchArticleActivity extends AppCompatActivity{

    private int mYear, mMonth, mDay;

    @BindView(R.id.btnDateBegin) ImageButton btnBeginDate;
    @BindView(R.id.btnDateEnd) ImageButton btnEndDate;
    @BindView(R.id.beginDate) EditText txtBeginDate;
    @BindView(R.id.endDate) EditText txtEndDate;
    @BindView(R.id.boxSearch) EditText txtBoxSearch;
    @BindView(R.id.txtDownloading) TextView txtDownloading;
    @BindView(R.id.checkBoxArts) CheckBox checkBoxArts;
    @BindView(R.id.checkBoxBusiness) CheckBox checkBoxBusiness;
    @BindView(R.id.checkBoxEntrepreneurs) CheckBox checkBoxEntrepreneurs;
    @BindView(R.id.checkBoxPolitics) CheckBox checkBoxPolitics;
    @BindView(R.id.checkBoxSports) CheckBox checkBoxSports;
    @BindView(R.id.checkBoxTravel) CheckBox checkBoxTravel;
    @BindView(R.id.btnSearch) Button btnSearch;

    @BindView(R.id.activity_search_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.activity_search_swipe_container) SwipeRefreshLayout swipeRefreshLayout;

    private Disposable disposable;
    private List<Doc> articles;
    private SearchAdapter adapter;
    private boolean searchDone = false;
    private String beginDate;
    private String endDate;
    private String articleSearch;

    UtilsSingleton utils = UtilsSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article);
        ButterKnife.bind(this);
        this.configureToolbar();

        // START CONFIGURATION
        this.configureRecyclerView();
        this.configureSwipeRefreshLayout();
        this.configureOnClickRecyclerView();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void configureRecyclerView(){
        this.articles = new ArrayList<>();
        this.adapter = new SearchAdapter(this.articles, Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.activity_search_article_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Doc dlArticle = adapter.getArticle(position);

                        Intent myIntent = new Intent(SearchArticleActivity.this, WebViewActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("url", dlArticle.getWebUrl());

                        myIntent.putExtras(bundle);
                        startActivity(myIntent);
                    }
                });
    }

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (searchDone == true){
                    articles.clear();
                    updateUIWhenStartingHTTPRequest();
                    executeHttpRequestWithRetrofit();
                }
            }
        });
    }

    // -----------------
    // ACTION
    // -----------------

    @OnClick(R.id.btnDateBegin)
    public void onClickBtnDateBegin() {

        // GET CURRENT DATE
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // SELECT DATE
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        if(monthOfYear > 9) {
                            txtBeginDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            beginDate = (year + "" + (monthOfYear + 1) + "" + dayOfMonth);
                        }
                        else {
                            txtBeginDate.setText(dayOfMonth + "/" + "0" + (monthOfYear + 1) + "/" + year);
                            beginDate = (year + "" + "0" + (monthOfYear + 1) + "" + dayOfMonth);

                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.btnDateEnd)
    public void onClickBtnDateEnd() {

        // GET CURRENT DATE
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        // SELECT DATE
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        if(monthOfYear > 9) {
                            txtEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            endDate = (year + "" + (monthOfYear + 1) + "" + dayOfMonth);

                        }
                        else {
                            txtEndDate.setText(dayOfMonth + "/" + "0" + (monthOfYear + 1) + "/" + year);
                            endDate = (year + "" + "0" + (monthOfYear + 1) + "" + dayOfMonth);

                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.btnSearch)
    public void onClickBtnSearch() {
        articles.clear();

        articleSearch = utils.createParamQuery(txtBoxSearch.getText().toString(), checkBoxArts, checkBoxBusiness, checkBoxEntrepreneurs, checkBoxPolitics,
                checkBoxSports, checkBoxTravel, checkBoxArts.getText().toString(), checkBoxBusiness.getText().toString(),
                checkBoxEntrepreneurs.getText().toString(), checkBoxPolitics.getText().toString(),
                checkBoxSports.getText().toString(), checkBoxTravel.getText().toString());

        this.updateUIWhenStartingHTTPRequest();
        this.executeHttpRequestWithRetrofit();
        searchDone = true;
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        this.disposable = NyTimeStreams.streamFetchSearchArticles(articleSearch, beginDate, endDate,"CMCk9Nz5BAjNKu5cF8nkDmoMzd3EOJST")
                .subscribeWith(new DisposableObserver<SearchResponse>(){
                    @Override
                    public void onNext(SearchResponse response) {
                        Log.e("TAG","On Next");

                        if (response.getResponse().getDocs().isEmpty() == false){
                            txtDownloading.setText("");
                            List<Doc> dlArticles = response.getResponse().getDocs();
                            updateUI(dlArticles);
                        }
                        else{
                            articles.clear();
                            txtDownloading.setText("Search not found");
                        }
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

    private void updateUIWhenStartingHTTPRequest(){
        txtDownloading.setText("Downloading...");
    }
}
