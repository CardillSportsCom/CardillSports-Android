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

import static com.cardill.sports.stattracker.common.SortableCardillTableListener.PLAYER_NAME_KEY;

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
