package com.cardill.sports.stattracker.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.CardillTableListener;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.common.ui.BaseFragment;
import com.cardill.sports.stattracker.details.businesslogic.StatsTableAdapter;
import com.cardill.sports.stattracker.game.data.GameData;
import com.cardill.sports.stattracker.game.data.Stat;
import com.cardill.sports.stattracker.game.data.StatType;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.stats.businesslogic.StatsPresenter;
import com.cardill.sports.stattracker.stats.businesslogic.StatsViewBinder;
import com.cardill.sports.stattracker.teamselection.data.NewGamePlayer;
import com.evrencoskun.tableview.TableView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import static com.cardill.sports.stattracker.common.CardillTableListener.PLAYER_NAME_KEY;
import static com.cardill.sports.stattracker.details.businesslogic.StatsTableAdapter.NON_EDITABLE;

/**
 * Shows a player profile
 */
public class ProfileFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView nameTextView = view.findViewById(R.id.name);
        String name = getArguments().getString(PLAYER_NAME_KEY);
        nameTextView.setText(String.format("%s's Profile", name));

        return view;
    }



}
