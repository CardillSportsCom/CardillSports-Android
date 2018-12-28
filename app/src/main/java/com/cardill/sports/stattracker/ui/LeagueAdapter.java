package com.cardill.sports.stattracker.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardill.sports.stattracker.ApplicationModule_ProvideGameDaoFactory;
import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.league.League;

import java.util.List;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder> {
    private List<League> mData;

    public LeagueAdapter(List<League> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public LeagueViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TextView leageItem = (TextView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.league_item, viewGroup, false);
        return new LeagueViewHolder(leageItem);
    }

    @Override
    public void onBindViewHolder(@NonNull LeagueViewHolder leagueViewHolder, int i) {
        String name = mData.get(i).getName();
        leagueViewHolder.setText(name);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class LeagueViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTextView;

        public LeagueViewHolder(@NonNull TextView textView) {
            super(textView);
            mTextView = textView;
        }

        public void setText(String text) {
            mTextView.setText(text);
        }


    }
}
