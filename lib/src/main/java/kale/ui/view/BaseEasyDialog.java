package kale.ui.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import kale.ui.view.DialogInterface.OnCancelListener;
import kale.ui.view.DialogInterface.OnDismissListener;

/**
 * @author Jack Tony
 * @date 2015/10/12
 */
public abstract class BaseEasyDialog extends DialogFragment {

    private CharSequence mTitle;

    private static final String KEY_TITLE = "KEY_TITLE";

    private OnDismissListener mOnDismissListener;

    private static final String KEY_DISMISS_LISTENER = "KEY_DISMISS_LISTENER";

    private OnCancelListener mOnCancelListener;

    private static final String KEY_CANCEL_LISTENER = "KEY_CANCEL_LISTENER";


    protected abstract static class Builder {

        protected Bundle bundle = new Bundle();

        public Builder setTitle(CharSequence title) {
            bundle.putCharSequence(KEY_TITLE, title);
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener listener) {
            bundle.putSerializable(KEY_DISMISS_LISTENER, listener);
            return this;
        }

        public Builder setOnCancelListener(OnCancelListener listener) {
            bundle.putSerializable(KEY_CANCEL_LISTENER, listener);
            return this;
        }

        public abstract BaseEasyDialog create();
        
    }


    protected abstract
    @NonNull
    AlertDialog.Builder initBuilder();

    @CallSuper
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle;
        if ((bundle = getArguments()) != null) {
            mTitle = bundle.getCharSequence(KEY_TITLE);
            mOnDismissListener = (OnDismissListener) bundle.getSerializable(KEY_DISMISS_LISTENER);
            mOnCancelListener = (OnCancelListener) bundle.getSerializable(KEY_CANCEL_LISTENER);
            onRestoreInstanceState(savedInstanceState);
        }

        AlertDialog.Builder builder = initBuilder();
        if (mTitle != null) {
            builder.setTitle(mTitle);
        }
        setBuilder(builder, bundle);
        return builder.create();
    }

    protected abstract void setBuilder(AlertDialog.Builder builder, @Nullable Bundle arguments);

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

    }

/*    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TITLE, mTitle);
        outState.putSerializable(KEY_DISMISS_LISTENER, mOnDismissListener);
        outState.putSerializable(KEY_CANCEL_LISTENER, mOnCancelListener);
    }*/


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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTitle = null;
        mOnDismissListener = null;
        mOnCancelListener = null;
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

}
