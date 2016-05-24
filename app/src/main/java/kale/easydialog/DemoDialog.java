package kale.easydialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import kale.ui.view.BaseEasyDialog;

/**
 * @author Kale
 * @date 2016/5/3
 */
public class DemoDialog extends BaseEasyDialog {

    /**
     * 继承自{@link kale.ui.view.BaseEasyDialog.Builder}以扩展builder
     */
    public static class Builder extends BaseEasyDialog.Builder<Builder> {

        public static final String KEY_NUM = "KEY_NUM";

        private Bundle bundle = new Bundle();

        /**
         * 扩展一个方法来传入参数
         */
        public Builder setSomeArg(int i) {
            bundle.putInt(KEY_NUM, i);
            return this;
        }

        @NonNull
        @Override
        protected BaseEasyDialog createDialog() {
            return new DemoDialog();
        }

        @Override
        protected void addArgs(BaseEasyDialog dialog) {
            super.addArgs(dialog); // 保留父类的bundle
            dialog.addArguments(bundle); // 增加自己的bundle
        }
    }

    /**
     * 配置alertDialog的builder
     */
    @Override
    protected void configDialogBuilder(AlertDialog.Builder builder) {
        super.configDialogBuilder(builder);
        
        builder.setMessage("message");
    }

    @Override
    protected int getLayoutResId() {
        return super.getLayoutResId();
    }

    @Override
    protected void bindViews(Dialog dialog) {
        super.bindViews(dialog);
    }

    @Override
    protected void setViews() {
        super.setViews();
    }
}
