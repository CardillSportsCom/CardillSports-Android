package com.cardillsports.stattracker;

import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.game.ui.GameActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class ApplicationModule {

    @ContributesAndroidInjector
    abstract GameActivity contributeActivityInjector();

    @Provides
    static CardillService provideCardillService(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        String BASE_URL = "https://api-cardillsports-st.herokuapp.com";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(CardillService.class);
    }
}
