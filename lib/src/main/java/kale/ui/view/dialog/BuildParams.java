package kale.ui.view.dialog;

import java.io.Serializable;

/**
 * @author Kale
 * @date 2016/11/22
 */

class BuildParams implements Serializable {

    public int mIconId = 0;

    public int themeResId;

    public CharSequence title;

    public CharSequence message;

    public CharSequence positiveText;

    public CharSequence neutralText;

    public CharSequence negativeText;

    public CharSequence[] items;

    public boolean[] checkedItems;

    public boolean isMultiChoice;

    public boolean isSingleChoice;

    public int checkedItem;

}
