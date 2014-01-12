/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.classes.utils;

import com.noad.solutions.common.number.utils.DecimalPlace;

/**
 *
 * @author gyamfi
 */
public class ExamsGrader {

    private static String CWA = "CWA";
    private static String GPA = "GPA";
    private static String markStyle;

    public static String getGradeStyle() {
        return markStyle;
    }

    public static String CWAGradingSystem(double totalMark) {
        markStyle = null;
        if (totalMark >= 70) {
            markStyle = "color:LimeGreen;font-weight:bolder;";
            return "A";
        } else if (60 <= totalMark && totalMark <= 69) {
            markStyle = "color:MediumBlue;font-weight:bolder;";
            return "B";
        } else if (50 <= totalMark && totalMark <= 59) {
            markStyle = "color:#f28007;font-weight:bolder;";
            return "C";
        } else if (40 <= totalMark && totalMark <= 49) {
            return "D";
        } else if (totalMark <= 39) {
            markStyle = "color:red;font-weight:bolder;";
            return "F";
        }

        return null;

    }

    public static String GPAGradingSystem(double totalMark) {
        System.out.println("THE MARK IS " + totalMark);
        if (totalMark >= 80) {
            return "A";
        } else if (totalMark >= 75 && totalMark <= 79) {
            return "B+";
        } else if (totalMark >= 70 && totalMark <= 74) {
            return "B";
        } else if (totalMark >= 65 && totalMark <= 69) {
            return "C+";
        } else if (totalMark >= 60 && totalMark <= 64) {
            return "C";
        } else if (totalMark >= 55 && totalMark <= 65) {
            return "D+";
        } else if (totalMark >= 50 && totalMark <= 54) {
            return "D";
        } else if (totalMark < 50) {
            return "E";
        }

        return null;

    }

    public static double GPAGradePoint(String grade) {
        markStyle = null;

        if (grade.equals("A")) {
            markStyle = "color:LimeGreen;font-weight:bolder;";
            return 4.00;
        } else if (grade.equals("B+")) {
            markStyle = "color:MediumBlue;font-weight:bolder;";
            return 3.50;
        } else if (grade.equals("B")) {
            markStyle = "color:#f28007;font-weight:bolder;";
            return 3.00;
        } else if (grade.equals("C+")) {
            return 2.50;
        } else if (grade.equals("C")) {
            return 2.00;
        } else if (grade.equals("D+")) {
            return 1.50;
        } else if (grade.equals("D")) {
            markStyle = "color:black;font-weight:bolder;";
            return 1.00;
        } else if (grade.equals("E")) {
            markStyle = "color:red;font-weight:bolder;";
            return 0.00;
        }
        return 0;
    }

    public static double findAverage(double minMark, double maxMark) {
        try {

            double average = DecimalPlace.getTwoDecimalPlaces((minMark + maxMark) / 2);
            return average;
        } catch (Exception e) {
        }
        return 0;


    }

    public static double findSTD(double minMark, double maxMark, double average) {
        try {

            double total = (Math.pow((minMark - average), 2) + Math.pow((maxMark - average), 2));
            double std = Math.sqrt(total);
            std = DecimalPlace.getTwoDecimalPlaces(std);
            return std;
        } catch (Exception e) {
        }
        return 0;

    }

    public static String getCWA() {
        return CWA;
    }

    public static void setCWA(String CWA) {
        ExamsGrader.CWA = CWA;
    }

    public static String getGPA() {
        return GPA;
    }

    public static void setGPA(String GPA) {
        ExamsGrader.GPA = GPA;
    }
    
}
