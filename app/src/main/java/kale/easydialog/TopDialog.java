package kale.easydialog;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import kale.ui.view.dialog.BaseCustomDialog;
import kale.ui.view.dialog.EasyDialog;

/**
 * @author Kale
 * @date 2016/11/15
 *
 * 自定义布局的dialog
 */

public class TopDialog extends BaseCustomDialog {

    private TextView titleTv;

    /**
     * 自定义布局
     */
    @Override
    protected int getLayoutResId() {
        return R.layout.custom_dialog_layout;
    }

    /**
     * title的布局，title的布局会显示在自定义布局的上方
     */
    @Override
    protected void modifyOriginBuilder(EasyDialog.Builder builder) {
        super.modifyOriginBuilder(builder);

        View titleView = LayoutInflater.from(getContext()).inflate(R.layout.custom_title_layout, null, false);
        builder.setCustomTitle(titleView); // 修改builder中的titleView
    }

    @Override
    protected void bindViews(View root) {
        titleTv = findView(R.id.title_tv);
    }

    @Override
    protected void setViews() {
        setLayout();
        setBackground();

        // 可从getDialogParams()得到builder中的所有参数
        titleTv.setText(getDialogParams().title);

        titleTv.setOnClickListener(v -> {
            FragmentTransaction ft = getFragmentManager().beginTransaction();

            ft.remove(TopDialog.this);
            ft.addToBackStack(null);

            EasyDialog.builder(getContext())
                    .setTitle("第二个对话框")
                    .setMessage("点击“返回”后会退回到之前的dialog")
                    .build()
                    .show(ft, "dialog");
        });
    }

    /**
     * 这时dialog已经初始化完毕
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 可通过attr设置：
     * getDialog().getWindow().setAttributes(layoutParams);
     *
     * 也可通过setLayout来设置：
     * getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
     */
    private void setLayout() {
        final DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        final WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();

        // 强制宽高
        int padding = getResources().getDimensionPixelOffset(R.dimen.dialog_padding);
        layoutParams.width = dm.widthPixels - (padding * 2);

        layoutParams.height = getResources().getDimensionPixelOffset(R.dimen.dialog_height);

        layoutParams.gravity = Gravity.TOP; // 设置展示的位置
    }

    /**
     * 强制取消背景，保持有透明
     */
    private void setBackground() {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable()); // 去除dialog的背景，即透明
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0xffffffff)); // 设置白色背景
//        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg_custom_red); // 设置背景
    }
}
