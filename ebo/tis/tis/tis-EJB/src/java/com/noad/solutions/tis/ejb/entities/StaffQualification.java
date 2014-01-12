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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "staff_qualification")
@NamedQueries({
    @NamedQuery(name = "StaffQualification.findAll", query = "SELECT s FROM StaffQualification s")})
public class StaffQualification implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "staff_qualification_id")
    private String staffQualificationId;
    @Size(max = 255)
    @Column(name = "qualification")
    private String qualification;
    @Column(name = "date_acquired")
    @Temporal(TemporalType.DATE)
    private Date dateAcquired;
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
    @Size(max = 10)
    @Column(name = "removed")
    private String removed;
    @JoinColumn(name = "staff", referencedColumnName = "staff_id")
    @ManyToOne
    private Staff staff;

    public StaffQualification() {
    }

    public StaffQualification(String staffQualificationId) {
        this.staffQualificationId = staffQualificationId;
    }

    public String getStaffQualificationId() {
        return staffQualificationId;
    }

    public void setStaffQualificationId(String staffQualificationId) {
        this.staffQualificationId = staffQualificationId;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public Date getDateAcquired() {
        return dateAcquired;
    }

    public void setDateAcquired(Date dateAcquired) {
        this.dateAcquired = dateAcquired;
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

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (staffQualificationId != null ? staffQualificationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StaffQualification)) {
            return false;
        }
        StaffQualification other = (StaffQualification) object;
        if ((this.staffQualificationId == null && other.staffQualificationId != null) || (this.staffQualificationId != null && !this.staffQualificationId.equals(other.staffQualificationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.StaffQualification[ staffQualificationId=" + staffQualificationId + " ]";
    }
    
}
