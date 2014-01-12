/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.BinderSplitter;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.RegistrationCoursesDetails;
import com.noad.solutions.common.classes.utils.RegistrationSlips;
import com.noad.solutions.common.classes.utils.ReportParametersCommons;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.common.report.utils.ReportUtility;
import com.noad.solutions.common.string.contants.utils.PrintOptions;
import com.noad.solutions.tis.ejb.entities.Course;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.ProgramCourse;
import com.noad.solutions.tis.ejb.entities.SemesterRegistration;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.web.reportConverters.TisReportConverter;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 *
 * @author Daud
 */
@Named(value = "registeredStudentsController")
@SessionScoped
public class RegisteredStudentsController implements Serializable {

    private Student student = new Student();
    private SemesterRegistration registration = new SemesterRegistration();
    private List<SemesterRegistration> listOfRegistrations;
    private DataModel<SemesterRegistration> registrationDataModel;
    private SearchInputs searchInputs = new SearchInputs();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private SelectItem[] programCourseSelectItems = null;
    private List<Course> listOfCourses = null;
    private String selectedReportOption = null;
    private UserSessionData userSessionData;

    public RegisteredStudentsController() {
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);
    }

    public void programCourseListener() {

        try {
            List<Course> tempCourse = new ArrayList<Course>();
            listOfCourses = new ArrayList<Course>();
            String currentSemester = TisEjbSource.getBasicQuariesInstance().getCurrentAcademicYear().getSemester();
            List<ProgramCourse> listOfProgramCourses = TisEjbSource.getBasicQuariesInstance().getProgramCourses(selectedItemHelper.getSelectedProgram(),
                    selectedItemHelper.getSelectedAcademicLevel(), currentSemester);
            for (ProgramCourse eachProgramCourse : listOfProgramCourses) {
                tempCourse = (BinderSplitter.coursesSplitterList(eachProgramCourse.getCourses()));
                for (Course eachCourse : tempCourse) {
                    listOfCourses.add(eachCourse);
                }
            }
            System.out.println("THE SIZE OF PROGRAM COURSES IS " + tempCourse.size());
            programCourseSelectItems = new SelectItem[listOfCourses.size()];
//            programCourseSelectItems[0] = new SelectItem("", "PLEASE COURSE");
            int counter = 0;
            for (Course eachCourse : listOfCourses) {
                programCourseSelectItems[counter] = new SelectItem(eachCourse.getCourseId(), (eachCourse.getCourseInitials() + " " + eachCourse.getCourseCode() + " " + eachCourse.getCourseName()));
                counter++;
            }

        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void viewStudents() {

        setListOfRegistrations(new ArrayList<SemesterRegistration>());
        setRegistrationDataModel(new ListDataModel<SemesterRegistration>(getListOfRegistrations()));
        try {
            listOfRegistrations = TisEjbSource.getBasicQuariesInstance().getSemesterRegisteredStudents(selectedItemHelper.getSelectedProgram(), selectedItemHelper.getSelectedAcademicLevel());
            System.out.println("THE SIZE OF REGISTER STDS IS " + listOfRegistrations.size());
            if (listOfRegistrations.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " Registered Courses");
                return;
            }
            registrationDataModel = new ListDataModel<SemesterRegistration>(listOfRegistrations);

        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void refresh() {
        try {
            searchInputs = new SearchInputs();
            selectedItemHelper = new SelectedItemHelper();
            listOfRegistrations = new ArrayList<SemesterRegistration>();
            registrationDataModel = new ListDataModel<SemesterRegistration>(listOfRegistrations);
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public String searchButton() {
        try {
            listOfRegistrations = new ArrayList<SemesterRegistration>();
            registrationDataModel = new ListDataModel<SemesterRegistration>(listOfRegistrations);
            if (searchInputs.getSearchParameter().equalsIgnoreCase("Index Number")) {
                student = TisEjbSource.getBasicQuariesInstance().findStudentByIndexNumber(searchInputs.getSearchValue());
            } else if (searchInputs.getSearchParameter().equalsIgnoreCase("Student ID")) {
                student = TisEjbSource.getCrudInstance().studentFind(searchInputs.getSearchValue());
            }
            if (student == null) {
                JSFMessages.errorMessage("No Student Found With " + searchInputs.getSearchParameter() + "\t" + searchInputs.getSearchValue());
                return "";
            }
            listOfRegistrations = TisEjbSource.getCrudInstance().semesterRegistrationFindByAttribute("student.studentId", student.getStudentId(), "STRING", false);
            registrationDataModel = new ListDataModel<SemesterRegistration>(listOfRegistrations);

        } catch (Exception e) {
            appLogger(e);
        }
        return "";

    }

    public void printCommonReports() {
        System.out.println("inside commons");
        try {

            Course course = TisEjbSource.getCrudInstance().courseFind(selectedItemHelper.getSelectedCourse());
            Program program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());
            String courseCodeAndInitials = course.getCourseInitials() + " " + course.getCourseCode();
            List<SemesterRegistration> listOfCourseRegisteredStudents = TisEjbSource.getExamsProcessingInstance().getStudentsRegisteredForACourse(selectedItemHelper.getSelectedProgram(),
                    courseCodeAndInitials, TisEjbSource.getBasicQuariesInstance().getCurrentAcademicYear().getAcademicYear());
            String courseName = course.getCourseInitials() + " " + course.getCourseCode() + " " + course.getCourseName();
            HashMap reportParameters = new HashMap();
            reportParameters.put("level", selectedItemHelper.getSelectedAcademicLevel());
            reportParameters.put("programName", program.getProgramName());
            reportParameters.put("courseName", course.getCourseInitials() + " " + course.getCourseCode() + " " + course.getCourseName());
            reportParameters.putAll(ReportParametersCommons.getCommonParameters());
            System.out.println("THE SELECTED OPTION IS " + selectedReportOption);
            System.out.println("THE SELECTED COURSE IS " + courseCodeAndInitials);
            ReportUtility reportUtility;

            if (listOfCourseRegisteredStudents.isEmpty()) {
                JSFMessages.warnMessage(JSFMessages.NO_DATA_TO_PRINT);
                return;
            }
            System.out.println("THE LIST IS " + listOfCourseRegisteredStudents.size());
            if (selectedReportOption.equalsIgnoreCase(PrintOptions.STUDENTS_REGISTERED_SELECTED_COURSE)) {
                System.out.println("THE SIZE OF DATA IS " + TisReportConverter.convertRegistrations(listOfCourseRegisteredStudents).size());
                reportParameters.put("reportitle", "Students Registered " + courseName + " , Credit " + course.getCreditHours().toString());
                reportUtility = new ReportUtility(reportParameters, "students-registered-selected-course", TisReportConverter.convertToSingleCourseRegistration(listOfCourseRegisteredStudents), ReportUtility.PDF_FILE);
                reportUtility.generateReport();
            } else if (selectedReportOption.equalsIgnoreCase(PrintOptions.STUDENTS_REGISTERED_COURSES_LIST)) {
                List<SemesterRegistration> listStudents = TisEjbSource.getExamsProcessingInstance().getSemesterStudentsRegistions(selectedItemHelper.getSelectedProgram(), selectedItemHelper.getSelectedAcademicLevel(),
                        TisEjbSource.getBasicQuariesInstance().getCurrentAcademicYear().getAcademicYearId());

                reportParameters.put("reportitle", " Students' Registeration List ");
                reportUtility = new ReportUtility(reportParameters, "students-registered-courses-list", TisReportConverter.convertToStudentsRegisteredDeferredCourses(listStudents), ReportUtility.PDF_FILE);
                reportUtility.generateReport();
            } else if (selectedReportOption.equalsIgnoreCase(PrintOptions.EXAMS_ATTENDANCE_SHEET)) {
                reportParameters.put("reportitle", "Exams Attendance Sheet");
                reportUtility = new ReportUtility(reportParameters, "exams-attendance-sheet", TisReportConverter.convertRegistrations(listOfCourseRegisteredStudents), ReportUtility.PDF_FILE);
                reportUtility.generateReport();
            } else if (selectedReportOption.equalsIgnoreCase(PrintOptions.EXAMS_RAW_SCORE_SHEET)) {
                reportParameters.put("reportitle", "Exams Raw Score Sheet");
                reportUtility = new ReportUtility(reportParameters, "exams-raw-score-sheet", TisReportConverter.convertToRawScoreSheet(listOfCourseRegisteredStudents), ReportUtility.PDF_FILE);
                reportUtility.generateReport();
            }

        } catch (Exception e) {
            appLogger(e);
        }

    }

    public String printSingleStudentRegistration() {

        if (listOfRegistrations.isEmpty()) {
            JSFMessages.warnMessage(JSFMessages.NO_DATA_TO_PRINT);
        } else {

            RegistrationCoursesDetails coursesDetails = new RegistrationCoursesDetails();
            List<Course> listOfCourseToRegister = BinderSplitter.coursesSplitterList(listOfRegistrations.get(0).getCourses());
            List<RegistrationCoursesDetails> listOfegistrationCoursesDetailses = new ArrayList<RegistrationCoursesDetails>();
            int counter = 1;
            for (Course eachCourse : listOfCourseToRegister) {
                coursesDetails.setCounter(counter);
                String courseName = eachCourse.getCourseInitials().concat(" ") + eachCourse.getCourseCode().concat(" ") + eachCourse.getCourseName();
                coursesDetails.setCourseName(courseName);
                coursesDetails.setCreditHours(eachCourse.getCreditHours().intValue());
                listOfegistrationCoursesDetailses.add(coursesDetails);
                coursesDetails = new RegistrationCoursesDetails();
                counter++;
            }
            int totalCreditHours = 0, creditRegistered = 0;

            RegistrationSlips registrationSlips = new RegistrationSlips();
            List<RegistrationSlips> listOfRegistrationSlipses = new ArrayList<RegistrationSlips>();

            registrationSlips.setIndexNumber(student.getStudentIndexNumber());
            registrationSlips.setLevel(selectedItemHelper.getSelectedAcademicLevel());
            registrationSlips.setListOfRegistrationCoursesDetails(listOfegistrationCoursesDetailses);
            registrationSlips.setNationality(student.getCountry());
            registrationSlips.setNumberOfSubjects(listOfCourseToRegister.size());
            registrationSlips.setProgramName(student.getProgram().getProgramName());
            registrationSlips.setStudentId(student.getStudentId());
            registrationSlips.setStudentName(student.getSurname() + " " + student.getOtherNames());
            registrationSlips.setTotalDeferredCredits(totalCreditHours);
            registrationSlips.setTotalRegisteredCredits(BinderSplitter.totalCreditCourses(listOfCourseToRegister));

            listOfRegistrationSlipses.add(registrationSlips);
            listOfRegistrationSlipses.add(registrationSlips);


            String reportTitle = userSessionData.getCurrentAcademicYear().getAcademicYear() + " ACADEMIC YEAR";

            String fullFileName = ReportUtility.REPORT_FOLDER + "registration-subreport" + ReportUtility.REPORT_FILE_EXTENSION;
            InputStream reportFileInput = JSFUtility.externalContext().getResourceAsStream(fullFileName);
            HashMap hashMap = new HashMap();

            hashMap.put("programName", student.getProgram().getProgramName());
            hashMap.put("resultSlipSubreport", reportFileInput);
            hashMap.put("reportTitle", reportTitle);
            hashMap.putAll(ReportParametersCommons.getCommonParameters());

            System.out.println("THE SIZE OF RESULTS IS " + listOfRegistrationSlipses.size());
            System.out.println("THE SIZE OF RESULTS IS " + listOfRegistrationSlipses.get(0).getListOfRegistrationCoursesDetails().size());
//
            ReportUtility reportUtility = new ReportUtility(hashMap, "registration-slip", listOfRegistrationSlipses, ReportUtility.getPDF_FILE());
            reportUtility.generateReport();

        }
        return null;
    }

    public String bulkPrint() {
        return null;

    }

    void appLogger(Exception e) {
        e.printStackTrace();
        Logger.getLogger(RegisteredStudentsController.class.getName()).log(Level.SEVERE, null, e.getCause());
    }
    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">

    public SemesterRegistration getRegistration() {
        return registration;
    }

    public void setRegistration(SemesterRegistration registration) {
        this.registration = registration;
    }

    public List<SemesterRegistration> getListOfRegistrations() {
        return listOfRegistrations;
    }

    public void setListOfRegistrations(List<SemesterRegistration> listOfRegistrations) {
        this.listOfRegistrations = listOfRegistrations;
    }

    public UserSessionData getUserSessionData() {
        return userSessionData;
    }

    public void setUserSessionData(UserSessionData userSessionData) {
        this.userSessionData = userSessionData;
    }

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

    public SelectItem[] getProgramCourseSelectItems() {
        return programCourseSelectItems;
    }

    public void setProgramCourseSelectItems(SelectItem[] programCourseSelectItems) {
        this.programCourseSelectItems = programCourseSelectItems;
    }

    public List<Course> getListOfCourses() {
        return listOfCourses;
    }

    public void setListOfCourses(List<Course> listOfCourses) {
        this.listOfCourses = listOfCourses;
    }

    public DataModel<SemesterRegistration> getRegistrationDataModel() {
        return registrationDataModel;
    }

    public void setRegistrationDataModel(DataModel<SemesterRegistration> registrationDataModel) {
        this.registrationDataModel = registrationDataModel;
    }

    public SearchInputs getSearchInputs() {
        return searchInputs;
    }

    public void setSearchInputs(SearchInputs searchInputs) {
        this.searchInputs = searchInputs;
    }

    public PageCommonInputs getPageCommonInputs() {
        return pageCommonInputs;
    }

    public void setPageCommonInputs(PageCommonInputs pageCommonInputs) {
        this.pageCommonInputs = pageCommonInputs;
    }

    public SelectedItemHelper getSelectedItemHelper() {
        return selectedItemHelper;
    }

    public void setSelectedItemHelper(SelectedItemHelper selectedItemHelper) {
        this.selectedItemHelper = selectedItemHelper;
    }
    //</editor-fold>
}
