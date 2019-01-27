package com.lucas.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;


import com.lucas.mynews.R;

import java.util.Calendar;

public class SearchArticleActivity extends AppCompatActivity implements
        View.OnClickListener{

    ImageButton btnBeginDate;
    ImageButton btnEndDate;
    EditText txtBeginDate;
    EditText txtEndDate;
    private int mYear, mMonth, mDay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article);

        this.configureToolbar();

        btnBeginDate = (ImageButton) findViewById(R.id.btnDateBegin);
        btnEndDate = (ImageButton) findViewById(R.id.btnDateEnd);
        txtBeginDate = (EditText) findViewById(R.id.beginDate);
        txtEndDate = (EditText) findViewById(R.id.endDate);


        btnBeginDate.setOnClickListener(this);
        btnEndDate.setOnClickListener(this);
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
}
