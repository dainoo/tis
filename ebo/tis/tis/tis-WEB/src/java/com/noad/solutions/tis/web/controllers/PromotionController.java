/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.common.date.utils.DateTimeFunctions;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.common.string.contants.utils.AcademicStatus;
import com.noad.solutions.tis.ejb.entities.AcademicLevel;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.ejb.entities.StudentLevel;
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

/**
 *
 * @author Daud
 */
@Named(value = "promotionController")
@SessionScoped
public class PromotionController implements Serializable {

    private UserSessionData userSessionData = new UserSessionData();
    private StudentLevel studentLevel = new StudentLevel();
    private List<StudentLevel> listOfStudentLevels = null;
    private List<Student> listOfStudents = null;
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private Program program = new Program();
    private AcademicLevel academicLevel = new AcademicLevel();
    private DataModel<StudentLevel> studentLevelsDataModel;
    private DataModel<Student> studentsDataModel;
    private SearchInputs searchInputs = new SearchInputs();
    private boolean completedStudents = false;

    public PromotionController() {
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);

    }

    public void initialStudentLevelLevel() {

        List<Student> list = TisEjbSource.getCrudInstance().studentGetAll(false);
        StudentLevel sl = new StudentLevel();
        for (Student each : list) {
            sl.setStudentLevelId(each.getStudentId() + "/" + each.getStudentIndexNumber() + "/" + userSessionData.getCurrentAcademicYear().getAcademicYear());
            sl.setAcademicYear(userSessionData.getCurrentAcademicYear().getAcademicYear());
            sl.setStudent(each);
            sl.setAcademicLevel("level 100");
            TisEjbSource.getCrudInstance().studentLevelCreate(sl);
            sl = new StudentLevel();
        }
    }

    public void massSearch() {
        try {
            program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());
            pageCommonInputs.checkAllData = false;
            academicLevel = TisEjbSource.getCrudInstance().academicLevelFind(selectedItemHelper.getSelectedAcademicLevel());
            listOfStudents = new ArrayList<Student>();
            studentsDataModel = new ListDataModel<Student>(listOfStudents);
            List<Student> studentsInCurrentSemester = TisEjbSource.getBasicQuariesInstance().getRegisteredStudents(selectedItemHelper.getSelectedProgram(), selectedItemHelper.getSelectedAcademicLevel());
            if (studentsInCurrentSemester.isEmpty()) {
                JSFMessages.errorMessage("No Students Found For " + program.getProgramName() + "\t" + academicLevel.getAcademicLevel());
                return;
            }
            for (Student student : studentsInCurrentSemester) {
                listOfStudents.add(TisEjbSource.getCrudInstance().studentFind(student.getStudentId()));
            }
            studentsDataModel = new ListDataModel<Student>(listOfStudents);
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void singleSearch() {
        try {
            pageCommonInputs.checkAllData = false;
            listOfStudents = new ArrayList<Student>();
            studentsDataModel = new ListDataModel<Student>();
            Student student = null;
            if (searchInputs.getSearchParameter().equalsIgnoreCase("Student Id")) {
                student = TisEjbSource.getCrudInstance().studentFind(searchInputs.getSearchValue());
            } else if (searchInputs.getSearchParameter().equalsIgnoreCase("Index Number")) {
                student = TisEjbSource.getBasicQuariesInstance().findStudentByIndexNumber(searchInputs.getSearchValue());
            }
//            listOfStudents = TisEjbSource.getCrudInstance().studentFindByAttribute(searchInputs.getSearchParameter(), searchInputs.getSearchValue(), "STRING", false);

            if (student == null) {
                JSFMessages.errorMessage("No Student  Found With " + searchInputs.getSearchParameter() + " " + searchInputs.getSearchValue());
            } else {
                listOfStudents.add(student);
                studentsDataModel = new ListDataModel<Student>(listOfStudents);
            }

        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void selectAllListener() {
        try {
            System.out.println("THE ALL IS "+pageCommonInputs.isCheckAllData());
            if (listOfStudents.isEmpty()) {
                JSFMessages.warnMessage("No StudentLevels to Select");
            } else {
                if (pageCommonInputs.isCheckAllData()) {
                    for (Student eachStd : listOfStudents) {
//                        eachStd.setSelected(true);
                        eachStd.setSelected(true);
                    }
                } else if (!pageCommonInputs.isCheckAllData()){
                    for (Student eachStd : listOfStudents) {
//                        eachStd.setSelected(false);
                        eachStd.setSelected(false);
                        
                    }

                }
            }
            studentsDataModel = new ListDataModel<Student>(listOfStudents);
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void effectPromotionOrDemotion() {
        System.out.println("INSIDE PROMOTION");

        try {
            int counter = 0;
            if (listOfStudents.isEmpty()) {
                JSFMessages.errorMessage("Please Select Students");
                return;
            }
            for (Student eachStudent : listOfStudents) {
                System.out.println("THE SELECTED STUDENT IS " + eachStudent.getSelected());
                if (eachStudent.getSelected()) {
                    counter++;
                    if (completedStudents) {
                        eachStudent.setAcademicStatus(AcademicStatus.COMPLETED);
                        eachStudent.setCompletionYear(DateTimeFunctions.getCurrentYear());
                    } else {
                        if (selectedItemHelper.getSelectedAcademicLevel().equalsIgnoreCase("Level 100")) {
                            eachStudent.setAcademicStatus(AcademicStatus.FRESHER);
                        } else {
                            eachStudent.setAcademicStatus(AcademicStatus.CONTINUING);
                        }
                        eachStudent.setCompletionYear(null);
                        eachStudent.setCurrentLevel(TisEjbSource.getCrudInstance().academicLevelFind(selectedItemHelper.getSelectedAcademicLevel()));
                    }
                    TisEjbSource.getCrudInstance().studentUpdate(eachStudent);
                }
            }
            if (counter == 0) {
                JSFMessages.errorMessage("No Promotion/Demotion Effected");
            } else {
                if (completedStudents) {
                    JSFMessages.infoMessage(counter + " Students Completed In " + DateTimeFunctions.getCurrentYear());
                } else {
                    JSFMessages.infoMessage(counter + " Students Promoted/Demoted To " + selectedItemHelper.getSelectedAcademicLevel());
                }
                resetButton();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetButton() {
        try {
            studentLevel = new StudentLevel();
            listOfStudents = new ArrayList<Student>();
            studentsDataModel = new ListDataModel<Student>(listOfStudents);
            searchInputs = new SearchInputs();
            completedStudents = false;
            selectedItemHelper = new SelectedItemHelper();
            pageCommonInputs = new PageCommonInputs();
        } catch (Exception e) {
            appLogger(e);
        }
    }

    void appLogger(Exception e) {
        Logger.getLogger(PromotionController.class.getName()).log(Level.SEVERE, e.getCause().getLocalizedMessage(), e);
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public UserSessionData getUserSessionData() {
        return userSessionData;
    }

    public void setUserSessionData(UserSessionData userSessionData) {
        this.userSessionData = userSessionData;
    }

    public StudentLevel getStudentLevel() {
        return studentLevel;
    }

    public void setStudentLevel(StudentLevel studentLevel) {
        this.studentLevel = studentLevel;
    }

    public boolean isCompletedStudents() {
        return completedStudents;
    }

    public void setCompletedStudents(boolean completedStudents) {
        this.completedStudents = completedStudents;
    }

    public List<StudentLevel> getListOfStudentLevels() {
        return listOfStudentLevels;
    }

    public void setListOfStudentLevels(List<StudentLevel> listOfStudentLevels) {
        this.listOfStudentLevels = listOfStudentLevels;
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

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public AcademicLevel getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(AcademicLevel academicLevel) {
        this.academicLevel = academicLevel;
    }

    public DataModel<StudentLevel> getStudentLevelsDataModel() {
        return studentLevelsDataModel;
    }

    public void setStudentLevelsDataModel(DataModel<StudentLevel> studentLevelsDataModel) {
        this.studentLevelsDataModel = studentLevelsDataModel;
    }

    public SearchInputs getSearchInputs() {
        return searchInputs;
    }

    public List<Student> getListOfStudents() {
        return listOfStudents;
    }

    public void setListOfStudents(List<Student> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }

    public DataModel<Student> getStudentsDataModel() {
        return studentsDataModel;
    }

    public void setStudentsDataModel(DataModel<Student> studentsDataModel) {
        this.studentsDataModel = studentsDataModel;
    }

    public void setSearchInputs(SearchInputs searchInputs) {
        this.searchInputs = searchInputs;
    }
    //</editor-fold>
}
