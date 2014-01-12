/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.CssStyles;
import com.noad.solutions.common.intefaces.utils.CommonMethodsInterface;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.ejb.entities.Staff;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.JSFIdGenerator;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.tis.ejb.entities.StaffQualification;
import com.noad.solutions.tis.ejb.entities.UserAccount;
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
 * @author Secretary
 */
@ManagedBean
@SessionScoped
public class StaffMemebersController extends CssStyles implements Serializable, CommonMethodsInterface {

    private Staff staff = new Staff();
    private StaffQualification staffQualification = new StaffQualification();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private SearchInputs searchInputs = new SearchInputs();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private List<Staff> listOfStaffs;
    private List<StaffQualification> listOfStaffQualifications = new ArrayList<StaffQualification>();
    private List<Staff> listOfFreezStaffs = new ArrayList<Staff>();
    private DataModel<Staff> staffsDataModel;
    private DataModel<StaffQualification> staffQualificationsDataModel = new ListDataModel<StaffQualification>();
    private String qualification = null;
    private Date dateAquired = null;
    private String computerName;
    

    public StaffMemebersController() {
        pageCommonInputs.showDataRecordsDisplay();
        computerName = JSFUtility.getHostAddressDetails(JSFUtility.MACHINE_NAME);

    }


    @Override
    public void newDataButton() {
        pageCommonInputs.showDataInputsDisplay();
        pageCommonInputs.setShowEditButtons(true);

    }

    public void addQualification() {
        try {
            System.out.println("INSIDE ADD");
            staffQualification.setQualification(qualification);
            staffQualification.setDateAcquired(dateAquired);
            listOfStaffQualifications.add(staffQualification);
            staffQualificationsDataModel = new ListDataModel<StaffQualification>(listOfStaffQualifications);
            staffQualification = new StaffQualification();
            qualification = null;
            dateAquired = null;
            System.out.println(" THE QUL " + listOfStaffQualifications.get(0).getQualification());
        } catch (Exception e) {
            appLogger(e);
        }
    }

    public void removeQualification() {
        try {
            staffQualification = staffQualificationsDataModel.getRowData();
            listOfStaffQualifications.remove(staffQualification);
            staffQualificationsDataModel = new ListDataModel<StaffQualification>(listOfStaffQualifications);
        } catch (Exception e) {
        }
    }

