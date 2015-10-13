package kale.easydialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import kale.ui.view.DialogInterface.OnCancelListener;
import kale.ui.view.DialogInterface.OnClickListener;
import kale.ui.view.DialogInterface.OnDismissListener;
import kale.ui.view.DialogInterface.OnItemClickListener;
import kale.ui.view.DialogInterface.OnMultiChoiceClickListener;
import kale.ui.view.MultiChoiceDialog;
import kale.ui.view.ProgressDialog;
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

    private Button simplePgDialogBtn;

    private Button simplePgHDialogBtn;

    private void assignViews() {
        simpleDialogBtn = (Button) findViewById(R.id.simple_dialog_btn);
        singleDialogBtn = (Button) findViewById(R.id.single_dialog_btn);
        multiDialogBtn = (Button) findViewById(R.id.multi_dialog_btn);
        simplePgDialogBtn = (Button) findViewById(R.id.simple_pg_dialog_btn);
        simplePgHDialogBtn = (Button) findViewById(R.id.simple_pg_h_dialog_btn);
    }

    
    private void setViews() {
        // 最简单提示对话框
        simpleDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new Dial().show(getSupportFragmentManager(), "dd");
                setSimpleDialog();
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

        // 最简单的进度条对话框
        simplePgDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog dialog = new ProgressDialog();
                dialog.setTitle("圆形进度条");
                dialog.setMessage("test message");
                dialog.setCancelable(true);
                dialog.setIndeterminate(true);//设置不显示明确的进度
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置采用圆形进度条（默认）

                // dialog.setIndeterminate(false);// 设置显示明确的进度
                // dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                // 点击空白处，点击返回键都会触发onCancel->onDismiss
                dialog.setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Log.d(TAG, "onCancel");
                    }
                });
                dialog.setOnDismissListener(new OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Log.d(TAG, "onDismiss");
                    }
                });
                dialog.show(getSupportFragmentManager(), TAG);
            }
        });

        // 有进度的对话框
        simplePgHDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog dialog = new ProgressDialog();
                dialog.setTitle("横向进度条");
                dialog.setMax(100);
                dialog.setIndeterminate(false);//设置不显示明确的进度
                dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                // dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置采用圆形进度条
                dialog.setProgress(40);
                dialog.show(getSupportFragmentManager(), TAG);

                //启动线程，模拟一个耗时的操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int Progress = 0;
                        while (Progress < 100) {
                            try {
                                Thread.sleep(100);
                                Progress++;
                                // dialog.setProgress(Progress);
                                dialog.incrementProgressBy(1);// 进度条一次加1
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        dialog.dismiss();// 完成后消失
                    }
                }).start();
            }
        });
    }

    private void multiChoiceDialog() {
        MultiChoiceDialog dialog = new MultiChoiceDialog();
        dialog.setData(new String[]{"Android", "ios", "wp"}, new boolean[]{true, false, true});
        dialog.setOnMultiChoiceClickListener(new OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                Log.d(TAG, "onClick pos = " + which + " , isChecked = " + isChecked);
            }
        });
        dialog.show(getSupportFragmentManager(), TAG);
    }

    private void singleChoiceDialog() {
        SingleChoiceDialog dialog = new SingleChoiceDialog();
        dialog.setCancelable(false);
        dialog.setTitle("Single Choice Dialog");
        dialog.setData(new String[]{"Android", "ios", "wp"}, 1);
        dialog.setOnItemSelectedListener(new OnItemClickListener() {
            @Override
            public void onItemClick(DialogInterface dialog, int position, long id) {
                Log.d(TAG, "onItemClick pos = " + position);
                dialog.dismiss();
            }
        });
        dialog.show(getSupportFragmentManager(), TAG);
    }

    private void setSimpleDialog() {
        SimpleDialog dialog = new SimpleDialog();
        dialog.setCancelable(true);
        dialog.setTitle("Title");
        dialog.setMessage("Message");

        // onCancel - > onDismiss
        dialog.setOnCancelListener(new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d(TAG, "onCancel");
            }
        });
        dialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.d(TAG, "onDismiss");
            }
        });
        // 设置对话框上的按钮 ok->dismiss
        dialog.setPositiveListener("ok---------df", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick ok");
            }
        });

        // cancel -> dismiss
        dialog.setNegativeListener("cancel dfsdsfdasf", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick cancel");
                dialog.dismiss();
            }
        });
        dialog.show(getSupportFragmentManager(), TAG);
    }

}
