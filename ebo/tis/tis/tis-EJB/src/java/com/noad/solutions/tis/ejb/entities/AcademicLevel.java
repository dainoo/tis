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
@Table(name = "academic_level")
@NamedQueries({
    @NamedQuery(name = "AcademicLevel.findAll", query = "SELECT a FROM AcademicLevel a")})
public class AcademicLevel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "academic_level_id")
    private String academicLevelId;
    @Size(max = 20)
    @Column(name = "academic_level")
    private String academicLevel;
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
    @OneToMany(mappedBy = "academicLevel")
    private List<StudentAccount> studentAccountList;
    @OneToMany(mappedBy = "academicLevel")
    private List<IncompleteCourse> incompleteCourseList;
    @OneToMany(mappedBy = "academicLevel")
    private List<StudentMark> studentMarkList;
    @OneToMany(mappedBy = "academicLevel")
    private List<ProgramCourse> programCourseList;
    @OneToMany(mappedBy = "currentLevel")
    private List<Student> studentList;
    @OneToMany(mappedBy = "academicLevel")
    private List<SchoolBill> schoolBillList;
    @OneToMany(mappedBy = "academicLevel")
    private List<SemesterRegistration> semesterRegistrationList;

    public AcademicLevel() {
    }

    public AcademicLevel(String academicLevelId) {
        this.academicLevelId = academicLevelId;
    }

    public String getAcademicLevelId() {
        return academicLevelId;
    }

    public void setAcademicLevelId(String academicLevelId) {
        this.academicLevelId = academicLevelId;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
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

    public List<StudentAccount> getStudentAccountList() {
        return studentAccountList;
    }

    public void setStudentAccountList(List<StudentAccount> studentAccountList) {
        this.studentAccountList = studentAccountList;
    }

    public List<IncompleteCourse> getIncompleteCourseList() {
        return incompleteCourseList;
    }

    public void setIncompleteCourseList(List<IncompleteCourse> incompleteCourseList) {
        this.incompleteCourseList = incompleteCourseList;
    }

    public List<StudentMark> getStudentMarkList() {
        return studentMarkList;
    }

    public void setStudentMarkList(List<StudentMark> studentMarkList) {
        this.studentMarkList = studentMarkList;
    }

    public List<ProgramCourse> getProgramCourseList() {
        return programCourseList;
    }

    public void setProgramCourseList(List<ProgramCourse> programCourseList) {
        this.programCourseList = programCourseList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<SchoolBill> getSchoolBillList() {
        return schoolBillList;
    }

    public void setSchoolBillList(List<SchoolBill> schoolBillList) {
        this.schoolBillList = schoolBillList;
    }

    public List<SemesterRegistration> getSemesterRegistrationList() {
        return semesterRegistrationList;
    }

    public void setSemesterRegistrationList(List<SemesterRegistration> semesterRegistrationList) {
        this.semesterRegistrationList = semesterRegistrationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (academicLevelId != null ? academicLevelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcademicLevel)) {
            return false;
        }
        AcademicLevel other = (AcademicLevel) object;
        if ((this.academicLevelId == null && other.academicLevelId != null) || (this.academicLevelId != null && !this.academicLevelId.equals(other.academicLevelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.AcademicLevel[ academicLevelId=" + academicLevelId + " ]";
    }
    
}
