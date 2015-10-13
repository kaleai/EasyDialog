package kale.ui.view.DialogInterface;

import android.content.DialogInterface;

import java.io.Serializable;

/**
 * @author Jack Tony
 * @date 2015/10/10
 */
public interface OnDismissListener extends Serializable ,DialogInterface.OnDismissListener {

    public void onDismiss(DialogInterface dialog);
}
