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

    private static final String KEY_MESSAGE = "KEY_MESSAGE";

    private static final String KEY_PROGRESS_STYLE = "KEY_PROGRESS_STYLE";

    private static final String KEY_PROGRESS_NUMBER_FORMAT = "KEY_PROGRESS_NUMBER_FORMAT";

    private static final String KEY_PROGRESS_PERCENT_FORMAT = "KEY_PROGRESS_PERCENT_FORMAT";

    private static final String KEY_MAX = "KEY_MAX";

    private static final String KEY_PROGRESS_VAL = "KEY_PROGRESS_VAL";

    private static final String KEY_SECONDARY_PROGRESS_VAL = "KEY_SECONDARY_PROGRESS_VAL";

    private static final String KEY_INDETERMINATE = "KEY_INDETERMINATE";


    @NonNull
    @Override
    protected AlertDialog.Builder initBuilder() {
        // 这里不用build构建模式，这里的返回一个默认对象。其实是无意义的！
        return new AlertDialog.Builder(getActivity());
    }

    public static class Builder extends BaseEasyDialog.Builder {

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

        @Override
        public ProgressDialog create() {
            ProgressDialog dialog = new ProgressDialog();
            dialog.setArguments(bundle);
            return dialog;
        }
    }
    
    
    @Override
    protected void setBuilder(AlertDialog.Builder builder, Bundle arguments) {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        PDialog dialog = new PDialog(getActivity(), getTheme());

        Bundle arguments = getArguments();
        if (arguments != null) {
            CharSequence message = arguments.getCharSequence(KEY_MESSAGE);
            int progressStyle = arguments.getInt(KEY_PROGRESS_STYLE);
            String progressNumberFormat = arguments.getString(KEY_PROGRESS_NUMBER_FORMAT, "%1d/%2d");
            NumberFormat progressPercentFormat = (NumberFormat) arguments.getSerializable(KEY_PROGRESS_PERCENT_FORMAT);
            int max = arguments.getInt(KEY_MAX, 100);
            int progressVal = arguments.getInt(KEY_PROGRESS_VAL);
            int secondaryProgressVal = arguments.getInt(KEY_SECONDARY_PROGRESS_VAL);
            boolean indeterminate = arguments.getBoolean(KEY_INDETERMINATE);
        
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
        }
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
        if (getDialog() != null) {
            return ((PDialog) getDialog()).isIndeterminate();
        }
        return false;
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
