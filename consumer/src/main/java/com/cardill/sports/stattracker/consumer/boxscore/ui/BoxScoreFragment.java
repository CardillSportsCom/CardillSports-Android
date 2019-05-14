package com.cardill.sports.stattracker.consumer.boxscore.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardill.sports.stattracker.consumer.common.data.ConsumerGameData;
import com.cardill.sports.stattracker.consumer.common.data.ConsumerPlayer;
import com.cardill.sports.stattracker.consumer.R;
import com.cardill.sports.stattracker.consumer.boxscore.businesslogic.BoxScorePresenter;
import com.cardill.sports.stattracker.common.ui.BaseFragment;
import com.cardill.sports.stattracker.consumer.common.data.ConsumerTableUtils;
import com.cardill.sports.stattracker.consumer.network.CardillService;
import com.evrencoskun.tableview.TableView;

import javax.inject.Inject;

import androidx.navigation.Navigation;

import static com.cardill.sports.stattracker.consumer.gamedays.ui.GameDayFragment.GAME_ID_KEY;

public class BoxScoreFragment extends BaseFragment implements BoxScoreViewBinder {

    private BoxScorePresenter mPresenter;
    private String gameId;
    @Inject
    CardillService cardillService;
    private TableView tableView;
    private View progress;
    private TextView score;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_box_score, container, false);

        gameId = (String) getArguments().get(GAME_ID_KEY);

        mPresenter = new BoxScorePresenter(this, cardillService);

        tableView = view.findViewById(R.id.team_1_table_view);

        progress = view.findViewById(R.id.progress);

        score = view.findViewById(R.id.score_textview);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadBoxScore(gameId);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.boxscore_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_delete_game) {
            mPresenter.deleteGameRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showBoxScore(ConsumerGameData gameData) {
        progress.setVisibility(View.GONE);

        int team1 = 0;
        for (ConsumerPlayer player : gameData.getTeamOnePlayers()) {
            team1 += player.getThreePointersMade() * 2;
            team1 += player.fieldGoalMade() - player.getThreePointersMade();
        }

        int team2 = 0;
        for (ConsumerPlayer player : gameData.getTeamTwoPlayers()) {
            team2 += player.getThreePointersMade();
            team2 += player.fieldGoalMade() - player.getThreePointersMade();
        }

        String scoreText = team1 + " - " + team2;

        score.setText(scoreText);


        ConsumerTableUtils.initBoxScoreTable(getActivity(), tableView, gameData.getTeamOnePlayers(), gameData.getTeamTwoPlayers());


    }

    @Override
    public void showDeleteGameConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.ok, (dialog, id) -> {
            // User clicked OK button
            mPresenter.deleteGameConfirmed(gameId);
        });
        builder.setNegativeButton(R.string.cancel, (dialog, id) -> {
            // User cancelled the dialog
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void navigateToScores() {
        Navigation.findNavController(tableView).navigate(R.id.scoresFragment);
    }
}