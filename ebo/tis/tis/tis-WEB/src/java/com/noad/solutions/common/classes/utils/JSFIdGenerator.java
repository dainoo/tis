/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.classes.utils;

import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 *
 * @author Daud
 */
public class JSFIdGenerator {
//@EJB
//public static SmartManufacturingSessionbean smartManufacturingSessionbean;

    public static String APPLICATION_NAME = "TIS";
    public static String ZERO_STRING = "0";
    public static String ZERO_STRING_TEN_PATTERN = "0000000000";
    public static String ZERO_STRING_THREE_PATTERN = "000";
    public static String ZERO_STRING_EIGHT_PATTERN = "00000000";
    private static String ZERO_STRING_SIX_PATTERN = "000000";

    // <editor-fold defaultstate="collapsed" desc="ID GENERATOR METHODS">
    public static String generateId(String entityName, String initials) {
        String id;
        id = TisEjbSource.getCrudInstance().genIdGetNextIdString(entityName, ZERO_STRING, APPLICATION_NAME, ZERO_STRING_TEN_PATTERN);
        return id + initials;
    }

    public static String generateInstitutionId() {
        String id;
        id = TisEjbSource.getCrudInstance().genIdGetNextIdString("TIS_CLIENT", ZERO_STRING, APPLICATION_NAME, ZERO_STRING_THREE_PATTERN);
        return "80" + id;
    }

    public static String generateInitals(Object object) {

        String initials = "ID";
        if (object.toString() == null) {
            return initials;
        } else if (object.toString().length() <= 2) {
            initials = object.toString();
            return initials;
        } else {
            initials = null;
            initials = object.toString().substring(0, 3).toUpperCase();
            return initials;

        }

    }

    public static String generateRandomId() {
        String firstId = UUID.randomUUID().toString().substring(1, 10).toUpperCase();
        String secondId = UUID.randomUUID().toString().substring(1, 10).toUpperCase();
        return (secondId + firstId);

    }

    public static String generateUsername(String firstName, String secondName, String departmentName) {
        if (firstName == null || secondName == null || departmentName == null) {
            return "xxxxxxx";
        } else {
            String schoolInitial = "";
            String username = null;
            Random random = new Random();
            int min = random.nextInt(firstName.length());
            firstName = firstName.substring(min);
            for (String retval : departmentName.split("\\s+")) {
                schoolInitial += retval.substring(0, 1).toLowerCase();
            }

            username = firstName + secondName + "." + schoolInitial;
            return username;
        }


    }

    public static String generatePassword() {
        String password = UUID.randomUUID().toString().substring(1, 8);
        return password;

    }

    public static String generateIndexNumber(String programName, Date admissionDate, String programCode) {

        String indexNumber = null;
        String admissionTYear = new SimpleDateFormat("yy").format(admissionDate);//GIVES TWO DIGIT YEAR
        indexNumber = TisEjbSource.getCrudInstance().genIdGetNextIdString((programName + admissionTYear), ZERO_STRING, APPLICATION_NAME, ZERO_STRING_THREE_PATTERN);
        return programCode + indexNumber + admissionTYear;


    }

    public static String generateIndexNumber(Program program, Date admissionDate) {

        String indexNumber = null;
        String admissionTYear = new SimpleDateFormat("yy").format(admissionDate);//GIVES TWO DIGIT YEAR
        indexNumber = TisEjbSource.getCrudInstance().genIdGetNextIdString((program.getPrgramCode() + admissionTYear), ZERO_STRING, APPLICATION_NAME, ZERO_STRING_THREE_PATTERN);
        return program.getPrgramCode() + indexNumber + admissionTYear;


    }

    public static String generateStudentNumber(Date admissionDate) {
        String studentNumber = null;
        String admissionTYear = new SimpleDateFormat("yy").format(admissionDate);//GIVES TWO DIGIT YEAR
        studentNumber = TisEjbSource.getCrudInstance().genIdGetNextIdString("student", ZERO_STRING, APPLICATION_NAME, ZERO_STRING_SIX_PATTERN);
        return admissionTYear + studentNumber;

    }
    // </editor-fold>
}
