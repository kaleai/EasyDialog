package kale.ui.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;

/**
 * @author Kale
 * @date 2016/11/15
 *
 * 自定义dialog的父类，如果是自定义的dialog则需要实现它
 */
public abstract class BaseCustomDialog extends EasyDialog {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (!isBottomDialog()) {
            return super.onCreateDialog(savedInstanceState);
        } else {
            return new BottomSheetDialog(getContext(), getTheme());
        }
    }

    @Override
    protected void modifyAlertDialogBuilder(android.support.v7.app.AlertDialog.Builder builder) {
        super.modifyAlertDialogBuilder(builder);
        if (getLayoutResId() != 0) {
            builder.setView(getLayoutResId());
        }
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        if (isBottomDialog()) {
            dialog.setContentView(getLayoutResId());
        }
    }

    @Override
    protected void bindAndSetViews(@Nullable View root) {
        super.bindAndSetViews(root);
        bindViews(root);
        setViews();
    }

    /**
     * @return 默认返回0
     */
    protected abstract int getLayoutResId();

    protected abstract void bindViews(View root);

    protected abstract void setViews();
    
}
