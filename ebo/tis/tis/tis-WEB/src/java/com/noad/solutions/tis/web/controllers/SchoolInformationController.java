/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.JSFIdGenerator;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.tis.ejb.entities.SchoolSetup;
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
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.imageio.stream.FileImageOutputStream;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Daud
 */
@Named(value = "schoolInfoController")
@SessionScoped
public class SchoolInformationController implements Serializable {

    private SchoolSetup schoolSetup = new SchoolSetup();
    private UserSessionData userSessionData = new UserSessionData();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private SearchInputs searchInputs = new SearchInputs();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private List<SchoolSetup> listOfSchoolSetups;
    private DataModel<SchoolSetup> schoolSetupsDataModel;

    public SchoolInformationController() {
        pageCommonInputs.showDataRecordsDisplay();
        userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);
        viewAllButton();
    }


    public String saveButton() {
        try {
            schoolSetup.setSchoolSetupId(JSFIdGenerator.generateInstitutionId());
            schoolSetup.setCreatedBy(userSessionData.getStaff().getUserAccount().getUsername());
            pageCommonInputs.setCheckIfSaved(TisEjbSource.getCrudInstance().schoolSetupCreate(schoolSetup));
            if (pageCommonInputs.getCheckIfSaved() == null) {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_SAVE + schoolSetup.getSchoolName());
            } else {
                JSFMessages.infoMessage(schoolSetup.getSchoolName() + JSFMessages.SUCCESS_SAVE);
                resetButton();
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    public void resetButton() {
        try {
            schoolSetup = new SchoolSetup();
            searchInputs = new SearchInputs();
            selectedItemHelper = new SelectedItemHelper();

        } catch (Exception e) {
            appLogger(e);
        }
    }

    public String updateButton() {
        try {
            schoolSetup.setEditedBy(userSessionData.getStaff().getUserAccount().getUsername());
            pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().schoolSetupUpdate(schoolSetup));
            if (pageCommonInputs.isCheckIfUpdated()) {
                JSFMessages.infoMessage(schoolSetup.getSchoolName() + JSFMessages.SUCCESS_UPDATE);
                resetButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_UPDATE + schoolSetup.getSchoolName());
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    public void viewAllButton() {
        try {
            listOfSchoolSetups = new ArrayList<SchoolSetup>();
            schoolSetupsDataModel = new ListDataModel<SchoolSetup>(listOfSchoolSetups);
            listOfSchoolSetups = TisEjbSource.getCrudInstance().schoolSetupGetAll(false);
            if (listOfSchoolSetups.isEmpty()) {
                JSFMessages.errorMessage("No Institution Information Found");
                return;
            }
            schoolSetupsDataModel = new ListDataModel<SchoolSetup>(listOfSchoolSetups);
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void selectButton() {
        try {
            schoolSetup = schoolSetupsDataModel.getRowData();
            System.out.println("THE SCHOOL IS "+schoolSetup.getSchoolName());
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public String cancelButton() {
        return "../pages/welcome.xhtml?faces-redirect=true";
    }

    void appLogger(Exception e) {
        Logger.getLogger(SchoolSetupController.class.getName()).log(Level.SEVERE, null, e.getCause());
    }
    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">

    public SchoolSetup getSchoolSetup() {
        return schoolSetup;
    }

    public void setSchoolSetup(SchoolSetup schoolSetup) {
        this.schoolSetup = schoolSetup;
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

    public List<SchoolSetup> getListOfSchoolSetups() {
        return listOfSchoolSetups;
    }

    public void setListOfSchoolSetups(List<SchoolSetup> listOfSchoolSetups) {
        this.listOfSchoolSetups = listOfSchoolSetups;
    }

    public DataModel<SchoolSetup> getSchoolSetupsDataModel() {
        return schoolSetupsDataModel;
    }

    public void setSchoolSetupsDataModel(DataModel<SchoolSetup> schoolSetupsDataModel) {
        this.schoolSetupsDataModel = schoolSetupsDataModel;
    }
    //</editor-fold>
}
