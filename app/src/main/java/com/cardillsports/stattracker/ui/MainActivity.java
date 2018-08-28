package com.cardillsports.stattracker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.ArrayAdapter;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.businesslogic.CardillPresenter;
import com.cardillsports.stattracker.businesslogic.PlayerAdapter;
import com.cardillsports.stattracker.data.Player;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CardillViewBinder {

    private CardillPresenter mPresenter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new CardillPresenter(this);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        PlayerItemTouchHelperCallback callback = new PlayerItemTouchHelperCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);

        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        getSupportActionBar().setTitle("Select Team 1");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void loadPlayers(List<Player> players) {
        mRecyclerView.setAdapter(new PlayerAdapter(players));
    }
}
