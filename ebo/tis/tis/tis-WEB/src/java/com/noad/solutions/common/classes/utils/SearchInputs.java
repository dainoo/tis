/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.classes.utils;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Daud
 */
public class SearchInputs implements Serializable {

    private String searchParameter = null;
    private String searchValue = null;
    private Date startDate = null;
    private Date endDate = null;

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public String getSearchParameter() {
        return searchParameter;
    }
    
    public void setSearchParameter(String searchParameter) {
        this.searchParameter = searchParameter;
    }
    
    public String getSearchValue() {
        return searchValue;
    }
    
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    //</editor-fold>
    
}
