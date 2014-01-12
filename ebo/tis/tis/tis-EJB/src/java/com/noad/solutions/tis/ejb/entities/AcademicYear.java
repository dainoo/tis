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
@Table(name = "academic_year")
@NamedQueries({
    @NamedQuery(name = "AcademicYear.findAll", query = "SELECT a FROM AcademicYear a")})
public class AcademicYear implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "academic_year_id")
    private String academicYearId;
    @Size(max = 10)
    @Column(name = "academic_year")
    private String academicYear;
    @Size(max = 50)
    @Column(name = "semester")
    private String semester;
    @Size(max = 5)
    @Column(name = "current_semester")
    private String currentSemester;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
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
    @OneToMany(mappedBy = "academicYear")
    private List<StudentAccount> studentAccountList;
    @OneToMany(mappedBy = "academicYear")
    private List<IncompleteCourse> incompleteCourseList;
    @OneToMany(mappedBy = "academicYear")
    private List<LecturerTeachingCourse> lecturerTeachingCourseList;
    @OneToMany(mappedBy = "academicYear")
    private List<StudentMark> studentMarkList;
    @OneToMany(mappedBy = "academicYear")
    private List<SchoolBill> schoolBillList;
    @OneToMany(mappedBy = "academicYear")
    private List<SemesterRegistration> semesterRegistrationList;

    public AcademicYear() {
    }

    public AcademicYear(String academicYearId) {
        this.academicYearId = academicYearId;
    }

    public String getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(String academicYearId) {
        this.academicYearId = academicYearId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(String currentSemester) {
        this.currentSemester = currentSemester;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public List<LecturerTeachingCourse> getLecturerTeachingCourseList() {
        return lecturerTeachingCourseList;
    }

    public void setLecturerTeachingCourseList(List<LecturerTeachingCourse> lecturerTeachingCourseList) {
        this.lecturerTeachingCourseList = lecturerTeachingCourseList;
    }

    public List<StudentMark> getStudentMarkList() {
        return studentMarkList;
    }

    public void setStudentMarkList(List<StudentMark> studentMarkList) {
        this.studentMarkList = studentMarkList;
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
        hash += (academicYearId != null ? academicYearId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AcademicYear)) {
            return false;
        }
        AcademicYear other = (AcademicYear) object;
        if ((this.academicYearId == null && other.academicYearId != null) || (this.academicYearId != null && !this.academicYearId.equals(other.academicYearId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.AcademicYear[ academicYearId=" + academicYearId + " ]";
    }
    
}
