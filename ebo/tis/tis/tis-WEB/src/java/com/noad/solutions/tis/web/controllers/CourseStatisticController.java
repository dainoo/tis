/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.BinderSplitter;
import com.noad.solutions.common.classes.utils.CourseStatisticsCommons;
import com.noad.solutions.common.classes.utils.ExamsGrader;
import com.noad.solutions.common.classes.utils.IncompleteType;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.ReportParametersCommons;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.common.report.utils.ReportUtility;
import com.noad.solutions.tis.ejb.entities.Course;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.ProgramCourse;
import com.noad.solutions.tis.ejb.entities.StudentMark;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Daud
 */
@Named(value = "courseStatisticController")
@SessionScoped
public class CourseStatisticController implements Serializable {

    private UserSessionData userSessionData;
    private CourseStatisticsCommons courseStatisticsCommons = new CourseStatisticsCommons();
    private Program program = new Program();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private List<StudentMark> listOfStudentMarks = new ArrayList<StudentMark>();
    private List<CourseStatisticsCommons> listOfCourseStatisticsCommonses;
    private DataModel<CourseStatisticsCommons> courseStatisticsCommonsesDataModel;
    private int totalCoursesAs = 0, totalCoursesBPlus = 0, totalCourseBs = 0, totalCourseCPlus = 0, totalDefers = 0, totalMedical = 0, totalFails = 0;
    private int totalCoursesCs = 0, totalCoursesDPlus = 0, totalCoursesDs = 0, totalCoursesEs = 0, totalCoursesFs = 0, totalMarksNotAvailable = 0;
    private int totalOverAllCourses = 0;
    private String totalOverAllFailed = null;
    private String[] gpaGrade = {"A", "B+", "B", "C+", "C", "D+", "D", "E"};
    private String[] cwaGrade = {"A", "B", "C", "D", "F"};
    private String[] othersGrade = {IncompleteType.TRAILED, IncompleteType.DEFERRED, IncompleteType.MEDICAL, IncompleteType.MARKS_NOT_AVAILABLE};
    private List<Course> listOfCourses;

