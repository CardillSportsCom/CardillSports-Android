package com.cardill.sports.stattracker.scores.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cardill.sports.stattracker.R;

import androidx.navigation.fragment.NavHostFragment;

public class NewGameFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_game, container, false);
        Button newGameButton = view.findViewById(R.id.button_new_game);
        newGameButton.setOnClickListener(
                x -> NavHostFragment.findNavController(this)
                        .navigate(R.id.action_newGameFragment_to_teamSelectionActivity));

        Button newTeamButton = view.findViewById(R.id.button_new_team);
        newTeamButton.setOnClickListener(
                x -> NavHostFragment.findNavController(this)
                        .navigate(R.id.action_newGameFragment_to_teamCreationActivity));

        return view;
    }
}