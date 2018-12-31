package com.cardill.sports.stattracker;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.birbit.android.jobqueue.JobManager;
import com.birbit.android.jobqueue.config.Configuration;
import com.cardill.sports.stattracker.debug.RoomActivity;
import com.cardill.sports.stattracker.game.ui.PlayerListActivity;
import com.cardill.sports.stattracker.league.LeagueRepository;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.network.MockCardillService;
import com.cardill.sports.stattracker.offline.domain.RemoteGameRepository;
import com.cardill.sports.stattracker.game.di.GameActivityModule;
import com.cardill.sports.stattracker.game.ui.GameRecapActivity;
import com.cardill.sports.stattracker.details.ui.DetailsActivity;
import com.cardill.sports.stattracker.game.ui.GameActivity;
import com.cardill.sports.stattracker.offline.data.GameDataDao;
import com.cardill.sports.stattracker.offline.data.GameDatabase;
import com.cardill.sports.stattracker.offline.data.LocalGameDataStore;
import com.cardill.sports.stattracker.offline.data.RemoteGameDataStore;
import com.cardill.sports.stattracker.offline.domain.LocalGameRepository;
import com.cardill.sports.stattracker.offline.domain.services.jobs.SyncGameJob;
import com.cardill.sports.stattracker.profile.ProfileActivity;
import com.cardill.sports.stattracker.teamcreation.ui.TeamCreationActivity;
import com.cardill.sports.stattracker.main.MainActivity;
import com.cardill.sports.stattracker.teamselection.ui.TeamSelectionActivity;
import com.cardill.sports.stattracker.user.AuthorizationInterceptor;
import com.cardill.sports.stattracker.user.UnauthorizedInterceptor;
import com.cardill.sports.stattracker.user.Session;
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

    @ContributesAndroidInjector
    abstract TeamCreationActivity contributeTeamCreationActivityInjector();

    @ContributesAndroidInjector
    abstract PlayerListActivity contributePlayerListActivityInjector();

    @ContributesAndroidInjector(modules = GameActivityModule.class)
    abstract GameActivity contributeGameActivityInjector();

    @ContributesAndroidInjector
    abstract DetailsActivity contributeDetailsActivityInjector();

    @ContributesAndroidInjector
    abstract GameRecapActivity contributeBoxScoreActivityInjector();

    @ContributesAndroidInjector
    abstract RoomActivity contributeRoomActivityInjector();

    @ContributesAndroidInjector
    abstract ProfileActivity contributeProfileActivityInjector();

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
    static JobManager provideJobManager(Application application, CardillService service, LeagueRepository leagueRepository) {

        Configuration config = new Configuration.Builder(application.getApplicationContext())
                .injector(job -> {
                    if (job instanceof SyncGameJob) {
                        ((JobManagerInjectable) job).inject(service, leagueRepository);
                    }
                })
                .build();

        return new JobManager(config);
    }

    @Singleton
    @Provides
    static RemoteGameRepository provideRemoteGameRepository(JobManager jobManager, CardillService service) {
        return new RemoteGameDataStore(jobManager, service);
    }

    @Provides
    static Session provideSession(Application application) {
        SharedPreferences sharedPref = application.getApplicationContext()
                .getSharedPreferences("com.cardill.sports.stattracker", (Context.MODE_PRIVATE));

        return new Session(sharedPref);
    }

    @Provides
    @Singleton
    static AuthService provideAuthService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(AuthService.class);
    }

    @Provides
    @Singleton
    static CardillService provideCardillService(Session session, AuthService authService) {

        //TODO (vithushan) make this a build config or something better than a local var
        boolean online = true;

        if (online) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .addInterceptor(new AuthorizationInterceptor(session))
                    .addInterceptor(new UnauthorizedInterceptor(session, authService))
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .client(httpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            return retrofit.create(CardillService.class);
        } else {
            return new MockCardillService();
        }
    }

    @Provides
    @Singleton
    static LeagueRepository provideLeagueRepository(Application application) {
        return new LeagueRepository(application);
    }
}
