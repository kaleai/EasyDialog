package kale.ui.view.DialogInterface;

import android.content.DialogInterface;

import java.io.Serializable;

/**
 * @author Jack Tony
 * @date 2015/10/10
 */
public interface OnItemClickListener extends Serializable{

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param position The position of the view in the adapter.
     * @param id The row id of the item that was clicked.
     */
    void onItemClick(DialogInterface dialog, int position, long id);
}
