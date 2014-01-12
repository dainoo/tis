/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.jsf.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Daud
 */
public class JSFMessages {

    public static final String SUCCESS_SAVE = " 's details has been saved successfully";
    public static final String UNSUCCESS_SAVE = " Failed to save details of ";
    public static final String SUCCESS_UPDATE = " 's details has been edited successfully";
    public static final String UNSUCCESS_UPDATE = "Failed to edit details of ";
    public static final String SUCCESS_DELETE = " 's details has been deleted successfully";
    public static final String UNSUCCESS_DELETE = " Failed to delete details of ";
    public static final String WRONG_USERNAME_OR_PASSWORD = "Incorrect username or password";
    public static final String EMPTY_USERNAME_OR_PASSWORD = "Please enter username or password ";
    public static final String EMPTY_USERNAME = "Please enter username";
    public static final String CONFIRM_PASSWORD = "New Password and Confirm password Do Not Match";
    public static final String NO_DATA_FOUND = "No Records Found";
    public static String NO_DATA_TO_PRINT = "No Data To Print";
    public static String DUPLICATE = " Already Exist : Duplicate Not allowed";
    public static String NO_CHURCH_MEMBER = "No Church Member Found With ID ";

    public static void addMessage(FacesMessage.Severity severity, String message) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(severity, message, message);
        facesContext.addMessage(null, fm);
    }

    public static void warnMessage(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, message);
    }

    public static void infoMessage(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, message);
    }

    public static void errorMessage(String message) {
        addMessage(FacesMessage.SEVERITY_ERROR, message);
    }

    public static void fatalMessage(String message) {
        addMessage(FacesMessage.SEVERITY_FATAL, message);
    }
}
