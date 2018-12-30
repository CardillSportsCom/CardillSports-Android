package com.cardill.sports.stattracker.common.businesslogic;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cardill.sports.stattracker.R;
import com.cardill.sports.stattracker.details.businesslogic.DetailsChangedEvent;
import com.cardill.sports.stattracker.common.data.Stat;
import com.cardill.sports.stattracker.common.data.GameStatType;
import com.cardill.sports.stattracker.common.data.GamePlayer;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractSorterViewHolder;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class StatsTableAdapter extends AbstractTableAdapter<GameStatType, GamePlayer, Stat> {

    public static final int EDITABLE = 0;
    public static final int NON_EDITABLE = 1;
    private static final String TEAM_ONE_COLOR_STRING = "#50ffffff";
    private static final String TEAM_TWO_COLOR_STRING = "#50fbc02d";


    private final Context context;
    private PublishSubject<DetailsChangedEvent> mPublishSubject;
    private int viewType;


    public StatsTableAdapter(Context context, int viewType) {
        super(context);
        this.viewType = viewType;

        mPublishSubject = PublishSubject.create();
        this.context = context;
    }

    public Observable<DetailsChangedEvent> getChangeEvents() {
        return mPublishSubject;
    }

    /**
     * This is sample CellViewHolder class
     * This viewHolder must be extended from AbstractViewHolder class instead of RecyclerView.ViewHolder.
     */
    class MyCellViewHolder extends AbstractViewHolder {

        public final ElegantNumberButton numberButton;
        private final ViewGroup container;

        public MyCellViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            numberButton = itemView.findViewById(R.id.stat_button);
        }
    }

    /**
     * This is sample CellViewHolder class
     * This viewHolder must be extended from AbstractViewHolder class instead of RecyclerView.ViewHolder.
     */
    class BoxScoreCellViewHolder extends AbstractViewHolder {

        public final TextView textView;

        public BoxScoreCellViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.stat_button);
        }
    }

    /**
     * This is where you create your custom Cell ViewHolder. This method is called when Cell
     * RecyclerView of the TableView needs a new RecyclerView.ViewHolder of the given type to
     * represent an item.
     *
     * @param viewType : This value comes from #getCellItemViewType method to support different type
     *                 of viewHolder as a Cell item.
     * @see #getCellItemViewType(int);
     */
    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EDITABLE) {
            View layout = LayoutInflater.from(context).inflate(R.layout.editable_stat_item,
                    parent, false);
            return new MyCellViewHolder(layout);
        } else {
            View layout = LayoutInflater.from(context).inflate(R.layout.non_editable_stat_item,
                    parent, false);
            return new BoxScoreCellViewHolder(layout);
        }
    }

    /**
     * That is where you set Cell View Model data to your custom Cell ViewHolder. This method is
     * Called by Cell RecyclerView of the TableView to display the data at the specified position.
     * This method gives you everything you need about a cell item.
     *
     * @param holder         : This is one of your cell ViewHolders that was created on
     *                       ```onCreateCellViewHolder``` method. In this example we have created
     *                       "MyCellViewHolder" holder.
     * @param cellItemModel  : This is the cell view model located on this X and Y position. In this
     *                       example, the model class is "Cell".
     * @param columnPosition : This is the X (Column) position of the cell item.
     * @param rowPosition    : This is the Y (Row) position of the cell item.
     * @see #onCreateCellViewHolder(ViewGroup, int);
     */
    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int
            columnPosition, int rowPosition) {

        Stat cell = (Stat) cellItemModel;

        if (holder instanceof MyCellViewHolder) {

            // Get the holder to update cell item text
            MyCellViewHolder viewHolder = (MyCellViewHolder) holder;

            viewHolder.numberButton.setNumber(String.valueOf(cell.getValue()));

            viewHolder.numberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                @Override
                public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                    //TODO update gameRepo
                    mPublishSubject.onNext(new DetailsChangedEvent(columnPosition, rowPosition, newValue));
                }
            });

            if (cell.isTeamOne()) {
                viewHolder.container.setBackgroundColor(Color.parseColor(TEAM_ONE_COLOR_STRING));
            } else {
                viewHolder.container.setBackgroundColor(Color.parseColor(TEAM_TWO_COLOR_STRING));
            }

            // If your TableView should have auto resize for cells & columns.
            // Then you should consider the below lines. Otherwise, you can ignore them.

            // It is necessary to remeasure itself.
            viewHolder.itemView.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
            viewHolder.numberButton.requestLayout();
        } else if (holder instanceof BoxScoreCellViewHolder) {
            BoxScoreCellViewHolder viewHolder = (BoxScoreCellViewHolder) holder;
            viewHolder.textView.setText(String.valueOf(cell.getValue()));
            if (cell.isTeamOne()) {
                viewHolder.textView.setBackgroundColor(Color.parseColor(TEAM_ONE_COLOR_STRING));
            } else {
                viewHolder.textView.setBackgroundColor(Color.parseColor(TEAM_TWO_COLOR_STRING));
            }
        }
    }


    /**
     * This is sample CellViewHolder class.
     * This viewHolder must be extended from AbstractViewHolder class instead of RecyclerView.ViewHolder.
     */
    class MyColumnHeaderViewHolder extends AbstractSorterViewHolder {

        public final TextView cell_textview;
        public final ViewGroup container;

        public MyColumnHeaderViewHolder(View itemView) {
            super(itemView);
            cell_textview = (TextView) itemView.findViewById(R.id.stat_label);
            container = itemView.findViewById(R.id.container);
        }
    }

    /**
     * This is where you create your custom Column Header ViewHolder. This method is called when
     * Column Header RecyclerView of the TableView needs a new RecyclerView.ViewHolder of the given
     * type to represent an item.
     *
     * @param viewType : This value comes from "getColumnHeaderItemViewType" method to support
     *                 different type of viewHolder as a Column Header item.
     * @see #getColumnHeaderItemViewType(int);
     */
    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Column Header xml Layout
        View layout = LayoutInflater.from(context).inflate(R.layout
                .table_view_column_header_layout, parent, false);

        // Create a ColumnHeader ViewHolder
        return new MyColumnHeaderViewHolder(layout);
    }

    /**
     * That is where you set Column Header View Model data to your custom Column Header ViewHolder.
     * This method is Called by ColumnHeader RecyclerView of the TableView to display the data at
     * the specified position. This method gives you everything you need about a column header
     * item.
     *
     * @param holder                : This is one of your column header ViewHolders that was created on
     *                              ```onCreateColumnHeaderViewHolder``` method. In this example we have created
     *                              "MyColumnHeaderViewHolder" holder.
     * @param columnHeaderItemModel : This is the column header view model located on this X position. In this
     *                              example, the model class is "ColumnHeader".
     * @param position              : This is the X (Column) position of the column header item.
     * @see #onCreateColumnHeaderViewHolder(ViewGroup, int) ;
     */
    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object columnHeaderItemModel, int
            position) {
        GameStatType columnHeader = (GameStatType) columnHeaderItemModel;

        // Get the holder to update cell item text
        MyColumnHeaderViewHolder columnHeaderViewHolder = (MyColumnHeaderViewHolder) holder;
        columnHeaderViewHolder.cell_textview.setText(columnHeader.name());

        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.

        // It is necessary to remeasure itself.
        columnHeaderViewHolder.container.getLayoutParams().width = LinearLayout
                .LayoutParams.WRAP_CONTENT;
        columnHeaderViewHolder.cell_textview.requestLayout();
    }

    /**
     * This is sample CellViewHolder class.
     * This viewHolder must be extended from AbstractViewHolder class instead of RecyclerView.ViewHolder.
     */
    public class MyRowHeaderViewHolder extends AbstractViewHolder {

        public final TextView cell_textview;
        private GamePlayer player;

        public MyRowHeaderViewHolder(View itemView) {
            super(itemView);
            cell_textview = (TextView) itemView.findViewById(R.id.player_label);
        }

        public void setPlayer(GamePlayer player) {
            this.player = player;
        }

        public GamePlayer getPlayer() {
            return player;
        }
    }


    /**
     * This is where you create your custom Row Header ViewHolder. This method is called when
     * Row Header RecyclerView of the TableView needs a new RecyclerView.ViewHolder of the given
     * type to represent an item.
     *
     * @param viewType : This value comes from "getRowHeaderItemViewType" method to support
     *                 different type of viewHolder as a row Header item.
     * @see #getRowHeaderItemViewType(int);
     */
    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {

        // Get Row Header xml Layout
        View layout = LayoutInflater.from(context).inflate(R.layout
                .table_view_row_header_layout, parent, false);

        // Create a Row Header ViewHolder
        return new MyRowHeaderViewHolder(layout);
    }


    /**
     * That is where you set Row Header View Model data to your custom Row Header ViewHolder. This
     * method is Called by RowHeader RecyclerView of the TableView to display the data at the
     * specified position. This method gives you everything you need about a row header item.
     *
     * @param holder             : This is one of your row header ViewHolders that was created on
     *                           ```onCreateRowHeaderViewHolder``` method. In this example we have created
     *                           "MyRowHeaderViewHolder" holder.
     * @param rowHeaderItemModel : This is the row header view model located on this Y position. In this
     *                           example, the model class is "RowHeader".
     * @param position           : This is the Y (row) position of the row header item.
     * @see #onCreateRowHeaderViewHolder(ViewGroup, int) ;
     */
    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel, int
            position) {
        GamePlayer rowHeader = (GamePlayer) rowHeaderItemModel;

        // Get the holder to update row header item text
        MyRowHeaderViewHolder rowHeaderViewHolder = (MyRowHeaderViewHolder) holder;
        rowHeaderViewHolder.cell_textview.setText(rowHeader.getPlayer().firstName());
        rowHeaderViewHolder.setPlayer(rowHeader);

        if (rowHeader.isTeamOne()) {
            rowHeaderViewHolder.cell_textview.setBackgroundColor(Color.parseColor(TEAM_ONE_COLOR_STRING));
        } else {
            rowHeaderViewHolder.cell_textview.setBackgroundColor(Color.parseColor(TEAM_TWO_COLOR_STRING));
        }
    }


    @Override
    public View onCreateCornerView() {
        // Get Corner xml layout
        return LayoutInflater.from(context).inflate(R.layout.table_view_corner_layout, null);
    }

    @Override
    public int getColumnHeaderItemViewType(int columnPosition) {
        // The unique ID for this type of column header item
        // If you have different items for Cell View by X (Column) position,
        // then you should fill this method to be able create different
        // type of CellViewHolder on "onCreateCellViewHolder"
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int rowPosition) {
        // The unique ID for this type of row header item
        // If you have different items for Row Header View by Y (Row) position,
        // then you should fill this method to be able create different
        // type of RowHeaderViewHolder on "onCreateRowHeaderViewHolder"
        return 0;
    }

    @Override
    public int getCellItemViewType(int columnPosition) {
        return viewType;
    }
}
