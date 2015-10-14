package kale.ui.view.DialogInterface;

import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Jack Tony
 * @date 2015/10/10
 */
public abstract class OnDismissListener implements DialogInterface.OnDismissListener, Parcelable {

    public abstract void onDismiss(DialogInterface dialog);

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }


}
