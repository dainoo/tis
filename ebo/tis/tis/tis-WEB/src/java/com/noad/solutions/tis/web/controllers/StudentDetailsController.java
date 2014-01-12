package com.noad.solutions.tis.web.controllers;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.noad.solutions.common.classes.utils.CssStyles;
import com.noad.solutions.common.intefaces.utils.CommonMethodsInterface;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.JSFIdGenerator;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.string.contants.utils.Gender;
import com.noad.solutions.common.string.contants.utils.MaritalStatus;
import com.noad.solutions.common.string.contants.utils.Title;
import com.noad.solutions.tis.ejb.entities.Department;
import com.noad.solutions.tis.ejb.entities.Guardiance;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.SchoolSetup;
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
import javax.faces.model.SelectItem;

/**
 *
 * @author Secretary
 */
@ManagedBean
@SessionScoped
public class StudentDetailsController extends CssStyles implements Serializable, CommonMethodsInterface {

    private Student student = new Student();
    private Guardiance guardiance = new Guardiance();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private SearchInputs searchInputs = new SearchInputs();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private List<Student> listOfStudents;
    private List<Guardiance> listOfGuardiances = new ArrayList<Guardiance>();
    private List<Student> listOfFreezStudents = new ArrayList<Student>();
    private DataModel<Student> studentsDataModel;
    private DataModel<Guardiance> guardiancesDataModel = new ListDataModel<Guardiance>();
    private String qualification = null;
    private Date dateAquired = null;
    private boolean autoGenerateStudentId = false;
    private boolean autoGenerateIndexNumber = false;
    private SelectItem[] programSelectItems = null;
    private SchoolSetup schoolSetup;

    public StudentDetailsController() {
        schoolSetup = TisEjbSource.getCrudInstance().schoolSetupFind("404040");
        pageCommonInputs.showDataRecordsDisplay();

    }

