/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.CssStyles;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.ejb.entities.SchoolSetup;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.imageio.stream.FileImageOutputStream;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Daud
 */
@Named(value = "schoolSetupController")
@SessionScoped
public class SchoolSetupController extends CssStyles implements Serializable {

    private SchoolSetup schoolSetup = new SchoolSetup();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private SearchInputs searchInputs = new SearchInputs();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private List<SchoolSetup> listOfSchoolSetups;
    private List<SchoolSetup> listOfFreezSchoolSetups = new ArrayList<SchoolSetup>();
    private DataModel<SchoolSetup> schoolSetupsDataModel;
    private String clientCode;
    private String computerName;
    private String serverRoootImagePath = System.getProperty("com.sun.aas.instanceRoot")
            + File.separator + "docroot"
            + File.separator + "tisRes" + File.separator;
    private String clientPicturePath = serverRoootImagePath + "schoolPicture" + File.separator;
    private String clientPictureURL = "http://" + computerName + ":8080/tisres/" + "schoolPictureFolder" + "/";
    String imapgeCode;

    public SchoolSetupController() {
        pageCommonInputs.showDataRecordsDisplay();
        pageCommonInputs.setShowDataInputPanel("display:none;");
        pageCommonInputs.setShowDataRecordsPanel("display:inline;");
    }

    public void uploadMemberPicture(FileUploadEvent fileUploadEvent) {

        byte[] data = fileUploadEvent.getFile().getContents();
        System.out.println("THE SERVER ROOT IS " + serverRoootImagePath);
        FileImageOutputStream imageOutput;
        try {
            imapgeCode = UUID.randomUUID().toString().substring(1, 9);
            imageOutput = new FileImageOutputStream(new File(clientPicturePath + imapgeCode + ".jpg"));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
//            imageOutput.flush();
            clientPictureURL = "http://" + "localhost" + ":8080/tisres/" + "schoolPictureFolder" + "/" + imapgeCode + ".jpg";
            System.out.println("THE CLIENT schoolPictureFolderURE URL IS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + clientPictureURL);
            System.out.println("THE LENGTH OF THE PATH IS " + clientPicturePath.length());
            System.out.println("THE CLIENT schoolPictureFolderURE PATH IS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + clientPicturePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String saveButton() {
        try {
            pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().schoolSetupUpdate(schoolSetup));
            if (pageCommonInputs.isCheckIfUpdated()) {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_SAVE + schoolSetup.getSchoolName());
            } else {
                JSFMessages.infoMessage(schoolSetup.getSchoolSetupId() + JSFMessages.SUCCESS_SAVE);
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

    public String cancelButton() {
        return "../pages/welcome.xhtml?faces-redirect=true";
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

    public List<SchoolSetup> getListOfFreezSchoolSetups() {
        return listOfFreezSchoolSetups;
    }

    public void setListOfFreezSchoolSetups(List<SchoolSetup> listOfFreezSchoolSetups) {
        this.listOfFreezSchoolSetups = listOfFreezSchoolSetups;
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

    void appLogger(Exception e) {
        Logger.getLogger(SchoolSetupController.class.getName()).log(Level.SEVERE, null, e.getCause());
    }
}
