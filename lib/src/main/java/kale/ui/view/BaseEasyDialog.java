package kale.ui.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import kale.ui.view.DialogInterface.OnCancelListener;
import kale.ui.view.DialogInterface.OnDismissListener;

/**
 * @author Jack Tony
 * @date 2015/10/12
 */
public abstract class BaseEasyDialog extends DialogFragment {

    protected final int DEFAULT_RES_ID = -31;

    private static final String KEY_TITLE = "key_title";

    private static final String KEY_TITLE_RES_ID = "key_title_res_id";

    private CharSequence mTitle;


    private static final String KEY_DISMISS_LISTENER = "key_dismiss_listener";

    private OnDismissListener mOnDismissListener;


    private static final String KEY_CANCEL_LISTENER = "key_cancel_listener";

    private OnCancelListener mOnCancelListener;


    private boolean mIsRestored = false;

    protected abstract static class Builder<T> {

        protected Bundle bundle = new Bundle();

        public T setTitle(CharSequence title) {
            bundle.putCharSequence(KEY_TITLE, title);
            return (T) this;
        }

        public T setTitle(@StringRes int title) {
            bundle.putInt(KEY_TITLE_RES_ID, title);
            return (T) this;
        }

        public T setOnDismissListener(OnDismissListener listener) {
            bundle.putParcelable(KEY_DISMISS_LISTENER, listener);
            return (T) this;
        }

        public T setOnCancelListener(OnCancelListener listener) {
            bundle.putParcelable(KEY_CANCEL_LISTENER, listener);
            return (T) this;
        }

        public <E extends BaseEasyDialog> E create() {
            E dialog = (E) createDialog();
            dialog.setArguments(bundle);
            return dialog;
        }

        protected abstract
        @NonNull
        BaseEasyDialog createDialog();

        public Bundle getBundle() {
            return bundle;
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getCharSequence(KEY_TITLE);
            int stringResId;
            if (mTitle == null && (stringResId = arguments.getInt(KEY_TITLE_RES_ID, DEFAULT_RES_ID)) != DEFAULT_RES_ID) {
                mTitle = getString(stringResId);
            }
            mOnDismissListener = arguments.getParcelable(KEY_DISMISS_LISTENER);
            mOnCancelListener = arguments.getParcelable(KEY_CANCEL_LISTENER);
        }
    }

    @CallSuper
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        AlertDialog.Builder builder = initBuilder();
        builder.setTitle(mTitle); // 内部会进行非空判断的，所以这里不用进行判断，可以直接传null
        setBuilder(builder);
        Dialog dialog = builder.create();
        setDialog(dialog);
        return dialog;
    }

    protected abstract
    @NonNull
    AlertDialog.Builder initBuilder();

    protected abstract void setBuilder(@NonNull AlertDialog.Builder builder);

    protected void setDialog(Dialog dialog) {

    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mIsRestored = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        setViews();
    }

    protected void setViews() {
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mOnCancelListener != null) {
            mOnCancelListener.onCancel(dialog);
        }
    }

    public boolean isRestored() {
        return mIsRestored;
    }

    public CharSequence getTitle() {
        return mTitle;
    }

    protected OnDismissListener getOnDismissListener() {
        return mOnDismissListener;
    }

    protected OnCancelListener getOnCancelListener() {
        return mOnCancelListener;
    }

    protected <T extends View> T getView(@IdRes int id) {
        return (T) getDialog().findViewById(id);
    }

}
