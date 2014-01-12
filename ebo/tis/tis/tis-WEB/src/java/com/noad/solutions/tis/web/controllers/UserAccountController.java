/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.BinderSplitter;
import com.noad.solutions.common.classes.utils.CssStyles;
import com.noad.solutions.common.classes.utils.JSFIdGenerator;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.UserAccessRight;
import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.tis.ejb.entities.AccessPage;
import com.noad.solutions.tis.ejb.entities.Staff;
import com.noad.solutions.tis.ejb.entities.UserAccount;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.web.pagesrights.AppMainMenus;
import com.noad.solutions.tis.web.pagesrights.AppPageSettings;
import com.noad.solutions.tis.web.pagesrights.AppPages;
import com.noad.solutions.tis.web.pagesrights.UserPagesRights;
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
public class UserAccountController extends CssStyles implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="DECLARATIONS">
    public UserAccount userAccount = new UserAccount();
    public AccessPage accessPage = new AccessPage();
    public SearchInputs searchInputs = new SearchInputs();
    public SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    public boolean allSettings = false;
    public boolean allStaffs = false;
    public boolean allStudents = false;
    public boolean allFinacies = false;
    public boolean allExams = false;
    public boolean allDataSecurities = false;
    public List<AppPageSettings> listOfSettings;
    public List<AppPageSettings> listOfStaffs;
    public List<AppPageSettings> listOfStudents;
    public List<AppPageSettings> listOfExaminations;
    public List<AppPageSettings> listOfFinancies;
    public List<AppPageSettings> listOfDataSecurities;
    public List<UserAccount> listOfUserAccounts = null;
    public DataModel<AppPageSettings> settingsDataModel;
    public DataModel<AppPageSettings> staffDataModel;
    public DataModel<AppPageSettings> studentsDataModel;
    public DataModel<AppPageSettings> examinationsDataModel;
    public DataModel<AppPageSettings> financiesDataModel;
    public DataModel<AppPageSettings> dataSecuritiesDataModel;
    public DataModel<UserAccount> userAccountDataModel = null;
    public ArrayList<UserPagesRights> listOfPagesRightses;
    public int counteSelectedPages = 0;
    public String usercombinePages = "";
    //</editor-fold>
    public String confirmedPassword;
    public String userId = null;
    public String username = null;
    public String password = null;
    public String accessLevel = null;
    public String status = null;
    public PageCommonInputs pageCommonInputs = new PageCommonInputs();
    public Staff staff = new Staff();
    public List<Staff> listOfStaffsMembers = new ArrayList<Staff>();
    public DataModel<Staff> staffMembersDataModel;
    private UserSessionData userSessionData = new UserSessionData();
    private UserAccessRight userAccessRight = new UserAccessRight();

    public UserAccountController() {
        pageCommonInputs.showDataRecordsDisplay();
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);
        pageCommonInputs.showDataRecordsDisplay();
        preLoadAccessPages();
        List<AccessPage> accessPagesList = TisEjbSource.getCrudInstance().accessPageFindByAttribute("", userSessionData.getStaff().getStaffId());
        if (accessPagesList.isEmpty()) {
            userAccessRight.setAccessRight(UserAccessRight.hide);
        } else {
            AccessPage ap = accessPagesList.get(0);
            userAccessRight.setPrintRight(ap.getSelected() ? UserAccessRight.display : UserAccessRight.hide);
            userAccessRight.setWriteRight(ap.getWriteLevel() ? UserAccessRight.display : UserAccessRight.hide);
            userAccessRight.setReadRight(ap.getReadLevel() ? UserAccessRight.display : UserAccessRight.hide);
        }
    }

    private void preLoadAccessPages() {

        setListOfStudents(new ArrayList<AppPageSettings>());
        setListOfSettings(new ArrayList<AppPageSettings>());
        setListOfStaffs(new ArrayList<AppPageSettings>());
        setListOfExaminations(new ArrayList<AppPageSettings>());
        setListOfFinancies(new ArrayList<AppPageSettings>());
        setListOfDataSecurities(new ArrayList<AppPageSettings>());
        settingsDataModel = new ListDataModel<AppPageSettings>(listOfSettings);
        staffDataModel = new ListDataModel<AppPageSettings>(listOfStaffs);
        studentsDataModel = new ListDataModel<AppPageSettings>(listOfStudents);
        examinationsDataModel = new ListDataModel<AppPageSettings>(listOfExaminations);
        financiesDataModel = new ListDataModel<AppPageSettings>(listOfFinancies);
        dataSecuritiesDataModel = new ListDataModel<AppPageSettings>(listOfDataSecurities);

        try {
            setListOfSettings(AppPages.settingsSubMenus());
            setListOfStaffs(AppPages.staffSubMenus());
            setListOfStudents(AppPages.studentsSubMenus());
            setListOfExaminations(AppPages.examinationsSubMenus());
            setListOfFinancies(AppPages.financiesSubMenus());
            setListOfDataSecurities(AppPages.dataSecuritySubMenus());

            setStudentsDataModel(new ListDataModel<AppPageSettings>(getListOfStudents()));
            setStaffDataModel(new ListDataModel<AppPageSettings>(getListOfStaffs()));
            setSettingsDataModel(new ListDataModel<AppPageSettings>(getListOfSettings()));
            setExaminationsDataModel(new ListDataModel<AppPageSettings>(getListOfExaminations()));
            setFinanciesDataModel(new ListDataModel<AppPageSettings>(getListOfFinancies()));
            setDataSecuritiesDataModel(new ListDataModel<AppPageSettings>(getListOfDataSecurities()));
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void allSettingsListener() {


        if (isAllSettings()) {
            for (AppPageSettings eachSettings : getListOfSettings()) {
                eachSettings.setSelected(true);
                eachSettings.setWriteLevel(true);
                eachSettings.setReadLevel(true);
                eachSettings.setPrintLevel(true);
            }
        } else {
            setListOfSettings(AppPages.settingsSubMenus());
        }
        settingsDataModel = new ListDataModel<AppPageSettings>(listOfSettings);
        for (AppPageSettings selected : listOfSettings) {
            System.out.println(" SEEEEEEE SELECTED >>>>>>>>>>>> " + selected.isSelected());
        }
    }

    public void allStaffsListener() {
        if (isAllStaffs()) {
            for (AppPageSettings eachStaff : getListOfStaffs()) {
                eachStaff.setSelected(true);
                eachStaff.setWriteLevel(true);
                eachStaff.setReadLevel(true);
                eachStaff.setPrintLevel(true);
            }
        } else {
            setListOfStaffs(AppPages.staffSubMenus());
        }
        staffDataModel = new ListDataModel<AppPageSettings>(listOfStaffs);
    }

    public void allStudentsListener() {
        if (isAllStudents()) {
            for (AppPageSettings eachClaim : getListOfStudents()) {
                eachClaim.setSelected(true);
                eachClaim.setWriteLevel(true);
                eachClaim.setReadLevel(true);
                eachClaim.setPrintLevel(true);
            }
        } else {
            setListOfStudents(AppPages.studentsSubMenus());
        }
        setStudentsDataModel(new ListDataModel<AppPageSettings>(getListOfStudents()));
    }

    public void allExaminationsListener() {
        if (isAllExams()) {
            for (AppPageSettings eachExams : getListOfExaminations()) {
                eachExams.setSelected(true);
                eachExams.setWriteLevel(true);
                eachExams.setReadLevel(true);
                eachExams.setPrintLevel(true);
            }
        } else {
            setListOfExaminations(AppPages.examinationsSubMenus());
        }
        setExaminationsDataModel(new ListDataModel<AppPageSettings>(getListOfExaminations()));
    }

    public void allFinanciesListener() {
        if (isAllFinacies()) {
            for (AppPageSettings eachReport : getListOfFinancies()) {
                eachReport.setSelected(true);
                eachReport.setWriteLevel(true);
                eachReport.setReadLevel(true);
                eachReport.setPrintLevel(true);
            }
        } else {
            setListOfFinancies(AppPages.financiesSubMenus());
        }
        setFinanciesDataModel(new ListDataModel<AppPageSettings>(getListOfFinancies()));
    }

    public void allDataSecuritiesListener() {
        if (isAllDataSecurities()) {
            for (AppPageSettings eachDataSecurity : getListOfDataSecurities()) {
                eachDataSecurity.setSelected(true);
                eachDataSecurity.setWriteLevel(true);
                eachDataSecurity.setReadLevel(true);
                eachDataSecurity.setPrintLevel(true);
            }
        } else {
            setListOfDataSecurities(AppPages.dataSecuritySubMenus());
        }
        setDataSecuritiesDataModel(new ListDataModel<AppPageSettings>(getListOfDataSecurities()));
    }

    public void newDataButton() {
    }

    public String saveButton() {

        try {

            List<AppPageSettings> allUserPages = new ArrayList<AppPageSettings>();
            allUserPages.addAll(listOfDataSecurities);
            allUserPages.addAll(listOfExaminations);
            allUserPages.addAll(listOfFinancies);
            allUserPages.addAll(listOfSettings);
            allUserPages.addAll(listOfStaffs);
            allUserPages.addAll(listOfStudents);
            String userPages = BinderSplitter.bindUserPages(allUserPages);
            if (userPages.equalsIgnoreCase("<NO_PAGES>")) {
                JSFMessages.errorMessage("Please Select User Access Pages");
            } else {
                userAccount.setUserPages(userPages);
                userAccount.setUsername(JSFIdGenerator.generateUsername(staff.getSurname(), staff.getOtherNames(), staff.getDepartment().getDepartmentName()));
                userAccount.setPassword(JSFIdGenerator.generatePassword());
                userAccount.setUserAccountId(staff.getStaffId());
                userAccount.setStatus(pageCommonInputs.getStatus());
                userAccount.setUserRole(pageCommonInputs.getUserRole());
                userAccount.setCreatedDate(new Date());
                saveUserAccessPages(allUserPages);
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

    public String updateButton() {

        try {
            TisEjbSource.getBasicQuariesInstance().deleteUserPages(staff.getStaffId());
            List<AppPageSettings> allUserPages = new ArrayList<AppPageSettings>();
            allUserPages.addAll(listOfDataSecurities);
            allUserPages.addAll(listOfExaminations);
            allUserPages.addAll(listOfFinancies);
            allUserPages.addAll(listOfSettings);
            allUserPages.addAll(listOfStaffs);
            allUserPages.addAll(listOfStudents);
            System.out.println("THE SIZE OF ALL USER PAGES IS " + allUserPages.size());
            String userPages = BinderSplitter.bindUserPages(allUserPages);
            if (userPages.equalsIgnoreCase("<NO_PAGES>")) {
                JSFMessages.errorMessage("Please Select User Access Pages");
            } else {
                userAccount.setUserPages(userPages);
                userAccount.setStatus(pageCommonInputs.getStatus());
                userAccount.setUserRole(pageCommonInputs.getUserRole());
                userAccount.setCreatedDate(new Date());
                saveUserAccessPages(allUserPages);

                pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().userAccountUpdate(userAccount));
                if (pageCommonInputs.isCheckIfUpdated()) {
                    JSFMessages.infoMessage("User Account Of " + staff.getSurname() + " " + staff.getOtherNames() + " Updated");
                    resetButton();
                    viewAllButton();
                    pageCommonInputs.showDataRecordsDisplay();
                } else {
                    JSFMessages.errorMessage("Failed To Update User Account Of " + staff.getSurname() + " " + staff.getOtherNames());
                }
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    public String searchButton() {
//        try {
//            setListOfUserAccounts(new ArrayList<UserAccount>());
//            setUserAccountDataModel(new ListDataModel<UserAccount>(getListOfUserAccounts()));
//            setListOfUserAccounts(TisEjbSource.getCrudInstance().userAccountFindByAttribute(getSearchInputs().getSearchParameter(), getSearchInputs().getSearchValue(), "STRING", false));
//            System.out.println("THE SIZE OF USERACCOUNT IS " + getListOfUserAccounts().size());
//            setUserAccountDataModel(new ListDataModel<UserAccount>(getListOfUserAccounts()));
//        } catch (Exception e) {
//            appLogger(e);
//        }
//        
        try {
            listOfUserAccounts = new ArrayList<UserAccount>();
            userAccountDataModel = new ListDataModel<UserAccount>(listOfUserAccounts);
            listOfUserAccounts = TisEjbSource.getCrudInstance().userAccountFindByAttribute(searchInputs.getSearchParameter(), searchInputs.getSearchValue(), "STRING", false);
            if (listOfUserAccounts.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " For Staff Members");
                return "";
            }
            userAccountDataModel = new ListDataModel<UserAccount>(listOfUserAccounts);
//            staffMembersDataModel = new ListDataModel<Staff>(listOfStaffsMembers);
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    public String viewAllButton() {
        try {
//            listOfStaffsMembers = new ArrayList<Staff>();
//            staffMembersDataModel = new ListDataModel<Staff>(listOfStaffsMembers);
//            listOfStaffsMembers = TisEjbSource.getCrudInstance().staffGetAll(false);
//            System.out.println("THE SIZE OF STAFF IS " + listOfStaffsMembers.size());
//
//            if (listOfStaffsMembers.isEmpty()) {
//                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " User Account");
//                return "";
//            }
//            staffMembersDataModel = new ListDataModel<Staff>(listOfStaffsMembers);

            listOfUserAccounts = new ArrayList<UserAccount>();
            userAccountDataModel = new ListDataModel<UserAccount>(listOfUserAccounts);
            listOfUserAccounts = TisEjbSource.getCrudInstance().userAccountGetAll(false);

            if (listOfUserAccounts.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " User Account");
                return "";
            }
            userAccountDataModel = new ListDataModel<UserAccount>(listOfUserAccounts);

            return "";
        } catch (Exception e) {
            appLogger(e);
        }


        return "";
    }

    public String selectButton() {
        try {

            pageCommonInputs.showDataInputsDisplay();
            userAccount = userAccountDataModel.getRowData();
            staff = userAccount.getStaff();
            setUserId(staff.getStaffId());
            setStatus(null);
            setAccessLevel(null);
        } catch (Exception e) {
            appLogger(e);
        }

        try {
            System.out.println("THE SELECTED STAFF IS " + staff.getSurname());
            if (staff.getUserAccount().getUsername() == null || staff.getUserAccount().getPassword() == null) {
                getPageCommonInputs().setShowEditButtons(true);
            } else {
                getPageCommonInputs().setShowEditButtons(false);
                setStatus(getUserAccount().getStatus());
                setAccessLevel(getUserAccount().getUserRole());
            }
        } catch (Exception e) {
            appLogger(e);
        }


        System.out.println("THE USER ID IS " + getUserId());
        setListOfPagesRightses(new ArrayList<UserPagesRights>());
        List<String> listOfMenus = new ArrayList<String>();
        List<AccessPage> listOfAccessPages;
        System.out.println("THE SIZE OF MENU IS " + listOfMenus.size());
        setListOfSettings(new ArrayList<AppPageSettings>());
        setListOfStudents(new ArrayList<AppPageSettings>());
        setListOfStaffs(new ArrayList<AppPageSettings>());
        setListOfExaminations(new ArrayList<AppPageSettings>());
        setListOfFinancies(new ArrayList<AppPageSettings>());
        setListOfDataSecurities(new ArrayList<AppPageSettings>());
        settingsDataModel = new ListDataModel<AppPageSettings>(listOfSettings);
        staffDataModel = new ListDataModel<AppPageSettings>(listOfStaffs);
        studentsDataModel = new ListDataModel<AppPageSettings>(listOfStudents);
        examinationsDataModel = new ListDataModel<AppPageSettings>(listOfExaminations);
        financiesDataModel = new ListDataModel<AppPageSettings>(listOfFinancies);
        dataSecuritiesDataModel = new ListDataModel<AppPageSettings>(listOfDataSecurities);

        AppPageSettings aps = new AppPageSettings(0, false, null, null, null);
        String allSettingsPages = "";
        String allStaffPages = "";
        String allStudentPages = "";
        String allExaminationsPages = "";
        String allFinanciesPages = "";
        String allDataSecuritiesPages = "";
        try {

            //<editor-fold defaultstate="collapsed" desc="USER SELECTED PAGE RIGHTS">
            System.out.println("THE USER ID IS " + staff.getStaffId());
            listOfMenus = TisEjbSource.getBasicQuariesInstance().getUserMenus(staff.getStaffId());
            for (String eachMenu : listOfMenus) {
                listOfAccessPages = TisEjbSource.getCrudInstance().accessPageFindByAttribute(eachMenu, staff.getStaffId());
                if (eachMenu.equalsIgnoreCase(AppMainMenus.SETTINGS)) {
                    for (AccessPage accessPage1 : listOfAccessPages) {
                        setUserSubmenus(aps, accessPage1);
                        getListOfSettings().add(aps);
                        allSettingsPages = allSettingsPages.concat(aps.getSubmenuName());
                        System.out.println("THE ALL SETTINGS PAGES IS " + allSettingsPages);
                        aps = new AppPageSettings(1, false, null, null, null);
                    }

                } else if (eachMenu.equalsIgnoreCase(AppMainMenus.STAFFS)) {
                    for (AccessPage accessPage1 : listOfAccessPages) {
                        setUserSubmenus(aps, accessPage1);
                        getListOfStaffs().add(aps);
                        allStaffPages = allStaffPages.concat(aps.getSubmenuName());
                        aps = new AppPageSettings(2, false, null, null, null);
                    }
                } else if (eachMenu.equalsIgnoreCase(AppMainMenus.STUDENTS)) {
                    for (AccessPage accessPage1 : listOfAccessPages) {
                        setUserSubmenus(aps, accessPage1);
                        getListOfStudents().add(aps);
                        allStudentPages = allStudentPages.concat(aps.getSubmenuName());
                        aps = new AppPageSettings(3, false, null, null, null);
                    }
                } else if (eachMenu.equalsIgnoreCase(AppMainMenus.EXAMINATIONS)) {
                    for (AccessPage accessPage1 : listOfAccessPages) {
                        setUserSubmenus(aps, accessPage1);
                        getListOfExaminations().add(aps);
                        allExaminationsPages = allExaminationsPages.concat(aps.getSubmenuName());
                        aps = new AppPageSettings(4, false, null, null, null);
                    }
                } else if (eachMenu.equalsIgnoreCase(AppMainMenus.FINANCIES)) {
                    for (AccessPage accessPage1 : listOfAccessPages) {
                        setUserSubmenus(aps, accessPage1);
                        getListOfFinancies().add(aps);
                        allFinanciesPages = allFinanciesPages.concat(aps.getSubmenuName());
                        aps = new AppPageSettings(5, false, null, null, null);
                    }
                } else if (eachMenu.equalsIgnoreCase(AppMainMenus.DATA_SECURITY)) {
                    for (AccessPage accessPage1 : listOfAccessPages) {
                        setUserSubmenus(aps, accessPage1);
                        getListOfDataSecurities().add(aps);
                        allFinanciesPages = allDataSecuritiesPages.concat(aps.getSubmenuName());
                        aps = new AppPageSettings(6, false, null, null, null);
                    }
                }
            }


            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="APP PAGES RIGHTS">
            List<AppPageSettings> allStaffsSubmenus = new ArrayList<AppPageSettings>(AppPages.staffSubMenus());
            List<AppPageSettings> allSettingsSumbmenus = new ArrayList<AppPageSettings>(AppPages.settingsSubMenus());
            List<AppPageSettings> allStudentsSubmenu = new ArrayList<AppPageSettings>(AppPages.studentsSubMenus());
            List<AppPageSettings> allExaminationsSubmenus = new ArrayList<AppPageSettings>(AppPages.examinationsSubMenus());
            List<AppPageSettings> allFinanciesSubmenus = new ArrayList<AppPageSettings>(AppPages.financiesSubMenus());
            List<AppPageSettings> allDataSecurtiesSubmenus = new ArrayList<AppPageSettings>(AppPages.dataSecuritySubMenus());
            for (AppPageSettings eachSettingsSubMenu : allSettingsSumbmenus) {
                if (!allSettingsPages.contains(eachSettingsSubMenu.getSubmenuName())) {
                    aps.setMenuName(eachSettingsSubMenu.getMenuName());
                    aps.setPageName(eachSettingsSubMenu.getPageName());
                    aps.setSelected(false);
                    aps.setSubmenuName(eachSettingsSubMenu.getSubmenuName());
                    getListOfSettings().add(aps);
                    aps = new AppPageSettings(1, false, null, null, null);
                }


            }
            System.out.println("THE SIZE OF SETTINGS IS " + listOfSettings.size());
            for (AppPageSettings pageSettings : listOfSettings) {
                System.out.println(pageSettings.isSelected() + " " + pageSettings.getMenuName() + " " + pageSettings.getPageName() + " " + pageSettings.getSubmenuName());
            }
            settingsDataModel = new ListDataModel<AppPageSettings>(listOfSettings);
            for (AppPageSettings eachStudentSubmenu : allStudentsSubmenu) {
                if (!allStudentPages.contains(eachStudentSubmenu.getSubmenuName())) {
                    aps.setMenuName(eachStudentSubmenu.getMenuName());
                    aps.setPageName(eachStudentSubmenu.getPageName());
                    aps.setSelected(false);
                    aps.setSubmenuName(eachStudentSubmenu.getSubmenuName());
                    getListOfStudents().add(aps);
                    aps = new AppPageSettings(1,false, null, null, null);
                }


            }
            for (AppPageSettings eachStaffSubMenu : allStaffsSubmenus) {
                if (!allStaffPages.contains(eachStaffSubMenu.getSubmenuName())) {
                    aps.setMenuName(eachStaffSubMenu.getMenuName());
                    aps.setPageName(eachStaffSubMenu.getPageName());
                    aps.setSelected(false);
                    aps.setSubmenuName(eachStaffSubMenu.getSubmenuName());
                    getListOfStaffs().add(aps);
                    aps = new AppPageSettings(2,false, null, null, null);
                }
            }
            for (AppPageSettings eachClientSubMenu : allExaminationsSubmenus) {
                if (!allExaminationsPages.contains(eachClientSubMenu.getSubmenuName())) {
                    aps.setMenuName(eachClientSubMenu.getMenuName());
                    aps.setPageName(eachClientSubMenu.getPageName());
                    aps.setSelected(false);
                    aps.setSubmenuName(eachClientSubMenu.getSubmenuName());
                    getListOfExaminations().add(aps);
                    aps = new AppPageSettings(3,false, null, null, null);

                }

            }
            for (AppPageSettings eachFinanciesSubMenu : allFinanciesSubmenus) {
                if (!allFinanciesPages.contains(eachFinanciesSubMenu.getSubmenuName())) {
                    aps.setMenuName(eachFinanciesSubMenu.getMenuName());
                    aps.setPageName(eachFinanciesSubMenu.getPageName());
                    aps.setSelected(false);
                    aps.setSubmenuName(eachFinanciesSubMenu.getSubmenuName());
                    getListOfFinancies().add(aps);
                    aps = new AppPageSettings(4,false, null, null, null);

                }

            }
            for (AppPageSettings eachDataSecuritySubMenu : allDataSecurtiesSubmenus) {
                if (!allDataSecuritiesPages.contains(eachDataSecuritySubMenu.getSubmenuName())) {
                    aps.setMenuName(eachDataSecuritySubMenu.getMenuName());
                    aps.setPageName(eachDataSecuritySubMenu.getPageName());
                    aps.setSelected(false);
                    aps.setSubmenuName(eachDataSecuritySubMenu.getSubmenuName());
                    getListOfDataSecurities().add(aps);
                    aps = new AppPageSettings(5,false, null, null, null);

                }

            }
            //</editor-fold>

            System.out.println("ALL SETTINGS PAGES " + allSettingsPages);
            System.out.println("ALL EXAMS PAGES " + allStaffPages);
            System.out.println("ALL STUDENTS PAGES " + allStudentPages);
//            setSettingsDataModel(new ListDataModel<AppPageSettings>(getListOfSettings()));
            setStudentsDataModel(new ListDataModel<AppPageSettings>(getListOfStudents()));
            setStaffDataModel(new ListDataModel<AppPageSettings>(getListOfStaffs()));
            setExaminationsDataModel(new ListDataModel<AppPageSettings>(getListOfExaminations()));
            setFinanciesDataModel(new ListDataModel<AppPageSettings>(getListOfFinancies()));
            setDataSecuritiesDataModel(new ListDataModel<AppPageSettings>(getListOfDataSecurities()));
            return "general_user_accounts.xhtml?faces-redirect=true";
//                    + PageExtensions.FACES_REDIRECT;
        } catch (Exception e) {
            appLogger(e);
        }
        return "general_user_accounts.xhtml?faces-redirect=true";


    }

    public void resetDataTable() {
        try {
            searchInputs = new SearchInputs();
            listOfUserAccounts = new ArrayList<UserAccount>();
            userAccountDataModel = new ListDataModel<UserAccount>(listOfUserAccounts);
        } catch (Exception e) {
        }

    }

    public void resetButton() {

        setUserAccount(new UserAccount());
        preLoadAccessPages();
        setAllStudents(false);
        setAllSettings(false);
        setAllStaffs(false);
        setAllFinacies(false);
        setAllExams(false);
        setAllDataSecurities(false);
        setUsername(null);
        setPassword(null);
        setStatus(null);

    }

    public void cancelButton() {
        pageCommonInputs.showDataRecordsDisplay();
        pageCommonInputs.setShowEditButtons(true);
        userAccount = new UserAccount();
        staff = new Staff();
        preLoadAccessPages();
    }

    public String deleteButton() {
        try {
            getUserAccount().setUserAccountId(staff.getStaffId());
            getUserAccount().setUserRole(null);
            getUserAccount().setStatus(null);
            getUserAccount().setUsername(null);
            getUserAccount().setPassword(null);
            getUserAccount().setUserPages(null);
            getUserAccount().setCreatedBy(userSessionData.getStaff().getUserAccount().getUsername());
            getUserAccount().setCreatedDate(new Date());
            getPageCommonInputs().setCheckIfUpdated(TisEjbSource.getCrudInstance().userAccountUpdate(getUserAccount()));
            TisEjbSource.getBasicQuariesInstance().deleteUserPages(staff.getStaffId());
            if (getPageCommonInputs().checkIfUpdated) {
                JSFMessages.infoMessage("User Account Of " + staff.getSurname() + " " + staff.getOtherNames() + " Deleted");
                resetButton();
            } else {
                JSFMessages.warnMessage("Failed To Delete User Account Of " + staff.getSurname() + " " + staff.getOtherNames());
            }

        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    private void saveUserAccessPages(List<AppPageSettings> appPageSettingses) {
        try {

            AccessPage ap = new AccessPage();
            for (AppPageSettings aps : appPageSettingses) {
                if (aps.isSelected()) {
                    ap.setCreatedBy(userSessionData.getStaff().getUserAccount().getUsername());
                    ap.setAccessPageId(userAccount.getUserAccountId().toUpperCase() + "-" + JSFIdGenerator.generateRandomId());
                    ap.setDateCreated(new Date());
                    ap.setOrderLevel(aps.getOrderLevel());
                    ap.setPageName(aps.getPageName());
                    ap.setUserSubmenu(aps.getSubmenuName());
                    ap.setUserMenu(aps.getMenuName());
                    ap.setPrintLevel(aps.isPrintLevel());
                    ap.setReadLevel(aps.isReadLevel());
                    ap.setUserAccount(userAccount.getUserAccountId());
                    ap.setWriteLevel(aps.isWriteLevel());
                    ap.setSelected(aps.isSelected());
                    TisEjbSource.getCrudInstance().accessPageCreate(ap);
                    ap = new AccessPage();
                }

            }
        } catch (Exception e) {
            appLogger(e);
        }
    }

    void appLogger(Exception e) {
        Logger.getLogger(UserAccountController.class.getName()).log(Level.SEVERE, null, e.getCause());
    }

    private void setUserSubmenus(AppPageSettings aps, AccessPage accessPage1) {

        aps.setSelected(true);
        aps.setMenuName(accessPage1.getUserMenu());
        aps.setSubmenuName(accessPage1.getUserSubmenu());
        aps.setPageName(accessPage1.getPageName());
        aps.setReadLevel(accessPage1.getReadLevel());
        aps.setWriteLevel(accessPage1.getWriteLevel());
        if (accessPage1.getPrintLevel() == null) {
            aps.setPrintLevel(false);
        } else {
            aps.setPrintLevel(accessPage1.getWriteLevel());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public AccessPage getAccessPage() {
        return accessPage;
    }

    public void setAccessPage(AccessPage accessPage) {
        this.accessPage = accessPage;
    }

    public SearchInputs getSearchInputs() {
        return searchInputs;
    }

    public void setSearchInputs(SearchInputs searchInputs) {
        this.searchInputs = searchInputs;
    }

    public SelectedItemHelper getSelectedItemHelper() {
        return selectedItemHelper;
    }

    public void setSelectedItemHelper(SelectedItemHelper selectedItemHelper) {
        this.selectedItemHelper = selectedItemHelper;
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

    public boolean isAllDataSecurities() {
        return allDataSecurities;
    }

    public void setAllDataSecurities(boolean allDataSecurities) {
        this.allDataSecurities = allDataSecurities;
    }

    public List<AppPageSettings> getListOfSettings() {
        return listOfSettings;
    }

    public void setListOfSettings(List<AppPageSettings> listOfSettings) {
        this.listOfSettings = listOfSettings;
    }

    public List<AppPageSettings> getListOfStaffs() {
        return listOfStaffs;
    }

    public void setListOfStaffs(List<AppPageSettings> listOfStaffs) {
        this.listOfStaffs = listOfStaffs;
    }

    public List<AppPageSettings> getListOfStudents() {
        return listOfStudents;
    }

    public void setListOfStudents(List<AppPageSettings> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }

    public List<AppPageSettings> getListOfExaminations() {
        return listOfExaminations;
    }

    public void setListOfExaminations(List<AppPageSettings> listOfExaminations) {
        this.listOfExaminations = listOfExaminations;
    }

    public List<AppPageSettings> getListOfFinancies() {
        return listOfFinancies;
    }

    public void setListOfFinancies(List<AppPageSettings> listOfFinancies) {
        this.listOfFinancies = listOfFinancies;
    }

    public List<AppPageSettings> getListOfDataSecurities() {
        return listOfDataSecurities;
    }

    public void setListOfDataSecurities(List<AppPageSettings> listOfDataSecurities) {
        this.listOfDataSecurities = listOfDataSecurities;
    }

    public List<UserAccount> getListOfUserAccounts() {
        return listOfUserAccounts;
    }

    public void setListOfUserAccounts(List<UserAccount> listOfUserAccounts) {
        this.listOfUserAccounts = listOfUserAccounts;
    }

    public DataModel<AppPageSettings> getSettingsDataModel() {
        return settingsDataModel;
    }

    public void setSettingsDataModel(DataModel<AppPageSettings> settingsDataModel) {
        this.settingsDataModel = settingsDataModel;
    }

    public DataModel<AppPageSettings> getStaffDataModel() {
        return staffDataModel;
    }

    public void setStaffDataModel(DataModel<AppPageSettings> staffDataModel) {
        this.staffDataModel = staffDataModel;
    }

    public DataModel<AppPageSettings> getStudentsDataModel() {
        return studentsDataModel;
    }

    public void setStudentsDataModel(DataModel<AppPageSettings> studentsDataModel) {
        this.studentsDataModel = studentsDataModel;
    }

    public DataModel<AppPageSettings> getExaminationsDataModel() {
        return examinationsDataModel;
    }

    public void setExaminationsDataModel(DataModel<AppPageSettings> examinationsDataModel) {
        this.examinationsDataModel = examinationsDataModel;
    }

    public DataModel<AppPageSettings> getFinanciesDataModel() {
        return financiesDataModel;
    }

    public void setFinanciesDataModel(DataModel<AppPageSettings> financiesDataModel) {
        this.financiesDataModel = financiesDataModel;
    }

    public DataModel<AppPageSettings> getDataSecuritiesDataModel() {
        return dataSecuritiesDataModel;
    }

    public void setDataSecuritiesDataModel(DataModel<AppPageSettings> dataSecuritiesDataModel) {
        this.dataSecuritiesDataModel = dataSecuritiesDataModel;
    }

    public DataModel<UserAccount> getUserAccountDataModel() {
        return userAccountDataModel;
    }

    public void setUserAccountDataModel(DataModel<UserAccount> userAccountDataModel) {
        this.userAccountDataModel = userAccountDataModel;
    }

    public ArrayList<UserPagesRights> getListOfPagesRightses() {
        return listOfPagesRightses;
    }

    public void setListOfPagesRightses(ArrayList<UserPagesRights> listOfPagesRightses) {
        this.listOfPagesRightses = listOfPagesRightses;
    }

    public int getCounteSelectedPages() {
        return counteSelectedPages;
    }

    public void setCounteSelectedPages(int counteSelectedPages) {
        this.counteSelectedPages = counteSelectedPages;
    }

    public String getUsercombinePages() {
        return usercombinePages;
    }

    public void setUsercombinePages(String usercombinePages) {
        this.usercombinePages = usercombinePages;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessLevel() {
        return accessLevel;
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

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getStatus() {
        return status;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PageCommonInputs getPageCommonInputs() {
        return pageCommonInputs;
    }

    public void setPageCommonInputs(PageCommonInputs pageCommonInputs) {
        this.pageCommonInputs = pageCommonInputs;
    }
    //</editor-fold>
}
