package com.lucas.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.lucas.mynews.Models.NyTimesApiResponse;
import com.lucas.mynews.Models.Search.Doc;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.Constant;
import com.lucas.mynews.Utils.ItemClickSupport;
import com.lucas.mynews.Utils.NyTimeStreams;
import com.lucas.mynews.Utils.UtilsFunction;
import com.lucas.mynews.Views.Adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

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
    private String beginDate;
    private String endDate;
    private String articleSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article);
        ButterKnife.bind(this);

        // START CONFIGURATION
        this.configureToolbar();
        this.configureRecyclerView();
        this.configureSwipeRefreshLayout();
        this.configureOnClickRecyclerView();
        swipeRefreshLayout.setEnabled(false);
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
    }

    private void configureRecyclerView(){
        this.articles = new ArrayList<>();
        this.adapter = new SearchAdapter(this.articles);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.activity_search_article_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Doc dlArticle = adapter.getArticle(position);

                    Intent myIntent = new Intent(SearchArticleActivity.this, WebViewActivity.class);
                    Bundle bundle = new Bundle();

                    bundle.putString(Constant.bundleKeyUrl, dlArticle.getWebUrl());

                    myIntent.putExtras(bundle);
                    startActivity(myIntent);
                });
    }

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(() -> {
                articles.clear();
                updateUIWhenStartingHTTPRequest();
                executeHttpRequestWithRetrofit();
        });
    }

    //MAKE UP BTN BEHAVE LIKE BACK BTN
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return(super.onOptionsItemSelected(item));
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
                (view, year, monthOfYear, dayOfMonth) -> {

                    if(monthOfYear > 9) {
                        txtBeginDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        beginDate = (year + "" + (monthOfYear + 1) + "" + dayOfMonth);
                    }
                    else {
                        txtBeginDate.setText(dayOfMonth + "/" + "0" + (monthOfYear + 1) + "/" + year);
                        beginDate = (year + "" + "0" + (monthOfYear + 1) + "" + dayOfMonth);

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
                (view, year, monthOfYear, dayOfMonth) -> {

                    if(monthOfYear > 9) {
                        txtEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        endDate = (year + "" + (monthOfYear + 1) + "" + dayOfMonth);

                    }
                    else {
                        txtEndDate.setText(dayOfMonth + "/" + "0" + (monthOfYear + 1) + "/" + year);
                        endDate = (year + "" + "0" + (monthOfYear + 1) + "" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.btnSearch)
    public void onClickBtnSearch() {
        articles.clear();

        articleSearch = UtilsFunction.createParamQuery(txtBoxSearch.getText().toString(), checkBoxArts, checkBoxBusiness, checkBoxEntrepreneurs, checkBoxPolitics,
                checkBoxSports, checkBoxTravel, checkBoxArts.getText().toString(), checkBoxBusiness.getText().toString(),
                checkBoxEntrepreneurs.getText().toString(), checkBoxPolitics.getText().toString(),
                checkBoxSports.getText().toString(), checkBoxTravel.getText().toString());

        if((checkBoxArts.isChecked() || checkBoxBusiness.isChecked() || checkBoxEntrepreneurs.isChecked() ||
                checkBoxPolitics.isChecked() || checkBoxSports.isChecked() || checkBoxTravel.isChecked()))
        {
            this.updateUIWhenStartingHTTPRequest();
            this.executeHttpRequestWithRetrofit();
            swipeRefreshLayout.setEnabled(true);
        }
        else {
            Toast.makeText(getApplication(), "You must select a category", Toast.LENGTH_LONG).show();
        }
    }

    // -------------------
    // HTTP (RxJAVA)
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        this.disposable = NyTimeStreams.streamFetchSearchArticles(articleSearch, beginDate, endDate,Constant.apiKey)
                .subscribeWith(new DisposableObserver<NyTimesApiResponse>(){
                    @Override
                    public void onNext(NyTimesApiResponse response) {
                        Log.e("TAG","On Next");

                        if (response.getResponseSearchArticle().getDocs().isEmpty() == false){
                            txtDownloading.setText("");
                            List<Doc> dlArticles = response.getResponseSearchArticle().getDocs();
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

    private void updateUIWhenStartingHTTPRequest(){
        txtDownloading.setText("Downloading...");
    }
}
