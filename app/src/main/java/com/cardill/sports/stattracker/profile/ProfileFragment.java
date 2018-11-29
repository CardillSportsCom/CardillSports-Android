package com.cardill.sports.stattracker.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.CardillTableListener;
import com.cardill.sports.stattracker.common.SortableCardillTableListener;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.ui.BaseFragment;
import com.cardill.sports.stattracker.game.data.Stat;
import com.cardill.sports.stattracker.game.data.StatType;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.profile.businesslogic.PlayerStatsTableAdapter;
import com.cardill.sports.stattracker.profile.businesslogic.ProfilePresenter;
import com.cardill.sports.stattracker.profile.businesslogic.ProfileViewBinder;
import com.cardill.sports.stattracker.profile.data.PlayerStatType;
import com.cardill.sports.stattracker.stats.data.PlayerStat;
import com.cardill.sports.stattracker.stats.data.PlayerStatResponse;
import com.evrencoskun.tableview.TableView;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import timber.log.Timber;

import static com.cardill.sports.stattracker.common.SortableCardillTableListener.PLAYER_ID_KEY;

/**
 * Shows a player profile
 */
public class ProfileFragment extends BaseFragment implements ProfileViewBinder {

    private static final String SOURCE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private TextView nameTextView;
    private ProfilePresenter mPresenter;
    private ProgressBar progress;
    private TableView tableView;

    @Inject
    CardillService cardillService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nameTextView = view.findViewById(R.id.name);
        progress = view.findViewById(R.id.progress);
        tableView = view.findViewById(R.id.table_view);
        mPresenter = new ProfilePresenter(this, cardillService);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String playerId = getArguments().getString(PLAYER_ID_KEY);
        mPresenter.onLoad(playerId);
    }

    @Override
    public void showProfile(PlayerStatResponse playerStatResponse) {
        String playerName = playerStatResponse.getPlayerStats()[0].getPlayer();
        nameTextView.setText(playerName);

        progress.setVisibility(View.GONE);

        initTableView(tableView, playerStatResponse.getPlayerStats());
    }

    private void initTableView(TableView tableView, PlayerStat[] playerStats) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        PlayerStatsTableAdapter adapter = new PlayerStatsTableAdapter(getActivity());
        SimpleDateFormat sdf = new SimpleDateFormat(
                ProfileFragment.SOURCE_PATTERN, Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        tableView.setAdapter(adapter);

        List<String> columnHeaderItems =
                Lists.transform(
                        Arrays.asList(PlayerStatType.values()),
                        Enum::name);

        List<List<String>> mCellList = generateTableCellList(playerStats);

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

    private List<List<String>> generateTableCellList(PlayerStat[] playerStats) {
        List<List<String>> cellList = new ArrayList<>();

        for (PlayerStat playerStat : playerStats) {
            List<Long> statList = new ArrayList<>(8);
            statList.add(playerStat.getFgm());
            statList.add(playerStat.getFga());
            statList.add(playerStat.getAssists());
            statList.add(playerStat.getRebounds());
            statList.add(playerStat.getSteals());
            statList.add(playerStat.getBlocks());
            statList.add(playerStat.getTurnovers());

            List<String> stringList = Lists.transform(
                    statList,
                    String::valueOf);
            cellList.add(stringList);
        }

        return cellList;
    }
}
