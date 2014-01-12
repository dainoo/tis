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
@Table(name = "student_mark")
@NamedQueries({
    @NamedQuery(name = "StudentMark.findAll", query = "SELECT s FROM StudentMark s")})
public class StudentMark implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "student_mark_id")
    private String studentMarkId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "class_work_one")
    private Double classWorkOne;
    @Column(name = "class_work_two")
    private Double classWorkTwo;
    @Column(name = "mid_sem_mark")
    private Double midSemMark;
    @Column(name = "exam_mark")
    private Double examMark;
    @Column(name = "total_mark")
    private Double totalMark;
    @Column(name = "grade_point")
    private Double gradePoint;
    @Size(max = 5)
    @Column(name = "grade")
    private String grade;
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
    @Size(max = 255)
    @Column(name = "mark_style")
    private String markStyle;
    @JoinColumn(name = "academic_year", referencedColumnName = "academic_year_id")
    @ManyToOne
    private AcademicYear academicYear;
    @JoinColumn(name = "student", referencedColumnName = "student_id")
    @ManyToOne
    private Student student;
    @JoinColumn(name = "academic_level", referencedColumnName = "academic_level_id")
    @ManyToOne
    private AcademicLevel academicLevel;
    @JoinColumn(name = "lecturer", referencedColumnName = "staff_id")
    @ManyToOne
    private Staff lecturer;
    @JoinColumn(name = "course", referencedColumnName = "course_id")
    @ManyToOne
    private Course course;

    public StudentMark() {
    }

    public StudentMark(String studentMarkId) {
        this.studentMarkId = studentMarkId;
    }

    public String getStudentMarkId() {
        return studentMarkId;
    }

    public void setStudentMarkId(String studentMarkId) {
        this.studentMarkId = studentMarkId;
    }

    public Double getClassWorkOne() {
        return classWorkOne;
    }

    public void setClassWorkOne(Double classWorkOne) {
        this.classWorkOne = classWorkOne;
    }

    public Double getClassWorkTwo() {
        return classWorkTwo;
    }

    public void setClassWorkTwo(Double classWorkTwo) {
        this.classWorkTwo = classWorkTwo;
    }

    public Double getMidSemMark() {
        return midSemMark;
    }

    public void setMidSemMark(Double midSemMark) {
        this.midSemMark = midSemMark;
    }

    public Double getExamMark() {
        return examMark;
    }

    public void setExamMark(Double examMark) {
        this.examMark = examMark;
    }

    public Double getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(Double totalMark) {
        this.totalMark = totalMark;
    }

    public Double getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(Double gradePoint) {
        this.gradePoint = gradePoint;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public String getMarkStyle() {
        return markStyle;
    }

    public void setMarkStyle(String markStyle) {
        this.markStyle = markStyle;
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

    public Staff getLecturer() {
        return lecturer;
    }

    public void setLecturer(Staff lecturer) {
        this.lecturer = lecturer;
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
        hash += (studentMarkId != null ? studentMarkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentMark)) {
            return false;
        }
        StudentMark other = (StudentMark) object;
        if ((this.studentMarkId == null && other.studentMarkId != null) || (this.studentMarkId != null && !this.studentMarkId.equals(other.studentMarkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.StudentMark[ studentMarkId=" + studentMarkId + " ]";
    }
    
}
