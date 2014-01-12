/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Daud
 */
@Entity
@Table(name = "access_page")
@NamedQueries({
    @NamedQuery(name = "AccessPage.findAll", query = "SELECT a FROM AccessPage a")})
public class AccessPage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "access_page_id")
    private String accessPageId;
    @Size(max = 50)
    @Column(name = "user_account")
    private String userAccount;
    @Size(max = 50)
    @Column(name = "user_menu")
    private String userMenu;
    @Size(max = 50)
    @Column(name = "user_submenu")
    private String userSubmenu;
    @Size(max = 50)
    @Column(name = "page_name")
    private String pageName;
    @Column(name = "order_level")
    private Integer orderLevel;
    @Column(name = "read_level")
    private Boolean readLevel;
    @Column(name = "selected")
    private Boolean selected;
    @Column(name = "write_level")
    private Boolean writeLevel;
    @Column(name = "print_level")
    private Boolean printLevel;
    @Size(max = 50)
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    public AccessPage() {
    }

    public AccessPage(String accessPageId) {
        this.accessPageId = accessPageId;
    }

    public String getAccessPageId() {
        return accessPageId;
    }

    public void setAccessPageId(String accessPageId) {
        this.accessPageId = accessPageId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserMenu() {
        return userMenu;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public void setUserMenu(String userMenu) {
        this.userMenu = userMenu;
    }

    public String getUserSubmenu() {
        return userSubmenu;
    }

    public void setUserSubmenu(String userSubmenu) {
        this.userSubmenu = userSubmenu;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public Integer getOrderLevel() {
        return orderLevel;
    }

    public void setOrderLevel(Integer orderLevel) {
        this.orderLevel = orderLevel;
    }

    public Boolean getReadLevel() {
        return readLevel;
    }

    public void setReadLevel(Boolean readLevel) {
        this.readLevel = readLevel;
    }

    public Boolean getWriteLevel() {
        return writeLevel;
    }

    public void setWriteLevel(Boolean writeLevel) {
        this.writeLevel = writeLevel;
    }

    public Boolean getPrintLevel() {
        return printLevel;
    }

    public void setPrintLevel(Boolean printLevel) {
        this.printLevel = printLevel;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accessPageId != null ? accessPageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccessPage)) {
            return false;
        }
        AccessPage other = (AccessPage) object;
        if ((this.accessPageId == null && other.accessPageId != null) || (this.accessPageId != null && !this.accessPageId.equals(other.accessPageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.AccessPage[ accessPageId=" + accessPageId + " ]";
    }
    
}
