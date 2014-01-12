/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.reportConverters;

import com.noad.solutions.common.classes.utils.BinderSplitter;
import com.noad.solutions.common.classes.utils.IncompleteType;
import com.noad.solutions.common.classes.utils.SingleCourseRegistration;
import com.noad.solutions.tis.ejb.entities.AcademicYear;
import com.noad.solutions.tis.ejb.entities.Course;
import com.noad.solutions.tis.ejb.entities.SemesterRegistration;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.ejb.entities.StudentLevel;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dawoode
 */
public class TisReportConverter {

    private static List<StudentProgramListData> listOfStudentProgramListDatas = null;
    private static List<StudentContactsData> listOfStudentContactsDatas = null;
    private static List<FullStudentInfoDetails> listOfFullStudentInfoDetails = null;
    private static List<StudentStatusData> listOfStudentStatusDatas = null;
    private static List<ExamsAttendaceSheetData> listOfExamsAttendaceSheetDatas = null;
    private static List<ExamsRawScoreSheetData> listOfRawScoreSheetDatas = null;
    private static List<SingleCourseRegistration> listOfSingleCourseRegistrations = null;
    private static List<StudentsRegisteredDeferredCourses> listOfStudentsRegisteredDeferredCourseses = null;

    public static List<StudentProgramListData> convertStudentProgramListData(List<Student> listOfStudents) {
        try {
            listOfStudentProgramListDatas = new ArrayList<StudentProgramListData>();
            StudentProgramListData spld = new StudentProgramListData();
            int counter = 1;
            for (Student eachStudent : listOfStudents) {
                spld.setCounter(String.valueOf(counter));
                spld.setGender(eachStudent.getGender());
                spld.setCurrentLevel(eachStudent.getCurrentLevel().getAcademicLevelId());
                spld.setDepartmentName(eachStudent.getDepartment().getDepartmentName());
                spld.setStudentFullNameWithTitle(eachStudent.getTitle() + " " + eachStudent.getSurname() + " " + eachStudent.getOtherNames());
                spld.setProgramName(eachStudent.getProgram().getProgramName());
                spld.setStudentIndexNumber(eachStudent.getStudentIndexNumber());
                spld.setStudentNumber(eachStudent.getStudentId());
                listOfStudentProgramListDatas.add(spld);
                counter++;
                spld = new StudentProgramListData();
            }
            return listOfStudentProgramListDatas;
        } catch (Exception e) {
            appLogger(e);
            return null;
        }

    }

    public static List<StudentContactsData> convertStudenConvertData(List<Student> listOfStudents) {
        try {
            listOfStudentContactsDatas = new ArrayList<StudentContactsData>();
            StudentContactsData scd = new StudentContactsData();
            int counter = 1;
            for (Student eachStudent : listOfStudents) {

                String studentFullName = eachStudent.getSurname() + " " + eachStudent.getOtherNames();
                scd.setCounter(String.valueOf(counter));
                scd.setStudentName(studentFullName);
                scd.setContactAddress(eachStudent.getContact());
                scd.setPostalAddress(eachStudent.getPostalAddress());
                scd.setEmailAddress(eachStudent.getEmailAddress());
                scd.setIndexNumber(eachStudent.getStudentIndexNumber());
                scd.setStudentId(eachStudent.getStudentId());
                listOfStudentContactsDatas.add(scd);
                scd = new StudentContactsData();
                counter++;
            }
            return listOfStudentContactsDatas;

        } catch (Exception e) {
            appLogger(e);
        }
        return null;
    }

