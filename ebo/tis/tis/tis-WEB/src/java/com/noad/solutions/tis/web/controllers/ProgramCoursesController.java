/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

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
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.ProgramCourse;
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
@ManagedBean
@SessionScoped
public class ProgramCoursesController extends CssStyles implements Serializable, CommonMethodsInterface {

    private ProgramCourse programCourse = new ProgramCourse();
    private DataModel<ProgramCourse> programCoursesDataModel = new ListDataModel<ProgramCourse>();
    private List<ProgramCourse> listOfProgramCourses = new ArrayList<ProgramCourse>();
    private Program program = new Program();
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

    public ProgramCoursesController() {
        pageCommonInputs.showDataRecordsDisplay();
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);
        loadAllSchoolCourses();
    }

    public void academicLevelListener() {
//        try {
//            listOfProgramsAcademicLevels = TisEjbSource.getCustomCaucInstance().getProgramAcademicLevels(caucSelectItemsController.getSelectedProgram());
//            System.out.println("THE SIZE OF ACADEMIC LEVELS " + listOfProgramsAcademicLevels.size());
//            academicLevelSelectItems = new SelectItem[listOfProgramsAcademicLevels.size() + 1];
//            academicLevelSelectItems[0] = new SelectItem("", "-- Please Select --");
//            int counter = 1;
//            for (String eachAcademicLevel : listOfProgramsAcademicLevels) {
//                academicLevel = TisEjbSource.getCrudInstance().academicLevelFind(eachAcademicLevel);
//                academicLevelSelectItems[counter] = new SelectItem(academicLevel.getAcademicLevelId(), academicLevel.getAcademicLevel());
//                counter++;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
            if (true) {
            } else {
            }
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
            listOfProgramCourses = new ArrayList<ProgramCourse>();
            programCoursesDataModel = new ListDataModel<ProgramCourse>(listOfProgramCourses);
            listOfProgramCourses = TisEjbSource.getBasicQuariesInstance().loadProgramCourses(selectedItemHelper.getSelectedProgram(), selectedItemHelper.getSelectedAcademicLevel(), selectedItemHelper.getSelectedSemester());
            if (listOfProgramCourses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND);
            } else {
                programCoursesDataModel = new ListDataModel<ProgramCourse>(listOfProgramCourses);
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
//                programCourse.setCourse(course);
//                listOfProgramCourses.add(programCourse);
//                programCoursesDataModel = new ListDataModel<ProgramCourse>(listOfProgramCourses);
//                programCourse = new ProgramCourse();
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
        System.out.println("INSIDE ADD BULK COURSES");
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
//                        programCourse.setCourse(eachCourse);
//                        listOfProgramCourses.add(programCourse);
//                        programCoursesDataModel = new ListDataModel<ProgramCourse>(listOfProgramCourses);
//                        programCourse = new ProgramCourse();

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

//        programCourse = programCoursesDataModel.getRowData();
//        course = selectedCoursesDataModel.getRowData();
//        System.out.println("THE SELECTED COURSE IS " + course.getCourseName());
//        System.out.println("INSIDE REMOVE COURSE METHOD");
        try {

//            listOfProgramCourses.remove(programCourse);
//            programCoursesDataModel = new ListDataModel<ProgramCourse>(listOfProgramCourses);
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
                List<ProgramCourse> listoFPCs = TisEjbSource.getBasicQuariesInstance().getProgramCourseAndSemester(selectedItemHelper.getSelectedProgram(), selectedItemHelper.getSelectedSemester());
                if (listoFPCs.isEmpty()) {
                    counter = processSelectedProgramCourse(counter);
                    programCourse.setAcademicLevel(TisEjbSource.getCrudInstance().academicLevelFind(selectedItemHelper.getSelectedAcademicLevel()));
                    programCourse.setCreatedDate(new Date());
                    programCourse.setProgramCourseId(JSFIdGenerator.generateId("program_course", "PC"));
                    programCourse.setCreatedBy(userSessionData.getStaff().getUserAccount().getUsername());
                    pageCommonInputs.setCheckIfSaved(TisEjbSource.getCrudInstance().programCourseCreate(programCourse));

                    if (pageCommonInputs.getCheckIfSaved() != null) {
                        JSFMessages.infoMessage(counter + " program course(s) " + JSFMessages.SUCCESS_SAVE);
                        resetButton();
                    } else {
                        JSFMessages.errorMessage(JSFMessages.UNSUCCESS_SAVE + course.getCourseName());
                    }

                } else {
                    JSFMessages.errorMessage("Program Courses "+JSFMessages.DUPLICATE);
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
            counter = processSelectedProgramCourse(counter);
            programCourse.setEditedBy(JSFUtility.getSessionByUserName(JSFUtility.SESSION_NAME));
            programCourse.setEditedDate(new Date());
            programCourse.setCreatedBy(userSessionData.getStaff().getUserAccount().getUsername());
            pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().programCourseUpdate(programCourse));
            if (pageCommonInputs.checkIfUpdated) {
                JSFMessages.infoMessage("Program Courses Updated ");
                resetButton();
                viewAllButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_UPDATE + programCourse.getProgram().getProgramName());
            }
        } catch (Exception e) {
            System.out.println("UNABLE TO UPDATE " + e.getMessage());
        }
        return "";
    }

    @Override
    public void deleteButton() {
        try {
            System.out.println("THE PROGRAM COURSE IS " + programCourse.getProgramCourseId());
            pageCommonInputs.setCheckIfDeleted(TisEjbSource.getCrudInstance().programCourseDelete(programCourse, false));
            if (pageCommonInputs.isCheckIfDeleted()) {
                JSFMessages.infoMessage(programCourse.getCourses() + JSFMessages.SUCCESS_DELETE);
                resetButton();
                viewAllButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_DELETE + programCourse.getCourses());
            }
        } catch (Exception e) {
            appLogger(e);

        }
    }

    public void selectAllListener() {

        System.out.println("INSIDE SELECT ALL");
        System.out.println("INSIDE SELECT ALL VALUE " + pageCommonInputs.checkAllData);
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
            listOfProgramCourses = new ArrayList<ProgramCourse>();
            programCoursesDataModel = new ListDataModel<ProgramCourse>(listOfProgramCourses);
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public void selectButton() {
        try {
            listOfSelectedCourses = new ArrayList<Course>();
            selectedCoursesDataModel = new ListDataModel<Course>(listOfCourses);
            programCourse = programCoursesDataModel.getRowData();
            selectedItemHelper.setSelectedProgram(programCourse.getProgram().getProgramId());
            selectedItemHelper.setSelectedAcademicLevel(programCourse.getAcademicLevel().getAcademicLevel());
            selectedItemHelper.setSelectedSemester(programCourse.getSemester());
            String[] selected = programCourse.getCourses().split("/");
            for (String string : selected) {
                String[] initialCode = string.split(" ");
//                    String initial = initialCode[0];
//                    String code = initialCode[1];
                System.out.println("SEARCH BY " + initialCode[0] + "  " + initialCode[1]);
                course = TisEjbSource.getBasicQuariesInstance().getCourseByInitialsAndCode(initialCode[0], initialCode[1]);
//                    System.out.println("THE COURSE IS "+course.getCourseName());
                listOfSelectedCourses.add(course);
            }
            System.out.println("THE SIZE OF SELECTED PROGRAM COURSSSSSSS " + listOfSelectedCourses.size());
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

            listOfProgramCourses = new ArrayList<ProgramCourse>();
            programCoursesDataModel = new ListDataModel<ProgramCourse>(listOfProgramCourses);

            listOfProgramCourses = TisEjbSource.getCrudInstance().programCourseFindByAttribute(searchInputs.getSearchParameter(), searchInputs.getSearchValue(), "STRING", false);
            System.out.println("THE SIZE OF SEARCH DATA IS " + listOfCourses.size());
            if (listOfProgramCourses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND);
            } else {
                programCoursesDataModel = new ListDataModel<ProgramCourse>(listOfProgramCourses);
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public String viewAllButton() {
        try {
            listOfCourses = new ArrayList<Course>();
            listOfProgramCourses = new ArrayList<ProgramCourse>();
            programCoursesDataModel = new ListDataModel<ProgramCourse>(listOfProgramCourses);
            listOfProgramCourses = TisEjbSource.getCrudInstance().programCourseGetAll(false);
            if (listOfProgramCourses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " Programs' Courses");
                return "";
            }
            programCoursesDataModel = new ListDataModel<ProgramCourse>(listOfProgramCourses);
//            listOfFreezCourses.add(listOfCourses.get(0));
        } catch (Exception e) {
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

    public ProgramCourse getProgramCourse() {
        return programCourse;
    }

    public void setProgramCourse(ProgramCourse programCourse) {
        this.programCourse = programCourse;
    }

    public DataModel<ProgramCourse> getProgramCoursesDataModel() {
        return programCoursesDataModel;
    }

    public void setProgramCoursesDataModel(DataModel<ProgramCourse> programCoursesDataModel) {
        this.programCoursesDataModel = programCoursesDataModel;
    }

    public List<ProgramCourse> getListOfProgramCourses() {
        return listOfProgramCourses;
    }

    public void setListOfProgramCourses(List<ProgramCourse> listOfProgramCourses) {
        this.listOfProgramCourses = listOfProgramCourses;
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
        Logger.getLogger(ProgramCoursesController.class.getName()).log(Level.SEVERE, null, e.getMessage());
    }

    public void loadAllSchoolCourses() {
        listOfCourses = new ArrayList<Course>();
        coursesDataModel = new ListDataModel<Course>(listOfCourses);
        listOfCourses = TisEjbSource.getCrudInstance().courseGetAll(false);
        coursesDataModel = new ListDataModel<Course>(listOfCourses);
    }

    int processSelectedProgramCourse(int counter) {
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
//        programCourse.setAcademicLevel(selectedItemHelper.getSelectedAcademicLevel());
        programCourse.setProgram(TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram()));
        programCourse.setSemester(selectedItemHelper.getSelectedSemester());
        programCourse.setCourses(selectedCourses);
        programCourse.setAcademicLevel(academicLevel);
        return counter;
    }
}
