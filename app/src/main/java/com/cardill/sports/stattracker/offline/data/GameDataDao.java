package com.cardill.sports.stattracker.offline.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cardill.sports.stattracker.game.data.GameData;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface GameDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(GameData gameData);

    @Query("SELECT * FROM `GameData`")
    List<GameData> get();
}
