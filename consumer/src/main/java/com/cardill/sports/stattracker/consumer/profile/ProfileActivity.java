package com.cardill.sports.stattracker.consumer.profile;

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

import com.cardill.sports.stattracker.consumer.R;
import com.cardill.sports.stattracker.common.data.User;
import com.cardill.sports.stattracker.consumer.common.data.ConsumerTableUtils;
import com.cardill.sports.stattracker.consumer.network.CardillService;
import com.cardill.sports.stattracker.consumer.profile.businesslogic.ProfilePresenter;
import com.cardill.sports.stattracker.consumer.profile.businesslogic.ProfileViewBinder;
import com.cardill.sports.stattracker.consumer.profile.data.PlayerStatResponse;
import com.evrencoskun.tableview.TableView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import timber.log.Timber;

import static com.cardill.sports.stattracker.consumer.common.businesslogic.SortableCardillTableListener.PLAYER_ID_KEY;

public class ProfileActivity extends AppCompatActivity implements ProfileViewBinder {

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

        final Toolbar toolbar = findViewById(R.id.htab_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        toolbar.setNavigationOnClickListener(v -> finish());

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.htab_collapse_toolbar);

        collapsingToolbarLayout.setContentScrimColor(
                ContextCompat.getColor(this, R.color.black)
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
        if (item.getItemId() == R.id.change_picture) {
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

        ConsumerTableUtils.initProfileTable(this, tableView, playerStatResponse.getPlayerStats());
    }
}

