/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.ExamsGrader;
import com.noad.solutions.common.classes.utils.ResultsSlip;
import com.noad.solutions.common.classes.utils.CoursesMarksDetails;
import com.noad.solutions.common.classes.utils.MassCoursesMarksDetails;
import com.noad.solutions.common.classes.utils.MassResultsSlips;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.ReportParametersCommons;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.common.number.utils.DecimalPlace;
import com.noad.solutions.common.report.utils.ReportUtility;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.ejb.entities.StudentMark;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
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

/**
 *
 * @author Daud
 */
@Named(value = "studentsResultsSlipController")
@SessionScoped
public class StudentsResultsSlipController implements Serializable {

    private UserSessionData userSessionData = new UserSessionData();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private CoursesMarksDetails coursesMarksDetails = new CoursesMarksDetails();
    private MassCoursesMarksDetails massCoursesMarksDetails = new MassCoursesMarksDetails();
    private ResultsSlip resultsSlip = new ResultsSlip();
    private MassResultsSlips massResultsSlips = new MassResultsSlips();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private List<ResultsSlip> listOfResultsSlips = new ArrayList<ResultsSlip>();
    private List<CoursesMarksDetails> listOfCoursesMarksDetailses = null;
    private List<MassCoursesMarksDetails> listOfMassCoursesMarksDetailses = null;
    private List<MassResultsSlips> listOfMassResultsSlipses = new ArrayList<MassResultsSlips>();
    private DataModel<CoursesMarksDetails> resultsSlipCommonsesDataModel = null;
    private Student student = new Student();
    private String markTitle = "", weightedHeader = "", weightedAverageHeader = "";
    private int semCrdtReg = 0, semCrdtObt = 0, cummCrdtReg = 0, cummCrdtObt = 0;
    private double semWtdMark = 0.0, semAvg = 0.0, cummWtdMark = 0.0, cummAvg = 0.0;

