package com.cardillsports.stattracker.stats;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.game.businesslogic.NewGamePlayerAdapter;
import com.cardillsports.stattracker.scores.model.GameDay;
import com.cardillsports.stattracker.scores.model.GameDays;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by vithushan on 9/12/18.
 */

public class GameDaysAdapter extends RecyclerView.Adapter<GameDaysAdapter.GameDaysViewHolder> {
    private GameDay[] gameDays;
    private PublishSubject<ScoreEvent.DateSelected> mPublishSubject;

    public GameDaysAdapter(GameDay[] gameDays) {
        this.gameDays = gameDays;
        mPublishSubject = PublishSubject.create();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.game_day_item;
    }

    @NonNull
    @Override
    public GameDaysViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new GameDaysViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameDaysViewHolder holder, int position) {
        GameDay gameDay = gameDays[position];
        holder.getTextView().setText(gameDay.getGameDate());
        RxView.clicks(holder.getTextView())
                .subscribe(x -> mPublishSubject.onNext(new ScoreEvent.DateSelected(gameDay)));
    }

    public Observable<ScoreEvent.DateSelected> getEventObservable() {
        return mPublishSubject;
    }

    @Override
    public int getItemCount() {
        return gameDays.length;
    }

    class GameDaysViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public GameDaysViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }

        public TextView getTextView() {
            return textView;
        }
    }
}
