package com.cardillsports.stattracker.businesslogic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.data.Player;

import java.util.ArrayList;
import java.util.List;

public class GamePlayerAdapter extends RecyclerView.Adapter<GamePlayerAdapter.PlayerViewHolder> {
    private final List<Player> mPlayers;
    private List<Player> mTeamOne;
    private List<Player> mTeamTwo;

    public GamePlayerAdapter(List<Player> players) {
        mPlayers = players;
        mTeamOne = new ArrayList<>();
        mTeamTwo = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.player_game_item;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        holder.getNameTextView().setText(mPlayers.get(position).firstName());
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNameTextView;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.name_text_view);
        }

        public TextView getNameTextView() {
            return mNameTextView;
        }

    }
}
