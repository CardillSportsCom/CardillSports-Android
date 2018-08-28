package com.cardillsports.stattracker.businesslogic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.data.Player;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;
import java.util.List;

public class StatsAdapter extends RecyclerView.Adapter<StatsAdapter.StatsButtonViewHolder> {
    private List<String> mItems;

    public StatsAdapter() {
        mItems = new ArrayList<>();
        mItems.add("2PT MADE");
        mItems.add("3PT MADE");
        mItems.add("FG Missed");

        mItems.add("Rebounds");
        mItems.add("Blocks");
        mItems.add("Steals");
        mItems.add("Turnvovers");
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
        holder.getTextView().setText(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
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
