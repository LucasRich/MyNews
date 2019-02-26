package com.lucas.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import com.lucas.mynews.Models.Search.Doc;
import com.lucas.mynews.Models.Search.SearchResponse;
import com.lucas.mynews.Models.TopStories.TopStoriesArticle;
import com.lucas.mynews.Models.TopStories.TopStoriesResponse;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.AlarmReceiver;
import com.lucas.mynews.Utils.NyTimeStreams;
import com.lucas.mynews.Utils.SharedPref;
import com.lucas.mynews.Utils.UtilsSingleton;
import com.lucas.mynews.Views.Adapter.SearchAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.single.SingleJust;
import io.reactivex.observers.DisposableObserver;

public class NotificationActivity extends AppCompatActivity {

    @BindView(R.id.checkBoxArts) CheckBox checkBoxArts;
    @BindView(R.id.checkBoxBusiness) CheckBox checkBoxBusiness;
    @BindView(R.id.checkBoxEntrepreneurs) CheckBox checkBoxEntrepreneurs;
    @BindView(R.id.checkBoxPolitics) CheckBox checkBoxPolitics;
    @BindView(R.id.checkBoxSports) CheckBox checkBoxSports;
    @BindView(R.id.checkBoxTravel) CheckBox checkBoxTravel;
    @BindView(R.id.boxSearch_notification) EditText txtBoxSearchNotification;
    @BindView(R.id.switch_notification) Switch switchNotification;

    private Disposable disposable;
    private List<Doc> articles;
    private String beginDate;
    private String endDate;
    private int nbArticle = 0;

    UtilsSingleton utils = UtilsSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        SharedPref.init(getApplicationContext());
        ButterKnife.bind(this);
        configureToolbar();
        startNotificationAtMidday();
        }

    // -------------------
    // CONFIGURATION
    // -------------------

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
    // ACTION
    // -------------------

    private void startNotificationAtMidday(){
        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,12);
        calendar.set(Calendar.MINUTE, 00);

        //alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 100, pendingIntent);

    }

    // -------------------
    // HTTP (RxJAVA) OBTAIN ARTICLES NUMBER
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        this.disposable = NyTimeStreams.streamFetchSearchArticles(SharedPref.read(SharedPref.notificationArticleSearch, ""), beginDate, endDate,
                "CMCk9Nz5BAjNKu5cF8nkDmoMzd3EOJST")

                .subscribeWith(new DisposableObserver<SearchResponse>(){
                    @Override
                    public void onNext(SearchResponse response) {
                        Log.e("TAG","On Next");
                        List<Doc> dlArticles = response.getResponse().getDocs();

                            for (Doc dlArticle : dlArticles){
                              nbArticle++;
                            }
                        SharedPref.write(SharedPref.nbArticles, nbArticle);
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

    @Override
    protected void onStart() {
        super.onStart();

        txtBoxSearchNotification.setText(SharedPref.read(SharedPref.txtBoxSearchNotification, ""));

        checkBoxArts.setChecked(SharedPref.read(SharedPref.checkBoxArts, false));
        checkBoxBusiness.setChecked(SharedPref.read(SharedPref.checkBoxBusiness, false));
        checkBoxEntrepreneurs.setChecked(SharedPref.read(SharedPref.checkBoxEntrepreneurs, false));
        checkBoxPolitics.setChecked(SharedPref.read(SharedPref.checkBoxPolitics, false));
        checkBoxSports.setChecked(SharedPref.read(SharedPref.checkBoxSports, false));
        checkBoxTravel.setChecked(SharedPref.read(SharedPref.checkBoxTravel, false));

        switchNotification.setChecked(SharedPref.read(SharedPref.switchNotification, false));
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPref.write(SharedPref.txtBoxSearchNotification, txtBoxSearchNotification.getText().toString());

        SharedPref.write(SharedPref.checkBoxArts, checkBoxArts.isChecked());
        SharedPref.write(SharedPref.checkBoxBusiness, checkBoxBusiness.isChecked());
        SharedPref.write(SharedPref.checkBoxEntrepreneurs, checkBoxEntrepreneurs.isChecked());
        SharedPref.write(SharedPref.checkBoxPolitics, checkBoxPolitics.isChecked());
        SharedPref.write(SharedPref.checkBoxSports, checkBoxSports.isChecked());
        SharedPref.write(SharedPref.checkBoxTravel, checkBoxTravel.isChecked());

        SharedPref.write(SharedPref.switchNotification, switchNotification.isChecked());

        if(SharedPref.read(SharedPref.switchNotification, false) == true && (checkBoxArts.isChecked() == true || checkBoxBusiness.isChecked() == true ||
                checkBoxEntrepreneurs.isChecked() == true || checkBoxPolitics.isChecked() == true || checkBoxSports.isChecked() == true ||
                checkBoxTravel.isChecked() == true))
        {

            SharedPref.write(SharedPref.notificationArticleSearch, utils.createParamQuery(txtBoxSearchNotification.getText().toString(), checkBoxArts,
                    checkBoxBusiness, checkBoxEntrepreneurs, checkBoxPolitics, checkBoxSports, checkBoxTravel, checkBoxArts.getText().toString(),
                    checkBoxBusiness.getText().toString(), checkBoxEntrepreneurs.getText().toString(), checkBoxPolitics.getText().toString(),
                    checkBoxSports.getText().toString(), checkBoxTravel.getText().toString()));

            executeHttpRequestWithRetrofit();
            startNotificationAtMidday();
            SharedPref.write(SharedPref.notificationAllow, true);
        }
        else {
            SharedPref.write(SharedPref.notificationAllow, false);
        }
    }
}
