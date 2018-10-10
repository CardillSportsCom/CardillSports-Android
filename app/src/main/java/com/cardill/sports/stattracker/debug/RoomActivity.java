package com.cardill.sports.stattracker.debug;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.game.data.GameData;
import com.cardill.sports.stattracker.network.MockData;
import com.cardill.sports.stattracker.offline.domain.LocalGameRepository;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RoomActivity extends AppCompatActivity {

    @Inject
    LocalGameRepository gameDataStore;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        View readButton = findViewById(R.id.read);
        View writeButton = findViewById(R.id.write);

        writeButton.setOnClickListener(v -> {
            GameData gameData = MockData.mockGameData();
            gameDataStore.add(gameData)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        });

        readButton.setOnClickListener(v -> {
            Disposable disposable = gameDataStore.get()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(gameData ->
                    {
                        mFirebaseAnalytics.setUserId("VITHUSHAN'S PHONE");
                        Bundle params = new Bundle();
                        params.putParcelableArrayList("game-recovery-key", gameData);
                        mFirebaseAnalytics.logEvent("game_recovery", params);
                        Timber.tag("VITHUSHAN").d("JUST GOT: %s", gameData.size());
                    });

        });

    }


}
