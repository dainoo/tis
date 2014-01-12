/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.ejb.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Daud
 */
@Entity
@Table(name = "incomplete_course")
@NamedQueries({
    @NamedQuery(name = "IncompleteCourse.findAll", query = "SELECT i FROM IncompleteCourse i")})
public class IncompleteCourse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "incomplete_course_id")
    private String incompleteCourseId;
    @Size(max = 10)
    @Column(name = "passed")
    private String passed;
    @Column(name = "attempts")
    private Integer attempts;
    @Size(max = 50)
    @Column(name = "incomplete_type")
    private String incompleteType;
    @Size(max = 10)
    @Column(name = "removed")
    private String removed;
    @JoinColumn(name = "academic_year", referencedColumnName = "academic_year_id")
    @ManyToOne
    private AcademicYear academicYear;
    @JoinColumn(name = "student", referencedColumnName = "student_id")
    @ManyToOne
    private Student student;
    @JoinColumn(name = "academic_level", referencedColumnName = "academic_level_id")
    @ManyToOne
    private AcademicLevel academicLevel;
    @JoinColumn(name = "course", referencedColumnName = "course_id")
    @ManyToOne
    private Course course;

    public IncompleteCourse() {
    }

    public IncompleteCourse(String incompleteCourseId) {
        this.incompleteCourseId = incompleteCourseId;
    }

    public String getIncompleteCourseId() {
        return incompleteCourseId;
    }

    public void setIncompleteCourseId(String incompleteCourseId) {
        this.incompleteCourseId = incompleteCourseId;
    }

    public String getPassed() {
        return passed;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public String getIncompleteType() {
        return incompleteType;
    }

    public void setIncompleteType(String incompleteType) {
        this.incompleteType = incompleteType;
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

    public AcademicLevel getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(AcademicLevel academicLevel) {
        this.academicLevel = academicLevel;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (incompleteCourseId != null ? incompleteCourseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IncompleteCourse)) {
            return false;
        }
        IncompleteCourse other = (IncompleteCourse) object;
        if ((this.incompleteCourseId == null && other.incompleteCourseId != null) || (this.incompleteCourseId != null && !this.incompleteCourseId.equals(other.incompleteCourseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.IncompleteCourse[ incompleteCourseId=" + incompleteCourseId + " ]";
    }
    
}
