package com.lucas.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lucas.mynews.Models.TopStories.TopStoriesArticle;
import com.lucas.mynews.R;

import com.bumptech.glide.RequestManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TopStoriesViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.fragment_top_stories_item_title) TextView textViewTitle;
    @BindView(R.id.fragment_top_stories_item_date) TextView textViewDate;
    @BindView(R.id.fragment_top_stories_item_section) TextView textViewSection;
    @BindView(R.id.fragment_top_stories_item_image) ImageView imageView;

    public TopStoriesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithTopStoriesArticles(TopStoriesArticle articles, RequestManager glide){
        this.textViewTitle.setText(articles.getTitle());
        this.textViewDate.setText(getGoodFormatDate(articles.getPublishedDate()));
        this.textViewSection.setText(DisplaySectionAndSubsctionIfNoNull(articles.getSection(), articles.getSubsection()));

        if (articles.getMultimedia().isEmpty() == false){
            glide.load(articles.getMultimedia().get(0).getUrl()).into(imageView);
        }else {
            glide.load("https://images-eu.ssl-images-amazon.com/images/I/615KWjAew4L.png").into(imageView);
        }
    }

    private String DisplaySectionAndSubsctionIfNoNull(String section, String subsection){
        String result;

        if (subsection.equals("")){
            result = section;
        }else {
            result =section + " > " + subsection;
        }
        return result;
    }

    private String getGoodFormatDate(String publishedDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yy-mm-dd");
        String dateInString = publishedDate;

        try {
            Date date = formatter.parse(dateInString.replaceAll("Z$", "+0000"));
            publishedDate = formatter.format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        StringBuilder myName = new StringBuilder(publishedDate);
        myName.setCharAt(2, '/');
        myName.setCharAt(5, '/');
        publishedDate = myName.toString();

        return publishedDate;
    }
}
