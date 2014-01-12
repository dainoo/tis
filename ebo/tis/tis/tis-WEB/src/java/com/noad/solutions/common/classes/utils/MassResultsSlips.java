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
public class MassResultsSlips {

    private String studentName = null;
    private String indexNumber = null;
    private String studentId = null;
    private String nationality = null;
    private String level = null;
    private String programName = null;
    private double semCrdtReg = 0.0, semCrdtObt = 0.0, cummCrdtReg = 0.0, cummCrdtObt = 0.0;
    private double semWtdMark = 0.0, semAvg = 0.0, cummWtdMark = 0.0, cummAvg = 0.0;
    private List<MassCoursesMarksDetails> listOfCoursesMarksDetails = new ArrayList<MassCoursesMarksDetails>();

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



    public double getSemCrdtReg() {
        return semCrdtReg;
    }

    public void setSemCrdtReg(double semCrdtReg) {
        this.semCrdtReg = semCrdtReg;
    }

    public double getSemCrdtObt() {
        return semCrdtObt;
    }

    public void setSemCrdtObt(double semCrdtObt) {
        this.semCrdtObt = semCrdtObt;
    }

    public double getCummCrdtReg() {
        return cummCrdtReg;
    }

    public void setCummCrdtReg(double cummCrdtReg) {
        this.cummCrdtReg = cummCrdtReg;
    }

    public double getCummCrdtObt() {
        return cummCrdtObt;
    }

    public void setCummCrdtObt(double cummCrdtObt) {
        this.cummCrdtObt = cummCrdtObt;
    }

    public double getSemWtdMark() {
        return semWtdMark;
    }

    public void setSemWtdMark(double semWtdMark) {
        this.semWtdMark = semWtdMark;
    }

    public double getSemAvg() {
        return semAvg;
    }

    public void setSemAvg(double semAvg) {
        this.semAvg = semAvg;
    }

    public List<MassCoursesMarksDetails> getListOfCoursesMarksDetails() {
        return listOfCoursesMarksDetails;
    }

    public void setListOfCoursesMarksDetails(List<MassCoursesMarksDetails> listOfCoursesMarksDetails) {
        this.listOfCoursesMarksDetails = listOfCoursesMarksDetails;
    }

    public double getCummWtdMark() {
        return cummWtdMark;
    }

    public void setCummWtdMark(double cummWtdMark) {
        this.cummWtdMark = cummWtdMark;
    }

    public double getCummAvg() {
        return cummAvg;
    }

    public void setCummAvg(double cummAvg) {
        this.cummAvg = cummAvg;
    }

}
