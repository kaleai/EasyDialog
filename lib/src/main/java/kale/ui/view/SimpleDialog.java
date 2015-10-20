package kale.ui.view;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * @author Jack Tony
 * @date 2015/8/11
 */
public class SimpleDialog extends BaseEasyAlertDialog {

    private static final String KEY_MESSAGE = "key_message";

    private static final String KEY_MESSAGE_RES_ID = "key_message_res_id";

    private CharSequence mMessage;

    public static class Builder extends BaseEasyAlertDialog.Builder {

        public Builder setMessage(@NonNull String message) {
            bundle.putString(KEY_MESSAGE, message);
            return Builder.this;
        }

        public Builder setMessage(@StringRes int message) {
            bundle.putInt(KEY_MESSAGE_RES_ID, message);
            return Builder.this;
        }

        @NonNull
        @Override
        protected BaseEasyDialog createDialog() {
            return new SimpleDialog();
        }
    }

    @Override
    @CallSuper
    protected void setAlertBuilder(AlertDialog.Builder builder, @Nullable Bundle arguments) {
        if (arguments != null) {
            mMessage = arguments.getCharSequence(KEY_MESSAGE);
            
            int stringResId;
            if (mMessage == null && (stringResId = arguments.getInt(KEY_MESSAGE_RES_ID, DEFAULT_RES_ID)) != DEFAULT_RES_ID) {
                mMessage = getString(stringResId);
            }

            if (!TextUtils.isEmpty(mMessage)) {
                builder.setMessage(mMessage);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMessage = null;
    }

    public CharSequence getMessage() {
        return mMessage;
    }
}
