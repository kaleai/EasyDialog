package kale.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * @author Kale
 * @date 2016/11/22
 */
public class EasyDialog extends BaseEasyDialog {

    @Getter(AccessLevel.PROTECTED)
    DialogInterface.OnClickListener positiveListener;

    @Getter(AccessLevel.PROTECTED)
    DialogInterface.OnClickListener neutralListener;

    @Getter(AccessLevel.PROTECTED)
    DialogInterface.OnClickListener negativeListener;

    @Getter(AccessLevel.PROTECTED)
    DialogInterface.OnClickListener clickListener;

    @Getter(AccessLevel.PROTECTED)
    DialogInterface.OnMultiChoiceClickListener multiClickListener;

    @Getter(AccessLevel.PROTECTED)
    DialogInterface.OnDismissListener dismissListener;

    @Getter(AccessLevel.PROTECTED)
    DialogInterface.OnCancelListener cancelListener;

    /**
     * 保存参数
     */
    @CallSuper
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        EasyDialogListeners.saveListenersIfActivity(this);
    }

    /**
     * 恢复参数
     */
    @CallSuper
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        EasyDialogListeners.restoreListenersIfActivity(this, getActivity());
    }

    /**
     * 清理参数
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EasyDialogListeners.destroyListeners(this);
    }

    @Override
    protected Dialog createDialog(@NonNull FragmentActivity activity) {
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
                builder.setMultiChoiceItems(p.items, p.checkedItems, multiClickListener);
            } else if (p.isSingleChoice) {
                builder.setSingleChoiceItems(p.items, p.checkedItem, clickListener);
            } else {
                builder.setItems(p.items, clickListener);
            }
        }

        // 允许子类修改builder对象
        modifyAlertDialogBuilder(builder);

        return builder.create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (cancelListener != null) {
            cancelListener.onCancel(dialog);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss(dialog);
        }
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

    public static Builder builder(@NonNull Context context) {
        return builder(context, 0);
    }

    public static Builder builder(@NonNull Context context, @StyleRes int themeResId) {
        return builder(context, themeResId, null);
    }

    public static <Dialog extends EasyDialog> Builder builder(@NonNull Context context, final Class<Dialog> clz) {
        return builder(context, 0, clz);
    }

    public static <Dialog extends EasyDialog> Builder builder(@NonNull Context context, @StyleRes int themeResId, final Class<Dialog> clz) {
        if (themeResId == 0) {
            return new BaseEasyDialog.Builder(context) {
                @NonNull
                @Override
                protected EasyDialog createDialog() {
                    return getEasyDialog(clz);
                }
            };
        } else {
            return new BaseEasyDialog.Builder(context, themeResId) {
                @NonNull
                @Override
                protected EasyDialog createDialog() {
                    return getEasyDialog(clz);
                }
            };
        }
    }

    @Nullable
    private static <Dialog extends EasyDialog> EasyDialog getEasyDialog(Class<Dialog> clz) {
        if (clz == null) {
            return new EasyDialog();
        }

        // 如果有class对象，那么则反射出一个实例
        Dialog dialog = null;
        try {
            dialog = clz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return dialog;
    }
    
}
