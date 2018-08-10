package kale.easydialog;

import android.view.View;
import android.widget.TextView;

import kale.ui.view.dialog.BaseCustomDialog;

/**
 * @author Kale
 * @date 2017/6/26
 */
public class BottomDialog extends BaseCustomDialog {

    @Override
    protected int getLayoutResId() {
        return R.layout.custom_dialog_layout;
    }

    @Override
    protected void bindViews(View root) {

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
