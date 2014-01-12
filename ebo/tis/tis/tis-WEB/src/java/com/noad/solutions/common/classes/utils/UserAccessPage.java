
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.classes.utils;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author daud
 */
@Named(value = "userAccessPageController")
@SessionScoped
public class UserAccessPage implements Serializable {

    private final static String display = "display:inline;";
    private final static String hide = "display:none;";
    private String readRight = UserAccessPage.hide;
    private String writeRight = UserAccessPage.hide;
    private String printRight = UserAccessPage.hide;
    private String accessRight = UserAccessPage.hide;
    private String readSchoolSetup = UserAccessPage.display;
    private String writeSchoolSetup = UserAccessPage.display;
    private String readDepartment = UserAccessPage.display;
    private String writeDepartment = UserAccessPage.display;
    private String readProgram = UserAccessPage.display;
    private String writeProgram = UserAccessPage.display;
    private String readCourse = UserAccessPage.display;
    private String writeCourse = UserAccessPage.display;
    private String SCH_R = UserAccessPage.hide;
    private String SCH_W = UserAccessPage.hide;
    private String SCH_A = UserAccessPage.hide;
    private String DPT_R = UserAccessPage.hide;
    private String DPT_W = UserAccessPage.hide;
    private String DPT_A = UserAccessPage.hide;

    public String getReadSchoolSetup() {
        return readSchoolSetup;
    }

    public void setReadSchoolSetup(String readSchoolSetup) {
        this.readSchoolSetup = readSchoolSetup;
    }

    public String getWriteSchoolSetup() {
        return writeSchoolSetup;
    }

    public static String getDisplay() {
        return display;
    }

    public static String getHide() {
        return hide;
    }

    public String getReadRight() {
        return readRight;
    }

    public void setReadRight(String readRight) {
        this.readRight = readRight;
    }

    public String getWriteRight() {
        return writeRight;
    }

    public void setWriteRight(String writeRight) {
        this.writeRight = writeRight;
    }

    public String getPrintRight() {
        return printRight;
    }

    public void setPrintRight(String printRight) {
        this.printRight = printRight;
    }

    public String getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(String accessRight) {
        this.accessRight = accessRight;
    }

    public void setWriteSchoolSetup(String writeSchoolSetup) {
        this.writeSchoolSetup = writeSchoolSetup;
    }

    public String getReadDepartment() {
        return readDepartment;
    }

    public void setReadDepartment(String readDepartment) {
        this.readDepartment = readDepartment;
    }

    public String getWriteDepartment() {
        return writeDepartment;
    }

    public void setWriteDepartment(String writeDepartment) {
        this.writeDepartment = writeDepartment;
    }

    public String getReadProgram() {
        return readProgram;
    }

    public void setReadProgram(String readProgram) {
        this.readProgram = readProgram;
    }

    public String getWriteProgram() {
        return writeProgram;
    }

    public void setWriteProgram(String writeProgram) {
        this.writeProgram = writeProgram;
    }

    public String getReadCourse() {
        return readCourse;
    }

    public void setReadCourse(String readCourse) {
        this.readCourse = readCourse;
    }

    public String getWriteCourse() {
        return writeCourse;
    }

    public void setWriteCourse(String writeCourse) {
        this.writeCourse = writeCourse;
    }
}
