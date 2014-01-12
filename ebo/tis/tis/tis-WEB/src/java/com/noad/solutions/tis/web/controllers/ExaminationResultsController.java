/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.BinderSplitter;
import com.noad.solutions.common.classes.utils.ExamsGrader;
import com.noad.solutions.common.classes.utils.ExamsResultsCommons;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.ReportParametersCommons;
import com.noad.solutions.common.classes.utils.ResultsSummary;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.SummaryResults;
import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.common.number.utils.DecimalPlace;
import com.noad.solutions.common.report.utils.ReportUtility;
import com.noad.solutions.tis.ejb.entities.Course;
import com.noad.solutions.tis.ejb.entities.IncompleteCourse;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.ProgramCourse;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.ejb.entities.StudentMark;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.DataModel;

/**
 *
 * @author Daud
 */
@Named(value = "examinationResultsController")
@SessionScoped
public class ExaminationResultsController implements Serializable {

    private UserSessionData userSessionData = new UserSessionData();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private ExamsResultsCommons examsResultsCommons = new ExamsResultsCommons();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private ResultsSummary resultsSummary = new ResultsSummary();
    private List<SummaryResults> summaryResultseList = new ArrayList<SummaryResults>();
    private List<ResultsSummary> listOfresultsSummary = null;
    private List<ExamsResultsCommons> listOfExamsResultsCommonses = null;
    private List<Course> listProgramSemesterCourses = null;
    private List<String> subjectColumnHeaders = null;
    private DataModel<ExamsResultsCommons> examsResultsCommonsesDataModel = null;
    private Student student = new Student();
    private String[] marksColumns = {"", "", "", "", "", "", "", "", "", "", "", ""};
    private Program program = new Program();
    private String wtdHeader = "", semAvgHeader = "", cummAvgHeader = "", remarks = "";

