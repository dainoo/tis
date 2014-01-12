/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.ejb.sessionbeans;

import com.noad.solutions.tis.ejb.entities.AcademicLevel;
import com.noad.solutions.tis.ejb.entities.AcademicYear;
import com.noad.solutions.tis.ejb.entities.Course;
import com.noad.solutions.tis.ejb.entities.IncompleteCourse;
import com.noad.solutions.tis.ejb.entities.LecturerTeachingCourse;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.ProgramCourse;
import com.noad.solutions.tis.ejb.entities.SemesterRegistration;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.ejb.entities.StudentLevel;
import com.noad.solutions.tis.ejb.entities.StudentMark;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Daud
 */
@Stateless
@LocalBean
public class TisExamsProcessingSessionbean {

    @PersistenceContext(unitName = "tis-EJBPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

//<editor-fold defaultstate="collapsed" desc="COUNTS AND SUMS">
    //Finds the semester credit hours obtained : REASON
    public int getSemCreHrsObt(Student student, AcademicYear academicYear) {
        try {

            String queryString = "SELECT SUM(u.course.creditHours) FROM StudentMark u WHERE u.student.program.programId='"
                    + student.getProgram().getProgramId() + "' AND u.student.studentId='" + student.getStudentId() + "' "
                    + " AND u.academicYear.academicYearId='" + academicYear.getAcademicYearId() + "'";

            if (student.getProgram().getGradingSystem().equalsIgnoreCase("GPA")) {
                queryString += " AND u.totalMark >=" + 50;
            } else if (student.getProgram().getGradingSystem().equalsIgnoreCase("CWA")) {
                queryString += " AND u.totalMark >=" + 40;
            }
            Number totalNumber = (Number) em.createQuery(queryString).getSingleResult();
            return totalNumber.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    //Finds the semester credit hours registered
    public int getSemCreHrsReg(Student student, AcademicYear academicYear) {
        try {

            String queryString = "SELECT SUM(u.course.creditHours) FROM SemesterRegistration u WHERE u.student.program.programId='"
                    + student.getProgram().getProgramId() + "' AND u.student.studentId='" + student.getStudentId() + "' "
                    + " AND u.academicYear.academicYearId='" + academicYear.getAcademicYearId() + "'";

            Number totalNumber = (Number) em.createQuery(queryString).getSingleResult();
            return totalNumber.intValue();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    //Finds the cummumulative credit hours obtained : REASON
    public int getCummCreHrsObt(Student student) {
        try {

            String queryString = "SELECT SUM(u.course.creditHours) FROM StudentMark u WHERE u.student.program.programId='"
                    + student.getProgram().getProgramId() + "' AND u.student.studentId='" + student.getStudentId() + "' ";

            if (student.getProgram().getGradingSystem().equalsIgnoreCase("GPA")) {
                queryString += "AND u.grade<>'E' ";
            } else if (student.getProgram().getGradingSystem().equalsIgnoreCase("CWA")) {
                queryString += "AND u.grade<>='F' ";
            }
            Number totalNumber = (Number) em.createQuery(queryString).getSingleResult();
            return totalNumber.intValue();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Finds the cummumulative credit hours registered : REASON
    public int getCummCreHrsReg(Student student) {
        try {
            String queryString = "SELECT SUM(u.course.creditHours) FROM SemesterRegistration u WHERE u.student.program.programId='" + student.getProgram().getProgramId() + "' "
                    + "AND u.student.studentId='" + student.getStudentId() + "'";
            Number totalCummCreHrs = (Number) em.createQuery(queryString).getSingleResult();
            return totalCummCreHrs.intValue();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    public double getTotalSemWeightedMark(Student student, AcademicYear academicYear) {
        try {

            String queryString = null;
            String otherPart = " FROM StudentMark u WHERE u.student.program.programId='" + student.getProgram().getProgramId() + "' "
                    + "AND u.student.studentId='" + student.getStudentId() + "' "
                    + "AND u.academicYear.academicYearId='" + academicYear.getAcademicYearId() + "'";
            if (student.getProgram().getGradingSystem().equalsIgnoreCase("CWA")) {
                queryString = "SELECT SUM(u.totalMark*u.course.creditHours)" + otherPart;
            } else if (student.getProgram().getGradingSystem().equalsIgnoreCase("GPA")) {
                queryString = "SELECT SUM(u.gradePoint*u.course.creditHours)" + otherPart;
            }
            Number totalSemMark = (Number) em.createQuery(queryString).getSingleResult();
            System.out.println("THE QUERY IS " + queryString);
            return totalSemMark.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.00;
    }

    public double getTotalSemMark(Student student, AcademicYear academicYear) {
        try {

            String queryString = null;
            String otherPart = " FROM StudentMark u WHERE u.student.program.programId='" + student.getProgram().getProgramId() + "' "
                    + "AND u.student.studentId='" + student.getStudentId() + "'"
                    + "AND u.academicYear.academicYearId='" + academicYear.getAcademicYearId();
            if (student.getProgram().getGradingSystem().equalsIgnoreCase("CWA")) {
                queryString = "SELECT SUM(u.totalMark)" + otherPart;
            } else if (student.getProgram().getGradingSystem().equalsIgnoreCase("GPA")) {
                queryString = "SELECT SUM(u.gradePoint)" + otherPart;
            }
            Number totalSemMark = (Number) em.createQuery(queryString).getSingleResult();
            return totalSemMark.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.00;
    }

    public double getTotalCummWeightedMark(Student student) {
        try {

            String queryString = null;
            String otherPart = " FROM StudentMark u WHERE u.student.program.programId='" + student.getProgram().getProgramId() + "' "
                    + "AND u.student.studentId='" + student.getStudentId() + "'";
            if (student.getProgram().getGradingSystem().equalsIgnoreCase("CWA")) {
                queryString = "SELECT SUM(u.totalMark*u.course.creditHours)" + otherPart;
            } else if (student.getProgram().getGradingSystem().equalsIgnoreCase("GPA")) {
                queryString = "SELECT SUM(u.gradePoint*u.course.creditHours)" + otherPart;
            }
            Number totalCummMark = (Number) em.createQuery(queryString).getSingleResult();
            return totalCummMark.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.00;


    }

    public double getTotalCummMark(Student student) {
        try {

            String queryString = null;
            String otherPart = " FROM StudentMark u WHERE u.student.program.programId='" + student.getProgram().getProgramId() + "' "
                    + "AND u.student.studentId='" + student.getStudentId() + "'";
            if (student.getProgram().getGradingSystem().equalsIgnoreCase("CWA")) {
                queryString = "SELECT SUM(u.totalMark)" + otherPart;
            } else if (student.getProgram().getGradingSystem().equalsIgnoreCase("GPA")) {
                queryString = "SELECT SUM(u.gradePoint)" + otherPart;
            }
            Number totalCummMark = (Number) em.createQuery(queryString).getSingleResult();
            return totalCummMark.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.00;


    }

    //Finds number of grades in each course
    public int countTotalGradesPerCourse(String selectedCourse, String selectedProgram, AcademicYear academicYear, String selectedAcademicLevel) {

        try {
            String queryString = "SELECT COUNT(u.course.courseId) FROM StudentMark u WHERE u.student.program.programId='" + selectedProgram + "' "
                    + "AND u.course.courseId='" + selectedCourse + "' AND u.academicYear.academicYearId='"
                    + academicYear.getAcademicYearId() + "' AND u.academicYear.semester='" + academicYear.getSemester() + "' "
                    + "AND u.academicLevel.academicLevelId='" + selectedAcademicLevel + "'";

            Number totalCount = (Number) em.createQuery(queryString).getSingleResult();
            return totalCount.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Gets the total credit hours a selected course of the students
    public double getTotalCourseCreditHours(String selectedCourse, String selectedProgram, AcademicYear academicYear, String selectedAcademicLevel) {
        try {
            String queryString = "SELECT SUM(u.course.creditHours) FROM StudentMark u WHERE u.student.program.programId='" + selectedProgram + "' AND u.academicYear.academicYearId='"
                    + academicYear.getAcademicYearId() + "' AND u.academicYear.semester='" + academicYear.getSemester() + "' AND u.academicLevel.academicLevelId='" + selectedAcademicLevel + "'"
                    + "AND u.course.courseId='" + selectedCourse + "'";
            Number totalCreditHours = (Number) em.createQuery(queryString).getSingleResult();
            return totalCreditHours.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.00;

    }

    public int countNumberOfCoursesGradesObtained(String grade, String selectedProgram, AcademicYear academicYear, String selectedAcademicLevel) {

        try {
            String queryString = "SELECT COUNT(u.grade) FROM StudentMark u WHERE u.student.program.programId='" + selectedProgram + "' AND u.academicYear.academicYearId='"
                    + academicYear.getAcademicYearId() + "' AND u.academicYear.semester='" + academicYear.getSemester() + "' AND u.academicLevel.academicLevelId='" + selectedAcademicLevel + "'"
                    + "AND u.grade='" + grade + "'";
            Number totalCount = (Number) em.createQuery(queryString).getSingleResult();
            return totalCount.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countNumberOfIncompleteCourses(String selectedProgram, AcademicYear academicYear, String selectedAcademicLevel, String incompleteType) {

        try {
            String queryString = "SELECT COUNT(u.incompleteType) FROM IncompleteCourse u WHERE u.student.program.programId='" + selectedProgram + "' AND u.academicYear.academicYearId='"
                    + academicYear.getAcademicYearId() + "' AND u.academicYear.semester='" + academicYear.getSemester() + "' AND u.academicLevel.academicLevelId='" + selectedAcademicLevel + "'"
                    + "AND u.incompleteType='" + incompleteType + "'";
            Number totalCount = (Number) em.createQuery(queryString).getSingleResult();
            return totalCount.intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public double getMaxCourseMark(String selectedProgram, String selectedCourse, AcademicYear academicYear, String selectedAcademicLevel) {
        try {

            String queryString = "SELECT MAX(u.totalMark) FROM StudentMark u WHERE u.student.program.programId='" + selectedProgram + "' AND u.course.courseId='" + selectedCourse + "' AND u.academicYear.academicYearId='"
                    + academicYear.getAcademicYearId() + "' AND u.academicYear.semester='" + academicYear.getSemester() + "' AND u.academicLevel.academicLevelId='" + selectedAcademicLevel + "'";
            Number maxNumber = (Number) em.createQuery(queryString).getSingleResult();
            return maxNumber.doubleValue();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.00;

    }
    //Finds the maximum obtained in a selected course for the current semester

    public double getMinCourseMark(String selectedProgram, String selectedCourse, AcademicYear academicYear, String selectedAcademicLevel) {
        try {

            String queryString = "SELECT MIN(u.totalMark) FROM StudentMark u WHERE u.student.program.programId='" + selectedProgram + "' AND u.course.courseId='" + selectedCourse + "' AND u.academicYear.academicYearId='"
                    + academicYear.getAcademicYearId() + "' AND u.academicYear.semester='" + academicYear.getSemester() + "' AND u.academicLevel.academicLevelId='" + selectedAcademicLevel + "'";
            Number minNumber = (Number) em.createQuery(queryString).getSingleResult();
            return minNumber.doubleValue();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.00;

    }

    public int numberOfStudentPasses(String studentId, String academicYear, double passMark) {
        try {

            String queryString = "SELECT COUNT(u.midTermExamMark) FROM MidTermExamMark u WHERE u.student.studentFullId='" + studentId + "'"
                    + " AND u.academicYear='" + academicYear + "' AND u.midTermExamMark >=" + passMark;
            Object object = em.createQuery(queryString).getSingleResult();
            int numberOfPasses = Integer.parseInt(object.toString());
            return numberOfPasses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    public int countNumberOfGradesObtained(String grade, String selectedProgram, String selectedCourse, AcademicYear academicYear, String selectedAcademicLevel) {

        try {
            String queryString = "SELECT COUNT(u.grade) FROM StudentMark u WHERE u.student.program.programId='" + selectedProgram + "' AND u.course.courseId='" + selectedCourse + "' AND u.academicYear.academicYearId='"
                    + academicYear.getAcademicYearId() + "' AND u.academicYear.semester='" + academicYear.getSemester() + "' AND u.academicLevel.academicLevelId='" + selectedAcademicLevel + "'"
                    + "AND u.grade='" + grade + "'";
            Object object = em.createQuery(queryString).getSingleResult();
            int totalNumber = Integer.parseInt(object.toString());
            return totalNumber;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int countNumberOfOtherGradesObtained(String grade, String selectedProgram, String selectedCourse, AcademicYear academicYear, String selectedAcademicLevel) {

        try {
            String queryString = "SELECT COUNT(u.incompleteType) FROM IncompleteCourse u WHERE u.student.program.programId='" + selectedProgram + "' "
                    + "AND u.course.courseId='" + selectedCourse + "' AND u.academicYear.academicYearId='"
                    + academicYear.getAcademicYearId() + "' AND u.academicYear.semester='" + academicYear.getSemester() + "'"
                    + " AND u.academicLevel.academicLevelId='" + selectedAcademicLevel + "'"
                    + "AND u.passed='NO' AND u.incompleteType='" + grade + "'";
            Object object = em.createQuery(queryString).getSingleResult();
            int totalNumber = Integer.parseInt(object.toString());
            return totalNumber;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //</editor-fold> 
    public List<String> getDistinctStudentAcademicYear(String studentId) {

        try {
            List<String> listDistinctStudentAcademicYear = null;
            String queryString = "SELECT DISTINCT u.academicYear.academicYearId FROM StudentMark u WHERE u.student.studentId='" + studentId + "'"
                    + "ORDER BY u.academicYear.semester ASC";
            listDistinctStudentAcademicYear = em.createQuery(queryString).getResultList();
            return listDistinctStudentAcademicYear;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public List<String> getDistinctStudentAcademicLevel(String studentId) {

        try {
            List<String> listDistinctStudentAcademicLevel = null;
            String queryString = "SELECT DISTINCT u.academicLevel.academicLevelId FROM StudentMark u WHERE u.student.studentId='" + studentId + "'";
            listDistinctStudentAcademicLevel = em.createQuery(queryString).getResultList();
            return listDistinctStudentAcademicLevel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }
    //Find a student course mark for the semester

    public StudentMark getSemMark(Student student, String selectedCourse, AcademicYear academicYear, String selectedAcademicLevel) {
        StudentMark studentMark = null;
        try {

            String queryString = "SELECT u FROM StudentMark u WHERE u.student.program.programId='" + student.getProgram().getProgramId() + "' "
                    + "AND u.course.courseId='" + selectedCourse + "'" + " AND u.academicYear.academicYearId='" + academicYear.getAcademicYearId()
                    + "' AND u.academicYear.semester='" + academicYear.getSemester() + "'" + " AND u.academicLevel.academicLevelId='" + selectedAcademicLevel
                    + "' AND u.student.studentId='" + student.getStudentId() + "'";
            studentMark = (StudentMark) em.createQuery(queryString).getSingleResult();
            return studentMark;

        } catch (Exception e) {

            e.printStackTrace();

            return studentMark;
        }


    }

    public List<StudentMark> getSemStdtCourseMarks(Student student, String selectedLevel, String academicYear) {
        try {
            List<StudentMark> listStudentMarks = null;
            String queryString = "SELECT u FROM StudentMark u WHERE u.student.studentId='" + student.getStudentId() + "' AND "
                    + "u.student.program.programId='" + student.getProgram().getProgramId() + "'"
                    + " AND u.academicLevel.academicLevelId='" + selectedLevel + "' AND u.academicYear.academicYearId='" + academicYear + "'";

            System.out.println("THE QUERY IS " + queryString);
            listStudentMarks = em.createQuery(queryString).getResultList();
            return listStudentMarks;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<StudentMark> getFailed() {

        List<StudentMark> failedList;
        String qry = "SELECT u FROM StudentMark u WHERE u.totalMark<" + 50;
        failedList = em.createQuery(qry).getResultList();

        return failedList;

    }

    public List<String> getCummStudentRegisteredCourses(Student student) {
        try {
            List<String> studentCourses = null;
            String queryString = "SELECT u.courses FROM SemesterRegistration u WHERE u.student.program.programId='" + student.getProgram().getProgramId() + "' "
                    + "AND u.student.studentId='" + student.getStudentId() + "'";
            studentCourses = em.createQuery(queryString).getResultList();
            return studentCourses;
        } catch (Exception e) {
            e.printStackTrace();
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

    public List<IncompleteCourse> getStudentTrailedCourses(Student student, String incompleteType) {

        try {
            List<IncompleteCourse> listOfIncompleteCourses = null;
            String queryString = "SELECT u FROM IncompleteCourse u WHERE u.student.studentId='" + student.getStudentId() + "' ";
            if (incompleteType.equalsIgnoreCase("ALL")) {
                queryString += "AND u.passed='NO'";

            } else {
                queryString = "SELECT u FROM IncompleteCourse u WHERE u.student.studentId='" + student.getStudentId() + "'"
                        + " AND u.passed='NO' AND u.incompleteType='" + incompleteType + "'";
            }
            listOfIncompleteCourses = em.createQuery(queryString).getResultList();
            return listOfIncompleteCourses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public List<Student> getIncompeletStds(Program program) {
        try {
            List<Student> listIncompleteStds = null;
            String queryString = "SELECT DISTINCT u.student FROM IncompleteCourse u  WHERE u.student.program.programId='"
                    + program.getProgramId() + "' AND u.passed='NO'";
            listIncompleteStds = em.createQuery(queryString).getResultList();
            return listIncompleteStds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public IncompleteCourse getStudentPreviousIncompleteCourses(Student student, Course course, String incompleteType) {
        try {
            String queryString = "SELECT u FROM IncompleteCourse u WHERE u.student.studentId='" + student.getStudentId() + "' "
                    + "AND u.course.courseId='" + course.getCourseId() + "' AND u.passed='NO' "
                    + "AND u.incompleteType='" + incompleteType + "'";
            IncompleteCourse incompleteCourse = (IncompleteCourse) em.createQuery(queryString).getSingleResult();
            return incompleteCourse;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;
    }

    public List<IncompleteCourse> getStudentIncompleteCourses(Student student, Course course, String incompleteType) {
        try {
            List<IncompleteCourse> listOfIncompleteCourses = null;
            String queryString = "SELECT u FROM IncompleteCourse u WHERE u.student.studentId='" + student.getStudentId() + "' "
                    + "AND u.course.courseId='" + course.getCourseId() + "' AND u.passed='NO' "
                    + "AND u.incompleteType='" + incompleteType + "'";
            listOfIncompleteCourses = em.createQuery(queryString).getResultList();
            return listOfIncompleteCourses;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;
    }

    public List<Course> getStudentSemesterIncompleteCourses(Student student, String academicYear, String level, String incompleteType) {
        try {
            List<Course> listOfIncompleteCourses = null;
            String queryString = "SELECT u.course FROM IncompleteCourse u WHERE u.student.studentId='" + student.getStudentId() + "' "
                    + "AND u.academicLevel.academicLevelId='" + level + "' AND u.passed='NO' "
                    + "AND u.incompleteType='" + incompleteType + "' AND u.academicYear.academicYearId='" + academicYear + "'";
            System.out.println("THE QUARY IS INCOMPLETE IS "+queryString);
            listOfIncompleteCourses = em.createQuery(queryString).getResultList();
            return listOfIncompleteCourses;
        } catch (Exception e) {
            appLogger(e);
        }
        return Collections.EMPTY_LIST;
    }

    public List<Student> getSelectedProgramAndLevelStudents(String selectedProgram, String selectedAcademicLevel, String selectedYear) {
        try {
            List<Student> listStudents = null;
            String queryString = "SELECT u.student FROM SemesterRegistration u WHERE u.student.program.programId='"
                    + selectedProgram + "' AND u.removed='NO' "
                    + "AND u.academicLevel.academicLevelId='" + selectedAcademicLevel + "' AND u.academicYear.academicYearId='" + selectedYear + "'";
            System.out.println("THE QUARY IS " + queryString);
            listStudents = em.createQuery(queryString).getResultList();
            return listStudents;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<Student> getSelectedProgramAndLevelStudents(String selectedProgram, String selectedAcademicLevel) {
        try {
            List<Student> listStudents = null;
            String queryString = "SELECT DISTINCT u.student FROM StudentMark u WHERE u.student.program.programId='"
                    + selectedProgram + "' AND u.removed='NO' "
                    + "AND u.academicLevel.academicLevelId='" + selectedAcademicLevel + "'";
            System.out.println("THE QUARY IS " + queryString);
            listStudents = em.createQuery(queryString).getResultList();
            return listStudents;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<StudentMark> getCummMark(Student student) {
        try {

            List<StudentMark> studentMarks;
            String queryString = null;
            queryString += "FROM StudentMark u WHERE u.student.program.programId='" + student.getProgram().getProgramId() + "' "
                    + "AND u.student.studentId='" + student.getStudentId() + "'";
            studentMarks = em.createQuery(queryString).getResultList();
            return studentMarks;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<StudentMark> getCummMarkPassed(Student student) {
        try {

            List<StudentMark> studentMarks;
            String queryString = "SELECT u FROM StudentMark u WHERE u.student.program.programId='" + student.getProgram().getProgramId() + "' "
                    + "AND u.student.studentId='" + student.getStudentId() + "'";
//            if (student.getProgram().getGradingSystem().equalsIgnoreCase("GPA")) {
//
//                queryString += "' AND u.grade <>'E'";
//
//            } else if (student.getProgram().getGradingSystem().equalsIgnoreCase("CWA")) {
//                queryString += "' AND u.grade <>'F'";
//            }
            studentMarks = em.createQuery(queryString).getResultList();
            return studentMarks;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Student> getPassedStudents(String ignoredStudents, String selectedProgram) {
        try {
            List<Student> listStudents = null;
            String queryString = "SELECT u FROM Student u WHERE u.program.programId='" + selectedProgram + "' AND u.deleted='NO' AND " + ignoredStudents;
            listStudents = em.createQuery(queryString).getResultList();
            return listStudents;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<String> getDeferredStds(Program program, AcademicLevel academicLevel) {
        try {
            List<String> listIncompleteStds = null;
            String queryString = "SELECT DISTINCT u.student.studentId FROM IncompleteCourse u  WHERE u.incompleteType='Df' AND u.passed='NO'";
            listIncompleteStds = em.createQuery(queryString).getResultList();

            return listIncompleteStds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<String> getTrailedStds(Program program, AcademicLevel academicLevel) {
        try {
            List<String> listIncompleteStds = null;
            String queryString = "SELECT DISTINCT u.student.studentId FROM IncompleteCourse u  WHERE u.incompleteType='Trailed' AND u.passed='NO'";
            listIncompleteStds = em.createQuery(queryString).getResultList();
            return listIncompleteStds;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<IncompleteCourse> getStudentIncompleteCourses(Student student) {

        try {
            List<IncompleteCourse> listOfIncompleteCourses = null;
            String queryString = "SELECT u FROM IncompleteCourse u WHERE u.student.studentId='" + student.getStudentId() + "'"
                    + " AND u.passed='NO' ";
            listOfIncompleteCourses = em.createQuery(queryString).getResultList();
            return listOfIncompleteCourses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    public List<IncompleteCourse> getStudentDeferredCourses(Student student) {

        try {
            List<IncompleteCourse> listOfIncompleteCourses = null;
            String queryString = "SELECT u FROM IncompleteCourse u WHERE u.student.studentId='" + student.getStudentId() + "'"
                    + " AND u.passed='NO' AND u.incompleteType='Df'";
            listOfIncompleteCourses = em.createQuery(queryString).getResultList();
            return listOfIncompleteCourses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

    //Finds the current cummulated mark of the selected student
    public List<StudentMark> doCourseStatistics(String selectedProgram, AcademicYear academicYear, String selectedAcademicLevel) {
        try {
            List<StudentMark> listStudentMarks = null;
            String queryString = "SELECT u FROM StudentMark u WHERE u.student.program.programId='" + selectedProgram + "' AND u.academicYear.academicYearId='"
                    + academicYear.getAcademicYearId() + "' AND u.academicYear.semester='" + academicYear.getSemester() + "' AND u.academicLevel.academicLevelId" + selectedAcademicLevel + "'";
            listStudentMarks = em.createQuery(queryString).getResultList();
            return listStudentMarks;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*Gets all students marks from the selected program,academic level,
     * current academic year and current academic level
     */
    public List<StudentMark> getCurrentSemesterStudentCourseMark(String selectedProgram, String selectedCourse, AcademicYear academicYear, String selectedAcademicLevel) {
        try {
            List<StudentMark> listStudentMarks = null;
            String queryString = "SELECT u FROM StudentMark u WHERE u.student.program.programId='" + selectedProgram + "' AND u.course.courseId='" + selectedCourse + "' AND u.academicYear.academicYearId='"
                    + academicYear.getAcademicYearId() + "' AND u.academicYear.semester='" + academicYear.getSemester() + "' AND u.academicLevel.academicLevelId='" + selectedAcademicLevel + "'";
            listStudentMarks = em.createQuery(queryString).getResultList();
            return listStudentMarks;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<StudentMark> getStudentTranscriptMark(String studentId, String academicYear) {
        try {
            List<StudentMark> listStudentMarks = null;
            String queryString = "SELECT u FROM StudentMark u WHERE u.student.studentId='" + studentId + "' AND u.academicYear.academicYearId='"
                    + academicYear + "'";
            listStudentMarks = em.createQuery(queryString).getResultList();
            return listStudentMarks;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public StudentMark getSemCourseMark(String studentId, String selectedProgram, String selectedCourse, AcademicYear academicYear, String selectedAcademicLevel) {
        StudentMark studentMark = null;
        try {
            String queryString = "SELECT u FROM StudentMark u WHERE u.student.program.programId='" + selectedProgram + "' AND u.course.courseId='" + selectedCourse + "' AND u.academicYear.academicYearId='"
                    + academicYear.getAcademicYearId() + "' AND u.academicYear.semester='" + academicYear.getSemester() + "'"
                    + " AND u.academicLevel.academicLevelId='" + selectedAcademicLevel + "' AND u.student.studentId='" + studentId + "'";
            studentMark = (StudentMark) em.createQuery(queryString).getSingleResult();
            return studentMark;

        } catch (Exception e) {

            e.printStackTrace();

            return studentMark;
        }


    }

    public List<StudentMark> getAllStudentCourseMark(String studentId, String selectedProgram) {
        try {
            List<StudentMark> listStudentMarks = null;

            String queryString = "SELECT u FROM StudentMark u WHERE u.student.program.programId='" + selectedProgram
                    + "' AND u.student.studentId='" + studentId + "'";
            listStudentMarks = em.createQuery(queryString).getResultList();
            return listStudentMarks;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<StudentMark> getClassStudentsMarks(String selectedProgram, AcademicYear academicYear, String selectedCourse) {
        try {
            List<StudentMark> listOfStudentMarks = null;
            String queryString = "SELECT r FROM StudentMark r WHERE r.student.program.programId='" + selectedProgram + "' AND r.course.courseId ='" + selectedCourse + "'"
                    + " AND r.academicYear.academicYearId='" + academicYear.getAcademicYearId() + "'";
            listOfStudentMarks = em.createQuery(queryString).getResultList();
            return listOfStudentMarks;

        } catch (Exception e) {
            System.out.println("ERROR GETTING STUDENT SemesterRegistrationS : cause" + e.getCause());
            return null;
        }

    }

    public List<StudentMark> getStudentSemesterMarks(String selectedIndexNumber, AcademicYear academicYear) {
        try {
            List<StudentMark> listOfStudentMarks = null;
            String queryString = "SELECT r FROM StudentMark r WHERE r.student.studentIndexNumber='" + selectedIndexNumber + "'"
                    + " AND r.academicYear.academicYearId='" + academicYear.getAcademicYearId() + "'";
            listOfStudentMarks = em.createQuery(queryString).getResultList();
            return listOfStudentMarks;

        } catch (Exception e) {
            System.out.println("ERROR GETTING STUDENT SemesterRegistrationS : cause" + e.getCause());
            return null;
        }

    }

    public List<SemesterRegistration> getStudentsRegisteredForACourse(String selectedProgram, String selectedCourse) {
        try {
            List<SemesterRegistration> listOfRegisteredStudents = null;
            System.out.println("THE SELECTED COURSE IS " + selectedCourse);
            String queryString = "SELECT u FROM SemesterRegistration u WHERE u.student.program.programId='" + selectedProgram + "' "
                    + "AND u.academicYear.currentSemester='YES' AND u.courses LIKE '%" + selectedCourse + "%' "
                    + "AND u.uploadedMarks NOT LIKE '%" + selectedCourse + "%' ";
            System.out.println("THE QUARY IS " + queryString);
            listOfRegisteredStudents = em.createQuery(queryString).getResultList();
            return listOfRegisteredStudents;
        } catch (Exception e) {
            appLogger(e);

        }

        return null;

    }

    public List<SemesterRegistration> getStudentsRegisteredForACourse(String selectedProgram, String selectedCourse, String academicYear) {
        try {
            List<SemesterRegistration> listOfRegisteredStudents = null;
            System.out.println("THE SELECTED COURSE IS " + selectedCourse);
            String queryString = "SELECT u FROM SemesterRegistration u WHERE u.student.program.programId='" + selectedProgram + "' "
                    + "AND u.academicYear.currentSemester='YES' AND u.courses LIKE '%" + selectedCourse + "%' ";
            System.out.println("THE QUARY IS " + queryString);
            listOfRegisteredStudents = em.createQuery(queryString).getResultList();
            return listOfRegisteredStudents;
        } catch (Exception e) {
            appLogger(e);

        }

        return null;

    }

    public List<SemesterRegistration> getSemesterStudentsRegistions(String selectedProgram, String selectedLevel, String academicYear) {
        try {
            List<SemesterRegistration> listOfRegisteredStudents = null;
            System.out.println("THE SELECTED COURSE IS " + selectedLevel);
            String queryString = "SELECT u FROM SemesterRegistration u WHERE u.student.program.programId='" + selectedProgram + "' "
                    + "AND u.academicYear.academicYearId='" + academicYear + "' AND u.academicLevel.academicLevelId ='" + selectedLevel + "' ";
            System.out.println("THE QUARY IS " + queryString);
            listOfRegisteredStudents = em.createQuery(queryString).getResultList();
            return listOfRegisteredStudents;
        } catch (Exception e) {
            appLogger(e);

        }

        return null;

    }

    public List<SemesterRegistration> getStudentRegisteredSemesterCourses(String selectedStudent, String selectedAcademicYear) {
        try {
            List<SemesterRegistration> listOfRegisteredStudents = null;
            System.out.println("THE SELECTED COURSE IS " + selectedAcademicYear);
            String queryString = "SELECT u FROM SemesterRegistration u WHERE u.student.studentId='" + selectedStudent + "' "
                    + "AND u.academicYear.academicYearId='" + selectedAcademicYear + "' ";
            System.out.println("THE QUARY IS " + queryString);
            listOfRegisteredStudents = em.createQuery(queryString).getResultList();
            return listOfRegisteredStudents;
        } catch (Exception e) {
            appLogger(e);

        }

        return null;

    }

    public LecturerTeachingCourse getLecturerSemesterCourse(String lecturerId) {
        try {
            System.out.println("THE LECTURERER ID IS " + lecturerId);
            String queryString = "SELECT u FROM LecturerTeachingCourse u WHERE u.lecturer.staffId='"
                    + lecturerId + "' AND u.academicYear.currentSemester='YES' AND u.removed='NO'";

            LecturerTeachingCourse lecturerTeachingCourse = (LecturerTeachingCourse) em.createQuery(queryString).getSingleResult();
            System.out.println("THE LECT TEACHING IS " + queryString);
            return lecturerTeachingCourse;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;

    }

    void appLogger(Exception e) {
        e.printStackTrace();
        Logger
                .getLogger(TisExamsProcessingSessionbean.class
                .getName()).log(Level.SEVERE, null, e.getCause());
    }
}
