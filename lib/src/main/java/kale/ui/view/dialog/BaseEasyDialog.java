package kale.ui.view.dialog;

import java.lang.reflect.Field;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.CallSuper;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.view.Window;

import lombok.AccessLevel;
import lombok.Getter;

import static android.support.v7.app.AlertController.AlertParams;
import static android.support.v7.app._Kale_EasyDialog_AlertDialog.resolveDialogTheme;

/**
 * @author Jack Tony
 * @date 2015/10/12
 *
 * 仅仅提供dialog基础的功能，主要处理处理传参相关的工作
 */
public abstract class BaseEasyDialog extends AppCompatDialogFragment {

    public static final String DIALOG_TAG = "KALE-EASY-DIALOG";

    private static final String KEY_DIALOG_PARAMS = "key_dialog_params";

    private static final String KEY_IS_BOTTOM_DIALOG = "key_is_bottom_dialog";

    private static final String KEY_IS_RETAIN_INSTANCE = "key_is_retain_instance";

    @Getter(AccessLevel.PROTECTED)
    private boolean isBottomDialog;

    @Getter(AccessLevel.PROTECTED)
    private DialogParams dialogParams;

    @Getter
    private boolean isRestored = false;

    @Override
    public void setArguments(Bundle args) {
        if (getArguments() != null) {
            getArguments().putAll(args);
        } else {
            super.setArguments(args);
        }
    }

    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            dialogParams = (DialogParams) bundle.getSerializable(KEY_DIALOG_PARAMS);

            isBottomDialog = bundle.getBoolean(KEY_IS_BOTTOM_DIALOG, false);

