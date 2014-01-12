/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.constant.utils;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Daud
 */
@ManagedBean
@SessionScoped
public class IndexController {

    String fromServer = "Hello ! World";

    public IndexController() {
    }

    public String getFromServer() {
        return fromServer;
    }

    public void changeText() {
        fromServer = "I HAVE CHANGED THE TEXT";
        System.out.println("INSIDE CHANGE TEXT");
    }

    public void setFromServer(String fromServer) {
        this.fromServer = fromServer;
    }
}
