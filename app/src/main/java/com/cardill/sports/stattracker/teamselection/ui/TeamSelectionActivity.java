package com.cardill.sports.stattracker.teamselection.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.data.CardillService;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.game.data.GameData;
import com.cardill.sports.stattracker.game.ui.GameActivity;
import com.cardill.sports.stattracker.teamselection.businesslogic.PlayerAdapter;
import com.cardill.sports.stattracker.teamselection.businesslogic.TeamSelectionPresenter;
import com.cardill.sports.stattracker.teamselection.data.AddPlayerRequestBody;
import com.cardill.sports.stattracker.teamselection.data.NewGamePlayer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class TeamSelectionActivity extends AppCompatActivity implements TeamSelectionViewBinder {

    public static final String GAME_DATA = "game-data-key";

    private TeamSelectionPresenter mPresenter;
    private RecyclerView mRecyclerView;
    private PlayerAdapter adapter;
    private View mProgress;

    @Inject CardillService cardillService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);

        mPresenter = new TeamSelectionPresenter(this, cardillService);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mProgress = findViewById(R.id.progress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.player_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_next) {
            mPresenter.onTeamsSelected();
            return true;
        }
        else if(item.getItemId() == R.id.add_player){
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


            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
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
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    public void loadPlayers(List<NewGamePlayer> players) {
        mProgress.setVisibility(View.GONE);
        adapter = new PlayerAdapter(players);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToGameScreen() {
        finish();

        List<Player> teamOnePlayers = adapter.getTeamOnePlayers();
        List<Player> teamTwoPlayers = adapter.getTeamTwoPlayers();

        Intent intent = new Intent(this, GameActivity.class);
        GameData gameData = new GameData(
                teamOnePlayers,
                teamTwoPlayers,
                true);

        intent.putExtra(GAME_DATA, (Parcelable) gameData);
        startActivity(intent);
    }

    @Override
    public void showLoading() {
        mProgress.setVisibility(View.VISIBLE);
    }

    public boolean isEmailValid( String email){
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
