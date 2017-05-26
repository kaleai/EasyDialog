package kale.easydialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import kale.ui.view.BaseCustomDialog;
import kale.ui.view.BaseEasyDialog;

/**
 * @author Kale
 * @date 2016/11/15
 */

public class CustomDialog extends BaseCustomDialog {

    public static class Builder extends BaseEasyDialog.Builder<Builder> {

        Builder(@NonNull Context context) {
            super(context);
        }

        @NonNull
        @Override
        protected CustomDialog createDialog() {
            return new CustomDialog();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.demo_dialog_layout02;
    }

    @Override
    protected void bindViews(View root) {
        
    }

    @Override
    protected void setViews() {
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable()); // 去除dialog的背景
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xffffffff)); // 白色背景
        final DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        final WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width = dm.widthPixels;
        layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.dialog_height); // 200dp
        layoutParams.gravity = Gravity.BOTTOM;
        getDialog().getWindow().setAttributes(layoutParams); // 通过attr设置

        // 通过setLayout来设置
//        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
    }
}
