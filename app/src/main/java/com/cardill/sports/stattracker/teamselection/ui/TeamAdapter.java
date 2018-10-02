package com.cardill.sports.stattracker.teamselection.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.scores.businesslogic.GamesAdapter;
import com.cardill.sports.stattracker.stats.data.Player;
import com.cardill.sports.stattracker.teamselection.data.Team;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> {

    private final PublishSubject<TeamEvent> mPublishSubject;
    private List<Team> mTeams;

    public TeamAdapter() {
        mTeams = new ArrayList<>();
        mPublishSubject = PublishSubject.create();
    }

    public void setTeams(List<Team> teams) {
        mTeams.clear();
        mTeams.addAll(teams);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.team_item;
    }

    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewHolder holder, int position) {
        Team team = mTeams.get(position);

        StringBuilder builder = new StringBuilder();

        for (Player player : team.getPlayers()) {
            builder.append(player.getFirstName());
            builder.append(", ");
        }

        String teamText = "";

        try {
            teamText = builder.toString().substring(0, builder.length() - 2);
        } catch (StringIndexOutOfBoundsException e) {
            Timber.tag("VITHUSHAN").e(e.getMessage());
            teamText = "INVALID STRING";
        }

        holder.getTextView().setText(teamText);
        holder.getTextView().setOnClickListener(x -> mPublishSubject.onNext(new TeamEvent(team.getPlayers())));
    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    public Observable<TeamEvent> getEventObservable() {
        return mPublishSubject;
    }

    class TeamViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public TeamViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
