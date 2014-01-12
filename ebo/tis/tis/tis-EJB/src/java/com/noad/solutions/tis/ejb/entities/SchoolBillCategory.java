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
@Table(name = "school_bill_category")
@NamedQueries({
    @NamedQuery(name = "SchoolBillCategory.findAll", query = "SELECT s FROM SchoolBillCategory s")})
public class SchoolBillCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "school_bill_category_id")
    private String schoolBillCategoryId;
    @Lob
    @Column(name = "school_bill_category")
    private byte[] schoolBillCategory;
    @Size(max = 5)
    @Column(name = "priority")
    private String priority;
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
    @OneToMany(mappedBy = "schoolBillCategory")
    private List<StudentAccount> studentAccountList;
    @OneToMany(mappedBy = "schoolBillCategory")
    private List<SchoolBill> schoolBillList;

    public SchoolBillCategory() {
    }

    public SchoolBillCategory(String schoolBillCategoryId) {
        this.schoolBillCategoryId = schoolBillCategoryId;
    }

    public String getSchoolBillCategoryId() {
        return schoolBillCategoryId;
    }

    public void setSchoolBillCategoryId(String schoolBillCategoryId) {
        this.schoolBillCategoryId = schoolBillCategoryId;
    }

    public byte[] getSchoolBillCategory() {
        return schoolBillCategory;
    }

    public void setSchoolBillCategory(byte[] schoolBillCategory) {
        this.schoolBillCategory = schoolBillCategory;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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

    public List<StudentAccount> getStudentAccountList() {
        return studentAccountList;
    }

    public void setStudentAccountList(List<StudentAccount> studentAccountList) {
        this.studentAccountList = studentAccountList;
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
        hash += (schoolBillCategoryId != null ? schoolBillCategoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolBillCategory)) {
            return false;
        }
        SchoolBillCategory other = (SchoolBillCategory) object;
        if ((this.schoolBillCategoryId == null && other.schoolBillCategoryId != null) || (this.schoolBillCategoryId != null && !this.schoolBillCategoryId.equals(other.schoolBillCategoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.SchoolBillCategory[ schoolBillCategoryId=" + schoolBillCategoryId + " ]";
    }
    
}
