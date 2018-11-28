package com.cardill.sports.stattracker.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.ui.BaseFragment;
import com.cardill.sports.stattracker.network.CardillService;
import com.cardill.sports.stattracker.profile.businesslogic.ProfilePresenter;
import com.cardill.sports.stattracker.profile.businesslogic.ProfileViewBinder;
import com.cardill.sports.stattracker.stats.data.PlayerStatResponse;

import javax.inject.Inject;

import static com.cardill.sports.stattracker.common.SortableCardillTableListener.PLAYER_ID_KEY;

/**
 * Shows a player profile
 */
public class ProfileFragment extends BaseFragment implements ProfileViewBinder {

    private TextView nameTextView;
    private ProfilePresenter mPresenter;

    @Inject
    CardillService cardillService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nameTextView = view.findViewById(R.id.name);
        mPresenter = new ProfilePresenter(this, cardillService);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        String playerId = getArguments().getString(PLAYER_ID_KEY);
        mPresenter.onLoad(playerId);
    }

    @Override
    public void showProfile(PlayerStatResponse playerStatResponse) {
        String playerName = playerStatResponse.getPlayerStats()[0].getPlayer();
        nameTextView.setText(playerName);
    }
}
