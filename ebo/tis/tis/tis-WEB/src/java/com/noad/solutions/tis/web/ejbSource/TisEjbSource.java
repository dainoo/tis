/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.ejbSource;
import com.noad.solutions.tis.ejb.sessionbeans.TisBasicQuariesSessionbean;
import com.noad.solutions.tis.ejb.sessionbeans.TisCrudSessionbean;
import com.noad.solutions.tis.ejb.sessionbeans.TisExamsProcessingSessionbean;
import com.noad.solutions.tis.ejb.sessionbeans.TisValidationSessionbean;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author daud
 */
public class TisEjbSource implements Serializable{

    private static TisCrudSessionbean tisCrudSessionbean = null;
    private static TisValidationSessionbean tisValidationSessionbean=null;
    private static TisBasicQuariesSessionbean tisBasicQuariesSessionbean=null;
    private static TisExamsProcessingSessionbean tisExamsProcessingSessionbean=null;

    public static TisCrudSessionbean getCrudInstance() {

        if (tisCrudSessionbean != null) {
            return tisCrudSessionbean;

        }
        Context context;
        try {
            context = (Context) new InitialContext();
            tisCrudSessionbean = (TisCrudSessionbean) context.lookup("java:global/tis/tis-EJB/TisCrudSessionbean!com.noad.solutions.tis.ejb.sessionbeans.TisCrudSessionbean");
            if (tisCrudSessionbean != null) {
                return tisCrudSessionbean;
            }
        } catch (NamingException ex) {
            System.out.println(" UNABLE TO LOOK UP TIS SESSIONBEAN EJB" + ex.getMessage());
            appLogger(ex);
        }
        return null;
    }
    
    public static TisValidationSessionbean getValidationInstance() {

        if (tisValidationSessionbean != null) {
            return tisValidationSessionbean;

        }
        Context context;
        try {
            context = (Context) new InitialContext();
            tisValidationSessionbean = (TisValidationSessionbean) context.lookup("java:global/tis/tis-EJB/TisValidationSessionbean!com.noad.solutions.tis.ejb.sessionbeans.TisValidationSessionbean");
            if (tisValidationSessionbean != null) {
                return tisValidationSessionbean;
            }
        } catch (NamingException ex) {
            System.out.println(" UNABLE TO LOOK UP TIS VALIDATION SESSIONBEAN EJB" + ex.getMessage());
            appLogger(ex);
        }
        return null;
    }
    
    public static TisBasicQuariesSessionbean getBasicQuariesInstance() {

        if (tisBasicQuariesSessionbean != null) {
            return tisBasicQuariesSessionbean;

        }
        Context context;
        try {
            context = (Context) new InitialContext();
            tisBasicQuariesSessionbean = (TisBasicQuariesSessionbean) context.lookup("java:global/tis/tis-EJB/TisBasicQuariesSessionbean!com.noad.solutions.tis.ejb.sessionbeans.TisBasicQuariesSessionbean");
            if (tisBasicQuariesSessionbean != null) {
                return tisBasicQuariesSessionbean;
            }
        } catch (NamingException ex) {
            System.out.println(" UNABLE TO LOOK UP TIS BASIC QUARIES SESSIONBEAN EJB" + ex.getMessage());
            appLogger(ex);
        }
        return null;
    }
    public static TisExamsProcessingSessionbean getExamsProcessingInstance() {

        if (tisExamsProcessingSessionbean != null) {
            return tisExamsProcessingSessionbean;

        }
        Context context;
        try {
            context = (Context) new InitialContext();
            tisExamsProcessingSessionbean = (TisExamsProcessingSessionbean) context.lookup("java:global/tis/tis-EJB/TisExamsProcessingSessionbean!com.noad.solutions.tis.ejb.sessionbeans.TisExamsProcessingSessionbean");
            if (tisExamsProcessingSessionbean != null) {
                return tisExamsProcessingSessionbean;
            }
        } catch (NamingException ex) {
            System.out.println(" UNABLE TO LOOK UP TIS BASIC QUARIES SESSIONBEAN EJB" + ex.getMessage());
            appLogger(ex);
        }
        return null;
    }

    static void appLogger(NamingException ex) {
        Logger.getLogger(TisEjbSource.class.getName()).log(Level.SEVERE, null, ex.getCause());
    }
}
