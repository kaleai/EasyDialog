package kale.easydialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import kale.ui.view.dialog.BaseCustomDialog;

/**
 * @author Kale
 * @date 2018/8/15
 */
public class DemoDialog extends BaseCustomDialog {

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void bindViews(View root) {

    }

    @Override
    protected void setViews() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    
}