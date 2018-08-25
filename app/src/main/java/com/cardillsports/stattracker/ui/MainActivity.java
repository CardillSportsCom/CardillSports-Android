package com.cardillsports.stattracker.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.businesslogic.CardillPresenter;

public class MainActivity extends AppCompatActivity implements CardillViewBinder {

    private CardillPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new CardillPresenter(this);

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
}
