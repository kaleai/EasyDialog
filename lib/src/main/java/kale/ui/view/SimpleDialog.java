package kale.ui.view;

import android.support.v7.app.AlertDialog;

/**
 * @author Jack Tony
 * @date 2015/8/11
 */
public class SimpleDialog extends BaseEasyDialog {

    private String mMessage;

    @Override
    protected AlertDialog.Builder initBuilder(AlertDialog.Builder builder) {
        if (mMessage != null) {
            builder.setMessage(mMessage);
        }
        return builder;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
