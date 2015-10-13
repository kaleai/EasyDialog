package kale.ui.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import kale.ui.view.DialogInterface.OnMultiChoiceClickListener;

/**
 * @author Jack Tony
 * @date 2015/8/11
 */
public class MultiChoiceDialog extends BaseEasyAlertDialog {

    private String[] mItemStrArr;

    private static final String KEY_ITEM_STR_ARR = "KEY_ITEM_STR_ARR";

    private boolean[] mDefaultChoiceArr;

    private static final String KEY_DEFAULT_CHOICE_ARR = "KEY_DEFAULT_CHOICE_ARR";

    private OnMultiChoiceClickListener mListener;

    private static final String KEY_MULTI_CHOICE_LISTENER = "KEY_MULTI_CHOICE_LISTENER";

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        mItemStrArr = savedInstanceState.getStringArray(KEY_ITEM_STR_ARR);
        mDefaultChoiceArr = savedInstanceState.getBooleanArray(KEY_DEFAULT_CHOICE_ARR);
        mListener = (OnMultiChoiceClickListener) savedInstanceState.getSerializable(KEY_MULTI_CHOICE_LISTENER);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray(KEY_ITEM_STR_ARR, mItemStrArr);
        outState.putBooleanArray(KEY_DEFAULT_CHOICE_ARR, mDefaultChoiceArr);
        outState.putSerializable(KEY_MULTI_CHOICE_LISTENER, mListener);
    }

    @Override
    protected void setBuilder(AlertDialog.Builder builder) {
        if (mItemStrArr == null) {
            throw new IllegalArgumentException("Item's String Array is null!");
        }
        builder.setMultiChoiceItems(mItemStrArr, mDefaultChoiceArr, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (mListener != null) {
                    mListener.onClick(dialog, which, isChecked);
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

    public void setData(@NonNull String[] itemStrArr, @NonNull boolean[] defaultChoiceArr) {
        mItemStrArr = itemStrArr;
        mDefaultChoiceArr = defaultChoiceArr;
    }

    public void setOnMultiChoiceClickListener(OnMultiChoiceClickListener listener) {
        mListener = listener;
    }

}
