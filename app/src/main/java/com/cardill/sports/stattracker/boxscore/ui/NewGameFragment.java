package com.cardill.sports.stattracker.boxscore.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.ui.MainActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import androidx.navigation.fragment.NavHostFragment;

public class NewGameFragment extends Fragment {

    private View view;
    private AdView mAdView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_new_game, container, false);
        Button newGameButton = view.findViewById(R.id.button_new_game);
        newGameButton.setOnClickListener(
                x -> NavHostFragment.findNavController(this)
                        .navigate(R.id.action_newGameFragment_to_teamSelectionActivity));

        Button newTeamButton = view.findViewById(R.id.button_new_team);
        newTeamButton.setOnClickListener(
                x -> NavHostFragment.findNavController(this)
                        .navigate(R.id.action_newGameFragment_to_teamCreationActivity));

        mAdView = view.findViewById(R.id.adView);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        AdRequest adRequest = new AdRequest.Builder()
                .addKeyword("basketball")
                .build();
        mAdView.loadAd(adRequest);

    }
}