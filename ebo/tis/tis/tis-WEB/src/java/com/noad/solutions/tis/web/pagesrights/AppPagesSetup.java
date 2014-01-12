/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.pagesrights;

/**
 *
 * @author Daud
 */
public class AppPagesSetup {

    private boolean selected = false;
    private String menuName = null;
    private String submenuName = null;
    private String pageName = null;
    private int orderLevel = 0;
    private boolean writeLevel = false;
    private boolean readLevel = false;
    private boolean printLevel = false;

    public AppPagesSetup(boolean selected, int orderLevel, String menuName, String submenuName, String pageName) {
        this.selected = false;
        this.orderLevel = orderLevel;
        this.menuName = menuName;
        this.pageName = pageName;
        this.submenuName = submenuName;
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isPrintLevel() {
        return printLevel;
    }

    public void setPrintLevel(boolean printLevel) {
        this.printLevel = printLevel;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getSubmenuName() {
        return submenuName;
    }

    public void setSubmenuName(String submenuName) {
        this.submenuName = submenuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public boolean isWriteLevel() {
        return writeLevel;
    }

    public void setWriteLevel(boolean writeLevel) {
        this.writeLevel = writeLevel;
    }

    public boolean isReadLevel() {
        return readLevel;
    }

    public void setReadLevel(boolean readLevel) {
        this.readLevel = readLevel;
    }

    public int getOrderLevel() {
        return orderLevel;
    }

    public void setOrderLevel(int orderLevel) {
        this.orderLevel = orderLevel;
    }
    //</editor-fold>
}
