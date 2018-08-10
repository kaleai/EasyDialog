package kale.easydialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import kale.ui.view.dialog.BaseCustomDialog;
import kale.ui.view.dialog.BaseEasyDialog;
import kale.ui.view.dialog.EasyDialog;

/**
 * @author Kale
 * @date 2016/5/3
 *
 * 自定义builder参数的dialog
 */
public class CustomBuilderDialog extends BaseCustomDialog {

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
        protected EasyDialog createDialog() {
            CustomBuilderDialog dialog = new CustomBuilderDialog();
            dialog.setArguments(bundle); // 增加自己的bundle
            return dialog;
        }
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void bindViews(View root) {

    }

    @Override
    protected void modifyOriginBuilder(EasyDialog.Builder builder) {
        super.modifyOriginBuilder(builder);
        builder.setMessage("new message is " + getArguments().getInt(KEY_NUM));
    }

    @Override
    protected void setViews() {
        int i = getArguments().getInt(KEY_NUM);
        Toast.makeText(getContext(), "num: " + i, Toast.LENGTH_SHORT).show();
    }

}
