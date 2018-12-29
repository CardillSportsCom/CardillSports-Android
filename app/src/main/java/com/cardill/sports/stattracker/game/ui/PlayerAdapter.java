package com.cardill.sports.stattracker.game.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.data.Player;
import com.cardill.sports.stattracker.game.businesslogic.NewGamePlayerAdapter;

import java.util.ArrayList;
import java.util.List;

class PlayerAdapter extends ArrayAdapter<Player> {


    public PlayerAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    public void setPlayers(List<Player> players) {
        clear();
        addAll(players);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Player player = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(player.firstName);

        // Return the completed view to render on screen

        return convertView;

    }
}
