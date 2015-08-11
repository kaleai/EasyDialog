package kale.ui.view;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;


/**
 * @author Jack Tony
 * @date 2015/8/11
 */
abstract class BaseEasyDialog extends DialogFragment {

    protected AlertDialog.Builder builder;

    private String mTitle;
    
    private String mPositiveText;

    private OnClickListener mPositiveListener;

    private String mNegativeText;
    
    private OnClickListener mNegativeListener;
    
    private OnDismissListener mOnDismissListener;
    
    private OnCancelListener mOnCancelListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = new AlertDialog.Builder(getActivity());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        if (mTitle != null){
            builder.setTitle(mTitle);
        }
        if (mPositiveText != null && mPositiveListener != null) {
            builder.setPositiveButton(mPositiveText, mPositiveListener);
        }
        if (mNegativeText != null && mNegativeListener != null) {
            builder.setNegativeButton(mNegativeText, mNegativeListener);
        }
        return initBuilder(builder).create();
    }

    protected abstract AlertDialog.Builder initBuilder(AlertDialog.Builder builder);


    public void setPositiveListener(String positiveText, DialogInterface.OnClickListener listener) {
        mPositiveText = positiveText; 
        mPositiveListener = listener;
    }

    public void setNegativeListener(String negativeText, DialogInterface.OnClickListener listener) {
        mNegativeText = negativeText;
        mNegativeListener = listener;
    }

    public void setOnDismissListener(@NonNull DialogInterface.OnDismissListener dimissListener) {
        mOnDismissListener = dimissListener;
    }

    public void setOnCancelListener(@NonNull DialogInterface.OnCancelListener onCancelListener) {
        mOnCancelListener = onCancelListener;
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

    public void setTitle(String title) {
        mTitle = title;
    }
}
