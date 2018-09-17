package com.cardill.sports.stattracker.game.businesslogic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.data.Player;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * List of players shown in-game for stat recording.
 */
public class NewGamePlayerAdapter extends RecyclerView.Adapter<NewGamePlayerAdapter.PlayerViewHolder> {
    private final List<Player> mPlayers;
    private PublishSubject<GameEvent.PlayerSelected> mPublishSubject;
    private Team team;
    private Player lastSelectedPlayer;

    public NewGamePlayerAdapter(List<Player> players, Team team) {
        mPlayers = players;
        this.team = team;
        mPublishSubject = PublishSubject.create();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.new_game_player_item;
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
        Player player = mPlayers.get(position);
        if (player.equals(lastSelectedPlayer)) {
            holder.mNameTextView.setAlpha(0.5f);
            holder.mNameTextView.setEnabled(false);
        } else {
            holder.mNameTextView.setAlpha(1.0f);
            holder.mNameTextView.setEnabled(true);
        }
        holder.getNameTextView().setText(player.firstName());
        Disposable subscribe = RxView.clicks(holder.getNameTextView())
                .subscribe(x -> mPublishSubject.onNext(new GameEvent.PlayerSelected(team, player)));
    }

    public Observable<GameEvent.PlayerSelected> getPlayerSelectedEvents() {
        return mPublishSubject;
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    public void setLastSelectedPlayer(Player lastSelectedPlayer) {

        this.lastSelectedPlayer = lastSelectedPlayer;
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private final Button mNameTextView;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.name_text_view);
        }

        public Button getNameTextView() {
            return mNameTextView;
        }
    }
}
