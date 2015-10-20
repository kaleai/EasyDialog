package kale.ui.view;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import kale.ui.view.DialogInterface.OnClickListener;


/**
 * @author Jack Tony
 * @date 2015/8/11
 */
public abstract class BaseEasyAlertDialog extends BaseEasyDialog {

    private static final String KEY_POSITIVE_TEXT = "key_positive_text";

    private static final String KEY_POSITIVE_TEXT_RES_ID = "key_positive_text_res_id";

    private static final String KEY_POSITIVE_LISTENER = "key_positive_listener";

    private static final String KEY_NEUTRAL_TEXT = "key_neutral_text";

    private static final String KEY_NEUTRAL_TEXT_RES_ID = "key_neutral_text_res_id";

    private static final String KEY_NEUTRAL_LISTENER = "key_neutral_listener";

    private static final String KEY_NEGATIVE_TEXT = "key_negative_text";

    private static final String KEY_NEGATIVE_TEXT_RES_ID = "key_negative_text_res_id";

    private static final String KEY_NEGATIVE_LISTENER = "key_negative_listener";

    @NonNull
    @Override
    protected AlertDialog.Builder initBuilder() {
        return new AlertDialog.Builder(getActivity());
    }

    protected abstract static class Builder extends BaseEasyDialog.Builder {

        public Builder setPositiveButton(CharSequence positiveText, OnClickListener listener) {
            bundle.putCharSequence(KEY_POSITIVE_TEXT, positiveText);
            bundle.putParcelable(KEY_POSITIVE_LISTENER, listener);
            return this;
        }

        public Builder setPositiveButton(@StringRes int positiveText, OnClickListener listener) {
            bundle.putInt(KEY_POSITIVE_TEXT_RES_ID, positiveText);
            bundle.putParcelable(KEY_POSITIVE_LISTENER, listener);
            return this;
        }

        public Builder setNeutralButton(CharSequence neutralText, OnClickListener listener) {
            bundle.putCharSequence(KEY_NEUTRAL_TEXT, neutralText);
            bundle.putParcelable(KEY_NEUTRAL_LISTENER, listener);
            return this;
        }

        public Builder setNeutralButton(@StringRes int neutralText, OnClickListener listener) {
            bundle.putInt(KEY_NEUTRAL_TEXT_RES_ID, neutralText);
            bundle.putParcelable(KEY_NEUTRAL_LISTENER, listener);
            return this;
        }

        public Builder setNegativeButton(CharSequence negativeText, OnClickListener listener) {
            bundle.putCharSequence(KEY_NEGATIVE_TEXT, negativeText);
            bundle.putParcelable(KEY_NEGATIVE_LISTENER, listener);
            return this;
        }

        public Builder setNegativeButton(@StringRes int negativeText, OnClickListener listener) {
            bundle.putInt(KEY_NEGATIVE_TEXT_RES_ID, negativeText);
            bundle.putParcelable(KEY_NEGATIVE_LISTENER, listener);
            return this;
        }

    }

    @Override
    protected void setBuilder(AlertDialog.Builder builder, @Nullable Bundle arguments) {
        if (arguments != null) {
            int stringResId;

            CharSequence positiveText = arguments.getCharSequence(KEY_POSITIVE_TEXT);
            if (positiveText == null && (stringResId = arguments.getInt(KEY_POSITIVE_TEXT_RES_ID, DEFAULT_RES_ID)) != DEFAULT_RES_ID) {
                positiveText = getString(stringResId);
            }
            OnClickListener positiveListener = arguments.getParcelable(KEY_POSITIVE_LISTENER);

            CharSequence neutralText = arguments.getCharSequence(KEY_NEUTRAL_TEXT);
            if (neutralText == null && (stringResId = arguments.getInt(KEY_NEUTRAL_TEXT_RES_ID, DEFAULT_RES_ID)) != DEFAULT_RES_ID) {
                neutralText = getString(stringResId);
            }
            OnClickListener neutralListener = arguments.getParcelable(KEY_NEUTRAL_LISTENER);

            CharSequence negativeText = arguments.getCharSequence(KEY_NEGATIVE_TEXT);
            if (negativeText == null && (stringResId = arguments.getInt(KEY_NEGATIVE_TEXT_RES_ID, DEFAULT_RES_ID)) != DEFAULT_RES_ID) {
                negativeText = getString(stringResId);
            }
            OnClickListener negativeListener = arguments.getParcelable(KEY_NEGATIVE_LISTENER);

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