    @Override
    public String saveButton() {
        try {
            if (TisEjbSource.getValidationInstance().checkDuplicateStaff(staff)) {
                JSFMessages.warnMessage(staff.getSurname() + " " + staff.getOtherNames() + JSFMessages.DUPLICATE);
                return "";
            }
            UserAccount ua=new UserAccount();
            staff.setStaffId(JSFIdGenerator.generateId("staff", "ST"));
            ua.setUserAccountId(staff.getStaffId());
            staff.setDepartment(TisEjbSource.getCrudInstance().departmentFind(selectedItemHelper.getSelectedDepartment()));
            pageCommonInputs.setCheckIfSaved(TisEjbSource.getCrudInstance().staffCreate(staff));
            TisEjbSource.getCrudInstance().userAccountCreate(ua);
            if (pageCommonInputs.getCheckIfSaved() == null) {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_SAVE + staff.getSurname() + " " + staff.getOtherNames());
            } else {
                JSFMessages.infoMessage(staff.getSurname() + " " + staff.getOtherNames() + JSFMessages.SUCCESS_SAVE);
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
            staff = new Staff();
            searchInputs = new SearchInputs();
            selectedItemHelper = new SelectedItemHelper();

        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public String updateButton() {
        try {
            staff.setDepartment(TisEjbSource.getCrudInstance().departmentFind(selectedItemHelper.getSelectedDepartment()));
            pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().staffUpdate(staff));
            if (pageCommonInputs.isCheckIfUpdated()) {
                JSFMessages.infoMessage(staff.getSurname() + " " + staff.getOtherNames() + JSFMessages.SUCCESS_UPDATE);
                resetButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_UPDATE + staff.getSurname() + " " + staff.getOtherNames());
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public void deleteButton() {
        try {
            pageCommonInputs.setCheckIfDeleted(TisEjbSource.getCrudInstance().staffDelete(staff, false));
            if (pageCommonInputs.isCheckIfDeleted()) {
                JSFMessages.infoMessage(staff.getSurname() + " " + staff.getOtherNames() + JSFMessages.SUCCESS_DELETE);
                resetButton();
            } else {
                JSFMessages.errorMessage(JSFMessages.UNSUCCESS_DELETE + staff.getSurname() + " " + staff.getOtherNames());
            }
        } catch (Exception e) {
            appLogger(e);

        }
    }

    @Override
    public void cancelButton() {
        try {
            pageCommonInputs.showDataRecordsDisplay();
            staff = new Staff();
            staffQualification = new StaffQualification();
            selectedItemHelper = new SelectedItemHelper();
            listOfStaffQualifications = new ArrayList<StaffQualification>();
            staffQualificationsDataModel = new ListDataModel<StaffQualification>(listOfStaffQualifications);
        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public void resetDataTable() {
        try {
            listOfStaffs = new ArrayList<Staff>();
            staffsDataModel = new ListDataModel<Staff>();
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
            staff = staffsDataModel.getRowData();
            selectedItemHelper.setSelectedDepartment(staff.getDepartment().getDepartmentId());

        } catch (Exception e) {
            appLogger(e);
        }
    }

    @Override
    public String searchButton() {
        try {
            listOfStaffs = new ArrayList<Staff>();
            staffsDataModel = new ListDataModel<Staff>();
            listOfStaffs = TisEjbSource.getCrudInstance().staffFindByAttribute(searchInputs.getSearchParameter(), searchInputs.getSearchValue(), "STRING", false);
            if (listOfStaffs.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND);
                return "";
            }
            staffsDataModel = new ListDataModel<Staff>(listOfStaffs);
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }

    @Override
    public String viewAllButton() {
        try {
            listOfStaffs = new ArrayList<Staff>();
            staffsDataModel = new ListDataModel<Staff>(listOfStaffs);
            listOfStaffs = TisEjbSource.getCrudInstance().staffGetAll(false);
            if (listOfStaffs.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " Staffs");
                return "";
            }
            staffsDataModel = new ListDataModel<Staff>(listOfStaffs);
//            listOfFreezStaffs.add(listOfStaffs.get(0));
        } catch (Exception e) {
            appLogger(e);
        }
        return "";
    }
    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
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

    public StaffQualification getStaffQualification() {
        return staffQualification;
    }

    public void setStaffQualification(StaffQualification staffQualification) {
        this.staffQualification = staffQualification;
    }

    public List<StaffQualification> getListOfStaffQualifications() {
        return listOfStaffQualifications;
    }

    public void setListOfStaffQualifications(List<StaffQualification> listOfStaffQualifications) {
        this.listOfStaffQualifications = listOfStaffQualifications;
    }

    public DataModel<StaffQualification> getStaffQualificationsDataModel() {
        return staffQualificationsDataModel;
    }

    public void setStaffQualificationsDataModel(DataModel<StaffQualification> staffQualificationsDataModel) {
        this.staffQualificationsDataModel = staffQualificationsDataModel;
    }

    public void setSearchInputs(SearchInputs searchControls) {
        this.searchInputs = searchControls;
    }

    public List<Staff> getListOfStaffs() {
        return listOfStaffs;
    }

    public List<Staff> getListOfFreezStaffs() {
        return listOfFreezStaffs;
    }

    public void setListOfFreezStaffs(List<Staff> listOfFreezStaffs) {
        this.listOfFreezStaffs = listOfFreezStaffs;
    }

    public void setListOfStaffs(List<Staff> listOfStaffs) {
        this.listOfStaffs = listOfStaffs;
    }

    public DataModel<Staff> getStaffsDataModel() {
        return staffsDataModel;
    }

    public void setStaffsDataModel(DataModel<Staff> staffsDataModel) {
        this.staffsDataModel = staffsDataModel;
    }

    public Date getDateAquired() {
        return dateAquired;
    }

    public void setDateAquired(Date dateAquired) {
        this.dateAquired = dateAquired;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    //</editor-fold>

    void appLogger(Exception e) {
        Logger.getLogger(StaffMemebersController.class.getName()).log(Level.SEVERE, null, e.getCause());
    }
}
