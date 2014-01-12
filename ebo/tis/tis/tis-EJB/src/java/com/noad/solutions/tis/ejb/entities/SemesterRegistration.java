/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.ejb.entities;

import com.noad.solutions.tis.ejb.commons.Selected;
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
@Table(name = "semester_registration")
@NamedQueries({
    @NamedQuery(name = "SemesterRegistration.findAll", query = "SELECT s FROM SemesterRegistration s")})
public class SemesterRegistration extends Selected implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "semester_registration_id")
    private String semesterRegistrationId;
    @Size(max = 255)
    @Column(name = "courses")
    private String courses;
    @Size(max = 255)
    @Column(name = "uploaded_marks")
    private String uploadedMarks;
    @Column(name = "registered_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredDate;
    @Size(max = 10)
    @Column(name = "removed")
    private String removed;
    @JoinColumn(name = "academic_year", referencedColumnName = "academic_year_id")
    @ManyToOne
    private AcademicYear academicYear;
    @JoinColumn(name = "student", referencedColumnName = "student_id")
    @ManyToOne
    private Student student;
    @JoinColumn(name = "registered_by", referencedColumnName = "staff_id")
    @ManyToOne
    private Staff registeredBy;
    @JoinColumn(name = "academic_level", referencedColumnName = "academic_level_id")
    @ManyToOne
    private AcademicLevel academicLevel;

    public SemesterRegistration() {
    }

    public SemesterRegistration(String semesterRegistrationId) {
        this.semesterRegistrationId = semesterRegistrationId;
    }

    public String getSemesterRegistrationId() {
        return semesterRegistrationId;
    }

    public void setSemesterRegistrationId(String semesterRegistrationId) {
        this.semesterRegistrationId = semesterRegistrationId;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getUploadedMarks() {
        return uploadedMarks;
    }

    public void setUploadedMarks(String uploadedMarks) {
        this.uploadedMarks = uploadedMarks;
    }

    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Staff getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(Staff registeredBy) {
        this.registeredBy = registeredBy;
    }

    public AcademicLevel getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(AcademicLevel academicLevel) {
        this.academicLevel = academicLevel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (semesterRegistrationId != null ? semesterRegistrationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SemesterRegistration)) {
            return false;
        }
        SemesterRegistration other = (SemesterRegistration) object;
        if ((this.semesterRegistrationId == null && other.semesterRegistrationId != null) || (this.semesterRegistrationId != null && !this.semesterRegistrationId.equals(other.semesterRegistrationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.SemesterRegistration[ semesterRegistrationId=" + semesterRegistrationId + " ]";
    }
    
}
