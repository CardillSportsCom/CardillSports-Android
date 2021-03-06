package com.cardill.sports.stattracker.consumer.gamedays.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardill.sports.stattracker.consumer.R;
import com.cardill.sports.stattracker.consumer.common.data.ScoreEvent;
import com.cardill.sports.stattracker.consumer.common.data.Game;
import com.cardill.sports.stattracker.consumer.common.data.GameDay;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Adapter for showing a list of games
 */
public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameViewHolder> {
    private GameDay gameDay;
    private PublishSubject<ScoreEvent.GameSelected> mPublishSubject;

    public GamesAdapter(GameDay gameDay) {
        this.gameDay = gameDay;
        mPublishSubject = PublishSubject.create();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.game_day_item;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {

        Game game = gameDay.getGames()[position];
        String score = game.getTeamAScore() + " - " + game.getTeamBScore();

        String text = "Game " + (position + 1) + ": " + score;

        holder.getTextView().setText(text);

        RxView.clicks(holder.getTextView())
                .subscribe(x -> mPublishSubject.onNext(new ScoreEvent.GameSelected(game)));
    }

    public Observable<ScoreEvent.GameSelected> getEventObservable() {
        return mPublishSubject;
    }

    @Override
    public int getItemCount() {
        return gameDay.getGames().length;
    }

    class GameViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public GameViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
