package kale.easydialog;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;

import kale.ui.view.dialog.BaseCustomDialog;
import kale.ui.view.dialog.EasyDialog;

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
            // 手动调用外层回调
            getPositiveListener().onClick(getDialog(), DialogInterface.BUTTON_POSITIVE);
            // 关闭对话框
            dismiss();
            // 展示新的一个对话框
            showDialog();
        });
    }
    
    private void showDialog(){
        EasyDialog.Builder builder = EasyDialog.builder(getActivity(),android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle("Dynamic Style Dialog")
                .setIcon(R.drawable.kale)
                .setMessage("上半部分是透明背景的样式")
                .build()
                .show(getFragmentManager());
    }

    @Override
    protected void setViews() {

    }

}
