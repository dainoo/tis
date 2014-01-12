/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.CssStyles;
import com.noad.solutions.common.intefaces.utils.CommonMethodsInterface;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.JSFIdGenerator;
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
 * @author Secretary
 */
@ManagedBean
@SessionScoped
public class ProgramsController extends CssStyles implements Serializable, CommonMethodsInterface {

    private Program program = new Program();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private SearchInputs searchInputs = new SearchInputs();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private List<Program> listOfPrograms;
    private List<Program> listOfFreezPrograms = new ArrayList<Program>();
    private DataModel<Program> programsDataModel;

    public ProgramsController() {
        pageCommonInputs.showDataRecordsDisplay();
       
    }

    @Override
    public void newDataButton() {
        pageCommonInputs.showDataInputsDisplay();
        pageCommonInputs.setShowEditButtons(true);

    }

    @Override
    public String saveButton() {
        try {
            if (TisEjbSource.getValidationInstance().checkDuplicateProgram(program)) {
                JSFMessages.warnMessage(program.getProgramName() + JSFMessages.DUPLICATE);
                return "";
            }
            program.setDepartment(TisEjbSource.getCrudInstance().departmentFind(selectedItemHelper.getSelectedDepartment()));
            program.setProgramId(JSFIdGenerator.generateId("program", "DP"));
            pageCommonInputs.setCheckIfSaved(TisEjbSource.getCrudInstance().programCreate(program));
            if (pageCommonInputs.getCheckIfSaved() == null) {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_SAVE + program.getProgramName());
            } else {
                JSFMessages.infoMessage(program.getProgramName() + JSFMessages.SUCCESS_SAVE);
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
            program = new Program();
            searchInputs = new SearchInputs();
            selectedItemHelper = new SelectedItemHelper();

        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public String updateButton() {
        try {
            program.setDepartment(TisEjbSource.getCrudInstance().departmentFind(selectedItemHelper.getSelectedDepartment()));
            pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().programUpdate(program));
            if (pageCommonInputs.isCheckIfUpdated()) {
                JSFMessages.infoMessage(program.getProgramName() + JSFMessages.SUCCESS_UPDATE);
                resetButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_UPDATE + program.getProgramName());
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public void deleteButton() {
        try {
            pageCommonInputs.setCheckIfDeleted(TisEjbSource.getCrudInstance().programDelete(program, false));
            if (pageCommonInputs.isCheckIfDeleted()) {
                JSFMessages.infoMessage(program.getProgramName() + JSFMessages.SUCCESS_DELETE);
                resetButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_DELETE + program.getProgramName());
            }
        } catch (Exception e) {
            appLogger(e);

        }
    }

    @Override
    public void cancelButton() {
        try {
            pageCommonInputs.showDataRecordsDisplay();
            program = new Program();
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public void resetDataTable() {
        try {
            listOfPrograms = new ArrayList<Program>();
            programsDataModel = new ListDataModel<Program>();
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
            program = programsDataModel.getRowData();
            selectedItemHelper.setSelectedDepartment(program.getDepartment().getDepartmentId());
            selectedItemHelper.setSelectedDepartment(TisEjbSource.getCrudInstance().departmentFind(program.getDepartment().getDepartmentId()).getDepartmentId());
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public String searchButton() {
        try {
            listOfPrograms = new ArrayList<Program>();
            programsDataModel = new ListDataModel<Program>();
            listOfPrograms = TisEjbSource.getCrudInstance().programFindByAttribute(searchInputs.getSearchParameter(), searchInputs.getSearchValue(), "STRING", false);
            if (listOfPrograms.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND);
                return "";
            }
            programsDataModel = new ListDataModel<Program>(listOfPrograms);
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public String viewAllButton() {
        try {
            listOfPrograms = new ArrayList<Program>();
            programsDataModel = new ListDataModel<Program>(listOfPrograms);
            listOfPrograms = TisEjbSource.getCrudInstance().programGetAll(false);
            if (listOfPrograms.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " Programs");
                return "";
            }
            programsDataModel = new ListDataModel<Program>(listOfPrograms);
//            listOfFreezPrograms.add(listOfPrograms.get(0));
        } catch (Exception e) {
        }
        return "";
    }
    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
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

    public List<Program> getListOfPrograms() {
        return listOfPrograms;
    }

    public List<Program> getListOfFreezPrograms() {
        return listOfFreezPrograms;
    }

    public void setListOfFreezPrograms(List<Program> listOfFreezPrograms) {
        this.listOfFreezPrograms = listOfFreezPrograms;
    }

    public void setListOfPrograms(List<Program> listOfPrograms) {
        this.listOfPrograms = listOfPrograms;
    }

    public DataModel<Program> getProgramsDataModel() {
        return programsDataModel;
    }

    public void setProgramsDataModel(DataModel<Program> programsDataModel) {
        this.programsDataModel = programsDataModel;
    }
    //</editor-fold>

    void appLogger(Exception e) {
        Logger.getLogger(ProgramsController.class.getName()).log(Level.SEVERE, null, e.getCause());
    }
}
