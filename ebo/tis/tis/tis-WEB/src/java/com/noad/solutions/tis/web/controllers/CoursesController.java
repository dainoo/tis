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
import java.io.Serializable;
import java.util.ArrayList;
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
public class CoursesController extends CssStyles implements Serializable, CommonMethodsInterface {

    private Course course = new Course();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private SearchInputs searchInputs = new SearchInputs();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private List<Course> listOfCourses;
    private List<Course> listOfFreezCourses = new ArrayList<Course>();
    private DataModel<Course> coursesDataModel;
    private UserSessionData userSessionData = new UserSessionData();

    public CoursesController() {
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);
        pageCommonInputs.showDataRecordsDisplay();
    }

    @Override
    public void newDataButton() {
        pageCommonInputs.showDataInputsDisplay();
        pageCommonInputs.setShowEditButtons(true);
        System.out.println("THE CURRENT YEAR IS " + userSessionData.getCurrentAcademicYear().getAcademicYearId());

    }

    @Override
    public String saveButton() {
        try {
            if (TisEjbSource.getValidationInstance().checkDuplicateCourse(course)) {
                JSFMessages.warnMessage(course.getCourseName() + JSFMessages.DUPLICATE);
                return "";
            }
            course.setCourseId(JSFIdGenerator.generateId("course", "DP"));
            pageCommonInputs.setCheckIfSaved(TisEjbSource.getCrudInstance().courseCreate(course));
            if (pageCommonInputs.getCheckIfSaved() == null) {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_SAVE + course.getCourseName());
            } else {
                JSFMessages.infoMessage(course.getCourseName() + JSFMessages.SUCCESS_SAVE);
                resetButton();
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public void resetButton() {
        try {
            course = new Course();
            searchInputs = new SearchInputs();
            selectedItemHelper = new SelectedItemHelper();

        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public String updateButton() {
        try {
            pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().courseUpdate(course));
            if (pageCommonInputs.isCheckIfUpdated()) {
                JSFMessages.infoMessage(course.getCourseName() + JSFMessages.SUCCESS_UPDATE);
                resetButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_UPDATE + course.getCourseName());
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public void deleteButton() {
        try {
            pageCommonInputs.setCheckIfDeleted(TisEjbSource.getCrudInstance().courseDelete(course, false));
            if (pageCommonInputs.isCheckIfDeleted()) {
                JSFMessages.infoMessage(course.getCourseName() + JSFMessages.SUCCESS_DELETE);
                resetButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_DELETE + course.getCourseName());
            }
        } catch (Exception e) {
            appLogger(e);

        }
    }

    @Override
    public void cancelButton() {
        try {
            pageCommonInputs.showDataRecordsDisplay();
            course = new Course();
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public void resetDataTable() {
        try {
            listOfCourses = new ArrayList<Course>();
            coursesDataModel = new ListDataModel<Course>();
            searchInputs = new SearchInputs();
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public void selectButton() {
        try {
            pageCommonInputs.showDataInputsDisplay();
            pageCommonInputs.setShowEditButtons(false);
            course = coursesDataModel.getRowData();
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public String searchButton() {
        try {
            listOfCourses = new ArrayList<Course>();
            coursesDataModel = new ListDataModel<Course>();
            listOfCourses = TisEjbSource.getCrudInstance().courseFindByAttribute(searchInputs.getSearchParameter(), searchInputs.getSearchValue(), "STRING", false);
            if (listOfCourses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND);
                return "";
            }
            coursesDataModel = new ListDataModel<Course>(listOfCourses);
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public String viewAllButton() {
        try {
            listOfCourses = new ArrayList<Course>();
            coursesDataModel = new ListDataModel<Course>(listOfCourses);
            listOfCourses = TisEjbSource.getCrudInstance().courseGetAll(false);
            if (listOfCourses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " Courses");
                return "";
            }
            coursesDataModel = new ListDataModel<Course>(listOfCourses);
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

    public UserSessionData getUserSessionData() {
        return userSessionData;
    }

    public void setUserSessionData(UserSessionData userSessionData) {
        this.userSessionData = userSessionData;
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

    public DataModel<Course> getCoursesDataModel() {
        return coursesDataModel;
    }

    public void setCoursesDataModel(DataModel<Course> coursesDataModel) {
        this.coursesDataModel = coursesDataModel;
    }
    //</editor-fold>

    void appLogger(Exception e) {
//        e.printStackTrace();
        Logger.getLogger(CoursesController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
    }
}
