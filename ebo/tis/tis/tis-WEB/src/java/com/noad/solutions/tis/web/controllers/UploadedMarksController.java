/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.BinderSplitter;
import com.noad.solutions.common.classes.utils.ExamsGrader;
import com.noad.solutions.common.classes.utils.JSFIdGenerator;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.tis.ejb.entities.Course;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.ProgramCourse;
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
@Named(value = "uploadedMarksController")
@SessionScoped
public class UploadedMarksController implements Serializable {

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

    public UploadedMarksController() {
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);
        preLoadLecturerDeptPrograms();
    }

    private void preLoadLecturerDeptPrograms() {
        try {
            List<Program> listOfPrograms = new ArrayList<Program>();
            listOfPrograms = TisEjbSource.getCrudInstance().programGetAll(false);
            departmentProgramsSelectItems = new SelectItem[listOfPrograms.size() + 1];
            departmentProgramsSelectItems[0] = new SelectItem("", "PLEASE SELECT ONE");
            int counter = 1;
            for (Program eachProgram : listOfPrograms) {
                departmentProgramsSelectItems[counter] = new SelectItem(eachProgram.getProgramId(), eachProgram.getProgramName());
                counter++;
            }
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void programSemesterCoursesListner() {
        try {
            List<ProgramCourse> listOfProgramCourses = new ArrayList<ProgramCourse>();
            listOfCourses = new ArrayList<Course>();
            listOfProgramCourses = TisEjbSource.getBasicQuariesInstance().getProgramCourseAndSemester(selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear().getSemester());
            for (ProgramCourse programCourse : listOfProgramCourses) {
                listOfCourses.addAll(BinderSplitter.coursesSplitterList(programCourse.getCourses()));
            }
            System.out.println("THE SIZE OF COURSES IS " + listOfCourses.size());
            if (listOfCourses == null) {
                programSemesterCoursesSelectItems = new SelectItem[0];
                programSemesterCoursesSelectItems[0] = new SelectItem("", "NO COURSE(S) FOUND");
            } else {
                programSemesterCoursesSelectItems = new SelectItem[listOfCourses.size()];
                int counter = 0;
                for (Course eachCourse : listOfCourses) {
                    programSemesterCoursesSelectItems[counter] = new SelectItem((eachCourse.getCourseId()), (eachCourse.getCourseInitials() + " " + eachCourse.getCourseCode() + " " + eachCourse.getCourseName()));
                    counter++;
                }
            }
        } catch (Exception e) {
            appLogger(e);

        }

    }

    public String loadStudents() {

        try {


            Program program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());
            Course course = TisEjbSource.getCrudInstance().courseFind(selectedItemHelper.getSelectedCourse());

            listOfStudentMark = new ArrayList<StudentMark>();
            studentMarksDataModel = new ListDataModel<StudentMark>(listOfStudentMark);
            listOfStudentMark = TisEjbSource.getExamsProcessingInstance().getClassStudentsMarks(selectedItemHelper.getSelectedProgram(), userSessionData.getCurrentAcademicYear(), selectedItemHelper.getSelectedCourse());

            System.out.println("SZE OF STUDENT MARKS IS " + listOfStudentMark.size());
            if (listOfStudentMark.isEmpty()) {
                JSFMessages.errorMessage("No Student Marks Found For " + course.getCourseInitials() + " " + course.getCourseCode() + " " + course.getCourseName());
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

                    eachMark.setStudentMarkId(eachMark.getStudent().getStudentId() + JSFIdGenerator.generateRandomId());
                    eachMark.setLecturer(userSessionData.getStaff());
                    eachMark.setTotalMark(totalMark);
                    eachMark.setAcademicYear(userSessionData.getCurrentAcademicYear());
                    eachMark.setCourse(BinderSplitter.getCourseSplitter(selectedItemHelper.getSelectedCourse()));
                    eachMark.setAcademicLevel(eachMark.getAcademicLevel());
                    eachMark.setCreatedBy(userSessionData.getStaff().getUserAccount().getUsername());
                    TisEjbSource.getCrudInstance().studentMarkCreate(eachMark);
                }

                for (SemesterRegistration eachRegistration : listOfRegisteredStudents) {
                    SemesterRegistration semesterRegistration = TisEjbSource.getCrudInstance().semesterRegistrationFind(eachRegistration.getSemesterRegistrationId());
                    String marksUploaded = "";

                    if (semesterRegistration.getUploadedMarks().equalsIgnoreCase("NO")) {
                        marksUploaded = selectedItemHelper.getSelectedCourse();
                        semesterRegistration.setUploadedMarks(marksUploaded);
                    } else {
                        marksUploaded = "/" + selectedItemHelper.getSelectedCourse();
                        semesterRegistration.setUploadedMarks(marksUploaded);
                    }
                    TisEjbSource.getCrudInstance().semesterRegistrationUpdate(semesterRegistration);

                }
                JSFMessages.infoMessage(listOfStudentMark.size() + " Marks Uploaded");
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
        Logger.getLogger(UploadedMarksController.class.getName()).log(Level.SEVERE, null, e.getCause());
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