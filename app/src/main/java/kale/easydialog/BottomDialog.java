package kale.easydialog;

import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import kale.ui.view.dialog.BaseCustomDialog;

/**
 * @author Kale
 * @date 2017/6/26
 */
public class BottomDialog extends BaseCustomDialog {

    private BottomSheetBehavior mBehavior;
    
    @Override
    protected int getLayoutResId() {
        return R.layout.custom_dialog_layout;
    }

    @Override
    protected void bindViews(View root) {
        FrameLayout bottomSheet = root.findViewById(android.support.design.R.id.design_bottom_sheet);
        mBehavior = BottomSheetBehavior.from(bottomSheet);
    }

    @Override
    protected void setViews() {
        TextView textView = findView(R.id.message_tv);

        textView.setOnClickListener(view -> {
//                getDialog().cancel(); // 如果要触发cancel，必须手动触发
            dismiss();
        });
        textView.setText(getDialogParams().message);
    }
}
