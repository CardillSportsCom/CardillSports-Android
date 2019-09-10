package com.cardill.sports.stattracker.consumer.common.data;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.cardill.sports.stattracker.common.businesslogic.CardillTableListener;
import com.cardill.sports.stattracker.consumer.common.businesslogic.ConsumerPlayerStatsTableAdapter;
import com.cardill.sports.stattracker.consumer.common.businesslogic.SortableCardillTableListener;
import com.cardill.sports.stattracker.common.data.PlayerStatType;
import com.cardill.sports.stattracker.common.data.Stat;
import com.cardill.sports.stattracker.consumer.profile.businesslogic.PlayerStatsTableAdapter;
import com.cardill.sports.stattracker.consumer.profile.data.HistoricalStatType;
import com.cardill.sports.stattracker.consumer.profile.data.HistoricalStatTypeTitleProvider;
import com.cardill.sports.stattracker.consumer.profile.data.PlayerStat;
import com.evrencoskun.tableview.TableView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import timber.log.Timber;

import static com.cardill.sports.stattracker.common.businesslogic.StatsTableAdapter.NON_EDITABLE;

public class ConsumerTableUtils {
    private static final String SOURCE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /*
        BOX SCORE
     */
    public static void initBoxScoreTable(FragmentActivity activity, TableView tableView, List<ConsumerPlayer> teamOne, List<ConsumerPlayer> teamTwo) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        ConsumerStatsTableAdapter adapter = new ConsumerStatsTableAdapter(activity, NON_EDITABLE);

        tableView.setAdapter(adapter);

        List<HistoricalStatType> columnHeaderItems = Arrays.asList(HistoricalStatType.values());
        List<List<Stat>> mCellList = ConsumerTableUtils.generateBoxScoreTable(teamOne, teamTwo, NumberFormat.getPercentInstance());

        List<ConsumerGamePlayer> players = new ArrayList<>();

        for (ConsumerPlayer player : teamOne) {
            players.add(new ConsumerGamePlayer(player, true, false));
        }
        for (ConsumerPlayer player : teamTwo) {
            players.add(new ConsumerGamePlayer(player, false, true));
        }

        adapter.setAllItems(columnHeaderItems, players, mCellList);
        tableView.setTableViewListener(new CardillTableListener(tableView));

