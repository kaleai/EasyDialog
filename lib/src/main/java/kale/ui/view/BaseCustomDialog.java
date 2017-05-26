package kale.ui.view;

import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author Kale
 * @date 2016/11/15
 *
 * 不用系统的dialog，自己定义的dialog需要继承此类
 */
public abstract class BaseCustomDialog extends EasyDialog {

    @Override
    protected void modifyOriginBuilder(Builder builder) {
        super.modifyOriginBuilder(builder);
        if (getLayoutResId() != 0) {
            builder.setView(getLayoutResId());
        }
    }

    @Override
    protected void bindAndSetViews(@Nullable View root) {
        super.bindAndSetViews(root);
        bindViews(root);
        setViews();
    }

    /**
     * @return 默认返回0
     */
    protected abstract int getLayoutResId();

    protected abstract void bindViews(View root);

    protected abstract void setViews();
}
