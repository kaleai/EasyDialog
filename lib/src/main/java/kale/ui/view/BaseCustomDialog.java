package kale.ui.view;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;

/**
 * @author Kale
 * @date 2016/11/15
 *
 * 不用系统的dialog，自己定义的dialog需要继承此类
 */
public abstract class BaseCustomDialog extends BaseEasyDialog {

    @Override
    protected void configDialogBuilder(AlertDialog.Builder builder) {
        super.configDialogBuilder(builder);
        if (getLayoutResId() != 0) {
            builder.setView(getLayoutResId());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        bindViews(getDialog());
        setViews();
    }

    /**
     * @return 默认返回0
     */
    protected abstract int getLayoutResId();

    /**
     * 可以利用{@link BaseEasyDialog#getView(int)}得到view
     */
    protected abstract void bindViews(Dialog dialog);

    protected abstract void setViews();
}
