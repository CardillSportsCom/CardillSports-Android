package com.cardill.sports.stattracker.details.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.common.businesslogic.PlayerStatsTableAdapter;
import com.cardill.sports.stattracker.common.businesslogic.StatsTableAdapter;
import com.cardill.sports.stattracker.common.data.Player;
import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.listener.ITableViewListener;

import androidx.navigation.Navigation;

class DetailsTableListener implements ITableViewListener {
    private TableView tableView;
    private DetailsViewBinder viewBinder;

    public DetailsTableListener(TableView tableView, DetailsViewBinder viewBinder) {
        this.tableView = tableView;
        this.viewBinder = viewBinder;
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
            StatsTableAdapter.MyRowHeaderViewHolder holder =
                    (StatsTableAdapter.MyRowHeaderViewHolder) rowHeaderView;
            Player player = holder.getPlayer().getPlayer();
            viewBinder.showPlayerList(player);
        }
    }

    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {

    }
}
