package kale.easydialog;

import android.view.View;
import android.widget.TextView;

import kale.ui.view.dialog.BaseCustomDialog;

/**
 * @author Kale
 * @date 2017/6/26
 */
public class BottomSheetDialog extends BaseCustomDialog {

    @Override
    protected int getLayoutResId() {
        return R.layout.custom_dialog_layout;
    }

    @Override
    protected void bindViews(View root) {

    }

    @Override
    protected void setViews() {
        findView(R.id.image_text_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getDialog().cancel(); // 如果要触发cancel，必须写上这个
                dismiss();
            }
        });
        ((TextView) findView(R.id.message_tv)).setText(getDialogParams().message);
    }
}
