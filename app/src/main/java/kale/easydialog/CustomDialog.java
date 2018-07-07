package kale.easydialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import kale.ui.view.dialog.BaseCustomDialog;
import kale.ui.view.dialog.BaseEasyDialog;
import kale.ui.view.dialog.EasyDialog;

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
        return R.layout.custom_dialog_layout;
    }

    @Override
    protected void bindViews(View root) {
  
    }

    @Override
    protected EasyDialog.Builder resetOriginBuilder(EasyDialog.Builder builder) {
        super.resetOriginBuilder(builder);
        View titleView = LayoutInflater.from(getContext()).inflate(R.layout.custom_title_view, null, false);
        builder.setCustomTitle(titleView);
        return builder;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    protected void setViews() {
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable()); // 去除dialog的背景
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xffffffff)); // 白色背景
        final DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        final WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();

        int padding = getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin);
        layoutParams.width = dm.widthPixels - (padding * 2);
//        layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.dialog_height); // 200dp
        layoutParams.gravity = Gravity.TOP;
//        getDialog().getWindow().setAttributes(layoutParams); // 通过attr设置

        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg_custom_red);

        // 也可通过setLayout来设置
//        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);

        TextView title = findView(R.id.title_tv);
        title.setText(getBuildParams().title);
    }
}
