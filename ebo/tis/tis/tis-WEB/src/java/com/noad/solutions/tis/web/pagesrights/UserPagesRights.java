/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.pagesrights;

import java.util.List;

/**
 *
 * @author Daud
 */
public class UserPagesRights {

    private String menuName = null;
    private List<UserPages> listOfUserPageses = null;

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public List<UserPages> getListOfUserPageses() {
        return listOfUserPageses;
    }

    public void setListOfUserPageses(List<UserPages> listOfUserPageses) {
        this.listOfUserPageses = listOfUserPageses;
    }
}
