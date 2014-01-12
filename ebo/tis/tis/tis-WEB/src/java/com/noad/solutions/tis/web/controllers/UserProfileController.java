/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.tis.ejb.entities.UserAccount;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Daud
 */
@Named(value = "userProfileController")
@RequestScoped
public class UserProfileController {

    private UserAccount userAccount = new UserAccount();
    private String oldPassword = null;
    private String newPassword = null;
    private String confirmedPassword = null;

    public UserProfileController() {
        userAccount = TisEjbSource.getCrudInstance().userAccountFind(JSFUtility.getUserSession(JSFUtility.SESSION_NAME).getStaff().getStaffId());
    }

    public String viewProfile() {
        return "../pages/user_profile.xhtml?faces-redirect=true";

    }

    public String changePassword() {
        try {
            if (!userAccount.getPassword().equals(oldPassword)) {
                JSFMessages.warnMessage("No Such Password Exist");
                return "";
            }
            if (!newPassword.equals(confirmedPassword)) {
                JSFMessages.errorMessage(JSFMessages.CONFIRM_PASSWORD);
                return "";
            }
            userAccount.setPassword(newPassword);
            boolean checkIfUpdated = TisEjbSource.getCrudInstance().userAccountUpdate(userAccount);
            if (checkIfUpdated) {
                JSFMessages.infoMessage("Your Password Has Been Changed");
                resetPassords();
            } else {
                JSFMessages.errorMessage("Unable To Change Password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public void resetPassords() {
        oldPassword = null;
        newPassword = null;
        confirmedPassword = null;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }
}