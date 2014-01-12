/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.report.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Daud
 */
public class JSFMessages {

    public static String NO_DATA_TO_PRINT = "No Data To Print";

    public static void addMessage(FacesMessage.Severity severity, String message) {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        FacesMessage fm = new FacesMessage(severity, message, message);
        facesContext.addMessage(null, fm);
    }

    public static void warnMessage(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, message);
    }
}
