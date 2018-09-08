package com.cardillsports.stattracker;

import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.common.data.MockCardillService;
import com.cardillsports.stattracker.game.data.GameRepository;
import com.cardillsports.stattracker.game.ui.DetailsActivity;
import com.cardillsports.stattracker.game.ui.GameActivity;
import com.cardillsports.stattracker.main.ui.MainActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class ApplicationModule {


    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivityInjector();

    @ContributesAndroidInjector
    abstract GameActivity contributeGameActivityInjector();

    @ContributesAndroidInjector
    abstract DetailsActivity contributeDetailsActivityInjector();

    @Provides
    static CardillService provideCardillService(){

        //TODO (vithushan) make this a build config or something better than a local var
        boolean online = true;

        if (online) {
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
        } else {
            return new MockCardillService();
        }
    }
}
