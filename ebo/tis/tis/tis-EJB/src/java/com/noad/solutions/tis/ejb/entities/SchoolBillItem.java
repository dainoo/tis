/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "school_bill_item")
@NamedQueries({
    @NamedQuery(name = "SchoolBillItem.findAll", query = "SELECT s FROM SchoolBillItem s")})
public class SchoolBillItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "school_bill_item_id")
    private String schoolBillItemId;
    @Size(max = 50)
    @Column(name = "school_bill_item")
    private String schoolBillItem;
    @Lob
    @Size(max = 65535)
    @Column(name = "school_bill_description")
    private String schoolBillDescription;
    @Size(max = 50)
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 50)
    @Column(name = "edited_by")
    private String editedBy;
    @Column(name = "edited_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date editedDate;
    @OneToMany(mappedBy = "schoolBillItem")
    private List<SchoolBill> schoolBillList;

    public SchoolBillItem() {
    }

    public SchoolBillItem(String schoolBillItemId) {
        this.schoolBillItemId = schoolBillItemId;
    }

    public String getSchoolBillItemId() {
        return schoolBillItemId;
    }

    public void setSchoolBillItemId(String schoolBillItemId) {
        this.schoolBillItemId = schoolBillItemId;
    }

    public String getSchoolBillItem() {
        return schoolBillItem;
    }

    public void setSchoolBillItem(String schoolBillItem) {
        this.schoolBillItem = schoolBillItem;
    }

    public String getSchoolBillDescription() {
        return schoolBillDescription;
    }

    public void setSchoolBillDescription(String schoolBillDescription) {
        this.schoolBillDescription = schoolBillDescription;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public Date getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(Date editedDate) {
        this.editedDate = editedDate;
    }

    public List<SchoolBill> getSchoolBillList() {
        return schoolBillList;
    }

    public void setSchoolBillList(List<SchoolBill> schoolBillList) {
        this.schoolBillList = schoolBillList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolBillItemId != null ? schoolBillItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolBillItem)) {
            return false;
        }
        SchoolBillItem other = (SchoolBillItem) object;
        if ((this.schoolBillItemId == null && other.schoolBillItemId != null) || (this.schoolBillItemId != null && !this.schoolBillItemId.equals(other.schoolBillItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.SchoolBillItem[ schoolBillItemId=" + schoolBillItemId + " ]";
    }
    
}
