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
        @Field(name = "defaultChoiceIndex", type = boolean[].class)
}, inherited = false)
public class MultiChoiceDialog extends BaseEasyDialog {

    public static class Builder extends BaseEasyDialog.Builder<Builder> {

        private MultiChoiceDialog_Builder builder = MultiChoiceDialog_Builder.create();

        private DialogInterface.OnMultiChoiceClickListener multiChoiceListener;

        public Builder setData(@NonNull String[] itemStrArr, @NonNull boolean[] defaultChoiceArr) {
            builder.setItemStrArr(itemStrArr).setDefaultChoiceIndex(defaultChoiceArr);
            return this;
        }

        public Builder setOnMultiChoiceClickListener(DialogInterface.OnMultiChoiceClickListener listener) {
            multiChoiceListener = listener;
            return this;
        }

        @NonNull
        @Override
        protected BaseEasyDialog createDialog() {
            MultiChoiceDialog dialog = new MultiChoiceDialog();
            dialog.setMultiChoiceListener(multiChoiceListener);
            return dialog;
        }

        @Override
        protected void addArgs(BaseEasyDialog dialog) {
            super.addArgs(dialog);
            dialog.addArguments(builder.createBundle());
        }

    }

    @Setter
    private DialogInterface.OnMultiChoiceClickListener multiChoiceListener;

    @Override
    protected void configDialogBuilder(AlertDialog.Builder builder) {
        super.configDialogBuilder(builder);
        MultiChoiceDialog_Builder.ArgsData data = MultiChoiceDialog_Builder.getArguments(this);
        builder.setMultiChoiceItems(data.getItemStrArr(), data.getDefaultChoiceIndex(), new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (multiChoiceListener != null) {
                    multiChoiceListener.onClick(dialog, which, isChecked);
                }
            }
        });
    }

}
