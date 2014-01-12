package com.noad.solutions.common.security.utils;

import com.noad.solutions.common.jsf.utils.JSFUtility;
import javax.faces.application.NavigationHandler;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 *
 * @author Daud
 */
public class AuthorizationListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        String currentPage = JSFUtility.getCurrentContext().getViewRoot().getViewId();
        boolean isLoginPage = (currentPage.lastIndexOf("login.xhtml") > -1);
        boolean isLoadingPage = (currentPage.lastIndexOf("loading.xhtml") > -1);
        boolean isExpiredPage = (currentPage.lastIndexOf("expired_session.xhtml") > -1);
        Object currentUser = JSFUtility.getHttpSession().getAttribute(JSFUtility.SESSION_NAME);

//        when user expires
        if (JSFUtility.getHttpSession() == null) {
            NavigationHandler nh = JSFUtility.getCurrentContext().getApplication().getNavigationHandler();
            nh.handleNavigation(JSFUtility.getCurrentContext(), null, "expiredSession");
        } else {
            
            try {
                
                if (!isLoginPage && !isLoadingPage && !isExpiredPage && (currentUser == null || currentUser == "")) {
                    NavigationHandler nh = JSFUtility.getCurrentContext().getApplication().getNavigationHandler();
                    nh.handleNavigation(JSFUtility.getCurrentContext(), null, "expiredSession");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;

    }
}
