package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @author Kale
 * @date 2016/11/22
 */

public class AlertController {

    public static class AlertParams {

        public Context mContext;

        public LayoutInflater mInflater;

        public int mIconId = 0;

        public Drawable mIcon;

        public int mIconAttrId = 0;

        public CharSequence mTitle;

        public View mCustomTitleView;

        public CharSequence mMessage;

        public CharSequence mPositiveButtonText;

        public DialogInterface.OnClickListener mPositiveButtonListener;

        public CharSequence mNegativeButtonText;

        public DialogInterface.OnClickListener mNegativeButtonListener;

        public CharSequence mNeutralButtonText;

        public DialogInterface.OnClickListener mNeutralButtonListener;

        public boolean mCancelable;

        public DialogInterface.OnCancelListener mOnCancelListener;

        public DialogInterface.OnDismissListener mOnDismissListener;

        public DialogInterface.OnKeyListener mOnKeyListener;

        public CharSequence[] mItems;

        public ListAdapter mAdapter;

        public DialogInterface.OnClickListener mOnClickListener;

        public int mViewLayoutResId;

        public View mView;

        public int mViewSpacingLeft;

        public int mViewSpacingTop;

        public int mViewSpacingRight;

        public int mViewSpacingBottom;

        public boolean mViewSpacingSpecified = false;

        public boolean[] mCheckedItems;

        public boolean mIsMultiChoice;

        public boolean mIsSingleChoice;

        public int mCheckedItem = -1;

        public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;

        public Cursor mCursor;

        public String mLabelColumn;

        public String mIsCheckedColumn;

        public boolean mForceInverseBackground;

        public AdapterView.OnItemSelectedListener mOnItemSelectedListener;

        public AlertParams.OnPrepareListViewListener mOnPrepareListViewListener;

        public boolean mRecycleOnMeasure = true;


        /**
         * Interface definition for a callback to be invoked before the ListView
         * will be bound to an adapter.
         */
        public interface OnPrepareListViewListener {

            /**
             * Called before the ListView is bound to an adapter.
             * @param listView The ListView that will be shown in the dialog.
             */
            void onPrepareListView(ListView listView);
        }
    }
}
