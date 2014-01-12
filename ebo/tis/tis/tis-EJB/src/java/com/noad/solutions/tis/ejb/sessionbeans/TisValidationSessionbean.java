/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.ejb.sessionbeans;


import com.noad.solutions.tis.ejb.entities.AcademicLevel;
import com.noad.solutions.tis.ejb.entities.AcademicYear;
import com.noad.solutions.tis.ejb.entities.Course;
import com.noad.solutions.tis.ejb.entities.Department;
import com.noad.solutions.tis.ejb.entities.LecturerTeachingCourse;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.SemesterRegistration;
import com.noad.solutions.tis.ejb.entities.Staff;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.ejb.entities.UserAccount;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author daud
 */
@Stateless
@LocalBean
public class TisValidationSessionbean implements Serializable {

    @PersistenceContext(unitName = "tis-EJBPU")
    private EntityManager em;

    public UserAccount getUserAccessDetails(String username, String password) {

        try {
            UserAccount userAccount;
            try {
                String queryString = "SELECT u FROM UserAccount u WHERE u.username='" + username + "' AND u.password='" + password + "' "
                        + "AND u.status='Active'";
                userAccount = (UserAccount) em.createQuery(queryString).getSingleResult();
                System.out.println("THE USER ACCOUNT QUARY IS " + queryString);
                return userAccount;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("UNABLE TO GET USER DETAILS : CAUSE " + e.getMessage());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public boolean checkDuplicateDepartment(Department department) {
        try {

            String queryString = "SELECT u FROM Department u WHERE u.departmentName='" + department.getDepartmentName() + "'";
            Department depart = (Department) em.createQuery(queryString).getSingleResult();
            if (depart == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean checkDuplicateCourse(Course checkCourse) {

        try {
            Course course = new Course();
            String stringQuery = "SELECT c FROM Course c WHERE c.courseInitials='" + checkCourse.getCourseInitials() + "' AND c.courseCode='" + checkCourse.getCourseCode() + "'"
                    + "AND c.courseName ='" + checkCourse.getCourseName() + "'";
            course = (Course) em.createQuery(stringQuery).getSingleResult();
            if (course == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public boolean checkDuplicateProgram(Program program) {

        try {
            String stringQuery = "SELECT c FROM Program c WHERE c.programName='" + program.getProgramName() + "'";
            Program prog = (Program) em.createQuery(stringQuery).getSingleResult();
            if (prog == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public boolean checkDuplicateAcademicYear(AcademicYear academicYear) {
        try {
            String queryString = "SELECT u FROM AcademicYear u WHERE u.academicPeriod ='"
                    + academicYear.getAcademicYearId() + "' AND u.semester='"
                    + academicYear.getSemester() + "'";
            AcademicYear year = (AcademicYear) em.createQuery(queryString).getSingleResult();
            if (year == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR GETTING CHECKING DUPLICATE " + e.getMessage());
        }
        return false;


    }

    public boolean checkDuplicateAcademicLevel(AcademicLevel academicLevel) {
        try {
            System.out.println("inside duplicate");
            String queryString = "SELECT u FROM AcademicLevel u WHERE u.academicLevel ='" + academicLevel.getAcademicLevel() + "'";
            AcademicLevel level = (AcademicLevel) em.createQuery(queryString).getSingleResult();
            if (level == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR GETTING CHECKING DUPLICATE " + e.getMessage());
        }
        return false;


    }

    public boolean checkDuplicateStaff(Staff checkStaffDetails) {

        try {
            Staff staff = new Staff();
            String stringQuery = "SELECT c FROM Course c WHERE c.email='" + checkStaffDetails.getEmailAddress() + "' AND c.surname='" + checkStaffDetails.getSurname() + "'"
                    + "AND c.department.departmentId ='" + checkStaffDetails.getDepartment().getDepartmentId() + "'";
            staff = (Staff) em.createQuery(stringQuery).getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean checkDuplicateStudent(Student checkStaffDetails) {

        try {
            String stringQuery = "SELECT c FROM Student c WHERE c.studentId='" + checkStaffDetails.getStudentId() + "' OR c.studentIndexNumber='" + checkStaffDetails.getStudentIndexNumber() + "'";
            Student student = (Student) em.createQuery(stringQuery).getSingleResult();
            if (student == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public boolean checkDuplicateLecturerTeachingCourse(LecturerTeachingCourse lecturerTeachingCourse) {
        try {
            LecturerTeachingCourse lecCourse = new LecturerTeachingCourse();
            String queryString = "SELECT c LecturerTeachingCourse c WHERE c.lecturer.staffId='" + lecturerTeachingCourse.getLecturer().getStaffId()
                    + "' AND c.program.programId ='" + lecturerTeachingCourse.getProgram().getProgramId()
                    + "' AND c.course.courseId ='" + lecturerTeachingCourse.getCourses()
                    + "' AND c.semester ='" + lecturerTeachingCourse.getAcademicYear().getSemester()
                    + "' AND c.academicYear.academicYearId ='" + lecturerTeachingCourse.getAcademicYear().getAcademicYearId() + "' AND "
                    + "c.examiner.department.departmentId='" + lecturerTeachingCourse.getLecturer().getDepartment().getDepartmentId() + "'";
            lecCourse = (LecturerTeachingCourse) em.createQuery(queryString).getSingleResult();
            if (lecCourse == null) {
                return false;
            } else if (lecCourse != null) {
                return true;
            }
        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("THE ERRROR IS CAUSED BY " + e.getCause());

        }
        return false;

    }

    public boolean checkDuplicateSemesterRegistration(SemesterRegistration semesterRegistration) {
        try {
            String queryString = "SELECT r FROM SemesterRegistration r WHERE r.programCourse.programCourseId='" + semesterRegistration.getCourses() + "' ";
            queryString += " AND r.student.indexNumber='" + semesterRegistration.getStudent().getStudentIndexNumber() + "' AND r.academicLevel.academicLevelId='"
                    + semesterRegistration.getAcademicLevel().getAcademicLevelId() + "' AND r.academicYear.academicYearId='" + semesterRegistration.getAcademicYear().getAcademicYearId() + "'";
            em.createQuery(queryString).getResultList();
            return true;
        } catch (Exception e) {
            return false;
//            e.printStackTrace();
//            System.out.println("THE ERROR IS CAUSED BY " + e.getCause());
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
