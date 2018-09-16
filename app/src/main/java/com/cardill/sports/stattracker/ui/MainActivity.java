package com.cardill.sports.stattracker.ui;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.cardill.sports.stattracker.R;

import javax.inject.Inject;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
