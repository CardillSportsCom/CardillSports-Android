package com.cardillsports.stattracker.offline.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.cardillsports.stattracker.game.data.GameData;

@Dao
public interface GameDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(GameData gameData);
}
