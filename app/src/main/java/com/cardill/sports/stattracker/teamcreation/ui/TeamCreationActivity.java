package com.cardill.sports.stattracker.teamcreation.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.league.LeagueRepository;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.teamcreation.businesslogic.TeamCreationPresenter;
import com.cardill.sports.stattracker.teamcreation.businesslogic.TeamCreationViewBinder;
import com.cardill.sports.stattracker.teamcreation.businesslogic.TeamCreationViewModel;
import com.cardill.sports.stattracker.teamselection.data.AddPlayerRequestBody;
import com.cardill.sports.stattracker.teamselection.ui.CheckablePlayerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class TeamCreationActivity extends AppCompatActivity implements TeamCreationViewBinder {

    public static final String GAME_DATA = "game-data-key";

    private TeamCreationPresenter mPresenter;
    private View mProgress;
    private CheckablePlayerAdapter mCheckablePlayerAdapter;

    @Inject
    CardillService cardillService;

    @Inject
    LeagueRepository leagueRepository;

    private TeamCreationViewModel teamCreationViewModel;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_creation);

        teamCreationViewModel = ViewModelProviders.of(this).get(TeamCreationViewModel.class);
        teamCreationViewModel.getPlayers().observe(this, this::renderUI);
        teamCreationViewModel.isLoading().observe(this, this::renderLoading);

        mListView = findViewById(R.id.list_view);

        mCheckablePlayerAdapter = new CheckablePlayerAdapter(this);
        mListView.setAdapter(mCheckablePlayerAdapter);

        mProgress = findViewById(R.id.progress);

        mPresenter = new TeamCreationPresenter(teamCreationViewModel, cardillService, this, leagueRepository);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.loadPlayers();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.player_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_next) {
            mPresenter.onTeamSelected(getSelectedPlayers());
            return true;
        } else if (item.getItemId() == R.id.add_player) {
            handleAddPlayer();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Player> getSelectedPlayers() {
        List<Player> players = new ArrayList<>();
        SparseBooleanArray sparseBooleanArray = mListView.getCheckedItemPositions();
        for (int i = 0; i < mListView.getCount(); i++) {
            if (sparseBooleanArray.get(i)) {
                Player player = (Player) mListView.getItemAtPosition(i);
                players.add(player);
            }
        }
        return players;
    }

    private void renderLoading(Boolean isLoading) {
        if (isLoading) {
            mProgress.setVisibility(View.VISIBLE);
        } else {
            mProgress.setVisibility(View.GONE);
        }
    }

    private void renderUI(List<Player> players) {
        mCheckablePlayerAdapter.setPlayers(players);
    }

    private void handleAddPlayer() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Player");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText firstName = new EditText(this);
        firstName.setHint("First name");
        firstName.setInputType(InputType.TYPE_CLASS_TEXT );
        layout.addView(firstName);
        final EditText lastName = new EditText(this);
        lastName.setHint("Last name");
        lastName.setInputType(InputType.TYPE_CLASS_TEXT );
        layout.addView(lastName);
        final EditText email = new EditText(this);
        email.setHint("Email");
        email.setInputType(InputType.TYPE_CLASS_TEXT );
        layout.addView(email);
        builder.setView(layout);
        builder.setPositiveButton("OK", (dialog, which) -> {
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String firstNameText = firstName.getText().toString();
            String lastNameText = lastName.getText().toString();
            String emailText = email.getText().toString();
            if(firstNameText.isEmpty() || lastNameText.isEmpty() || emailText.isEmpty()){
                String message = "All fields are required";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
            else if(!isEmailValid(emailText)){
                String message = "Email address is invalid";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
            else {
                AddPlayerRequestBody addPlayerRequestBody = new AddPlayerRequestBody(firstNameText, lastNameText, emailText, "password");
                mPresenter.addPlayer(addPlayerRequestBody);
                dialog.dismiss();
            }
        });
    }

    private boolean isEmailValid( String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    @Override
    public void askToCreateAnotherTeam() {
        teamCreationViewModel.isLoading().setValue(false);
        mListView.clearChoices();

        new AlertDialog.Builder(this)
                .setTitle("Make another team?")
                .setMessage("Do you want to create another team?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.yes_create_another, (dialog, whichButton) -> {
                })
                .setNegativeButton(R.string.no_im_finished, (dialog, whichButton) -> this.finish()).show();
    }
}
