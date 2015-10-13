package kale.ui.view;


import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import kale.ui.view.DialogInterface.OnClickListener;


/**
 * @author Jack Tony
 * @date 2015/8/11
 */
public abstract class BaseEasyAlertDialog extends BaseEasyDialog {

    private String mPositiveText;

    private static final String KEY_POSITIVE_TEXT = "KEY_POSITIVE_TEXT";

    private OnClickListener mPositiveListener;

    private static final String KEY_POSITIVE_LISTENER = "KEY_POSITIVE_LISTENER";

    private String mNegativeText;

    private static final String KEY_NEGATIVE_TEXT = "KEY_NEGATIVE_TEXT";

    private OnClickListener mNegativeListener;

    private static final String KEY_NEGATIVE_LISTENER = "KEY_NEGATIVE_LISTENER";

    @NonNull
    @Override
    protected AlertDialog.Builder initBuilder() {
        return new AlertDialog.Builder(getActivity());
    }
    
    @Override
    protected void setBuilder(AlertDialog.Builder builder, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPositiveText = savedInstanceState.getString(KEY_POSITIVE_TEXT);
            mPositiveListener = (OnClickListener) savedInstanceState.getSerializable(KEY_POSITIVE_LISTENER);
            mNegativeText = savedInstanceState.getString(KEY_NEGATIVE_TEXT);
            mNegativeListener = (OnClickListener) savedInstanceState.getSerializable(KEY_NEGATIVE_LISTENER);
        }
        if (mPositiveText != null && mPositiveListener != null) {
            builder.setPositiveButton(mPositiveText, mPositiveListener);
        }
        if (mNegativeText != null && mNegativeListener != null) {
            builder.setNegativeButton(mNegativeText, mNegativeListener);
        }
        setBuilder(builder);
    }

    @CallSuper
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_POSITIVE_TEXT, mPositiveText);
        outState.putSerializable(KEY_POSITIVE_LISTENER, mPositiveListener);
        outState.putString(KEY_NEGATIVE_TEXT, mNegativeText);
        outState.putSerializable(KEY_NEGATIVE_LISTENER, mNegativeListener);
    }

    protected abstract void setBuilder(AlertDialog.Builder builder);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPositiveText = null;
        mPositiveListener = null;
        mNegativeText = null;
        mNegativeListener = null;
    }

    public void setPositiveListener(String positiveText, OnClickListener listener) {
        mPositiveText = positiveText; 
        mPositiveListener = listener;
    }

    public void setNegativeListener(String negativeText, OnClickListener listener) {
        mNegativeText = negativeText;
        mNegativeListener = listener;
    }
    
}
