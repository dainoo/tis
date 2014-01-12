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
@Table(name = "school_bill")
@NamedQueries({
    @NamedQuery(name = "SchoolBill.findAll", query = "SELECT s FROM SchoolBill s")})
public class SchoolBill implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "school_bill_id")
    private String schoolBillId;
    @Size(max = 50)
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JoinColumn(name = "academic_year", referencedColumnName = "academic_year_id")
    @ManyToOne
    private AcademicYear academicYear;
    @JoinColumn(name = "program", referencedColumnName = "program_id")
    @ManyToOne
    private Program program;
    @JoinColumn(name = "academic_level", referencedColumnName = "academic_level_id")
    @ManyToOne
    private AcademicLevel academicLevel;
    @JoinColumn(name = "school_bill_item", referencedColumnName = "school_bill_item_id")
    @ManyToOne
    private SchoolBillItem schoolBillItem;
    @JoinColumn(name = "school_bill_category", referencedColumnName = "school_bill_category_id")
    @ManyToOne
    private SchoolBillCategory schoolBillCategory;

    public SchoolBill() {
    }

    public SchoolBill(String schoolBillId) {
        this.schoolBillId = schoolBillId;
    }

    public String getSchoolBillId() {
        return schoolBillId;
    }

    public void setSchoolBillId(String schoolBillId) {
        this.schoolBillId = schoolBillId;
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

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public AcademicLevel getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(AcademicLevel academicLevel) {
        this.academicLevel = academicLevel;
    }

    public SchoolBillItem getSchoolBillItem() {
        return schoolBillItem;
    }

    public void setSchoolBillItem(SchoolBillItem schoolBillItem) {
        this.schoolBillItem = schoolBillItem;
    }

    public SchoolBillCategory getSchoolBillCategory() {
        return schoolBillCategory;
    }

    public void setSchoolBillCategory(SchoolBillCategory schoolBillCategory) {
        this.schoolBillCategory = schoolBillCategory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (schoolBillId != null ? schoolBillId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SchoolBill)) {
            return false;
        }
        SchoolBill other = (SchoolBill) object;
        if ((this.schoolBillId == null && other.schoolBillId != null) || (this.schoolBillId != null && !this.schoolBillId.equals(other.schoolBillId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.SchoolBill[ schoolBillId=" + schoolBillId + " ]";
    }
    
}
