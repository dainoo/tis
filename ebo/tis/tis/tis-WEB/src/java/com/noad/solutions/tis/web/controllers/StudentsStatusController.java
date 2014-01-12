/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.ReportParametersCommons;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.report.utils.ReportUtility;
import com.noad.solutions.common.string.contants.utils.PrintOptions;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.web.reportConverters.TisReportConverter;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;

/**
 *
 * @author Dawoode
 */
@Named(value = "studentsStatusController")
@SessionScoped
public class StudentsStatusController implements Serializable {

    private Student student = new Student();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private String selectedReportOption = null;
    private DataModel<Student> studentsDataModel = null;
    private List<Student> listOfStudents = new ArrayList<Student>();

    public StudentsStatusController() {
    }

    public void viewStudents() {

        try {
            prepareForNewData();
            System.out.println("THE SELECTED PROGRAM IS " + selectedItemHelper.getSelectedProgram());
            System.out.println("THE SELECTED level IS " + selectedItemHelper.getSelectedAcademicLevel());
            System.out.println("THE SELECTED level IS " + selectedReportOption);
            selectedReportOption = PrintOptions.CURRENT_PARAMETERS;
            listOfStudents = TisEjbSource.getBasicQuariesInstance().getStudentsAcademicStatus(selectedItemHelper.getSelectedProgram(), selectedItemHelper.getSelectedAcademicLevel(), selectedItemHelper.getSelectedAcademicYear(), selectedItemHelper.getSelectedAcademicStatus(), selectedReportOption);
            System.out.println("THE SIZE OF STUDENTS IS " + listOfStudents.size());
            if (listOfStudents.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " For Students");
            } else {
                studentsDataModel = new ListDataModel<Student>(listOfStudents);
            }
        } catch (Exception e) {
            appLogger(e);
        }

    }

    public void refresh() {

        try {

            prepareForNewData();
            selectedReportOption = null;
            selectedItemHelper = new SelectedItemHelper();
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public String printSelectedOption() {
        try {
            System.out.println("THE SELECTED PROGRAM IS " + selectedItemHelper.getSelectedProgram());
            System.out.println("THE SELECTED level IS " + selectedItemHelper.getSelectedAcademicLevel());
            System.out.println("THE SELECTED print option " + selectedReportOption);
            System.out.println("THE SELECTED status " + selectedItemHelper.selectedAcademicStatus);
            System.out.println("THE SELECTED ACADEMIC YEAR " + selectedItemHelper.selectedAcademicYear);
            System.out.println("THE SIZE OF DATA TO BE PRINTED " + listOfStudents.size());
            Program program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());
            if (listOfStudents.isEmpty()) {
                JSFMessages.warnMessage(JSFMessages.NO_DATA_TO_PRINT);
                return "";
            }
            String reportTitle = "";
            if (selectedReportOption.equalsIgnoreCase("STUDENTS_WITH_SELECTED_ACADEMIC_STATUS")) {
                reportTitle = "STUDENTS IN " + selectedItemHelper.getSelectedAcademicLevel() + " WITH ACADEMIC STATUS - ";
            } else {
                reportTitle = "STUDENTS READING " + program.getProgramName() + " WITH ACADEMIC STATUS - ";
            }
            reportTitle += selectedItemHelper.getSelectedAcademicStatus();
            HashMap reportParameters = new HashMap();
            reportParameters.put("reportitle",reportTitle.toUpperCase());
            reportParameters.put("programName", program.getProgramName());
            reportParameters.putAll(ReportParametersCommons.getCommonParameters());
            ReportUtility reportUtility = new ReportUtility(reportParameters, "student-status", TisReportConverter.convertStudentStatus(listOfStudents), ReportUtility.PDF_FILE);
            reportUtility.generateReport();

        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    void prepareForNewData() {
        listOfStudents = new ArrayList<Student>();
        studentsDataModel = new ListDataModel<Student>(listOfStudents);
    }

    void appLogger(Exception e) {
        e.printStackTrace();
        Logger.getLogger(CoursesController.class.getName()).log(Level.SEVERE, null, e.getCause());
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public String getSelectedReportOption() {
        return selectedReportOption;
    }

    public void setSelectedReportOption(String selectedReportOption) {
        this.selectedReportOption = selectedReportOption;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public SelectedItemHelper getSelectedItemHelper() {
        return selectedItemHelper;
    }

    public void setSelectedItemHelper(SelectedItemHelper selectedItemHelper) {
        this.selectedItemHelper = selectedItemHelper;
    }

    public DataModel<Student> getStudentsDataModel() {
        return studentsDataModel;
    }

    public void setStudentsDataModel(DataModel<Student> studentsDataModel) {
        this.studentsDataModel = studentsDataModel;
    }

    public List<Student> getListOfStudents() {
        return listOfStudents;
    }

    public void setListOfStudents(List<Student> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }
    //</editor-fold>
}
