/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.CssStyles;
import com.noad.solutions.common.intefaces.utils.CommonMethodsInterface;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.ejb.entities.Department;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
public class DepartmentsController extends CssStyles implements Serializable, CommonMethodsInterface {

    private Department department = new Department();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private SearchInputs searchInputs = new SearchInputs();
    private List<Department> listOfDepartments;
    private List<Department> listOfFreezDepartments = new ArrayList<Department>();
    private DataModel<Department> departmentsDataModel;

    public DepartmentsController() {
        pageCommonInputs.showDataRecordsDisplay();
        pageCommonInputs.setShowDataInputPanel("display:none;");
        pageCommonInputs.setShowDataRecordsPanel("display:inline;");
    }

    @Override
    public void newDataButton() {
        pageCommonInputs.showDataInputsDisplay();
        pageCommonInputs.setShowEditButtons(true);

    }

    @Override
    public String saveButton() {
        try {
            System.out.println("THE DEPT NAME IS " + department.getDepartmentName());
            System.out.println("THE DEPT CONTACT IS " + department.getDepartmentContact());
            if (TisEjbSource.getValidationInstance().checkDuplicateDepartment(department)) {
                JSFMessages.warnMessage(department.getDepartmentName() + JSFMessages.DUPLICATE);
                return "";
            }
//            department.setDepartmentId(JSFIdGenerator.generateId("department", "DP"));
            department.setDepartmentId(UUID.randomUUID().toString().substring(10));
            pageCommonInputs.setCheckIfSaved(TisEjbSource.getCrudInstance().departmentCreate(department));
            if (pageCommonInputs.getCheckIfSaved() == null) {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_SAVE + department.getDepartmentName());
            } else {
                JSFMessages.infoMessage(department.getDepartmentName() + JSFMessages.SUCCESS_SAVE);
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
            department = new Department();
            searchInputs = new SearchInputs();
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public String updateButton() {
        try {
//             if (TisEjbSource.getValidationInstance().checkDuplicateDepartment(department)) {
//                JSFMessages.warnMessage(department.getDepartmentName() + JSFMessages.DUPLICATE);
//                return "";
//            }
            pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().departmentUpdate(department));
            if (pageCommonInputs.isCheckIfUpdated()) {
                JSFMessages.infoMessage(department.getDepartmentName() + JSFMessages.SUCCESS_UPDATE);
                resetButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_UPDATE + department.getDepartmentName());
            }
        } catch (Exception e) {
        }
        return "";
    }

    @Override
    public void deleteButton() {
        try {
            pageCommonInputs.setCheckIfDeleted(TisEjbSource.getCrudInstance().departmentDelete(department, false));
            if (pageCommonInputs.isCheckIfDeleted()) {
                JSFMessages.infoMessage(department.getDepartmentName() + JSFMessages.SUCCESS_DELETE);
                resetButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_DELETE + department.getDepartmentName());
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void cancelButton() {
        try {
            pageCommonInputs.showDataRecordsDisplay();
            department = new Department();
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public void resetDataTable() {
        try {
            listOfDepartments = new ArrayList<Department>();
            departmentsDataModel = new ListDataModel<Department>();
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
            department = departmentsDataModel.getRowData();
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public String searchButton() {
        try {
            listOfDepartments = new ArrayList<Department>();
            departmentsDataModel = new ListDataModel<Department>();
            listOfDepartments = TisEjbSource.getCrudInstance().departmentFindByAttribute(searchInputs.getSearchParameter(), searchInputs.getSearchValue(), "STRING", false);
            if (listOfDepartments.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND);
                return "";
            }
            departmentsDataModel = new ListDataModel<Department>(listOfDepartments);
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public String viewAllButton() {
        try {
            listOfDepartments = new ArrayList<Department>();
            departmentsDataModel = new ListDataModel<Department>(listOfDepartments);
            listOfDepartments = TisEjbSource.getCrudInstance().departmentGetAll(false);
            if (listOfDepartments.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " Departments");
                return "";
            }
            departmentsDataModel = new ListDataModel<Department>(listOfDepartments);
//            listOfFreezDepartments.add(listOfDepartments.get(0));
        } catch (Exception e) {
        }
        return "";
    }
    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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

    public void setSearchInputs(SearchInputs searchControls) {
        this.searchInputs = searchControls;
    }

    public List<Department> getListOfDepartments() {
        return listOfDepartments;
    }

    public List<Department> getListOfFreezDepartments() {
        return listOfFreezDepartments;
    }

    public void setListOfFreezDepartments(List<Department> listOfFreezDepartments) {
        this.listOfFreezDepartments = listOfFreezDepartments;
    }

    public void setListOfDepartments(List<Department> listOfDepartments) {
        this.listOfDepartments = listOfDepartments;
    }

    public DataModel<Department> getDepartmentsDataModel() {
        return departmentsDataModel;
    }

    public void setDepartmentsDataModel(DataModel<Department> departmentsDataModel) {
        this.departmentsDataModel = departmentsDataModel;
    }
    //</editor-fold>

    void appLogger(Exception e) {
        Logger.getLogger(DepartmentsController.class.getName()).log(Level.SEVERE, null, e.getCause());
    }
}
