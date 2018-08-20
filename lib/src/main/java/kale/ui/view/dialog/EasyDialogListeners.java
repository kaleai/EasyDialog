package kale.ui.view.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.VisibleForTesting;

/**
 * @author Kale
 * @date 2018/8/16
 */
class EasyDialogListeners {

    private final static DialogInterface.OnClickListener CLICK_LISTENER = (dialog, which) -> System.out.println(which);

    private final static DialogInterface.OnMultiChoiceClickListener MULTI_CHOICE_LISTENER = (dialog, which, isChecked) -> System.out.println(which);

    private final static DialogInterface.OnCancelListener CANCEL_LISTENER = dialog -> System.out.println(dialog.getClass().getSimpleName());

    private final static DialogInterface.OnDismissListener DISMISS_LISTENER = dialog -> System.out.println(dialog.getClass().getSimpleName());

    static void saveListenersIfActivity(EasyDialog dialog) {
        dialog.cancelListener = replaceActivityByValue(dialog.cancelListener, CANCEL_LISTENER);
        dialog.dismissListener = replaceActivityByValue(dialog.dismissListener, DISMISS_LISTENER);
        dialog.multiClickListener = replaceActivityByValue(dialog.multiClickListener, MULTI_CHOICE_LISTENER);

        DialogInterface.OnClickListener[] listeners = getClickListenerArr(dialog);
        for (int i = 0; i < listeners.length; i++) {
            setListenerValue(dialog, i, replaceActivityByValue(listeners[i], CLICK_LISTENER));
        }
    }

    static void restoreListenersIfActivity(EasyDialog dialog, Activity activity) {
        dialog.cancelListener = replaceValueByActivity(dialog.cancelListener, DialogInterface.OnCancelListener.class, activity);
        dialog.dismissListener = replaceValueByActivity(dialog.dismissListener, DialogInterface.OnDismissListener.class, activity);
        dialog.multiClickListener = replaceValueByActivity(dialog.multiClickListener, DialogInterface.OnMultiChoiceClickListener.class, activity);

        DialogInterface.OnClickListener[] listeners = getClickListenerArr(dialog);
        for (int i = 0; i < listeners.length; i++) {
            setListenerValue(dialog, i, replaceValueByActivity(listeners[i], DialogInterface.OnClickListener.class, activity));
        }
    }

    static void destroyListeners(EasyDialog dialog) {
        dialog.cancelListener = destroyListener(dialog.cancelListener, CANCEL_LISTENER);
        dialog.dismissListener = destroyListener(dialog.dismissListener, DISMISS_LISTENER);
        dialog.multiClickListener = destroyListener(dialog.multiClickListener, MULTI_CHOICE_LISTENER);

        DialogInterface.OnClickListener[] listeners = getClickListenerArr(dialog);
        for (int i = listeners.length - 1; i >= 0; i--) {
            setListenerValue(dialog, i, destroyListener(listeners[i], CLICK_LISTENER));
        }
    }

    private static void setListenerValue(EasyDialog dialog, int index, DialogInterface.OnClickListener value) {
        if (index == 0) {
            dialog.positiveListener = value;
        } else if (index == 1) {
            dialog.neutralListener = value;
        } else if (index == 2) {
            dialog.negativeListener = value;
        } else {
            dialog.clickListener = value;
        }
    }

    private static DialogInterface.OnClickListener[] getClickListenerArr(EasyDialog dialog) {
        return new DialogInterface.OnClickListener[]{
                dialog.positiveListener,
                dialog.neutralListener,
                dialog.negativeListener,
                dialog.clickListener,
        };
    }

    /**
     * 如果当前listener指向的是activity对象，那么则用一个静态值替换activity，避免内存泄漏
     *
     * null也可以认为是一个静态值，不会持有activity
     */
    @VisibleForTesting
    static <T> T replaceActivityByValue(T listener, T staticValue) {
        if (listener instanceof Activity) {
            return staticValue;
        } else {
            return null;
        }
    }

    /**
     * 如果当前对象是一个静态值，那么则让它指向dialog的activity
     */
    @VisibleForTesting 
    static <T> T replaceValueByActivity(T src, Class<T> clz, Object activity) {
        if (src != null) {
            // obj不为null，则说明其持有的是静态值，需要被替换
            if (clz.isInstance(activity)) {
                return (T) activity;
            }
        }

        return null;
    }

    /**
     * 如果当前对象不为null，并且等于静态对象，那么则不销毁它
     *
     * 一般情况下这里都会将监听器置空
     */
    private static <T> T destroyListener(T obj, T staticValue) {
        if (obj == staticValue) {
            return staticValue;
        } else {
            return null;
        }
    }

}