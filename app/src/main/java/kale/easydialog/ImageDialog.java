package kale.easydialog;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import kale.ui.view.dialog.BaseCustomDialog;

/**
 * @author Kale
 * @date 2018/8/13
 */
public class ImageDialog extends BaseCustomDialog {

    @Override
    protected int getLayoutResId() {
        return R.layout.custom_dialog_image_layout;
    }

    @Override
    protected void modifyOriginBuilder(Builder builder) {
        super.modifyOriginBuilder(builder);
        builder.setPositiveButton(null, null);
    }

    @Override
    protected void bindViews(View root) {
        Button button = root.findViewById(R.id.button);
        button.setText(getDialogParams().positiveText);
        button.setOnClickListener(v -> {
            getPositiveListener().onClick(getDialog(), DialogInterface.BUTTON_POSITIVE);
            dismiss();
        });
    }

    @Override
    protected void setViews() {

    }

}