    public static List<FullStudentInfoDetails> convertStudentStudentInfoDetails(List<Student> listOfStudents) {

        try {
            System.out.println("THE SIZE OF STUDENTS IS "+listOfStudents.size());
            listOfFullStudentInfoDetails = new ArrayList<FullStudentInfoDetails>();
            FullStudentInfoDetails studentInfoDetails = new FullStudentInfoDetails();
            int counte = 1;
            for (Student eachStudent : listOfStudents) {
                studentInfoDetails.setContactAddress(eachStudent.getContact());
                studentInfoDetails.setCounter(String.valueOf(counte));
                studentInfoDetails.setCurrentLevel(eachStudent.getCurrentLevel().getAcademicLevelId());
                studentInfoDetails.setDateOfAdmission(eachStudent.getDateOfAdmission());
                studentInfoDetails.setDateOfBirth(eachStudent.getDateOfBirth());
                studentInfoDetails.setDeformities(eachStudent.getDeformities());
                studentInfoDetails.setDepartmentName(eachStudent.getDepartment().getDepartmentName());
                studentInfoDetails.setEmailAddress(eachStudent.getEmailAddress());
                studentInfoDetails.setGender(eachStudent.getGender());
                studentInfoDetails.setGuardianceAddress(eachStudent.getGuardiance().getPostalAddress());
                studentInfoDetails.setGuardianceContact(eachStudent.getGuardiance().getContact());
                studentInfoDetails.setGuardianceEmail(eachStudent.getGuardiance().getEmailAddress());
                studentInfoDetails.setGuardianceName(eachStudent.getGuardiance().getName());
                studentInfoDetails.setGuardianceOccupation(eachStudent.getGuardiance().getOccupation());
                studentInfoDetails.setGuardianceRelationship(eachStudent.getGuardiance().getRelationship());
                studentInfoDetails.setHomeTown(eachStudent.getHomeTown());
                studentInfoDetails.setIndexNumber(eachStudent.getStudentIndexNumber());
                studentInfoDetails.setMaritalStatus(eachStudent.getMaritalStatus());
                studentInfoDetails.setNationality(eachStudent.getCountry());
                studentInfoDetails.setPostalAddress(eachStudent.getPostalAddress());
                studentInfoDetails.setProgramName(eachStudent.getProgram().getProgramName());
                studentInfoDetails.setStudentId(eachStudent.getStudentId());
                studentInfoDetails.setStudentName(eachStudent.getSurname().toUpperCase() + " " + eachStudent.getOtherNames());
                listOfFullStudentInfoDetails.add(studentInfoDetails);
                studentInfoDetails = new FullStudentInfoDetails();
                counte++;
            }
            System.out.println("THE TOTAL SIZE OF DETAIL STD INFO IS " + listOfFullStudentInfoDetails.size());
            return listOfFullStudentInfoDetails;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;

    }

    public static List<StudentStatusData> convertStudentStatus(List<Student> listOfStudents) {
        try {
            listOfStudentStatusDatas = new ArrayList<StudentStatusData>();
            StudentStatusData studentStatusData = new StudentStatusData();
            int counter = 1;
            for (Student eachStudent : listOfStudents) {
                studentStatusData.setCounter(String.valueOf(counter));
                studentStatusData.setAcademicLevel(eachStudent.getCurrentLevel().getAcademicLevelId());
                studentStatusData.setDateOfAdmission(eachStudent.getDateOfAdmission());
                studentStatusData.setGender(eachStudent.getGender());
                studentStatusData.setAcademicStatus(eachStudent.getAcademicStatus());
                studentStatusData.setAdmissionYear(eachStudent.getAdmissionYear());
                studentStatusData.setIndexNumber(eachStudent.getStudentIndexNumber());
                studentStatusData.setProgramName(eachStudent.getProgram().getProgramName());
                studentStatusData.setStudentId(eachStudent.getStudentId());
                studentStatusData.setStudentName(eachStudent.getSurname() + " " + eachStudent.getOtherNames());
                listOfStudentStatusDatas.add(studentStatusData);
                studentStatusData = new StudentStatusData();
                counter++;
            }
            System.out.println("THE SIZE OF STUDENT STATUS IS " + listOfStudentStatusDatas.size());
            return listOfStudentStatusDatas;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public static List<ExamsAttendaceSheetData> convertRegistrations(List<SemesterRegistration> listOfRegistrations) {
        try {
            ExamsAttendaceSheetData attendaceSheet = new ExamsAttendaceSheetData();
            listOfExamsAttendaceSheetDatas = new ArrayList<ExamsAttendaceSheetData>();
            int counter = 1;
            for (SemesterRegistration eachRegistration : listOfRegistrations) {
                attendaceSheet.setCounter(counter);
                attendaceSheet.setGender(eachRegistration.getStudent().getGender());
                attendaceSheet.setIndexNumber(eachRegistration.getStudent().getStudentIndexNumber());
                attendaceSheet.setSignature("");
                attendaceSheet.setStudentName(eachRegistration.getStudent().getSurname().concat(" ") + eachRegistration.getStudent().getOtherNames());
                listOfExamsAttendaceSheetDatas.add(attendaceSheet);
                attendaceSheet = new ExamsAttendaceSheetData();
                counter++;
            }
            return listOfExamsAttendaceSheetDatas;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;


    }

    public static List<ExamsRawScoreSheetData> convertToRawScoreSheet(List<SemesterRegistration> listOfRegistrations) {
        try {
            ExamsRawScoreSheetData rawScoreSheet = new ExamsRawScoreSheetData();
            listOfRawScoreSheetDatas = new ArrayList<ExamsRawScoreSheetData>();
            int counter = 1;
            for (SemesterRegistration eachRegistration : listOfRegistrations) {
                rawScoreSheet.setCounter(String.valueOf(counter));
                rawScoreSheet.setExamsMark("");
                rawScoreSheet.setIndexNumber(eachRegistration.getStudent().getStudentIndexNumber());
                rawScoreSheet.setMidTermMark("");
                rawScoreSheet.setCertify("");
                rawScoreSheet.setStudentName(eachRegistration.getStudent().getSurname().concat(" ") + eachRegistration.getStudent().getOtherNames());
                listOfRawScoreSheetDatas.add(rawScoreSheet);
                rawScoreSheet = new ExamsRawScoreSheetData();
                counter++;
            }
            return listOfRawScoreSheetDatas;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;


    }

    public static List<SingleCourseRegistration> convertToSingleCourseRegistration(List<SemesterRegistration> listOfRegistrations) {
        try {
            SingleCourseRegistration courseRegistration = new SingleCourseRegistration();
            listOfSingleCourseRegistrations = new ArrayList<SingleCourseRegistration>();
            int counter = 1;
            for (SemesterRegistration semesterRegistration : listOfRegistrations) {
                courseRegistration.setCounter(String.valueOf(counter));
                courseRegistration.setStudentName(semesterRegistration.getStudent().getSurname() + " " + semesterRegistration.getStudent().getOtherNames());
                courseRegistration.setLevel(semesterRegistration.getAcademicLevel().getAcademicLevelId());
                courseRegistration.setStudentId(semesterRegistration.getStudent().getStudentId());
                courseRegistration.setIndexNumber(semesterRegistration.getStudent().getStudentIndexNumber());
                listOfSingleCourseRegistrations.add(courseRegistration);
                courseRegistration = new SingleCourseRegistration();
                counter++;
            }
            return listOfSingleCourseRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;


    }

    public static List<StudentsRegisteredDeferredCourses> convertToStudentsRegisteredDeferredCourses(List<SemesterRegistration> listOfRegistrations) {

        try {
            StudentsRegisteredDeferredCourses srdc = new StudentsRegisteredDeferredCourses();
            listOfStudentsRegisteredDeferredCourseses = new ArrayList<StudentsRegisteredDeferredCourses>();
            AcademicYear academicYear = TisEjbSource.getBasicQuariesInstance().getCurrentAcademicYear();
            String program = listOfRegistrations.get(0).getStudent().getProgram().getProgramId();
            String level = listOfRegistrations.get(0).getAcademicLevel().getAcademicLevelId();

            for (SemesterRegistration eachRegistration : listOfRegistrations) {

                int deferres = TisEjbSource.getExamsProcessingInstance().countNumberOfIncompleteCourses(program, academicYear, level, IncompleteType.DEFERRED);
                int totalCreditRegistered = BinderSplitter.totalCreditCourses(BinderSplitter.coursesSplitterList(eachRegistration.getCourses()));
                List<Course> incompleteCourses = TisEjbSource.getExamsProcessingInstance().getStudentSemesterIncompleteCourses(eachRegistration.getStudent(), academicYear.getAcademicYearId(), level, IncompleteType.DEFERRED);

                srdc.setIndexNumber(eachRegistration.getStudent().getStudentIndexNumber());
                srdc.setStudentId(eachRegistration.getStudent().getStudentId());
                srdc.setStudentName(eachRegistration.getStudent().getSurname() + " " + eachRegistration.getStudent().getOtherNames());
                srdc.setCoursesRegistered(eachRegistration.getCourses());
                srdc.setTotalDeferredCourses(deferres);
                srdc.setTotalCreditRegistetered(totalCreditRegistered);
                srdc.setTotalCreditDeferred(BinderSplitter.totalCreditCourses(incompleteCourses));
                srdc.setCoursesDeferred(BinderSplitter.courseBinder(incompleteCourses));
                srdc.setTotalRegisteredCourses(BinderSplitter.coursesSplitterList(eachRegistration.getCourses()).size());

                listOfStudentsRegisteredDeferredCourseses.add(srdc);
                srdc = new StudentsRegisteredDeferredCourses();

            }
            return listOfStudentsRegisteredDeferredCourseses;
        } catch (Exception e) {
            appLogger(e);
        }
        return null;


    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public static List<StudentProgramListData> getListOfStudentProgramListDatas() {
        return listOfStudentProgramListDatas;
    }

    public static void setListOfStudentProgramListDatas(List<StudentProgramListData> listOfStudentProgramListDatas) {
        TisReportConverter.listOfStudentProgramListDatas = listOfStudentProgramListDatas;
    }

    public static List<StudentContactsData> getListOfStudentContactsDatas() {
        return listOfStudentContactsDatas;
    }

    public static List<ExamsAttendaceSheetData> getListOfExamsAttendaceSheetDatas() {
        return listOfExamsAttendaceSheetDatas;
    }

    public static void setListOfExamsAttendaceSheetDatas(List<ExamsAttendaceSheetData> listOfExamsAttendaceSheetDatas) {
        TisReportConverter.listOfExamsAttendaceSheetDatas = listOfExamsAttendaceSheetDatas;
    }

    public static List<ExamsRawScoreSheetData> getListOfRawScoreSheetDatas() {
        return listOfRawScoreSheetDatas;
    }

    public static void setListOfRawScoreSheetDatas(List<ExamsRawScoreSheetData> listOfRawScoreSheetDatas) {
        TisReportConverter.listOfRawScoreSheetDatas = listOfRawScoreSheetDatas;
    }

    public static void setListOfStudentContactsDatas(List<StudentContactsData> listOfStudentContactsDatas) {
        TisReportConverter.listOfStudentContactsDatas = listOfStudentContactsDatas;
    }

    public static List<FullStudentInfoDetails> getListOfFullStudentInfoDetails() {
        return listOfFullStudentInfoDetails;
    }

    public static void setListOfFullStudentInfoDetails(List<FullStudentInfoDetails> listOfFullStudentInfoDetails) {
        TisReportConverter.listOfFullStudentInfoDetails = listOfFullStudentInfoDetails;
    }

    public static List<StudentStatusData> getListOfStudentStatusDatas() {
        return listOfStudentStatusDatas;
    }

    public static void setListOfStudentStatusDatas(List<StudentStatusData> listOfStudentStatusDatas) {
        TisReportConverter.listOfStudentStatusDatas = listOfStudentStatusDatas;
    }
    //</editor-fold>

    static void appLogger(Exception e) {
        e.printStackTrace();
        Logger.getLogger(TisReportConverter.class.getName()).log(Level.SEVERE, null, e.getCause());
    }
}
