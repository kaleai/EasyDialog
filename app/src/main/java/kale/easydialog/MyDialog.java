package kale.easydialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * @author Jack Tony
 * @date 2015/10/30
 */
public class MyDialog extends AlertDialog{

    protected MyDialog(Context context) {
        this(context, R.style.Theme_Dialog_Alert);
    }

    protected MyDialog(Context context, int theme) {
        super(context, theme);
    }

    protected MyDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
