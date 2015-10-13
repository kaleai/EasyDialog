package kale.ui.view.DialogInterface;

import android.content.DialogInterface;

import java.io.Serializable;

/**
 * @author Jack Tony
 * @date 2015/10/10
 */
public interface OnClickListener extends Serializable, DialogInterface.OnClickListener {

    public void onClick(DialogInterface dialog, int which);
}
