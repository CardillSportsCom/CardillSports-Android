package com.cardill.sports.stattracker;

import android.app.Application;

import com.cardill.sports.stattracker.common.data.CardillService;
import com.cardill.sports.stattracker.common.data.MockCardillService;
import com.cardill.sports.stattracker.offline.domain.RemoteGameRepository;
import com.cardill.sports.stattracker.game.di.GameActivityModule;
import com.cardill.sports.stattracker.game.ui.BoxScoreActivity;
import com.cardill.sports.stattracker.details.ui.DetailsActivity;
import com.cardill.sports.stattracker.game.ui.GameActivity;
import com.cardill.sports.stattracker.offline.data.GameDataDao;
import com.cardill.sports.stattracker.offline.data.GameDatabase;
import com.cardill.sports.stattracker.offline.data.LocalGameDataStore;
import com.cardill.sports.stattracker.offline.data.RemoteGameDataStore;
import com.cardill.sports.stattracker.offline.domain.LocalGameRepository;
import com.cardill.sports.stattracker.ui.MainActivity;
import com.cardill.sports.stattracker.teamselection.ui.TeamSelectionActivity;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = AndroidInjectionModule.class)
public abstract class ApplicationModule {

    @Binds
    abstract Application application(CardillApplication app);

    @ContributesAndroidInjector
    abstract TeamSelectionActivity contributeTeamSelectionActivityInjector();

    @ContributesAndroidInjector(modules = GameActivityModule.class)
    abstract GameActivity contributeGameActivityInjector();

    @ContributesAndroidInjector
    abstract DetailsActivity contributeDetailsActivityInjector();

    @ContributesAndroidInjector
    abstract BoxScoreActivity contributeBoxScoreActivityInjector();

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity contributeMainActivityInjector();

    @Singleton
    @Provides
    static GameDataDao provideGameDao(Application application) {
        return GameDatabase.getInstance(application.getApplicationContext())
                .gameDataDao();
    }

    @Singleton
    @Provides
    static LocalGameRepository provideLocalGameRepository(GameDataDao gameDataDao) {
        return new LocalGameDataStore(gameDataDao);
    }

    @Singleton
    @Provides
    static RemoteGameRepository provideRemoteGameRepository() {
        return new RemoteGameDataStore();
    }

    @Provides
    @Singleton
    static CardillService provideCardillService(){

        //TODO (vithushan) make this a build config or something better than a local var
        boolean online = true;

        if (online) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            String BASE_URL = "https://api-cardillsports-st.herokuapp.com";
            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            return retrofit.create(CardillService.class);
        } else {
            return new MockCardillService();
        }
    }
}
