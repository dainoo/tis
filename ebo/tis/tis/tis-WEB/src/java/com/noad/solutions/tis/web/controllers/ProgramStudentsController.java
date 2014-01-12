/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.ReportParametersCommons;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.report.utils.ReportUtility;
import com.noad.solutions.common.string.contants.utils.PrintOptions;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.web.reportConverters.TisReportConverter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Dawoode
 */
@ManagedBean
@SessionScoped
public class ProgramStudentsController implements Serializable {

    private Student student = new Student();
    private SearchInputs searchInputs = new SearchInputs();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private String selectedReportOption = null;
    private DataModel<Student> studentsDataModel = null;
    private List<Student> listOfStudents = new ArrayList<Student>();

    public ProgramStudentsController() {
    }

    public void viewStudents() {

        try {
            System.out.println("THE SELECTED PROGRAM IS " + selectedItemHelper.getSelectedProgram());
            System.out.println("THE SELECTED level IS " + selectedItemHelper.getSelectedAcademicLevel());
//            listOfStudents = new ArrayList<Student>();
            listOfStudents.clear();
            studentsDataModel = new ListDataModel<Student>();
            listOfStudents = TisEjbSource.getBasicQuariesInstance().getStudentsProgramsAndLevel(selectedItemHelper.getSelectedProgram(), selectedItemHelper.getSelectedAcademicLevel(), selectedItemHelper.getSelectedAcademicYear());
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

    public String searchButton() {
        try {
//            prepareForNewData();
//
//            if (listOfStudents.isEmpty()) {
//                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " For Students");
//                return "";
//            }
            listOfStudents.clear();
            studentsDataModel = new ListDataModel<Student>(listOfStudents);

            if (searchInputs.getSearchParameter().equalsIgnoreCase("studentIndexNumber")) {
                student = TisEjbSource.getBasicQuariesInstance().findStudentByIndexNumber(searchInputs.getSearchValue());
            } else if (searchInputs.getSearchParameter().equalsIgnoreCase("StudentID")) {
                student = TisEjbSource.getCrudInstance().studentFind(searchInputs.getSearchValue());
            }
            System.out.println("THE STUDENT IS "+student.getSurname());
            if (student == null) {
                JSFMessages.errorMessage("No Student Found With " + searchInputs.getSearchParameter() + "\t" + searchInputs.getSearchValue());
                return "";
            }
            listOfStudents.add(student);
            studentsDataModel = new ListDataModel<Student>(listOfStudents);

        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    public void refresh() {

        try {
            prepareForNewData();
            searchInputs = new SearchInputs();
            selectedItemHelper = new SelectedItemHelper();
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public String printSelectedOption() {
        try {
            System.out.println("THE REPORT OPTION IS " + selectedReportOption);
            System.out.println("THE REPORT PROGRAM IS " + selectedItemHelper.getSelectedProgram());
            System.out.println("THE REPORT YEAR IS " + selectedItemHelper.getSelectedAcademicYear());
            System.out.println("THE REPORT LEVEL IS " + selectedItemHelper.getSelectedAcademicLevel());
            System.out.println("THE SIZE OF THE STUDENTS IS " + listOfStudents.size());

            if (listOfStudents.isEmpty()) {
                JSFMessages.warnMessage(JSFMessages.NO_DATA_TO_PRINT);
                return "";
            }
            System.out.println("THE SIZE OF THE STUDENTS IS printtttt   " + listOfStudents.size());
            Program program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());
            HashMap reportParameters = new HashMap();
            reportParameters.put("reportitle", selectedReportOption);
            reportParameters.put("level", selectedItemHelper.getSelectedAcademicLevel());
            reportParameters.put("programName", program.getProgramName());
            reportParameters.putAll(ReportParametersCommons.getCommonParameters());
            ReportUtility reportUtility = new ReportUtility();
            if (selectedReportOption.equals(PrintOptions.STUDENTS_PROGRAM_LIST)) {
                System.out.println("INSIDE STDS PROG LIST");
                reportUtility = new ReportUtility(reportParameters, "students_program_list", TisReportConverter.convertStudentProgramListData(listOfStudents), ReportUtility.PDF_FILE);
            } else if (selectedReportOption.equals(PrintOptions.STUDENTS_CONTACTS)) {
                System.out.println("INSIDE STDS CONTACTS");
                reportUtility = new ReportUtility(reportParameters, "student-contacts", TisReportConverter.convertStudenConvertData(listOfStudents), ReportUtility.PDF_FILE);
            } else if (selectedReportOption.equals(PrintOptions.STUDENTS_DETAILED_INFORMATION)) {
                reportUtility = new ReportUtility(reportParameters, "students-detailed-info", TisReportConverter.convertStudentStudentInfoDetails(listOfStudents), ReportUtility.PDF_FILE);
            }
            reportUtility.generateReport();
            System.out.println("THE REPORT OPTION IS " + selectedReportOption);

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

    public SearchInputs getSearchInputs() {
        return searchInputs;
    }

    public void setSearchInputs(SearchInputs searchInputs) {
        this.searchInputs = searchInputs;
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
