package kale.ui.view;

import com.lzh.courier.annoapi.Field;
import com.lzh.courier.annoapi.Params;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import lombok.Getter;

/**
 * @author Jack Tony
 * @date 2015/8/11
 */
@Params(fields = {
        @Field(name = "message", type = CharSequence.class),
        @Field(name = "messageResId", type = int.class)
},inherited = false)
public class SimpleDialog extends BaseEasyDialog {

    public static class Builder extends BaseEasyDialog.Builder<Builder> {

        private SimpleDialog_Builder builder = SimpleDialog_Builder.create();

        public Builder setMessage(@NonNull CharSequence message) {
            builder.setMessage(message);
            return this;
        }

        public Builder setMessage(@StringRes int message) {
            builder.setMessageResId(message);
            return this;
        }

        @NonNull
        @Override
        protected BaseEasyDialog createDialog() {
            SimpleDialog dialog = new SimpleDialog();
            dialog.addArguments(builder.createBundle());
            return dialog;
        }

    }

    @Getter
    private CharSequence message;

    @Override
    protected void configDialogBuilder(AlertDialog.Builder builder) {
        super.configDialogBuilder(builder);
        SimpleDialog_Builder.ArgsData data = SimpleDialog_Builder.getArguments(this);
        message = getText(data.getMessage(), data.getMessageResId());
        if (message != null) {
            builder.setMessage(message);
        }
    }

}