    public CourseStatisticController() {
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);

    }

    public void computeCourseStatistics() {

        try {
            List<ProgramCourse> listOfProgramCourses;

            listOfProgramCourses = new ArrayList<ProgramCourse>();
            listOfCourseStatisticsCommonses = new ArrayList<CourseStatisticsCommons>();
            courseStatisticsCommonsesDataModel = new ListDataModel<CourseStatisticsCommons>();
            program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());
            listOfProgramCourses = TisEjbSource.getBasicQuariesInstance().loadProgramCourses(selectedItemHelper.getSelectedProgram(), selectedItemHelper.getSelectedAcademicLevel(), userSessionData.getCurrentAcademicYear().getSemester());
            if (listOfProgramCourses.isEmpty()) {
                JSFMessages.errorMessage("No Courses Found ");
                return;
            }
            listOfCourses = BinderSplitter.coursesSplitterList(listOfProgramCourses.get(0).getCourses());
            if (program == null) {
                JSFMessages.errorMessage("No Program Found");
                return;
            }

            int counter = 1;
            if (program.getGradingSystem().equalsIgnoreCase(ExamsGrader.getGPA())) {
                pageCommonInputs.showGradingType = true;
                for (Course eachCourse : listOfCourses) {
                    listOfStudentMarks = TisEjbSource.getExamsProcessingInstance().getCurrentSemesterStudentCourseMark(selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                    String couresName = eachCourse.getCourseInitials() + " " + eachCourse.getCourseCode() + " " + eachCourse.getCourseName();

                    courseStatisticsCommons.setCourseName(couresName);
                    courseStatisticsCommons.setCounter(String.valueOf(counter));
                    courseStatisticsCommons.setCredit(String.valueOf(eachCourse.getCreditHours()));
                    courseStatisticsCommons.setNumberOfAs(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfGradesObtained(gpaGrade[0], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfBPlus(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfGradesObtained(gpaGrade[1], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfBs(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfGradesObtained(gpaGrade[2], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfCPlus(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfGradesObtained(gpaGrade[3], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfCs(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfGradesObtained(gpaGrade[4], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfDPlus(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfGradesObtained(gpaGrade[5], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfDs(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfGradesObtained(gpaGrade[6], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfEs(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfGradesObtained(gpaGrade[7], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfDeferres(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfOtherGradesObtained(othersGrade[1], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfMedicalCases(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfOtherGradesObtained(othersGrade[2], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfMarksNotAvailable(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfOtherGradesObtained(othersGrade[3], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));

                    double maxMark = TisEjbSource.getExamsProcessingInstance().getMaxCourseMark(selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                    double minMark = TisEjbSource.getExamsProcessingInstance().getMinCourseMark(selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                    System.out.println("THE MAX VALUE IS " + maxMark);
                    System.out.println("THE MIN VALUE IS " + minMark);
                    double average = ExamsGrader.findAverage(minMark, maxMark);
                    double std = ExamsGrader.findSTD(minMark, maxMark, average);

                    courseStatisticsCommons.setMax(String.valueOf(maxMark));
                    courseStatisticsCommons.setMin(String.valueOf(minMark));
                    courseStatisticsCommons.setAvg(String.valueOf(average));
                    courseStatisticsCommons.setStd(String.valueOf(std));
                    totalOverAllCourses = 0;
                    int totalGradesPerCourse = (Integer.parseInt(courseStatisticsCommons.getNumberOfAs()) + Integer.parseInt(courseStatisticsCommons.getNumberOfBs())
                            + Integer.parseInt(courseStatisticsCommons.getNumberOfCs()) + Integer.parseInt(courseStatisticsCommons.getNumberOfDs()) + Integer.parseInt(courseStatisticsCommons.getNumberOfEs()));
                    totalGradesPerCourse += Integer.parseInt(courseStatisticsCommons.getNumberOfBPlus()) + Integer.parseInt(courseStatisticsCommons.getNumberOfCPlus()) + Integer.parseInt(courseStatisticsCommons.getNumberOfDPlus());
                    System.out.println("THE NUMBER STUDENTS FOR COURSE " + eachCourse.getCourseName() + "\t " + totalGradesPerCourse);
                    String overallCourseFailed = String.valueOf(courseStatisticsCommons.getNumberOfEs()) + " / " + String.valueOf(totalGradesPerCourse);
                    courseStatisticsCommons.setNumberOfFailed(overallCourseFailed);
                    totalOverAllCourses += totalGradesPerCourse;
                    listOfCourseStatisticsCommonses.add(courseStatisticsCommons);
                    courseStatisticsCommons = new CourseStatisticsCommons();
                    counter++;
                }
                totalCoursesAs = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(gpaGrade[0], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalCoursesBPlus = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(gpaGrade[1], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalCourseBs = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(gpaGrade[2], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalCourseCPlus = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(gpaGrade[3], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalCoursesCs = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(gpaGrade[4], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalCoursesDPlus = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(gpaGrade[5], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalCoursesDs = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(gpaGrade[6], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalCoursesEs = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(gpaGrade[7], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalFails = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(gpaGrade[7], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                
                totalDefers = TisEjbSource.getExamsProcessingInstance().countNumberOfIncompleteCourses(selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel(),othersGrade[1]);
                totalMedical = TisEjbSource.getExamsProcessingInstance().countNumberOfIncompleteCourses(selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel(),othersGrade[2]);
                totalMarksNotAvailable = TisEjbSource.getExamsProcessingInstance().countNumberOfIncompleteCourses(selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel(),othersGrade[3]);
                totalOverAllFailed = String.valueOf(totalCoursesEs + " / " + totalOverAllCourses);


                if (listOfCourseStatisticsCommonses.isEmpty()) {
                    JSFMessages.errorMessage("Semester Courses Marks Have Not Been Uploaded Yet");
                    return;
                }
                courseStatisticsCommonsesDataModel = new ListDataModel<CourseStatisticsCommons>(listOfCourseStatisticsCommonses);


            } else if (program.getGradingSystem().equalsIgnoreCase(ExamsGrader.getCWA())) {
                pageCommonInputs.showGradingType = false;
                for (Course eachCourse : listOfCourses) {
                    listOfStudentMarks = TisEjbSource.getExamsProcessingInstance().getCurrentSemesterStudentCourseMark(selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                    String couresName = eachCourse.getCourseInitials() + " " + eachCourse.getCourseCode() + " " + eachCourse.getCourseName();

                    courseStatisticsCommons.setCourseName(couresName);
                    courseStatisticsCommons.setCredit(String.valueOf(eachCourse.getCreditHours()));
                    courseStatisticsCommons.setCounter(String.valueOf(counter));
                    courseStatisticsCommons.setNumberOfAs(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfGradesObtained(cwaGrade[0], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfBs(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfGradesObtained(cwaGrade[1], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfCs(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfGradesObtained(cwaGrade[2], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfDs(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfGradesObtained(cwaGrade[3], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfFs(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfGradesObtained(cwaGrade[4], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfDeferres(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfOtherGradesObtained(othersGrade[1], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfMedicalCases(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfOtherGradesObtained(othersGrade[2], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));
                    courseStatisticsCommons.setNumberOfMarksNotAvailable(String.valueOf(TisEjbSource.getExamsProcessingInstance().countNumberOfOtherGradesObtained(othersGrade[3], selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel())));

                    double maxMark = TisEjbSource.getExamsProcessingInstance().getMaxCourseMark(selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                    double minMark = TisEjbSource.getExamsProcessingInstance().getMinCourseMark(selectedItemHelper.getSelectedProgram(), eachCourse.getCourseId(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                    System.out.println("THE MAX VALUE IS " + maxMark);
                    System.out.println("THE MIN VALUE IS " + minMark);
                    double average = ExamsGrader.findAverage(minMark, maxMark);
                    double std = ExamsGrader.findSTD(minMark, maxMark, average);

                    courseStatisticsCommons.setMax(String.valueOf(maxMark));
                    courseStatisticsCommons.setMin(String.valueOf(minMark));
                    courseStatisticsCommons.setAvg(String.valueOf(average));
                    courseStatisticsCommons.setStd(String.valueOf(std));

                    int totalGradesPerCourse = (Integer.parseInt(courseStatisticsCommons.getNumberOfAs()) + Integer.parseInt(courseStatisticsCommons.getNumberOfBs())
                            + Integer.parseInt(courseStatisticsCommons.getNumberOfCs()) + Integer.parseInt(courseStatisticsCommons.getNumberOfDs()) + Integer.parseInt(courseStatisticsCommons.getNumberOfEs()));
                    totalGradesPerCourse += Integer.parseInt(courseStatisticsCommons.getNumberOfBPlus()) + Integer.parseInt(courseStatisticsCommons.getNumberOfCPlus()) + Integer.parseInt(courseStatisticsCommons.getNumberOfDPlus());
                    System.out.println("THE NUMBER STUDENTS FOR COURSE " + eachCourse.getCourseName() + "\t " + totalGradesPerCourse);
                    totalOverAllCourses += totalGradesPerCourse;
                    String overallCourseFailed = String.valueOf(courseStatisticsCommons.getNumberOfEs()) + " / " + String.valueOf(totalGradesPerCourse);
                    totalOverAllFailed += Integer.parseInt(overallCourseFailed);
                    courseStatisticsCommons.setNumberOfFailed(overallCourseFailed);
                    courseStatisticsCommons.setFails(overallCourseFailed);
                    listOfCourseStatisticsCommonses.add(courseStatisticsCommons);
                    courseStatisticsCommons = new CourseStatisticsCommons();
                    counter++;
                }
                totalOverAllFailed += totalOverAllFailed;
                totalCoursesAs = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(gpaGrade[0], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalCourseBs = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(gpaGrade[1], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalCoursesCs = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(gpaGrade[2], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalCoursesDs = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(gpaGrade[3], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalCoursesFs = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(gpaGrade[4], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalDefers = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(othersGrade[1], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalMedical = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(othersGrade[2], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalMarksNotAvailable = TisEjbSource.getExamsProcessingInstance().countNumberOfCoursesGradesObtained(othersGrade[3], selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedAcademicLevel());
                totalOverAllFailed = String.valueOf(totalCoursesFs + " / " + totalOverAllCourses);

                if (listOfCourseStatisticsCommonses.isEmpty()) {
                    JSFMessages.errorMessage("Semester Courses Marks Have Not Been Uploaded Yet");
                    return;
                }
                courseStatisticsCommonsesDataModel = new ListDataModel<CourseStatisticsCommons>(listOfCourseStatisticsCommonses);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printCourseStatistics() {
        try {
            if (listOfCourseStatisticsCommonses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_TO_PRINT);
                return;
            }
            String academicYear = userSessionData.getCurrentAcademicYear().getAcademicYear() + " SEMESTER " + userSessionData.getCurrentAcademicYear().getSemester();
            HashMap hashMap = new HashMap();
            System.out.println("THE PROGRAM NAME IS " + program.getProgramName());
            hashMap.put("academicYear", academicYear);
            hashMap.put("programName", program.getProgramName());
            hashMap.put("totalCoursesAs", String.valueOf(totalCoursesAs));
            hashMap.put("totalCoursesBPlus", String.valueOf(totalCoursesBPlus));
            hashMap.put("totalCourseBs", String.valueOf(totalCourseBs));
            hashMap.put("totalCourseCPlus", String.valueOf(totalCourseCPlus));
            hashMap.put("totalCoursesCs", String.valueOf(totalCoursesCs));
            hashMap.put("totalCoursesDPlus", String.valueOf(totalCoursesDPlus));
            hashMap.put("totalCoursesDs", String.valueOf(totalCoursesDs));
            hashMap.put("totalCoursesEs", String.valueOf(totalCoursesEs));
            hashMap.put("totalOverAllFailed", String.valueOf(totalOverAllFailed));
            hashMap.put("totalDefers", String.valueOf(totalDefers));
            hashMap.put("totalMedical", String.valueOf(totalMedical));
            hashMap.put("totalMarksNotAvailable", String.valueOf(totalMarksNotAvailable));

            hashMap.putAll(ReportParametersCommons.getCommonParameters());
            ReportUtility reportUtility = new ReportUtility(hashMap, "course-statistics", listOfCourseStatisticsCommonses, ReportUtility.PDF_FILE);
            reportUtility.generateReport();


        } catch (Exception e) {
        }
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public String[] getGpaGrade() {
        return gpaGrade;
    }

    public void setGpaGrade(String[] gpaGrade) {
        this.gpaGrade = gpaGrade;
    }

    public String[] getCwaGrade() {
        return cwaGrade;
    }

    public int getTotalFails() {
        return totalFails;
    }

    public String getTotalOverAllFailed() {
        return totalOverAllFailed;
    }

    public void setTotalOverAllFailed(String totalOverAllFailed) {
        this.totalOverAllFailed = totalOverAllFailed;
    }

    public void setTotalFails(int totalFails) {
        this.totalFails = totalFails;
    }

    public int getTotalOverAllCourses() {
        return totalOverAllCourses;
    }

    public void setTotalOverAllCourses(int totalOverAllCourses) {
        this.totalOverAllCourses = totalOverAllCourses;
    }

    public void setCwaGrade(String[] cwaGrade) {
        this.cwaGrade = cwaGrade;
    }

    public UserSessionData getUserSessionData() {
        return userSessionData;
    }

    public void setUserSessionData(UserSessionData userSessionData) {
        this.userSessionData = userSessionData;
    }

    public CourseStatisticsCommons getCourseStatisticsCommons() {
        return courseStatisticsCommons;
    }

    public void setCourseStatisticsCommons(CourseStatisticsCommons courseStatisticsCommons) {
        this.courseStatisticsCommons = courseStatisticsCommons;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
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

    public List<StudentMark> getListOfStudentMarks() {
        return listOfStudentMarks;
    }

    public void setListOfStudentMarks(List<StudentMark> listOfStudentMarks) {
        this.listOfStudentMarks = listOfStudentMarks;
    }

    public List<CourseStatisticsCommons> getListOfCourseStatisticsCommonses() {
        return listOfCourseStatisticsCommonses;
    }

    public void setListOfCourseStatisticsCommonses(List<CourseStatisticsCommons> listOfCourseStatisticsCommonses) {
        this.listOfCourseStatisticsCommonses = listOfCourseStatisticsCommonses;
    }

    public DataModel<CourseStatisticsCommons> getCourseStatisticsCommonsesDataModel() {
        return courseStatisticsCommonsesDataModel;
    }

    public void setCourseStatisticsCommonsesDataModel(DataModel<CourseStatisticsCommons> courseStatisticsCommonsesDataModel) {
        this.courseStatisticsCommonsesDataModel = courseStatisticsCommonsesDataModel;
    }

    public int getTotalCoursesAs() {
        return totalCoursesAs;
    }

    public void setTotalCoursesAs(int totalCoursesAs) {
        this.totalCoursesAs = totalCoursesAs;
    }

    public int getTotalCoursesBPlus() {
        return totalCoursesBPlus;
    }

    public void setTotalCoursesBPlus(int totalCoursesBPlus) {
        this.totalCoursesBPlus = totalCoursesBPlus;
    }

    public int getTotalCourseBs() {
        return totalCourseBs;
    }

    public void setTotalCourseBs(int totalCourseBs) {
        this.totalCourseBs = totalCourseBs;
    }

    public int getTotalCourseCPlus() {
        return totalCourseCPlus;
    }

    public void setTotalCourseCPlus(int totalCourseCPlus) {
        this.totalCourseCPlus = totalCourseCPlus;
    }

    public int getTotalCoursesCs() {
        return totalCoursesCs;
    }

    public void setTotalCoursesCs(int totalCoursesCs) {
        this.totalCoursesCs = totalCoursesCs;
    }

    public int getTotalCoursesDPlus() {
        return totalCoursesDPlus;
    }

    public void setTotalCoursesDPlus(int totalCoursesDPlus) {
        this.totalCoursesDPlus = totalCoursesDPlus;
    }

    public int getTotalCoursesDs() {
        return totalCoursesDs;
    }

    public void setTotalCoursesDs(int totalCoursesDs) {
        this.totalCoursesDs = totalCoursesDs;
    }

    public int getTotalCoursesEs() {
        return totalCoursesEs;
    }

    public void setTotalCoursesEs(int totalCoursesEs) {
        this.totalCoursesEs = totalCoursesEs;
    }

    public int getTotalCoursesFs() {
        return totalCoursesFs;
    }

    public void setTotalCoursesFs(int totalCoursesFs) {
        this.totalCoursesFs = totalCoursesFs;
    }

    public List<Course> getListOfCourses() {
        return listOfCourses;
    }

    public void setListOfCourses(List<Course> listOfCourses) {
        this.listOfCourses = listOfCourses;
    }

    public int getTotalDefers() {
        return totalDefers;
    }

    public void setTotalDefers(int totalDefers) {
        this.totalDefers = totalDefers;
    }

    public int getTotalMedical() {
        return totalMedical;
    }

    public void setTotalMedical(int totalMedical) {
        this.totalMedical = totalMedical;
    }

    public int getTotalMarksNotAvailable() {
        return totalMarksNotAvailable;
    }

    public void setTotalMarksNotAvailable(int totalMarksNotAvailable) {
        this.totalMarksNotAvailable = totalMarksNotAvailable;
    }

    public String[] getOthersGrade() {
        return othersGrade;
    }

    public void setOthersGrade(String[] othersGrade) {
        this.othersGrade = othersGrade;
    }
    //</editor-fold>
}
