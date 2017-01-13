package com.example.diegocunha.androidwearpoc.helper;

import android.graphics.Color;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by snowghost on 12/01/17.
 */

public class GradientHelper {

    private static List<TimerColor> allColors() {
        List<TimerColor> colors = new ArrayList<>();
        colors.add(new TimerColor(0, new RGBColor(84, 108, 138)));
        colors.add(new TimerColor(2, new RGBColor(52, 48, 116)));
        colors.add(new TimerColor(4, new RGBColor(0, 59, 73)));
        colors.add(new TimerColor(6, new RGBColor(49, 109, 147)));
        colors.add(new TimerColor(8, new RGBColor(100, 179, 244)));
        colors.add(new TimerColor(10, new RGBColor(194, 229, 156)));
        colors.add(new TimerColor(12, new RGBColor(80, 140, 221)));
        colors.add(new TimerColor(14, new RGBColor(115, 222, 245)));
        colors.add(new TimerColor(16, new RGBColor(225, 114, 110)));
        colors.add(new TimerColor(18, new RGBColor(255, 195, 113)));
        colors.add(new TimerColor(20, new RGBColor(249, 110, 154)));
        colors.add(new TimerColor(22, new RGBColor(151, 116, 253)));


        return colors;
    }


    public static TimerColor fromColor(int hour) {
        int position = 0;
        for (int i = 0; i < allColors().size(); i++) {
            if(allColors().get(i).getHour() <= hour){
                position = i;
            }
        }

        TimerColor color = allColors().get(position);

        return color;
    }

    public static TimerColor toColor(int hour) {
        TimerColor color = fromColor(hour);

        for (int i = 0; i < allColors().size(); i++) {
            if (allColors().get(i).getHour() == color.getHour()) {
                int toIndex = (i  + 1) % allColors().size();
                return allColors().get(toIndex);
            }
        }
        return null;
    }


    public static TimerColor nextColor(int hour){
        TimerColor color = toColor(hour);

        for (int i = 0; i < allColors().size(); i++) {

            if(color != null){
                if (allColors().get(i).getHour() == color.getHour()) {
                    int toIndex = (i  + 1) % allColors().size();
                    return allColors().get(toIndex);
                }
            }

        }
        return null;
    }

    private static List<RGBColor> rgbColorForTime(Date date){
        int hour = date.getHours();
        int minute = date.getMinutes();

        TimerColor fromColor = fromColor(hour);
        TimerColor toColor = toColor(hour);
        TimerColor nextColor = nextColor(hour);


        int differenceHour = hour - fromColor.getHour();
        int differenceMinutes = differenceHour * 60;
        double targetMinute = (double) ((minute + differenceMinutes) / 120);

        List<RGBColor> colors=  new ArrayList<>();
        colors.add(interpolate(fromColor.getRgb(), toColor.getRgb(), targetMinute));
        colors.add(interpolate(toColor.getRgb(), nextColor.getRgb(), targetMinute));

        return Arrays.asList(interpolate(fromColor.getRgb(), toColor.getRgb(), targetMinute),
                interpolate(toColor.getRgb(), nextColor.getRgb(), targetMinute));
    }


    private static RGBColor interpolate(RGBColor fromRGB,RGBColor toRGB, double constant){
        int red = (int) ((fromRGB.getRed() + (toRGB.getRed() - fromRGB.getRed())) * constant);
        int green = (int) ((fromRGB.getGreen() + (toRGB.getGreen() - fromRGB.getGreen())) * constant);
        int blue = (int) ((fromRGB.getBlue() + (toRGB.getBlue() - fromRGB.getBlue())) * constant);

        return new RGBColor(red, green, blue);
    }

    public static List<Integer> colorsForTime(Date date){
        List<RGBColor> colors = rgbColorForTime(date);
        RGBColor colorOne = new RGBColor(100, 179, 244);
        RGBColor colorTwo = new RGBColor(194, 229, 156);


       return Arrays.asList(Color.rgb(colorOne.getRed(), colorOne.getGreen(), colorOne.getBlue()),
               Color.rgb(colorTwo.getRed(), colorTwo.getGreen(), colorTwo.getBlue()));

    }
}
