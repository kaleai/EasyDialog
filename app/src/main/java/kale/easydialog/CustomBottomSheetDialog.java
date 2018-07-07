package kale.easydialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import kale.ui.view.dialog.BaseCustomDialog;
import kale.ui.view.dialog.BaseEasyDialog;
import kale.ui.view.dialog.EasyDialog;

/**
 * @author Kale
 * @date 2017/6/26
 */
public class CustomBottomSheetDialog extends BaseCustomDialog {

    public static class Builder extends BaseEasyDialog.Builder<Builder> {

        public Builder(@NonNull Context context) {
            super(context);
        }

        @NonNull
        @Override
        protected EasyDialog createDialog() {
            return new CustomBottomSheetDialog();
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
