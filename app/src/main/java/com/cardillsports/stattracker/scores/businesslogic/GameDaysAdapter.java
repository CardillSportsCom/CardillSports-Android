package com.cardillsports.stattracker.scores.businesslogic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.scores.model.GameDay;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Adapter for showing a list of game days.
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
        Disposable disposable = RxView.clicks(holder.getTextView())
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
