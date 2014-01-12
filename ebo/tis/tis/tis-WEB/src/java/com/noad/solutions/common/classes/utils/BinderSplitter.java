/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.classes.utils;

import com.noad.solutions.tis.ejb.entities.AccessPage;
import com.noad.solutions.tis.ejb.entities.Course;
import com.noad.solutions.tis.ejb.entities.Ebo;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.web.pagesrights.AccessFunction;
import com.noad.solutions.tis.web.pagesrights.AppMainMenus;
import com.noad.solutions.tis.web.pagesrights.AppPageSettings;
import java.util.ArrayList;
import java.util.List;
//

/**
 *
 * @author Daud
 */
public class BinderSplitter {

    public static List<Ebo> userPagesSplitterList(String userPages) {
        List<AccessFunction> listOfAccessFunctions = new ArrayList<AccessFunction>();
        List<Ebo> listOfUserAccessPages = new ArrayList<Ebo>();
        List<String> submenu = new ArrayList<String>();
        List<String> functions = new ArrayList<String>();
        Ebo ebo = new Ebo();
        AccessFunction accessFunction = new AccessFunction();
        //
        try {
            System.out.println("THE FULL ACCESS IS " + userPages);
            String[] fullRight = userPages.split("#");

            for (String eachFullRight : fullRight) {
                System.out.println("THE EACH FULLRIGHT IS  " + eachFullRight);
                submenu.add(eachFullRight.substring(4, 7));
                functions.add(eachFullRight.substring(8));
            }
            for (String string : submenu) {

                ebo = TisEjbSource.getCrudInstance().eboFind(string);
                listOfUserAccessPages.add(ebo);
                ebo = new Ebo();
            }
            String TRUE = "true";
            for (String eachFunction : functions) {
                String func[] = eachFunction.split("-");
                accessFunction.setWrite(func[0].contains(TRUE));
                accessFunction.setRead(func[1].contains(TRUE));
                accessFunction.setPrint(func[2].contains(TRUE));
                accessFunction.setAccess(func[3].contains(TRUE));
                listOfAccessFunctions.add(accessFunction);
                accessFunction = new AccessFunction();
            }
            for (int i = 0; i < listOfUserAccessPages.size(); i++) {
                listOfUserAccessPages.get(i).setPrintRight(listOfAccessFunctions.get(i).isPrint());
                listOfUserAccessPages.get(i).setReadRight(listOfAccessFunctions.get(i).isRead());
                listOfUserAccessPages.get(i).setWriteRight(listOfAccessFunctions.get(i).isWrite());
                listOfUserAccessPages.get(i).setAccessRight(listOfAccessFunctions.get(i).isAccess());
                listOfUserAccessPages.get(i).setSelected(true);
            }

            for (Ebo eachEbo : listOfUserAccessPages) {
                System.out.println("MENU >>>> " + eachEbo.getMainMenu() + "  SUBMENU >>>> " + eachEbo.getSubmenu() + "  SELECTED >>>> " + eachEbo.isSelected() + " WRITE >>>>" + eachEbo.isWriteRight() + " READ >>>>" + eachEbo.isReadRight() + " PRINT >>>>" + eachEbo.isPrintRight() + " ACCESS >>>>" + eachEbo.isAccessRight());

                System.out.println("<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfUserAccessPages;
    }

    public static String userPagesBinder(List<Ebo> listOfUserPages) {
        String userPages = "";
        if (listOfUserPages.isEmpty()) {
            return "<NO_USER_PAGES>";
        }
        int counter = 0;
        for (Ebo eachSelectedUserPage : listOfUserPages) {
            if (eachSelectedUserPage.isSelected()) {
                if (counter == 0) {
                    userPages += AppMainMenus.getAppMenuCodeByMenuName(eachSelectedUserPage.getMainMenu()) + "|" + eachSelectedUserPage.getEboId() + "/"
                            + String.valueOf(eachSelectedUserPage.isWriteRight()) + "-" + String.valueOf(eachSelectedUserPage.isReadRight()) + "-"
                            + String.valueOf(eachSelectedUserPage.isPrintRight() + "-" + String.valueOf(true));
                } else {
                    userPages += "#" + AppMainMenus.getAppMenuCodeByMenuName(eachSelectedUserPage.getMainMenu()) + "|" + eachSelectedUserPage.getEboId() + "/"
                            + String.valueOf(eachSelectedUserPage.isWriteRight()) + "-" + String.valueOf(eachSelectedUserPage.isReadRight()) + "-"
                            + String.valueOf(eachSelectedUserPage.isPrintRight() + "-" + String.valueOf(true));
                }
                counter++;
            }

        }
//        System.out.println("THE USER PAGES IS " + userPages);
        return userPages;
    }

    private static String userRightFunctions(boolean right) {
        String userFunctions = "";
//        String rightType = "";
//        if (eachSelectedUserPage.isWriteRight()) {
//            rightType = "W";
//            userFunctions += rightType;
//        } else if (eachSelectedUserPage.isReadRight()) {
//            rightType = "-R";
//            userFunctions += rightType;
//        } else if (eachSelectedUserPage.isPrintRight()) {
//            rightType = "-P";
//            userFunctions += rightType;
//        } else if (true) {
//            rightType = "-A";
//            userFunctions += rightType;
//        }
        return userFunctions;
    }

    public static List<Course> coursesSplitterList(String courseInitialAndCode) {
        List<Course> listOfCourses = new ArrayList<Course>();
        Course course = new Course();
        String[] selected = courseInitialAndCode.split("/");
        for (String string : selected) {
            String[] initialCode = string.split(" ");
            System.out.println("SEARCH BY " + initialCode[0] + "  " + initialCode[1]);
            course = TisEjbSource.getBasicQuariesInstance().getCourseByInitialsAndCode(initialCode[0], initialCode[1]);
            listOfCourses.add(course);
            course = new Course();
        }
        return listOfCourses;
    }

    public static Course getCourseSplitter(String courseInitialAndCode) {
        List<Course> listOfCourses = new ArrayList<Course>();
        Course course = new Course();
        String[] selected = courseInitialAndCode.split("/");
        for (String string : selected) {
            String[] initialCode = string.split(" ");
            course = TisEjbSource.getBasicQuariesInstance().getCourseByInitialsAndCode(initialCode[0], initialCode[1]);
            listOfCourses.add(course);
//            course = new Course();
        }
        return course;
    }

    public static String courseBinder(List<Course> listOfCourses) {
        String selectedCourses = "";
        if (listOfCourses.isEmpty()) {
            return "<NO COURSES>";
        }
        int counter = 0;
        for (Course eachSelectedCourse : listOfCourses) {
            if (counter == 0) {
                selectedCourses += eachSelectedCourse.getCourseInitials() + " " + eachSelectedCourse.getCourseCode();
            } else {
                selectedCourses += "/" + eachSelectedCourse.getCourseInitials() + " " + eachSelectedCourse.getCourseCode();
            }
            counter++;
        }

        return selectedCourses;
    }

    public static String accessPageBinder(List<AccessPage> listOfAccessPages) {
        String accessPagesList = "";
        if (listOfAccessPages.isEmpty()) {
            return "<NO COURSES>";
        }
        int counter = 0;
        for (AccessPage eachAccessPage : listOfAccessPages) {
            if (counter == 0) {
                accessPagesList += eachAccessPage.getPageName();
            } else {
                accessPagesList += "/" + eachAccessPage.getPageName();
            }
            counter++;
        }

        return accessPagesList;
    }

    public static String bindUserPages(List<AppPageSettings> listOfAppPageSettingses) {
        String accessPagesList = "";
        if (listOfAppPageSettingses.isEmpty()) {
            return "<NO_PAGES>";
        }
        int counter = 0;
        for (AppPageSettings eachAccessPage : listOfAppPageSettingses) {
            if (eachAccessPage.isSelected()) {
                System.out.println("THE PAGE NAME IS "+eachAccessPage.getPageName());
                List<Ebo> pagesList = TisEjbSource.getCrudInstance().eboFindByAttribute("pageName", eachAccessPage.getPageName(), "STRING");
                if (!pagesList.isEmpty()) {
                    System.out.println("THE EBO ID IS "+pagesList.get(0).getEboId());
                    if (counter == 0) {
                        accessPagesList += pagesList.get(0).getEboId();
                    } else {
                        accessPagesList += "/" + pagesList.get(0).getEboId();
                    }
                    counter++;

                }
                
            }
        }

        return accessPagesList;
    }

    public static int totalCreditCourses(List<Course> listOfCourses) {
        if (listOfCourses.isEmpty()) {
            return 0;
        }
        int counter = 0;
        for (Course eachSelectedCourse : listOfCourses) {
            counter += eachSelectedCourse.getCreditHours();
        }
        return counter;
    }
}
