package com.cardillsports.stattracker.stats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.teamselection.ui.TeamSelectionActivity;

import javax.inject.Inject;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class StatsActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);

        navView.setOnNavigationItemSelectedListener(item -> {
            NavController navController = Navigation.findNavController(
                    this,
                    R.id.my_nav_host_fragment);

            switch (item.getItemId()) {
                case R.id.nav_game:
                    navController.navigate(R.id.newGameFragment);
                    return true;
                case R.id.nav_scores:
                    navController.navigate(R.id.scoresFragment);
                    return true;
                case R.id.nav_stats:
                    navController.navigate(R.id.statsFragment);
                    return true;
                default:
                    return false;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return Navigation.findNavController(this, R.id.scoresFragment).navigateUp();
    }
}
