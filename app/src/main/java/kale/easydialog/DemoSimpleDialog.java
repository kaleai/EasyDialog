package kale.easydialog;

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

    private byte[] mBitmapByteArr;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mBitmapByteArr = arguments.getByteArray(KEY_IMAGE_BITMAP);
            mInputText = arguments.getCharSequence(KEY_INPUT_TEXT);
            mInputHint = arguments.getCharSequence(KEY_INPUT_HINT);
        }
    }

    @Override
    protected void setAlertBuilder(AlertDialog.Builder builder) {
        super.setAlertBuilder(builder);
        builder.setView(R.layout.demo_dialog_layout);
        if (mBitmapByteArr != null) {
            mBitmap = BitmapFactory.decodeByteArray(mBitmapByteArr, 0, mBitmapByteArr.length);
            builder.setMessage(null);
        }
    }


    @Override
    public void setViews() {
        if (mBitmap != null) {
            LinearLayout imageTextLl = getView(R.id.image_text_ll);
            imageTextLl.setVisibility(View.VISIBLE);

            ((ImageView) getView(R.id.image_iv)).setImageBitmap(mBitmap);
            ((TextView) getView(R.id.msg_tv)).setText(getMessage());
        }

        if (mInputText != null) {
            mInputTextEt = getView(R.id.input_et);
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
