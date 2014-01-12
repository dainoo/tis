/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.reportConverters;

/**
 *
 * @author Daud
 */
public class StudentsRegisteredDeferredCourses {

    private String indexNumber = null;
    private String studentId = null;
    private String studentName = null;
    private String coursesRegistered = null;
    private String coursesDeferred = null;
    private int totalCreditRegistetered = 0;
    private int totalCreditDeferred = 0;
    private int totalRegisteredCourses = 0;
    private int totalDeferredCourses = 0;

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getTotalCreditRegistetered() {
        return totalCreditRegistetered;
    }

    public void setTotalCreditRegistetered(int totalCreditRegistetered) {
        this.totalCreditRegistetered = totalCreditRegistetered;
    }

    public int getTotalCreditDeferred() {
        return totalCreditDeferred;
    }

    public void setTotalCreditDeferred(int totalCreditDeferred) {
        this.totalCreditDeferred = totalCreditDeferred;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCoursesRegistered() {
        return coursesRegistered;
    }

    public void setCoursesRegistered(String coursesRegistered) {
        this.coursesRegistered = coursesRegistered;
    }

    public String getCoursesDeferred() {
        return coursesDeferred;
    }

    public void setCoursesDeferred(String coursesDeferred) {
        this.coursesDeferred = coursesDeferred;
    }

    public int getTotalRegisteredCourses() {
        return totalRegisteredCourses;
    }

    public void setTotalRegisteredCourses(int totalRegisteredCourses) {
        this.totalRegisteredCourses = totalRegisteredCourses;
    }

    public int getTotalDeferredCourses() {
        return totalDeferredCourses;
    }

    public void setTotalDeferredCourses(int totalDeferredCourses) {
        this.totalDeferredCourses = totalDeferredCourses;
    }
}
