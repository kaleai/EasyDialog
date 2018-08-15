package android.support.v7.app;

import android.content.Context;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;

/**
 * @author Kale
 * @date 2018/8/13
 * 
 * 用来解析dialog主题的辅助类
 * 
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class _Kale_EasyDialog_AlertDialog {

    public static int resolveDialogTheme(Context context, @StyleRes int resId) {
        return AlertDialog.resolveDialogTheme(context, resId);
    }

}