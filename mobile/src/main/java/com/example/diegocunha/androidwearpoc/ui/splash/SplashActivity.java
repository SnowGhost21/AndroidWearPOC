package com.example.diegocunha.androidwearpoc.ui.splash;

import android.graphics.drawable.GradientDrawable;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.diegocunha.androidwearpoc.R;
import com.example.diegocunha.androidwearpoc.helper.GradientHelper;

import java.util.Date;

public class SplashActivity extends AppCompatActivity implements SplashView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View layout = findViewById(R.id.activity_splash);

        int[] values = {GradientHelper.colorsForTime(new Date()).get(0), GradientHelper.colorsForTime(new Date()).get(1)};
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, values);
        drawable.setCornerRadius(0f);

        layout.setBackgroundDrawable(drawable);
    }
}
