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
public class MaritalStatus {

    public static final String MARRIED = "Married";
    public static final String SINGLE = "Single";
    public static final String SEPARATED = "Separated";
    public static final String DIVORCED = "Divorced";

    public static List<String> getMaritalStatus() {
        List<String> listOfMaritalStatus = new ArrayList<String>();
        listOfMaritalStatus.add(MARRIED);
        listOfMaritalStatus.add(SINGLE);
        listOfMaritalStatus.add(SEPARATED);
        listOfMaritalStatus.add(DIVORCED);
        return listOfMaritalStatus;


    }

    public static String getMARRIED() {
        return MARRIED;
    }

    public static String getSINGLE() {
        return SINGLE;
    }

    public static String getSEPARATED() {
        return SEPARATED;
    }

    public static String getDIVORCED() {
        return DIVORCED;
    }
}
