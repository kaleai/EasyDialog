package kale.easydialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import kale.ui.view.BaseEasyDialog;
import kale.ui.view.SimpleDialog;

/**
 * @author Jack Tony
 * @date 2015/8/27
 */
public class DemoSimpleDialog extends SimpleDialog {

    private static final String KEY_INPUT_TEXT = "key_input_text";

    private static final String KEY_INPUT_HINT = "key_input_hint";

    private static final String KEY_IMAGE_BITMAP = "key_image_bitmap";

    private Bitmap mBitmap;

    private CharSequence mInputText;

    private CharSequence mInputHint;

    private EditText mInputTextEt;

    public static class Builder extends SimpleDialog.Builder {

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
            return new DemoSimpleDialog();
        }
    }

    @Override
    protected void setAlertBuilder(AlertDialog.Builder builder, @Nullable Bundle arguments) {
        super.setAlertBuilder(builder, arguments);
        builder.setView(R.layout.demo_dialog_layout);
        if (arguments != null) {
            byte[] bitmapByteArr = arguments.getByteArray(KEY_IMAGE_BITMAP);
            mInputText = arguments.getCharSequence(KEY_INPUT_TEXT);
            mInputHint = arguments.getCharSequence(KEY_INPUT_HINT);

            if (bitmapByteArr != null) {
                mBitmap = BitmapFactory.decodeByteArray(bitmapByteArr, 0, bitmapByteArr.length);
                builder.setMessage(null);
            }
        }
    }


    @Override
    public void setViews() {
        // 在onStart中找到控件并进行设置
        if (mBitmap != null) {
            LinearLayout imageTextLl = getView(R.id.image_text_ll);
            imageTextLl.setVisibility(View.VISIBLE);

            ImageView imageView = getView(R.id.image_iv);
            imageView.setImageBitmap(mBitmap);

            TextView tv = getView(R.id.msg_tv);
            tv.setText(getMessage());
        }

        if (!TextUtils.isEmpty(mInputText)) {
            mInputTextEt = getView(R.id.input_et);
            mInputTextEt.setVisibility(View.VISIBLE);
            mInputTextEt.setText(mInputText);
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
        mBitmap = null;
        mInputText = null;
        mInputHint = null;
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
