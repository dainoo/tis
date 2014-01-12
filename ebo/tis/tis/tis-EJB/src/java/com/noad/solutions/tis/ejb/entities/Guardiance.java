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
@Table(name = "guardiance")
@NamedQueries({
    @NamedQuery(name = "Guardiance.findAll", query = "SELECT g FROM Guardiance g")})
public class Guardiance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "guardiance_id")
    private String guardianceId;
    @Size(max = 20)
    @Column(name = "title")
    private String title;
    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @Size(max = 50)
    @Column(name = "relationship")
    private String relationship;
    @Size(max = 15)
    @Column(name = "contact")
    private String contact;
    @Size(max = 50)
    @Column(name = "email_address")
    private String emailAddress;
    @Size(max = 50)
    @Column(name = "postal_address")
    private String postalAddress;
    @Size(max = 50)
    @Column(name = "occupation")
    private String occupation;
    @Size(max = 30)
    @Column(name = "religion")
    private String religion;
    @Size(max = 10)
    @Column(name = "removed")
    private String removed;
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
    @OneToMany(mappedBy = "guardiance")
    private List<Student> studentList;

    public Guardiance() {
    }

    public Guardiance(String guardianceId) {
        this.guardianceId = guardianceId;
    }

    public String getGuardianceId() {
        return guardianceId;
    }

    public void setGuardianceId(String guardianceId) {
        this.guardianceId = guardianceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
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

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (guardianceId != null ? guardianceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Guardiance)) {
            return false;
        }
        Guardiance other = (Guardiance) object;
        if ((this.guardianceId == null && other.guardianceId != null) || (this.guardianceId != null && !this.guardianceId.equals(other.guardianceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.Guardiance[ guardianceId=" + guardianceId + " ]";
    }
    
}
