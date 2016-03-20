package kale.ui.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import java.text.NumberFormat;

/**
 * @author Jack Tony
 * @date 2015/10/13
 */
public class ProgressDialog extends BaseEasyDialog {

    private static final String KEY_MESSAGE = "key_message";

    private static final String KEY_PROGRESS_STYLE = "key_progress_style";

    private static final String KEY_PROGRESS_NUMBER_FORMAT = "key_progress_number_format";

    private static final String KEY_PROGRESS_PERCENT_FORMAT = "key_progress_percent_format";

    private static final String KEY_MAX = "key_max";

    private static final String KEY_PROGRESS_VAL = "key_progress_val";

    private static final String KEY_SECONDARY_PROGRESS_VAL = "key_secondary_progress_val";

    private static final String KEY_INDETERMINATE = "key_indeterminate";


    private CharSequence message;

    private int progressStyle;

    private String progressNumberFormat;

    private NumberFormat progressPercentFormat;

    private int max;

    private int progressVal;

    private int secondaryProgressVal;

    private boolean indeterminate;

    
    public static class Builder extends BaseEasyDialog.Builder<Builder> {

        public Builder(boolean isCircular) {
            bundle.putInt(KEY_PROGRESS_STYLE, isCircular ? PDialog.STYLE_SPINNER : PDialog.STYLE_HORIZONTAL);
        }

        public Builder setMessage(@NonNull CharSequence message) {
            bundle.putCharSequence(KEY_MESSAGE, message);
            return this;
        }

        public Builder setMax(int max) {
            bundle.putInt(KEY_MAX, max);
            return this;
        }

        public Builder setProgress(int value) {
            bundle.putInt(KEY_PROGRESS_VAL, value);
            return this;
        }

        public Builder setSecondaryProgress(int secondaryProgress) {
            bundle.putInt(KEY_SECONDARY_PROGRESS_VAL, secondaryProgress);
            return this;
        }

        public Builder setIndeterminate(boolean indeterminate) {
            bundle.putBoolean(KEY_INDETERMINATE, indeterminate);
            return this;
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
        public Builder setProgressNumberFormat(String format) {
            bundle.putString(KEY_PROGRESS_NUMBER_FORMAT, format);
            return this;
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
        public Builder setProgressPercentFormat(NumberFormat format) {
            bundle.putSerializable(KEY_PROGRESS_PERCENT_FORMAT, format);
            return this;
        }


        @NonNull
        @Override
        protected BaseEasyDialog createDialog() {
            return new ProgressDialog();
        }
    }

    @NonNull
    @Override
    protected AlertDialog.Builder initBuilder() {
        // 这里不用build构建模式，这里的返回一个默认对象。其实是无意义的！
        return new AlertDialog.Builder(getActivity());
    }

    @Override
    protected void setBuilder(@NonNull AlertDialog.Builder builder) {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            message = arguments.getCharSequence(KEY_MESSAGE);
            progressStyle = arguments.getInt(KEY_PROGRESS_STYLE);
            progressNumberFormat = arguments.getString(KEY_PROGRESS_NUMBER_FORMAT, "%1d/%2d");
            progressPercentFormat = (NumberFormat) arguments.getSerializable(KEY_PROGRESS_PERCENT_FORMAT);
            max = arguments.getInt(KEY_MAX, 100);
            progressVal = arguments.getInt(KEY_PROGRESS_VAL);
            secondaryProgressVal = arguments.getInt(KEY_SECONDARY_PROGRESS_VAL);
            indeterminate = arguments.getBoolean(KEY_INDETERMINATE);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        PDialog dialog = new PDialog(getActivity(), getTheme());
        dialog.setTitle(getTitle());
        if (!TextUtils.isEmpty(message)) {
            dialog.setMessage(message);
        }
        dialog.setCancelable(isCancelable());
        dialog.setOnCancelListener(getOnCancelListener());
        dialog.setOnDismissListener(getOnDismissListener());

        dialog.setProgressStyle(progressStyle);
        dialog.setMax(max);
        dialog.setIndeterminate(indeterminate);
        dialog.setProgressNumberFormat(progressNumberFormat);
        dialog.setProgressPercentFormat(progressPercentFormat != null ? progressPercentFormat : NumberFormat.getPercentInstance());
        dialog.setProgress(progressVal);
        dialog.setSecondaryProgress(secondaryProgressVal);
        return dialog;
    }

    public int getProgress() {
        if (getDialog() != null) {
            return ((PDialog) getDialog()).getProgress();
        }
        return -1;
    }

    public int getSecondaryProgress() {
        if (getDialog() != null) {
            return ((PDialog) getDialog()).getSecondaryProgress();
        }
        return -1;
    }

    public int getMax() {
        if (getDialog() != null) {
            return ((PDialog) getDialog()).getMax();
        }
        return -1;
    }

    public boolean isIndeterminate() {
        return getDialog() != null && ((PDialog) getDialog()).isIndeterminate();
    }

    public void incrementProgressBy(int diff) {
        if (getDialog() != null) {
            ((PDialog) getDialog()).incrementProgressBy(diff);
        }
    }

    public void incrementSecondaryProgressBy(int diff) {
        if (getDialog() != null) {
            ((PDialog) getDialog()).incrementSecondaryProgressBy(diff);
        }
    }

}
