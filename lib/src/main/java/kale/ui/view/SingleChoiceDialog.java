package kale.ui.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import kale.ui.view.DialogInterface.OnClickListener;
import kale.ui.view.DialogInterface.OnItemClickListener;

/**
 * @author Jack Tony
 * @date 2015/8/11
 */
public class SingleChoiceDialog extends BaseEasyAlertDialog {

    private static final String KEY_ITEM_STR_ARR = "key_item_str_arr";

    private String[] mItemStrArr;
    

    private static final String KEY_DEFAULT_CHOICE_ARR = "key_default_choice_arr";

    private int mDefaultChoiceIndex;
    

    private static final String KEY_ITEM_CLICK_LISTENER = "key_item_click_listener";

    private OnItemClickListener mListener;
    
    
    public static class Builder extends BaseEasyAlertDialog.Builder<Builder> {

        public Builder setData(@NonNull String[] itemStrArr, int defaultChoiceIndex) {
            bundle.putStringArray(KEY_ITEM_STR_ARR, itemStrArr);
            bundle.putInt(KEY_DEFAULT_CHOICE_ARR, defaultChoiceIndex);
            return this;
        }

        public Builder setOnItemSelectedListener(OnItemClickListener listener) {
            bundle.putParcelable(KEY_ITEM_CLICK_LISTENER, listener);
            return this;
        }

        @NonNull
        @Override
        protected BaseEasyDialog createDialog() {
            return new SingleChoiceDialog();
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mItemStrArr = arguments.getStringArray(KEY_ITEM_STR_ARR);
            mDefaultChoiceIndex = arguments.getInt(KEY_DEFAULT_CHOICE_ARR, 0);
            mListener = arguments.getParcelable(KEY_ITEM_CLICK_LISTENER);
        }
    }

    @Override
    protected void setAlertBuilder(@NonNull AlertDialog.Builder builder) {
        // 默认选中了第一个，-1标识默认没选中
        builder.setSingleChoiceItems(mItemStrArr, mDefaultChoiceIndex, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mListener != null) {
                    mListener.onItemClick(dialog, which);
                }
            }
        });
    }

}
