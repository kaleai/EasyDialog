package kale.ui.view.DialogInterface;

import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Jack Tony
 * @date 2015/10/10
 */
public abstract class OnMultiChoiceClickListener implements Parcelable {

    /**
     * This method will be invoked when an item in the dialog is clicked.
     *
     * @param dialog    The dialog where the selection was made.
     * @param which     The position of the item in the list that was clicked.
     * @param isChecked True if the click checked the item, else false.
     */
    public abstract void onClick(DialogInterface dialog, int which, boolean isChecked);

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