        tableView.setColumnWidth(0, 400);
        tableView.setColumnWidth(1, 200);
        tableView.setColumnWidth(2, 200);
        tableView.setColumnWidth(3, 200);
        tableView.setColumnWidth(4, 200);
        tableView.setColumnWidth(5, 200);
        tableView.setColumnWidth(6, 200);
        tableView.setColumnWidth(7, 200);
        tableView.setColumnWidth(8, 200);
        tableView.setColumnWidth(9, 200);
        tableView.setColumnWidth(10,200);
    }

    private static List<List<Stat>> generateBoxScoreTable(List<ConsumerPlayer> teamOne, List<ConsumerPlayer> teamTwo, NumberFormat numberFormat) {
        List<List<Stat>> cellList = new ArrayList<>();

        for (ConsumerPlayer player : teamOne) {
            cellList.add(createBoxScoreStatList(player, true));
        }

        for (ConsumerPlayer player : teamTwo) {
            cellList.add(createBoxScoreStatList(player, false));
        }

        return cellList;
    }

    private static List<Stat> createBoxScoreStatList(ConsumerPlayer player, boolean isTeamOne) {
        List<Stat> statList = new ArrayList<>(8);

        double fg = 0;
        if (player.fieldGoalMade() != 0) {
            fg = player.fieldGoalMade() / (double) (player.fieldGoalMissed());
        }
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        String fieldGoalString = percentInstance.format(fg) +
                " (" + player.fieldGoalMade() + " / " + player.fieldGoalMissed() + ") ";
        statList.add(new Stat(HistoricalStatType.FG_PERCENT, fieldGoalString, isTeamOne));

        statList.add(new Stat(HistoricalStatType.POINTS, player.points(), isTeamOne));
        statList.add(new Stat(HistoricalStatType.THREES, player.getThreePointersMade(), isTeamOne));
        statList.add(new Stat(HistoricalStatType.AST, player.assists(), isTeamOne));
        statList.add(new Stat(HistoricalStatType.REB, player.rebounds(), isTeamOne));
        statList.add(new Stat(HistoricalStatType.STL, player.steals(), isTeamOne));
        statList.add(new Stat(HistoricalStatType.BLK, player.blocks(), isTeamOne));
        statList.add(new Stat(HistoricalStatType.TO, player.turnovers(), isTeamOne));

        return statList;
    }

    /*
        STATS
     */

    public static void initStatsTable(FragmentActivity activity, TableView tableView, List<ConsumerPlayer> players) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        ConsumerPlayerStatsTableAdapter adapter = new ConsumerPlayerStatsTableAdapter(activity, NON_EDITABLE);

        tableView.setAdapter(adapter);

        List<PlayerStatType> columnHeaderItems = Arrays.asList(PlayerStatType.values());
        List<List<Stat>> mCellList = generateStatsTableCellList(players);

        List<ConsumerGamePlayer> gamePlayers = new ArrayList<>();

        for (ConsumerPlayer player : players) {
            gamePlayers.add(new ConsumerGamePlayer(player, true, false));
        }
        adapter.setAllItems(columnHeaderItems, gamePlayers, mCellList);

        tableView.setTableViewListener(new SortableCardillTableListener(tableView));

        tableView.setColumnWidth(0, 200);
        tableView.setColumnWidth(1, 200);
        tableView.setColumnWidth(2, 400);
        tableView.setColumnWidth(3, 200);
        tableView.setColumnWidth(4, 200);
        tableView.setColumnWidth(5, 200);
        tableView.setColumnWidth(6, 200);
        tableView.setColumnWidth(7, 200);
        tableView.setColumnWidth(8, 200);
        tableView.setColumnWidth(9, 200);
        //tableView.setColumnWidth(10,200);
    }

    private static List<List<Stat>> generateStatsTableCellList(List<ConsumerPlayer> players) {
        List<List<Stat>> cellList = new ArrayList<>();

        for (ConsumerPlayer player : players) {
            List<Stat> statList = new ArrayList<>(8);
            statList.add(new Stat(PlayerStatType.WINS, player.wins(), true));
            statList.add(new Stat(PlayerStatType.GP, player.gamesPlayed(), true));

            double fg = 0;
            if (player.fieldGoalMade() != 0) {
                fg = player.fieldGoalMade() / (double) player.fieldGoalMissed();
            }
            NumberFormat percentInstance = NumberFormat.getPercentInstance();
            String fieldGoalString = percentInstance.format(fg) +
                    " (" + player.fieldGoalMade() + " / " + player.fieldGoalMissed() + ") ";
            statList.add(new Stat(PlayerStatType.FG_PERCENT, fieldGoalString, true));

            statList.add(new Stat(PlayerStatType.POINTS, player.points(), true));
            //threes
            statList.add(new Stat(PlayerStatType.AST, player.assists(), true));
            statList.add(new Stat(PlayerStatType.REB, player.rebounds(), true));
            statList.add(new Stat(PlayerStatType.STL, player.steals(), true));
            statList.add(new Stat(PlayerStatType.BLK, player.blocks(), true));
            statList.add(new Stat(PlayerStatType.TO, player.turnovers(), true));
            cellList.add(statList);
        }

        return cellList;
    }

    /*
        PROFILE
     */
    public static void initProfileTable(Context context, TableView tableView, PlayerStat[] playerStats) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        PlayerStatsTableAdapter adapter = new PlayerStatsTableAdapter(context);
        SimpleDateFormat sdf = new SimpleDateFormat(
                SOURCE_PATTERN, Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        tableView.setAdapter(adapter);

        List<String> columnHeaderItems = new ArrayList<>();
        for (HistoricalStatType historicalStatType : HistoricalStatType.values()) {
            String title = context.getString(HistoricalStatTypeTitleProvider.getTitle(historicalStatType));
            columnHeaderItems.add(title);
        }

        List<List<String>> mCellList = generateProfileTableCellList(playerStats);

        List<Date> gameDates = new ArrayList<>();

        for (PlayerStat playerStat : playerStats) {

            try {
                Date date = sdf.parse(playerStat.getDateCreated());
                gameDates.add(date);
            } catch (ParseException e) {
                Timber.e(e);
            }

        }
        adapter.setAllItems(columnHeaderItems, gameDates, mCellList);

        tableView.setTableViewListener(new CardillTableListener(tableView));

        tableView.setColumnWidth(0, 200);
        tableView.setColumnWidth(1, 200);
        tableView.setColumnWidth(2, 200);
        tableView.setColumnWidth(3, 250);
        tableView.setColumnWidth(4, 200);
        tableView.setColumnWidth(5, 200);
        tableView.setColumnWidth(6, 200);
        tableView.setColumnWidth(7, 200);
        tableView.setColumnWidth(8, 200);
        tableView.setColumnWidth(9, 200);
        tableView.setColumnWidth(10, 200);
    }

    private static List<List<String>> generateProfileTableCellList(PlayerStat[] playerStats) {
        List<List<String>> cellList = new ArrayList<>();
        NumberFormat percentInstance = NumberFormat.getPercentInstance();

        for (PlayerStat playerStat : playerStats) {
            List<String> statList = new ArrayList<>(8);

            double fg = 0;
            if (playerStat.getFGM() != 0) {
                fg = playerStat.getFGM() / (double) (playerStat.getFGA());
            }
            String fieldGoalString = percentInstance.format(fg) +
                    " (" + playerStat.getFGM() + " / " + playerStat.getFGA() + ") ";

            statList.add(fieldGoalString);
            statList.add(String.valueOf(playerStat.getPoints()));
            statList.add(String.valueOf(playerStat.getTwoPointersMade()));
            statList.add(String.valueOf(playerStat.getAssists()));
            statList.add(String.valueOf(playerStat.getRebounds()));
            statList.add(String.valueOf(playerStat.getSteals()));
            statList.add(String.valueOf(playerStat.getBlocks()));
            statList.add(String.valueOf(playerStat.getTurnovers()));

            List<String> stringList = new ArrayList<>();

            for (String stat : statList) {
                stringList.add(String.valueOf(stat));
            }

            cellList.add(stringList);
        }

        return cellList;
    }
}
