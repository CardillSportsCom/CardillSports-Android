package com.cardill.sports.stattracker.consumer.common.businesslogic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.cardill.sports.stattracker.common.businesslogic.StatsTableAdapter;
import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.listener.ITableViewListener;

import static com.cardill.sports.stattracker.common.businesslogic.SortableCardillTableListener.PLAYER_ID_KEY;

//import androidx.navigation.Navigation;

public class CardillTableListener implements ITableViewListener {

    public static final String PLAYER_NAME_KEY = "player-name-key";

    TableView tableView; //Be careful of the listener and table holding references to each other

    public CardillTableListener(TableView tableView) {
        this.tableView = tableView;
    }

    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {

    }

    @Override
    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {

    }

    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {

    }

    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {

    }

    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
        if (rowHeaderView instanceof StatsTableAdapter.MyRowHeaderViewHolder) {
            StatsTableAdapter.MyRowHeaderViewHolder holder = (StatsTableAdapter.MyRowHeaderViewHolder) rowHeaderView;
            Bundle params = new Bundle();
            params.putString(PLAYER_ID_KEY, holder.getPlayer().getPlayer().id());
            //TODO nav to profile after consumer module
            //Navigation.findNavController(tableView).navigate(R.id.profileActivity, params);
        }
    }

    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {

    }
}
