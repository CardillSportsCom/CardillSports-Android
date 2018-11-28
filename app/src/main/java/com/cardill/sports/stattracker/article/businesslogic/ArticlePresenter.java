package com.cardill.sports.stattracker.article.businesslogic;

import android.util.Log;

import com.cardill.sports.stattracker.article.data.CardillContent;
import com.cardill.sports.stattracker.article.network.CardillSportsClient;
import com.cardill.sports.stattracker.article.network.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlePresenter {

    private CardillSportsClient mClient;
    private AbstractViewBinder<List<CardillContent>> mViewBinder;


    public ArticlePresenter(AbstractViewBinder<List<CardillContent>> viewBinder) {
        mClient = ServiceGenerator.createService(CardillSportsClient.class);
        mViewBinder = viewBinder;
    }

    public void loadData() {
        Call<List<CardillContent>> call = mClient.content();
        call.enqueue(new Callback<List<CardillContent>>() {
            @Override
            public void onResponse(Call<List<CardillContent>> responseCall, Response<List<CardillContent>> response) {
                if (response.isSuccessful()) {
                    List<CardillContent> cardillContents = response.body();
                    mViewBinder.onDataLoaded(cardillContents);
                } else {
                    // error response, no access to resource?
                }
            }

            @Override
            public void onFailure(Call<List<CardillContent>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }


}
