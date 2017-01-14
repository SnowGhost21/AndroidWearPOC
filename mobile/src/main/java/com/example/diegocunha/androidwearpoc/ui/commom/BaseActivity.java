package com.example.diegocunha.androidwearpoc.ui.commom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.example.diegocunha.androidwearpoc.helper.GradientHelper;
import com.example.diegocunha.androidwearpoc.service.BackgroundThemeService;

/**
 * Created by snowghost on 13/01/17.
 */

public abstract class BaseActivity extends AppCompatActivity{
    private static int[] backgroundTheme;
    private BackgroundThemeMonitorReceiver themeMonitorReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        themeMonitorReceiver = new BackgroundThemeMonitorReceiver();
        registerReceiver(themeMonitorReceiver, new IntentFilter(BackgroundThemeService.THEME_CHAGEND_ACTION));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(backgroundTheme != null && backgroundTheme.length > 0 ){
            onBackgroundThemeChanged(backgroundTheme);
        }
    }

    @Override
    protected void onDestroy() {
        if(themeMonitorReceiver != null){
            unregisterReceiver(themeMonitorReceiver);
            themeMonitorReceiver = null;
        }

        super.onDestroy();
    }

    public int[] getBackgroundTheme() {
        return backgroundTheme.length > 0 ? backgroundTheme : GradientHelper.colorsForTime();
    }
    protected abstract void onBackgroundThemeChanged(int[] theme);

    private class BackgroundThemeMonitorReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equalsIgnoreCase(BackgroundThemeService.THEME_CHAGEND_ACTION)) {
                backgroundTheme = intent.getIntArrayExtra(BackgroundThemeService.THEME_EXTRA);
                onBackgroundThemeChanged(backgroundTheme);
            }
        }
    }
}