            boolean isRetainInstance = bundle.getBoolean(KEY_IS_RETAIN_INSTANCE, false);
            setRetainInstance(isRetainInstance);
        }
    }

    /**
     * 这里千万不要做{@link Dialog#findViewById(int)}的操作
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog ignored = super.onCreateDialog(savedInstanceState);
        if (savedInstanceState != null) {
            isRestored = true;
            onRestoreInstanceState(savedInstanceState);
        }
        return createDialog(getActivity());
    }

    /**
     * 这时dialog已经创建完毕，可以调用{@link android.app.Dialog#findViewById(int)}
     */
    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        bindAndSetViews(window != null ? window.getDecorView() : null);
    }

    @Override
    public void onDestroyView() {
        Dialog dialog = getDialog();

        // Work around bug: http://code.google.com/p/android/issues/detail?id=17423
        if (dialog != null && getRetainInstance()) {
            dialog.setDismissMessage(null);
        }
        super.onDestroyView();
    }

    protected abstract void onRestoreInstanceState(Bundle savedInstanceState);

    protected abstract void bindAndSetViews(View root);

    protected abstract Dialog createDialog(FragmentActivity activity);

    /**
     * 辅助方法，用来find对话框中的view
     */
    protected <T extends View> T findView(@IdRes int id) {
        return getDialog().findViewById(id);
    }

    public void show(FragmentManager manager) {
        show(manager, DIALOG_TAG);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if (manager == null || manager.isDestroyed() || manager.isStateSaved()) {
            // do nothing!!!
            return;
        }

        try {
            super.show(manager, tag);
        } catch (IllegalStateException e) {
            // 防御：java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
            e.printStackTrace();
        }
    }

    public void showAllowingStateLoss(FragmentManager manager) {
        showAllowingStateLoss(manager, DIALOG_TAG);
    }

    public void showAllowingStateLoss(FragmentManager manager, String tag) {
        manager.beginTransaction().add(this, tag).commitAllowingStateLoss();
    }

    /**
     * @author Kale
     * @date 2016/11/22
     */
    public abstract static class Builder<T extends Builder> extends AlertDialog.Builder {

        private int dialogThemeResId;

        private boolean isBottomDialog = false;

        private boolean isRetainInstance = false;

        public Builder(@NonNull Context context) {
            this(context, resolveDialogTheme(context, 0));
        }

        public Builder(@NonNull Context context, @StyleRes int themeResId) {
            super(context, themeResId);
            dialogThemeResId = themeResId;
        }

        ///////////////////////////////////////////////////////////////////////////
        // normal
        ///////////////////////////////////////////////////////////////////////////

        @Override
        public T setTitle(CharSequence title) {
            return (T) super.setTitle(title);
        }

        @Override
        public T setTitle(@StringRes int titleId) {
            return (T) super.setTitle(titleId);
        }

        @Override
        public T setMessage(CharSequence message) {
            return (T) super.setMessage(message);
        }

        @Override
        public T setMessage(@StringRes int messageId) {
            return (T) super.setMessage(messageId);
        }

        @Override
        public T setIcon(@DrawableRes int iconId) {
            return (T) super.setIcon(iconId);
        }

        @Override
        public T setIcon(Drawable icon) {
            return (T) super.setIcon(icon);
        }

        ///////////////////////////////////////////////////////////////////////////
        // button bar
        ///////////////////////////////////////////////////////////////////////////

        @Override
        public T setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
            return (T) super.setPositiveButton(text, listener);
        }

        @Override
        public T setPositiveButton(@StringRes int textId, DialogInterface.OnClickListener listener) {
            return (T) super.setPositiveButton(textId, listener);
        }

        @Override
        public T setNeutralButton(CharSequence text, DialogInterface.OnClickListener listener) {
            return (T) super.setNeutralButton(text, listener);
        }

        @Override
        public T setNeutralButton(@StringRes int textId, DialogInterface.OnClickListener listener) {
            return (T) super.setNeutralButton(textId, listener);
        }

        @Override
        public T setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
            return (T) super.setNegativeButton(text, listener);
        }

        @Override
        public T setNegativeButton(@StringRes int textId, DialogInterface.OnClickListener listener) {
            return (T) super.setNegativeButton(textId, listener);
        }

        ///////////////////////////////////////////////////////////////////////////
        // single
        ///////////////////////////////////////////////////////////////////////////

        @Override
        public T setItems(CharSequence[] items, DialogInterface.OnClickListener listener) {
            return (T) super.setItems(items, listener);
        }

        @Override
        public T setItems(@ArrayRes int itemsId, DialogInterface.OnClickListener listener) {
            return (T) super.setItems(itemsId, listener);
        }

        @Override
        public T setSingleChoiceItems(CharSequence[] items, int checkedItem, DialogInterface.OnClickListener listener) {
            return (T) super.setSingleChoiceItems(items, checkedItem, listener);
        }

        @Override
        public T setSingleChoiceItems(@ArrayRes int itemsId, int checkedItem, DialogInterface.OnClickListener listener) {
            return (T) super.setSingleChoiceItems(itemsId, checkedItem, listener);
        }

        ///////////////////////////////////////////////////////////////////////////
        // multi
        ///////////////////////////////////////////////////////////////////////////

        @Override
        public T setMultiChoiceItems(@ArrayRes int itemsId, boolean[] checkedItems,
                DialogInterface.OnMultiChoiceClickListener listener) {
            return (T) super.setMultiChoiceItems(itemsId, checkedItems, listener);
        }

        @Override
        public T setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems,
                DialogInterface.OnMultiChoiceClickListener listener) {
            return (T) super.setMultiChoiceItems(items, checkedItems, listener);
        }

        public T setIsBottomDialog(boolean inBottom) {
            isBottomDialog = inBottom;
            return (T) this;
        }

        public T setRetainInstance(boolean retain) {
            isRetainInstance = retain;
            return (T) this;
        }

        ///////////////////////////////////////////////////////////////////////////
        // finish
        ///////////////////////////////////////////////////////////////////////////

        public <D extends EasyDialog> D build() {
            EasyDialog dialog = createDialog();
            AlertParams p = getParams();

            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_DIALOG_PARAMS, createDialogParamsByAlertParams(p));
            bundle.putBoolean(KEY_IS_BOTTOM_DIALOG, isBottomDialog);
            bundle.putBoolean(KEY_IS_RETAIN_INSTANCE, isRetainInstance);
            dialog.setArguments(bundle);

            dialog.cancelListener = p.mOnCancelListener;
            dialog.dismissListener = p.mOnDismissListener;

            dialog.positiveListener = p.mPositiveButtonListener;
            dialog.neutralListener = p.mNeutralButtonListener;
            dialog.negativeListener = p.mNegativeButtonListener;
            dialog.clickListener = p.mOnClickListener;
            dialog.multiClickListener = p.mOnCheckboxClickListener;

            dialog.setCancelable(p.mCancelable);
            return (D) dialog;
        }

        @NonNull
        protected abstract EasyDialog createDialog();

        private DialogParams createDialogParamsByAlertParams(AlertParams p) {
            DialogParams params = new DialogParams();

            params.themeResId = dialogThemeResId;

            params.mIconId = p.mIconId;
            params.title = p.mTitle;
            params.message = p.mMessage;
            params.positiveText = p.mPositiveButtonText;
            params.neutralText = p.mNeutralButtonText;
            params.negativeText = p.mNegativeButtonText;
            params.items = p.mItems;
            params.isMultiChoice = p.mIsMultiChoice;
            params.checkedItems = p.mCheckedItems;
            params.isSingleChoice = p.mIsSingleChoice;
            params.checkedItem = p.mCheckedItem;

            return params;
        }

        /**
         * Should use {@link Builder#build()}
         */
        @Deprecated
        @Override
        public AlertDialog create() {
            throw new UnsupportedOperationException("you should use build()");
        }

        AlertParams getParams() {
            AlertParams P = null;
            try {
                Field field = AlertDialog.Builder.class.getDeclaredField("P");
                field.setAccessible(true);
                P = (AlertParams) field.get(this);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return P;
        }

    }

}
