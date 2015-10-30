package kale.ui.view;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;

import kale.lib.easydialog.R;
import kale.ui.view.DialogInterface.OnClickListener;


/**
 * @author Jack Tony
 * @date 2015/8/11
 */
public abstract class BaseEasyAlertDialog extends BaseEasyDialog {

    private static final String KEY_POSITIVE_TEXT = "key_positive_text";

    private static final String KEY_POSITIVE_TEXT_RES_ID = "key_positive_text_res_id";

    private CharSequence mPositiveText;
    

    private static final String KEY_POSITIVE_LISTENER = "key_positive_listener";

    private OnClickListener mPositiveListener;
    

    private static final String KEY_NEUTRAL_TEXT = "key_neutral_text";

    private static final String KEY_NEUTRAL_TEXT_RES_ID = "key_neutral_text_res_id";

    private CharSequence mNeutralText;
    

    private static final String KEY_NEUTRAL_LISTENER = "key_neutral_listener";

    private OnClickListener mNeutralListener;
    

    private static final String KEY_NEGATIVE_TEXT = "key_negative_text";

    private static final String KEY_NEGATIVE_TEXT_RES_ID = "key_negative_text_res_id";

    private CharSequence mNegativeText;
    

    private static final String KEY_NEGATIVE_LISTENER = "key_negative_listener";

    private OnClickListener mNegativeListener;

    protected abstract static class Builder<T extends Builder> extends BaseEasyDialog.Builder<T> {

        public T setPositiveButton(CharSequence positiveText, OnClickListener listener) {
            bundle.putCharSequence(KEY_POSITIVE_TEXT, positiveText);
            bundle.putParcelable(KEY_POSITIVE_LISTENER, listener);
            return (T)this;
        }

        public T setPositiveButton(@StringRes int positiveText, OnClickListener listener) {
            bundle.putInt(KEY_POSITIVE_TEXT_RES_ID, positiveText);
            bundle.putParcelable(KEY_POSITIVE_LISTENER, listener);
            return (T)this;
        }

        public T setNeutralButton(CharSequence neutralText, OnClickListener listener) {
            bundle.putCharSequence(KEY_NEUTRAL_TEXT, neutralText);
            bundle.putParcelable(KEY_NEUTRAL_LISTENER, listener);
            return (T)this;
        }

        public T setNeutralButton(@StringRes int neutralText, OnClickListener listener) {
            bundle.putInt(KEY_NEUTRAL_TEXT_RES_ID, neutralText);
            bundle.putParcelable(KEY_NEUTRAL_LISTENER, listener);
            return (T)this;
        }

        public T setNegativeButton(CharSequence negativeText, OnClickListener listener) {
            bundle.putCharSequence(KEY_NEGATIVE_TEXT, negativeText);
            bundle.putParcelable(KEY_NEGATIVE_LISTENER, listener);
            return (T)this;
        }

        public T setNegativeButton(@StringRes int negativeText, OnClickListener listener) {
            bundle.putInt(KEY_NEGATIVE_TEXT_RES_ID, negativeText);
            bundle.putParcelable(KEY_NEGATIVE_LISTENER, listener);
            return (T)this;
        }
    }

    static int resolveDialogTheme(Context context, int resid) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.alertDialogTheme, outValue, true);
        return outValue.resourceId;
    }

    @NonNull
    @Override
    protected AlertDialog.Builder initBuilder() {
//        return new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
        return new AlertDialog.Builder(getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            int stringResId;

            mPositiveText = arguments.getCharSequence(KEY_POSITIVE_TEXT);
            if (mPositiveText == null && (stringResId = arguments.getInt(KEY_POSITIVE_TEXT_RES_ID, DEFAULT_RES_ID)) != DEFAULT_RES_ID) {
                mPositiveText = getString(stringResId);
            }
            mPositiveListener = arguments.getParcelable(KEY_POSITIVE_LISTENER);

            mNeutralText = arguments.getCharSequence(KEY_NEUTRAL_TEXT);
            if (mNeutralText == null && (stringResId = arguments.getInt(KEY_NEUTRAL_TEXT_RES_ID, DEFAULT_RES_ID)) != DEFAULT_RES_ID) {
                mNeutralText = getString(stringResId);
            }
            mNeutralListener = arguments.getParcelable(KEY_NEUTRAL_LISTENER);

            mNegativeText = arguments.getCharSequence(KEY_NEGATIVE_TEXT);
            if (mNegativeText == null && (stringResId = arguments.getInt(KEY_NEGATIVE_TEXT_RES_ID, DEFAULT_RES_ID)) != DEFAULT_RES_ID) {
                mNegativeText = getString(stringResId);
            }
            mNegativeListener = arguments.getParcelable(KEY_NEGATIVE_LISTENER);
        }
    }

    @Override
    protected void setBuilder(@NonNull AlertDialog.Builder builder) {
        if (mPositiveText != null) {
            builder.setPositiveButton(mPositiveText, mPositiveListener);
        }
        if (mNeutralText != null) {
            builder.setNeutralButton(mNeutralText, mNeutralListener);
        }
        if (mNegativeText != null) {
            builder.setNegativeButton(mNegativeText, mNegativeListener);
        }
        setAlertBuilder(builder);
    }

    protected abstract void setAlertBuilder(@NonNull AlertDialog.Builder builder);

}