    public void departmentProgramsListener() {
        try {
            List<Program> listOfPrograms = TisEjbSource.getCrudInstance().programFindByAttribute("department.departmentId", selectedItemHelper.getSelectedDepartment(), "STRING", false);
            programSelectItems = new SelectItem[listOfPrograms.size() + 1];
            programSelectItems[0] = new SelectItem("", "PLEASE SELECT PROGRAM UNDER " + listOfPrograms.get(0).getDepartment().getDepartmentName());
            int counter = 1;
            for (Program eachProgram : listOfPrograms) {
                programSelectItems[counter] = new SelectItem(eachProgram.getProgramId(), eachProgram.getProgramName());
                counter++;
            }
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
        System.out.println("INSIDE SAVE BUTTON");
        try {
            Program program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());
            Department department = TisEjbSource.getCrudInstance().departmentFind(selectedItemHelper.getSelectedDepartment());
            student.setCurrentLevel(TisEjbSource.getCrudInstance().academicLevelFind(selectedItemHelper.getSelectedAcademicLevel()));
            student.setStudentId(JSFIdGenerator.generateId("student", "st"));
            student.setStudentIndexNumber(JSFIdGenerator.generateIndexNumber(program.getProgramName(), student.getDateOfAdmission(), program.getPrgramCode()));

//            if (autoGenerateStudentId) {
//            }
//            if (autoGenerateIndexNumber) {
//            }
//            if (!autoGenerateStudentId && student.getStudentId() == null) {
//                JSFMessages.errorMessage("Please Enter Student ID Or Check Auto Generate Student ID ");
//                return "";
//            }
//            if (!autoGenerateIndexNumber && student.getStudentIndexNumber() == null) {
//                JSFMessages.errorMessage("Please Enter Index Number  Or Check Auto Generate Index Number ");
//                return "";
//            }
            if (TisEjbSource.getValidationInstance().checkDuplicateStudent(student)) {
                JSFMessages.warnMessage("Student With ID  " + student.getStudentId() + " And Index Number" + student.getStudentIndexNumber() + JSFMessages.DUPLICATE);
                return "";
            }
            titleGiver();
            student.setDepartment(department);
            student.setAdmissionYear(TisEjbSource.getBasicQuariesInstance().getCurrentAcademicYear().getAcademicYear());
            student.setProgram(program);
//            student.setStudentId(JSFIdGenerator.generateStudentNumber());
            student.setDepartment(TisEjbSource.getCrudInstance().departmentFind(selectedItemHelper.getSelectedDepartment()));
            guardiance.setGuardianceId(JSFIdGenerator.generateRandomId());
            student.setGuardiance(guardiance);
            String saved = TisEjbSource.getCrudInstance().guardianceCreate(guardiance);
            pageCommonInputs.setCheckIfSaved(TisEjbSource.getCrudInstance().studentCreate(student));
            System.out.println("THE SAVED ID -GUARDIANCE >>>>>>> " + guardiance.getGuardianceId());
            System.out.println("THE SAVED ID -STUDENT >>>>>>> " + student.getStudentId());
            if (pageCommonInputs.getCheckIfSaved() == null) {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_SAVE + student.getSurname() + " " + student.getOtherNames());
            } else {
                JSFMessages.infoMessage(student.getSurname() + " " + student.getOtherNames() + JSFMessages.SUCCESS_SAVE);
                resetButton();
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    public void createStaff() {
        System.out.println("INSIDE CREATE METHOD");
        Program program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());
        Department department = TisEjbSource.getCrudInstance().departmentFind(selectedItemHelper.getSelectedDepartment());
        student.setCurrentLevel(TisEjbSource.getCrudInstance().academicLevelFind(selectedItemHelper.getSelectedAcademicLevel()));
        student.setStudentId(JSFIdGenerator.generateId("student", "st"));
//        student.setStudentIndexNumber(JSFIdGenerator.generateIndexNumber(program.getProgramName(), student.getDateOfAdmission(), program.getPrgramCode()));
//        titleGiver();
        student.setDepartment(department);
        student.setAdmissionYear(TisEjbSource.getBasicQuariesInstance().getCurrentAcademicYear().getAcademicYear());
        student.setProgram(program);
//            student.setStudentId(JSFIdGenerator.generateStudentNumber());
        student.setDepartment(TisEjbSource.getCrudInstance().departmentFind(selectedItemHelper.getSelectedDepartment()));
        guardiance.setGuardianceId(JSFIdGenerator.generateRandomId());
        student.setGuardiance(guardiance);
        String saved = TisEjbSource.getCrudInstance().guardianceCreate(guardiance);
        pageCommonInputs.setCheckIfSaved(TisEjbSource.getCrudInstance().studentCreate(student));
        System.out.println("THE SAVED ID -GUARDIANCE >>>>>>> " + guardiance.getGuardianceId());
        System.out.println("THE SAVED ID -STUDENT >>>>>>> " + student.getStudentId());
        if (pageCommonInputs.getCheckIfSaved() == null) {
            JSFMessages.errorMessage(JSFMessages.UNSUCCESS_SAVE + student.getSurname() + " " + student.getOtherNames());
        } else {
            JSFMessages.infoMessage(student.getSurname() + " " + student.getOtherNames() + JSFMessages.SUCCESS_SAVE);
            resetButton();
        }

    }

    @Override
    public void resetButton() {
        try {
            student = new Student();
            guardiance = new Guardiance();
            searchInputs = new SearchInputs();
            selectedItemHelper = new SelectedItemHelper();
            autoGenerateIndexNumber = false;
            autoGenerateStudentId = false;


        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public String updateButton() {
        try {
            titleGiver();
            student.setGuardiance(guardiance);
            student.setDepartment(TisEjbSource.getCrudInstance().departmentFind(selectedItemHelper.getSelectedDepartment()));
            pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().studentUpdate(student));
            if (pageCommonInputs.isCheckIfUpdated()) {
                JSFMessages.infoMessage(student.getSurname() + " " + student.getOtherNames() + JSFMessages.SUCCESS_UPDATE);
                resetButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_UPDATE + student.getSurname() + " " + student.getOtherNames());
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public void deleteButton() {
        try {
            pageCommonInputs.setCheckIfDeleted(TisEjbSource.getCrudInstance().studentDelete(student, false));
            if (pageCommonInputs.isCheckIfDeleted()) {
                JSFMessages.infoMessage(student.getSurname() + " " + student.getOtherNames() + JSFMessages.SUCCESS_DELETE);
                resetButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_DELETE + student.getSurname() + " " + student.getOtherNames());
            }
        } catch (Exception e) {
            appLogger(e);

        }
    }

    @Override
    public void cancelButton() {
        try {
            pageCommonInputs.showDataRecordsDisplay();
            student = new Student();
            guardiance = new Guardiance();
            selectedItemHelper = new SelectedItemHelper();
            listOfGuardiances = new ArrayList<Guardiance>();
            guardiancesDataModel = new ListDataModel<Guardiance>(listOfGuardiances);
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public void resetDataTable() {
        try {
            listOfStudents = new ArrayList<Student>();
            studentsDataModel = new ListDataModel<Student>();
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
            student = studentsDataModel.getRowData();
            guardiance = student.getGuardiance();
            selectedItemHelper.setSelectedDepartment(student.getDepartment().getDepartmentId());
            selectedItemHelper.setSelectedProgram(student.getProgram().getProgramId());
            selectedItemHelper.setSelectedAcademicLevel(student.getCurrentLevel().getAcademicLevelId());

        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public String searchButton() {
        try {
            listOfStudents = new ArrayList<Student>();
            studentsDataModel = new ListDataModel<Student>();
            listOfStudents = TisEjbSource.getCrudInstance().studentFindByAttribute(searchInputs.getSearchParameter(), searchInputs.getSearchValue(), "STRING", false);
            if (listOfStudents.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND);
                return "";
            }
            studentsDataModel = new ListDataModel<Student>(listOfStudents);
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public String viewAllButton() {
        try {
            listOfStudents = new ArrayList<Student>();
            studentsDataModel = new ListDataModel<Student>(listOfStudents);
            listOfStudents = TisEjbSource.getCrudInstance().studentGetAll(false);
            if (listOfStudents.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " Students");
                return "";
            }
            studentsDataModel = new ListDataModel<Student>(listOfStudents);
//            listOfFreezStudents.add(listOfStudents.get(0));
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    void appLogger(Exception e) {
        e.printStackTrace();
        Logger.getLogger(StudentDetailsController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
    }

    void titleGiver() {
        if (student.getGender().equalsIgnoreCase(Gender.MALE)) {
            student.setTitle(Title.MR);
        } else if (student.getGender().equals(Gender.FEMALE) && student.getMaritalStatus().equalsIgnoreCase(MaritalStatus.MARRIED)) {
            student.setTitle(Title.MISS);
        } else if (student.getGender().equals(Gender.FEMALE) && !student.getMaritalStatus().equalsIgnoreCase(MaritalStatus.MARRIED)) {
            student.setTitle(Title.MS);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public Student getStudent() {
        return student;
    }

    public SelectItem[] getProgramSelectItems() {
        return programSelectItems;
    }

    public SchoolSetup getSchoolSetup() {
        return schoolSetup;
    }

    public void setSchoolSetup(SchoolSetup schoolSetup) {
        this.schoolSetup = schoolSetup;
    }

    public void setProgramSelectItems(SelectItem[] programSelectItems) {
        this.programSelectItems = programSelectItems;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public PageCommonInputs getPageCommonInputs() {
        return pageCommonInputs;
    }

    public boolean isAutoGenerateStudentId() {
        return autoGenerateStudentId;
    }

    public void setAutoGenerateStudentId(boolean autoGenerateStudentId) {
        this.autoGenerateStudentId = autoGenerateStudentId;
    }

    public boolean isAutoGenerateIndexNumber() {
        return autoGenerateIndexNumber;
    }

    public void setAutoGenerateIndexNumber(boolean autoGenerateIndexNumber) {
        this.autoGenerateIndexNumber = autoGenerateIndexNumber;
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

    public Guardiance getGuardiance() {
        return guardiance;
    }

    public void setGuardiance(Guardiance guardiance) {
        this.guardiance = guardiance;
    }

    public List<Guardiance> getListOfGuardiances() {
        return listOfGuardiances;
    }

    public void setListOfGuardiances(List<Guardiance> listOfGuardiances) {
        this.listOfGuardiances = listOfGuardiances;
    }

    public DataModel<Guardiance> getGuardiancesDataModel() {
        return guardiancesDataModel;
    }

    public void setGuardiancesDataModel(DataModel<Guardiance> guardiancesDataModel) {
        this.guardiancesDataModel = guardiancesDataModel;
    }

    public void setSearchInputs(SearchInputs searchControls) {
        this.searchInputs = searchControls;
    }

    public List<Student> getListOfStudents() {
        return listOfStudents;
    }

    public List<Student> getListOfFreezStudents() {
        return listOfFreezStudents;
    }

    public void setListOfFreezStudents(List<Student> listOfFreezStudents) {
        this.listOfFreezStudents = listOfFreezStudents;
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

    public Date getDateAquired() {
        return dateAquired;
    }

    public void setDateAquired(Date dateAquired) {
        this.dateAquired = dateAquired;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    //</editor-fold>
}
