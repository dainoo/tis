/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.ejb.entities;

import com.noad.solutions.tis.ejb.commons.UserRight;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Daud
 */
@Entity
@Table(name = "ebo")
@NamedQueries({
    @NamedQuery(name = "Ebo.findAll", query = "SELECT e FROM Ebo e")})
public class Ebo extends UserRight implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ebo_id")
    private String eboId;
    @Size(max = 50)
    @Column(name = "main_menu")
    private String mainMenu;
    @Size(max = 50)
    @Column(name = "submenu")
    private String submenu;
    @Size(max = 50)
    @Column(name = "page_name")
    private String pageName;

    public Ebo() {
    }

    public Ebo(String eboId) {
        this.eboId = eboId;
    }

    public String getEboId() {
        return eboId;
    }

    public void setEboId(String eboId) {
        this.eboId = eboId;
    }

    public String getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(String mainMenu) {
        this.mainMenu = mainMenu;
    }

    public String getSubmenu() {
        return submenu;
    }

    public void setSubmenu(String submenu) {
        this.submenu = submenu;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eboId != null ? eboId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ebo)) {
            return false;
        }
        Ebo other = (Ebo) object;
        if ((this.eboId == null && other.eboId != null) || (this.eboId != null && !this.eboId.equals(other.eboId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.Ebo[ eboId=" + eboId + " ]";
    }
    
}
