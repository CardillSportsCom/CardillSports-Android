package com.cardill.sports.stattracker.consumer.scores;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardill.sports.stattracker.consumer.R;
import com.cardill.sports.stattracker.consumer.common.data.ScoreEvent;
import com.cardill.sports.stattracker.consumer.common.data.GameDay;
import com.jakewharton.rxbinding2.view.RxView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

/**
 * Adapter for showing a list of game days.
 */
public class GameDaysAdapter extends RecyclerView.Adapter<GameDaysAdapter.GameDaysViewHolder> {
    public static final String SOURCE_PATTERN = "MM/d/yyyy";
    public static final String TARGET_PATTERN = "MMM d, yyy";

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
        SimpleDateFormat simpleDateFormat = holder.getSimpleDateFormat();

        try {
            Timber.d(gameDay.getGameDate());
            Date d = simpleDateFormat.parse(gameDay.getGameDate());
            simpleDateFormat.applyPattern(TARGET_PATTERN);
            String newDateString = simpleDateFormat.format(d);
            holder.getTextView().setText(newDateString);
        } catch (ParseException e) {
            holder.getTextView().setText(gameDay.getGameDate());
        }

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
        private final SimpleDateFormat simpleDateFormat;

        private TextView textView;

        public GameDaysViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
            simpleDateFormat = new SimpleDateFormat(SOURCE_PATTERN);

        }

        public TextView getTextView() {
            return textView;
        }

        public SimpleDateFormat getSimpleDateFormat() {
            return simpleDateFormat;
        }
    }
}