    public ExaminationResultsController() {
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);
    }

    public void computeExamsResults() {
        try {

            program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());
            pageCommonInputs.showGradingType = program.getGradingSystem().equalsIgnoreCase(ExamsGrader.getGPA()) ? true : false;

            //Gets all course that are supposed to be done the current semester
            List<ProgramCourse> listOfProgramCourses = TisEjbSource.getExamsProcessingInstance().loadProgramCourses(selectedItemHelper.getSelectedProgram(), selectedItemHelper.getSelectedAcademicLevel(), userSessionData.getCurrentAcademicYear().getSemester());
            listProgramSemesterCourses = BinderSplitter.coursesSplitterList(listOfProgramCourses.get(0).getCourses());
            if (listProgramSemesterCourses.isEmpty()) {
                JSFMessages.errorMessage("No Courses Found For " + selectedItemHelper.getSelectedAcademicLevel() + " " + userSessionData.getCurrentAcademicYear().getSemester());
                return;
            }
            List<Student> listOfStudents = TisEjbSource.getExamsProcessingInstance().getSelectedProgramAndLevelStudents(selectedItemHelper.getSelectedProgram(), selectedItemHelper.getSelectedAcademicLevel()
                    ,userSessionData.getCurrentAcademicYear().getAcademicYearId());
            if (listOfStudents.isEmpty()) {
                JSFMessages.errorMessage("No Students Found Reading " + program.getProgramName() + " In " + selectedItemHelper.getSelectedAcademicLevel());
                return;
            }
            listOfExamsResultsCommonses = new ArrayList<ExamsResultsCommons>();
            int maxColumnNumber = 0, compare = 0, counter = 1;
            subjectColumnHeaders = new ArrayList<String>(12);
            for (Course eachCourse : listProgramSemesterCourses) {
                subjectColumnHeaders.add(eachCourse.getCourseInitials() + " " + eachCourse.getCourseCode().concat("\n")+ eachCourse.getCreditHours());
            }
            if (listProgramSemesterCourses.size() < 12) {
                for (int i = listProgramSemesterCourses.size(); i < 12; i++) {
                    subjectColumnHeaders.add(i, "");
                }

            }
            for (Student eachStudent : listOfStudents) {
                List<StudentMark> studentMarkList = new ArrayList<StudentMark>();
                //Find student corresponding semester registered course mark :NB we are using his/her registered courses
                for (Course eachCourse : listProgramSemesterCourses) {
                    StudentMark studentMark = TisEjbSource.getExamsProcessingInstance().getSemCourseMark(eachStudent.getStudentId(), selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                    studentMarkList.add(studentMark);
                }

                System.out.println("THE MAX COLUMNS IS " + maxColumnNumber);
                int semTotalCredits = 0, numberOfTrails = 0;
                double semTotalCreObt = 0.0, semWtdMark = 0.0, cumWtdMark = 0.0, semAvg = 0.0, cummAvg = 0.0;
                if (pageCommonInputs.isShowGradingType()) {
                    for (int i = 0; i < studentMarkList.size(); i++) {
                        marksColumns[i] = String.valueOf(studentMarkList.get(i).getGradePoint());
                    }
                    wtdHeader = "";
                    semAvgHeader = "";
                    cummAvgHeader = "";
                    wtdHeader = "GP";
                    semAvgHeader = "SGPA";
                    cummAvgHeader = "GPA";
                    examsResultsCommons.setCourse0(marksColumns[0]);
                    examsResultsCommons.setCourse1(marksColumns[1]);
                    examsResultsCommons.setCourse2(marksColumns[2]);
                    examsResultsCommons.setCourse3(marksColumns[3]);
                    examsResultsCommons.setCourse4(marksColumns[4]);
                    examsResultsCommons.setCourse5(marksColumns[5]);
                    examsResultsCommons.setCourse6(marksColumns[6]);
                    examsResultsCommons.setCourse7(marksColumns[7]);
                    examsResultsCommons.setCourse8(marksColumns[8]);
                    examsResultsCommons.setCourse9(marksColumns[9]);
                    examsResultsCommons.setCourse10(marksColumns[10]);
                    examsResultsCommons.setCourse11(marksColumns[11]);

                    for (StudentMark eachMark : studentMarkList) {
                        if (eachMark.getTotalMark() >= 50) {
                            semTotalCreObt += eachMark.getCourse().getCreditHours();
                        }
                        semWtdMark += (eachMark.getGradePoint() * eachMark.getCourse().getCreditHours());
                        semTotalCredits += eachMark.getCourse().getCreditHours();
                    }

                } else {
                    wtdHeader = "";
                    semAvgHeader = "";
                    cummAvgHeader = "";
                    wtdHeader = "MKS";
                    semAvgHeader = "SWA";
                    cummAvgHeader = "CWA";
                    for (int i = 0; i < studentMarkList.size(); i++) {
                        marksColumns[i] = String.valueOf(studentMarkList.get(i).getTotalMark());
                    }
                    examsResultsCommons.setCourse0(marksColumns[0]);
                    examsResultsCommons.setCourse1(marksColumns[1]);
                    examsResultsCommons.setCourse2(marksColumns[2]);
                    examsResultsCommons.setCourse3(marksColumns[3]);
                    examsResultsCommons.setCourse4(marksColumns[4]);
                    examsResultsCommons.setCourse5(marksColumns[5]);
                    examsResultsCommons.setCourse6(marksColumns[6]);
                    examsResultsCommons.setCourse7(marksColumns[7]);
                    examsResultsCommons.setCourse8(marksColumns[8]);
                    examsResultsCommons.setCourse9(marksColumns[9]);
                    examsResultsCommons.setCourse10(marksColumns[10]);
                    examsResultsCommons.setCourse11(marksColumns[11]);

                    for (StudentMark eachMark : studentMarkList) {
                        if (eachMark.getTotalMark() >= 50) {
                            semTotalCreObt += eachMark.getCourse().getCreditHours();
                        }
                        semWtdMark += (eachMark.getTotalMark() * eachMark.getCourse().getCreditHours());
                        semTotalCredits += eachMark.getCourse().getCreditHours();
                    }

                }
                int cummCreReg = 0;
                List<String> allStudentRegisteredCourses = TisEjbSource.getExamsProcessingInstance().getCummStudentRegisteredCourses(eachStudent);
                System.out.println("THE SIZE OF ALL REGISTERED COURSES IS " + allStudentRegisteredCourses.size());
                for (String eachRegCrse : allStudentRegisteredCourses) {
                    listProgramSemesterCourses = new ArrayList<Course>();
                    listProgramSemesterCourses = BinderSplitter.coursesSplitterList(eachRegCrse);
                    //Getting all registered courses credit hours
                    for (Course eachCourse : listProgramSemesterCourses) {
                        cummCreReg += eachCourse.getCreditHours();
                    }
                }
                semWtdMark = TisEjbSource.getExamsProcessingInstance().getTotalSemWeightedMark(eachStudent, userSessionData.getCurrentAcademicYear());
                semAvg = DecimalPlace.getTwoDecimalPlaces(semWtdMark / semTotalCredits);
                cumWtdMark = TisEjbSource.getExamsProcessingInstance().getTotalCummWeightedMark(eachStudent);
                cummAvg = DecimalPlace.getTwoDecimalPlaces(cumWtdMark / cummCreReg);

                examsResultsCommons.setSemCrdtObt(String.valueOf(TisEjbSource.getExamsProcessingInstance().getSemCreHrsObt(eachStudent, userSessionData.getCurrentAcademicYear())));
                examsResultsCommons.setSemCrdtReg(String.valueOf(semTotalCreObt));
                examsResultsCommons.setSemWtdMark(String.valueOf(semWtdMark));
                examsResultsCommons.setSemAvg(String.valueOf(semAvg));
                examsResultsCommons.setCummCrdtObt(String.valueOf(TisEjbSource.getExamsProcessingInstance().getCummCreHrsObt(eachStudent)));
                examsResultsCommons.setCummCrdtReg(String.valueOf(cummCreReg));
                examsResultsCommons.setCummWtdMark(String.valueOf(cumWtdMark));
                examsResultsCommons.setCummAvg(String.valueOf(DecimalPlace.getTwoDecimalPlaces(cummAvg)));
                examsResultsCommons.setStudentName(eachStudent.getSurname() + " " + eachStudent.getOtherNames());
                examsResultsCommons.setIndexNumber(eachStudent.getStudentIndexNumber());

                String trailedCourses = " ";
                List<IncompleteCourse> listOfIncompleteCourses = TisEjbSource.getExamsProcessingInstance().getStudentIncompleteCourses(eachStudent);
                numberOfTrails = listOfIncompleteCourses.size();
                for (IncompleteCourse eachIncompleteCourse : listOfIncompleteCourses) {
                    trailedCourses += eachIncompleteCourse.getCourse().getCourseInitials() + " " + eachIncompleteCourse.getCourse().getCourseCode();
                    trailedCourses += "(" + eachIncompleteCourse.getIncompleteType() + ")" + " ";
                }
                remarks = "";
                if (numberOfTrails <= 0) {
                    examsResultsCommons.setRemarks("PASS");
                    examsResultsCommons.setNumberOfTrails("");
                    remarks += "PASS";
                } else if (numberOfTrails >= 1) {
                    remarks += "Trails " + String.valueOf(numberOfTrails) + ""+trailedCourses;
//                    examsResultsCommons.setRemarks("Trails");
//                    examsResultsCommons.setNumberOfTrails("(" + String.valueOf(numberOfTrails) + ")");
                }
//                remarks += examsResultsCommons.getRemarks() + " " + examsResultsCommons.getNumberOfTrails() + " " + trailedCourses;
                examsResultsCommons.setRemarks(remarks);
//                examsResultsCommons.setCoursesTrailed(trailedCourses);
                examsResultsCommons.setCounter(String.valueOf(counter));
                listOfExamsResultsCommonses.add(examsResultsCommons);
                examsResultsCommons = new ExamsResultsCommons();
                counter++;


            }


        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void printExamsResults() {
        try {
            if (listOfExamsResultsCommonses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_TO_PRINT);
                return;
            }
            HashMap hashMap = new HashMap();
            hashMap.put("course0", subjectColumnHeaders.get(0));
            hashMap.put("course1", subjectColumnHeaders.get(1));
            hashMap.put("course2", subjectColumnHeaders.get(2));
            hashMap.put("course3", subjectColumnHeaders.get(3));
            hashMap.put("course4", subjectColumnHeaders.get(4));
            hashMap.put("course5", subjectColumnHeaders.get(5));
            hashMap.put("course6", subjectColumnHeaders.get(6));
            hashMap.put("course7", subjectColumnHeaders.get(7));
            hashMap.put("course8", subjectColumnHeaders.get(8));
            hashMap.put("course9", subjectColumnHeaders.get(9));
            hashMap.put("course10", subjectColumnHeaders.get(10));
            hashMap.put("course11", subjectColumnHeaders.get(11));
            hashMap.put("programName", program.getProgramName());
            hashMap.put("academicLevel", selectedItemHelper.getSelectedAcademicLevel());
            hashMap.put("wtdHeader", wtdHeader);
            hashMap.put("cummAvgHeader", cummAvgHeader);
            hashMap.put("semAvgHeader", semAvgHeader);
            hashMap.putAll(ReportParametersCommons.getCommonParameters());

            ReportUtility reportUtility = new ReportUtility(hashMap, "exams-results", listOfExamsResultsCommonses, ReportUtility.PDF_FILE);
            reportUtility.generateReport();

        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void resetButton() {
    }

    private void appLogger(Exception e) {
        e.printStackTrace();
        Logger.getLogger(ExaminationResultsController.class.getName()).log(Level.SEVERE, e.getMessage(), e);



    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public UserSessionData getUserSessionData() {
        return userSessionData;
    }

    public void setUserSessionData(UserSessionData userSessionData) {
        this.userSessionData = userSessionData;
    }

    public ExamsResultsCommons getExamsResultsCommons() {
        return examsResultsCommons;
    }

    public void setExamsResultsCommons(ExamsResultsCommons examsResultsCommons) {
        this.examsResultsCommons = examsResultsCommons;
    }

    public String getSemWtdHeader() {
        return wtdHeader;
    }

    public void setSemWtdHeader(String semWtdHeader) {
        this.wtdHeader = semWtdHeader;
    }

    public String getSemAvgHeader() {
        return semAvgHeader;
    }

    public void setSemAvgHeader(String semAvgHeader) {
        this.semAvgHeader = semAvgHeader;
    }

    public String getCummAvgHeader() {
        return cummAvgHeader;
    }

    public void setCummAvgHeader(String cummAvgHeader) {
        this.cummAvgHeader = cummAvgHeader;
    }

    public SelectedItemHelper getSelectedItemHelper() {
        return selectedItemHelper;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public String[] getMarksColumns() {
        return marksColumns;
    }

    public void setMarksColumns(String[] marksColumns) {
        this.marksColumns = marksColumns;
    }

    public void setSelectedItemHelper(SelectedItemHelper selectedItemHelper) {
        this.selectedItemHelper = selectedItemHelper;
    }

    public List<SummaryResults> getSummaryResultseList() {
        return summaryResultseList;
    }

    public void setSummaryResultseList(List<SummaryResults> summaryResultseList) {
        this.summaryResultseList = summaryResultseList;
    }

    public ResultsSummary getResultsSummary() {
        return resultsSummary;
    }

    public void setResultsSummary(ResultsSummary resultsSummary) {
        this.resultsSummary = resultsSummary;
    }

    public List<ResultsSummary> getListOfresultsSummary() {
        return listOfresultsSummary;
    }

    public void setListOfresultsSummary(List<ResultsSummary> listOfresultsSummary) {
        this.listOfresultsSummary = listOfresultsSummary;
    }

    public List<ExamsResultsCommons> getListOfExamsResultsCommonses() {
        return listOfExamsResultsCommonses;
    }

    public void setListOfExamsResultsCommonses(List<ExamsResultsCommons> listOfExamsResultsCommonses) {
        this.listOfExamsResultsCommonses = listOfExamsResultsCommonses;
    }

    public DataModel<ExamsResultsCommons> getExamsResultsCommonsesDataModel() {
        return examsResultsCommonsesDataModel;
    }

    public void setExamsResultsCommonsesDataModel(DataModel<ExamsResultsCommons> examsResultsCommonsesDataModel) {
        this.examsResultsCommonsesDataModel = examsResultsCommonsesDataModel;
    }

    public List<Course> getListProgramSemesterCourses() {
        return listProgramSemesterCourses;
    }

    public void setListProgramSemesterCourses(List<Course> listProgramSemesterCourses) {
        this.listProgramSemesterCourses = listProgramSemesterCourses;
    }

    public List<String> getSubjectColumnHeaders() {
        return subjectColumnHeaders;
    }

    public void setSubjectColumnHeaders(List<String> subjectColumnHeaders) {
        this.subjectColumnHeaders = subjectColumnHeaders;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public PageCommonInputs getPageCommonInputs() {
        return pageCommonInputs;
    }

    public void setPageCommonInputs(PageCommonInputs pageCommonInputs) {
        this.pageCommonInputs = pageCommonInputs;
    }
    //</editor-fold>
}
