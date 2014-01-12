/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.ejb.sessionbeans;

import com.noad.solutions.tis.ejb.entities.AcademicYear;
import com.noad.solutions.tis.ejb.entities.AccessPage;
import com.noad.solutions.tis.ejb.entities.Course;
import com.noad.solutions.tis.ejb.entities.IncompleteCourse;
import com.noad.solutions.tis.ejb.entities.ProgramCourse;
import com.noad.solutions.tis.ejb.entities.SemesterRegistration;
import com.noad.solutions.tis.ejb.entities.Staff;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.ejb.entities.StudentLevel;
import com.noad.solutions.tis.ejb.entities.UserAccount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author daud
 */
@Stateless
@LocalBean
public class TisBasicQuariesSessionbean {

    @PersistenceContext(unitName = "tis-EJBPU")
    private EntityManager em;

    public List<Staff> getDepartmentStaffs() {
        return null;


    }

    public StudentLevel checkDuplicatPromotion(String studentId, String academicYear, String academicLevel) {
        try {
            String queryString = "SELECT s FROM StudentLevel s WHERE s.student.studentId='" + studentId + "' "
                    + "AND s.academicYear='" + academicYear + "' AND s.academicLevel'" + academicLevel + "'";
            StudentLevel studentLevel = (StudentLevel) em.createQuery(queryString).getSingleResult();
            return studentLevel;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;

    }

    public List<Student> getStudentsAcademicStatus(String selectedProgram, String selectedLevel, String selectedYear, String selectedStatus, String statusType) {
        try {
            List<Student> listOfStudents = null;
            String queryString = "SELECT u FROM Student u WHERE u.program.programId='" + selectedProgram + "' "
                    + "AND u.academicStatus='" + selectedStatus + "'" + " AND u.admissionYear='" + selectedYear + "'";
            if (selectedStatus.equalsIgnoreCase("Completed")) {
            } else if (statusType.equalsIgnoreCase("STUDENTS_WITH_SELECTED_ACADEMIC_STATUS")) {
                queryString += " AND u.currentLevel.academicLevelId='" + selectedLevel + "'";
            } else if (statusType.equalsIgnoreCase("STUDENTS_READING_SELECTED_PROGRAM")) {
            }
            listOfStudents = em.createQuery(queryString).getResultList();
            return listOfStudents;

        } catch (Exception e) {
            appLogger(e);
        }
        return null;

    }

    public List<ProgramCourse> getProgramCourseAndSemester(String selectedProgram, String selectedSemester) {

        try {
            List<ProgramCourse> listOfProgramCourses = null;
            String queryString = "SELECT u FROM ProgramCourse u WHERE u.program.programId=:selectedProgram "
                    + "AND u.semester=:selectedSemester AND u.removed='NO'";
            listOfProgramCourses = em.createQuery(queryString).setParameter("selectedProgram", selectedProgram)
                    .setParameter("selectedSemester", selectedSemester).getResultList();
            return listOfProgramCourses;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;
    }

    public List<SemesterRegistration> getSemesterRegisteredStudents(String selectedProgram, String selectedLevel) {

        try {
            List<SemesterRegistration> listOfSemesterRegistrations = null;
            String queryString = "SELECT u FROM SemesterRegistration u WHERE "
                    + "u.student.program.programId='" + selectedProgram + "' AND "
                    + "u.academicLevel.academicLevelId='" + selectedLevel + "' AND "
                    + "u.academicYear.currentSemester='YES'";
            System.out.println("THE QUEARY IS "+queryString);
            listOfSemesterRegistrations = em.createQuery(queryString).getResultList();
            return listOfSemesterRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;
    }
    
    public List<Student> getRegisteredStudents(String selectedProgram, String selectedLevel) {

        try {
            List<Student> listOfSemesterRegistrations = null;
            String queryString = "SELECT u.student FROM SemesterRegistration u WHERE "
                    + "u.student.program.programId='" + selectedProgram + "' AND "
                    + "u.academicLevel.academicLevelId='" + selectedLevel + "' AND "
                    + "u.academicYear.currentSemester='YES'";
            System.out.println("THE QUEARY IS "+queryString);
            listOfSemesterRegistrations = em.createQuery(queryString).getResultList();
            
            return listOfSemesterRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;
    }
    public List<Student> getRegisteredStudent(String searchParameter,String searchValue, String selectedLevel) {

        try {
            List<Student> listOfSemesterRegistrations = null;
            String queryString = "SELECT u.student FROM SemesterRegistration u WHERE "
                    + "u.student." + searchParameter + "='"
                    + "u.academicLevel.academicLevelId='" + selectedLevel + "' AND "
                    + "u.academicYear.currentSemester='YES'";
            System.out.println("THE QUEARY IS "+queryString);
            listOfSemesterRegistrations = em.createQuery(queryString).getResultList();
            return listOfSemesterRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;
    }

    public List<IncompleteCourse> getStudentIncompleteCourse(String selectedStudent, String selectedSemester) {

        try {
            List<IncompleteCourse> listOfIncompleteCourses = null;
            String queryString = "SELECT u FROM IncompleteCourse u WHERE u.student.studentId=:selectedStudent AND "
                    + " u.academicYear.semester=:selectedSemester AND u.passed='NO'";
            Query query = (Query) em.createQuery(queryString);
            query.setParameter("selectedStudent", selectedStudent);
            query.setParameter("selectedSemester", selectedSemester);
            listOfIncompleteCourses = query.getResultList();
            return listOfIncompleteCourses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public Student findStudentByIndexNumber(String indexNumber) {
        try {
            String quertyString = "SELECT s FROM Student s WHERE s.studentIndexNumber='" + indexNumber + "'";
            Student student = (Student) em.createQuery(quertyString).getSingleResult();
            return student;
        } catch (Exception e) {
            System.out.println("UNABLE TO FIND STUDENT WITH INDEX NUMBER " + e.getCause());
            return null;
        }
    }
    //Gets all students under selected program based on their academic level

    public List<StudentLevel> getStudentsUnderSelectedProgram(String selectedProgram, String selectedAcademicLevel) {
        try {
            List<StudentLevel> listOfStudents = null;
            String queryString = "SELECT s FROM StudentLevel s WHERE s.student.program.programId='" + selectedProgram + "'"
                    + " AND s.academicLevel='" + selectedAcademicLevel + "'";
            listOfStudents = em.createQuery(queryString).getResultList();
            return listOfStudents;
        } catch (Exception e) {
            System.out.println("THE CAUSE IS " + e.getCause());
            return null;
        }


    }

    public List<ProgramCourse> getProgramCourses(String selectedProgram, String selectedAcademicLevel, String selectedSemester) {
        try {
            List<ProgramCourse> listOfProgramCourse = null;
            String queryString = "SELECT p FROM ProgramCourse p WHERE ";
            queryString += "p.program.programId='" + selectedProgram + "' AND p.academicLevel.academicLevelId='"
                    + selectedAcademicLevel + "' " + "AND p.semester='" + selectedSemester + "'";

            System.out.println("THE QUARY OF PROGRAM COURSE IS " + queryString);
            listOfProgramCourse = em.createQuery(queryString).getResultList();
            return listOfProgramCourse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * GETS ALL PROGRAM ACADEMIC LEVELS @param programId
     */
    public List<String> getProgramAcademicLevels(String selectedProgram) {
        List<String> listOfProgramCourses = null;
        try {
            String queryString = "SELECT DISTINCT p.academicLevel.academicLevelId FROM ProgramCourse p WHERE p.program.programId ='" + selectedProgram + "'";
            listOfProgramCourses = em.createQuery(queryString).getResultList();
            return listOfProgramCourses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getUserDepartmentPrograms(String selectedDepartment) {
        List<String> listOfProgramCourses = null;
        try {
            String queryString = "SELECT DISTINCT p.program.programId FROM ProgramCourse p WHERE p.department.departmentId ='" + selectedDepartment + "'";
            listOfProgramCourses = em.createQuery(queryString).getResultList();
            return listOfProgramCourses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getDistinctAcademicYears() {
        try {
            List<String> distinctAcademicYears = new ArrayList<String>();
            String queryString = "SELECT DISTINCT u.academicYear FROM AcademicYear u ";
            distinctAcademicYears = em.createQuery(queryString).getResultList();
            System.out.println("THE DISTINCE ACADEMIC YEARS IS " + distinctAcademicYears.size());
            return distinctAcademicYears;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;

    }

    public List<StudentLevel> getStudentsByProgramsAndLevel(String selectedProgram, String selectedLevel, String selectedAcademicYear) {

        try {
            List<StudentLevel> listOfStudents = null;
            String queryString = "SELECT u FROM StudentLevel u WHERE u.student.program.programId='" + selectedProgram + "'"
                    + " AND u.academicLevel='" + selectedLevel + "' AND u.academicYear='" + selectedAcademicYear + "'"
                    + " ORDER BY u.student.surname , u.student.otherNames";
            System.out.println("THE QUARY IS " + queryString);
            listOfStudents = em.createQuery(queryString).getResultList();

            return listOfStudents;
        } catch (Exception e) {
            appLogger(e);
        }
        return Collections.EMPTY_LIST;

    }
    
    public List<Student> getStudentsProgramsAndLevel(String selectedProgram, String selectedLevel, String selectedAcademicYear) {

        try {
            List<Student> listOfStudents = null;
            String queryString = "SELECT u.student FROM SemesterRegistration u WHERE u.student.program.programId='" + selectedProgram + "'"
                    + " AND u.academicLevel.academicLevelId='" + selectedLevel + "' AND u.academicYear.academicYear='" + selectedAcademicYear + "'"
                    + " ORDER BY u.student.surname , u.student.otherNames";
            System.out.println("THE QUARY IS " + queryString);
            listOfStudents = em.createQuery(queryString).getResultList();

            return listOfStudents;
        } catch (Exception e) {
            appLogger(e);
        }
        return Collections.EMPTY_LIST;

    }

    public List<Student> getStudentsRegisteredForCourse() {

        try {
            List<SemesterRegistration> listRegisteredStudentsForCourse = null;
//            String queryString "SELECT u FROM SemesterRegistration u "
        } catch (Exception e) {
        }
        return null;


    }

    public List<String> getUserMenus(String userAccountId) {
        try {
            List<String> listOfUserMenus = null;
            String queryString = "SELECT DISTINCT u.userMenu FROM AccessPage u WHERE ";
            queryString += "u.userAccount='" + userAccountId + "' ORDER BY u.userMenu";
            listOfUserMenus = em.createQuery(queryString).getResultList();
            return listOfUserMenus;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<AccessPage> getUserAccessPages(String userAccountId) {
        List<AccessPage> listOfAccessPages = null;
        try {
            String qryString = "SELECT u FROM AccessPage u WHERE u.userAccount='" + userAccountId + "'";
            listOfAccessPages = em.createQuery(qryString).getResultList();
            return listOfAccessPages;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AccessPage getUserCurrentAccessPages(String pageName, String userAccountId) {
        try {
            AccessPage accessPage = null;
            String qryString = "SELECT u FROM AccessPage u WHERE u.userAccount='" + userAccountId + "'"
                    + " AND u.pageName ='" + pageName + "'";
            accessPage = (AccessPage) em.createQuery(qryString).getSingleResult();
            return accessPage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean getUserAccessPages(String userAccountId, String requestPage) {
        try {
            System.out.println("THE USER ID IS SESSIONBEANS >>>>>>>>>>>> " + userAccountId + "\t\t Page Name >>>>>>>>>>>>>> " + requestPage);
            List<AccessPage> allAccessPages = null;
            String qryString = "SELECT u FROM AccessPage u WHERE u.userAccount='" + userAccountId + "'"
                    + " AND u.pageName='" + requestPage + "'";
            allAccessPages = em.createQuery(qryString).getResultList();
            System.out.println("THE SIZE OF ACCESS PAGES IS " + allAccessPages.size());
            AccessPage accessPage = (AccessPage) em.createQuery(qryString).getSingleResult();
            if (accessPage == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteUserPages(String userId) {
        try {
            em.createQuery("DELETE FROM AccessPage u WHERE u.userAccount='" + userId + "'").executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkDuplicateUserAccounnt(String userAccount) {
        try {

            String queryString = "SELECT u FROM UserAccount u WHERE u.username='" + userAccount + "'";
            UserAccount ua = (UserAccount) em.createQuery(queryString).getSingleResult();
            if (ua == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<String> getAllCourseInitials() {

        try {
            List<String> listOfCourseInitials = null;
            String queryString = "SELECT DISTINCT c.courseInitials FROM Course c ";
            listOfCourseInitials = em.createQuery(queryString).getResultList();
            return listOfCourseInitials;
        } catch (Exception e) {
            System.out.println("COURSE ERROR " + e.getCause());
            return null;
        }

    }

    public List<String> getAllCourseCodes() {

        try {
            List<String> listOfCourseInitials = null;
            String queryString = "SELECT DISTINCT c.courseCode FROM Course c ";
            listOfCourseInitials = em.createQuery(queryString).getResultList();
            return listOfCourseInitials;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;

    }

    public List<String> getAllCourseCreditHours() {

        try {
            List<String> listOfCourseInitials = null;
            String queryString = "SELECT DISTINCT c.creditHours  FROM Course c ";
            listOfCourseInitials = em.createQuery(queryString).getResultList();
            return listOfCourseInitials;
        } catch (Exception e) {
            appLogger(e);
            System.out.println("COURSE ERROR " + e.getCause());
            return null;
        }


    }

    public List<Course> getAllCoursesWithSpecificCreditHours(double selectedHours) {
        try {
            List<Course> listOfCourseInitials = null;
            String queryString = "SELECT c  FROM Course c  WHERE c.creditHours=" + selectedHours;
            listOfCourseInitials = em.createQuery(queryString).getResultList();
            return listOfCourseInitials;
        } catch (Exception e) {
            System.out.println("COURSE ERROR " + e.getCause());
            return null;
        }

    }

    public AcademicYear getCurrentAcademicYear() {

        try {
            String queryString = "SELECT u FROM AcademicYear u  WHERE u.currentSemester='YES' AND u.removed='NO'";
            AcademicYear academicYear = (AcademicYear) em.createQuery(queryString).getSingleResult();
            System.out.println("THE CURRENT ACADEMIC YEAR QUARY IS " + queryString);
            return academicYear;
        } catch (Exception e) {
            e.printStackTrace();
            appLogger(e);
            System.out.println("ERROR GETTING DEFAULT ACADEMIC YEAR " + e.getMessage());
        }
        return null;


    }

    public List<ProgramCourse> loadProgramCourses(String selectedProgram, String selectedAcademicLevel, String selectedSemester) {
        try {
            List<ProgramCourse> listOfProgramCourse = null;
            String queryString = "SELECT p FROM ProgramCourse p WHERE ";
            if (selectedSemester.equals("all")) {
                queryString += "p.program.programId='" + selectedProgram + "' AND p.academicLevel.academicLevelId ='" + selectedAcademicLevel + "'";
            } else {
                queryString += "p.program.programId='" + selectedProgram + "' AND p.academicLevel.academicLevelId ='" + selectedAcademicLevel + "'"
                        + "AND p.semester='" + selectedSemester + "'";
            }
            listOfProgramCourse = em.createQuery(queryString).getResultList();
            return listOfProgramCourse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Staff> getTeachingStaff() {

        try {
            List<Staff> listOfStaffs = null;

            String queryString = "SELECT u FROM Staff u WHERE u.department.departmentCategory='Teaching' AND u.removed='NO'";
            listOfStaffs = em.createQuery(queryString).getResultList();
            return listOfStaffs;
        } catch (Exception e) {
            appLogger(e);
        }

        return null;

    }

    public Course getCourseByInitialsAndCode(String courseInitials, String courseCode) {
        try {
            List<Course> listOfCourses = null;
            String queryString = "SELECT u FROM Course u WHERE u.courseInitials='" + courseInitials + "' AND u.courseCode='" + courseCode + "'";
            listOfCourses = em.createQuery(queryString).getResultList();
            return listOfCourses.get(0);
        } catch (Exception e) {
            appLogger(e);
        }
        return null;
    }

    void appLogger(Exception e) {
        e.printStackTrace();
        Logger.getLogger(TisBasicQuariesSessionbean.class.getName()).log(Level.SEVERE, null, e.getCause());
    }
}
