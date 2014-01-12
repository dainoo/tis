/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.ejb.entities;

import com.noad.solutions.tis.ejb.commons.Selected;
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
@Table(name = "student")
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s")})
public class Student extends Selected implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "student_id")
    private String studentId;
    @Size(max = 15)
    @Column(name = "student_index_number")
    private String studentIndexNumber;
    @Size(max = 50)
    @Column(name = "surname")
    private String surname;
    @Size(max = 150)
    @Column(name = "other_names")
    private String otherNames;
    @Size(max = 10)
    @Column(name = "title")
    private String title;
    @Size(max = 50)
    @Column(name = "admission_year")
    private String admissionYear;
    @Size(max = 20)
    @Column(name = "academic_status")
    private String academicStatus;
    @Column(name = "date_of_admission")
    @Temporal(TemporalType.DATE)
    private Date dateOfAdmission;
    @Size(max = 20)
    @Column(name = "completion_year")
    private String completionYear;
    @Size(max = 10)
    @Column(name = "marital_status")
    private String maritalStatus;
    @Size(max = 50)
    @Column(name = "username")
    private String username;
    @Size(max = 50)
    @Column(name = "password")
    private String password;
    @Size(max = 10)
    @Column(name = "gender")
    private String gender;
    @Size(max = 255)
    @Column(name = "culture")
    private String culture;
    @Size(max = 255)
    @Column(name = "language_spoken")
    private String languageSpoken;
    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    @Size(max = 100)
    @Column(name = "home_town")
    private String homeTown;
    @Size(max = 50)
    @Column(name = "student_picture")
    private String studentPicture;
    @Size(max = 100)
    @Column(name = "country")
    private String country;
    @Size(max = 255)
    @Column(name = "deformities")
    private String deformities;
    @Size(max = 100)
    @Column(name = "place_of_birth")
    private String placeOfBirth;
    @Size(max = 100)
    @Column(name = "district")
    private String district;
    @Size(max = 50)
    @Column(name = "region")
    private String region;
    @Size(max = 50)
    @Column(name = "postal_address")
    private String postalAddress;
    @Size(max = 50)
    @Column(name = "email_address")
    private String emailAddress;
    @Size(max = 21)
    @Column(name = "contact")
    private String contact;
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
    @Column(name = "selected")
    private Boolean selected;
    @OneToMany(mappedBy = "student")
    private List<StudentLevel> studentLevelList;
    @OneToMany(mappedBy = "student")
    private List<StudentAccount> studentAccountList;
    @OneToMany(mappedBy = "student")
    private List<IncompleteCourse> incompleteCourseList;
    @OneToMany(mappedBy = "student")
    private List<StudentMark> studentMarkList;
    @JoinColumn(name = "program", referencedColumnName = "program_id")
    @ManyToOne
    private Program program;
    @JoinColumn(name = "current_level", referencedColumnName = "academic_level_id")
    @ManyToOne
    private AcademicLevel currentLevel;
    @JoinColumn(name = "guardiance", referencedColumnName = "guardiance_id")
    @ManyToOne
    private Guardiance guardiance;
    @JoinColumn(name = "department", referencedColumnName = "department_id")
    @ManyToOne
    private Department department;
    @OneToMany(mappedBy = "student")
    private List<SemesterRegistration> semesterRegistrationList;

    public Student() {
    }

    public Student(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentIndexNumber() {
        return studentIndexNumber;
    }

    public void setStudentIndexNumber(String studentIndexNumber) {
        this.studentIndexNumber = studentIndexNumber;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(String admissionYear) {
        this.admissionYear = admissionYear;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public void setAcademicStatus(String academicStatus) {
        this.academicStatus = academicStatus;
    }

    public Date getDateOfAdmission() {
        return dateOfAdmission;
    }

    public void setDateOfAdmission(Date dateOfAdmission) {
        this.dateOfAdmission = dateOfAdmission;
    }

    public String getCompletionYear() {
        return completionYear;
    }

    public void setCompletionYear(String completionYear) {
        this.completionYear = completionYear;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getLanguageSpoken() {
        return languageSpoken;
    }

    public void setLanguageSpoken(String languageSpoken) {
        this.languageSpoken = languageSpoken;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getStudentPicture() {
        return studentPicture;
    }

    public void setStudentPicture(String studentPicture) {
        this.studentPicture = studentPicture;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDeformities() {
        return deformities;
    }

    public void setDeformities(String deformities) {
        this.deformities = deformities;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public List<StudentLevel> getStudentLevelList() {
        return studentLevelList;
    }

    public void setStudentLevelList(List<StudentLevel> studentLevelList) {
        this.studentLevelList = studentLevelList;
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

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public AcademicLevel getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(AcademicLevel currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Guardiance getGuardiance() {
        return guardiance;
    }

    public void setGuardiance(Guardiance guardiance) {
        this.guardiance = guardiance;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
        hash += (studentId != null ? studentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.studentId == null && other.studentId != null) || (this.studentId != null && !this.studentId.equals(other.studentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.Student[ studentId=" + studentId + " ]";
    }
    
}
