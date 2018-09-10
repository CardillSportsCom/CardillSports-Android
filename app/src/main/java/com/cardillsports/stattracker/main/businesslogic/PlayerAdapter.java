package com.cardillsports.stattracker.main.businesslogic;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cardillsports.stattracker.R;
import com.cardillsports.stattracker.common.data.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {
    private final List<Player> mPlayers;
    private List<Player> mTeamOne;
    private List<Player> mTeamTwo;

    public PlayerAdapter(List<Player> players) {
        mPlayers = players;
        mTeamOne = new ArrayList<>();
        mTeamTwo = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.player_item;
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
        holder.getNameTextView().setText(mPlayers.get(position).firstName());
        holder.getTeamOneCheckbox().setOnCheckedChangeListener((compoundButton, b) -> {
            if (b == true) {
                mTeamOne.add(mPlayers.get(position));
            } else {
                mTeamOne.remove(mPlayers.get(position));
            }
        });

        holder.getTeamTwoCheckbox().setOnCheckedChangeListener((compoundButton, b) -> {
            if (b == true) {
                mTeamTwo.add(mPlayers.get(position));
            } else {
                mTeamTwo.remove(mPlayers.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }

    public List<Player> getTeamOnePlayers() {
        return mTeamOne;
    }

    public List<Player> getTeamTwoPlayers() {
        return mTeamTwo;
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        private final TextView mNameTextView;
        private final CheckBox mTeamOneCheckbox;
        private final CheckBox mTeamTwoCheckbox;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.name_text_view);
            mTeamOneCheckbox = itemView.findViewById(R.id.team_1_checkbox);
            mTeamTwoCheckbox = itemView.findViewById(R.id.team_2_checkbox);

            //Make checkbox easier to click
            itemView.post( new Runnable() {
                // Post in the parent's message queue to make sure the parent
                // lays out its children before we call getHitRect()
                public void run() {
                    final Rect r = new Rect();
                    mTeamOneCheckbox.getHitRect(r);
                    mTeamTwoCheckbox.getHitRect(r);
                    r.top -= 4;
                    r.bottom += 4;
                    itemView.setTouchDelegate( new TouchDelegate( r , mTeamOneCheckbox));
                    itemView.setTouchDelegate( new TouchDelegate( r , mTeamTwoCheckbox));
                }
            });
        }

        public TextView getNameTextView() {
            return mNameTextView;
        }

        public CheckBox getTeamOneCheckbox() {
            return mTeamOneCheckbox;
        }

        public CheckBox getTeamTwoCheckbox() {
            return mTeamTwoCheckbox;
        }
    }
}
