package com.lucas.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;


import com.lucas.mynews.Models.Search.SearchArticle;
import com.lucas.mynews.Models.Search.SearchResponse;
import com.lucas.mynews.Models.TopStories.TopStoriesArticle;
import com.lucas.mynews.Models.TopStories.TopStoriesResponse;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.NyTimeStreams;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchArticleActivity extends AppCompatActivity implements
        View.OnClickListener{

    private int mYear, mMonth, mDay;

    @BindView(R.id.btnDateBegin) ImageButton btnBeginDate;
    @BindView(R.id.btnDateEnd) ImageButton btnEndDate;
    @BindView(R.id.beginDate) EditText txtBeginDate;
    @BindView(R.id.endDate) EditText txtEndDate;

    private Disposable disposable;
    private List<SearchArticle> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article);
        ButterKnife.bind(this);
        this.configureToolbar();

        btnBeginDate.setOnClickListener(this);
        btnEndDate.setOnClickListener(this);

        executeHttpRequestWithRetrofit();
    }

    @Override
    public void onClick(View v) {

        if (v == btnBeginDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            txtBeginDate.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            if(monthOfYear > 9) {
                                txtBeginDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                            else {
                                txtBeginDate.setText(dayOfMonth + "/" + "0" + (monthOfYear + 1) + "/" + year);
                            }

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        if (v == btnEndDate) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            txtEndDate.setText(mDay + "/" + (mMonth + 1) + "/" + mYear);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            if(monthOfYear > 9) {
                                txtEndDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                            else {
                                txtEndDate.setText(dayOfMonth + "/" + "0" + (monthOfYear + 1) + "/" + year);
                            }
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
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
        this.disposable = NyTimeStreams.streamFetchSearchArticles("election", "CMCk9Nz5BAjNKu5cF8nkDmoMzd3EOJST")
                .subscribeWith(new DisposableObserver<SearchResponse>(){
                    @Override
                    public void onNext(SearchResponse response) {
                        Log.e("TAG","On Next");

                        List<SearchArticle> dlArticles = response.getResult();

                            System.out.println(dlArticles);
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
}
