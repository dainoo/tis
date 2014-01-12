/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.ejb.commons;

/**
 *
 * @author Daud
 */
public class UserRight {

    private boolean selected = false;
    private boolean writeRight = false;
    private boolean readRight = false;
    private boolean printRight = false;
    private boolean accessRight = false;
    private String cssReadRight = "none";
    private String cssWriteRighte = "none";
    private String cssPrintRight = "none";
    private String cssAccessRight = "none";

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public boolean isWriteRight() {
        return writeRight;
    }

    public void setWriteRight(boolean writeRight) {
        this.writeRight = writeRight;
    }

    public boolean isReadRight() {
        return readRight;
    }

    public void setReadRight(boolean readRight) {
        this.readRight = readRight;
    }

    public boolean isAccessRight() {
        return accessRight;
    }

    public void setAccessRight(boolean accessRight) {
        this.accessRight = accessRight;
    }

    public boolean isPrintRight() {
        return printRight;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setPrintRight(boolean printRight) {
        this.printRight = printRight;
    }

    public String getCssReadRight() {
        return cssReadRight;
    }

    public void setCssReadRight(String cssReadRight) {
        this.cssReadRight = cssReadRight;
    }

    public String getCssWriteRighte() {
        return cssWriteRighte;
    }

    public void setCssWriteRighte(String cssWriteRighte) {
        this.cssWriteRighte = cssWriteRighte;
    }

    public String getCssPrintRight() {
        return cssPrintRight;
    }

    public void setCssPrintRight(String cssPrintRight) {
        this.cssPrintRight = cssPrintRight;
    }

    public String getCssAccessRight() {
        return cssAccessRight;
    }

    public void setCssAccessRight(String cssAccessRight) {
        this.cssAccessRight = cssAccessRight;
    }
    //</editor-fold>
}
