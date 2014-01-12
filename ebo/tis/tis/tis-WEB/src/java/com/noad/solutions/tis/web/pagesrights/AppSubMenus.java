/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.pagesrights;

/**
 *
 * @author Dawoode
 */
public class AppSubMenus {

    private static String submenuCode = null;
    private static String menuName = null;
    private static String submenuName = null;
    private static String pageName = null;

    public AppSubMenus(String menuName, String submenuCode, String submenuName, String pageName) {
        AppSubMenus.submenuCode = submenuCode;
        AppSubMenus.menuName = menuName;
        AppSubMenus.submenuName = submenuName;
        AppSubMenus.pageName = pageName;
    }

    public static AppSubMenus getAppSubmenu(String submenuCode) {
        if (submenuCode.equals(AppSubmenuCodes.ACADEMIC_YEARS)) {
            return new AppSubMenus(AppMainMenus.SETTINGS, AppSubmenuCodes.ACADEMIC_YEARS, "academic_years", "Academic Years");
        } else if (submenuCode.equals(AppSubmenuCodes.COURSES)) {
            return new AppSubMenus(AppMainMenus.SETTINGS, AppSubmenuCodes.COURSES, "courses", "Courses");
        } else if (submenuCode.equals(AppSubmenuCodes.DEPARTMENTS)) {
            return new AppSubMenus(AppMainMenus.SETTINGS, AppSubmenuCodes.DEPARTMENTS, "departments", "Departments");
        } else if (submenuCode.equals(AppSubmenuCodes.PROGRAMS)) {
            return new AppSubMenus(AppMainMenus.SETTINGS, AppSubmenuCodes.PROGRAMS, "programs", "Programs");
        } else if (submenuCode.equals(AppSubmenuCodes.PROGRAM_COURSES)) {
            return new AppSubMenus(AppMainMenus.SETTINGS, AppSubmenuCodes.PROGRAM_COURSES, "programs_courses", "Programs Courses");
        } else if (submenuCode.equals(AppSubmenuCodes.SCHOOL_SETUP)) {
            return new AppSubMenus(AppMainMenus.SETTINGS, AppSubmenuCodes.SCHOOL_SETUP, "school_setup", "School Setup");
        } else if (submenuCode.equals(AppSubmenuCodes.STAFF_MEMBERS)) {
            return new AppSubMenus(AppMainMenus.SETTINGS, AppSubmenuCodes.STAFF_MEMBERS, "staff_members", "Staff Members");
        } else if (submenuCode.equals(AppSubmenuCodes.USER_ACCOUNTS)) {
            return new AppSubMenus(AppMainMenus.SETTINGS, AppSubmenuCodes.USER_ACCOUNTS, "user_accounts", "User Accounts");
        }
        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public static String getSubmenuCode() {
        return submenuCode;
    }

    public static void setSubmenuCode(String submenuCode) {
        AppSubMenus.submenuCode = submenuCode;
    }

    public static String getMenuName() {
        return menuName;
    }

    public static void setMenuName(String menuName) {
        AppSubMenus.menuName = menuName;
    }

    public static String getSubmenuName() {
        return submenuName;
    }

    public static void setSubmenuName(String submenuName) {
        AppSubMenus.submenuName = submenuName;
    }

    public static String getPageName() {
        return pageName;
    }

    public static void setPageName(String pageName) {
        AppSubMenus.pageName = pageName;
    }
    //</editor-fold>
}
