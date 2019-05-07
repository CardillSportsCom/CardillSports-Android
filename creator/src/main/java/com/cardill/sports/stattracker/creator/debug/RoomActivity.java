package com.cardill.sports.stattracker.creator.debug;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cardill.sports.stattracker.creator.R;
import com.cardill.sports.stattracker.common.data.GameData;
import com.cardill.sports.stattracker.creator.offline.domain.LocalGameRepository;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RoomActivity extends AppCompatActivity {

    private static final int PERMISSION_WRITE_STORAGE = 100;
    @Inject
    LocalGameRepository gameDataStore;

    private FirebaseAnalytics mFirebaseAnalytics;
    private ArrayList<GameData> gameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        View readButton = findViewById(R.id.read);
        View writeButton = findViewById(R.id.write);

        writeButton.setOnClickListener(v -> {
            // No op
        });

        readButton.setOnClickListener(v -> {
            Disposable disposable = gameDataStore.get()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(gameData ->
                    {
                        if (ContextCompat.checkSelfPermission(this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                                && isExternalStorageWritable()) {
                            this.gameData = gameData;
                            writeToDownloads(this.gameData);
                        } else {
                            ActivityCompat.requestPermissions(this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    PERMISSION_WRITE_STORAGE);
                        }
                        Timber.tag("VITHUSHAN").d("JUST GOT: %s", gameData.size());
                    });

        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    writeToDownloads(gameData);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void writeToDownloads(ArrayList<GameData> gameData) {
        Gson gson = new Gson();
        String s = gson.toJson(gameData);
        FileOutputStream outputStream;

        try {
            File storageDir = getPublicAlbumStorageDir("cardill");
            File file = new File(storageDir, "logs.txt");
            outputStream = new FileOutputStream(file);
            outputStream.write(s.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), albumName);

        if (!file.mkdirs()) {
            Timber.e("Directory not created");
        }

        return file;

    }


}