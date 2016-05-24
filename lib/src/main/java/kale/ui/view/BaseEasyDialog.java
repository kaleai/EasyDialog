package kale.ui.view;


import com.lzh.courier.annoapi.Field;
import com.lzh.courier.annoapi.Params;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jack Tony
 * @date 2015/10/12
 */
@Params(fields = {
        @Field(name = "title", type = CharSequence.class),
        @Field(name = "titleRes", type = int.class),
        @Field(name = "positiveText", type = CharSequence.class),
        @Field(name = "positiveTextResId", type = int.class),
        @Field(name = "neutralText", type = CharSequence.class),
        @Field(name = "neutralTextResId", type = int.class),
        @Field(name = "negativeText", type = CharSequence.class),
        @Field(name = "negativeTextResId", type = int.class),
}, inherited = false)
public abstract class BaseEasyDialog extends DialogFragment {

    @Getter
    private boolean isRestored = false;

    @Setter
    private DialogInterface.OnDismissListener onDismissListener;

    @Setter
    private DialogInterface.OnCancelListener onCancelListener;

    @Setter
    private DialogInterface.OnClickListener positiveListener;

    @Setter
    private DialogInterface.OnClickListener neutralListener;

    @Setter
    private DialogInterface.OnClickListener negativeListener;

    protected abstract static class Builder<T extends Builder> {

        private DialogInterface.OnDismissListener onDismissListener;

        private DialogInterface.OnCancelListener onCancelListener;

        private DialogInterface.OnClickListener positiveListener, neutralListener, negativeListener;

        private BaseEasyDialog_Builder builder = BaseEasyDialog_Builder.create();

        public T setTitle(CharSequence title) {
            builder.setTitle(title);
            return (T) this;
        }

        public T setTitle(@StringRes int title) {
            builder.setTitleRes(title);
            return (T) this;
        }

        public T setOnDismissListener(DialogInterface.OnDismissListener listener) {
            onDismissListener = listener;
            return (T) this;
        }

        public T setOnCancelListener(DialogInterface.OnCancelListener listener) {
            onCancelListener = listener;
            return (T) this;
        }

        public T setPositiveButton(CharSequence positiveText, DialogInterface.OnClickListener listener) {
            builder.setPositiveText(positiveText);
            positiveListener = listener;
            return (T) this;
        }

        public T setPositiveButton(@StringRes int positiveText, DialogInterface.OnClickListener listener) {
            builder.setPositiveTextResId(positiveText);
            positiveListener = listener;
            return (T) this;
        }

        public T setNeutralButton(CharSequence neutralText, DialogInterface.OnClickListener listener) {
            builder.setNeutralText(neutralText);
            neutralListener = listener;
            return (T) this;
        }

        public T setNeutralButton(@StringRes int neutralText, DialogInterface.OnClickListener listener) {
            builder.setNegativeTextResId(neutralText);
            neutralListener = listener;
            return (T) this;
        }

        public T setNegativeButton(CharSequence negativeText, DialogInterface.OnClickListener listener) {
            builder.setNegativeText(negativeText);
            negativeListener = listener;
            return (T) this;
        }

        public T setNegativeButton(@StringRes int negativeText, DialogInterface.OnClickListener listener) {
            builder.setNegativeTextResId(negativeText);
            negativeListener = listener;
            return (T) this;
        }

        public <E extends BaseEasyDialog> E build() {
            BaseEasyDialog dialog = createDialog();
            addArgs(dialog);
            dialog.setOnDismissListener(onDismissListener);
            dialog.setOnCancelListener(onCancelListener);
            dialog.setPositiveListener(positiveListener);
            dialog.setNeutralListener(neutralListener);
            dialog.setNegativeListener(negativeListener);
            return (E) dialog;
        }

        /**
         * 通过add来添加bundle，并且不会丢失父类的bundle对象
         */
        @CallSuper
        protected void addArgs(BaseEasyDialog dialog) {
            dialog.addArguments(builder.createBundle());
        }

        protected abstract
        @NonNull
        BaseEasyDialog createDialog();

    }

    public void addArguments(Bundle bundle) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            arguments.putAll(bundle);
            setArguments(arguments);
        } else {
            setArguments(bundle);
        }
    }

    private BaseEasyDialog_Builder.ArgsData data;

    @CallSuper
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        configDialogBuilder(builder);

        Dialog dialog = builder.create();
        setDialog(dialog);
        return dialog; // bind dialog to fragment
    }

    @CallSuper
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        isRestored = true;
    }

    @Override
    public void onStart() {
        super.onStart();
        bindViews(getDialog());
        setViews();
    }

    @CallSuper
    protected void configDialogBuilder(AlertDialog.Builder builder) {
        data = BaseEasyDialog_Builder.getArguments(this);

        builder.setTitle(data.getTitle()); // 内部会进行非空判断的，可以直接传null

        CharSequence positiveText = getText(data.getPositiveText(), data.getPositiveTextResId());
        if (positiveText != null) {
            builder.setPositiveButton(positiveText, positiveListener);
        }

        CharSequence neutralText = getText(data.getNeutralText(), data.getNeutralTextResId());
        if (neutralText != null) {
            builder.setNeutralButton(neutralText, neutralListener);
        }

        CharSequence negativeText = getText(data.getNegativeText(), data.getNegativeTextResId());
        if (negativeText != null) {
            builder.setNegativeButton(negativeText, negativeListener);
        }

        if (getLayoutResId() != 0) {
            builder.setView(getLayoutResId());
        }
    }

    @Nullable
    protected CharSequence getText(CharSequence text, int id) {
        if (text == null) {
            return id != 0 ? getString(id) : null;
        } else {
            return text;
        }
    }

    protected void setDialog(Dialog dialog) {
    }

    protected int getLayoutResId() {
        return 0;
    }

    protected void bindViews(Dialog dialog) {
    }

    protected void beforeSetViews() {
    }

    protected void setViews() {
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (onCancelListener != null) {
            onCancelListener.onCancel(dialog);
        }
    }

    public CharSequence getTitle() {
        return data.getTitle();
    }

    protected <T extends View> T getView(@IdRes int id) {
        return (T) getDialog().findViewById(id);
    }

    public void show(FragmentManager manager) {
        show(manager, "dialog");
    }

    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

}
