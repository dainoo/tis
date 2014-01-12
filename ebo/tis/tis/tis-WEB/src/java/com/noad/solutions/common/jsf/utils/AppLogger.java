/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.jsf.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daud
 */
public class AppLogger {

    public static void catchHandler(Exception e) {

        Logger.getLogger(Object.class.getName()).log(Level.SEVERE, null, e.getCause());

    }
}
