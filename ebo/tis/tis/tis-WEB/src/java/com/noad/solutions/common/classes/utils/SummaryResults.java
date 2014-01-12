/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.classes.utils;

/**
 *
 * @author Daud
 */
public class SummaryResults {

    private String breadDown = null;
    private String semesterSummary = null;
    private String cummulativeSummary = null;

//    public SummaryResults(String breadDown, String semesterSummary, String cummulativeSummary) {
//        this.breadDown = breadDown;
//        this.semesterSummary = semesterSummary;
//        this.cummulativeSummary = cummulativeSummary;
//    }

    public String getBreadDown() {
        return breadDown;
    }

    public void setBreadDown(String breadDown) {
        this.breadDown = breadDown;
    }

    public String getSemesterSummary() {
        return semesterSummary;
    }

    public void setSemesterSummary(String semesterSummary) {
        this.semesterSummary = semesterSummary;
    }

    public String getCummulativeSummary() {
        return cummulativeSummary;
    }

    public void setCummulativeSummary(String cummulativeSummary) {
        this.cummulativeSummary = cummulativeSummary;
    }
}
