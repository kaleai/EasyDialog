package kale.ui.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.View;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Kale
 * @date 2016/11/22
 */
public class EasyDialog extends BaseEasyDialog {

    @Getter
    @Setter
    private DialogInterface.OnClickListener positiveListener;

    @Getter
    @Setter
    private DialogInterface.OnClickListener neutralListener;

    @Getter
    @Setter
    private DialogInterface.OnClickListener negativeListener;

    @Getter
    @Setter
    private DialogInterface.OnClickListener onClickListener;

    @Getter
    @Setter
    private DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener;

    @Getter
    private boolean isRestored = false;

    /**
     * 这里千万不要做{@link Dialog#findViewById(int)}的操作
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog ignored = super.onCreateDialog(savedInstanceState);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        return createDialog(getActivity());
    }

    /**
     * 复写来保存参数
     */
    @CallSuper
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 复写来恢复参数
     */
    @CallSuper
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        isRestored = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        positiveListener = null;
        negativeListener = null;
        neutralListener = null;
        onClickListener = null;
        onMultiChoiceClickListener = null;
    }

    private Dialog createDialog(@NonNull Activity activity) {
        DialogParams p = getDialogParams(); // 得到来自父类的参数，这里将参数组装成builder对象

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, p.themeResId)
                .setTitle(p.title)
                .setIcon(p.mIconId)
                .setMessage(p.message)
                .setPositiveButton(p.positiveText, positiveListener)
                .setNeutralButton(p.neutralText, neutralListener)
                .setNegativeButton(p.negativeText, negativeListener);

        if (p.items != null) {
            if (p.isMultiChoice) {
                builder.setMultiChoiceItems(p.items, p.checkedItems, onMultiChoiceClickListener);
            } else if (p.isSingleChoice) {
                builder.setSingleChoiceItems(p.items, p.checkedItem, onClickListener);
            } else {
                builder.setItems(p.items, onClickListener);
            }
        }

        // 允许子类修改builder对象
        modifyAlertDialogBuilder(builder);

        return builder.create();
    }

    /**
     * 修改构造当前dialog的{@link AlertDialog.Builder}对象，框架将会用这个builder对象来做dialog真正的构造器
     */
    @CallSuper
    protected void modifyAlertDialogBuilder(AlertDialog.Builder builder) {
    }

    @Override
    protected void bindAndSetViews(@Nullable View root) {
    }

    public static Builder builder(Context context) {
        return builder(context, 0);
    }

    public static Builder builder(Context context, @StyleRes int themeResId) {
        return builder(context, themeResId, null);
    }

    public static <Dialog extends EasyDialog> Builder builder(Context context, final Class<Dialog> clz) {
        return builder(context, 0, clz);
    }

    public static <Dialog extends EasyDialog> Builder builder(Context context, @StyleRes int themeResId, final Class<Dialog> clz) {
        return DialogBuilderFactory.getBuilder(context, themeResId, clz);
    }

}
