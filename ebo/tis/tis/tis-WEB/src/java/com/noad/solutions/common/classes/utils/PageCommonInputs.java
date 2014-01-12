/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.classes.utils;

import java.io.Serializable;

/**
 *
 * @author daud
 */
public class PageCommonInputs implements Serializable {

    public void showDataInputsDisplay() {
        showDataInputPanel = "display:inline;";
        showDataRecordsPanel = "display:none;";
    }

    public void showDataRecordsDisplay() {
        showDataInputPanel = "display:none;";
        showDataRecordsPanel = "display:inline;";
    }
    public final String studentPicturePath = "http://localhost:8080/tisres/students_pics/";
    public final String resourcePicturePath = "http://localhost:8080/tisres/";
    public String weightedAverageHeader = "";
    public String showDataInputPanel = "";
    public String showDataRecordsPanel = "";
    public boolean disableComponent = true;
    public boolean DISABLE_MENU = false;
    public boolean checkIfUpdated = false;
    public boolean checkIfDeleted = false;
    public boolean checkAllData = false;
    private boolean selectAllData = false;
    private boolean readData = false;
    private boolean writeData = false;
    private boolean showEditButtons = true;
    public boolean showGradingType = true;
    public static final boolean FALSE = false;
    public static final boolean TRUE = true;
    public int activeIndex = 0;
    public int displayDataNubmer = 0;
    private String username = null;
    private String password = null;
    private String userRole = null;
    private String status = null;
    private String confirmedPassword = null;
    public String checkIfSaved = null;
    private String editNewTitle = null;

    public static boolean isFALSE() {
        return FALSE;
    }

    public static boolean isTRUE() {
        return TRUE;
    }

    public boolean isDisableComponent() {
        return disableComponent;
    }

    public boolean isShowGPACommons() {
        return showGradingType;
    }

    public String getWeightedAverageHeader() {
        return weightedAverageHeader;
    }

    public void setWeightedAverageHeader(String weightedAverageHeader) {
        this.weightedAverageHeader = weightedAverageHeader;
    }

    public boolean isShowGradingType() {
        return showGradingType;
    }

    public void setShowGradingType(boolean showGradingType) {
        this.showGradingType = showGradingType;
    }

    public void setDisableComponent(boolean disableComponent) {
        this.disableComponent = disableComponent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudentPicturePath() {
        return studentPicturePath;
    }

    public String getResourcePicturePath() {
        return resourcePicturePath;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public boolean isDISABLE_MENU() {
        return DISABLE_MENU;
    }

    public void setDISABLE_MENU(boolean DISABLE_MENU) {
        this.DISABLE_MENU = DISABLE_MENU;
    }

    public boolean isCheckIfUpdated() {
        return checkIfUpdated;
    }

    public void setCheckIfUpdated(boolean checkIfUpdated) {
        this.checkIfUpdated = checkIfUpdated;
    }

    public boolean isCheckIfDeleted() {
        return checkIfDeleted;
    }

    public void setCheckIfDeleted(boolean checkIfDeleted) {
        this.checkIfDeleted = checkIfDeleted;
    }

    public String getShowDataInputPanel() {
        return showDataInputPanel;
    }

    public void setShowDataInputPanel(String showDataInputPanel) {
        this.showDataInputPanel = showDataInputPanel;
    }

    public String getShowDataRecordsPanel() {
        return showDataRecordsPanel;
    }

    public void setShowDataRecordsPanel(String showDataRecords) {
        this.showDataRecordsPanel = showDataRecords;
    }

    public boolean isCheckAllData() {
        return checkAllData;
    }

    public void setCheckAllData(boolean checkAllData) {
        this.checkAllData = checkAllData;
    }

    public boolean isSelectAllData() {
        return selectAllData;
    }

    public void setSelectAllData(boolean selectAllData) {
        this.selectAllData = selectAllData;
    }

    public boolean isReadData() {
        return readData;
    }

    public void setReadData(boolean readData) {
        this.readData = readData;
    }

    public boolean isWriteData() {
        return writeData;
    }

    public void setWriteData(boolean writeData) {
        this.writeData = writeData;
    }

    public boolean isShowEditButtons() {
        return showEditButtons;
    }

    public void setShowEditButtons(boolean showEditButtons) {
        this.showEditButtons = showEditButtons;
    }

    public int getActiveIndex() {
        return activeIndex;
    }

    public void setActiveIndex(int activeIndex) {
        this.activeIndex = activeIndex;
    }

    public int getDisplayDataNubmer() {
        return displayDataNubmer;
    }

    public void setDisplayDataNubmer(int displayDataNubmer) {
        this.displayDataNubmer = displayDataNubmer;
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

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getCheckIfSaved() {
        return checkIfSaved;
    }

    public void setCheckIfSaved(String checkIfSaved) {
        this.checkIfSaved = checkIfSaved;
    }

    public String getEditNewTitle() {
        return editNewTitle;
    }

    public void setEditNewTitle(String editNewTitle) {
        this.editNewTitle = editNewTitle;
    }
}
