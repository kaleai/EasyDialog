package kale.easydialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;

import kale.ui.view.BaseCustomDialog;

/**
 * @author Kale
 * @date 2016/11/15
 */

public class CustomDialog extends BaseCustomDialog {

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void bindViews(Dialog dialog) {

    }

    @Override
    protected void setViews() {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable());

    }
}
