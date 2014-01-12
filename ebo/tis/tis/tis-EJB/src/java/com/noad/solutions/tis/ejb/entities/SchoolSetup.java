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
@Table(name = "school_setup")
@NamedQueries({
    @NamedQuery(name = "SchoolSetup.findAll", query = "SELECT s FROM SchoolSetup s")})
public class SchoolSetup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "school_setup_id")
    private String schoolSetupId;
    @Size(max = 255)
    @Column(name = "school_name")
    private String schoolName;
    @Size(max = 15)
    @Column(name = "contact")
    private String contact;
    @Size(max = 255)
    @Column(name = "school_motor")
    private String schoolMotor;
    @Size(max = 255)
    @Column(name = "school_logo")
    private String schoolLogo;
    @Size(max = 100)
    @Column(name = "postal_address")
    private String postalAddress;
    @Size(max = 100)
    @Column(name = "location")
    private String location;
    @Size(max = 100)
    @Column(name = "email_address")
    private String emailAddress;
    @Size(max = 8)
    @Column(name = "auto_generate_id")
    private String autoGenerateId;
    @Size(max = 8)
    @Column(name = "auto_generate_index_number")
    private String autoGenerateIndexNumber;
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

    public SchoolSetup() {
    }

    public SchoolSetup(String schoolSetupId) {
        this.schoolSetupId = schoolSetupId;
    }

    public String getSchoolSetupId() {
        return schoolSetupId;
    }

    public void setSchoolSetupId(String schoolSetupId) {
        this.schoolSetupId = schoolSetupId;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getSchoolMotor() {
        return schoolMotor;
    }

    public void setSchoolMotor(String schoolMotor) {
        this.schoolMotor = schoolMotor;
    }

    public String getSchoolLogo() {
        return schoolLogo;
    }

    public void setSchoolLogo(String schoolLogo) {
        this.schoolLogo = schoolLogo;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getAutoGenerateId() {
        return autoGenerateId;
    }

    public void setAutoGenerateId(String autoGenerateId) {
        this.autoGenerateId = autoGenerateId;
    }

    public String getAutoGenerateIndexNumber() {
        return autoGenerateIndexNumber;
    }

    public void setAutoGenerateIndexNumber(String autoGenerateIndexNumber) {
        this.autoGenerateIndexNumber = autoGenerateIndexNumber;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolSetupId != null ? schoolSetupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolSetup)) {
            return false;
        }
        SchoolSetup other = (SchoolSetup) object;
        if ((this.schoolSetupId == null && other.schoolSetupId != null) || (this.schoolSetupId != null && !this.schoolSetupId.equals(other.schoolSetupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.SchoolSetup[ schoolSetupId=" + schoolSetupId + " ]";
    }
    
}
