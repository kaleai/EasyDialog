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
 * 自定义builder的dialog
 */
public class NewBuilderDialog extends BaseCustomDialog {

    public static final String KEY_AGE = "KEY_AGE",KEY_NAME="KEY_NAME";

    /**
     * 继承自{@link EasyDialog.Builder}以扩展builder
     */
    public static class Builder extends BaseEasyDialog.Builder<Builder> {

        private Bundle bundle = new Bundle();

        public Builder(@NonNull Context context) {
            super(context);
        }

        public Builder setAge(int age) {
            bundle.putInt(KEY_AGE, age);
            return this;
        }

        public Builder setName(String name) {
            bundle.putString(KEY_NAME, name);
            return this;
        }

        @NonNull
        @Override
        protected EasyDialog createDialog() {
            NewBuilderDialog dialog = new NewBuilderDialog();
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
        Bundle arguments = getArguments();
        String str = "name: " + arguments.getString(KEY_NAME) + ", age: " + arguments.getInt(KEY_AGE);
        builder.setMessage("修改后的message是：\n" + str);
    }

    @Override
    protected void setViews() {
        int age = getArguments().getInt(KEY_AGE);
        Toast.makeText(getContext(), "age: " + age, Toast.LENGTH_SHORT).show();
    }

}
