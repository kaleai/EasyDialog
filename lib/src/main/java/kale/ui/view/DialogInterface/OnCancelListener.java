package kale.ui.view.DialogInterface;

import android.content.DialogInterface;

import java.io.Serializable;

/**
 * @author Jack Tony
 * @date 2015/10/10
 */
public interface OnCancelListener extends Serializable ,DialogInterface.OnCancelListener{

    public void onCancel(DialogInterface dialog);

}
