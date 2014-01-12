/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.BinderSplitter;
import com.noad.solutions.common.classes.utils.CssStyles;
import com.noad.solutions.common.intefaces.utils.CommonMethodsInterface;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.ejb.entities.Course;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.JSFIdGenerator;
import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.tis.ejb.entities.AcademicLevel;
import com.noad.solutions.tis.ejb.entities.Department;
import com.noad.solutions.tis.ejb.entities.LecturerTeachingCourse;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.Staff;
import com.noad.solutions.tis.ejb.entities.UserAccount;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author Secretary
 */
//@Named(value = "lecturerCoursesController")
@ManagedBean
@SessionScoped
public class LecturerCoursesController extends CssStyles implements Serializable, CommonMethodsInterface {

    private LecturerTeachingCourse lecturerTeachingCourses = new LecturerTeachingCourse();
    private DataModel<LecturerTeachingCourse> lecturerTeachingCoursessDataModel = new ListDataModel<LecturerTeachingCourse>();
    private List<LecturerTeachingCourse> listOfLecturerTeachingCourses = new ArrayList<LecturerTeachingCourse>();
    private Program program = new Program();
    private Staff staff = new Staff();
    private UserAccount userAccount = new UserAccount();
    private AcademicLevel academicLevel = new AcademicLevel();
    private Department department = new Department();
    private Course course = new Course();
    private List<Course> listOfCourses = new ArrayList<Course>();
    private List<Course> listOfSelectedCourses = new ArrayList<Course>();
    private List<Program> listOfPrograms = new ArrayList<Program>();
    private List<String> listOfExaminerDepartmentPrograms = new ArrayList<String>();
    private List<String> listOfProgramsAcademicLevels = new ArrayList<String>();
    private List<AcademicLevel> listOfAcademicLevels = new ArrayList<AcademicLevel>();
    private DataModel<Course> coursesDataModel = new ListDataModel<Course>();
    private DataModel<Course> selectedCoursesDataModel = new ListDataModel<Course>();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private SearchInputs searchInputs = new SearchInputs();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private List<Course> listOfFreezCourses = new ArrayList<Course>();
    private UserSessionData userSessionData = new UserSessionData();

