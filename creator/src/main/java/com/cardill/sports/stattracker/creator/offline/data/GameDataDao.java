package com.cardill.sports.stattracker.creator.offline.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.cardill.sports.stattracker.common.data.GameData;

import java.util.List;

@Dao
public interface GameDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long add(GameData gameData);

    @Query("SELECT * FROM `GameData`")
    List<GameData> get();
}
