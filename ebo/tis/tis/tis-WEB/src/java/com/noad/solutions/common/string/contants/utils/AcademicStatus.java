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
public class AcademicStatus {

    public static final String FRESHER = "Fresher";
    public static final String CONTINUING = "Continuing";
    public static final String WITHDRAWN = "Withdrawn";
    public static final String PROBATION = "Probation";
    public static final String RASTICATED = "Rasticated";
    public static final String REPEATED = "Repeated";
    public static final String COMPLETED = "Completed";

    public static List<String> getAcademicStatus() {

        List<String> listOfAcademicStatus = new ArrayList<String>();
        listOfAcademicStatus.add(FRESHER);
        listOfAcademicStatus.add(CONTINUING);
        listOfAcademicStatus.add(COMPLETED);
        listOfAcademicStatus.add(WITHDRAWN);
        listOfAcademicStatus.add(PROBATION);
        listOfAcademicStatus.add(RASTICATED);
        listOfAcademicStatus.add(REPEATED);
        return listOfAcademicStatus;

    }
}
