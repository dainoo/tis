/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.pagesrights;

/**
 *
 * @author Daud
 */
public class AppMainMenus {

    public static String SETTINGS = "Settings";
    public static String STAFFS = "Staffs";
    public static String STUDENTS = "Students";
    public static String EXAMINATIONS = "Examinations";
    public static String FINANCIES = "Financies";
    public static String STATISTICS = "Statistics";
    public static String DATA_SECURITY = "Data Security";
    
    public static String SETTINGS_CODE = "SET";
    public static String STAFFS_CODE = "STF";
    public static String STUDENTS_CODE = "STD";
    public static String EXAMINATIONS_CODE = "EXM";
    public static String FINANCIES_CODE = "FIN";
    public static String STATISTICS_CODE = "STA";
    public static String DATA_SECURITY_CODE = "DAS";

    
    public static  String getCode(String code){
        
        return null;
    
    }
    public static String getAppMenuByCode(String code) {
        if (code.equalsIgnoreCase(AppMainMenus.SETTINGS_CODE)) {
            return AppMainMenus.SETTINGS;
        } else if (code.equalsIgnoreCase(AppMainMenus.STAFFS_CODE)) {
            return AppMainMenus.STAFFS;
        } else if (code.equalsIgnoreCase(AppMainMenus.STUDENTS_CODE)) {
            return AppMainMenus.STUDENTS;
        } else if (code.equalsIgnoreCase(AppMainMenus.EXAMINATIONS_CODE)) {
            return AppMainMenus.EXAMINATIONS;
        } else if (code.equalsIgnoreCase(AppMainMenus.STATISTICS_CODE)) {
            return AppMainMenus.STATISTICS;
        } else if (code.equalsIgnoreCase(AppMainMenus.FINANCIES_CODE)) {
            return AppMainMenus.FINANCIES;
        } else if (code.equalsIgnoreCase(AppMainMenus.DATA_SECURITY_CODE)) {
            return AppMainMenus.DATA_SECURITY;
        }
        
        return "NONE";

    }

    public static String getAppMenuCodeByMenuName(String menuName) {
        if (menuName.equalsIgnoreCase(AppMainMenus.SETTINGS)) {
            return AppMainMenus.SETTINGS_CODE;
        } else if (menuName.equalsIgnoreCase(AppMainMenus.STAFFS)) {
            return AppMainMenus.STAFFS_CODE;
        } else if (menuName.equalsIgnoreCase(AppMainMenus.STUDENTS)) {
            return AppMainMenus.STUDENTS_CODE;
        } else if (menuName.equalsIgnoreCase(AppMainMenus.EXAMINATIONS)) {
            return AppMainMenus.EXAMINATIONS_CODE;
        } else if (menuName.equalsIgnoreCase(FINANCIES)) {
            return AppMainMenus.FINANCIES_CODE;
        } else if (menuName.equalsIgnoreCase(AppMainMenus.STATISTICS)) {
            return AppMainMenus.STATISTICS_CODE;
        } else if (menuName.equalsIgnoreCase(AppMainMenus.DATA_SECURITY)) {
            return AppMainMenus.DATA_SECURITY_CODE;
        }
        return "NONE";

    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public static String getSETTINGS() {
        return SETTINGS;
    }
    
    public static String getSTAFFS() {
        return STAFFS;
    }
    
    public static String getSTUDENTS() {
        return STUDENTS;
    }
    
    public static String getEXAMINATIONS() {
        return EXAMINATIONS;
    }
    
    public static String getFINANCIES() {
        return FINANCIES;
    }
    
    public static String getSTATISTICS() {
        return STATISTICS;
    }
    
    public static String getDATA_SECURITY() {
        return DATA_SECURITY;
    }
    //</editor-fold>
}
