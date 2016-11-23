package kale.ui.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import lombok.AccessLevel;
import lombok.Setter;

/**
 * @author Kale
 * @date 2016/11/22
 */
public class EasyDialog extends BaseEasyDialog {

    public static final String KEY_BUILD_PARAMS = "key_build_params";

    private BuildParams mBuildParams;

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

    /**
     * use {@link Builder#build()}
     */
    @Deprecated
    public EasyDialog() {
    }

    @Override
    public void onStart() {
        super.onStart();
        configViews(getDialog());
    }

    protected void configViews(Dialog dialog) {

    }

    @Override
    public void setArguments(Bundle args) {
        if (getArguments() != null) {
            getArguments().putAll(args);
        } else {
            super.setArguments(args);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mBuildParams = (BuildParams) bundle.getSerializable(KEY_BUILD_PARAMS);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        BuildParams p = mBuildParams;
        Builder builder = new Builder(getActivity());

        builder.setTitle(p.title)
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
        configBuilder(builder);
        return builder.create();
    }

    /**
     * 当默认的build不能满足需求时，请继承此方法
     */
    @CallSuper
    protected void configBuilder(Builder builder) {
    }

}
