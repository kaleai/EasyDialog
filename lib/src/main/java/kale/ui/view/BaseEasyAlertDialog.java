package kale.ui.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import kale.ui.view.DialogInterface.OnClickListener;


/**
 * @author Jack Tony
 * @date 2015/8/11
 */
public abstract class BaseEasyAlertDialog extends BaseEasyDialog {

    private static final String KEY_POSITIVE_TEXT = "KEY_POSITIVE_TEXT";

    private static final String KEY_POSITIVE_LISTENER = "KEY_POSITIVE_LISTENER";

    private static final String KEY_NEGATIVE_TEXT = "KEY_NEGATIVE_TEXT";

    private static final String KEY_NEGATIVE_LISTENER = "KEY_NEGATIVE_LISTENER";
    
    
    @NonNull
    @Override
    protected AlertDialog.Builder initBuilder() {
        return new AlertDialog.Builder(getActivity());
    }

    protected abstract static class Builder extends BaseEasyDialog.Builder {

        public Builder setPositiveListener(String positiveText, OnClickListener listener) {
            bundle.putCharSequence(KEY_POSITIVE_TEXT, positiveText);
            bundle.putSerializable(KEY_POSITIVE_LISTENER, listener);
            return this;
        }

        public Builder setNegativeListener(String negativeText, OnClickListener listener) {
            bundle.putCharSequence(KEY_NEGATIVE_TEXT, negativeText);
            bundle.putSerializable(KEY_NEGATIVE_LISTENER, listener);
            return this;
        }

    }

    @Override
    protected void setBuilder(AlertDialog.Builder builder, @Nullable Bundle arguments) {
        if (arguments != null) {
            CharSequence positiveText = arguments.getCharSequence(KEY_POSITIVE_TEXT);
            OnClickListener positiveListener = (OnClickListener) arguments.getSerializable(KEY_POSITIVE_LISTENER);
            CharSequence negativeText = arguments.getCharSequence(KEY_NEGATIVE_TEXT);
            OnClickListener negativeListener = (OnClickListener) arguments.getSerializable(KEY_NEGATIVE_LISTENER);
            
            if (positiveText != null && positiveListener != null) {
                builder.setPositiveButton(positiveText, positiveListener);
            }
            if (negativeText != null && negativeListener != null) {
                builder.setNegativeButton(negativeText, negativeListener);
            }
        }
        setAlertBuilder(builder, arguments);
    }

    protected abstract void setAlertBuilder(AlertDialog.Builder builder, @Nullable Bundle arguments);


}
