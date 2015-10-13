package kale.ui.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import kale.ui.view.DialogInterface.OnClickListener;
import kale.ui.view.DialogInterface.OnItemClickListener;

/**
 * @author Jack Tony
 * @date 2015/8/11
 */
public class SingleChoiceDialog extends BaseEasyAlertDialog {

    private String[] mItemStrArr;
    
    private static final String KEY_ITEM_STR_ARR = "KEY_ITEM_STR_ARR";
    
    private int mDefaultChoiceIndex = 0;
    
    private static final String KEY_DEFAULT_CHOICE_ARR = "KEY_DEFAULT_CHOICE_ARR";
    
    private OnItemClickListener mListener;

    private static final String KEY_ITEM_CLICK_LISTENER = "KEY_ITEM_CLICK_LISTENER";
    
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mItemStrArr = savedInstanceState.getStringArray(KEY_ITEM_STR_ARR);
        mDefaultChoiceIndex = savedInstanceState.getInt(KEY_DEFAULT_CHOICE_ARR);
        mListener = (OnItemClickListener) savedInstanceState.getSerializable(KEY_ITEM_CLICK_LISTENER);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray(KEY_ITEM_STR_ARR, mItemStrArr);
        outState.putInt(KEY_DEFAULT_CHOICE_ARR, mDefaultChoiceIndex);
        outState.putSerializable(KEY_ITEM_CLICK_LISTENER, mListener);
    }

    @Override
    protected void setBuilder(AlertDialog.Builder builder) {
        if (mItemStrArr == null) {
            throw new IllegalArgumentException("Item's String Array is null!");
        }
        // 默认选中了第一个
        builder.setSingleChoiceItems(mItemStrArr, mDefaultChoiceIndex, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mListener != null) {
                    mListener.onItemClick(dialog, which, which);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mItemStrArr = null;
        mListener = null;
    }

    public void setData(@NonNull String[] itemStrArr, int defaultChoiceIndex) {
        mItemStrArr = itemStrArr;
        mDefaultChoiceIndex = defaultChoiceIndex;
    }

    public void setOnItemSelectedListener(OnItemClickListener listener) {
        mListener = listener;
    }
    
}
