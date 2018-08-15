package kale.ui.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

/**
 * @author Kale
 * @date 2018/8/15
 */
final class DialogBuilderFactory {

    static <Dialog extends EasyDialog> BaseEasyDialog.Builder getBuilder(Context context, @StyleRes int themeResId, final Class<Dialog> clz) {
        if (themeResId == 0) {
            return new BaseEasyDialog.Builder(context) {
                @NonNull
                @Override
                protected EasyDialog createDialog() {
                    return getEasyDialog(clz);
                }
            };
        } else {
            return new BaseEasyDialog.Builder(context, themeResId) {
                @NonNull
                @Override
                protected EasyDialog createDialog() {
                    return getEasyDialog(clz);
                }
            };
        }
    }

    @Nullable
    private static <Dialog extends EasyDialog> EasyDialog getEasyDialog(Class<Dialog> clz) {
        if (clz == null) {
            return new EasyDialog();
        }
        
        // 如果有class对象，那么则反射出一个实例
        Dialog dialog = null;
        try {
            dialog = clz.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return dialog;
    }

}
