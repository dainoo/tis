/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.pagesrights;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daud
 */
public class AppPages {

    private static final String FACES_REDIRECT = ".xhtml";
    public static List<AppPageSettings> listOfAppPageSettingses = null;

    private static void appLogger(Exception e) {
        Logger.getLogger(AppPages.class.getName()).log(Level.SEVERE, null, e.getCause());
    }

    public AppPages() {
    }

    public static List<AppPageSettings> settingsSubMenus() {
        try {
            listOfAppPageSettingses = new ArrayList<AppPageSettings>();
            listOfAppPageSettingses.add(new AppPageSettings(1, false, AppMainMenus.SETTINGS, "School Information", "school_setup"));
            listOfAppPageSettingses.add(new AppPageSettings(1, false, AppMainMenus.SETTINGS, "Departments", "departments"));
            listOfAppPageSettingses.add(new AppPageSettings(1, false, AppMainMenus.SETTINGS, "Programs", "programs"));
            listOfAppPageSettingses.add(new AppPageSettings(1, false, AppMainMenus.SETTINGS, "Courses", "courses"));
            listOfAppPageSettingses.add(new AppPageSettings(1, false, AppMainMenus.SETTINGS, "Programs' Courses", "program_courses"));
            listOfAppPageSettingses.add(new AppPageSettings(1, false, AppMainMenus.SETTINGS, "Academic Year", "academic_years"));
        } catch (Exception e) {
            appLogger(e);

        }
        return listOfAppPageSettingses;


    }

    public static List<AppPageSettings> staffSubMenus() {
        try {
            listOfAppPageSettingses = new ArrayList<AppPageSettings>();
            listOfAppPageSettingses.add(new AppPageSettings(2, false, AppMainMenus.STAFFS, "Staff Members", "staff_members"));
            listOfAppPageSettingses.add(new AppPageSettings(2, false, AppMainMenus.STAFFS, "Department Heads", "department_heads"));
            listOfAppPageSettingses.add(new AppPageSettings(2, false, AppMainMenus.STAFFS, "User Account", "general_user_accounts"));
        } catch (Exception e) {
            appLogger(e);

        }
        return listOfAppPageSettingses;
    }

    public static List<AppPageSettings> studentsSubMenus() {
        try {
            listOfAppPageSettingses = new ArrayList<AppPageSettings>();
            listOfAppPageSettingses.add(new AppPageSettings(3, false, AppMainMenus.STUDENTS, "Student Information", "students"));
            listOfAppPageSettingses.add(new AppPageSettings(3, false, AppMainMenus.STUDENTS, "Program Students", "program_students"));
            listOfAppPageSettingses.add(new AppPageSettings(3, false, AppMainMenus.STUDENTS, "Students Academic Status", "students_status"));
            listOfAppPageSettingses.add(new AppPageSettings(3, false, AppMainMenus.STUDENTS, "Promotions", "promotions"));
            listOfAppPageSettingses.add(new AppPageSettings(3, false, AppMainMenus.STUDENTS, "Transcript ", "transcript"));
        } catch (Exception e) {
            appLogger(e);

        }
        return listOfAppPageSettingses;

    }

    public static List<AppPageSettings> examinationsSubMenus() {
        try {

            listOfAppPageSettingses = new ArrayList<AppPageSettings>();
            listOfAppPageSettingses.add(new AppPageSettings(4, false, AppMainMenus.EXAMINATIONS, "Lecturers' Courses", "lecturer_courses"));
            listOfAppPageSettingses.add(new AppPageSettings(4, false, AppMainMenus.EXAMINATIONS, "Course Registrations", "course_registration"));
            listOfAppPageSettingses.add(new AppPageSettings(4, false, AppMainMenus.EXAMINATIONS, "Registered Students", "registered_students"));
            listOfAppPageSettingses.add(new AppPageSettings(4, false, AppMainMenus.EXAMINATIONS, "Marks Upload", "marks_upload"));
            listOfAppPageSettingses.add(new AppPageSettings(4, false, AppMainMenus.EXAMINATIONS, "Uploaded Marks", "uploaded_marks"));
            listOfAppPageSettingses.add(new AppPageSettings(4, false, AppMainMenus.EXAMINATIONS, "Marks Amendements", "marks_amendments"));
            listOfAppPageSettingses.add(new AppPageSettings(4, false, AppMainMenus.EXAMINATIONS, "Results Analysis", "course_statistics"));
        } catch (Exception e) {
            appLogger(e);
        }
        return listOfAppPageSettingses;



    }

    public static List<AppPageSettings> financiesSubMenus() {
        try {
            listOfAppPageSettingses = new ArrayList<AppPageSettings>();
            listOfAppPageSettingses.add(new AppPageSettings(5, false, AppMainMenus.FINANCIES, "Cards Availability", "cards_availability"));
            listOfAppPageSettingses.add(new AppPageSettings(5, false, AppMainMenus.FINANCIES, "Claim Reports", "claim_reports"));
            listOfAppPageSettingses.add(new AppPageSettings(5, false, AppMainMenus.FINANCIES, "Tariffs", "tariffs"));
        } catch (Exception e) {
            appLogger(e);

        }
        return listOfAppPageSettingses;
    }

    public static List<AppPageSettings> dataSecuritySubMenus() {
        try {
            listOfAppPageSettingses = new ArrayList<AppPageSettings>();
            listOfAppPageSettingses.add(new AppPageSettings(6, false, AppMainMenus.DATA_SECURITY, "Data Backup", "database_backup"));
        } catch (Exception e) {
            appLogger(e);

        }
        return listOfAppPageSettingses;
    }

    public static List<AppPageSettings> getListOfAppPageSettingses() {
        return listOfAppPageSettingses;
    }

    public static void setListOfAppPageSettingses(List<AppPageSettings> listOfAppPageSettingses) {
        AppPages.listOfAppPageSettingses = listOfAppPageSettingses;
    }
}
