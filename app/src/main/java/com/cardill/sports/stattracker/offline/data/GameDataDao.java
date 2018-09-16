package com.cardill.sports.stattracker.offline.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.cardill.sports.stattracker.game.data.GameData;

@Dao
public interface GameDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(GameData gameData);
}
