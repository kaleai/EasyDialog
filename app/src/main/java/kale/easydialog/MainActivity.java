package kale.easydialog;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kale.ui.view.EasyDialog;

/**
 * 关于更多对话框的设置请参考：http://www.cnblogs.com/tianzhijiexian/p/3867731.html
 */
public class MainActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignViews();
        setViews();
    }

    private Button simpleDialogBtn;

    private Button singleDialogBtn;

    private Button multiDialogBtn;

    private Button customDialog;

    private Button customDialog02;

    private void assignViews() {
        simpleDialogBtn = (Button) findViewById(R.id.simple_dialog_btn);
        singleDialogBtn = (Button) findViewById(R.id.single_dialog_btn);
        multiDialogBtn = (Button) findViewById(R.id.multi_dialog_btn);
        customDialog = (Button) findViewById(R.id.custom_dialog_btn);
        customDialog02 = (Button) findViewById(R.id.custom_dialog02_btn);

        findViewById(R.id.jump_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CustomStyleActivity.class));
            }
        });
    }

    private void setViews() {
        // 最简单提示对话框
        simpleDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleDialog();
            }
        });

        // 单选对话框
        singleDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleChoiceDialog();
            }
        });

        // 多选对话框
        multiDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiChoiceDialog();
            }
        });

        // 自定义对话框
        customDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();
            }
        });

        customDialog02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog02();
            }
        });
    }

    private void simpleDialog() {
        EasyDialog.Builder builder = new EasyDialog.Builder(this);
        builder.setTitle("Title")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage(R.string.hello_world)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Log.d(TAG, "onCancel"); // onCancel - > onDismiss
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Log.d(TAG, "onDismiss");
                    }
                })
//                .setNeutralButton("know", null)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick ok");// 设置对话框上的按钮 ok->dismiss
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick cancel");
                        dialog.dismiss(); // cancel -> dismiss
                    }
                })
                .setCancelable(true);

        EasyDialog dialog = builder.build();
        dialog.show(getSupportFragmentManager());
    }

    private void singleChoiceDialog() {
        EasyDialog dialog = new EasyDialog.Builder(this)
                .setTitle("Single Choice Dialog")
                .setSingleChoiceItems(new String[]{"Android", "ios", "wp"}, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        Log.d(TAG, "onItemClick pos = " + position);
                        dialog.dismiss();
                    }
                })// 设置单选列表的数据和监听
                .build();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), TAG);
    }

    private void multiChoiceDialog() {
        new EasyDialog.Builder(this)
                // 设置数据和默认选中的选项
                .setMultiChoiceItems(new String[]{"Android", "ios", "wp"}, new boolean[]{true, false, true},
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                Log.d(TAG, "onClick pos = " + which + " , isChecked = " + isChecked);
                            }
                        }) // 设置监听器
                .build().show(getSupportFragmentManager(), TAG);
    }

    private DemoSimpleDialog dialog;

    private void customDialog() {
        dialog = new DemoSimpleDialog.Builder(this)
                .setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.kale))
                .setInputText("", "hint")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface ignore, int which) {
                        String text = dialog.getInputTextEt().getText().toString();
                        if (!TextUtils.isEmpty(text)) {
                            Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .build();
        dialog.show(getSupportFragmentManager()); // 一个参数的show()
    }

    private void customDialog02() {
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("Custom Dialog");
        CustomDialog dialog = builder.build();
        dialog.show(getSupportFragmentManager());
    }

}
