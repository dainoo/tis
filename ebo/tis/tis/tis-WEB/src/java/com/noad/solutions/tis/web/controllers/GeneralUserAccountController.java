/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.BinderSplitter;
import com.noad.solutions.common.classes.utils.JSFIdGenerator;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.common.intefaces.utils.CommonMethodsInterface;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.common.string.contants.utils.AppMenus;
import com.noad.solutions.tis.ejb.entities.Ebo;
import com.noad.solutions.tis.ejb.entities.Staff;
import com.noad.solutions.tis.ejb.entities.UserAccount;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.web.pagesrights.AppMainMenus;
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
 * @author Daud
 */
@ManagedBean
@SessionScoped
public class GeneralUserAccountController extends UserSessionData implements Serializable, CommonMethodsInterface {

    private Ebo ebo = new Ebo();
    private UserAccount userAccount = new UserAccount();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private UserSessionData userSessionData = new UserSessionData();
    private SearchInputs searchInputs = new SearchInputs();
    private Staff staff = new Staff();
    private List<Staff> listOfStaffsMembers = new ArrayList<Staff>();
    private List<UserAccount> listOfUserAccounts = new ArrayList<UserAccount>();
    private DataModel<Staff> staffMembersDataModel;
    private DataModel<UserAccount> userAccountsDataModel;
    private List<Ebo> listOfSettings = new ArrayList<Ebo>();
    private List<Ebo> listOfStaffs = new ArrayList<Ebo>();
    private List<Ebo> listOfStudents = new ArrayList<Ebo>();
    private List<Ebo> listOfExaminations = new ArrayList<Ebo>();
    private List<Ebo> listOfFinancies = new ArrayList<Ebo>();
    private List<Ebo> listOfDataSecurities = new ArrayList<Ebo>();
    private List<Ebo> listOfAllAppPages = new ArrayList<Ebo>();
    private DataModel<Ebo> settingsDataModel = new ListDataModel<Ebo>();
    private DataModel<Ebo> staffDataModel = new ListDataModel<Ebo>();
    private DataModel<Ebo> studentsDataModel = new ListDataModel<Ebo>();
    private DataModel<Ebo> examinationsDataModel = new ListDataModel<Ebo>();
    private DataModel<Ebo> financiesDataModel = new ListDataModel<Ebo>();
    private DataModel<Ebo> dataSecuritiesDataModel = new ListDataModel<Ebo>();
    private DataModel<Ebo> allPagesDataModel = new ListDataModel<Ebo>();
    private boolean allSettings = false;
    private boolean allStaffs = false;
    private boolean allStudents = false;
    private boolean allFinacies = false;
    private boolean allExams = false;
    private boolean allDataSecurities = false;

    public GeneralUserAccountController() {
        try {
            pageCommonInputs.showDataRecordsDisplay();
            preLoadPages();
            userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);

        } catch (Exception e) {
        }
    }

    private void preLoadPages() {
//        listOfSettings = new ArrayList<Ebo>();
//        listOfStaffs = new ArrayList<Ebo>();
//        listOfStudents = new ArrayList<Ebo>();
//        listOfExaminations = new ArrayList<Ebo>();
//        listOfDataSecurities = new ArrayList<Ebo>();
//        listOfFinancies = new ArrayList<Ebo>();

        listOfDataSecurities.clear();
        listOfExaminations.clear();
        listOfFinancies.clear();
        listOfSettings.clear();
        listOfStaffs.clear();
        listOfStudents.clear();

        settingsDataModel = new ListDataModel<Ebo>(listOfSettings);
        staffDataModel = new ListDataModel<Ebo>(listOfStaffs);
        studentsDataModel = new ListDataModel<Ebo>(listOfStudents);
        examinationsDataModel = new ListDataModel<Ebo>(listOfExaminations);
        financiesDataModel = new ListDataModel<Ebo>(listOfFinancies);
        dataSecuritiesDataModel = new ListDataModel<Ebo>(listOfDataSecurities);

        listOfSettings = TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.SETTINGS, "STRING");
        listOfStaffs = TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.STAFFS, "STRING");
        listOfStudents = TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.STUDENTS, "STRING");
        listOfExaminations = TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.EXAMINATIONS, "STRING");
        listOfFinancies = TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.FINANCIES, "STRING");
        listOfDataSecurities = TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.DATA_SECURITY, "STRING");


        System.out.println("THE NUMBER OF SETTTINGS " + listOfSettings.size());
        System.out.println("THE NUMBER OF STAFFS " + listOfStaffs.size());
        System.out.println("THE NUMBER OF STUDENTS " + listOfStudents.size());
        System.out.println("THE NUMBER OF EXAMINATOINS " + listOfExaminations.size());
