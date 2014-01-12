/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.number.utils;

/**
 *
 * @author Daud
 */
public class DecimalPlace {

    public static double getTwoDecimalPlaces(double number) {
        if (number < 0) {
            return 0.00;
        } else if (number >= 0) {
            double roundedNumber = Math.round(number * 100.00) / 100.00;
            return roundedNumber;
        }
        return 0.00;
    }
}
