/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.reportConverters;

/**
 *
 * @author Daud
 */
public class ExamsRawScoreSheetData {

    private String studentName = null;
    private String indexNumber = null;
    private String midTermMark = null;
    private String examsMark = null;
    private String counter = null;
    private String certify = null;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getIndexNumber() {
        return indexNumber;
    }

    public String getMidTermMark() {
        return midTermMark;
    }

    public void setMidTermMark(String midTermMark) {
        this.midTermMark = midTermMark;
    }

    public String getExamsMark() {
        return examsMark;
    }

    public void setExamsMark(String examsMark) {
        this.examsMark = examsMark;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }
    
    public String getCertify() {
        return certify;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public void setCertify(String certify) {
        this.certify = certify;
    }
}
