package com.cardillsports.stattracker.businesslogic;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.data.GameRepository;
import com.cardillsports.stattracker.data.Player;
import com.cardillsports.stattracker.data.Stat;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class GamePlayerAdapter extends RecyclerView.Adapter<GamePlayerAdapter.GamePlayerViewHolder> {
    private final List<Player> mPlayers;
    private final RecyclerView.RecycledViewPool viewPool;
    private final GameRepository gameRepository;

    public GamePlayerAdapter(List<Player> players, GameRepository gameRepository) {
        mPlayers = players;
        this.gameRepository = gameRepository;
        viewPool = new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.player_game_item;
    }

    @NonNull
    @Override
    public GamePlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        GamePlayerViewHolder gamePlayerViewHolder = new GamePlayerViewHolder(view);
        gamePlayerViewHolder.getRecyclerView().setRecycledViewPool(viewPool);
        return gamePlayerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GamePlayerViewHolder holder, int position) {
        holder.getNameTextView().setText(mPlayers.get(position).firstName());
        StatsAdapter adapter = (StatsAdapter) holder.getRecyclerView().getAdapter();
        Disposable subscribe = adapter.getPublishSubject()
                .subscribe(stat -> gameRepository.updateStats(
                        mPlayers.get(position).id(),
                        stat.getStatType(),
                        stat.getCount()));
        holder.setDisposable(subscribe);
    }

    @Override
    public void onViewRecycled(@NonNull GamePlayerViewHolder holder) {
        super.onViewRecycled(holder);

        Disposable disposable = holder.getDisposable();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    public class GamePlayerViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNameTextView;
        private final RecyclerView mRecyclerView;
        private Disposable disposable;

        public GamePlayerViewHolder(View itemView) {
            super(itemView);

            disposable = new CompositeDisposable();

            mNameTextView = itemView.findViewById(R.id.name_text_view);
            mRecyclerView = itemView.findViewById(R.id.stats_recycler_view);

            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setLayoutManager(layoutManager);
            StatsAdapter adapter = new StatsAdapter();
            mRecyclerView.setAdapter(adapter);

        }

        public TextView getNameTextView() {
            return mNameTextView;
        }

        public RecyclerView getRecyclerView() {
            return mRecyclerView;
        }

        public Disposable getDisposable() {
            return disposable;
        }

        public void setDisposable(Disposable disposable) {
            this.disposable = disposable;
        }
    }
}
