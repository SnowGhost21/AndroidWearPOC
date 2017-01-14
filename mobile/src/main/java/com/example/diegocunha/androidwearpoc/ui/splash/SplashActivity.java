package com.example.diegocunha.androidwearpoc.ui.splash;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.diegocunha.androidwearpoc.R;
import com.example.diegocunha.androidwearpoc.helper.GradientHelper;
import com.example.diegocunha.androidwearpoc.service.BackgroundThemeService;
import com.example.diegocunha.androidwearpoc.ui.commom.BaseActivity;

import java.util.Date;

public class SplashActivity extends BaseActivity implements SplashView{

    View layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startService(new Intent(this, BackgroundThemeService.class));
        layout = findViewById(R.id.activity_splash);


        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, GradientHelper.colorsForTime());
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(0f);

        layout.setBackgroundDrawable(drawable);

    }


    @Override
    protected void onBackgroundThemeChanged(int[] theme) {

    }
}
