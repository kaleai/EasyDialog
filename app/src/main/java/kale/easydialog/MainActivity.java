package kale.easydialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import kale.ui.view.dialog.EasyDialog;

/**
 * 关于更多对话框的设置请参考：http://www.cnblogs.com/tianzhijiexian/p/3867731.html
 */
public class MainActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();

    private BottomSheetBehavior behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
    }

    private void setViews() {
        findViewById(R.id.dayNight_btn).setOnClickListener(
                v -> startActivity(new Intent(this, MyStyleActivity.class)));

        // 得到 Bottom Sheet 的视图对象所对应的 BottomSheetBehavior 对象
        behavior = BottomSheetBehavior.from(findViewById(R.id.ll_sheet_root));
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    public EasyDialog easyDialog;

    public void simpleDialog(View v) {
        final android.support.v4.app.FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();

        EasyDialog.Builder builder = EasyDialog.builder(this);
        builder.setTitle("Title")
                .setIcon(R.drawable.saber)
                .setMessage(R.string.hello_world)
                .setOnCancelListener(dialog -> Log.d(TAG, "onCancel"))
                .setOnDismissListener(dialog -> Log.d(TAG, "onDismiss"))
//                .setNeutralButton("know", null)
                // 设置对话框上的按钮 ok->dismiss
                .setPositiveButton("ok", (dialog, which) -> {
                    Log.d(TAG, "onClick ok");

                    ft.remove(easyDialog);
                    ft.addToBackStack(null);

                    EasyDialog.builder(MainActivity.this)
                            .setTitle("Stack Dialog")
                            .setMessage("Please press back button")
                            .build()
                            .show(ft, "stackDialog");
                })
                // cancel -> dismiss
                .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                .setNeutralButton("ignore", null)
                .setCancelable(true);

        easyDialog = builder.build();
        easyDialog.showAllowingStateLoss(getSupportFragmentManager());

//        easyDialog.show(getSupportFragmentManager());
    }

    public void listDialog(View v) {
        //选项数组  
        EasyDialog.builder(this)
                .setItems(R.array.country, (dialog, which) -> showToast("click " + which))
                .setPositiveButton("yes", null)
                .setNegativeButton("no", null)
                .build()
                .show(getSupportFragmentManager());
    }

    /**
     * 支持单选列表的对话框
     */
    public void singleChoiceDialog(View v) {
        EasyDialog dialog = EasyDialog.builder(this)
                .setTitle("Single Choice Dialog")
                .setSingleChoiceItems(new String[]{"Android", "ios", "wp"}, 1,
                        (dialog1, position) -> {
                            Log.d(TAG, "onItemClick pos = " + position);
                            dialog1.dismiss();
                        })// 设置单选列表的数据和监听
                .setPositiveButton("ok", null)
                .build();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), TAG);
    }

    /**
     * 支持多选列表的对话框
     */
    public void multiChoiceDialog(View v) {
        EasyDialog.builder(this)
                // 设置数据和默认选中的选项
                .setMultiChoiceItems(
                        new String[]{"Android", "ios", "wp"}, new boolean[]{true, false, true},
                        (dialog, which, isChecked) -> showToast("onClick pos = " + which + " , isChecked = " + isChecked)) // 设置监听器
                .build()
                .show(getSupportFragmentManager(), TAG);
    }

    private InputDialog dialog;

    /**
     * 可以输入文字的dialog，拥有自定义布局
     */
    public void inputDialog(View v) {
        dialog = new InputDialog.Builder(this)
                .setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.kale))
                .setInputText("", "hint")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface ignore, int which) {
                        String text = dialog.getInputTextEt().getText().toString();
                        if (!TextUtils.isEmpty(text)) {
                            showToast(text);
                        }
                    }
                })
                .build();
        dialog.show(getSupportFragmentManager()); // 一个参数的show()
    }

    public void imageDialog(View v) {
        EasyDialog.builder(this, ImageDialog.class)
                .setPositiveButton("弹出动态设置样式的Dialog", (dialog, which) -> {

                })
                .build()
                .show(getSupportFragmentManager());
    }

    /**
     * 显示在顶部的dialog，背景透明
     */
    public void topDialog(View v) {
        TopDialog.Builder builder = EasyDialog.builder(this, TopDialog.class);
        builder.setTitle("标题");
        builder.setPositiveButton("设置了宽高", null);
        builder.setNegativeButton("位置在顶部", null);
        builder.build().show(getSupportFragmentManager());
    }

    /**
     * 自定一个dialog的builder
     */
    public void customBuilderDialog(View v) {
        new NewBuilderDialog.Builder(this)
                .setTitle("Custom Builder Dialog")
                .setMessage("message")
                .setName("kale")
                .setAge(31)
                .build()
                .show(getSupportFragmentManager());
    }

    /**
     * 从底部弹出的对话框
     */
    public void bottomDialog(View v) {
        BottomDialog.Builder builder = EasyDialog.builder(this, BottomDialog.class);
        builder.setMessage("click me");

        builder.setIsBottomDialog(true); // 设置后则会变成从底部弹出，否则为正常模式

        EasyDialog dialog = builder.build();
        dialog.show(getSupportFragmentManager(), "dialog");

        // 如果设置了，那么底部dialog就不支持手势关闭和空白处关闭
        dialog.setCancelable(false);

        // 监听点空白处cancel的事件
        dialog.setOnCancelListener(d -> showToast("cancel"));
        dialog.setOnDismissListener(d -> showToast("dismiss"));
    }

    /**
     * Activity中的可以从底部拉出的dialog
     */
    public void bottomDialogInActivity(View v) {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    public void showToast(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }
}
