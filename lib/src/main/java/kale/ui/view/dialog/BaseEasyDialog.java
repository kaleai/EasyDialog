package kale.ui.view.dialog;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
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
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.TypedValue;
import android.view.View;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import static android.support.v7.app.AlertController.AlertParams;

/**
 * @author Jack Tony
 * @date 2015/10/12
 */
public abstract class BaseEasyDialog extends AppCompatDialogFragment {

    private static final String KEY_DIALOG_PARAMS = "key_dialog_params";

    private static final String KEY_IS_BOTTOM_DIALOG = "key_is_bottom_dialog";

    @Setter(AccessLevel.PUBLIC)
    private DialogInterface.OnDismissListener onDismissListener;

    @Setter(AccessLevel.PUBLIC)
    private DialogInterface.OnCancelListener onCancelListener;

    @Getter(AccessLevel.PROTECTED)
    private boolean isBottomDialog;

    @Getter(AccessLevel.PROTECTED)
    private DialogParams dialogParams;

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
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (onCancelListener != null) {
            onCancelListener.onCancel(dialog);
        }
    }

    protected <T extends View> T findView(@IdRes int id) {
        return getDialog().findViewById(id);
    }

    public void show(FragmentManager manager) {
        show(manager, "kale-easy-dialog");
    }

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
        manager.beginTransaction().add(this, "kale-dialog").commitAllowingStateLoss();
    }

    /**
     * @author Kale
     * @date 2016/11/22
     */
    public abstract static class Builder<T extends Builder> extends AlertDialog.Builder {

        private int themeResId;

        private boolean isBottomDialog = false;

        public Builder(@NonNull Context context) {
            super(context);
        }

        @SuppressLint("ResourceType")
        public static int resolveDialogTheme(@NonNull Context context, @StyleRes int resid) {
            if (resid >= 0x01000000) {   // start of real resource IDs.
                return resid;
            } else {
                TypedValue outValue = new TypedValue();
                context.getTheme().resolveAttribute(android.R.attr.alertDialogTheme, outValue, true);
                return outValue.resourceId;
            }
        }

        public Builder(@NonNull Context context, @StyleRes int themeResId) {
            super(context, themeResId);
            this.themeResId = themeResId;
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

        public T setIsBottomDialog(boolean b) {
            this.isBottomDialog = b;
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
            dialog.setArguments(bundle);

            dialog.setOnCancelListener(p.mOnCancelListener);
            dialog.setOnDismissListener(p.mOnDismissListener);

            dialog.setPositiveListener(p.mPositiveButtonListener);
            dialog.setNeutralListener(p.mNeutralButtonListener);
            dialog.setNegativeListener(p.mNegativeButtonListener);
            dialog.setOnClickListener(p.mOnClickListener);
            dialog.setOnMultiChoiceClickListener(p.mOnCheckboxClickListener);

            dialog.setCancelable(p.mCancelable);
            return (D) dialog;
        }

        @NonNull
        protected abstract EasyDialog createDialog();

        private DialogParams createDialogParamsByAlertParams(AlertParams p) {
            DialogParams params = new DialogParams();

            params.themeResId = themeResId;

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
         * use {@link Builder#build()}
         */
        @Deprecated
        @Override
        @SuppressWarnings("unchecked")
        public AlertDialog create() {
            return super.create();
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
