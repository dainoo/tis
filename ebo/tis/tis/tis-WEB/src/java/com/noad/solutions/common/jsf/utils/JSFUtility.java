/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.jsf.utils;

import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.ejb.entities.Staff;
import com.noad.solutions.tis.ejb.entities.UserAccount;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Daud
 */
public class JSFUtility {

    static void appLogger(UnknownHostException e) {
        Logger.getLogger(JSFUtility.class.getName()).log(Level.SEVERE, null, e.getCause());
    }

    public JSFUtility() {
    }
    public static final String SESSION_NAME = "userSessionData";
    public static final String MACHINE_NAME = "machineName";
    public static final String MACHINE_IP_ADDRESS = "machineIpAddress";
    int counter = 0;

    public static void writToACookie(String cookieName, String cookieValue) {
        getCurrentContext().getExternalContext().addResponseCookie(cookieName, cookieValue, null);
    }

    public static Map<String, Object> readCookieValue() {
        Map<String, Object> requestCookieMap = getCurrentContext().getExternalContext().getRequestCookieMap();
        return requestCookieMap;

    }

    public static String getCurrentPageFullPath() {

        HttpServletRequest servletRequest = (HttpServletRequest) externalContext().getRequest();

        String fullURI = servletRequest.getRequestURI();
        return fullURI;

    }

    public static Object getManagedBean(String beanName) {

        FacesContext fc = FacesContext.getCurrentInstance();
        ELContext elc = fc.getELContext();
        ExpressionFactory ef = fc.getApplication().getExpressionFactory();
        ValueExpression ve = ef.createValueExpression(elc, getJsfEl(beanName), Object.class);
        return ve.getValue(elc);
    }

    private static String getJsfEl(String beanName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static FacesContext getCurrentContext() {
        return FacesContext.getCurrentInstance();
    }

    public static FacesContext currentContext() {
        return FacesContext.getCurrentInstance();
    }

    public static ExternalContext externalContext() {
        return currentContext().getExternalContext();
    }

    public static Object getManageBean(String beanName, FacesContext fc) {
        if (fc == null) {
        } else {
            fc = getCurrentContext();
        }

        return fc.getApplication().getELResolver().getValue(fc.getELContext(), null, beanName);
    }

    public static ValueExpression createValueExpression(String elExpression, Class type) {
        return currentContext().getApplication().getExpressionFactory().createValueExpression(currentContext().getELContext(), "#{" + elExpression + "}", type);
    }

    public static HttpSession getHttpSession() {

        HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        return httpSession;
    }

    public static void setUserSession(String attribute, UserSessionData userSessionData) {
        getHttpSession().setAttribute(attribute, userSessionData);
    }

    public static String getHostAddressDetails(String addresssType) {
        try {
            InetAddress address = InetAddress.getLocalHost();
            if (addresssType.equals(JSFUtility.MACHINE_NAME)) {
                return address.getHostName();//returns host machine name
            } else if (addresssType.equals(JSFUtility.MACHINE_IP_ADDRESS)) {
                return address.getHostAddress();//returns IP of host machine
            }
        } catch (UnknownHostException e) {
            appLogger(e);
        }
        return null;
    }

    /*
     * Returns the MAC address of the machine
     */
    public static String getHostMacAddress() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(address);
            byte[] hardWareAddress = networkInterface.getHardwareAddress();
            StringBuilder macAddress = new StringBuilder();
            for (int i = 0; i < hardWareAddress.length; i++) {
                macAddress.append(String.format("%02X%s", hardWareAddress[i], (i < hardWareAddress.length - 1) ? "-" : ""));
            }
            return macAddress.toString();
        } catch (UnknownHostException e) {
            appLogger(e);
            return null;
        } catch (SocketException e) {
            e.getMessage();
            return null;
        }
    }

    public static UserSessionData getUserSession(String attribute) {
        return (UserSessionData) getHttpSession().getAttribute(attribute);
    }

    public static HttpSession getSession() {

        HttpSession session = (HttpSession) externalContext().getSession(false);
        return session;
    }

    public static String getSessionByUserName(String name) {
        UserAccount userAccount = null;
        userAccount = (UserAccount) getSession().getAttribute(name);
        if (userAccount == null) {
            return "UNKNOWN";
        } else {
            String username = userAccount.getUsername();
            return username;

        }


    }

    /*
     * RETURNS THE CURRENT USER DETAILS IN A SESSION
     */
    public static Staff getUserDetails() {
        try {
            Staff staff = null;
            UserAccount userAccount = null;
            userAccount = getSessionValueByUserAccount(SESSION_NAME);
            staff = TisEjbSource.getCrudInstance().staffFind(userAccount.getUserAccountId());
            return staff;

        } catch (Exception e) {
            return null;
        }

    }

    public static UserAccount getSessionValueByUserAccount(String name) {
        UserAccount userAccount = null;
        userAccount = (UserAccount) getSession().getAttribute(name);
        return userAccount;
    }

    public static void destroySession() {
        getSession().invalidate();
    }

    public static String getUrlParameter(String key) {
        HttpServletRequest request = (HttpServletRequest) getCurrentContext().getExternalContext().getRequest();

        String value = request.getParameter(key);
        return value;
    }

    public static void redirectPage(FacesContext context, String formOutcome) {
        context.responseComplete();
        System.out.println(context + " REdirecting to " + formOutcome);
        try {
            context.getExternalContext().redirect("/main.xhtml");
        } catch (Exception e) {
        }

    }
}
