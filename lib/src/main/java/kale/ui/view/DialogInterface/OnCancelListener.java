package kale.ui.view.DialogInterface;

import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Jack Tony
 * @date 2015/10/10
 */
public abstract class OnCancelListener implements Parcelable ,DialogInterface.OnCancelListener{

    public abstract void onCancel(DialogInterface dialog);

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
