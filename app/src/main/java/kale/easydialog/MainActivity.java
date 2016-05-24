package kale.easydialog;


import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import kale.ui.view.MultiChoiceDialog;
import kale.ui.view.SimpleDialog;
import kale.ui.view.SingleChoiceDialog;

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

    private void assignViews() {
        simpleDialogBtn = (Button) findViewById(R.id.simple_dialog_btn);
        singleDialogBtn = (Button) findViewById(R.id.single_dialog_btn);
        multiDialogBtn = (Button) findViewById(R.id.multi_dialog_btn);
        customDialog = (Button) findViewById(R.id.custom_dialog_btn);
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
    }

    private void simpleDialog() {
        SimpleDialog.Builder builder = new SimpleDialog.Builder();
        builder.setTitle("Title")
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
                .setNeutralButton("know", null)
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
                });

        SimpleDialog dialog = builder.build();
        dialog.setCancelable(true);
        dialog.show(getSupportFragmentManager());
    }

    private void singleChoiceDialog() {
        SingleChoiceDialog.Builder builder = new SingleChoiceDialog.Builder();
        SingleChoiceDialog dialog = builder
                .setTitle("Single Choice Dialog")
                .setData(new String[]{"Android", "ios", "wp"}, 1)// 设置单选列表的数据和监听
                .setOnItemSelectedListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        Log.d(TAG, "onItemClick pos = " + position);
                        dialog.dismiss();
                    }

                })
                .build();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), TAG);
    }

    private void multiChoiceDialog() {
        new MultiChoiceDialog.Builder()
                .setData(new String[]{"Android", "ios", "wp"}, new boolean[]{true, false, true}) // 设置数据和默认选中的选项
                // 设置监听器
                .setOnMultiChoiceClickListener(new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Log.d(TAG, "onClick pos = " + which + " , isChecked = " + isChecked);
                    }
                })
                .build().show(getSupportFragmentManager(), TAG);
    }

    DemoSimpleDialog dialog;

    private void customDialog() {
        dialog = new DemoSimpleDialog.Builder()
                .setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.kale))
                .setInputText("", "hint")
                .setMessage("这是图片")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface ignore, int which) {
                        Toast.makeText(getBaseContext(), dialog.getInputTextEt().getText().toString(), Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .build();
        dialog.show(getSupportFragmentManager(), TAG);
    }

}
