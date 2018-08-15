package kale.easydialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;

/**
 * @author Kale
 * @date 2016/11/21
 */

public class MyStyleActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        
        super.onCreate(savedInstanceState);

        findViewById(R.id.dayNight_btn).setOnClickListener(
                v -> startActivity(new Intent(this, MainActivity.class)));
    }
    
}
