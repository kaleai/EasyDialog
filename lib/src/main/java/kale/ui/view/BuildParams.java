package kale.ui.view;

import java.io.Serializable;

/**
 * @author Kale
 * @date 2016/11/22
 */

/*package*/ class BuildParams implements Serializable {

    int themeResId;

    CharSequence title;

    CharSequence message;

    CharSequence positiveText;

    CharSequence neutralText;

    CharSequence negativeText;

    CharSequence[] items;

    boolean[] checkedItems;

    boolean isMultiChoice;

    boolean isSingleChoice;

    int checkedItem;

}
