/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.image.utils;

import java.io.File;

/**
 *
 * @author Daud
 */
public class ImageProcessor {

    private String computerName;
    private static String serverRootImagePath = System.getProperty("com.sun.aas.instanceRoot")
            + File.separator + "docroot"
            + File.separator + "tisres" + File.separator;
    private static String clientPicturePath = serverRootImagePath + "students_pics" + File.separator;
    private String clientPictureURL = "http://" + computerName + ":8080/tisres/" + "students_pics" + "/";
    private String studentImageCode;
    private final String JPEG = ".jpg";

    public static String serverRootImagePath() {
        return clientPicturePath;

    }

    public String getComputerName() {
        return computerName;
    }

    public void setComputerName(String computerName) {
        this.computerName = computerName;
    }

    public static String getServerRootImagePath() {
        return serverRootImagePath;
    }

    public String getJPEG() {
        return JPEG;
    }

    public static void setServerRootImagePath(String serverRootImagePath) {
        ImageProcessor.serverRootImagePath = serverRootImagePath;
    }

    public static String getClientPicturePath() {
        return clientPicturePath;
    }

    public static void setClientPicturePath(String clientPicturePath) {
        ImageProcessor.clientPicturePath = clientPicturePath;
    }

    public String getClientPictureURL() {
        return clientPictureURL;
    }

    public void setClientPictureURL(String clientPictureURL) {
        this.clientPictureURL = clientPictureURL;
    }

    public String getStudentImageCode() {
        return studentImageCode;
    }

    public void setStudentImageCode(String studentImageCode) {
        this.studentImageCode = studentImageCode;
    }
    
    
}
