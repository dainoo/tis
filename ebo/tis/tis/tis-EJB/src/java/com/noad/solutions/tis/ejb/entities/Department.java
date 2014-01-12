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
@Table(name = "department")
@NamedQueries({
    @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d")})
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "department_id")
    private String departmentId;
    @Size(max = 100)
    @Column(name = "department_name")
    private String departmentName;
    @Size(max = 15)
    @Column(name = "department_contact")
    private String departmentContact;
    @Size(max = 100)
    @Column(name = "department_category")
    private String departmentCategory;
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
    @JoinColumn(name = "head_of_dept", referencedColumnName = "staff_id")
    @ManyToOne
    private Staff headOfDept;
    @OneToMany(mappedBy = "department")
    private List<Staff> staffList;
    @OneToMany(mappedBy = "department")
    private List<ProgramCourse> programCourseList;
    @OneToMany(mappedBy = "department")
    private List<Student> studentList;
    @OneToMany(mappedBy = "department")
    private List<Program> programList;

    public Department() {
    }

    public Department(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentContact() {
        return departmentContact;
    }

    public void setDepartmentContact(String departmentContact) {
        this.departmentContact = departmentContact;
    }

    public String getDepartmentCategory() {
        return departmentCategory;
    }

    public void setDepartmentCategory(String departmentCategory) {
        this.departmentCategory = departmentCategory;
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

    public Staff getHeadOfDept() {
        return headOfDept;
    }

    public void setHeadOfDept(Staff headOfDept) {
        this.headOfDept = headOfDept;
    }

    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
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

    public List<Program> getProgramList() {
        return programList;
    }

    public void setProgramList(List<Program> programList) {
        this.programList = programList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (departmentId != null ? departmentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Department)) {
            return false;
        }
        Department other = (Department) object;
        if ((this.departmentId == null && other.departmentId != null) || (this.departmentId != null && !this.departmentId.equals(other.departmentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.Department[ departmentId=" + departmentId + " ]";
    }
    
}
