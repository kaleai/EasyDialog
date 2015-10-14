package kale.ui.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * @author Jack Tony
 * @date 2015/8/11
 */
public class SimpleDialog extends BaseEasyAlertDialog {

    private static final String KEY_MESSAGE = "KEY_MESSAGE";

    public static class Builder extends BaseEasyAlertDialog.Builder {

        public Builder setMessage(@NonNull String message) {
            bundle.putString(KEY_MESSAGE, message);
            return Builder.this;
        }

        @NonNull
        @Override
        protected BaseEasyDialog createDialog() {
            return new SimpleDialog();
        }
    }

    @Override
    protected void setAlertBuilder(AlertDialog.Builder builder, @Nullable Bundle arguments) {
        if (arguments != null) {
            CharSequence message = arguments.getCharSequence(KEY_MESSAGE);
            if (!TextUtils.isEmpty(message)) {
                builder.setMessage(message);
            }
        }
    }

}
