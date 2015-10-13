package kale.ui.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import java.text.NumberFormat;

/**
 * @author Jack Tony
 * @date 2015/10/13
 */
public class ProgressDialog extends BaseEasyDialog {

    /**
     * Creates a ProgressDialog with a circular, spinning progress
     * bar. This is the default.
     */
    public static final int STYLE_SPINNER = 0;

    /**
     * Creates a ProgressDialog with a horizontal progress bar.
     */
    public static final int STYLE_HORIZONTAL = 1;


    private CharSequence mMessage;

    private static final String KEY_MESSAGE = "KEY_MESSAGE";


    private int mProgressStyle = STYLE_SPINNER;

    private static final String KEY_PROGRESS_STYLE = "KEY_PROGRESS_STYLE";


    private String mProgressNumberFormat;

    private static final String KEY_PROGRESS_NUMBER_FORMAT = "KEY_PROGRESS_NUMBER_FORMAT";

    private NumberFormat mProgressPercentFormat;

    private static final String KEY_PROGRESS_PERCENT_FORMAT = "KEY_PROGRESS_PERCENT_FORMAT";

    private int mMax;

    private static final String KEY_MAX = "KEY_MAX";

    private int mProgressVal;

    private static final String KEY_PROGRESS_VAL = "KEY_PROGRESS_VAL";

    private int mSecondaryProgressVal;

    private static final String KEY_SECONDARY_PROGRESS_VAL = "KEY_SECONDARY_PROGRESS_VAL";

    private int mIncrementDiff;

    private static final String KEY_INCREMENT_DIFF = "KEY_INCREMENT_DIFF";

    private int mIncrementSecondaryDiff;

    private static final String KEY_INCREMENT_SECONDARY_DIFF = "KEY_INCREMENT_SECONDARY_DIFF";

    private boolean mIndeterminate;

    private static final String KEY_INDETERMINATE = "KEY_INDETERMINATE";

    
    @NonNull
    @Override
    protected AlertDialog.Builder initBuilder() {
        // 这里不用build构建模式，这里的返回一个默认对象。其实是无意义的！
        return new AlertDialog.Builder(getActivity());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mMessage = savedInstanceState.getCharSequence(KEY_MESSAGE);
        mProgressStyle = savedInstanceState.getInt(KEY_PROGRESS_STYLE, STYLE_SPINNER);
        mProgressNumberFormat = savedInstanceState.getString(KEY_PROGRESS_NUMBER_FORMAT);
        mProgressPercentFormat = (NumberFormat) savedInstanceState.getSerializable(KEY_PROGRESS_PERCENT_FORMAT);
        mMax = savedInstanceState.getInt(KEY_MAX, 100);
        mProgressVal = savedInstanceState.getInt(KEY_PROGRESS_VAL);
        mSecondaryProgressVal = savedInstanceState.getInt(KEY_SECONDARY_PROGRESS_VAL);
        mIncrementDiff = savedInstanceState.getInt(KEY_INCREMENT_DIFF);
        mIncrementSecondaryDiff = savedInstanceState.getInt(KEY_INCREMENT_SECONDARY_DIFF);
        mIndeterminate = savedInstanceState.getBoolean(KEY_INDETERMINATE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(KEY_MESSAGE, mMessage);
        outState.putInt(KEY_PROGRESS_STYLE, mProgressStyle);
        outState.putString(KEY_PROGRESS_NUMBER_FORMAT, mProgressNumberFormat);
        outState.putSerializable(KEY_PROGRESS_PERCENT_FORMAT, mProgressPercentFormat);
        outState.putInt(KEY_MAX, mMax);
        outState.putInt(KEY_PROGRESS_VAL, mProgressVal);
        outState.putInt(KEY_SECONDARY_PROGRESS_VAL, mSecondaryProgressVal);
        outState.putInt(KEY_INCREMENT_DIFF, mIncrementDiff);
        outState.putInt(KEY_INCREMENT_SECONDARY_DIFF, mIncrementSecondaryDiff);
        outState.putBoolean(KEY_INDETERMINATE, mIndeterminate);
    }

    @Override
    protected void setBuilder(AlertDialog.Builder builder, Bundle savedInstanceState) {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        PDialog dialog = new PDialog(getContext(), getTheme());
        dialog.setTitle(getTitle());
        if (!TextUtils.isEmpty(mMessage)) {
            dialog.setMessage(mMessage);
        }
        dialog.setProgressStyle(mProgressStyle);
        dialog.setProgressNumberFormat(mProgressNumberFormat);
        dialog.setProgressPercentFormat(mProgressPercentFormat);
        dialog.setMax(mMax);
        dialog.setProgress(mProgressVal);
        dialog.setSecondaryProgress(mSecondaryProgressVal);
        dialog.incrementProgressBy(mIncrementDiff);
        dialog.incrementSecondaryProgressBy(mIncrementSecondaryDiff);
        dialog.setIndeterminate(mIndeterminate);
        dialog.setCancelable(isCancelable());
        dialog.setOnCancelListener(getOnCancelListener());
        dialog.setOnDismissListener(getOnDismissListener());
        
        return dialog;
    }

    public void setMessage(@NonNull CharSequence message) {
        mMessage = message;
    }

    public void setProgress(int value) {
        mProgressVal = value;
    }

    public void setSecondaryProgress(int secondaryProgress) {
        mSecondaryProgressVal = secondaryProgress;
    }

    public int getProgress() {
        return mProgressVal;
    }

    public int getSecondaryProgress() {
        return mSecondaryProgressVal;
    }

    public int getMax() {
        return mMax;
    }

    public void setMax(int max) {
        mMax = max;
    }

    public void incrementProgressBy(int diff) {
        if (getDialog() != null) {
            ((PDialog) getDialog()).incrementProgressBy(diff);
        }
        mIncrementDiff = diff;
    }

    public void incrementSecondaryProgressBy(int diff) {
        mIncrementSecondaryDiff = diff;
    }

    /*public void setProgressDrawable(Drawable d) {
        mProgressDrawable = d;
    }

    public void setIndeterminateDrawable(Drawable d) {
        mIndeterminateDrawable = d;
    }*/

    public void setIndeterminate(boolean indeterminate) {
        mIndeterminate = indeterminate;
    }

    public boolean isIndeterminate() {
        return mIndeterminate;
    }


    public void setProgressStyle(int style) {
        mProgressStyle = style;
    }

    /**
     * Change the format of the small text showing current and maximum units
     * of progress.  The default is "%1d/%2d".
     * Should not be called during the number is progressing.
     *
     * @param format A string passed to {@link String#format String.format()};
     *               use "%1d" for the current number and "%2d" for the maximum.  If null,
     *               nothing will be shown.
     */
    public void setProgressNumberFormat(String format) {
        mProgressNumberFormat = format;
    }

    /**
     * Change the format of the small text showing the percentage of progress.
     * The default is
     * {@link NumberFormat#getPercentInstance() NumberFormat.getPercentageInstnace().}
     * Should not be called during the number is progressing.
     *
     * @param format An instance of a {@link NumberFormat} to generate the
     *               percentage text.  If null, nothing will be shown.
     */
    public void setProgressPercentFormat(NumberFormat format) {
        mProgressPercentFormat = format;
    }

}
