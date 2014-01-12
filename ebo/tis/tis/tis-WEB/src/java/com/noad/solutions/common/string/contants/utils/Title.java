/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.string.contants.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dawoode
 */
public class Title {

    public static final String MR = "Mr";
    public static final String MS = "Ms";
    public static final String MISS = "Miss";
    public static final String DR = "Dr.";
    public static final String PROF = "Prof.";
    public static final String SIS = "Sis";
    public static final String REV = "REV.";
    public static final String VERY_REV = "Very Rev.";
    public static final String PASTOR = "Pastor";
    public static final String MASTER = "Master";
    public static final String MADAM = "Madam";

    public static List<String> getTitles() {
        List<String> listOfTitles = new ArrayList<String>();
        listOfTitles.add(PROF);
        listOfTitles.add(Title.DR);
        listOfTitles.add(VERY_REV);
        listOfTitles.add(REV);
        listOfTitles.add(PASTOR);
        listOfTitles.add(SIS);
        listOfTitles.add(MR);
        listOfTitles.add(MASTER);
        listOfTitles.add(MADAM);
        
        return listOfTitles;

    }
}
