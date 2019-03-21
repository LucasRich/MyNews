package com.lucas.mynews.Controllers.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucas.mynews.Controllers.Activities.MainActivity;
import com.lucas.mynews.Controllers.Activities.WebViewActivity;
import com.lucas.mynews.R;
import com.lucas.mynews.Utils.Constant;
import com.lucas.mynews.Utils.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment {

    protected abstract int getFragmentLayout();
    protected abstract void launchConfiguration();
    protected abstract void executeHttpRequestWithRetrofit();

    @BindView(R.id.fragment_display_article_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.fragment_display_article_swipe_container) SwipeRefreshLayout swipeRefreshLayout;

    protected Disposable disposable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, view);

        this.configureSwipeRefreshLayout();
        this.launchConfiguration();

        return(view);
    }

    private void configureSwipeRefreshLayout(){
        swipeRefreshLayout.setOnRefreshListener(this::executeHttpRequestWithRetrofit);
    }

    protected void goToWebView (String url) {

        Intent myIntent = new Intent(getActivity(), WebViewActivity.class);
        Bundle bundle = new Bundle();

        bundle.putString(Constant.bundleKeyUrl, url);

        myIntent.putExtras(bundle);
        startActivity(myIntent);
    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }
}