    public StudentsResultsSlipController() {
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);
        markTitle = "Grade Points";
    }

    public void prepareSingleExamsResults() {
        try {

            if (selectedItemHelper.getSelectedStudent() == null || selectedItemHelper.getSelectedStudent().equals("")) {
                JSFMessages.errorMessage("Please Enter The Student  Index Number ");
                return;
            }
            student = TisEjbSource.getBasicQuariesInstance().findStudentByIndexNumber(selectedItemHelper.getSelectedStudent());
            if (student == null) {
                JSFMessages.errorMessage("No Student With Index Number " + selectedItemHelper.getSelectedStudent());
                return;

            } else {


                listOfCoursesMarksDetailses = new ArrayList<CoursesMarksDetails>();
                listOfResultsSlips = new ArrayList<ResultsSlip>();
                resultsSlipCommonsesDataModel = new ListDataModel<CoursesMarksDetails>(listOfCoursesMarksDetailses);
                semAvg = 0.0;
                semWtdMark = 0.0;
                semCrdtObt = 0;
                semCrdtReg = 0;

                List<StudentMark> listOfCurrentSemesterStudentMarks = TisEjbSource.getExamsProcessingInstance().getSemStdtCourseMarks(student, selectedItemHelper.getSelectedAcademicLevel(), userSessionData.getCurrentAcademicYear().getAcademicYearId());

                if (listOfCurrentSemesterStudentMarks.isEmpty()) {
                    JSFMessages.errorMessage("No Marks Uploaded For " + userSessionData.getCurrentAcademicYear().getAcademicYear() + " Semester " + userSessionData.getCurrentAcademicYear().getSemester());
                    System.out.println("THE NUMBER OF STUDENT MARKS IS " + listOfCurrentSemesterStudentMarks.size());
                    return;
                } else {

//              Processing  Current semester marks and credit hours of  the student

                    for (StudentMark eachMark : listOfCurrentSemesterStudentMarks) {

                        if (student.getProgram().getGradingSystem().equalsIgnoreCase(ExamsGrader.getCWA())) {
                            if (eachMark.getTotalMark() >= 40) {
                                semCrdtObt += eachMark.getCourse().getCreditHours();
                            }
                            semWtdMark += eachMark.getExamMark() * eachMark.getCourse().getCreditHours();
                            coursesMarksDetails.setMarks(eachMark.getTotalMark());
                        } else if (student.getProgram().getGradingSystem().equalsIgnoreCase(ExamsGrader.getGPA())) {
                            if (eachMark.getTotalMark() >= 50) {
                                semCrdtObt += eachMark.getCourse().getCreditHours();
                            }
                            semWtdMark += (eachMark.getGradePoint() * eachMark.getCourse().getCreditHours());
                            coursesMarksDetails.setMarks(eachMark.getGradePoint());
                        }

                        semCrdtReg += eachMark.getCourse().getCreditHours();
                        coursesMarksDetails.setCourseName(eachMark.getCourse().getCourseInitials() + " " + eachMark.getCourse().getCourseCode() + " " + eachMark.getCourse().getCourseName());
                        coursesMarksDetails.setCreditHours(String.valueOf(eachMark.getCourse().getCreditHours()));
                        coursesMarksDetails.setGradePoint(eachMark.getGradePoint());
                        coursesMarksDetails.setGrade(eachMark.getGrade());
                        coursesMarksDetails.setSemCrdtObt(semCrdtObt);
                        coursesMarksDetails.setSemCrdtReg(semCrdtReg);
                        coursesMarksDetails.setMarkStyle(eachMark.getMarkStyle());

//                        Processing Cummulative Results

                        cummAvg = 0.0;
                        cummWtdMark = 0.0;
                        cummCrdtObt = 0;
                        cummCrdtReg = 0;
                        List<StudentMark> listOfCummulativeStudentMarks = student.getStudentMarkList();
                        if (student.getProgram().getGradingSystem().equalsIgnoreCase(ExamsGrader.getGPA())) {
                            for (StudentMark eachCummMark : listOfCummulativeStudentMarks) {
                                if (eachCummMark.getTotalMark() >= 50) {
                                    cummCrdtObt += eachCummMark.getCourse().getCreditHours();
                                    coursesMarksDetails.setCummCrdtObt(coursesMarksDetails.getCummCrdtObt() + eachCummMark.getCourse().getCreditHours());
                                }
                                cummCrdtReg += eachCummMark.getCourse().getCreditHours();
                                cummWtdMark += (eachCummMark.getGradePoint() * eachCummMark.getCourse().getCreditHours());
                                coursesMarksDetails.setCummWtdMark(coursesMarksDetails.getCummWtdMark() + (eachCummMark.getGradePoint() * eachCummMark.getCourse().getCreditHours()));
                                coursesMarksDetails.setCummCrdtReg(coursesMarksDetails.getCummCrdtReg() + eachCummMark.getCourse().getCreditHours());

                            }

                        } else if (student.getProgram().getGradingSystem().equalsIgnoreCase(ExamsGrader.getCWA())) {
                            for (StudentMark eachCummMark : listOfCummulativeStudentMarks) {
                                if (eachCummMark.getTotalMark() >= 40) {
                                    cummCrdtObt += eachCummMark.getCourse().getCreditHours();
                                    coursesMarksDetails.setCummCrdtObt(coursesMarksDetails.getCummCrdtObt() + coursesMarksDetails.getCummCrdtObt());
                                }
                                cummCrdtReg += eachCummMark.getCourse().getCreditHours();
                                cummWtdMark += (eachCummMark.getExamMark() * eachCummMark.getCourse().getCreditHours());
                                coursesMarksDetails.setCummWtdMark(coursesMarksDetails.getCummWtdMark() + (eachCummMark.getExamMark() * eachCummMark.getCourse().getCreditHours()));
                                coursesMarksDetails.setCummCrdtReg(coursesMarksDetails.getCummCrdtReg() + eachCummMark.getCourse().getCreditHours());
                            }
                        }
                        semWtdMark = DecimalPlace.getTwoDecimalPlaces(semWtdMark);
                        cummWtdMark = DecimalPlace.getTwoDecimalPlaces(cummWtdMark);
                        semAvg = (DecimalPlace.getTwoDecimalPlaces(semWtdMark / semCrdtReg));
                        cummAvg = (DecimalPlace.getTwoDecimalPlaces(cummWtdMark / cummCrdtReg));
                        coursesMarksDetails.setSemAvg(semAvg);
                        coursesMarksDetails.setCummAvg(cummAvg);
                        coursesMarksDetails.setSemWtdMark(semWtdMark);
                        listOfCoursesMarksDetailses.add(coursesMarksDetails);
                        coursesMarksDetails = new CoursesMarksDetails();


                    }
                    resultsSlip.setIndexNumber(student.getStudentIndexNumber());
                    resultsSlip.setLevel(student.getCurrentLevel().getAcademicLevelId());
                    resultsSlip.setNationality(student.getCountry());
                    resultsSlip.setProgramName(student.getProgram().getProgramName());
                    resultsSlip.setListOfCoursesMarksDetails(listOfCoursesMarksDetailses);
                    resultsSlip.setStudentId(student.getStudentId());
                    resultsSlip.setStudentName(student.getSurname().toUpperCase() + " " + student.getOtherNames());
                    listOfResultsSlips.add(resultsSlip);
                    listOfResultsSlips.add(resultsSlip);
                    System.out.println("THE SIZE OF SUBREPORT IS " + listOfCoursesMarksDetailses.size());
                    resultsSlipCommonsesDataModel = new ListDataModel<CoursesMarksDetails>(listOfCoursesMarksDetailses);


                }

            }
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void printSingleExamsResults() {
        try {
            if (listOfResultsSlips.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_TO_PRINT);
            } else {
                String reportTitle = "RESULTS SLIP FOR THE ";
                if (userSessionData.getCurrentAcademicYear().getSemester().equalsIgnoreCase("1")) {
                    reportTitle += "FIRST SEMESTER ";
                } else {
                    reportTitle += "SECOND SEMESTER";
                }
                if (student.getProgram().getGradingSystem().equalsIgnoreCase(ExamsGrader.getGPA())) {
                    markTitle = "GRADE POINTS";
                    weightedAverageHeader = "GPA";
                    weightedHeader = "Weighted Grade Point";
                } else {
                    markTitle = "MARKS";
                    weightedAverageHeader = "CWA";
                    weightedHeader = "Weighted Marks";
                }
//                Program program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());

                reportTitle += userSessionData.getCurrentAcademicYear().getAcademicYear() + " ACADEMIC YEAR";

                String fullFileName = ReportUtility.REPORT_FOLDER + "results-subreport" + ReportUtility.REPORT_FILE_EXTENSION;
                InputStream reportFileInput = JSFUtility.externalContext().getResourceAsStream(fullFileName);
                System.out.println("THE MARK TITLE IS " + markTitle);
                HashMap hashMap = new HashMap();

                hashMap.put("programName", student.getProgram().getProgramName());
                hashMap.put("markTitle", markTitle);
                hashMap.put("weightedAverageHeader", weightedAverageHeader);
                hashMap.put("weightedHeader", weightedHeader);

                hashMap.put("resultSlipSubreport", reportFileInput);
                hashMap.putAll(ReportParametersCommons.getCommonParameters());

                System.out.println("THE SIZE OF RESULTS IS " + listOfResultsSlips.size());
                System.out.println("THE SIZE OF RESULTS IS " + listOfResultsSlips.get(0).getListOfCoursesMarksDetails().size());
//
                ReportUtility reportUtility = new ReportUtility(hashMap, "results-slip", listOfResultsSlips, ReportUtility.getPDF_FILE());
                reportUtility.generateReport();

            }
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void printMassStudents() {

        try {

            List<Student> listOfStudents = TisEjbSource.getExamsProcessingInstance().getSelectedProgramAndLevelStudents(selectedItemHelper.getSelectedProgram(), selectedItemHelper.getSelectedAcademicLevel(),
                    userSessionData.getCurrentAcademicYear().getAcademicYearId());
            System.out.println("THE SIZE OF THE STUDENTS IS " + listOfStudents.size());
            Program program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());
//            student = TisEjbSource.getBasicQuariesInstance().findStudentByIndexNumber(selectedItemHelper.getSelectedStudent());
            if (listOfStudents.isEmpty()) {
                JSFMessages.warnMessage("No  Students Reading " + program.getProgramName() + " In " + selectedItemHelper.getSelectedAcademicLevel());
                return;
            } else {

//                listOfCoursesMarksDetailses = new ArrayList<CoursesMarksDetails>();

                for (Student eachStudent : listOfStudents) {

//                      Processing  Current semester marks and credit hours of  the student
                    List<StudentMark> listOfCurrentSemesterStudentMarks = TisEjbSource.getExamsProcessingInstance().getSemStdtCourseMarks(eachStudent, selectedItemHelper.getSelectedAcademicLevel(), userSessionData.getCurrentAcademicYear().getAcademicYearId());
                    if (listOfCurrentSemesterStudentMarks.isEmpty()) {
                        continue;
                    } else {

                        listOfMassCoursesMarksDetailses = new ArrayList<MassCoursesMarksDetails>();
                        semAvg = 0.0;
                        semWtdMark = 0.0;
                        semCrdtObt = 0;
                        semCrdtReg = 0;

                        for (StudentMark eachMark : listOfCurrentSemesterStudentMarks) {

                            if (eachStudent.getProgram().getGradingSystem().equalsIgnoreCase(ExamsGrader.getCWA())) {
                                if (eachMark.getTotalMark() >= 40) {
                                    semCrdtObt += eachMark.getCourse().getCreditHours();
                                }
                                semWtdMark += eachMark.getExamMark() * eachMark.getCourse().getCreditHours();
                                massCoursesMarksDetails.setMarks(eachMark.getTotalMark());
                            } else if (eachStudent.getProgram().getGradingSystem().equalsIgnoreCase(ExamsGrader.getGPA())) {
                                if (eachMark.getTotalMark() >= 50) {
                                    semCrdtObt += eachMark.getCourse().getCreditHours();
                                }
                                semWtdMark += (eachMark.getGradePoint() * eachMark.getCourse().getCreditHours());
                                massCoursesMarksDetails.setMarks(eachMark.getGradePoint());
                            }

                            semCrdtReg += eachMark.getCourse().getCreditHours();
                            massCoursesMarksDetails.setCourseName(eachMark.getCourse().getCourseInitials() + " " + eachMark.getCourse().getCourseCode() + " " + eachMark.getCourse().getCourseName());
                            massCoursesMarksDetails.setCreditHours(String.valueOf(eachMark.getCourse().getCreditHours()));
                            massCoursesMarksDetails.setGrade(eachMark.getGrade());
                            listOfMassCoursesMarksDetailses.add(massCoursesMarksDetails);
                            massCoursesMarksDetails = new MassCoursesMarksDetails();
                        }

                        //  Processing Cummulative Results
                        cummAvg = 0.0;
                        cummWtdMark = 0.0;
                        cummCrdtObt = 0;
                        cummCrdtReg = 0;

                        List<StudentMark> listOfCummulativeStudentMarks = null;
                        listOfCummulativeStudentMarks = eachStudent.getStudentMarkList();
                        System.out.println(eachStudent.getSurname() + " THE SIZE OF EACH STUDENT MARKS IS " + listOfCummulativeStudentMarks.size());
                        if (program.getGradingSystem().equalsIgnoreCase(ExamsGrader.getGPA())) {
                            for (StudentMark eachCummMark : listOfCummulativeStudentMarks) {
                                if (eachCummMark.getTotalMark() >= 50) {
                                    cummCrdtObt += eachCummMark.getCourse().getCreditHours();
                                }
                                cummCrdtReg += eachCummMark.getCourse().getCreditHours();
                                cummWtdMark += (eachCummMark.getGradePoint() * eachCummMark.getCourse().getCreditHours());
                            }

                        } else if (program.getGradingSystem().equalsIgnoreCase(ExamsGrader.getCWA())) {
                            for (StudentMark eachCummMark : listOfCummulativeStudentMarks) {
                                if (eachCummMark.getTotalMark() >= 40) {
                                    cummCrdtObt += eachCummMark.getCourse().getCreditHours();
                                }
                                cummCrdtReg += eachCummMark.getCourse().getCreditHours();
                                cummWtdMark += (eachCummMark.getExamMark() * eachCummMark.getCourse().getCreditHours());
                            }
                        }


                        semWtdMark = DecimalPlace.getTwoDecimalPlaces(semWtdMark);
                        semAvg = (DecimalPlace.getTwoDecimalPlaces(semWtdMark / semCrdtReg));
                        cummWtdMark = DecimalPlace.getTwoDecimalPlaces(cummWtdMark);
                        cummAvg = (DecimalPlace.getTwoDecimalPlaces(cummWtdMark / cummCrdtReg));


                        massResultsSlips.setListOfCoursesMarksDetails(listOfMassCoursesMarksDetailses);
                        massResultsSlips.setSemCrdtObt(semCrdtObt);
                        massResultsSlips.setSemCrdtReg(semCrdtReg);
                        massResultsSlips.setSemWtdMark(semWtdMark);
                        massResultsSlips.setSemAvg(semAvg);

                        massResultsSlips.setCummCrdtObt(cummCrdtObt);
                        massResultsSlips.setCummCrdtReg(cummCrdtReg);
                        massResultsSlips.setCummWtdMark(cummWtdMark);
                        massResultsSlips.setCummAvg(cummAvg);

                        massResultsSlips.setIndexNumber(eachStudent.getStudentIndexNumber());
                        massResultsSlips.setLevel(eachStudent.getCurrentLevel().getAcademicLevelId());
                        massResultsSlips.setNationality(eachStudent.getCountry());
                        massResultsSlips.setProgramName(eachStudent.getProgram().getProgramName());
                        massResultsSlips.setStudentId(eachStudent.getStudentId());
                        massResultsSlips.setStudentName(eachStudent.getSurname().toUpperCase() + " " + eachStudent.getOtherNames());

                        listOfMassResultsSlipses.add(massResultsSlips);
                        listOfMassResultsSlipses.add(massResultsSlips);
                        massResultsSlips = new MassResultsSlips();


                    }

                }


            }

            cummAvg = 0.0;
            cummWtdMark = 0.0;
            cummCrdtObt = 0;
            cummCrdtReg = 0;
            semAvg = 0.0;
            semWtdMark = 0.0;
            semCrdtObt = 0;
            semCrdtReg = 0;


            for (MassResultsSlips eachMassResultsSlipt : listOfMassResultsSlipses) {
                System.out.println("THE STUDENT IS " + eachMassResultsSlipt.getStudentName());
                System.out.println("<<<<<<<<<<<<<<<<<<<<BEGINNING>>>>>>>>>>>>>>>>>>>");
                System.out.println("THE COURSES MARKS ARE AS FOLLOWS");
                for (MassCoursesMarksDetails courseResults : eachMassResultsSlipt.getListOfCoursesMarksDetails()) {
                    System.out.println(courseResults.getCourseName() + " <<<<<<>>>>> " + courseResults.getMarks());
                }
                System.out.println("<<<<<<<<<<<<<<<<<<<<ENDING>>>>>>>>>>>>>>>>>>>");
            }

            if (listOfMassResultsSlipses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_TO_PRINT);
            } else {
                String reportTitle = "RESULTS SLIP FOR THE ";
                if (userSessionData.getCurrentAcademicYear().getSemester().equalsIgnoreCase("1")) {
                    reportTitle += "FIRST SEMESTER ";
                } else {
                    reportTitle += "SECOND SEMESTER";
                }
                if (program.getGradingSystem().equalsIgnoreCase(ExamsGrader.getGPA())) {
                    markTitle = "GRADE POINTS";
                    weightedAverageHeader = "GPA";
                    weightedHeader = "Weighted Grade Point";
                } else {
                    markTitle = "MARKS";
                    weightedAverageHeader = "CWA";
                    weightedHeader = "Weighted Marks";
                }
//                Program program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());

                reportTitle += userSessionData.getCurrentAcademicYear().getAcademicYear() + " ACADEMIC YEAR";

                String fullFileName = ReportUtility.REPORT_FOLDER + "mass-results-subreport" + ReportUtility.REPORT_FILE_EXTENSION;
                InputStream reportFileInput = JSFUtility.externalContext().getResourceAsStream(fullFileName);
                System.out.println("THE MARK TITLE IS " + markTitle);
                HashMap hashMap = new HashMap();

                hashMap.put("programName", program.getProgramName());
                hashMap.put("markTitle", markTitle);
                hashMap.put("weightedAverageHeader", weightedAverageHeader);
                hashMap.put("weightedHeader", weightedHeader);

                hashMap.put("resultSlipSubreport", reportFileInput);
                hashMap.putAll(ReportParametersCommons.getCommonParameters());

//
                ReportUtility reportUtility = new ReportUtility(hashMap, "mass-results-slip", listOfMassResultsSlipses, ReportUtility.getPDF_FILE());
                reportUtility.generateReport();

            }

        } catch (Exception e) {
            appLogger(e);
        }

    }

    public void resetButton() {
    }

    private void appLogger(Exception e) {
        e.printStackTrace();
        Logger
                .getLogger(ExaminationResultsController.class
                .getName()).log(Level.SEVERE, e.getMessage(), e);

    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public UserSessionData getUserSessionData() {
        return userSessionData;
    }

    public void setUserSessionData(UserSessionData userSessionData) {
        this.userSessionData = userSessionData;
    }

    public PageCommonInputs getPageCommonInputs() {
        return pageCommonInputs;
    }

    public void setPageCommonInputs(PageCommonInputs pageCommonInputs) {
        this.pageCommonInputs = pageCommonInputs;
    }

    public ResultsSlip getFullStudentResultsSlipCommons() {
        return resultsSlip;
    }

    public String getMarkTitle() {
        return markTitle;
    }

    public void setMarkTitle(String markTitle) {
        this.markTitle = markTitle;
    }

    public void setFullStudentResultsSlipCommons(ResultsSlip fullStudentResultsSlipCommons) {
        this.resultsSlip = fullStudentResultsSlipCommons;
    }

    public List<ResultsSlip> getListOfFullStudentResultsSlipCommonses() {
        return listOfResultsSlips;
    }

    public void setListOfFullStudentResultsSlipCommonses(List<ResultsSlip> listOfFullStudentResultsSlipCommonses) {
        this.listOfResultsSlips = listOfFullStudentResultsSlipCommonses;
    }

    public CoursesMarksDetails getResultsSlipCommons() {
        return coursesMarksDetails;
    }

    public void setResultsSlipCommons(CoursesMarksDetails resultsSlipCommons) {
        this.coursesMarksDetails = resultsSlipCommons;
    }

    public SelectedItemHelper getSelectedItemHelper() {
        return selectedItemHelper;
    }

    public void setSelectedItemHelper(SelectedItemHelper selectedItemHelper) {
        this.selectedItemHelper = selectedItemHelper;
    }

    public CoursesMarksDetails getCoursesMarksDetails() {
        return coursesMarksDetails;
    }

    public void setCoursesMarksDetails(CoursesMarksDetails coursesMarksDetails) {
        this.coursesMarksDetails = coursesMarksDetails;
    }

    public ResultsSlip getResultsSlip() {
        return resultsSlip;
    }

    public void setResultsSlip(ResultsSlip resultsSlip) {
        this.resultsSlip = resultsSlip;
    }

    public List<ResultsSlip> getListOfResultsSlips() {
        return listOfResultsSlips;
    }

    public void setListOfResultsSlips(List<ResultsSlip> listOfResultsSlips) {
        this.listOfResultsSlips = listOfResultsSlips;
    }

    public List<CoursesMarksDetails> getListOfCoursesMarksDetailses() {
        return listOfCoursesMarksDetailses;
    }

    public void setListOfCoursesMarksDetailses(List<CoursesMarksDetails> listOfCoursesMarksDetailses) {
        this.listOfCoursesMarksDetailses = listOfCoursesMarksDetailses;
    }

    public int getSemCrdtReg() {
        return semCrdtReg;
    }

    public void setSemCrdtReg(int semCrdtReg) {
        this.semCrdtReg = semCrdtReg;
    }

    public int getSemCrdtObt() {
        return semCrdtObt;
    }

    public void setSemCrdtObt(int semCrdtObt) {
        this.semCrdtObt = semCrdtObt;
    }

    public int getCummCrdtReg() {
        return cummCrdtReg;
    }

    public void setCummCrdtReg(int cummCrdtReg) {
        this.cummCrdtReg = cummCrdtReg;
    }

    public int getCummCrdtObt() {
        return cummCrdtObt;
    }

    public void setCummCrdtObt(int cummCrdtObt) {
        this.cummCrdtObt = cummCrdtObt;
    }

    public double getSemWtdMark() {
        return semWtdMark;
    }

    public void setSemWtdMark(double semWtdMark) {
        this.semWtdMark = semWtdMark;
    }

    public double getSemAvg() {
        return semAvg;
    }

    public void setSemAvg(double semAvg) {
        this.semAvg = semAvg;
    }

    public MassCoursesMarksDetails getMassCoursesMarksDetails() {
        return massCoursesMarksDetails;
    }

    public void setMassCoursesMarksDetails(MassCoursesMarksDetails massCoursesMarksDetails) {
        this.massCoursesMarksDetails = massCoursesMarksDetails;
    }

    public MassResultsSlips getMassResultsSlips() {
        return massResultsSlips;
    }

    public void setMassResultsSlips(MassResultsSlips massResultsSlips) {
        this.massResultsSlips = massResultsSlips;
    }

    public List<MassCoursesMarksDetails> getListOfMassCoursesMarksDetailses() {
        return listOfMassCoursesMarksDetailses;
    }

    public void setListOfMassCoursesMarksDetailses(List<MassCoursesMarksDetails> listOfMassCoursesMarksDetailses) {
        this.listOfMassCoursesMarksDetailses = listOfMassCoursesMarksDetailses;
    }

    public double getCummWtdMark() {
        return cummWtdMark;
    }

    public void setCummWtdMark(double cummWtdMark) {
        this.cummWtdMark = cummWtdMark;
    }

    public double getCummAvg() {
        return cummAvg;
    }

    public void setCummAvg(double cummAvg) {
        this.cummAvg = cummAvg;
    }

    public DataModel<CoursesMarksDetails> getResultsSlipCommonsesDataModel() {
        return resultsSlipCommonsesDataModel;
    }

    public void setResultsSlipCommonsesDataModel(DataModel<CoursesMarksDetails> resultsSlipCommonsesDataModel) {
        this.resultsSlipCommonsesDataModel = resultsSlipCommonsesDataModel;
    }

    public String getWeightedHeader() {
        return weightedHeader;
    }

    public void setWeightedHeader(String weightedHeader) {
        this.weightedHeader = weightedHeader;
    }

    public String getWeightedAverageHeader() {
        return weightedAverageHeader;
    }

    public void setWeightedAverageHeader(String weightedAverageHeader) {
        this.weightedAverageHeader = weightedAverageHeader;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    //</editor-fold>
}
