/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.CssStyles;
import com.noad.solutions.common.intefaces.utils.CommonMethodsInterface;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.ejb.entities.AcademicYear;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.jsf.utils.JSFMessages;
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
 * @author Daud
 */
@ManagedBean
@SessionScoped
public class AcademicYearsController extends CssStyles implements Serializable, CommonMethodsInterface {

    private AcademicYear academicYear = new AcademicYear();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private SearchInputs searchInputs = new SearchInputs();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private List<AcademicYear> listOfAcademicYears;
    private List<AcademicYear> listOfFreezAcademicYears = new ArrayList<AcademicYear>();
    private DataModel<AcademicYear> academicYearsDataModel;
    private boolean setAsCurrentAcademicYear = false;

    public AcademicYearsController() {
        pageCommonInputs.showDataRecordsDisplay();
        pageCommonInputs.setShowDataInputPanel("display:none;");
        pageCommonInputs.setShowDataRecordsPanel("display:inline;");
    }

    public void makeCurrentSemester() {
        try {

            for (AcademicYear eachYear : listOfAcademicYears) {
                eachYear.setCurrentSemester("NO");
                TisEjbSource.getCrudInstance().academicYearUpdate(eachYear);
            }
            academicYear = academicYearsDataModel.getRowData();
            academicYear.setCurrentSemester("YES");
            TisEjbSource.getCrudInstance().academicYearUpdate(academicYear);
            viewAllButton();
            JSFMessages.infoMessage(academicYear.getAcademicYear()+" Semester "+academicYear.getSemester()+" Set As Currrent Academic Year");
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
            if (TisEjbSource.getValidationInstance().checkDuplicateAcademicYear(academicYear)) {
                JSFMessages.warnMessage(academicYear.getAcademicYearId() + JSFMessages.DUPLICATE);
                return "";
            }
            if (setAsCurrentAcademicYear) {
                for (AcademicYear eachYear : listOfAcademicYears) {
                    eachYear.setCurrentSemester("NO");
                    TisEjbSource.getCrudInstance().academicYearUpdate(eachYear);
                }
                academicYear.setCurrentSemester("YES");
            }
            academicYear.setAcademicYearId(academicYear.getAcademicYear() + "/" + academicYear.getSemester());
            academicYear.setSemester(academicYear.getSemester().substring(3));
            pageCommonInputs.setCheckIfSaved(TisEjbSource.getCrudInstance().academicYearCreate(academicYear));
            if (pageCommonInputs.getCheckIfSaved() == null) {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_SAVE + academicYear.getAcademicYearId());
            } else {
                JSFMessages.infoMessage(academicYear.getAcademicYearId() + JSFMessages.SUCCESS_SAVE);
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
            academicYear = new AcademicYear();
            searchInputs = new SearchInputs();
            selectedItemHelper = new SelectedItemHelper();

        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public String updateButton() {

        try {
            academicYear.setSemester(academicYear.getSemester().substring(3));
            pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().academicYearUpdate(academicYear));

            if (pageCommonInputs.isCheckIfUpdated()) {
                JSFMessages.infoMessage(academicYear.getAcademicYearId() + JSFMessages.SUCCESS_UPDATE);
                resetButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_UPDATE + academicYear.getAcademicYearId());
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public void deleteButton() {
        try {
            pageCommonInputs.setCheckIfDeleted(TisEjbSource.getCrudInstance().academicYearDelete(academicYear, false));
            if (pageCommonInputs.isCheckIfDeleted()) {
                JSFMessages.infoMessage(academicYear.getAcademicYearId() + JSFMessages.SUCCESS_DELETE);
                resetButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_DELETE + academicYear.getAcademicYearId());
            }
        } catch (Exception e) {
            appLogger(e);

        }
    }

    @Override
    public void cancelButton() {
        try {
            pageCommonInputs.showDataRecordsDisplay();
            academicYear = new AcademicYear();
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public void resetDataTable() {
        try {
            listOfAcademicYears = new ArrayList<AcademicYear>();
            academicYearsDataModel = new ListDataModel<AcademicYear>();
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
            academicYear = academicYearsDataModel.getRowData();
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public String searchButton() {
        try {
            listOfAcademicYears = new ArrayList<AcademicYear>();
            academicYearsDataModel = new ListDataModel<AcademicYear>();
            listOfAcademicYears = TisEjbSource.getCrudInstance().academicYearFindByAttribute(searchInputs.getSearchParameter(), searchInputs.getSearchValue(), "STRING", false);
            if (listOfAcademicYears.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND);
                return "";
            }
            academicYearsDataModel = new ListDataModel<AcademicYear>(listOfAcademicYears);
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public String viewAllButton() {
        try {
            listOfAcademicYears = new ArrayList<AcademicYear>();
            academicYearsDataModel = new ListDataModel<AcademicYear>(listOfAcademicYears);
            listOfAcademicYears = TisEjbSource.getCrudInstance().academicYearGetAll(false);
            if (listOfAcademicYears.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " AcademicYears");
                return "";
            }
            academicYearsDataModel = new ListDataModel<AcademicYear>(listOfAcademicYears);
//            listOfFreezAcademicYears.add(listOfAcademicYears.get(0));
        } catch (Exception e) {
        }
        return "";
    }
    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
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

    public List<AcademicYear> getListOfAcademicYears() {
        return listOfAcademicYears;
    }

    public List<AcademicYear> getListOfFreezAcademicYears() {
        return listOfFreezAcademicYears;
    }

    public void setListOfFreezAcademicYears(List<AcademicYear> listOfFreezAcademicYears) {
        this.listOfFreezAcademicYears = listOfFreezAcademicYears;
    }

    public void setListOfAcademicYears(List<AcademicYear> listOfAcademicYears) {
        this.listOfAcademicYears = listOfAcademicYears;
    }

    public boolean isSetAsCurrentAcademicYear() {
        return setAsCurrentAcademicYear;
    }

    public void setSetAsCurrentAcademicYear(boolean setAsCurrentAcademicYear) {
        this.setAsCurrentAcademicYear = setAsCurrentAcademicYear;
    }

    public DataModel<AcademicYear> getAcademicYearsDataModel() {
        return academicYearsDataModel;
    }

    public void setAcademicYearsDataModel(DataModel<AcademicYear> academicYearsDataModel) {
        this.academicYearsDataModel = academicYearsDataModel;
    }
    //</editor-fold>

    void appLogger(Exception e) {
        Logger.getLogger(AcademicYearsController.class.getName()).log(Level.SEVERE, null, e.getCause());
    }
}
