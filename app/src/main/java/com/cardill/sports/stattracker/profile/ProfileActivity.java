package com.cardill.sports.stattracker.profile;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.businesslogic.CardillTableListener;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.profile.businesslogic.PlayerStatsTableAdapter;
import com.cardill.sports.stattracker.profile.businesslogic.ProfilePresenter;
import com.cardill.sports.stattracker.profile.businesslogic.ProfileViewBinder;
import com.cardill.sports.stattracker.profile.data.PlayerStat;
import com.cardill.sports.stattracker.profile.data.PlayerStatResponse;
import com.cardill.sports.stattracker.profile.data.PlayerStatType;
import com.cardill.sports.stattracker.common.data.User;
import com.evrencoskun.tableview.TableView;
import com.google.common.collect.Lists;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
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

import static com.cardill.sports.stattracker.common.businesslogic.SortableCardillTableListener.PLAYER_ID_KEY;

public class ProfileActivity extends AppCompatActivity implements ProfileViewBinder {
    public static final String SOURCE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static final String TAG = ProfileActivity.class.getSimpleName();
    private ProfilePresenter mPresenter;

    @Inject
    CardillService cardillService;

    private TableView tableView;
    private View progress;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

        final Toolbar toolbar = findViewById(R.id.htab_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.htab_collapse_toolbar);

        collapsingToolbarLayout.setContentScrimColor(
                ContextCompat.getColor(this, R.color.colorPrimary)
        );
        collapsingToolbarLayout.setStatusBarScrimColor(
                ContextCompat.getColor(this, R.color.colorPrimaryDark)
        );

        tableView = findViewById(R.id.table_view);
        progress = findViewById(R.id.progress);
        image = findViewById(R.id.htab_header);
        mPresenter = new ProfilePresenter(this, cardillService);
    }

    @Override
    public void onResume() {
        super.onResume();
        String playerId = getIntent().getExtras().getString(PLAYER_ID_KEY);
        mPresenter.onLoad(playerId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_picture:
                Toast.makeText(this, "HE", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProfile(PlayerStatResponse playerStatResponse) {
        progress.setVisibility(View.GONE);
        User player = playerStatResponse.getPlayerStats()[0].getPlayer();
        if (getSupportActionBar() != null)
            getSupportActionBar()
                    .setTitle(String.format("%s %s",
                            player.getFirstName(),
                            player.getLastName()));


        Picasso.Builder builder = new Picasso.Builder(this);
        builder.listener((picasso, uri, exception) -> Timber.e(exception));
        builder.build().load(player.getImageUri()).into(image);

        initTableView(tableView, playerStatResponse.getPlayerStats());
    }

    private void initTableView(TableView tableView, PlayerStat[] playerStats) {
        tableView.getCellRecyclerView().setMotionEventSplittingEnabled(true);
        PlayerStatsTableAdapter adapter = new PlayerStatsTableAdapter(this);
        SimpleDateFormat sdf = new SimpleDateFormat(
                SOURCE_PATTERN, Locale.US);
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
        NumberFormat percentInstance = NumberFormat.getPercentInstance();

        for (PlayerStat playerStat : playerStats) {
            List<String> statList = new ArrayList<>(8);
            statList.add(String.valueOf(playerStat.getFGM()));
            statList.add(String.valueOf(playerStat.getFGA()));

            double fg = playerStat.getFGM() / (double) playerStat.getFGA();
            statList.add(percentInstance.format(fg));

            statList.add(String.valueOf(playerStat.getAssists()));
            statList.add(String.valueOf(playerStat.getRebounds()));
            statList.add(String.valueOf(playerStat.getSteals()));
            statList.add(String.valueOf(playerStat.getBlocks()));
            statList.add(String.valueOf(playerStat.getTurnovers()));

            List<String> stringList = Lists.transform(
                    statList,
                    String::valueOf);
            cellList.add(stringList);
        }

        return cellList;
    }
}

