/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.tis.ejb.entities.Student;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.model.DataModel;

/**
 *
 * @author Dawoode
 */
@ManagedBean
@SessionScoped
public class StudentsAdmittedController implements Serializable {

    private Student student = new Student();
    private List<Student> listOfStudents = null;
    private DataModel<Student> studentsDataModel = null;
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();

    public StudentsAdmittedController() {
    }
    
    public void viewSelectedGroup(){
    
        try {
            
        } catch (Exception e) {
            appLogger(e);
        }
    }
      void appLogger(Exception e) {
        Logger.getLogger(StudentsAdmittedController.class.getName()).log(Level.SEVERE, null, e.getCause());
    }
}
