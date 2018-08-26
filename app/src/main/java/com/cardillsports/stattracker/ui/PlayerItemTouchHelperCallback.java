package com.cardillsports.stattracker.ui;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.cardillsports.stattracker.businesslogic.PlayerAdapter;

public class PlayerItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {
    private static final String TAG = "Vithushan";

    public PlayerItemTouchHelperCallback(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (viewHolder instanceof PlayerAdapter.PlayerViewHolder) {
            PlayerAdapter.PlayerViewHolder playerViewHolder = (PlayerAdapter.PlayerViewHolder) viewHolder;
            String name = (String) playerViewHolder.getNameTextView().getText();

            if (direction == ItemTouchHelper.LEFT || direction == ItemTouchHelper.START) {
                Log.d(TAG, name + " : team 1");
            } else {
                Log.d(TAG, name + " : team 2");
            }
        }
    }
}
