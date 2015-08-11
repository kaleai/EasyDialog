package kale.ui.view;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * @author Jack Tony
 * @date 2015/8/11
 */
public class MultiChoiceDialog extends BaseEasyDialog{

    private String[] mItemStrArr;
    
    private boolean[] mDefaultChoiceArr;
    
    private DialogInterface.OnMultiChoiceClickListener mListener;
    
    @Override
    protected AlertDialog.Builder initBuilder(AlertDialog.Builder builder) {
        if (mItemStrArr == null) {
            throw new IllegalArgumentException("itemStrArr is null!");
        }
        builder.setMultiChoiceItems(mItemStrArr, mDefaultChoiceArr, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (mListener != null) {
                    mListener.onClick(dialog, which, isChecked);
                }
            }
        });
        return builder;
    }

    public void setData(@NonNull String[] itemStrArr, @NonNull boolean[] defaultChoiceArr) {
        mItemStrArr = itemStrArr;
        mDefaultChoiceArr = defaultChoiceArr;
    }

    public void setOnMultiChoiceClickListener(DialogInterface.OnMultiChoiceClickListener listener) {
        mListener = listener;
    }

}
