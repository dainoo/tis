/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.ExamsGrader;
import com.noad.solutions.common.classes.utils.IncompleteType;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.ReportParametersCommons;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.common.classes.utils.WeightedAverageCommons;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.common.number.utils.DecimalPlace;
import com.noad.solutions.common.report.utils.ReportUtility;
import com.noad.solutions.tis.ejb.entities.IncompleteCourse;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.Student;
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
@Named(value = "weightedAverageController")
@SessionScoped
public class WeightedAverageController implements Serializable {

    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private UserSessionData userSessionData = new UserSessionData();
    private Program program = new Program();
    private WeightedAverageCommons weightedAverageCommons = new WeightedAverageCommons();
    private List<WeightedAverageCommons> listOfWeightedAverageCommonses = null;
    DataModel<WeightedAverageCommons> weightedAverageCommonsesDataModel = null;
    private String weightedAverageHeader = "";

    public WeightedAverageController() {
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);
    }

    public void generateWeightedAverage() {

        try {
            listOfWeightedAverageCommonses = new ArrayList<WeightedAverageCommons>();
            weightedAverageCommonsesDataModel = new ListDataModel<WeightedAverageCommons>(listOfWeightedAverageCommonses);
            program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());
            weightedAverageHeader = program.getGradingSystem();
            pageCommonInputs.showGradingType = program.getGradingSystem().equalsIgnoreCase(ExamsGrader.getGPA()) ? true : false;
            weightedAverageHeader = program.getGradingSystem();
            List<Student> listOfStudents = TisEjbSource.getExamsProcessingInstance().getSelectedProgramAndLevelStudents(selectedItemHelper.getSelectedProgram(), 
                    selectedItemHelper.getSelectedAcademicLevel()
                    ,userSessionData.getCurrentAcademicYear().getAcademicYearId());
            System.out.println("THE SIZE OF STUDENTS BEFORE OPEARATION " + listOfStudents.size());
            if (listOfStudents.isEmpty()) {
                JSFMessages.errorMessage("No Students Found Reading " + program.getProgramName() + " In " + selectedItemHelper.getSelectedAcademicLevel());
                return;
            }
            List<Student> incompleteStudentsList = TisEjbSource.getExamsProcessingInstance().getIncompeletStds(program);;

//            remove all incomplete student of the list of student ,remaining passed students
            for (Student student : incompleteStudentsList) {
                listOfStudents.remove(student);
            }
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>");
            System.out.println("THE SIZE OF STUDENTS AFTER OPEARATION " + listOfStudents.size());
            weightedAverageCommons = new WeightedAverageCommons();
            int counter = 1;
            
            //processing cummulative averager of passed students
            for (Student eachStudent : listOfStudents) {
//                Student eachStudent=eachStudent.g
                List<StudentMark> cummulativeStudentMarks = TisEjbSource.getExamsProcessingInstance().getCummMarkPassed(eachStudent);
                double cummGradePointMark = 0.0;
                double cumCreHrs = 0.0;
                if (pageCommonInputs.isShowGradingType()) {
                    for (StudentMark eachStdMark : cummulativeStudentMarks) {
                        cummGradePointMark += (eachStdMark.getGradePoint() * eachStdMark.getCourse().getCreditHours());
                        cumCreHrs += eachStdMark.getCourse().getCreditHours();
                    }
//                    weightedAverageCommons.setGpa(String.valueOf(DecimalPlace.getTwoDecimalPlaces(cummGradePointMark / cumCreHrs)));
                    weightedAverageCommons.setWeightedAverage(String.valueOf(DecimalPlace.getTwoDecimalPlaces(cummGradePointMark / cumCreHrs)));

                } else {
                    for (StudentMark eachStdMark : cummulativeStudentMarks) {
                        cummGradePointMark += (eachStdMark.getTotalMark() * eachStdMark.getCourse().getCreditHours());
                        cumCreHrs += eachStdMark.getCourse().getCreditHours();
                    }
//                    weightedAverageCommons.setCwa(String.valueOf(DecimalPlace.getTwoDecimalPlaces(cummGradePointMark / cumCreHrs)));
                    weightedAverageCommons.setWeightedAverage(String.valueOf(DecimalPlace.getTwoDecimalPlaces(cummGradePointMark / cumCreHrs)));
                }
                weightedAverageCommons.setCounter(String.valueOf(counter));
                weightedAverageCommons.setNumberOfPasses(String.valueOf(cummulativeStudentMarks.size()));
                weightedAverageCommons.setNumberOfTrails("0");
                weightedAverageCommons.setIncompleteCourses("Pass");
                weightedAverageCommons.setIndexNumber(eachStudent.getStudentIndexNumber());
                weightedAverageCommons.setStudentName(eachStudent.getTitle() + " " + eachStudent.getSurname() + " " + eachStudent.getOtherNames());
                listOfWeightedAverageCommonses.add(weightedAverageCommons);
                weightedAverageCommons = new WeightedAverageCommons();
            counter++;
            
            }
            
            //Processing incomplete students
            System.out.println("THE NUMBER OF INCOMPLETE STUDENTS "+incompleteStudentsList.size());
            for (Student eachIncompleteStudent : incompleteStudentsList) {
                List<StudentMark> cummulativeStudentMarks = TisEjbSource.getExamsProcessingInstance().getCummMarkPassed(eachIncompleteStudent);
                double cummGradePointMark = 0.0;
                double cumCreHrs = 0.0;
                if (pageCommonInputs.isShowGradingType()) {
                    for (StudentMark eachStdMark : cummulativeStudentMarks) {
                        cummGradePointMark += (eachStdMark.getGradePoint() * eachStdMark.getCourse().getCreditHours());
                        cumCreHrs += eachStdMark.getCourse().getCreditHours();
                    }
                    weightedAverageCommons.setWeightedAverage(String.valueOf(DecimalPlace.getTwoDecimalPlaces(cummGradePointMark / cumCreHrs)));

                } else {
                    for (StudentMark eachStdMark : cummulativeStudentMarks) {
                        cummGradePointMark += (eachStdMark.getTotalMark() * eachStdMark.getCourse().getCreditHours());
                        cumCreHrs += eachStdMark.getCourse().getCreditHours();
                    }
                    weightedAverageCommons.setWeightedAverage(String.valueOf(DecimalPlace.getTwoDecimalPlaces(cummGradePointMark / cumCreHrs)));
                }
                List<IncompleteCourse> listOfIncompleteCourses = TisEjbSource.getExamsProcessingInstance().getStudentTrailedCourses(eachIncompleteStudent, IncompleteType.ALL_INCOMPLETES);
                System.out.println("THE INCOMPLETE IS "+listOfIncompleteCourses.size());
                String trailedCourses = "";
                for (IncompleteCourse eachTrail : listOfIncompleteCourses) {
                    trailedCourses += eachTrail.getCourse().getCourseInitials() + " " + eachTrail.getCourse().getCourseCode() + 
                            "("+eachTrail.getIncompleteType()+"),";
                }
                weightedAverageCommons.setCounter(String.valueOf(counter));
                weightedAverageCommons.setNumberOfPasses(String.valueOf(cummulativeStudentMarks.size() - listOfIncompleteCourses.size()));
                weightedAverageCommons.setNumberOfTrails(String.valueOf(listOfIncompleteCourses.size()));
                weightedAverageCommons.setIncompleteCourses(trailedCourses);
                weightedAverageCommons.setIndexNumber(eachIncompleteStudent.getStudentIndexNumber());
                weightedAverageCommons.setStudentName(eachIncompleteStudent.getTitle() + " " + eachIncompleteStudent.getSurname() + " " + eachIncompleteStudent.getOtherNames());
                listOfWeightedAverageCommonses.add(weightedAverageCommons);
                weightedAverageCommons = new WeightedAverageCommons();
                counter++;
            }

            weightedAverageCommonsesDataModel = new ListDataModel<WeightedAverageCommons>(listOfWeightedAverageCommonses);
        } catch (Exception e) {
        }
    }

    public void printWeightedAverage() {

        try {
            try {
                if (listOfWeightedAverageCommonses.isEmpty()) {
                    JSFMessages.errorMessage(JSFMessages.NO_DATA_TO_PRINT);
                    return;
                }
                HashMap hashMap = new HashMap();
                hashMap.put("weightedAverageHeader", weightedAverageHeader);
                hashMap.put("programName", program.getProgramName());
                hashMap.put("academicLevel",selectedItemHelper.getSelectedAcademicLevel());
                hashMap.putAll(ReportParametersCommons.getCommonParameters());

                ReportUtility reportUtility = new ReportUtility(hashMap, "weighted_average", listOfWeightedAverageCommonses, ReportUtility.PDF_FILE);
                reportUtility.generateReport();

            } catch (Exception e) {
            }

        } catch (Exception e) {
        }
    }

    public void transferFailed() {

        List<StudentMark> listFailed = TisEjbSource.getExamsProcessingInstance().getFailed();
        IncompleteCourse ic = new IncompleteCourse();
        for (StudentMark studentMark : listFailed) {
            ic.setAcademicLevel(studentMark.getAcademicLevel());
            ic.setAcademicYear(studentMark.getAcademicYear());
            ic.setCourse(studentMark.getCourse());
            ic.setIncompleteCourseId(studentMark.getStudentMarkId());
            ic.setPassed("NO");
            ic.setStudent(studentMark.getStudent());
            TisEjbSource.getCrudInstance().incompleteCourseCreate(ic);
            ic = new IncompleteCourse();
        }
    }

    public SelectedItemHelper getSelectedItemHelper() {
        return selectedItemHelper;
    }

    public void setSelectedItemHelper(SelectedItemHelper selectedItemHelper) {
        this.selectedItemHelper = selectedItemHelper;
    }

    public PageCommonInputs getPageCommonInputs() {
        return pageCommonInputs;
    }

    public void setPageCommonInputs(PageCommonInputs pageCommonInputs) {
        this.pageCommonInputs = pageCommonInputs;
    }

    public UserSessionData getUserSessionData() {
        return userSessionData;
    }

    public void setUserSessionData(UserSessionData userSessionData) {
        this.userSessionData = userSessionData;
    }

    public WeightedAverageCommons getWeightedAverageCommons() {
        return weightedAverageCommons;
    }

    public void setWeightedAverageCommons(WeightedAverageCommons weightedAverageCommons) {
        this.weightedAverageCommons = weightedAverageCommons;
    }

    public String getWeightedAverageHeader() {
        return weightedAverageHeader;
    }

    public void setWeightedAverageHeader(String weightedAverageHeader) {
        this.weightedAverageHeader = weightedAverageHeader;
    }

    public List<WeightedAverageCommons> getListOfWeightedAverageCommonses() {
        return listOfWeightedAverageCommonses;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public void setListOfWeightedAverageCommonses(List<WeightedAverageCommons> listOfWeightedAverageCommonses) {
        this.listOfWeightedAverageCommonses = listOfWeightedAverageCommonses;
    }

    public DataModel<WeightedAverageCommons> getWeightedAverageCommonsesDataModel() {
        return weightedAverageCommonsesDataModel;
    }

    public void setWeightedAverageCommonsesDataModel(DataModel<WeightedAverageCommons> weightedAverageCommonsesDataModel) {
        this.weightedAverageCommonsesDataModel = weightedAverageCommonsesDataModel;
    }
}
