package kale.ui.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Kale
 * @date 2016/11/22
 */
public class EasyDialog extends BaseEasyDialog {

    @Setter(AccessLevel.PUBLIC)
    private DialogInterface.OnClickListener positiveListener;

    @Setter(AccessLevel.PUBLIC)
    private DialogInterface.OnClickListener neutralListener;

    @Setter(AccessLevel.PUBLIC)
    private DialogInterface.OnClickListener negativeListener;

    @Setter(AccessLevel.PUBLIC)
    private DialogInterface.OnClickListener onClickListener;

    @Setter(AccessLevel.PUBLIC)
    private DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener;

    @Getter
    private boolean isRestored = false;

    /**
     * use {@link Builder#build()}
     */
    public EasyDialog() {
    }

    /**
     * 这里千万不要做{@link Dialog#findViewById(int)}的操作
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        return createDialog(getActivity());
    }

    /**
     * 这时dialog已经创建完毕，可以调用{@link Dialog#findViewById(int)}
     */
    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        bindAndSetViews(window != null ? window.getDecorView() : null);
    }

    /**
     * 复写来保存参数
     */
    @CallSuper
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * 复写来恢复参数
     */
    @CallSuper
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        isRestored = true;
    }

    protected void bindAndSetViews(@Nullable View root) {
    }

    private Dialog createDialog(@NonNull Activity activity) {
        BuildParams p = getBuildParams(); // 得到来自父类的通用参数，这里将参数组装成builder对象
        Builder builder = new Builder(activity);

        builder.setTitle(p.title)
                .setIcon(p.mIconId)
                .setMessage(p.message)
                .setPositiveButton(p.positiveText, positiveListener)
                .setNeutralButton(p.neutralText, neutralListener)
                .setNegativeButton(p.negativeText, negativeListener)
                .setItems(p.items, null);

        if (p.items != null) {
            if (p.isMultiChoice) {
                builder.setMultiChoiceItems(p.items, p.checkedItems, onMultiChoiceClickListener);
            } else if (p.isSingleChoice) {
                builder.setSingleChoiceItems(p.items, p.checkedItem, onClickListener);
            } else {
                builder.setItems(p.items, onClickListener);
            }
        }

        Builder newBuilder = resetOriginBuilder(builder);
        modifyOriginBuilder(newBuilder);
        return newBuilder.create();
    }

    /**
     * 修改构造当前dialog的builder对象，框架将会用这个builder对象来做真正的构造器
     */
    protected Builder resetOriginBuilder(Builder builder) {
        return builder;
    }

    /**
     * 暂时兼容老版本，推荐用resetOriginBuilder()来更换
     */
    @CallSuper
    @Deprecated
    protected void modifyOriginBuilder(Builder builder) {

    }

    public static class Builder extends BaseEasyDialog.Builder<Builder> {

        public Builder(@NonNull Context context) {
            super(context);
        }

        @NonNull
        @Override
        protected EasyDialog createDialog() {
            return new EasyDialog();
        }

    }

}
