package com.lucas.mynews.Controllers.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucas.mynews.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchArticleFragment extends Fragment {


    public static SearchArticleFragment newInstance() {
        return (new SearchArticleFragment());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_article, container, false);
    }

}
