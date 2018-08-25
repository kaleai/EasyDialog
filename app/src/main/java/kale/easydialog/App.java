package kale.easydialog;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import kale.ui.view.dialog.EasyDialog;

/**
 * @author Kale
 * @date 2018/8/17
 */
public class App extends Application {

    private AppCompatActivity curActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                curActivity = (AppCompatActivity) activity;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                curActivity = null;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public AppCompatActivity getCurActivity() {
        return curActivity;
    }

    public void showDialog(String title, String message) {
        if (curActivity == null) {
            return;
        }

        EasyDialog.builder(curActivity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("ok", null)
                .build()
                .show(curActivity.getSupportFragmentManager());
    }
}