/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.BinderSplitter;
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
import com.noad.solutions.tis.ejb.entities.LecturerTeachingCourse;
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
@Named(value = "marksUploadController")
@SessionScoped
public class MarksUploadController implements Serializable {

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
    private SelectItem[] lectureAssignedCoursesSelectItems = null;

    public MarksUploadController() {
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);
        preLoadLecturerDeptPrograms();
    }

    private void preLoadLecturerDeptPrograms() {
        try {
            List<Program> listOfPrograms = new ArrayList<Program>();
            listOfPrograms = TisEjbSource.getCrudInstance().programFindByAttribute("department.departmentId", userSessionData.getStaff().getDepartment().getDepartmentId(), "STRING", false);
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

    public void loadLecutreAssignedCourses() {
        try {
            listOfCourses = new ArrayList<Course>();
            String lecturerId = userSessionData.getStaff().getStaffId();
            LecturerTeachingCourse lecturerTeachingCourse = TisEjbSource.getExamsProcessingInstance().getLecturerSemesterCourse(lecturerId);
            if (lecturerTeachingCourse == null) {
                lectureAssignedCoursesSelectItems = new SelectItem[0];
                lectureAssignedCoursesSelectItems[0] = new SelectItem("", "NO COURSE(S) ASSIGNED YOU");
            } else {
                listOfCourses = BinderSplitter.coursesSplitterList(lecturerTeachingCourse.getCourses());
                lectureAssignedCoursesSelectItems = new SelectItem[listOfCourses.size()];
                int counter = 0;
                for (Course eachCourse : listOfCourses) {
                    lectureAssignedCoursesSelectItems[counter] = new SelectItem((eachCourse.getCourseInitials() + " " + eachCourse.getCourseCode()), (eachCourse.getCourseInitials() + " " + eachCourse.getCourseCode() + " " + eachCourse.getCourseName()));
                    counter++;
                }
            }
        } catch (Exception e) {
            appLogger(e);

        }

    }

    public String loadStudents() {

        try {
            listOfRegisteredStudents = new ArrayList<SemesterRegistration>();
            studentMarksDataModel = new ListDataModel<StudentMark>(listOfStudentMark);

            listOfRegisteredStudents = TisEjbSource.getExamsProcessingInstance().getStudentsRegisteredForACourse(selectedItemHelper.getSelectedProgram(), selectedItemHelper.getSelectedCourse());
            if (listOfRegisteredStudents.isEmpty()) {
                JSFMessages.errorMessage("No Students Registered The Selected Course OR Marked Uploaded Already");
                return "";
            }
            System.out.println("NUMBER OF REGISTERED STUDENTS IS " + listOfRegisteredStudents.size());

            Program program = null;
            program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());
            listOfStudentMark = new ArrayList<StudentMark>();
            studentMarksDataModel = new ListDataModel<StudentMark>(listOfStudentMark);


            if (program.getGradingSystem().equalsIgnoreCase(ExamsGrader.getGPA())) {
                pageCommonInputs.showGradingType = true;
                for (SemesterRegistration eachStudent : listOfRegisteredStudents) {
                    studentMark.setStudent(eachStudent.getStudent());
                    studentMark.setAcademicLevel(eachStudent.getAcademicLevel());
                    listOfStudentMark.add(studentMark);
                    studentMark = new StudentMark();
                }
            } else {
                pageCommonInputs.showGradingType = false;
                for (SemesterRegistration eachStudent : listOfRegisteredStudents) {
                    studentMark.setStudent(eachStudent.getStudent());
                    studentMark.setClassWorkOne(0.00);
                    studentMark.setClassWorkTwo(0.00);
                    listOfStudentMark.add(studentMark);
                    studentMark = new StudentMark();

                }
            }
            System.out.println("NUMBER OF STUDENTS IS " + listOfStudentMark.size());

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

                    eachMark.setStudentMarkId(eachMark.getStudent().getStudentId() + JSFIdGenerator.generateRandomId());
                    eachMark.setLecturer(userSessionData.getStaff());
                    eachMark.setTotalMark(totalMark);
                    eachMark.setAcademicYear(userSessionData.getCurrentAcademicYear());
                    eachMark.setCourse(BinderSplitter.getCourseSplitter(selectedItemHelper.getSelectedCourse()));
                    eachMark.setAcademicLevel(eachMark.getAcademicLevel());
                    eachMark.setCreatedBy(userSessionData.getStaff().getUserAccount().getUsername());
                    TisEjbSource.getCrudInstance().studentMarkCreate(eachMark);

                    IncompleteCourse ic = TisEjbSource.getExamsProcessingInstance().getStudentPreviousIncompleteCourses(eachMark.getStudent(), eachMark.getCourse(),IncompleteType.TRAILED);

                    //Student has NOT failed the course before
                    if (ic == null) {
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

                        //Failed AGAIN
                        IncompleteCourse incompleteCourse = TisEjbSource.getCrudInstance().incompleteCourseFind(ic.getIncompleteCourseId());
                        incompleteCourse.setAcademicLevel(eachMark.getAcademicLevel());
                        incompleteCourse.setAcademicYear(eachMark.getAcademicYear());
                        incompleteCourse.setCourse(eachMark.getCourse());
                        incompleteCourse.setAttempts(incompleteCourse.getAttempts() + 1);
                        incompleteCourse.setStudent(eachMark.getStudent());

                        if (eachMark.getGrade().equalsIgnoreCase("E") || eachMark.getGrade().equalsIgnoreCase("F")) {
                            incompleteCourse.setPassed("NO");
                            incompleteCourse.setIncompleteType(IncompleteType.TRAILED);

                        } //Passed THIS TIME
                        else {
                            incompleteCourse.setPassed("YES");
                        }
                        TisEjbSource.getCrudInstance().incompleteCourseUpdate(incompleteCourse);

//                        NB: to get all the student failed courses search the student mark table for his/her grade E or F
                    }



                }

                //Updates each student registered course for which marks have been uploaded
                for (SemesterRegistration eachRegistration : listOfRegisteredStudents) {
                    SemesterRegistration semesterRegistration = TisEjbSource.getCrudInstance().semesterRegistrationFind(eachRegistration.getSemesterRegistrationId());
                    String marksUploaded = "";

                    if (semesterRegistration.getUploadedMarks().equalsIgnoreCase("NO")) {
                        marksUploaded = selectedItemHelper.getSelectedCourse();
                        semesterRegistration.setUploadedMarks(marksUploaded);
                    } else {
                        marksUploaded = "/" + selectedItemHelper.getSelectedCourse();
                        semesterRegistration.setUploadedMarks(semesterRegistration.getUploadedMarks() + marksUploaded);
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
        studentMark = new StudentMark();
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
        Logger.getLogger(MarksUploadController.class.getName()).log(Level.SEVERE, null, e.getCause());
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

    public List<SemesterRegistration> getListOfRegisteredStudents() {
        return listOfRegisteredStudents;
    }

    public void setListOfRegisteredStudents(List<SemesterRegistration> listOfRegisteredStudents) {
        this.listOfRegisteredStudents = listOfRegisteredStudents;
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

    public SelectItem[] getLectureAssignedCoursesSelectItems() {
        return lectureAssignedCoursesSelectItems;
    }

    public void setLectureAssignedCoursesSelectItems(SelectItem[] lectureAssignedCoursesSelectItems) {
        this.lectureAssignedCoursesSelectItems = lectureAssignedCoursesSelectItems;
    }

    public Student getStudent() {
        return student;
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