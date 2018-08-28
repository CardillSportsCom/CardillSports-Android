package com.cardillsports.stattracker.businesslogic;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
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

public class GamePlayerAdapter extends RecyclerView.Adapter<GamePlayerAdapter.GamePlayerViewHolder> {
    private final List<Player> mPlayers;

    public GamePlayerAdapter(List<Player> players) {
        mPlayers = players;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.player_game_item;
    }

    @NonNull
    @Override
    public GamePlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new GamePlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GamePlayerViewHolder holder, int position) {
        holder.getNameTextView().setText(mPlayers.get(position).firstName());
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    public class GamePlayerViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNameTextView;
        private final RecyclerView mRecyclerView;

        public GamePlayerViewHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.name_text_view);
            mRecyclerView = itemView.findViewById(R.id.stats_recycler_view);

            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(new StatsAdapter());
        }

        public TextView getNameTextView() {
            return mNameTextView;
        }

        public RecyclerView getRecyclerView() {
            return mRecyclerView;
        }
    }
}
