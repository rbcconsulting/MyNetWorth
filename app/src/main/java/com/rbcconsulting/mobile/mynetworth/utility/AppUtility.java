package com.rbcconsulting.mobile.mynetworth.utility;

/**
 * Created by Ralph on 13/08/2017.
 * Class that contain utilities that can be shared by all classes (e.g. Rounding numbers)
 */

public class AppUtility {

    /**
     * Use when rounding numbers to nearest 2 decimal point
     *
     * @param number
     * @return rounded value nearest to 2 decimal point
     */
    public static final double roundNearest2(double number) {
        return (double) Math.round(number * 100) / 100;
    }
}
