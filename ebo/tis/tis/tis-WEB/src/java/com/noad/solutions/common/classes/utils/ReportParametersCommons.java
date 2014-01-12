/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.classes.utils;

import com.noad.solutions.common.jsf.utils.JSFUtility;
import java.util.HashMap;

/**
 *
 * @author Daud
 */
public class ReportParametersCommons {

    private String schoolName = null;
    private String departmentName = null;

    public static HashMap getCommonParameters() {
        UserSessionData userSessionData;
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);
        HashMap hashMap = new HashMap();
        hashMap.put("schoolName", userSessionData.getSchoolSetup().getSchoolName());
        hashMap.put("departmentName", userSessionData.getStaff().getDepartment().getDepartmentName());
        hashMap.put("reportFooter", "Printed From Tertiary Institutions Software (TIS) - 0245 293945 : noadsolutions@gmail.com");
        hashMap.put("academicYear", userSessionData.getCurrentAcademicYear().getAcademicYear() + " SEMESTER " + userSessionData.getCurrentAcademicYear().getSemester()+" ACADEMIC YEAR");


        return hashMap;


    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
