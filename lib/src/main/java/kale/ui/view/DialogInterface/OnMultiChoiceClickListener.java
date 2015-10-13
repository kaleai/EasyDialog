package kale.ui.view.DialogInterface;

import android.content.DialogInterface;

import java.io.Serializable;

/**
 * @author Jack Tony
 * @date 2015/10/10
 */
public interface OnMultiChoiceClickListener extends Serializable {

    /**
     * This method will be invoked when an item in the dialog is clicked.
     *
     * @param dialog    The dialog where the selection was made.
     * @param which     The position of the item in the list that was clicked.
     * @param isChecked True if the click checked the item, else false.
     */
    public void onClick(DialogInterface dialog, int which, boolean isChecked);

}
