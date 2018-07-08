package kale.easydialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.TextView;

/**
 * @author Kale
 * @date 2016/11/21
 */

public class CustomStyleActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        
        super.onCreate(savedInstanceState);

        TextView view = findViewById(R.id.jump_btn);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomStyleActivity.this, MainActivity.class));
            }
        });
    }
    
}
