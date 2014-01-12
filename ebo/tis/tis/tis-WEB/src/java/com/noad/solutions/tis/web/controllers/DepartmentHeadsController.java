/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.tis.ejb.entities.Department;
import com.noad.solutions.tis.ejb.entities.Staff;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author Dawoode
 */
@Named(value = "departmentHeadsController")
@SessionScoped
public class DepartmentHeadsController implements Serializable {

    private Department department = new Department();
    private List<Department> listOfDepartments = new ArrayList<Department>();
    private DataModel<Department> departmentsDataModel;
    private SelectItem[] memebersOfDepartmentSelectItems = null;
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private SearchInputs searchInputs = new SearchInputs();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private SelectItem[] staffSelectItems;

    public DepartmentHeadsController() {

        preLoadDepartmentHeads();
    }

    private void preLoadDepartmentHeads() {
        try {
            listOfDepartments = new ArrayList<Department>();
            departmentsDataModel = new ListDataModel<Department>(listOfDepartments);
            listOfDepartments = TisEjbSource.getCrudInstance().departmentGetAll(false);
            departmentsDataModel = new ListDataModel<Department>(listOfDepartments);

        } catch (Exception e) {
            appLogger(e);
            e.printStackTrace();
        }


    }

    public SelectItem[] getStaffSelectItems() {

//        List<Staff> listOfStaffs = TisEjbSource.getCrudInstance().staffGetAll(false);
//        staffSelectItems = new SelectItem[listOfStaffs.size() + 1];
//        staffSelectItems[0] = new SelectItem("", "PLEASE SELECT STAFF");
//        int counter = 1;
//        for (Staff eachTitle : listOfStaffs) {
//            staffSelectItems[counter] = new SelectItem(eachTitle.getStaffId(), eachTitle.getSurname());
//            counter++;
//        }

        return staffSelectItems;
    }

    public void departmentMembersListener() {
        try {
            department = TisEjbSource.getCrudInstance().departmentFind(selectedItemHelper.getSelectedDepartment());
            System.out.println("THE SELECTD DEPT IS " + department.getDepartmentName());
            List<Staff> listOfStaffs = TisEjbSource.getCrudInstance().staffFindByAttribute("department.departmentName", department.getDepartmentName(), "STRING", false);
            System.out.println("THE SIZE OF DEPT IS " + listOfStaffs.size());
            memebersOfDepartmentSelectItems = new SelectItem[listOfStaffs.size() + 1];
            memebersOfDepartmentSelectItems[0] = new SelectItem("", "PLEASE SELECT HEAD OF DEPT.");
            int counter = 1;
            for (Staff eachMemeber : listOfStaffs) {

                memebersOfDepartmentSelectItems[counter] = new SelectItem(eachMemeber.getStaffId(), eachMemeber.getSurname());

                System.out.println("THE STAFF ID IS " + memebersOfDepartmentSelectItems.length);
                counter++;
            }



        } catch (Exception e) {
            appLogger(e);
        }
    }

    public String searchButton() {
        try {
            listOfDepartments = new ArrayList<Department>();
            departmentsDataModel = new ListDataModel<Department>(listOfDepartments);
            listOfDepartments = TisEjbSource.getCrudInstance().departmentFindByAttribute(searchInputs.getSearchParameter(), searchInputs.getSearchValue(), "STRING", false);
            if (listOfDepartments.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " Department");
                return "";
            }
            departmentsDataModel=new ListDataModel<Department>(listOfDepartments);
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    public void assignHOD() {

        System.out.println("THE SELECTED STAFF ID IS >>>>>>>>>>>> " + selectedItemHelper.getSelectedStaff());
        try {
            Staff staff = new Staff();
            staff = TisEjbSource.getCrudInstance().staffFind(selectedItemHelper.getSelectedStaff());
            department = TisEjbSource.getCrudInstance().departmentFind(selectedItemHelper.getSelectedDepartment());
            department.setHeadOfDept(staff);
            pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().departmentUpdate(department));
            String hod = department.getHeadOfDept().getTitle() + " " + department.getHeadOfDept().getSurname() + " " + department.getHeadOfDept().getOtherNames();
            if (pageCommonInputs.isCheckIfUpdated()) {
                JSFMessages.infoMessage("HOD Of " + department.getDepartmentName() + " Has Been Assigned To " + hod);
                preLoadDepartmentHeads();
            } else {
                JSFMessages.errorMessage("Failed To Assign HOD To " + hod);
            }
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void refreshButton() {
        try {
            preLoadDepartmentHeads();
        } catch (Exception e) {
            appLogger(e);
        }
    }

    private void printOptions() {
        try {
        } catch (Exception e) {
            appLogger(e);
        }
    }

    static void appLogger(Exception e) {
        e.printStackTrace();
        Logger.getLogger(DepartmentHeadsController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTES">
    public DataModel<Department> getListOfDepartmentsDataModel() {
        return departmentsDataModel;
    }

    public Department getDepartment() {
        return department;
    }

    public DataModel<Department> getDepartmentsDataModel() {
        return departmentsDataModel;
    }

    public void setDepartmentsDataModel(DataModel<Department> departmentsDataModel) {
        this.departmentsDataModel = departmentsDataModel;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public SelectedItemHelper getSelectedItemHelper() {
        return selectedItemHelper;
    }

    public void setSelectedItemHelper(SelectedItemHelper selectedItemHelper) {
        this.selectedItemHelper = selectedItemHelper;
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

    public void setStaffSelectItems(SelectItem[] staffSelectItems) {
        this.staffSelectItems = staffSelectItems;
    }

    public List<Department> getListOfDepartments() {
        return listOfDepartments;
    }

    public void setListOfDepartments(List<Department> listOfDepartments) {
        this.listOfDepartments = listOfDepartments;
    }

    public SelectItem[] getMemebersOfDepartmentSelectItems() {
        return memebersOfDepartmentSelectItems;
    }

    public void setMemebersOfDepartmentSelectItems(SelectItem[] memebersOfDepartmentSelectItems) {
        this.memebersOfDepartmentSelectItems = memebersOfDepartmentSelectItems;
    }
    //</editor-fold>
}
