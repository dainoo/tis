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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "program")
@NamedQueries({
    @NamedQuery(name = "Program.findAll", query = "SELECT p FROM Program p")})
public class Program implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "program_id")
    private String programId;
    @Size(max = 5)
    @Column(name = "prgram_code")
    private String prgramCode;
    @Size(max = 255)
    @Column(name = "program_name")
    private String programName;
    @Size(max = 10)
    @Column(name = "grading_system")
    private String gradingSystem;
    @Size(max = 10)
    @Column(name = "status")
    private String status;
    @Size(max = 20)
    @Column(name = "certificate_type")
    private String certificateType;
    @Size(max = 5)
    @Column(name = "duration")
    private String duration;
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
    @OneToMany(mappedBy = "program")
    private List<LecturerTeachingCourse> lecturerTeachingCourseList;
    @OneToMany(mappedBy = "program")
    private List<ProgramCourse> programCourseList;
    @OneToMany(mappedBy = "program")
    private List<Student> studentList;
    @JoinColumn(name = "department", referencedColumnName = "department_id")
    @ManyToOne
    private Department department;
    @OneToMany(mappedBy = "program")
    private List<SchoolBill> schoolBillList;

    public Program() {
    }

    public Program(String programId) {
        this.programId = programId;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getPrgramCode() {
        return prgramCode;
    }

    public void setPrgramCode(String prgramCode) {
        this.prgramCode = prgramCode;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getGradingSystem() {
        return gradingSystem;
    }

    public void setGradingSystem(String gradingSystem) {
        this.gradingSystem = gradingSystem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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

    public List<LecturerTeachingCourse> getLecturerTeachingCourseList() {
        return lecturerTeachingCourseList;
    }

    public void setLecturerTeachingCourseList(List<LecturerTeachingCourse> lecturerTeachingCourseList) {
        this.lecturerTeachingCourseList = lecturerTeachingCourseList;
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
        hash += (programId != null ? programId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Program)) {
            return false;
        }
        Program other = (Program) object;
        if ((this.programId == null && other.programId != null) || (this.programId != null && !this.programId.equals(other.programId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.Program[ programId=" + programId + " ]";
    }
    
}
