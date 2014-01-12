/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.ejb.entities;

import com.noad.solutions.tis.ejb.commons.Selected;
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
@Table(name = "student_level")
@NamedQueries({
    @NamedQuery(name = "StudentLevel.findAll", query = "SELECT s FROM StudentLevel s")})
public class StudentLevel extends Selected implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "student_level_id")
    private String studentLevelId;
    @Size(max = 50)
    @Column(name = "academic_level")
    private String academicLevel;
    @Size(max = 50)
    @Column(name = "academic_year")
    private String academicYear;
    @Size(max = 10)
    @Column(name = "removed")
    private String removed;
    @JoinColumn(name = "student", referencedColumnName = "student_id")
    @ManyToOne
    private Student student;

    public StudentLevel() {
    }

    public StudentLevel(String studentLevelId) {
        this.studentLevelId = studentLevelId;
    }

    public String getStudentLevelId() {
        return studentLevelId;
    }

    public void setStudentLevelId(String studentLevelId) {
        this.studentLevelId = studentLevelId;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getRemoved() {
        return removed;
    }

    public void setRemoved(String removed) {
        this.removed = removed;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentLevelId != null ? studentLevelId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentLevel)) {
            return false;
        }
        StudentLevel other = (StudentLevel) object;
        if ((this.studentLevelId == null && other.studentLevelId != null) || (this.studentLevelId != null && !this.studentLevelId.equals(other.studentLevelId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.StudentLevel[ studentLevelId=" + studentLevelId + " ]";
    }
}
