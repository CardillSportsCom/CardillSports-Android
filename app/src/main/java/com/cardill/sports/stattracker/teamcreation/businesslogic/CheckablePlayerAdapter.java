package com.cardill.sports.stattracker.teamcreation.businesslogic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.cardill.sports.stattracker.common.data.Player;

import java.util.List;

public class CheckablePlayerAdapter extends ArrayAdapter<Player> {

    public CheckablePlayerAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_multiple_choice);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Player player = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_multiple_choice, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(player.firstName());

        return convertView;
    }

    public void setPlayers(List<Player> players) {
        clear();
        addAll(players);
        notifyDataSetChanged();
    }
}
