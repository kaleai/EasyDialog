package kale.easydialog;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import kale.ui.view.dialog.BaseCustomDialog;
import kale.ui.view.dialog.BaseEasyDialog;

/**
 * @author Jack Tony
 * @date 2015/8/27
 */
public class InputDialog extends BaseCustomDialog {

    private static final String KEY_INPUT_TEXT = "key_input_text";

    private static final String KEY_INPUT_HINT = "key_input_hint";

    private static final String KEY_IMAGE_BITMAP = "key_image_bitmap";

    private Bitmap mBitmap;

    private CharSequence mInputText;

    private CharSequence mInputHint;

    private TextView mTitleTv;

    private EditText mInputTextEt;

    /**
     * 自定义builder来增加一些参数，记得要继承自BaseEasyDialog.Builder
     */
    public static class Builder extends BaseEasyDialog.Builder<Builder> {

        private Bundle bundle = new Bundle();

        public Builder(@NonNull Context context) {
            super(context);
        }

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
        protected InputDialog createDialog() {
            InputDialog dialog = new InputDialog();
            dialog.setArguments(bundle);
            return dialog;
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();

        byte[] mBitmapByteArr = null;
        if (arguments != null) {
            mBitmapByteArr = arguments.getByteArray(KEY_IMAGE_BITMAP);
            mInputText = arguments.getCharSequence(KEY_INPUT_TEXT);
            mInputHint = arguments.getCharSequence(KEY_INPUT_HINT);
        }
        if (mBitmapByteArr != null) {
            mBitmap = BitmapFactory.decodeByteArray(mBitmapByteArr, 0, mBitmapByteArr.length);
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.custom_dialog_input_layout;
    }

    @Override
    protected void bindViews(View root) {
        mTitleTv = findView(R.id.msg_tv);
        mInputTextEt = findView(R.id.input_et);
    }

    @Override
    public void setViews() {
        if (mBitmap != null) {
            LinearLayout imageTextLl = findView(R.id.root_ll);
            imageTextLl.setVisibility(View.VISIBLE);

            ((ImageView) findView(R.id.image_iv)).setImageBitmap(mBitmap);
            ((TextView) findView(R.id.msg_tv)).setText(R.string.app_name);
        }

        if (mInputText != null) {
            mInputTextEt.setVisibility(View.VISIBLE);
            if (!isRestored()) {
                // 如果是从旋转屏幕或其他状态恢复的fragment
                mInputTextEt.setText(mInputText);
            }
            mInputTextEt.setHint(mInputHint);
        }

        Button button = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);

        button.setOnClickListener(v -> {
            if (TextUtils.isEmpty(mInputTextEt.getText())) {
                Toast.makeText(getActivity(), "请输入内容，否则不能关闭！", Toast.LENGTH_SHORT).show();
            } else {
                getPositiveListener().onClick(null, AlertDialog.BUTTON_POSITIVE);
                dismiss();
            }
        });

        showInputMethod(mInputTextEt);
    }

    public void showInputMethod(final EditText editText) {
        editText.post(() -> {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
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
