package kale.easydialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import kale.ui.view.ProgressDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog dialog = new AlertDialog.Builder(this)
                //.setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle("title")
                //.setMessage("message") // 如果是列表就不要设置message了，否则会出问题
                .setPositiveButton("好", null)
                .setNeutralButton("中", null)
                .setNegativeButton("差", null)
                .setItems(new String[]{"android","ios","wp"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();

        dialog.show();


        ProgressDialog.show(this, "title", "test message");
    }
   
}
