package kale.easydialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import kale.ui.view.dialog.BaseEasyDialog;
import kale.ui.view.dialog.EasyDialog;

/**
 * @author Kale
 * @date 2016/5/3
 */
public class DemoDialog extends EasyDialog {

    public static final String KEY_NUM = "KEY_NUM";

    /**
     * 继承自{@link EasyDialog.Builder}以扩展builder
     */
    public static class Builder extends BaseEasyDialog.Builder<Builder> {

        private Bundle bundle = new Bundle();

        public Builder(@NonNull Context context) {
            super(context);
        }

        public Builder setSomeArg(int i) {
            bundle.putInt(KEY_NUM, i);
            return this;
        }

        @NonNull
        @Override
        protected DemoDialog createDialog() {
            DemoDialog dialog = new DemoDialog();
            dialog.setArguments(bundle); // 增加自己的bundle
            return dialog;
        }
    }

}
