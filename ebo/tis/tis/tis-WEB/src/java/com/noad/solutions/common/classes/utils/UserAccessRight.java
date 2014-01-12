
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
public class UserAccessRight implements Serializable {

    public final static String display = "display:inline;";
    public final static String hide = "display:none;";
    private String readRight = UserAccessRight.hide;
    private String writeRight = UserAccessRight.hide;
    private String printRight = UserAccessRight.hide;
    private String accessRight = UserAccessRight.hide;

    public String getReadRight() {
        return readRight;
    }

    public void setReadRight(String readRight) {
        this.readRight = readRight;
    }

    public String getWriteRight() {
        return writeRight;
    }

    public void setWriteRight(String writeRight) {
        this.writeRight = writeRight;
    }

    public String getPrintRight() {
        return printRight;
    }

    public void setPrintRight(String printRight) {
        this.printRight = printRight;
    }

    public String getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(String accessRight) {
        this.accessRight = accessRight;
    }
}
