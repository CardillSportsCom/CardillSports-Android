package com.cardill.sports.stattracker.stats.data;

import java.io.IOException;

import com.fasterxml.jackson.annotation.*;

public enum Name {
    TEAM_1, TEAM_2;

    @JsonValue
    public String toValue() {
        switch (this) {
            case TEAM_1: return "Team 1";
            case TEAM_2: return "Team 2";
        }
        return null;
    }

    @JsonCreator
    public static Name forValue(String value) throws IOException {
        if (value.equals("Team 1")) return TEAM_1;
        if (value.equals("Team 2")) return TEAM_2;
        throw new IOException("Cannot deserialize Name");
    }
}