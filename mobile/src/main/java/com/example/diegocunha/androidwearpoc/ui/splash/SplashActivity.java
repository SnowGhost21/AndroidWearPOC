package com.example.diegocunha.androidwearpoc.ui.splash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.diegocunha.androidwearpoc.R;

public class SplashActivity extends AppCompatActivity implements SplashView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
