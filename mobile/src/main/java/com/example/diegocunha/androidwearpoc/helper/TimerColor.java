package com.example.diegocunha.androidwearpoc.helper;

/**
 * Created by snowghost on 12/01/17.
 */

public class TimerColor {
    private int hour;
    private RGBColor rgb;

    public TimerColor(int hour, RGBColor rgb) {
        this.hour = hour;
        this.rgb = rgb;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public RGBColor getRgb() {
        return rgb;
    }

    public void setRgb(RGBColor rgb) {
        this.rgb = rgb;
    }
}
