package kale.ui.view;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.AdapterView;

/**
 * @author Jack Tony
 * @date 2015/8/11
 */
public class SingleChoiceDialog extends BaseEasyDialog {

    private String[] mItemStrArr;

    private int mDefaultChoiceIndex = 0;

    private AdapterView.OnItemClickListener mListener;

    @Override
    protected AlertDialog.Builder initBuilder(AlertDialog.Builder builder) {
        if (mItemStrArr == null) {
            throw new IllegalArgumentException("itemStrArr is null!");
        }
        // 默认选中了第一个
        builder.setSingleChoiceItems(mItemStrArr, mDefaultChoiceIndex, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mListener != null) {
                    mListener.onItemClick(null, null, which, which);
                }
            }
        });
        return builder;
    }

    public void setData(@NonNull String[] itemStrArr, int defaultChoiceIndex) {
        mItemStrArr = itemStrArr;
        mDefaultChoiceIndex = defaultChoiceIndex;
    }

    public void setOnItemSelectedListener(AdapterView.OnItemClickListener listener) {
        mListener = listener;
    }

}
