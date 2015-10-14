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

    private static final String KEY_ITEM_STR_ARR = "KEY_ITEM_STR_ARR";

    private static final String KEY_DEFAULT_CHOICE_ARR = "KEY_DEFAULT_CHOICE_ARR";
    
    private static final String KEY_ITEM_CLICK_LISTENER = "KEY_ITEM_CLICK_LISTENER";

    public static class Builder extends BaseEasyAlertDialog.Builder {
        
        public Builder setData(@NonNull String[] itemStrArr, int defaultChoiceIndex) {
            bundle.putStringArray(KEY_ITEM_STR_ARR, itemStrArr);
            bundle.putInt(KEY_DEFAULT_CHOICE_ARR, defaultChoiceIndex);
            return this;
        }

        public Builder setOnItemSelectedListener(OnItemClickListener listener) {
            bundle.putSerializable(KEY_ITEM_CLICK_LISTENER, listener);
            return this;
        }

        @Override
        public SingleChoiceDialog create() {
            SingleChoiceDialog dialog = new SingleChoiceDialog();
            dialog.setArguments(bundle);
            return dialog;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void setAlertBuilder(AlertDialog.Builder builder, @Nullable Bundle arguments) {
        if (arguments != null) {
            String[] itemStrArr = arguments.getStringArray(KEY_ITEM_STR_ARR);
            int defaultChoiceIndex = arguments.getInt(KEY_DEFAULT_CHOICE_ARR, 0);
            final OnItemClickListener mListener = (OnItemClickListener) arguments.getSerializable(KEY_ITEM_CLICK_LISTENER);
            
            if (itemStrArr == null) {
                throw new IllegalArgumentException("Item's String Array is null!");
            }
            
            // 默认选中了第一个
            builder.setSingleChoiceItems(itemStrArr, defaultChoiceIndex, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mListener != null) {
                        mListener.onItemClick(dialog, which, which);
                    }
                }
            });
        }
    }

}
