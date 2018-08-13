package kale.ui.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;

import lombok.Getter;
import lombok.Setter;

import static android.support.v7.app._Kale_EasyDialog_AlertDialog.resolveDialogTheme;

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
     * use {@link Builder#build()}
     */
    public EasyDialog() {
    }

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
     * 这时dialog已经创建完毕，可以调用{@link Dialog#findViewById(int)}
     */
    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        bindAndSetViews(window != null ? window.getDecorView() : null);
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

    protected void bindAndSetViews(@Nullable View root) {
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

        Builder builder = builder(activity)
                .setTitle(p.title)
                .setIcon(p.mIconId)
                .setMessage(p.message)
                .setPositiveButton(p.positiveText, positiveListener)
                .setNeutralButton(p.neutralText, neutralListener)
                .setNegativeButton(p.negativeText, negativeListener)
                .setItems(p.items, null);

        if (p.items != null) {
            if (p.isMultiChoice) {
                builder.setMultiChoiceItems(p.items, p.checkedItems, onMultiChoiceClickListener);
            } else if (p.isSingleChoice) {
                builder.setSingleChoiceItems(p.items, p.checkedItem, onClickListener);
            } else {
                builder.setItems(p.items, onClickListener);
            }
        }

        modifyOriginBuilder(builder);
        return builder.create();
    }

    /**
     * 修改构造当前dialog的builder对象，框架将会用这个builder对象来做真正的构造器
     */
    @CallSuper
    protected void modifyOriginBuilder(Builder builder) {
    }

    /**
     * 非反射的调用方式
     */
    public static Builder builder(Context context) {
        return builder(context, resolveDialogTheme(context, 0));
    }

    public static Builder builder(Context context, int themeId) {
        return new Builder(context, themeId) {
            @NonNull
            @Override
            protected EasyDialog createDialog() {
                return new EasyDialog();
            }
        };
    }

    public static <Dialog extends EasyDialog> Builder builder(Context context, final Class<Dialog> clz) {
        return builder(context, resolveDialogTheme(context, 0), clz);
    }

    public static <Dialog extends EasyDialog> Builder builder(Context context, int themeId, final Class<Dialog> clz) {
        return new Builder(context, themeId) {
            @NonNull
            @Override
            protected EasyDialog createDialog() {
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
        };
    }

}