//        if (!"NOAD".equals(userSessionData.getStaff().getSurname())) {
//            Ebo e = new Ebo();
//            e = TisEjbSource.getCrudInstance().eboFind("USA");
//            listOfStaffs.remove(e);
//        }

        settingsDataModel = new ListDataModel<Ebo>(listOfSettings);
        staffDataModel = new ListDataModel<Ebo>(listOfStaffs);
        studentsDataModel = new ListDataModel<Ebo>(listOfStudents);
        examinationsDataModel = new ListDataModel<Ebo>(listOfExaminations);
        financiesDataModel = new ListDataModel<Ebo>(listOfFinancies);
        dataSecuritiesDataModel = new ListDataModel<Ebo>(listOfDataSecurities);
//        settingsDataModel.setWrappedData(listOfSettings);

    }

    public void allSettingsListener() {

        System.out.println("INSIDE SETTINGS LISTENER");
        System.out.println("IS ALL SELECTED " + isAllSettings());
        if (allSettings) {
            System.out.println("INSIDE ALL SETTINGS");
            for (Ebo eachSettings : getListOfSettings()) {
                eachSettings.setSelected(true);
                eachSettings.setWriteRight(true);
                eachSettings.setReadRight(true);
                eachSettings.setAccessRight(true);
                eachSettings.setPrintRight(true);
            }
        } else {
            listOfSettings = TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.SETTINGS, "STRING");
        }
        settingsDataModel = new ListDataModel<Ebo>(listOfSettings);
    }

    public void allStaffsListener() {
        if (isAllStaffs()) {
            for (Ebo eachStaff : getListOfStaffs()) {
                eachStaff.setSelected(true);
                eachStaff.setWriteRight(true);
                eachStaff.setReadRight(true);
                eachStaff.setAccessRight(true);
                eachStaff.setPrintRight(true);

            }
        } else {
            listOfStaffs = TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.STAFFS, "STRING");
        }
        staffDataModel = new ListDataModel<Ebo>(listOfStaffs);
    }

    public void allStudentsListener() {
        if (isAllStudents()) {
            for (Ebo eachStudent : getListOfStudents()) {
                eachStudent.setSelected(true);
                eachStudent.setWriteRight(true);
                eachStudent.setReadRight(true);
                eachStudent.setAccessRight(true);
                eachStudent.setPrintRight(true);


            }
        } else {
            listOfStudents = TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.STUDENTS, "STRING");
        }
        setStudentsDataModel(new ListDataModel<Ebo>(getListOfStudents()));
    }

    public void allExaminationsListener() {
        if (isAllExams()) {
            for (Ebo eachEXams : getListOfExaminations()) {
                eachEXams.setSelected(true);
                eachEXams.setWriteRight(true);
                eachEXams.setReadRight(true);
                eachEXams.setAccessRight(true);
                eachEXams.setPrintRight(true);

            }
        } else {
            listOfExaminations = TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.EXAMINATIONS, "STRING");

        }
        setExaminationsDataModel(new ListDataModel<Ebo>(getListOfExaminations()));
    }

    public void allFinanciesListener() {
        if (isAllFinacies()) {
            for (Ebo eachReport : getListOfFinancies()) {
                eachReport.setSelected(true);
                eachReport.setWriteRight(true);
                eachReport.setReadRight(true);
                eachReport.setPrintRight(true);
                eachReport.setAccessRight(true);

            }
        } else {
            listOfFinancies = TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.FINANCIES, "STRING");
        }
        setFinanciesDataModel(new ListDataModel<Ebo>(getListOfFinancies()));
    }

    public void allDataSecuritiesListener() {
        if (isAllDataSecurities()) {
            for (Ebo eachDataSecurity : getListOfDataSecurities()) {
                eachDataSecurity.setSelected(true);
                eachDataSecurity.setWriteRight(true);
                eachDataSecurity.setReadRight(true);
                eachDataSecurity.setPrintRight(true);
            }
        } else {
            listOfDataSecurities = TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.DATA_SECURITY, "STRING");

        }
        setDataSecuritiesDataModel(new ListDataModel<Ebo>(getListOfDataSecurities()));
    }

    @Override
    public void newDataButton() {
    }

    @Override
    public String saveButton() {
        System.out.println("INSIDE SAVE BUTTON");
        try {
            listOfAllAppPages.addAll(listOfSettings);
            listOfAllAppPages.addAll(listOfStaffs);
            listOfAllAppPages.addAll(listOfStudents);
            System.out.println("THE SIZE OF ");
            String userPages = BinderSplitter.userPagesBinder(listOfAllAppPages);
            if (userPages.equalsIgnoreCase("<NO_USER_PAGES>")) {
                JSFMessages.errorMessage("Please Select User Access Pages");
            } else {

                userAccount.setUserPages(userPages);
                userAccount.setUsername(JSFIdGenerator.generateUsername(staff.getSurname(), staff.getOtherNames(), staff.getDepartment().getDepartmentName()));
                userAccount.setPassword(JSFIdGenerator.generatePassword());
                userAccount.setUserAccountId(staff.getStaffId());
                userAccount.setStatus(pageCommonInputs.getStatus());
                userAccount.setUserRole(pageCommonInputs.getUserRole());

//                userAccount.setCreatedBy(JSFUtility.getUserSession(JSFUtility.SESSION_NAME).getStaff().getUserAccount().getUsername());
                userAccount.setCreatedDate(new Date());
                pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().userAccountUpdate(userAccount));
                if (pageCommonInputs.isCheckIfUpdated()) {
                    JSFMessages.infoMessage("Account Created For " + staff.getSurname() + " " + staff.getOtherNames());
                    JSFMessages.infoMessage("PLEASE USE USERNAME AND PASSWORD BELOW TO CREATE YOUR OWN USERNAME AND PASSWORD ");
                    JSFMessages.infoMessage("Your Temporal USERNAME : " + userAccount.getUsername() + "   PASSWORD : " + userAccount.getPassword());
                    resetButton();
                    viewAllButton();
                    pageCommonInputs.showDataRecordsDisplay();
                } else {
                    JSFMessages.errorMessage("Failed To Create User Account");
                }
            }
        } catch (Exception e) {
            appLogger(e);
        }

        return "";


    }

    @Override
    public void resetButton() {
        pageCommonInputs.setUsername(null);
        pageCommonInputs.setPassword(null);
        pageCommonInputs.setUserRole(null);
        pageCommonInputs.setStatus(null);
        setUserAccount(new UserAccount());
        preLoadPages();
        setAllStudents(false);
        setAllSettings(false);
        setAllStaffs(false);
        setAllFinacies(false);
        setAllExams(false);
        setAllDataSecurities(false);
    }

    @Override
    public String updateButton() {
        try {
            listOfAllAppPages.addAll(listOfSettings);
            listOfAllAppPages.addAll(listOfStaffs);
            listOfAllAppPages.addAll(listOfStudents);
            System.out.println("THE SIZE OF ");
            String userPage = null;
            userPage = BinderSplitter.userPagesBinder(listOfAllAppPages);
            if (userPage.equalsIgnoreCase("<NO_USER_PAGES>")) {
                JSFMessages.errorMessage("Please Select User Access Pages");
            } else {

                userAccount.setUserPages(userPage);
                userAccount.setStatus(pageCommonInputs.getStatus());
                userAccount.setUserRole(pageCommonInputs.getUserRole());
//                userAccount.setEditedBy(JSFUtility.getUserSession(JSFUtility.SESSION_NAME).getStaff().getUserAccount().getUsername());
                userAccount.setEditedDate(new Date());

                pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().userAccountUpdate(userAccount));
                if (pageCommonInputs.isCheckIfUpdated()) {
                    JSFMessages.infoMessage("User Account Updated For " + staff.getSurname() + " " + staff.getOtherNames());
                    resetButton();
                    viewAllButton();
                    pageCommonInputs.showDataRecordsDisplay();

                } else {
                    JSFMessages.errorMessage("Failed To Upadte User Account Of " + staff.getSurname() + " " + staff.getOtherNames());
                }
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public void deleteButton() {
        try {
            userAccount.setUserPages(null);
            userAccount.setStatus(null);
            userAccount.setUserRole(null);
            userAccount.setPassword(null);

//                userAccount.setEditedBy(JSFUtility.getUserSession(JSFUtility.SESSION_NAME).getStaff().getUserAccount().getUsername());
            pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().userAccountUpdate(userAccount));
            if (pageCommonInputs.isCheckIfUpdated()) {
                JSFMessages.infoMessage("User Account Deleted For " + staff.getSurname() + " " + staff.getOtherNames());
                resetButton();
                viewAllButton();
                pageCommonInputs.showDataRecordsDisplay();

            } else {
                JSFMessages.errorMessage("Failed To Delete User Account Of " + staff.getSurname() + " " + staff.getOtherNames());
            }
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public void cancelButton() {
        preLoadPages();
        userAccount = new UserAccount();
        staff = new Staff();
        pageCommonInputs.showDataRecordsDisplay();
        pageCommonInputs.setShowEditButtons(true);
    }

    @Override
    public void resetDataTable() {
        try {
            searchInputs = new SearchInputs();
            listOfUserAccounts = new ArrayList<UserAccount>();
            userAccountsDataModel = new ListDataModel<UserAccount>(listOfUserAccounts);
        } catch (Exception e) {
        }
    }

    @Override
    public void selectButton() {

        try {
            staff = userAccountsDataModel.getRowData().getStaff();
            System.out.println("THE STAFF IS "+staff.getSurname());
            userAccount = userAccountsDataModel.getRowData();
            pageCommonInputs.showDataInputsDisplay();
            pageCommonInputs.setUserRole(userAccount.getUserRole());
            pageCommonInputs.setStatus(userAccount.getStatus());
            System.out.println("THE USER IS " + userAccount.getUsername());
            if (userAccount.getUsername() == null || userAccount.getPassword() == null) {
                pageCommonInputs.setShowEditButtons(true);
                preLoadPages();
            } else {
                pageCommonInputs.setShowEditButtons(false);

//                listOfSettings = new ArrayList<Ebo>();
//                listOfStaffs = new ArrayList<Ebo>();
//                listOfStudents = new ArrayList<Ebo>();
//                listOfExaminations = new ArrayList<Ebo>();
//                listOfDataSecurities = new ArrayList<Ebo>();
//                listOfFinancies = new ArrayList<Ebo>();
//
//                listOfAllAppPages = BinderSplitter.userPagesSplitterList(userAccount.getUserPages());
//                

                listOfAllAppPages.clear();
                listOfDataSecurities.clear();
                listOfExaminations.clear();
                listOfFinancies.clear();
                listOfSettings.clear();
                listOfStaffs.clear();
                listOfStaffsMembers.clear();
                listOfStudents.clear();
                listOfUserAccounts.clear();

                listOfAllAppPages = BinderSplitter.userPagesSplitterList(userAccount.getUserPages());

                for (Ebo eachAccess : listOfAllAppPages) {
                    System.out.println("THE SELECTED IS " + eachAccess.isSelected());
                    if (eachAccess.getMainMenu().equalsIgnoreCase(AppMainMenus.SETTINGS)) {
                        listOfSettings.add(eachAccess);
                    } else if (eachAccess.getMainMenu().equalsIgnoreCase(AppMainMenus.STAFFS)) {
                        listOfStaffs.add(eachAccess);
                    } else if (eachAccess.getMainMenu().equalsIgnoreCase(AppMainMenus.STUDENTS)) {
                        listOfStudents.add(eachAccess);
                    } else if (eachAccess.getMainMenu().equalsIgnoreCase(AppMainMenus.EXAMINATIONS)) {
                        listOfExaminations.add(eachAccess);
                    } else if (eachAccess.getMainMenu().equalsIgnoreCase(AppMainMenus.FINANCIES)) {
                        listOfFinancies.add(eachAccess);
                    } else if (eachAccess.getMainMenu().equalsIgnoreCase(AppMainMenus.DATA_SECURITY)) {
                        listOfDataSecurities.add(eachAccess);
                    }

                }

                staffDataModel = new ListDataModel<Ebo>(listOfStaffs);
                studentsDataModel = new ListDataModel<Ebo>(listOfStudents);
                examinationsDataModel = new ListDataModel<Ebo>(listOfExaminations);
                financiesDataModel = new ListDataModel<Ebo>(listOfFinancies);
                dataSecuritiesDataModel = new ListDataModel<Ebo>(listOfDataSecurities);
//                settingsDataModel=new ListDataModel<Ebo>();
//                settingsDataModel = new ListDataModel<Ebo>(listOfSettings);
//                System.out.println("data of settings " + settingsDataModel.getRowCount());


//                will contain all the default pages of the application
                List<Ebo> listOfAllSettingsSubmenus = new ArrayList<Ebo>(TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.SETTINGS, "STRING"));
//                List<Ebo> listOfAllStaffSubmenus = new ArrayList<Ebo>(TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.STAFFS, "STRING"));
//                List<Ebo> listOfAllStudentsSubmenus = new ArrayList<Ebo>(TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.STUDENTS, "STRING"));
//                List<Ebo> listOfAllxaminationSubmenus = new ArrayList<Ebo>(TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.EXAMINATIONS, "STRING"));
//                List<Ebo> listOfAllFinanciesSubmenus = new ArrayList<Ebo>(TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.FINANCIES, "STRING"));
//                List<Ebo> listOfAllDataSecuritiesSubmenus = new ArrayList<Ebo>(TisEjbSource.getCrudInstance().eboFindByAttribute(AppMenus.MAIN_MENU, AppMenus.DATA_SECURITY, "STRING"));
//
                int counter = 0;
                for (Ebo eachSettings : listOfAllSettingsSubmenus) {
                    if (!eachSettings.getSubmenu().equalsIgnoreCase(listOfSettings.get(counter).getSubmenu())) {
                        listOfSettings.add(eachSettings);
                    }
                    counter++;
                }
                settingsDataModel = new ListDataModel<Ebo>(listOfSettings);

//                for (Ebo ebo1 : listOfSettings) {
//                    System.out.println("THE SELECTED IS AFTER " + ebo1.isSelected());
//                }
//                System.out.println("");
//                counter = 0;
//                for (Ebo eachStaff : listOfAllStaffSubmenus) {
//                    if (!eachStaff.getSubmenu().equalsIgnoreCase(listOfStaffs.get(counter).getSubmenu())) {
//                        listOfStaffs.add(eachStaff);
//                    }
//                    counter++;
//                }
//
//
//
//                settingsDataModel = new ListDataModel<Ebo>(listOfSettings);
//                staffDataModel = new ListDataModel<Ebo>(listOfStaffs);
//                studentsDataModel = new ListDataModel<Ebo>(listOfStudents);
//                examinationsDataModel = new ListDataModel<Ebo>(listOfExaminations);
//                financiesDataModel = new ListDataModel<Ebo>(listOfFinancies);
//                dataSecuritiesDataModel = new ListDataModel<Ebo>(listOfDataSecurities);

//                System.out.println("THE SELECTED IN DATATABLE IS "+);
            }
            System.out.println("THE STAFF SELECTED IS " + staff.getSurname());
        } catch (Exception e) {
        }
    }

    @Override
    public String searchButton() {
        try {
            listOfUserAccounts = new ArrayList<UserAccount>();
            userAccountsDataModel = new ListDataModel<UserAccount>(listOfUserAccounts);
            listOfUserAccounts = TisEjbSource.getCrudInstance().userAccountFindByAttribute(searchInputs.getSearchParameter(), searchInputs.getSearchValue(), "STRING", false);
            if (listOfUserAccounts.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " For Staff Members");
                return "";
            }
            userAccountsDataModel = new ListDataModel<UserAccount>(listOfUserAccounts);
//            staffMembersDataModel = new ListDataModel<Staff>(listOfStaffsMembers);
        } catch (Exception e) {
            appLogger(e);
        }
        return "";

    }

    @Override
    public String viewAllButton() {

        listOfUserAccounts = new ArrayList<UserAccount>();
        userAccountsDataModel = new ListDataModel<UserAccount>(listOfUserAccounts);
        listOfUserAccounts = TisEjbSource.getCrudInstance().userAccountGetAll(false);

        if (listOfUserAccounts.isEmpty()) {
            JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " User Account");
            return "";
        }
        userAccountsDataModel = new ListDataModel<UserAccount>(listOfUserAccounts);

        return "";

    }

    public void prepareUserAccessPages() {
//        listOfSettings = new ArrayList<Ebo>();
//        listOfStaffs = new ArrayList<Ebo>();
//        listOfStudents = new ArrayList<Ebo>();
//        listOfExaminations = new ArrayList<Ebo>();
//        listOfDataSecurities = new ArrayList<Ebo>();
//        listOfFinancies = new ArrayList<Ebo>();


        listOfDataSecurities.clear();
        listOfExaminations.clear();
        listOfFinancies.clear();
        listOfSettings.clear();
        listOfStaffs.clear();
        listOfStaffsMembers.clear();
        listOfStudents.clear();
        listOfUserAccounts.clear();


        settingsDataModel = new ListDataModel<Ebo>(listOfSettings);
        staffDataModel = new ListDataModel<Ebo>(listOfStaffs);
        studentsDataModel = new ListDataModel<Ebo>(listOfStudents);
        examinationsDataModel = new ListDataModel<Ebo>(listOfExaminations);
        dataSecuritiesDataModel = new ListDataModel<Ebo>(listOfDataSecurities);
        financiesDataModel = new ListDataModel<Ebo>(listOfFinancies);
    }

    void appLogger(Exception e) {
        e.printStackTrace();
        Logger.getLogger(GeneralUserAccountController.class.getName()).log(Level.SEVERE, null, e.getCause());
    }
    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">

    public Ebo getEbo() {
        return ebo;
    }

    public void setEbo(Ebo ebo) {
        this.ebo = ebo;
    }

    public List<UserAccount> getListOfUserAccounts() {
        return listOfUserAccounts;
    }

    public void setListOfUserAccounts(List<UserAccount> listOfUserAccounts) {
        this.listOfUserAccounts = listOfUserAccounts;
    }

    public DataModel<Ebo> getAllPagesDataModel() {
        return allPagesDataModel;
    }

    public void setAllPagesDataModel(DataModel<Ebo> allPagesDataModel) {
        this.allPagesDataModel = allPagesDataModel;
    }

    public DataModel<UserAccount> getUserAccountsDataModel() {
        return userAccountsDataModel;
    }

    public void setUserAccountsDataModel(DataModel<UserAccount> userAccountsDataModel) {
        this.userAccountsDataModel = userAccountsDataModel;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public List<Ebo> getListOfSettings() {
        return listOfSettings;
    }

    public void setListOfSettings(List<Ebo> listOfSettings) {
        this.listOfSettings = listOfSettings;
    }

    public List<Ebo> getListOfStaffs() {
        return listOfStaffs;
    }

    public void setListOfStaffs(List<Ebo> listOfStaffs) {
        this.listOfStaffs = listOfStaffs;
    }

    public List<Ebo> getListOfStudents() {
        return listOfStudents;
    }

    public void setListOfStudents(List<Ebo> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }

    public List<Ebo> getListOfAllAppPages() {
        return listOfAllAppPages;
    }

    public void setListOfAllAppPages(List<Ebo> listOfAllAppPages) {
        this.listOfAllAppPages = listOfAllAppPages;
    }

    public PageCommonInputs getPageCommonInputs() {
        return pageCommonInputs;
    }

    public void setPageCommonInputs(PageCommonInputs pageCommonInputs) {
        this.pageCommonInputs = pageCommonInputs;
    }

    public DataModel<Ebo> getSettingsDataModel() {
        return settingsDataModel;
    }

    public void setSettingsDataModel(DataModel<Ebo> settingsDataModel) {
        this.settingsDataModel = settingsDataModel;
    }

    public DataModel<Ebo> getStaffDataModel() {
        return staffDataModel;
    }

    public void setStaffDataModel(DataModel<Ebo> staffDataModel) {
        this.staffDataModel = staffDataModel;
    }

    public DataModel<Ebo> getStudentsDataModel() {
        return studentsDataModel;
    }

    public void setStudentsDataModel(DataModel<Ebo> studentsDataModel) {
        this.studentsDataModel = studentsDataModel;
    }

    public DataModel<Ebo> getExaminationsDataModel() {
        return examinationsDataModel;
    }

    public void setExaminationsDataModel(DataModel<Ebo> examinationsDataModel) {
        this.examinationsDataModel = examinationsDataModel;
    }

    public DataModel<Ebo> getFinanciesDataModel() {
        return financiesDataModel;
    }

    public void setFinanciesDataModel(DataModel<Ebo> financiesDataModel) {
        this.financiesDataModel = financiesDataModel;
    }

    public DataModel<Ebo> getDataSecuritiesDataModel() {
        return dataSecuritiesDataModel;
    }

    public void setDataSecuritiesDataModel(DataModel<Ebo> dataSecuritiesDataModel) {
        this.dataSecuritiesDataModel = dataSecuritiesDataModel;
    }

    public boolean isAllSettings() {
        return allSettings;
    }

    public void setAllSettings(boolean allSettings) {
        this.allSettings = allSettings;
    }

    public boolean isAllStaffs() {
        return allStaffs;
    }

    public void setAllStaffs(boolean allStaffs) {
        this.allStaffs = allStaffs;
    }

    public boolean isAllStudents() {
        return allStudents;
    }

    public void setAllStudents(boolean allStudents) {
        this.allStudents = allStudents;
    }

    public boolean isAllFinacies() {
        return allFinacies;
    }

    public void setAllFinacies(boolean allFinacies) {
        this.allFinacies = allFinacies;
    }

    public boolean isAllExams() {
        return allExams;
    }

    public void setAllExams(boolean allExams) {
        this.allExams = allExams;
    }

    public List<Ebo> getListOfExaminations() {
        return listOfExaminations;
    }

    public void setListOfExaminations(List<Ebo> listOfExaminations) {
        this.listOfExaminations = listOfExaminations;
    }

    public List<Ebo> getListOfFinancies() {
        return listOfFinancies;
    }

    public void setListOfFinancies(List<Ebo> listOfFinancies) {
        this.listOfFinancies = listOfFinancies;
    }

    public List<Ebo> getListOfDataSecurities() {
        return listOfDataSecurities;
    }

    public void setListOfDataSecurities(List<Ebo> listOfDataSecurities) {
        this.listOfDataSecurities = listOfDataSecurities;
    }

    public boolean isAllDataSecurities() {
        return allDataSecurities;
    }

    public void setAllDataSecurities(boolean allDataSecurities) {
        this.allDataSecurities = allDataSecurities;
    }

    public UserSessionData getUserSessionData() {
        return userSessionData;
    }

    public List<Staff> getListOfStaffsMembers() {
        return listOfStaffsMembers;
    }

    public void setListOfStaffsMembers(List<Staff> listOfStaffsMembers) {
        this.listOfStaffsMembers = listOfStaffsMembers;
    }

    public DataModel<Staff> getStaffMembersDataModel() {
        return staffMembersDataModel;
    }

    public void setStaffMembersDataModel(DataModel<Staff> staffMembersDataModel) {
        this.staffMembersDataModel = staffMembersDataModel;
    }

    public void setUserSessionData(UserSessionData userSessionData) {
        this.userSessionData = userSessionData;
    }

    public SearchInputs getSearchInputs() {
        return searchInputs;
    }

    public void setSearchInputs(SearchInputs searchInputs) {
        this.searchInputs = searchInputs;
    }
    //</editor-fold>
}
