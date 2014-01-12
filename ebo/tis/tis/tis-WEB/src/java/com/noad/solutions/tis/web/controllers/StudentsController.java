/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.JSFIdGenerator;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.intefaces.utils.CommonMethodsInterface;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.string.contants.utils.Gender;
import com.noad.solutions.common.string.contants.utils.MaritalStatus;
import com.noad.solutions.common.string.contants.utils.Title;
import com.noad.solutions.tis.ejb.entities.AcademicLevel;
import com.noad.solutions.tis.ejb.entities.AcademicYear;
import com.noad.solutions.tis.ejb.entities.Department;
import com.noad.solutions.tis.ejb.entities.Guardiance;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import java.io.File;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.imageio.stream.FileImageOutputStream;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Daud
 */
@Named(value = "studentsController")
@SessionScoped
public class StudentsController implements Serializable, CommonMethodsInterface {

    private Student student = new Student();
    private Guardiance guardiance = new Guardiance();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private SelectItem[] programSelectItems;
    private List<Student> listOfStudents;
    private List<Guardiance> listOfGuardiances = new ArrayList<Guardiance>();
    private List<Student> listOfFreezStudents = new ArrayList<Student>();
    private DataModel<Student> studentsDataModel;
    private DataModel<Guardiance> guardiancesDataModel = new ListDataModel<Guardiance>();
    private boolean autoGenerateIndexNumber;
    private boolean autoGenerateStudentId;
    private SearchInputs searchInputs = new SearchInputs();
    private String clientCode;
    private String computerName;
    private String serverRoootImagePath = System.getProperty("com.sun.aas.instanceRoot")
            + File.separator + "docroot"
            + File.separator + "tisres" + File.separator;
    private String clientPicturePath = serverRoootImagePath + "students_pics" + File.separator;
    private String clientPictureURL = "http://" + computerName + ":8080/tisres/" + "students_pics" + "/";
    private String studentImageCode;
    private final String JPEG = ".jpg";
    private String studentPicture = "";

    public StudentsController() {
        pageCommonInputs.showDataRecordsDisplay();

    }

