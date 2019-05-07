package com.cardill.sports.stattracker.consumer.article.network;

import com.cardill.sports.stattracker.consumer.article.data.CardillContent;
import com.cardill.sports.stattracker.consumer.article.data.CreatorModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CardillSportsClient {
    @GET("/api/content")
    Call<List<CardillContent>> content();

    @GET("/api/podcasts")
    Call<List<CardillContent>> podcasts();

    @GET("/api/creators")
    Call<List<CreatorModel>> creators();
}