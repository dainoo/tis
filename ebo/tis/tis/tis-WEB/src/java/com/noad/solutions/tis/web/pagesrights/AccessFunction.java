/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.pagesrights;

/**
 *
 * @author Daud
 */
public class AccessFunction {

    private boolean write = false;
    private boolean read = false;
    private boolean print = false;
    private boolean access = false;
    private String cssReadRight = "none";
    private String cssWriteRighte = "none";
    private String cssPrintRight = "none";
    private String cssAccessRight = "none";

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public boolean isWrite() {
        return write;
    }
    
    public void setWrite(boolean write) {
        this.write = write;
    }
    
    public boolean isRead() {
        return read;
    }
    
    public void setRead(boolean read) {
        this.read = read;
    }
    
    public boolean isPrint() {
        return print;
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
    
    public void setPrint(boolean print) {
        this.print = print;
    }
    
    public boolean isAccess() {
        return access;
    }
    
    public void setAccess(boolean access) {
        this.access = access;
    }
    //</editor-fold>
}