    public void uploadMemberPicture(FileUploadEvent fileUploadEvent) {

        byte[] data = fileUploadEvent.getFile().getContents();
        System.out.println("THE SERVER ROOT IS " + serverRoootImagePath);
        FileImageOutputStream imageOutput;
        try {
            studentImageCode = UUID.randomUUID().toString().substring(1, 9);
            imageOutput = new FileImageOutputStream(new File(clientPicturePath + studentImageCode + ".jpg"));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
//            imageOutput.flush();
            clientPictureURL = "http://" + "localhost" + ":8080/tisres/" + "students_pics" + "/" + studentImageCode + ".jpg";
            System.out.println("THE CLIENT PICTURE URL IS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + clientPictureURL);
            System.out.println("THE LENGTH OF THE PATH IS " + clientPicturePath.length());
            System.out.println("THE CLIENT PICTURE PATH IS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + clientPicturePath);
            studentPicture = "";
            studentPicture += pageCommonInputs.getStudentPicturePath() + studentImageCode + JPEG;
            System.out.println("THE STUDENT PICTURE IS " + studentPicture);
        } catch (Exception e) {
            appLogger(e);
        }
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

        try {
            Program program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());
            Department department = TisEjbSource.getCrudInstance().departmentFind(selectedItemHelper.getSelectedDepartment());
            AcademicLevel academicLevel = TisEjbSource.getCrudInstance().academicLevelFind(selectedItemHelper.getSelectedAcademicLevel());
            AcademicYear academicYear = TisEjbSource.getCrudInstance().academicYearFind(selectedItemHelper.getSelectedAcademicYear());

            student.setStudentId(JSFIdGenerator.generateStudentNumber(student.getDateOfAdmission()));
            student.setStudentIndexNumber(JSFIdGenerator.generateIndexNumber(program, student.getDateOfAdmission()));
            student.setDepartment(department);
            student.setAdmissionYear(academicYear.getAcademicYear());
            student.setCurrentLevel(academicLevel);
            student.setProgram(program);
            student.setStudentPicture(studentPicture + JPEG);
            guardiance.setGuardianceId(JSFIdGenerator.generateRandomId());
            titleGiver();

            System.out.println("THE STUDENT ID IS " + student.getStudentId());
            System.out.println("THE STUDENT INDEX IS " + student.getStudentIndexNumber());

            String saved = TisEjbSource.getCrudInstance().guardianceCreate(guardiance);
            Guardiance g = new Guardiance();
            g = TisEjbSource.getCrudInstance().guardianceFind(guardiance.getGuardianceId());
            student.setGuardiance(g);
            pageCommonInputs.setCheckIfSaved(TisEjbSource.getCrudInstance().studentCreate(student));
            if (pageCommonInputs.getCheckIfSaved() == null && saved == null) {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_SAVE + " Of " + student.getSurname());
                return "";
            } else {
                JSFMessages.infoMessage(student.getSurname() + JSFMessages.SUCCESS_SAVE);
                resetButton();
//                viewAllButton();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";


    }

    @Override
    public String viewAllButton() {
        try {
            listOfStudents = new ArrayList<Student>();
            studentsDataModel = new ListDataModel<Student>(listOfStudents);
            listOfStudents = TisEjbSource.getCrudInstance().studentGetAll(false);
            System.out.println("THE NUMBER OF STUDENTS IS " + listOfStudents.size());
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
    public void resetDataTable() {
        try {
            listOfStudents = new ArrayList<Student>();
            studentsDataModel = new ListDataModel<Student>(listOfStudents);
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
            studentPicture = "";
            studentPicture += pageCommonInputs.getStudentPicturePath() + student.getStudentPicture();
            guardiance = student.getGuardiance();
            selectedItemHelper.setSelectedDepartment(student.getDepartment().getDepartmentId());
            selectedItemHelper.setSelectedProgram(student.getProgram().getProgramId());
            selectedItemHelper.setSelectedAcademicLevel(student.getCurrentLevel().getAcademicLevelId());
            selectedItemHelper.setSelectedAcademicYear(student.getAdmissionYear());
            departmentProgramsListener();

        } catch (Exception e) {
            appLogger(e);
        }
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
    public String updateButton() {
        try {
            Program program = TisEjbSource.getCrudInstance().programFind(selectedItemHelper.getSelectedProgram());
            Department department = TisEjbSource.getCrudInstance().departmentFind(selectedItemHelper.getSelectedDepartment());
            AcademicLevel academicLevel = TisEjbSource.getCrudInstance().academicLevelFind(selectedItemHelper.getSelectedAcademicLevel());

            titleGiver();
            student.setDepartment(department);
            student.setAdmissionYear(selectedItemHelper.getSelectedAcademicYear());
            student.setCurrentLevel(academicLevel);
            student.setProgram(program);
            student.setStudentPicture(studentImageCode + JPEG);
            student.setGuardiance(guardiance);
            student.setDepartment(TisEjbSource.getCrudInstance().departmentFind(selectedItemHelper.getSelectedDepartment()));
            pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().studentUpdate(student));
            pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().guardianceUpdate(guardiance));
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
    public void resetButton() {
        student = new Student();
        guardiance = new Guardiance();
        pageCommonInputs = new PageCommonInputs();
        selectedItemHelper = new SelectedItemHelper();
        autoGenerateIndexNumber = false;
        autoGenerateStudentId = false;
        pageCommonInputs.showDataInputsDisplay();
        studentImageCode = null;
        studentPicture = null;
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

    private void appLogger(Exception e) {
        e.printStackTrace();
        Logger.getLogger(StudentsController.class.getName()).log(Level.SEVERE, e.getMessage(), e);

    }
    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Guardiance getGuardiance() {
        return guardiance;
    }

    public void setGuardiance(Guardiance guardiance) {
        this.guardiance = guardiance;
    }

    public SelectedItemHelper getSelectedItemHelper() {
        return selectedItemHelper;
    }

    public void setSelectedItemHelper(SelectedItemHelper selectedItemHelper) {
        this.selectedItemHelper = selectedItemHelper;
    }

    public SelectItem[] getProgramSelectItems() {
        return programSelectItems;
    }

    public void setProgramSelectItems(SelectItem[] programSelectItems) {
        this.programSelectItems = programSelectItems;
    }

    public PageCommonInputs getPageCommonInputs() {
        return pageCommonInputs;
    }

    public void setPageCommonInputs(PageCommonInputs pageCommonInputs) {
        this.pageCommonInputs = pageCommonInputs;
    }
    //</editor-fold>

    public List<Student> getListOfStudents() {
        return listOfStudents;
    }

    public void setListOfStudents(List<Student> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }

    public List<Guardiance> getListOfGuardiances() {
        return listOfGuardiances;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public String getServerRoootImagePath() {
        return serverRoootImagePath;
    }

    public void setServerRoootImagePath(String serverRoootImagePath) {
        this.serverRoootImagePath = serverRoootImagePath;
    }

    public String getClientPicturePath() {
        return clientPicturePath;
    }

    public void setClientPicturePath(String clientPicturePath) {
        this.clientPicturePath = clientPicturePath;
    }

    public String getClientPictureURL() {
        return clientPictureURL;
    }

    public void setClientPictureURL(String clientPictureURL) {
        this.clientPictureURL = clientPictureURL;
    }

    public String getStudentImageCode() {
        return studentImageCode;
    }

    public void setStudentImageCode(String studentImageCode) {
        this.studentImageCode = studentImageCode;
    }

    public String getStudentPicture() {
        return studentPicture;
    }

    public void setStudentPicture(String studentPicture) {
        this.studentPicture = studentPicture;
    }

    public void setListOfGuardiances(List<Guardiance> listOfGuardiances) {
        this.listOfGuardiances = listOfGuardiances;
    }

    public List<Student> getListOfFreezStudents() {
        return listOfFreezStudents;
    }

    public void setListOfFreezStudents(List<Student> listOfFreezStudents) {
        this.listOfFreezStudents = listOfFreezStudents;
    }

    public DataModel<Student> getStudentsDataModel() {
        return studentsDataModel;
    }

    public void setStudentsDataModel(DataModel<Student> studentsDataModel) {
        this.studentsDataModel = studentsDataModel;
    }

    public DataModel<Guardiance> getGuardiancesDataModel() {
        return guardiancesDataModel;
    }

    public void setGuardiancesDataModel(DataModel<Guardiance> guardiancesDataModel) {
        this.guardiancesDataModel = guardiancesDataModel;
    }

    public boolean isAutoGenerateIndexNumber() {
        return autoGenerateIndexNumber;
    }

    public void setAutoGenerateIndexNumber(boolean autoGenerateIndexNumber) {
        this.autoGenerateIndexNumber = autoGenerateIndexNumber;
    }

    public boolean isAutoGenerateStudentId() {
        return autoGenerateStudentId;
    }

    public void setAutoGenerateStudentId(boolean autoGenerateStudentId) {
        this.autoGenerateStudentId = autoGenerateStudentId;
    }

    public SearchInputs getSearchInputs() {
        return searchInputs;
    }

    public void setSearchInputs(SearchInputs searchInputs) {
        this.searchInputs = searchInputs;
    }
}
