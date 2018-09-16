package com.cardill.sports.stattracker.common.data;

import com.cardill.sports.stattracker.game.data.GameData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vithushan on 9/8/18.
 */

public class MockData {

    public static GameData mockGameData() {

        List<Player> team1 = getTeam1();
        List<Player> team2 = getTeam2();


        GameData gameData = new GameData(0, team1, team2, false);

        return gameData;
    }

    private static List<Player> getTeam1() {

        List<Player> team1 = new ArrayList<>();

        Player p1 = Player.builder()
                .id("p1")
                .firstName("Vithushan")
                .lastName("N")
                .assists(1)
                .build();
        Player p2 = Player.builder()
                .id("p1")
                .firstName("JV")
                .lastName("N")
                .rebounds(3)
                .build();
        Player p3 = Player.builder()
                .id("p1")
                .firstName("Jason")
                .lastName("N")
                .fieldGoalMade(4)
                .build();
        Player p4 = Player.builder()
                .id("p1")
                .firstName("Sujee")
                .lastName("N")
                .assists(1)
                .fieldGoalMissed(2)
                .build();
        team1.add(p1);
        team1.add(p2);
        team1.add(p3);
        team1.add(p4);
        return team1;
    }

    private static List<Player> getTeam2() {

        List<Player> team1 = new ArrayList<>();

        Player p1 = Player.builder()
                .id("p1")
                .firstName("Sukee")
                .lastName("N")
                .assists(1)
                .build();
        Player p2 = Player.builder()
                .id("p1")
                .firstName("Prageen")
                .lastName("N")
                .rebounds(3)
                .build();
        Player p3 = Player.builder()
                .id("p1")
                .firstName("Lucksson")
                .lastName("N")
                .fieldGoalMade(4)
                .build();
        Player p4 = Player.builder()
                .id("p1")
                .firstName("Mith")
                .lastName("N")
                .assists(1)
                .fieldGoalMissed(2)
                .build();
        team1.add(p1);
        team1.add(p2);
        team1.add(p3);
        team1.add(p4);
        return team1;
    }
}
