package com.cardillsports.stattracker.game.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.details.businesslogic.StatsTableAdapter;
import com.cardillsports.stattracker.game.businesslogic.GamePlayerAdapter;
import com.cardillsports.stattracker.game.data.GameData;
import com.cardillsports.stattracker.game.data.GameRepository;
import com.cardillsports.stattracker.game.data.Stat;
import com.cardillsports.stattracker.game.data.StatType;
import com.evrencoskun.tableview.TableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

import static java.security.AccessController.getContext;

public class DetailsActivity extends AppCompatActivity {

    @Inject GameRepository gameRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        GameData gameData = gameRepository.getGameStats();

        TableView tableView =findViewById(R.id.team_1_table_view);

        // Create our custom TableView Adapter
        StatsTableAdapter adapter = new StatsTableAdapter(this);

        // Set this adapter to the our TableView
        tableView.setAdapter(adapter);

        // Let's set datas of the TableView on the Adapter
        List<StatType> columnHeaderItems = Arrays.asList(StatType.values());

        List<List<Stat>> mCellList = new ArrayList<>();

        adapter.setAllItems(columnHeaderItems, gameData.teamOnePlayers(), mCellList);
    }
}

