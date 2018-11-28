package com.cardill.sports.stattracker.article;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.article.businesslogic.AbstractViewBinder;
import com.cardill.sports.stattracker.article.businesslogic.ArticleAdapter;
import com.cardill.sports.stattracker.article.businesslogic.ArticlePresenter;
import com.cardill.sports.stattracker.article.data.CardillContent;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticleListFragment extends Fragment implements AbstractViewBinder<List<CardillContent>> {

    private ArticlePresenter mArticlePresenter;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mArticlePresenter = new ArticlePresenter(this);

        View rootView = inflater.inflate(R.layout.fragment_article, container, false);
        mRecyclerView = rootView.findViewById(R.id.article_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mArticlePresenter.loadData();
    }

    @Override
    public void onDataLoaded(List<CardillContent> data) {
        ArticleAdapter adapter = new ArticleAdapter(data, getContext());
        mRecyclerView.setAdapter(adapter);
    }
}