/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.classwarppers;

import com.noad.solutions.tis.ejb.entities.Department;
import javax.faces.model.SelectItem;

/**
 *
 * @author Dawoode
 */
public class DepartmentHeads extends Department {

    public SelectItem[] departmentMembersSelectItems = null;
    public String hodFullName = "";
    public String hodId=null;

    public SelectItem[] getDepartmentMembersSelectItems() {
        return departmentMembersSelectItems;
    }

    public void setDepartmentMembersSelectItems(SelectItem[] departmentMembersSelectItems) {
        this.departmentMembersSelectItems = departmentMembersSelectItems;
    }

    public String getHodFullName() {
        return hodFullName;
    }

    public String getHodId() {
        return hodId;
    }

    public void setHodId(String hodId) {
        this.hodId = hodId;
    }

    public void setHodFullName(String hodFullName) {
        this.hodFullName = hodFullName;
    }
}
