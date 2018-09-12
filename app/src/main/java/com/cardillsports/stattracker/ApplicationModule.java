package com.cardillsports.stattracker;

import android.app.Application;

import com.cardillsports.stattracker.common.data.CardillService;
import com.cardillsports.stattracker.common.data.MockCardillService;
import com.cardillsports.stattracker.offline.domain.RemoteGameRepository;
import com.cardillsports.stattracker.game.di.GameActivityModule;
import com.cardillsports.stattracker.game.ui.BoxScoreActivity;
import com.cardillsports.stattracker.details.ui.DetailsActivity;
import com.cardillsports.stattracker.game.ui.GameActivity;
import com.cardillsports.stattracker.offline.data.GameDataDao;
import com.cardillsports.stattracker.offline.data.GameDatabase;
import com.cardillsports.stattracker.offline.data.LocalGameDataStore;
import com.cardillsports.stattracker.offline.data.RemoteGameDataStore;
import com.cardillsports.stattracker.offline.domain.LocalGameRepository;
import com.cardillsports.stattracker.teamselection.ui.TeamSelectionActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = AndroidInjectionModule.class)
public abstract class ApplicationModule {

    @Binds
    abstract Application application(CardillApplication app);

    @ContributesAndroidInjector
    abstract TeamSelectionActivity contributeMainActivityInjector();

    @ContributesAndroidInjector(modules = GameActivityModule.class)
    abstract GameActivity contributeGameActivityInjector();

    @ContributesAndroidInjector
    abstract DetailsActivity contributeDetailsActivityInjector();

    @ContributesAndroidInjector
    abstract BoxScoreActivity contributeBoxScoreActivityInjector();

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
