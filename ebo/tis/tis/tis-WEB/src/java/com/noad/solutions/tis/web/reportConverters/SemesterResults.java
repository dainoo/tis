/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.reportConverters;

import com.noad.solutions.common.classes.utils.ExamsResultsCommons;
import java.util.List;

/**
 *
 * @author Daud
 */
public class SemesterResults {

    private String schoolName = null;
    private String schoolAddress = null;
    private String schoolContact = null;
    private String studentName = null;
    private String indexNumber = null;
    private String studentId = null;
    private String nationality = null;
    private String studentlevel = null;
    private String programName = null;
    private List<ExamsResultsCommons> listSubreport;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolContact() {
        return schoolContact;
    }

    public void setSchoolContact(String schoolContact) {
        this.schoolContact = schoolContact;
    }

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

    public String getStudentlevel() {
        return studentlevel;
    }

    public void setStudentlevel(String studentlevel) {
        this.studentlevel = studentlevel;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public List<ExamsResultsCommons> getListSubreport() {
        return listSubreport;
    }

    public void setListSubreport(List<ExamsResultsCommons> listSubreport) {
        this.listSubreport = listSubreport;
    }
    
    
    
}
