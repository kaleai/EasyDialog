package kale.easydialog;

import android.support.annotation.NonNull;
import android.widget.TextView;

import kale.ui.view.BaseEasyDialog;
import kale.ui.view.SimpleDialog;

/**
 * @author Jack Tony
 * @date 2015/8/27
 */
public class DemoSimpleDialog extends SimpleDialog {

    public static class Builder extends SimpleDialog.Builder {

        @NonNull
        @Override
        protected BaseEasyDialog createDialog() {
            return new DemoSimpleDialog();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // 在onStart中找到控件并进行设置
        TextView tv = (TextView) getDialog().findViewById(R.id.alertTitle);
        tv.setBackgroundColor(0xffff0000);
    }

}
