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
@Table(name = "student_account")
@NamedQueries({
    @NamedQuery(name = "StudentAccount.findAll", query = "SELECT s FROM StudentAccount s")})
public class StudentAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "student_account_id")
    private String studentAccountId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount_involved")
    private Double amountInvolved;
    @Column(name = "transaction_date")
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    @Column(name = "transaction_time")
    @Temporal(TemporalType.TIME)
    private Date transactionTime;
    @Size(max = 20)
    @Column(name = "receipt_number")
    private String receiptNumber;
    @Size(max = 20)
    @Column(name = "entry_type")
    private String entryType;
    @Size(max = 20)
    @Column(name = "mode_of_payment")
    private String modeOfPayment;
    @Size(max = 50)
    @Column(name = "mode_of_payment_number")
    private String modeOfPaymentNumber;
    @JoinColumn(name = "academic_year", referencedColumnName = "academic_year_id")
    @ManyToOne
    private AcademicYear academicYear;
    @JoinColumn(name = "collected_person", referencedColumnName = "staff_id")
    @ManyToOne
    private Staff collectedPerson;
    @JoinColumn(name = "academic_level", referencedColumnName = "academic_level_id")
    @ManyToOne
    private AcademicLevel academicLevel;
    @JoinColumn(name = "school_bill_category", referencedColumnName = "school_bill_category_id")
    @ManyToOne
    private SchoolBillCategory schoolBillCategory;
    @JoinColumn(name = "student", referencedColumnName = "student_id")
    @ManyToOne
    private Student student;

    public StudentAccount() {
    }

    public StudentAccount(String studentAccountId) {
        this.studentAccountId = studentAccountId;
    }

    public String getStudentAccountId() {
        return studentAccountId;
    }

    public void setStudentAccountId(String studentAccountId) {
        this.studentAccountId = studentAccountId;
    }

    public Double getAmountInvolved() {
        return amountInvolved;
    }

    public void setAmountInvolved(Double amountInvolved) {
        this.amountInvolved = amountInvolved;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Date transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getModeOfPaymentNumber() {
        return modeOfPaymentNumber;
    }

    public void setModeOfPaymentNumber(String modeOfPaymentNumber) {
        this.modeOfPaymentNumber = modeOfPaymentNumber;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public Staff getCollectedPerson() {
        return collectedPerson;
    }

    public void setCollectedPerson(Staff collectedPerson) {
        this.collectedPerson = collectedPerson;
    }

    public AcademicLevel getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(AcademicLevel academicLevel) {
        this.academicLevel = academicLevel;
    }

    public SchoolBillCategory getSchoolBillCategory() {
        return schoolBillCategory;
    }

    public void setSchoolBillCategory(SchoolBillCategory schoolBillCategory) {
        this.schoolBillCategory = schoolBillCategory;
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
        hash += (studentAccountId != null ? studentAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentAccount)) {
            return false;
        }
        StudentAccount other = (StudentAccount) object;
        if ((this.studentAccountId == null && other.studentAccountId != null) || (this.studentAccountId != null && !this.studentAccountId.equals(other.studentAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.noad.solutions.tis.ejb.entities.StudentAccount[ studentAccountId=" + studentAccountId + " ]";
    }
    
}
