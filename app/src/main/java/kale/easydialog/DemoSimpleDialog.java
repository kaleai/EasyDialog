package kale.easydialog;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import kale.ui.view.BaseCustomDialog;
import kale.ui.view.BaseEasyDialog;

/**
 * @author Jack Tony
 * @date 2015/8/27
 */
public class DemoSimpleDialog extends BaseCustomDialog {

    private static final String KEY_INPUT_TEXT = "key_input_text";

    private static final String KEY_INPUT_HINT = "key_input_hint";

    private static final String KEY_IMAGE_BITMAP = "key_image_bitmap";

    private Bitmap mBitmap;

    private CharSequence mInputText;

    private CharSequence mInputHint;

    private EditText mInputTextEt;

    public static class Builder extends BaseCustomDialog.Builder<DemoSimpleDialog.Builder> {

        private Bundle bundle = new Bundle();

        public Builder setImageBitmap(Bitmap bitmap) {
            bundle.putByteArray(KEY_IMAGE_BITMAP, bitmap2ByteArr(bitmap));
            return this;
        }

        public Builder setInputText(CharSequence text, CharSequence hint) {
            bundle.putCharSequence(KEY_INPUT_TEXT, text);
            bundle.putCharSequence(KEY_INPUT_HINT, hint);
            return this;
        }

        @NonNull
        @Override
        protected BaseEasyDialog createDialog() {
            DemoSimpleDialog dialog = new DemoSimpleDialog();
            dialog.addArguments(bundle);
            return dialog;
        }

    }

    @Override
    protected void configDialogBuilder(AlertDialog.Builder builder) {
        super.configDialogBuilder(builder);

        Bundle arguments = getArguments();

        byte[] mBitmapByteArr = null;
        if (arguments != null) {
            mBitmapByteArr = arguments.getByteArray(KEY_IMAGE_BITMAP);
            mInputText = arguments.getCharSequence(KEY_INPUT_TEXT);
            mInputHint = arguments.getCharSequence(KEY_INPUT_HINT);
        }

        if (mBitmapByteArr != null) {
            mBitmap = BitmapFactory.decodeByteArray(mBitmapByteArr, 0, mBitmapByteArr.length);
            builder.setMessage(null);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.demo_dialog_layout;
    }

    @Override
    protected void bindViews(Dialog dialog) {
        mInputTextEt = getView(R.id.input_et);
    }

    @Override
    public void setViews() {
        if (mBitmap != null) {
            LinearLayout imageTextLl = getView(R.id.image_text_ll);
            imageTextLl.setVisibility(View.VISIBLE);

            ((ImageView) getView(R.id.image_iv)).setImageBitmap(mBitmap);
            ((TextView) getView(R.id.msg_tv)).setText(R.string.app_name);
        }

        if (mInputText != null) {
            mInputTextEt.setVisibility(View.VISIBLE);
            if (!isRestored()) {
                // 如果是从旋转屏幕或其他状态恢复的fragment
                mInputTextEt.setText(mInputText);
            }
            mInputTextEt.setHint(mInputHint);
        }
    }

    public
    @Nullable
    EditText getInputTextEt() {
        return mInputTextEt;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mInputTextEt = null;
    }

    /**
     * Bitmap转Byte[]
     */
    public static byte[] bitmap2ByteArr(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

}
