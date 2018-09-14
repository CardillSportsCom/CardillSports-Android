package com.cardillsports.stattracker.game.data;

import android.arch.persistence.room.TypeConverter;

import com.cardillsports.stattracker.common.data.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Created by vithushan on 9/12/18.
 */
public class PlayerTypeConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<Player> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Player>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Player> someObjects) {
        return gson.toJson(someObjects);
    }
}
