package kale.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertController;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.View;

import java.lang.reflect.Field;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jack Tony
 * @date 2015/10/12
 */
public abstract class BaseEasyDialog extends DialogFragment {

    @Getter
    private boolean isRestored = false;

    @Setter(AccessLevel.PUBLIC)
    private DialogInterface.OnDismissListener onDismissListener;

    @Setter(AccessLevel.PUBLIC)
    private DialogInterface.OnCancelListener onCancelListener;

    @Override
    public void setArguments(Bundle args) {
        if (getArguments() != null) {
            getArguments().putAll(args);
        } else {
            super.setArguments(args);
        }
    }

    /**
     * 这里千万不要做{@link Dialog#findViewById(int)}的操作，这必须要等dialog创建后才行
     */
    @CallSuper
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        return null;
    }

    @CallSuper
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        isRestored = true;
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

    protected <T extends View> T getView(@IdRes int id) {
        return (T) getDialog().findViewById(id);
    }

    public void show(FragmentManager manager) {
        show(manager, "easy-dialog");
    }

    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Kale
     * @date 2016/11/22
     */
    public abstract static class Builder<T extends Builder> extends AlertDialog.Builder {

        private int themeResId;

        public Builder(@NonNull Context context) {
            super(context);
        }

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

        ///////////////////////////////////////////////////////////////////////////
        // finish
        ///////////////////////////////////////////////////////////////////////////

        public <D extends EasyDialog> D build() {
            EasyDialog dialog = createDialog();
            AlertController.AlertParams p = getParams();

            Bundle bundle = new Bundle();
            bundle.putSerializable(EasyDialog.KEY_BUILD_PARAMS, getBuildParams(p));
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

        public BuildParams getBuildParams(AlertController.AlertParams p) {
            BuildParams data = new BuildParams();
            data.themeResId = themeResId;

            data.title = p.mTitle;
            data.message = p.mMessage;
            data.positiveText = p.mPositiveButtonText;
            data.neutralText = p.mNeutralButtonText;
            data.negativeText = p.mNegativeButtonText;
            data.items = p.mItems;
            data.isMultiChoice = p.mIsMultiChoice;
            data.checkedItems = p.mCheckedItems;
            data.isSingleChoice = p.mIsSingleChoice;
            data.checkedItem = p.mCheckedItem;

            return data;
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

        AlertController.AlertParams getParams() {
            AlertController.AlertParams P = null;
            try {
                Field field = AlertDialog.Builder.class.getDeclaredField("P");
                field.setAccessible(true);
                P = (AlertController.AlertParams) field.get(this);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return P;
        }

    }
}
