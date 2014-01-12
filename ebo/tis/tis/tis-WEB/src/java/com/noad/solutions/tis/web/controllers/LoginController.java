/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.tis.ejb.entities.AcademicYear;
import com.noad.solutions.tis.ejb.entities.AccessPage;
import com.noad.solutions.tis.ejb.entities.SchoolSetup;
import com.noad.solutions.tis.ejb.entities.Staff;
import com.noad.solutions.tis.ejb.entities.UserAccount;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.web.pagesrights.UserPages;
import com.noad.solutions.tis.web.pagesrights.UserPagesRights;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Daud
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    private UserAccount userAccount = new UserAccount();
    private UserSessionData userSessionData = new UserSessionData();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private AcademicYear ay;
    private List<UserPagesRights> listOfPagesRightses;
    private Date calendar = new Date();
    private String appDate;
    private SearchInputs searchInputs = new SearchInputs();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private String selectedUserAcademicYear = null;

    public LoginController() {
        ay = TisEjbSource.getBasicQuariesInstance().getCurrentAcademicYear();
    }

    public String checkUserAccessRight() {
        System.out.println("THE USERNAME AND PASSWORD ARE " + pageCommonInputs.getUsername() + "\t" + pageCommonInputs.getPassword());
        userAccount = TisEjbSource.getValidationInstance().getUserAccessDetails(pageCommonInputs.getUsername(), pageCommonInputs.getPassword());
        if (userAccount == null) {
            try {
                JSFMessages.errorMessage("Wrong username/password");
            } catch (Exception e) {
                appLogger(e);
            }
            return "";

        } else {
            System.out.println("THE USERNAME AND PASSWORD FROM DATABASE ARE " + userAccount.getUsername() + "\t" + userAccount.getPassword());
            List<SchoolSetup> schoolSetupList = null;
            schoolSetupList = TisEjbSource.getCrudInstance().schoolSetupGetAll(false);
            SchoolSetup schoolSetup = schoolSetupList.get(0);
            Staff staff = TisEjbSource.getCrudInstance().staffFind(userAccount.getUserAccountId());
            AcademicYear academicYear = TisEjbSource.getBasicQuariesInstance().getCurrentAcademicYear();
            System.out.println("THE CURRENT ACADEMIC YEAR IS " + academicYear.getAcademicYear());

            userSessionData.setCanChangeCurrentAcademicYear(userAccount.getUserRole().contains("Admin") ? true : false);
            userSessionData.setCurrentAcademicYear(academicYear);
            userSessionData.setStaff(staff);
            userSessionData.setSchoolSetup(schoolSetup);
            JSFUtility.setUserSession(JSFUtility.SESSION_NAME, userSessionData);
            JSFUtility.getCurrentContext().getExternalContext().getSessionMap().put(JSFUtility.SESSION_NAME, userSessionData);

            UserPagesRights pagesRights = new UserPagesRights();
            listOfPagesRightses = new ArrayList<UserPagesRights>();
            List<String> listOfMenus = new ArrayList<String>();
            List<AccessPage> listOfAccessPages = new ArrayList<AccessPage>();
            System.out.println("THE USER ID IS " + userAccount.getUserAccountId());
            listOfMenus = TisEjbSource.getBasicQuariesInstance().getUserMenus(userAccount.getUserAccountId());//GETS ALL USER MENUS
            System.out.println("THE SIZE OF MENU IS " + listOfMenus.size());
//            FOR EACH MENU ,GET ITS SUBMENU
            for (String eachMenu : listOfMenus) {
                listOfAccessPages = TisEjbSource.getCrudInstance().accessPageFindByAttribute(eachMenu, userAccount.getUserAccountId());
                List<UserPages> listOfUserPages = new ArrayList<UserPages>();
                System.out.println("THE SIZE OF SUMMENU OF " + eachMenu + " IS " + listOfAccessPages.size());
                UserPages userPage = new UserPages();
                for (AccessPage accessPage : listOfAccessPages) {
                    userPage.setPage(accessPage.getPageName()+".xhtml");
                    userPage.setSubMenu(accessPage.getUserSubmenu());
                    System.out.println("THE PAGE URL IS " + userPage.getPage());
                    listOfUserPages.add(userPage);
                    userPage = new UserPages();

                }
                listOfPagesRightses.add(pagesRights);
                pagesRights = new UserPagesRights();

            }
            return "pages/welcome.xhtml?faces-redirect=true";

        }




//        }

    }

    public String changeUserCurrentAcademicYear() {
        System.out.println("HAS COME HERE");
        try {
//            JSFUtility.getSession().invalidate();
            if (!selectedUserAcademicYear.equalsIgnoreCase(userSessionData.getCurrentAcademicYear().getAcademicYearId())) {
                userSessionData.setChangeCurrentAcademicYear(true);
            } else {
                userSessionData.setChangeCurrentAcademicYear(false);
            }
            System.out.println("THE AY IS " + userSessionData.isChangeCurrentAcademicYear());
//            userSessionData.setCanChangeCurrentAcademicYear(userAccount.getUserRole().contains("Admin") ? true : false);

            AcademicYear academicYear = TisEjbSource.getCrudInstance().academicYearFind(selectedUserAcademicYear);
            Staff s = TisEjbSource.getCrudInstance().staffFind(JSFUtility.getUserSession(JSFUtility.SESSION_NAME).getStaff().getStaffId());
            userSessionData.setCurrentAcademicYear(academicYear);
            userSessionData.setStaff(s);

            return "";

        } catch (Exception e) {
            appLogger(e);
        }
        return "";

    }

    public String logOut() {
        try {
            pageCommonInputs = new PageCommonInputs();
            JSFUtility.getSession().invalidate();
            return "../login.xhtml?faces-redirect=true";

        } catch (Exception e) {
            appLogger(e);
        }
        return null;

    }

    void appLogger(Exception e) {
        Logger.getLogger(UserAccountController.class.getName()).log(Level.SEVERE, null, e.getCause());
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public List<UserPagesRights> getListOfPagesRightses() {
        return listOfPagesRightses;
    }

    public UserSessionData getUserSessionData() {
        return userSessionData;
    }

    public void setUserSessionData(UserSessionData userSessionData) {
        this.userSessionData = userSessionData;
    }

    public AcademicYear getAy() {
        return ay;
    }

    public void setAy(AcademicYear ay) {
        this.ay = ay;
    }

    public String getSelectedUserAcademicYear() {
        return selectedUserAcademicYear;
    }

    public SelectedItemHelper getSelectedItemHelper() {
        return selectedItemHelper;
    }

    public void setSelectedItemHelper(SelectedItemHelper selectedItemHelper) {
        this.selectedItemHelper = selectedItemHelper;
    }

    public void setSelectedUserAcademicYear(String selectedUserAcademicYear) {
        this.selectedUserAcademicYear = selectedUserAcademicYear;
    }

    public void setListOfPagesRightses(List<UserPagesRights> listOfPagesRightses) {
        this.listOfPagesRightses = listOfPagesRightses;
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }

    public PageCommonInputs getPageCommonInputs() {
        return pageCommonInputs;
    }

    public void setPageCommonInputs(PageCommonInputs pageCommonInputs) {
        this.pageCommonInputs = pageCommonInputs;
    }

    public SearchInputs getSearchInputs() {
        return searchInputs;
    }

    public void setSearchInputs(SearchInputs searchInputs) {
        this.searchInputs = searchInputs;
    }

    public Date getCalendar() {
        return calendar;
    }

    public void setCalendar(Date calendar) {
        this.calendar = calendar;
    }
    //</editor-fold>
}
