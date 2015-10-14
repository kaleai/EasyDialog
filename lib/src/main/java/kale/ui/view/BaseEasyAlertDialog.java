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

    private static final String KEY_NEUTRAL_TEXT = "KEY_NEUTRAL_TEXT";

    private static final String KEY_NEUTRAL_LISTENER = "KEY_NEUTRAL_LISTENER";

    private static final String KEY_NEGATIVE_TEXT = "KEY_NEGATIVE_TEXT";

    private static final String KEY_NEGATIVE_LISTENER = "KEY_NEGATIVE_LISTENER";


    @NonNull
    @Override
    protected AlertDialog.Builder initBuilder() {
        return new AlertDialog.Builder(getActivity());
    }

    protected abstract static class Builder extends BaseEasyDialog.Builder {

        public Builder setPositiveButton(CharSequence positiveText, OnClickListener listener) {
            bundle.putCharSequence(KEY_POSITIVE_TEXT, positiveText);
            bundle.putSerializable(KEY_POSITIVE_LISTENER, listener);
            return this;
        }

        public Builder setNeutralButton(CharSequence neutralText, OnClickListener listener) {
            bundle.putCharSequence(KEY_NEUTRAL_TEXT, neutralText);
            bundle.putSerializable(KEY_NEUTRAL_LISTENER, listener);
            return this;
        }

        public Builder setNegativeButton(CharSequence negativeText, OnClickListener listener) {
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
            CharSequence neutralText = arguments.getCharSequence(KEY_NEUTRAL_TEXT);
            OnClickListener neutralListener = (OnClickListener) arguments.getSerializable(KEY_NEUTRAL_LISTENER);
            CharSequence negativeText = arguments.getCharSequence(KEY_NEGATIVE_TEXT);
            OnClickListener negativeListener = (OnClickListener) arguments.getSerializable(KEY_NEGATIVE_LISTENER);

            if (positiveText != null) {
                builder.setPositiveButton(positiveText, positiveListener);
            }
            if (neutralText != null) {
                builder.setNeutralButton(neutralText, neutralListener);
            }
            if (negativeText != null) {
                builder.setNegativeButton(negativeText, negativeListener);
            }
        }
        setAlertBuilder(builder, arguments);
    }

    protected abstract void setAlertBuilder(AlertDialog.Builder builder, @Nullable Bundle arguments);


}