    public LecturerCoursesController() {
        pageCommonInputs.showDataRecordsDisplay();
        loadAllSchoolCourses();
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);
    }

    public void quickFindCourseCodeListener() {

        try {
            listOfCourses = new ArrayList<Course>();
            coursesDataModel = new ListDataModel<Course>(listOfCourses);
            listOfCourses = TisEjbSource.getCrudInstance().courseFindByAttribute("courseCode", selectedItemHelper.getSelectedCourseCode(), "STRING", false);
            if (listOfCourses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND);
            } else {
                coursesDataModel = new ListDataModel<Course>(listOfCourses);
            }

        } catch (Exception e) {
        }
    }

    public void quickFindCourseCreditHoursListener() {

        try {
            listOfCourses = new ArrayList<Course>();
            coursesDataModel = new ListDataModel<Course>(listOfCourses);
            listOfCourses = TisEjbSource.getBasicQuariesInstance().getAllCoursesWithSpecificCreditHours(selectedItemHelper.getSelectedCourseCreditHours());
            if (listOfCourses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND);
            } else {
                coursesDataModel = new ListDataModel<Course>(listOfCourses);
            }

        } catch (Exception e) {
        }
    }

    public void quickFindCourseInitialsListener() {

        try {
            listOfCourses = new ArrayList<Course>();
            coursesDataModel = new ListDataModel<Course>(listOfCourses);
            listOfCourses = TisEjbSource.getCrudInstance().courseFindByAttribute("courseInitials", selectedItemHelper.getSelectedCourseInitials(), "STRING", false);
            if (listOfCourses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND);
            } else {
                coursesDataModel = new ListDataModel<Course>(listOfCourses);
            }

        } catch (Exception e) {
        }
    }

    public void programSelectedCourses() {
        System.out.println("INSIDE PROG COURSES");
        try {
            listOfLecturerTeachingCourses = new ArrayList<LecturerTeachingCourse>();
            lecturerTeachingCoursessDataModel = new ListDataModel<LecturerTeachingCourse>(listOfLecturerTeachingCourses);
//            listOfLecturerTeachingCourses = TisEjbSource.getBasicQuariesInstance().loadLecturerTeachingCourse(selectedItemHelper.getSelectedProgram(), selectedItemHelper.getSelectedAcademicLevel());
            if (listOfLecturerTeachingCourses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND);
            } else {
                lecturerTeachingCoursessDataModel = new ListDataModel<LecturerTeachingCourse>(listOfLecturerTeachingCourses);
            }
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void searchCourse() {

        System.out.println("THE SEARCH PARATERS " + searchInputs.getSearchParameter() + searchInputs.getSearchValue());
        if (searchInputs.getSearchParameter() == null) {
            JSFMessages.warnMessage("Please Enter Search Criteria");
        } else if (searchInputs.getSearchValue() == null) {
            JSFMessages.warnMessage("Please Enter Search Text");
        } else if (searchInputs.getSearchParameter() == null && searchInputs.getSearchValue() == null) {
            JSFMessages.errorMessage("Please Enter Search Parameters");
        } else {
            try {
                listOfCourses = new ArrayList<Course>();
                listOfCourses = TisEjbSource.getCrudInstance().courseFindByAttribute(searchInputs.getSearchParameter(), searchInputs.getSearchValue(), "STRING", false);
                if (listOfCourses.isEmpty()) {
                    JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND);
                } else {
                    coursesDataModel = new ListDataModel<Course>(listOfCourses);
                }
            } catch (Exception e) {
                appLogger(e);
                System.out.println("THE ERROR IS " + e.getMessage());
            }

        }

    }

    public void addSelectedCourse() {

        System.out.println("INSIDE COURSESE ");
        course = coursesDataModel.getRowData();
        try {
            Course c = new Course();
            course = coursesDataModel.getRowData();
            c = TisEjbSource.getCrudInstance().courseFind(course.getCourseId());
            boolean duplicate = false;
            for (Course eachCourse : listOfSelectedCourses) {
                if (eachCourse.getCourseName().equalsIgnoreCase(c.getCourseName())) {
                    duplicate = true;
                }
            }
            if (duplicate) {
                JSFMessages.warnMessage(course.getCourseName() + " Has Already Been Selected");

            } else {
//                lecturerTeachingCourses.setCourse(course);
//                listOfLecturerTeachingCourses.add(lecturerTeachingCourses);
//                lecturerTeachingCoursessDataModel = new ListDataModel<LecturerTeachingCourse>(listOfLecturerTeachingCourses);
//                lecturerTeachingCourses = new LecturerTeachingCourse();
//                course = new Course();

                listOfSelectedCourses.add(c);
                selectedCoursesDataModel = new ListDataModel<Course>(listOfSelectedCourses);
                course = new Course();
            }


        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void addBulkCourses() {

        try {
            String duplicateCourse = "";
            boolean duplicate = false;
            for (Course eachCourse : listOfCourses) {

                if (eachCourse.getSelected()) {

                    duplicate = false;
                    for (Course eachSelectedCourse : listOfSelectedCourses) {
                        if (eachSelectedCourse.getCourseName().equalsIgnoreCase(eachCourse.getCourseName())) {
                            duplicateCourse += eachCourse.getCourseName() + " ";
                            duplicate = true;
                        }
                    }

                    if (duplicate) {
                    } else {
                        listOfSelectedCourses.add(eachCourse);
                        selectedCoursesDataModel = new ListDataModel<Course>(listOfSelectedCourses);
                        course = new Course();

                    }

                }

            }

            for (Course eachCourse : listOfCourses) {
                eachCourse.setSelected(false);
            }
            if (duplicate) {
                JSFMessages.warnMessage(duplicateCourse + " Have Already Been Selected");
            }
        } catch (Exception e) {
        }

    }

    public void removeCourse() {

        try {
            course = selectedCoursesDataModel.getRowData();
            listOfSelectedCourses.remove(course);
            selectedCoursesDataModel = new ListDataModel<Course>(listOfSelectedCourses);
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public void newDataButton() {
        pageCommonInputs.showDataInputsDisplay();
        pageCommonInputs.setShowEditButtons(true);

    }

    @Override
    public String saveButton() {
        try {
            int counter = 0;
            if (listOfSelectedCourses.isEmpty()) {
                JSFMessages.errorMessage("Please Select Program Courses");
            } else {
                counter = processSelectedLecturerTeachingCourse(counter);
                lecturerTeachingCourses.setCreatedDate(new Date());
                lecturerTeachingCourses.setLecturerCourseId(JSFIdGenerator.generateRandomId());
                lecturerTeachingCourses.setAcademicYear(TisEjbSource.getBasicQuariesInstance().getCurrentAcademicYear());

                pageCommonInputs.setCheckIfSaved(TisEjbSource.getCrudInstance().lecturerTeachingCourseCreate(lecturerTeachingCourses));

                if (pageCommonInputs.getCheckIfSaved() != null) {
                    JSFMessages.infoMessage(counter + " program course(s) " + JSFMessages.SUCCESS_SAVE);
                    resetButton();
                } else {
                    JSFMessages.errorMessage(JSFMessages.UNSUCCESS_SAVE + course.getCourseName());
                }
            }

        } catch (Exception e) {
            appLogger(e);
            System.out.println("UNABLE TO SAVE " + e.getMessage());
        }
        return "";
    }

    @Override
    public void resetButton() {
        try {
            course = new Course();
            searchInputs = new SearchInputs();
            selectedItemHelper = new SelectedItemHelper();
            listOfSelectedCourses = new ArrayList<Course>();
            selectedCoursesDataModel = new ListDataModel<Course>();
            loadAllSchoolCourses();

        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public String updateButton() {
        try {

            int counter = 0;
            counter = processSelectedLecturerTeachingCourse(counter);
//            lecturerTeachingCourses.setEditedBy(JSFUtility.getSessionByUserName(JSFUtility.SESSION_NAME));
            lecturerTeachingCourses.setEditedDate(new Date());
            pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().lecturerTeachingCourseUpdate(lecturerTeachingCourses));
            if (pageCommonInputs.checkIfUpdated) {
                JSFMessages.infoMessage("Program Courses Updated ");
                resetButton();
                viewAllButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_UPDATE + lecturerTeachingCourses.getProgram().getProgramName());
            }
        } catch (Exception e) {
            System.out.println("UNABLE TO UPDATE " + e.getMessage());
        }
        return "";
    }

    @Override
    public void deleteButton() {
        try {
            System.out.println("THE PROGRAM COURSE IS " + lecturerTeachingCourses.getLecturerCourseId());
            pageCommonInputs.setCheckIfDeleted(TisEjbSource.getCrudInstance().lecturerTeachingCourseDelete(lecturerTeachingCourses, false));
            if (pageCommonInputs.isCheckIfDeleted()) {
                JSFMessages.infoMessage(lecturerTeachingCourses.getCourses() + JSFMessages.SUCCESS_DELETE);
                resetButton();
                viewAllButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_DELETE + lecturerTeachingCourses.getCourses());
            }
        } catch (Exception e) {
            appLogger(e);

        }
    }

    public void selectAllListener() {
        if (pageCommonInputs.isCheckAllData()) {
            for (Course eachUniversity : listOfCourses) {
                eachUniversity.setSelected(true);
            }
        } else if (!pageCommonInputs.isCheckAllData()) {
            for (Course eachUniversity : listOfCourses) {
                eachUniversity.setSelected(false);
            }
        }
        coursesDataModel = new ListDataModel<Course>(listOfCourses);
    }

    @Override
    public void cancelButton() {
        try {
            pageCommonInputs.showDataRecordsDisplay();
            course = new Course();
            listOfSelectedCourses = new ArrayList<Course>();
            selectedCoursesDataModel = new ListDataModel<Course>();
            selectedItemHelper = new SelectedItemHelper();
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public void resetDataTable() {
        try {
            searchInputs = new SearchInputs();
            listOfLecturerTeachingCourses = new ArrayList<LecturerTeachingCourse>();
            lecturerTeachingCoursessDataModel = new ListDataModel<LecturerTeachingCourse>(listOfLecturerTeachingCourses);
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public void selectButton() {
        try {
            listOfSelectedCourses = new ArrayList<Course>();
            selectedCoursesDataModel = new ListDataModel<Course>(listOfCourses);
            lecturerTeachingCourses = lecturerTeachingCoursessDataModel.getRowData();
            selectedItemHelper.setSelectedStaff(lecturerTeachingCourses.getLecturer().getStaffId());
            listOfSelectedCourses = BinderSplitter.coursesSplitterList(lecturerTeachingCourses.getCourses());
            selectedCoursesDataModel = new ListDataModel<Course>(listOfSelectedCourses);
            pageCommonInputs.showDataInputsDisplay();
            pageCommonInputs.setShowEditButtons(false);
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public String searchButton() {
        try {

            listOfLecturerTeachingCourses = new ArrayList<LecturerTeachingCourse>();
            lecturerTeachingCoursessDataModel = new ListDataModel<LecturerTeachingCourse>(listOfLecturerTeachingCourses);

            listOfLecturerTeachingCourses = TisEjbSource.getCrudInstance().lecturerTeachingCourseFindByAttribute(searchInputs.getSearchParameter(), searchInputs.getSearchValue(), "STRING", false);
            System.out.println("THE SIZE OF SEARCH DATA IS " + listOfCourses.size());
            if (listOfLecturerTeachingCourses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " Lecturer's Teaching Courses");
            } else {
                lecturerTeachingCoursessDataModel = new ListDataModel<LecturerTeachingCourse>(listOfLecturerTeachingCourses);
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public String viewAllButton() {
        try {
//                    System.out.println("USER CURRENT AY IS "+userSessionData.getCurrentAcademicYear().getAcademicYearId());

            listOfCourses = new ArrayList<Course>();
            listOfLecturerTeachingCourses = new ArrayList<LecturerTeachingCourse>();
            lecturerTeachingCoursessDataModel = new ListDataModel<LecturerTeachingCourse>(listOfLecturerTeachingCourses);
            listOfLecturerTeachingCourses = TisEjbSource.getCrudInstance().lecturerTeachingCourseGetAll(false);
            System.out.println("THE LIST OF ALL LECTURERS COURSES IS " + listOfLecturerTeachingCourses.size());
            if (listOfLecturerTeachingCourses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " Programs' Courses");
                return "";
            }
            lecturerTeachingCoursessDataModel = new ListDataModel<LecturerTeachingCourse>(listOfLecturerTeachingCourses);
//            listOfFreezCourses.add(listOfCourses.get(0));
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }
    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public PageCommonInputs getPageCommonInputs() {
        return pageCommonInputs;
    }

    public SelectedItemHelper getSelectedItemHelper() {
        return selectedItemHelper;
    }

    public void setSelectedItemHelper(SelectedItemHelper selectedItemHelper) {
        this.selectedItemHelper = selectedItemHelper;
    }

    public void setPageCommonInputs(PageCommonInputs pageCommonInputs) {
        this.pageCommonInputs = pageCommonInputs;
    }

    public SearchInputs getSearchInputs() {
        return searchInputs;
    }

    public void setSearchInputs(SearchInputs searchControls) {
        this.searchInputs = searchControls;
    }

    public List<Course> getListOfCourses() {
        return listOfCourses;
    }

    public List<Course> getListOfFreezCourses() {
        return listOfFreezCourses;
    }

    public void setListOfFreezCourses(List<Course> listOfFreezCourses) {
        this.listOfFreezCourses = listOfFreezCourses;
    }

    public void setListOfCourses(List<Course> listOfCourses) {
        this.listOfCourses = listOfCourses;
    }

    public LecturerTeachingCourse getLecturerTeachingCourse() {
        return lecturerTeachingCourses;
    }

    public void setLecturerTeachingCourse(LecturerTeachingCourse lecturerTeachingCourses) {
        this.lecturerTeachingCourses = lecturerTeachingCourses;
    }

    public DataModel<LecturerTeachingCourse> getLecturerTeachingCoursesDataModel() {
        return lecturerTeachingCoursessDataModel;
    }

    public void setLecturerTeachingCoursesDataModel(DataModel<LecturerTeachingCourse> lecturerTeachingCoursessDataModel) {
        this.lecturerTeachingCoursessDataModel = lecturerTeachingCoursessDataModel;
    }

    public List<LecturerTeachingCourse> getListOfLecturerTeachingCourses() {
        return listOfLecturerTeachingCourses;
    }

    public void setListOfLecturerTeachingCourses(List<LecturerTeachingCourse> listOfLecturerTeachingCourses) {
        this.listOfLecturerTeachingCourses = listOfLecturerTeachingCourses;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public AcademicLevel getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(AcademicLevel academicLevel) {
        this.academicLevel = academicLevel;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Program> getListOfPrograms() {
        return listOfPrograms;
    }

    public void setListOfPrograms(List<Program> listOfPrograms) {
        this.listOfPrograms = listOfPrograms;
    }

    public List<String> getListOfExaminerDepartmentPrograms() {
        return listOfExaminerDepartmentPrograms;
    }

    public void setListOfExaminerDepartmentPrograms(List<String> listOfExaminerDepartmentPrograms) {
        this.listOfExaminerDepartmentPrograms = listOfExaminerDepartmentPrograms;
    }

    public List<String> getListOfProgramsAcademicLevels() {
        return listOfProgramsAcademicLevels;
    }

    public void setListOfProgramsAcademicLevels(List<String> listOfProgramsAcademicLevels) {
        this.listOfProgramsAcademicLevels = listOfProgramsAcademicLevels;
    }

    public List<AcademicLevel> getListOfAcademicLevels() {
        return listOfAcademicLevels;
    }

    public void setListOfAcademicLevels(List<AcademicLevel> listOfAcademicLevels) {
        this.listOfAcademicLevels = listOfAcademicLevels;
    }

    public List<Course> getListOfSelectedCourses() {
        return listOfSelectedCourses;
    }

    public void setListOfSelectedCourses(List<Course> listOfSelectedCourses) {
        this.listOfSelectedCourses = listOfSelectedCourses;
    }

    public DataModel<Course> getCoursesDataModel() {
        return coursesDataModel;
    }

    public LecturerTeachingCourse getLecturerTeachingCourses() {
        return lecturerTeachingCourses;
    }

    public void setLecturerTeachingCourses(LecturerTeachingCourse lecturerTeachingCourses) {
        this.lecturerTeachingCourses = lecturerTeachingCourses;
    }

    public DataModel<LecturerTeachingCourse> getLecturerTeachingCoursessDataModel() {
        return lecturerTeachingCoursessDataModel;
    }

    public void setLecturerTeachingCoursessDataModel(DataModel<LecturerTeachingCourse> lecturerTeachingCoursessDataModel) {
        this.lecturerTeachingCoursessDataModel = lecturerTeachingCoursessDataModel;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public DataModel<Course> getSelectedCoursesDataModel() {
        return selectedCoursesDataModel;
    }

    public void setSelectedCoursesDataModel(DataModel<Course> selectedCoursesDataModel) {
        this.selectedCoursesDataModel = selectedCoursesDataModel;
    }

    public void setCoursesDataModel(DataModel<Course> coursesDataModel) {
        this.coursesDataModel = coursesDataModel;
    }
    //</editor-fold>

    void appLogger(Exception e) {
        e.printStackTrace();
        Logger.getLogger(LecturerCoursesController.class.getName()).log(Level.SEVERE, null, e.getMessage());
    }

    private void loadAllSchoolCourses() {
        listOfCourses = TisEjbSource.getCrudInstance().courseGetAll(false);
        coursesDataModel = new ListDataModel<Course>(listOfCourses);
    }

    int processSelectedLecturerTeachingCourse(int counter) {
        String selectedCourses = "";
//                userAccount = JSFUtility.getSessionValueByUserAccount(JSFUtility.SESSION_NAME);
        for (Course eachSelectedCourse : listOfSelectedCourses) {
            if (counter == 0) {
                selectedCourses += eachSelectedCourse.getCourseInitials() + " " + eachSelectedCourse.getCourseCode();
            } else {
                selectedCourses += "/" + eachSelectedCourse.getCourseInitials() + " " + eachSelectedCourse.getCourseCode();
            }
            counter++;
        }
        System.out.println("THE SELECTED COURSES ARE " + selectedCourses);
        lecturerTeachingCourses.setAcademicLevel(selectedItemHelper.getSelectedAcademicLevel());
        lecturerTeachingCourses.setLecturer(TisEjbSource.getCrudInstance().staffFind(selectedItemHelper.getSelectedStaff()));
        lecturerTeachingCourses.setCourses(selectedCourses);
        return counter;
    }
}
