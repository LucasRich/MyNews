package com.lucas.mynews.Controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.lucas.mynews.Controllers.Fragments.TopStoriesFragment;
import com.lucas.mynews.Utils.SharedPref;
import com.lucas.mynews.Views.Adapter.PageAdapter;

import com.lucas.mynews.R;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static Context contextOfApplication;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private Fragment topStoriesFragment;

    private static final int FRAGMENT_TOPSTORIES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextOfApplication = getApplicationContext();

        this.configureToolbar();
        this.configureViewPagerAndTabs();
        this.configureDrawerLayout();
        this.configureNavigationView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the sz` and add it to the Toolbar
        getMenuInflater().inflate(R.menu.toolbar_main_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    // ---------------------
    // CONFIGURATION
    // ---------------------

    // 1 - Configure Toolbar
    private void configureToolbar(){
        this.toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);
    }

    private void configureViewPagerAndTabs(){
        ViewPager pager = (ViewPager)findViewById(R.id.activity_main_viewpager);
        //Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        // 1 - Get TabLayout from layout
        TabLayout tabs= (TabLayout)findViewById(R.id.activity_main_tabs);
        // 2 - Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // 3 - Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    // ---------------------
    // ACTION
    // ---------------------

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on sz` items
        switch (item.getItemId()) {
            case R.id.Notifications:
                launchNotificationActivity();
                 return true;
            case R.id.Help:
                launchHelpActivity();
                 return true;
            case R.id.About:
                launchAboutActivity();
                 return true;
            case R.id.menu_activity_main_search:
                launchSearchArticleActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        ViewPager pager = (ViewPager)findViewById(R.id.activity_main_viewpager);

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_search :
                this.launchSearchArticleActivity();
                break;
            case R.id.activity_main_drawer_notification :
                this.launchNotificationActivity();
                break;
            case R.id.activity_main_drawer_top_stories :
                pager.setCurrentItem(0, true);
                break;
            case R.id.activity_main_drawer_most_popular :
                pager.setCurrentItem(1, true);
                break;
            case R.id.activity_main_drawer_movie_reviews :
                pager.setCurrentItem(2, true);
                break;
            case R.id.activity_main_drawer_help :
                this.launchHelpActivity();
                break;
            case R.id.activity_main_drawer_about :
                this.launchAboutActivity();
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // ---------------------
    // UTILS
    // ---------------------

    private void launchSearchArticleActivity(){
        Intent myIntent = new Intent(MainActivity.this, SearchArticleActivity.class);
        this.startActivity(myIntent);
    }

    private void launchNotificationActivity(){
        Intent myIntent = new Intent(MainActivity.this, NotificationActivity.class);
        this.startActivity(myIntent);
    }

    private void launchHelpActivity(){
        Intent myIntent = new Intent(MainActivity.this, HelpActivity.class);
        this.startActivity(myIntent);
    }

    private void launchAboutActivity(){
        Intent myIntent = new Intent(MainActivity.this, AboutActivity.class);
        this.startActivity(myIntent);
    }


    public static Context getContextOfApplication(){
        return contextOfApplication;
    }
}
