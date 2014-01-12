/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.report.utils;

/**
 *
 * @author Dawoode
 */
public class AppReportParameters {

    public static String reportTile = null;
    public static String reportName=null;
    public static String reportFooterTitle = "Noad Solutions - Tertiary Institution Software : 02450293945";
    public static String printedBy = null;
    public static String personPrintedRole = null;

    public static String getReportTile() {
        return reportTile;
    }

    public static void setReportTile(String reportTile) {
        AppReportParameters.reportTile = reportTile;
    }

    public static String getReportName() {
        return reportName;
    }

    public static void setReportName(String reportName) {
        AppReportParameters.reportName = reportName;
    }

    public static String getReportFooterTitle() {
        return reportFooterTitle;
    }

    public static void setReportFooterTitle(String reportFooterTitle) {
        AppReportParameters.reportFooterTitle = reportFooterTitle;
    }

    public static String getPrintedBy() {
        return printedBy;
    }

    public static void setPrintedBy(String printedBy) {
        AppReportParameters.printedBy = printedBy;
    }

    public static String getPersonPrintedRole() {
        return personPrintedRole;
    }

    public static void setPersonPrintedRole(String personPrintedRole) {
        AppReportParameters.personPrintedRole = personPrintedRole;
    }

   
}
