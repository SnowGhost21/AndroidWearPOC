package com.example.diegocunha.androidwearpoc.ui.splash;

/**
 * Created by diegocunha on 11/01/17.
 */

public interface SplashView {

    void onConnectionSuccesfull(String message);
    void onConnectionSuspend(String message);
    void onConnectionFailed(String message);
}
