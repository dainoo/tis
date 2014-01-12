/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.ejb.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "staff")
@NamedQueries({
    @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s")})
public class Staff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "staff_id")
    private String staffId;
    @Size(max = 25)
    @Column(name = "title")
    private String title;
    @Size(max = 50)
    @Column(name = "surname")
    private String surname;
    @Size(max = 150)
    @Column(name = "other_names")
    private String otherNames;
    @Size(max = 10)
    @Column(name = "gender")
    private String gender;
    @Size(max = 32)
    @Column(name = "contact")
    private String contact;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Column(name = "date_appointed")
    @Temporal(TemporalType.DATE)
    private Date dateAppointed;
    @Size(max = 100)
    @Column(name = "country")
    private String country;
    @Size(max = 100)
    @Column(name = "residence")
    private String residence;
    @Size(max = 100)
    @Column(name = "postal_address")
    private String postalAddress;
    @Size(max = 50)
    @Column(name = "email_address")
    private String emailAddress;
    @Size(max = 5)
    @Column(name = "in_service")
    private String inService;
    @Size(max = 255)
    @Column(name = "staff_picture")
    private String staffPicture;
    @Size(max = 20)
    @Column(name = "marital_status")
    private String maritalStatus;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Size(max = 50)
    @Column(name = "created_by")
    private String createdBy;
    @Size(max = 50)
    @Column(name = "edited_by")
    private String editedBy;
    @Column(name = "edited_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date editedDate;
    @Size(max = 10)
    @Column(name = "removed")
    private String removed;
    @OneToMany(mappedBy = "headOfDept")
    private List<Department> departmentList;
    @OneToMany(mappedBy = "collectedPerson")
    private List<StudentAccount> studentAccountList;
    @OneToMany(mappedBy = "lecturer")
    private List<LecturerTeachingCourse> lecturerTeachingCourseList;
    @OneToMany(mappedBy = "lecturer")
    private List<StudentMark> studentMarkList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "staff")
    private UserAccount userAccount;
    @JoinColumn(name = "department", referencedColumnName = "department_id")
    @ManyToOne
    private Department department;
    @OneToMany(mappedBy = "staff")
    private List<StaffQualification> staffQualificationList;
    @OneToMany(mappedBy = "registeredBy")
    private List<SemesterRegistration> semesterRegistrationList;

    public Staff() {
    }

    public Staff(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateAppointed() {
        return dateAppointed;
    }

    public void setDateAppointed(Date dateAppointed) {
        this.dateAppointed = dateAppointed;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getInService() {
        return inService;
    }

    public void setInService(String inService) {
        this.inService = inService;
    }

    public String getStaffPicture() {
        return staffPicture;
    }

    public void setStaffPicture(String staffPicture) {
        this.staffPicture = staffPicture;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public List<StudentAccount> getStudentAccountList() {
        return studentAccountList;
    }

    public void setStudentAccountList(List<StudentAccount> studentAccountList) {
        this.studentAccountList = studentAccountList;
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

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<StaffQualification> getStaffQualificationList() {
        return staffQualificationList;
    }

    public void setStaffQualificationList(List<StaffQualification> staffQualificationList) {
        this.staffQualificationList = staffQualificationList;
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
        hash += (staffId != null ? staffId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.staffId == null && other.staffId != null) || (this.staffId != null && !this.staffId.equals(other.staffId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.Staff[ staffId=" + staffId + " ]";
    }
    
}
