package com.cardillsports.stattracker.offline.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.offline.model.ModelConstants;

@Database(entities = {GameData.class}, version = 1, exportSchema = false)
public abstract class GameDatabase extends RoomDatabase {
    private static GameDatabase instance;

    public static synchronized GameDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), GameDatabase.class, ModelConstants.DB_NAME)
                    .build();
        }
        return instance;
    }

    public abstract GameDataDao gameDataDao();
}
