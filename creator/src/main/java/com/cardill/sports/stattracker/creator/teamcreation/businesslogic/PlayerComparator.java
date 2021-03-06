package com.cardill.sports.stattracker.creator.teamcreation.businesslogic;

import com.cardill.sports.stattracker.common.data.Player;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
    @Override
    public int compare(Player player1, Player player2) {
        return player1.firstName().compareTo(player2.firstName());
    }
}
