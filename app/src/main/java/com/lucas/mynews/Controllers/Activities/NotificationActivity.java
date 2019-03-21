package com.lucas.mynews.Controllers.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.lucas.mynews.Models.NyTimesApiResponse;
import com.lucas.mynews.Models.Search.Doc;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.AlarmReceiver;
import com.lucas.mynews.Utils.Constant;
import com.lucas.mynews.Utils.NyTimeStreams;
import com.lucas.mynews.Utils.SharedPref;
import com.lucas.mynews.Utils.UtilsFunction;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
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
    private int nbArticle = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        SharedPref.init(getApplicationContext());
        ButterKnife.bind(this);

        this.configureToolbar();
    }

    // -------------------
    // CONFIGURATION
    // -------------------

    private void configureToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        Objects.requireNonNull(ab).setDisplayHomeAsUpEnabled(true);
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

    @OnClick(R.id.switch_notification)
    public void clickOnSwitchNotification() {
        if (!checkBoxArts.isChecked() && !checkBoxBusiness.isChecked() && !checkBoxEntrepreneurs.isChecked() &&
                !checkBoxPolitics.isChecked() && !checkBoxSports.isChecked() && !checkBoxTravel.isChecked())
        {
            switchNotification.setChecked(false);
            Toast.makeText(getApplication(), "You must select a category", Toast.LENGTH_LONG).show();
        }
    }

    // -------------------
    // HTTP (RxJAVA) OBTAIN ARTICLES NUMBER
    // -------------------

    private void executeHttpRequestWithRetrofit(){
        this.disposable = NyTimeStreams.streamFetchSearchArticles(SharedPref.read(SharedPref.notificationArticleSearch, ""),
                UtilsFunction.getCurrentDate(), UtilsFunction.getCurrentDate(), Constant.apiKey)

                .subscribeWith(new DisposableObserver<NyTimesApiResponse>(){
                    @Override
                    public void onNext(NyTimesApiResponse response) {
                        Log.e("TAG","On Next");
                        List<Doc> dlArticles = response.getResponseSearchArticle().getDocs();

                        for (Doc dlArticle : dlArticles){
                            nbArticle++;
                        }
                        SharedPref.write(SharedPref.nbArticles, nbArticle);
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
    // INIT NOTIFICATION INPUT WITH PREFERENCES
    // -------------------

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

    // -------------------
    // SAVE NOTIFICATION INPUT ON PREFERENCE AND START NOTIFICATION IF SWITCH AND ONE CHECKBOX TRUE
    // -------------------

    @Override
    protected void onStop() {
        super.onStop();

        // SAVE INPUT ON PREFERENCE
        SharedPref.write(SharedPref.txtBoxSearchNotification, txtBoxSearchNotification.getText().toString());
        SharedPref.write(SharedPref.checkBoxArts, checkBoxArts.isChecked());
        SharedPref.write(SharedPref.checkBoxBusiness, checkBoxBusiness.isChecked());
        SharedPref.write(SharedPref.checkBoxEntrepreneurs, checkBoxEntrepreneurs.isChecked());
        SharedPref.write(SharedPref.checkBoxPolitics, checkBoxPolitics.isChecked());
        SharedPref.write(SharedPref.checkBoxSports, checkBoxSports.isChecked());
        SharedPref.write(SharedPref.checkBoxTravel, checkBoxTravel.isChecked());
        SharedPref.write(SharedPref.switchNotification, switchNotification.isChecked());

        //ACTIVATE NOTIFICATION
        if(SharedPref.read(SharedPref.switchNotification, false) && (checkBoxArts.isChecked()||
                checkBoxBusiness.isChecked() || checkBoxEntrepreneurs.isChecked() || checkBoxPolitics.isChecked() ||
                checkBoxSports.isChecked() || checkBoxTravel.isChecked()))
        {
            SharedPref.write(SharedPref.notificationArticleSearch, UtilsFunction.createParamQuery(txtBoxSearchNotification.getText().toString(),
                    checkBoxArts, checkBoxBusiness, checkBoxEntrepreneurs, checkBoxPolitics, checkBoxSports, checkBoxTravel,
                    checkBoxArts.getText().toString(), checkBoxBusiness.getText().toString(), checkBoxEntrepreneurs.getText().toString(),
                    checkBoxPolitics.getText().toString(), checkBoxSports.getText().toString(), checkBoxTravel.getText().toString()));

            executeHttpRequestWithRetrofit();
            startNotificationAtMidday();
            SharedPref.write(SharedPref.notificationAllow, true);
        }
        else {
            SharedPref.write(SharedPref.notificationAllow, false);
            SharedPref.write(SharedPref.switchNotification, false);
        }
    }
}
