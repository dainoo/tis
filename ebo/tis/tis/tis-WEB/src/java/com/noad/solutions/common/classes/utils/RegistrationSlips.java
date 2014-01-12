/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.classes.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daud
 */
public class RegistrationSlips {

    private String studentName = null;
    private String indexNumber = null;
    private String studentId = null;
    private String nationality = null;
    private String level = null;
    private String programName = null;
    private int totalDeferredCredits = 0, totalRegisteredCredits = 0, numberOfSubjects = 0;
    private List<RegistrationCoursesDetails> listOfRegistrationCoursesDetails = new ArrayList<RegistrationCoursesDetails>();

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getTotalDeferredCredits() {
        return totalDeferredCredits;
    }

    public void setTotalDeferredCredits(int totalDeferredCredits) {
        this.totalDeferredCredits = totalDeferredCredits;
    }

    public int getTotalRegisteredCredits() {
        return totalRegisteredCredits;
    }

    public void setTotalRegisteredCredits(int totalRegisteredCredits) {
        this.totalRegisteredCredits = totalRegisteredCredits;
    }

    public int getNumberOfSubjects() {
        return numberOfSubjects;
    }

    public void setNumberOfSubjects(int numberOfSubjects) {
        this.numberOfSubjects = numberOfSubjects;
    }

    public List<RegistrationCoursesDetails> getListOfRegistrationCoursesDetails() {
        return listOfRegistrationCoursesDetails;
    }

    public void setListOfRegistrationCoursesDetails(List<RegistrationCoursesDetails> listOfRegistrationCoursesDetails) {
        this.listOfRegistrationCoursesDetails = listOfRegistrationCoursesDetails;
    }

    
}
