package com.cardill.sports.stattracker.profile;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.CardillTableListener;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.profile.businesslogic.PlayerStatsTableAdapter;
import com.cardill.sports.stattracker.profile.businesslogic.ProfilePresenter;
import com.cardill.sports.stattracker.profile.businesslogic.ProfileViewBinder;
import com.cardill.sports.stattracker.profile.data.PlayerStatType;
import com.cardill.sports.stattracker.stats.data.PlayerStat;
import com.cardill.sports.stattracker.stats.data.PlayerStatResponse;
import com.evrencoskun.tableview.TableView;
import com.google.common.collect.Lists;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

import static com.cardill.sports.stattracker.common.SortableCardillTableListener.PLAYER_ID_KEY;

public class ProfileActivity extends AppCompatActivity implements ProfileViewBinder {

    private static final String TAG = ProfileActivity.class.getSimpleName();
    private ProfilePresenter mPresenter;

    @Inject CardillService cardillService;

    private TableView tableView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.htab_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Parallax Tabs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);

        collapsingToolbarLayout.setContentScrimColor(
                ContextCompat.getColor(this, R.color.colorPrimary)
        );
        collapsingToolbarLayout.setStatusBarScrimColor(
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        tableView = findViewById(R.id.table_view);

        mPresenter = new ProfilePresenter(this, cardillService);
    }

    @Override
    public void onResume() {
        super.onResume();
        String playerId = getIntent().getExtras().getString(PLAYER_ID_KEY);
        mPresenter.onLoad(playerId);
    }

    @Override
    public void showProfile(PlayerStatResponse playerStatResponse) {
        initTableView(tableView, playerStatResponse.getPlayerStats());
    }

    private void initTableView(TableView tableView, PlayerStat[] playerStats) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        PlayerStatsTableAdapter adapter = new PlayerStatsTableAdapter(this);
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

