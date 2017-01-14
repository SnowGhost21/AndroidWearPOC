package com.example.diegocunha.androidwearpoc.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.diegocunha.androidwearpoc.helper.GradientHelper;

import java.util.Arrays;
import java.util.List;

/**
 * Created by snowghost on 13/01/17.
 */

public class BackgroundThemeService extends IntentService {

    private static final String TAG = "BackgroundThemeMonitor";
    private static final int DELAY = 1000 * 60 * 10;

    public static final String THEME_EXTRA = "THEME_EXTRA";
    public static final String THEME_CHAGEND_ACTION = "com.example.diegocunha.androidwearpoc.themechanged";

    private boolean running;
    private int[] currentTheme;

    public BackgroundThemeService() {
        super("BackgroundThemeService");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        running = true;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (running){
            synchronized (this){
                try{
                    int[] theme = GradientHelper.colorsForTime();

                    if(!Arrays.equals(currentTheme, theme)){
                        currentTheme = theme;
                        notifyThemeChanged();
                    }
                }catch (Exception e){
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    private void notifyThemeChanged() {
        Intent intent = new Intent(THEME_CHAGEND_ACTION);
        intent.putExtra(THEME_EXTRA, currentTheme);

        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        running = false;
        super.onDestroy();
    }
}
