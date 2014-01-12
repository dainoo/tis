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
@Table(name = "lecturer_teaching_course")
@NamedQueries({
    @NamedQuery(name = "LecturerTeachingCourse.findAll", query = "SELECT l FROM LecturerTeachingCourse l")})
public class LecturerTeachingCourse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "lecturer_course_id")
    private String lecturerCourseId;
    @Size(max = 255)
    @Column(name = "courses")
    private String courses;
    @Size(max = 50)
    @Column(name = "academic_level")
    private String academicLevel;
    @Size(max = 255)
    @Column(name = "course_marks_uploaded")
    private String courseMarksUploaded;
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
    @Column(name = "selected")
    private Boolean selected;
    @Size(max = 10)
    @Column(name = "removed")
    private String removed;
    @JoinColumn(name = "academic_year", referencedColumnName = "academic_year_id")
    @ManyToOne
    private AcademicYear academicYear;
    @JoinColumn(name = "program", referencedColumnName = "program_id")
    @ManyToOne
    private Program program;
    @JoinColumn(name = "lecturer", referencedColumnName = "staff_id")
    @ManyToOne
    private Staff lecturer;

    public LecturerTeachingCourse() {
    }

    public LecturerTeachingCourse(String lecturerCourseId) {
        this.lecturerCourseId = lecturerCourseId;
    }

    public String getLecturerCourseId() {
        return lecturerCourseId;
    }

    public void setLecturerCourseId(String lecturerCourseId) {
        this.lecturerCourseId = lecturerCourseId;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public String getCourseMarksUploaded() {
        return courseMarksUploaded;
    }

    public void setCourseMarksUploaded(String courseMarksUploaded) {
        this.courseMarksUploaded = courseMarksUploaded;
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

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
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

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Staff getLecturer() {
        return lecturer;
    }

    public void setLecturer(Staff lecturer) {
        this.lecturer = lecturer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lecturerCourseId != null ? lecturerCourseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LecturerTeachingCourse)) {
            return false;
        }
        LecturerTeachingCourse other = (LecturerTeachingCourse) object;
        if ((this.lecturerCourseId == null && other.lecturerCourseId != null) || (this.lecturerCourseId != null && !this.lecturerCourseId.equals(other.lecturerCourseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.LecturerTeachingCourse[ lecturerCourseId=" + lecturerCourseId + " ]";
    }
    
}
