package com.cardill.sports.stattracker.common.league;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardill.sports.stattracker.common.R;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

public class LeagueAdapter extends RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder> {
    private List<League> mData;
    private PublishSubject<League> publishSubject;

    public LeagueAdapter(List<League> mData) {
        this.mData = mData;
        publishSubject = PublishSubject.create();
    }

    public PublishSubject<League> getPublishSubject() {
        return publishSubject;
    }

    @NonNull
    @Override
    public LeagueViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        TextView leageItem = (TextView) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.league_item, viewGroup, false);
        return new LeagueViewHolder(leageItem);
    }

    @Override
    public void onBindViewHolder(@NonNull LeagueViewHolder leagueViewHolder, final int i) {
        String name = mData.get(i).getName();
        leagueViewHolder.setText(name);
        leagueViewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishSubject.onNext(mData.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setLeagues(List<League> leagueList) {
        mData = leagueList;
        notifyDataSetChanged();
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
