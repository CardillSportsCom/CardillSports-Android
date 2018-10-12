package com.cardill.sports.stattracker.common;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cardill.sports.stattracker.details.businesslogic.StatsTableAdapter;
import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.listener.ITableViewListener;
import com.evrencoskun.tableview.sort.SortState;

public class CardillTableListener implements ITableViewListener {

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
        SortState sortingStatus = tableView.getSortingStatus(column);
        if (sortingStatus != SortState.DESCENDING) {
            tableView.sortColumn(column, SortState.DESCENDING);
        } else {
            tableView.sortColumn(column, SortState.ASCENDING);
        }
        tableView.scrollToRowPosition(0);
    }

    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {

    }

    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
        StatsTableAdapter.MyRowHeaderViewHolder holder = (StatsTableAdapter.MyRowHeaderViewHolder) rowHeaderView;
        Toast.makeText(tableView.getContext(), holder.getPlayer().getPlayer().firstName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {

    }
}
