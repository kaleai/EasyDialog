package kale.ui.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import kale.ui.view.DialogInterface.OnMultiChoiceClickListener;

/**
 * @author Jack Tony
 * @date 2015/8/11
 */
public class MultiChoiceDialog extends BaseEasyAlertDialog {

    private static final String KEY_ITEM_STR_ARR = "key_item_str_arr";

    private String[] mItemStrArr;
    

    private static final String KEY_DEFAULT_CHOICE_ARR = "key_default_choice_arr";

    private boolean[] mDefaultChoiceArr;
    

    private static final String KEY_MULTI_CHOICE_LISTENER = "key_multi_choice_listener";

    private OnMultiChoiceClickListener mListener;
    

    public static class Builder extends BaseEasyAlertDialog.Builder<Builder> {

        public Builder setData(@NonNull String[] itemStrArr, @NonNull boolean[] defaultChoiceArr) {
            bundle.putStringArray(KEY_ITEM_STR_ARR, itemStrArr);
            bundle.putBooleanArray(KEY_DEFAULT_CHOICE_ARR, defaultChoiceArr);
            return this;
        }

        public Builder setOnMultiChoiceClickListener(OnMultiChoiceClickListener listener) {
            bundle.putParcelable(KEY_MULTI_CHOICE_LISTENER, listener);
            return this;
        }

        @NonNull
        @Override
        protected BaseEasyDialog createDialog() {
            return new MultiChoiceDialog();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mItemStrArr = arguments.getStringArray(KEY_ITEM_STR_ARR);
            mDefaultChoiceArr = arguments.getBooleanArray(KEY_DEFAULT_CHOICE_ARR);
            mListener = arguments.getParcelable(KEY_MULTI_CHOICE_LISTENER);
        }
    }

    @Override
    protected void setAlertBuilder(@NonNull AlertDialog.Builder builder) {
        builder.setMultiChoiceItems(mItemStrArr, mDefaultChoiceArr, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (mListener != null) {
                    mListener.onClick(dialog, which, isChecked);
                }
            }
        });
    }

}
