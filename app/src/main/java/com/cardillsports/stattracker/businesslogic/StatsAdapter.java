package com.cardillsports.stattracker.businesslogic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.data.GameRepository;
import com.cardillsports.stattracker.data.Stat;
import com.cardillsports.stattracker.data.StatType;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.StatsButtonViewHolder> {

    private PublishSubject<Stat> publishSubject;

    public StatsAdapter() {
        publishSubject = PublishSubject.create();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.stat_item;
    }


    @NonNull
    @Override
    public StatsButtonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);

        return new StatsButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatsButtonViewHolder holder, int position) {
        StatType statType = StatType.values()[position];

        holder.getTextView().setText(statType.name());

        holder.getStatButton().setOnValueChangeListener(
                (view, oldValue, newValue) ->
                        publishSubject.onNext(new Stat(statType, newValue)));
    }

    @Override
    public int getItemCount() {
        return StatType.values().length;
    }

    public Observable<Stat> getPublishSubject() {
        return publishSubject;
    }

    public class StatsButtonViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextView;
        private final ElegantNumberButton mStatButton;

        public StatsButtonViewHolder(View view) {
            super(view);

            mTextView = view.findViewById(R.id.stat_label);
            mStatButton = view.findViewById(R.id.stat_button);
        }

        public TextView getTextView() {
            return mTextView;
        }

        public ElegantNumberButton getStatButton() {
            return mStatButton;
        }
    }
}
