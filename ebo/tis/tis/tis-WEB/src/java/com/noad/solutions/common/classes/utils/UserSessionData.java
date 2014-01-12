/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.classes.utils;

import com.noad.solutions.tis.ejb.entities.AcademicYear;
import com.noad.solutions.tis.ejb.entities.SchoolSetup;
import com.noad.solutions.tis.ejb.entities.Staff;

/**
 *
 * @author daud
 */
public class UserSessionData {

    private AcademicYear currentAcademicYear = null;
    private SchoolSetup schoolSetup=null;
    private Staff staff=null;
    private UserAccessPage userAccessPage=new UserAccessPage();
    private boolean canChangeCurrentAcademicYear=false;
    private boolean changeCurrentAcademicYear=false;

    public AcademicYear getCurrentAcademicYear() {
        return currentAcademicYear;
    }

    public void setCurrentAcademicYear(AcademicYear currentAcademicYear) {
        this.currentAcademicYear = currentAcademicYear;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public boolean isChangeCurrentAcademicYear() {
        return changeCurrentAcademicYear;
    }

    public void setChangeCurrentAcademicYear(boolean changeCurrentAcademicYear) {
        this.changeCurrentAcademicYear = changeCurrentAcademicYear;
    }

    public boolean isCanChangeCurrentAcademicYear() {
        return canChangeCurrentAcademicYear;
    }

    public UserAccessPage getUserAccessPage() {
        return userAccessPage;
    }

    public void setUserAccessPage(UserAccessPage userAccessPage) {
        this.userAccessPage = userAccessPage;
    }

    public void setCanChangeCurrentAcademicYear(boolean canChangeCurrentAcademicYear) {
        this.canChangeCurrentAcademicYear = canChangeCurrentAcademicYear;
    }
    public SchoolSetup getSchoolSetup() {
        return schoolSetup;
    }

    public void setSchoolSetup(SchoolSetup schoolSetup) {
        this.schoolSetup = schoolSetup;
    }

   
}
