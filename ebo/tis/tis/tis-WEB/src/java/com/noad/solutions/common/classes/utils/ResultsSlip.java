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
public class ResultsSlip {

    private String studentName = null;
    private String indexNumber = null;
    private String studentId = null;
    private String nationality = null;
    private String level = null;
    private String programName = null;
    private List<CoursesMarksDetails> listOfCoursesMarksDetails = new ArrayList<CoursesMarksDetails>();

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

    public List<CoursesMarksDetails> getListOfCoursesMarksDetails() {
        return listOfCoursesMarksDetails;
    }

    public void setListOfCoursesMarksDetails(List<CoursesMarksDetails> listOfCoursesMarksDetails) {
        this.listOfCoursesMarksDetails = listOfCoursesMarksDetails;
    }




    

    
    
}
