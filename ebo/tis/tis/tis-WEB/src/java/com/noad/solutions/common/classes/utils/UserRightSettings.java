/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.classes.utils;

import com.noad.solutions.tis.ejb.entities.AccessPage;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import java.util.List;

/**
 *
 * @author Daud
 */
public class UserRightSettings {

    public UserAccessPage getUserRights(String userId) {
        UserAccessPage accessPage = new UserAccessPage();

        try {
            List<AccessPage> listOfAccessPages= TisEjbSource.getCrudInstance().accessPageFindByAttribute(userId, userId);
            String combinedUserPages=BinderSplitter.accessPageBinder(listOfAccessPages);
            
            accessPage.setReadCourse(combinedUserPages.contains(userId)?"inline;":"none;");
        } catch (Exception e) {
        }
        return null;

    }
}
