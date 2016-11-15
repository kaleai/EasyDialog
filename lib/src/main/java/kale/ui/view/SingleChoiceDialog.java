package kale.ui.view;


import com.lzh.courier.annoapi.Field;
import com.lzh.courier.annoapi.Params;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import lombok.Setter;

/**
 * @author Jack Tony
 * @date 2015/8/11
 */
@Params(fields = {
        @Field(name = "itemStrArr", type = String[].class),
        @Field(name = "defaultChoiceIndex", type = int.class)
},inherited = false)
public class SingleChoiceDialog extends BaseEasyDialog {

    public static class Builder extends BaseEasyDialog.Builder<Builder> {

        private DialogInterface.OnClickListener onItemClickListener;

        private SingleChoiceDialog_Builder builder = SingleChoiceDialog_Builder.create();

        public Builder setData(@NonNull String[] itemStrArr) {
            return setData(itemStrArr, -1);
        }

        public Builder setData(@NonNull String[] itemStrArr, int defaultChoiceIndex) {
            builder.setItemStrArr(itemStrArr);
            builder.setDefaultChoiceIndex(defaultChoiceIndex);
            return this;
        }

        public Builder setOnItemSelectedListener(DialogInterface.OnClickListener listener) {
            onItemClickListener = listener;
            return this;
        }

        @NonNull
        @Override
        protected BaseEasyDialog createDialog() {
            SingleChoiceDialog dialog = new SingleChoiceDialog();
            dialog.addArguments(builder.createBundle());
            dialog.setOnItemClickListener(onItemClickListener);
            return dialog;
        }

    }

    @Setter
    private DialogInterface.OnClickListener onItemClickListener;

    @Override
    protected void configDialogBuilder(AlertDialog.Builder builder) {
        super.configDialogBuilder(builder);
        SingleChoiceDialog_Builder.ArgsData data = SingleChoiceDialog_Builder.getArguments(this);

        // 默认选中了第一个，-1标识默认没选中
        final DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(dialog, which);
                }
            }
        };
        // -1表示默认没有选中项目
        if (data.getDefaultChoiceIndex() == -1) {
            builder.setItems(data.getItemStrArr(), listener);
        } else {
            builder.setSingleChoiceItems(data.getItemStrArr(), data.getDefaultChoiceIndex(), listener);
        }
    }

}
