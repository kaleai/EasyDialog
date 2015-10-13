package kale.ui.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * @author Jack Tony
 * @date 2015/8/11
 */
public class SimpleDialog extends BaseEasyAlertDialog {

    private String mMessage;

    private static final String KEY_MESSAGE = "KEY_MESSAGE";

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mMessage = savedInstanceState.getString(KEY_MESSAGE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_MESSAGE, mMessage);
    }

    @Override
    protected void setBuilder(AlertDialog.Builder builder) {
        if (!TextUtils.isEmpty(mMessage)) {
            builder.setMessage(mMessage);
        }
    }

    public void setMessage(@NonNull String message) {
        mMessage = message;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMessage = null;
    }
}
