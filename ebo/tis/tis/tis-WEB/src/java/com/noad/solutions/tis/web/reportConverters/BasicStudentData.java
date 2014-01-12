/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.reportConverters;

/**
 *
 * @author Dawoode
 */
public class BasicStudentData {

    private String counter = null;
    private String gender=null;
    private String studentNumber = null;
    private String studentIndexNumber = null;
    private String studentFullNameWithTitle = null;
    private String currentLevel = null;
    private String programName = null;
    private String departmentName = null;

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStudentIndexNumber() {
        return studentIndexNumber;
    }

    public void setStudentIndexNumber(String studentIndexNumber) {
        this.studentIndexNumber = studentIndexNumber;
    }

    public String getStudentFullNameWithTitle() {
        return studentFullNameWithTitle;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void setStudentFullNameWithTitle(String studentFullNameWithTitle) {
        this.studentFullNameWithTitle = studentFullNameWithTitle;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }


}
