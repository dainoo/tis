/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.ExamsGrader;
import com.noad.solutions.common.classes.utils.IncompleteType;
import com.noad.solutions.common.classes.utils.JSFIdGenerator;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.tis.ejb.entities.Course;
import com.noad.solutions.tis.ejb.entities.IncompleteCourse;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.SemesterRegistration;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.ejb.entities.StudentMark;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "marksAmendmentsController")
@SessionScoped
public class MarksAmendmentsController implements Serializable {

    private Student student = new Student();
    private StudentMark studentMark = new StudentMark();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private UserSessionData userSessionData = new UserSessionData();
    private List<StudentMark> listOfStudentMark = null;
    List<SemesterRegistration> listOfRegisteredStudents = new ArrayList<SemesterRegistration>();
    private List<Course> listOfCourses = null;
    private DataModel<StudentMark> studentMarksDataModel;
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private SelectItem[] departmentProgramsSelectItems = null;
    private SelectItem[] programSemesterCoursesSelectItems = null;

    public MarksAmendmentsController() {
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);
    }

    public String loadStudentMarks() {

        try {


            Course course = TisEjbSource.getCrudInstance().courseFind(selectedItemHelper.getSelectedCourse());
            student = TisEjbSource.getBasicQuariesInstance().findStudentByIndexNumber(student.getStudentIndexNumber());
            Program program = student.getProgram();
            listOfStudentMark = new ArrayList<StudentMark>();
            studentMarksDataModel = new ListDataModel<StudentMark>(listOfStudentMark);
            listOfStudentMark = TisEjbSource.getExamsProcessingInstance().getStudentSemesterMarks(student.getStudentIndexNumber(), userSessionData.getCurrentAcademicYear());

            System.out.println("SZE OF STUDENT MARKS IS " + listOfStudentMark.size());
            if (listOfStudentMark.isEmpty()) {
                JSFMessages.errorMessage("No  Marks Found For " + student.getSurname() + " " + student.getOtherNames());
                return "";
            }
            if (program.getGradingSystem().equalsIgnoreCase(ExamsGrader.getGPA())) {
                pageCommonInputs.showGradingType = true;
            } else {
                pageCommonInputs.showGradingType = false;
            }
            studentMarksDataModel = new ListDataModel<StudentMark>(listOfStudentMark);

        } catch (Exception e) {
            appLogger(e);
        }
        return "";


    }

    public void okMark() {
        try {
            double totalMark = 0.0;
            studentMark = studentMarksDataModel.getRowData();
            totalMark = studentMark.getClassWorkOne() + studentMark.getClassWorkTwo() + studentMark.getMidSemMark() + studentMark.getExamMark();
            studentMark.setGrade(ExamsGrader.GPAGradingSystem(totalMark));
            studentMark.setGradePoint(ExamsGrader.GPAGradePoint(studentMark.getGrade()));
            studentMark.setMarkStyle(ExamsGrader.getGradeStyle());
            studentMark.setTotalMark(totalMark);
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public String uploadMarks() {
        try {
            if (listOfStudentMark.isEmpty()) {
                JSFMessages.warnMessage("PLease Enter Students' Semester Marks");
                return "";
            } else {
                for (StudentMark eachMark : listOfStudentMark) {
                    double totalMark = eachMark.getClassWorkOne() + eachMark.getClassWorkTwo() + eachMark.getMidSemMark() + eachMark.getExamMark();

                    eachMark.setTotalMark(totalMark);
                    eachMark.setEditedBy(userSessionData.getStaff().getUserAccount().getUsername());
                    eachMark.setEditedDate(new java.util.Date());
                    TisEjbSource.getCrudInstance().studentMarkUpdate(eachMark);

                    //Checks if the student has trailed the course before
                    IncompleteCourse ic = TisEjbSource.getExamsProcessingInstance().getStudentPreviousIncompleteCourses(eachMark.getStudent(), eachMark.getCourse(),IncompleteType.TRAILED);

                    //Student has NOT failed the course before
                    if (ic == null) {
                        //First time failing the course
                        if (eachMark.getGrade().equalsIgnoreCase("E") || eachMark.getGrade().equalsIgnoreCase("F")) {

                            IncompleteCourse incompleteCourse = new IncompleteCourse();
                            incompleteCourse.setIncompleteCourseId(eachMark.getStudentMarkId() + "-" + JSFIdGenerator.generateRandomId());
                            incompleteCourse.setAcademicLevel(eachMark.getAcademicLevel());
                            incompleteCourse.setAcademicYear(eachMark.getAcademicYear());
                            incompleteCourse.setCourse(eachMark.getCourse());
                            incompleteCourse.setPassed("NO");
                            incompleteCourse.setIncompleteType(IncompleteType.TRAILED);

                            incompleteCourse.setStudent(eachMark.getStudent());
                            TisEjbSource.getCrudInstance().incompleteCourseCreate(incompleteCourse);
                        }
                    } //Student has failed the course before
                    else {

                        //Failed the course AGAIN
                        IncompleteCourse incompleteCourse = TisEjbSource.getCrudInstance().incompleteCourseFind(ic.getIncompleteCourseId());
                        if (eachMark.getGrade().equalsIgnoreCase("E") || eachMark.getGrade().equalsIgnoreCase("F")) {
                            incompleteCourse.setAcademicLevel(eachMark.getAcademicLevel());
                            incompleteCourse.setAcademicYear(eachMark.getAcademicYear());
                            incompleteCourse.setCourse(eachMark.getCourse());
                            incompleteCourse.setAttempts(incompleteCourse.getAttempts() + 1);
                            incompleteCourse.setStudent(eachMark.getStudent());
                            incompleteCourse.setPassed("NO");
                            incompleteCourse.setIncompleteType(IncompleteType.TRAILED);
                            TisEjbSource.getCrudInstance().incompleteCourseUpdate(incompleteCourse);
                        } //Passed THIS TIME when marks updated
                        else {
                            //if so then delete from incomplete courses
                            TisEjbSource.getCrudInstance().incompleteCourseDelete(incompleteCourse, true);
                        }

//                        NB: to get all the student failed courses search the student mark table for his/her grade E or F
                    }



                }

                JSFMessages.infoMessage(listOfStudentMark.size() + " Marks Amended");
                resetButton();
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return "";

    }

    private void resetButton() {
        listOfCourses = new ArrayList<Course>();
        listOfStudentMark = new ArrayList<StudentMark>();
        studentMarksDataModel = new ListDataModel<StudentMark>(listOfStudentMark);

    }

    public String printButton() {
        return null;
    }

    public void clearScreen() {
    }

    void appLogger(Exception e) {
        e.printStackTrace();
        Logger.getLogger(MarksAmendmentsController.class.getName()).log(Level.SEVERE, null, e.getCause());
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public PageCommonInputs getPageCommonInputs() {
        return pageCommonInputs;
    }

    public void setPageCommonInputs(PageCommonInputs pageCommonInputs) {
        this.pageCommonInputs = pageCommonInputs;
    }

    public DataModel<StudentMark> getStudentMarksDataModel() {
        return studentMarksDataModel;
    }

    public void setStudentMarksDataModel(DataModel<StudentMark> studentMarksDataModel) {
        this.studentMarksDataModel = studentMarksDataModel;
    }

    public SelectedItemHelper getSelectedItemHelper() {
        return selectedItemHelper;
    }

    public void setSelectedItemHelper(SelectedItemHelper selectedItemHelper) {
        this.selectedItemHelper = selectedItemHelper;
    }

    public List<StudentMark> getListOfStudentMark() {
        return listOfStudentMark;
    }

    public void setListOfStudentMark(List<StudentMark> listOfStudentMark) {
        this.listOfStudentMark = listOfStudentMark;
    }

    public List<Course> getListOfCourses() {
        return listOfCourses;
    }

    public void setListOfCourses(List<Course> listOfCourses) {
        this.listOfCourses = listOfCourses;
    }

    public Student getStudent() {
        return student;
    }

    public List<SemesterRegistration> getListOfRegisteredStudents() {
        return listOfRegisteredStudents;
    }

    public void setListOfRegisteredStudents(List<SemesterRegistration> listOfRegisteredStudents) {
        this.listOfRegisteredStudents = listOfRegisteredStudents;
    }

    public SelectItem[] getProgramSemesterCoursesSelectItems() {
        return programSemesterCoursesSelectItems;
    }

    public void setProgramSemesterCoursesSelectItems(SelectItem[] programSemesterCoursesSelectItems) {
        this.programSemesterCoursesSelectItems = programSemesterCoursesSelectItems;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentMark getStudentMark() {
        return studentMark;
    }

    public void setStudentMark(StudentMark studentMark) {
        this.studentMark = studentMark;
    }

    public UserSessionData getUserSessionData() {
        return userSessionData;
    }

    public void setUserSessionData(UserSessionData userSessionData) {
        this.userSessionData = userSessionData;
    }

    public SelectItem[] getDepartmentProgramsSelectItems() {
        return departmentProgramsSelectItems;
    }

    public void setDepartmentProgramsSelectItems(SelectItem[] departmentProgramsSelectItems) {
        this.departmentProgramsSelectItems = departmentProgramsSelectItems;
    }
    //</editor-fold>
}