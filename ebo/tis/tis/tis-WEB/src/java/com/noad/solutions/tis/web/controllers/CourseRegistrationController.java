/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.BinderSplitter;
import com.noad.solutions.common.classes.utils.IncompleteType;
import com.noad.solutions.common.classes.utils.JSFIdGenerator;
import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.RegistrationCoursesDetails;
import com.noad.solutions.common.classes.utils.RegistrationSlips;
import com.noad.solutions.common.classes.utils.ReportParametersCommons;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.SelectedItemHelper;
import com.noad.solutions.common.classes.utils.UserSessionData;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.common.jsf.utils.JSFUtility;
import com.noad.solutions.common.report.utils.ReportUtility;
import com.noad.solutions.tis.ejb.entities.AcademicLevel;
import com.noad.solutions.tis.ejb.entities.AcademicYear;
import com.noad.solutions.tis.ejb.entities.Course;
import com.noad.solutions.tis.ejb.entities.IncompleteCourse;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.ProgramCourse;
import com.noad.solutions.tis.ejb.entities.SemesterRegistration;
import com.noad.solutions.tis.ejb.entities.Staff;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import java.io.InputStream;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author Daud
 */
@Named(value = "courseRegistrationController")
@SessionScoped
public class CourseRegistrationController implements Serializable {

    private IncompleteCourse incompeteCourse = new IncompleteCourse();
    private BinderSplitter courseProcessor = new BinderSplitter();
    private SemesterRegistration registration = new SemesterRegistration();
    private AcademicLevel academicLevel = new AcademicLevel();
    private AcademicYear academicYear = new AcademicYear();
    private SearchInputs searchInputs = new SearchInputs();
    private Staff staff = new Staff();
    private Student student = new Student();
    private Program program = new Program();
    private Course course = new Course();
    private SelectedItemHelper selectedItemHelper = new SelectedItemHelper();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private List<ProgramCourse> listOfProgramCourses;
    private List<Course> listOfCourseToRegister = new ArrayList<Course>();
    private List<IncompleteCourse> listOfIncompleteCourses = new ArrayList<IncompleteCourse>();
    private List<SemesterRegistration> listOfSemesterRegistrations = new ArrayList<SemesterRegistration>();
    private List<ProgramCourse> listOfDepartmentProgramCourses;
    private List<Course> listOfCourses = new ArrayList<Course>();
    private DataModel<ProgramCourse> programCourseDataModel;
    private DataModel<Course> coursesDataModel = new ListDataModel<Course>();
    private DataModel<Course> courseToRegisterDataModel = new ListDataModel<Course>();
    private DataModel<IncompleteCourse> incompleteCoursesDataModel;
    private DataModel<SemesterRegistration> semesterRegistrationsDataModel;
    private int totalCreditHours = 0, creditRegistered = 0;
    private String studentIndexNumber = null;
    private Boolean disableStudentDetails = false;
    private boolean showRemoveAll = false;
    private String studentPicture = "";
    private SelectItem[] programSelectItems = null;
    private UserSessionData userSessionData = new UserSessionData();

    public CourseRegistrationController() {
        try {

            userSessionData = JSFUtility.getUserSession(JSFUtility.SESSION_NAME);
            academicYear = TisEjbSource.getBasicQuariesInstance().getCurrentAcademicYear();
            System.out.println("THE ACADEMIC YEAR DETAILS IS " + academicYear.getAcademicYear() + "\t" + academicYear.getSemester());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchProgramCourses() {
        System.out.println("INSIDE PROGRAM COURSES ");
        try {
            pageCommonInputs.showDataInputsDisplay();
            listOfProgramCourses = new ArrayList<ProgramCourse>();
            programCourseDataModel = new ListDataModel<ProgramCourse>();
            System.out.println("THE PROG CRS PARAMETERS  " + selectedItemHelper.getSelectedProgram() + "\t" + selectedItemHelper.getSelectedAcademicLevel() + "\t" + academicYear.getSemester());
            listOfProgramCourses = TisEjbSource.getBasicQuariesInstance().getProgramCourses(selectedItemHelper.getSelectedProgram(), selectedItemHelper.getSelectedAcademicLevel(), academicYear.getSemester());
            System.out.println("THE SIZE OF PRG CRSES " + listOfProgramCourses.size());
            if (listOfProgramCourses.isEmpty()) {
                JSFMessages.errorMessage(JSFMessages.NO_DATA_FOUND + " For Courses");
            } else {
                totalCreditHours = 0;
                listOfCourses = new ArrayList<Course>();
                listOfCourses = BinderSplitter.coursesSplitterList(listOfProgramCourses.get(0).getCourses());
                System.out.println("THE SIZE AFTER SPLITTING COURSES " + listOfCourses.size());

                for (Course eachCourse : listOfCourses) {
                    totalCreditHours += eachCourse.getCreditHours();
                }
                System.out.println("TOTAL CREDIT IS " + totalCreditHours);
                coursesDataModel = new ListDataModel<Course>(listOfCourses);
                System.out.println("THE SIZE OF COURSES IS " + coursesDataModel.getRowCount());
            }
        } catch (Exception e) {
            System.out.println("THE CAUSE IS " + e.getCause());
        }

    }

    public void selectAllListener() {
        System.out.println("INSIDE SELECT ALL" + listOfCourses.size());
        System.out.println("INSIDE SELECT ALL VALUE " + pageCommonInputs.checkAllData);
        pageCommonInputs.showDataInputsDisplay();
        for (Course c : listOfCourses) {
            System.out.println("THE COURSE IS " + c.getCourseName());
        }
//            
        try {
            System.out.println("THE SIZE OF COURSES IN SELECT ALL LISTENER " + listOfCourses.size());
            if (pageCommonInputs.isCheckAllData()) {
                for (Course eachCourse : listOfCourses) {
                    eachCourse.setSelected(true);
                }
            } else if (!pageCommonInputs.isCheckAllData()) {
                for (Course eachCourse : listOfCourses) {
                    eachCourse.setSelected(false);
                }
            }
            coursesDataModel = new ListDataModel<Course>(listOfCourses);
            System.out.println("THE SIZE OF COURSES IN SELECT ALL LISTENER  AT THE END" + listOfCourses.size());

        } catch (Exception e) {
            appLogger(e);
        }


    }

    public void searchStudendIndexNumberListener() {
        System.out.println("INSIDE INDEX LISTENTER");
        System.out.println("THE NUMBER IS " + studentIndexNumber);
        try {

            student = TisEjbSource.getBasicQuariesInstance().findStudentByIndexNumber(studentIndexNumber);
            System.out.println("THE STUDEN DETAILS " + student.getSurname());
            if (student == null) {
                JSFMessages.errorMessage("No Such student Found");
            } else {
                programSelectItems = new SelectItem[1];
                programSelectItems[0] = new SelectItem(student.getProgram().getProgramId(), student.getProgram().getProgramName());
                listOfIncompleteCourses = TisEjbSource.getBasicQuariesInstance().getStudentIncompleteCourse(student.getStudentId(), academicYear.getSemester());
                System.out.println("THE SIZE OF INCOMPLETE COURSE IS " + listOfIncompleteCourses.size());
                incompleteCoursesDataModel = new ListDataModel<IncompleteCourse>(listOfIncompleteCourses);
                disableStudentDetails = true;
            }
        } catch (Exception e) {
            System.out.println("THE CAUSE IN STUDENT SEARCH " + e.getCause());
        }
    }

    public String newDataButton() {
        pageCommonInputs.showDataInputsDisplay();
        return "";
    }

    public void saveButton() {

        if (listOfCourseToRegister.isEmpty()) {
            JSFMessages.errorMessage("Please select course(s) to register");
        } else {
            try {
//                Checking if the student has deferred any of the courses
                academicYear = TisEjbSource.getBasicQuariesInstance().getCurrentAcademicYear();
                academicLevel = TisEjbSource.getCrudInstance().academicLevelFind(selectedItemHelper.getSelectedAcademicLevel());
                List<Course> listOfDeferredCourses = new ArrayList<Course>();
                if (listOfDeferredCourses.size() < listOfCourseToRegister.size()) {

//                    Finding the student deferred courses
                    for (Course eachCourse : listOfCourses) {
                        if (listOfCourseToRegister.contains(eachCourse)) {
                        } else {
                            listOfDeferredCourses.add(eachCourse);
                        }
                    }
                    System.out.println("THE NUMBER DEFERRED IS " + listOfDeferredCourses.size());
//                  End of finding the student deferred courses

                    //Delete all his/her before deferred courses in the same semester

                    List<IncompleteCourse> deferredCoursesList = TisEjbSource.getExamsProcessingInstance().getStudentTrailedCourses(student, IncompleteType.DEFERRED);
                    if (deferredCoursesList.isEmpty()) {
                    } else {
                        for (IncompleteCourse deferredCourse : deferredCoursesList) {
                            if (listOfCourseToRegister.contains(deferredCourse.getCourse())) {
                                TisEjbSource.getCrudInstance().incompleteCourseDelete(deferredCourse, true);
                            }
                        }
                    }
                    //End of delete all his/her before deferred courses in the same semester


                    for (Course eachCourse : listOfDeferredCourses) {

                        IncompleteCourse ic = new IncompleteCourse();
                        deferredCoursesList = TisEjbSource.getExamsProcessingInstance().getStudentIncompleteCourses(student, eachCourse, IncompleteType.DEFERRED);

                        //First time of deferring the course

                        if (deferredCoursesList.isEmpty()) {
                            IncompleteCourse incompleteCourse = new IncompleteCourse();

                            incompleteCourse.setAcademicLevel(academicLevel);
                            incompleteCourse.setAcademicYear(academicYear);
                            incompleteCourse.setCourse(eachCourse);
                            incompleteCourse.setIncompleteType(IncompleteType.DEFERRED);
                            incompleteCourse.setPassed("NO");
                            incompleteCourse.setStudent(student);
                            incompleteCourse.setAttempts(1);
                            incompleteCourse.setIncompleteCourseId(student.getStudentIndexNumber() + "-" + JSFIdGenerator.generateRandomId());
                            TisEjbSource.getCrudInstance().incompleteCourseCreate(incompleteCourse);
                        } else {
                            //Just Update on the course in the same semester
                            ic = deferredCoursesList.get(0);
                            if (ic.getAcademicYear().getAcademicYearId().equals(academicYear.getAcademicYearId())) {
                                String incompleteId = ic.getIncompleteCourseId();
                                IncompleteCourse incompleteCourse = TisEjbSource.getCrudInstance().incompleteCourseFind(incompleteId);

                                incompleteCourse.setAcademicLevel(academicLevel);
                                incompleteCourse.setAcademicYear(academicYear);
                                incompleteCourse.setCourse(eachCourse);
                                incompleteCourse.setIncompleteType(IncompleteType.DEFERRED);
                                incompleteCourse.setPassed("NO");
                                incompleteCourse.setStudent(student);
                                TisEjbSource.getCrudInstance().incompleteCourseUpdate(incompleteCourse);
                            }
                        }

                    }


                }
                System.out.println("THE STUDENT ID IS " + student.getStudentId());
                //checks if the student has already registered
                listOfSemesterRegistrations = TisEjbSource.getCrudInstance().semesterRegistrationFindByAttribute("student.studentId", student.getStudentId(), "STRING", true);

                if (listOfSemesterRegistrations.isEmpty()) {
                    registration.setAcademicLevel(academicLevel);
                    registration.setCourses(BinderSplitter.courseBinder(listOfCourseToRegister));
                    registration.setAcademicYear(academicYear);
                    registration.setRegisteredBy(userSessionData.getStaff());
                    registration.setRegisteredDate(new Date());
                    registration.setSemesterRegistrationId(JSFIdGenerator.generateRandomId());
                    registration.setStudent(student);
                    registration.setUploadedMarks("NO");
                    pageCommonInputs.setCheckIfSaved(TisEjbSource.getCrudInstance().semesterRegistrationCreate(registration));
                    if (pageCommonInputs.getCheckIfSaved() != null) {
                        JSFMessages.infoMessage(listOfCourseToRegister.size() + " Registered For " + registration.getAcademicYear().getAcademicYear() + " SEMESTER " + registration.getAcademicYear().getSemester());
                        resetButton();
                    } else {
                        JSFMessages.errorMessage("Failed To Register Selected Courses");
                    }
                } else {
                    String idToUpdate = listOfSemesterRegistrations.get(0).getSemesterRegistrationId();
                    SemesterRegistration updateRegistration = TisEjbSource.getCrudInstance().semesterRegistrationFind(idToUpdate);
                    System.out.println("THE SEMESTER REG ID IS " + updateRegistration.getSemesterRegistrationId());

                    updateRegistration.setAcademicLevel(academicLevel);
                    updateRegistration.setAcademicYear(academicYear);
                    updateRegistration.setCourses(BinderSplitter.courseBinder(listOfCourseToRegister));
                    updateRegistration.setRegisteredDate(new Date());
                    updateRegistration.setStudent(student);
                    updateRegistration.setUploadedMarks("NO");
                    updateRegistration.setRegisteredBy(userSessionData.getStaff());

                    pageCommonInputs.setCheckIfUpdated(TisEjbSource.getCrudInstance().semesterRegistrationUpdate(updateRegistration));
                    if (pageCommonInputs.isCheckIfUpdated()) {
                        JSFMessages.infoMessage(listOfCourseToRegister.size() + " Updated For " + updateRegistration.getAcademicYear().getAcademicYear() + " SEMESTER " + updateRegistration.getAcademicYear().getSemester());
//                        resetButton();
                    } else {
                        JSFMessages.errorMessage("Failed To Update Selected Courses");
                    }
                }

            } catch (Exception e) {
                appLogger(e);
            }
        }

    }

    public void updateButton() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String cancelButton() {
        resetButton();
        return "";

    }

    public void printButton() {
        if (listOfCourseToRegister.isEmpty()) {
            JSFMessages.warnMessage(JSFMessages.NO_DATA_TO_PRINT);
        } else {

            RegistrationCoursesDetails coursesDetails = new RegistrationCoursesDetails();
            List<RegistrationCoursesDetails> listOfegistrationCoursesDetailses = new ArrayList<RegistrationCoursesDetails>();
            int counter = 1;
            for (Course eachCourse : listOfCourseToRegister) {
                coursesDetails.setCounter(counter);
                String courseName = eachCourse.getCourseInitials().concat(" ") + eachCourse.getCourseCode().concat(" ") + eachCourse.getCourseName();
                coursesDetails.setCourseName(courseName);
                coursesDetails.setCreditHours(eachCourse.getCreditHours().intValue());
                listOfegistrationCoursesDetailses.add(coursesDetails);
                coursesDetails = new RegistrationCoursesDetails();
                counter++;
            }

            RegistrationSlips registrationSlips = new RegistrationSlips();
            List<RegistrationSlips> listOfRegistrationSlipses = new ArrayList<RegistrationSlips>();

            registrationSlips.setIndexNumber(student.getStudentIndexNumber());
            registrationSlips.setLevel(selectedItemHelper.getSelectedAcademicLevel());
            registrationSlips.setListOfRegistrationCoursesDetails(listOfegistrationCoursesDetailses);
            registrationSlips.setNationality(student.getCountry());
            registrationSlips.setNumberOfSubjects(listOfCourseToRegister.size());
            registrationSlips.setProgramName(student.getProgram().getProgramName());
            registrationSlips.setStudentId(student.getStudentId());
            registrationSlips.setStudentName(student.getSurname() + " " + student.getOtherNames());
            registrationSlips.setTotalDeferredCredits(totalCreditHours);
            registrationSlips.setTotalRegisteredCredits(BinderSplitter.totalCreditCourses(listOfCourseToRegister));

            listOfRegistrationSlipses.add(registrationSlips);
            listOfRegistrationSlipses.add(registrationSlips);


            String reportTitle = userSessionData.getCurrentAcademicYear().getAcademicYear() + " ACADEMIC YEAR";

            String fullFileName = ReportUtility.REPORT_FOLDER + "registration-subreport" + ReportUtility.REPORT_FILE_EXTENSION;
            InputStream reportFileInput = JSFUtility.externalContext().getResourceAsStream(fullFileName);
            HashMap hashMap = new HashMap();

            hashMap.put("programName", student.getProgram().getProgramName());
            hashMap.put("resultSlipSubreport", reportFileInput);
            hashMap.put("reportTitle", reportTitle);
            hashMap.putAll(ReportParametersCommons.getCommonParameters());

            System.out.println("THE SIZE OF RESULTS IS " + listOfRegistrationSlipses.size());
            System.out.println("THE SIZE OF RESULTS IS " + listOfRegistrationSlipses.get(0).getListOfRegistrationCoursesDetails().size());
//
            ReportUtility reportUtility = new ReportUtility(hashMap, "registration-slip", listOfRegistrationSlipses, ReportUtility.getPDF_FILE());
            reportUtility.generateReport();

        }
    }

    public void resetButton() {
        showRemoveAll = false;
        student = new Student();
        creditRegistered = 0;
        studentIndexNumber = null;
        listOfSemesterRegistrations = new ArrayList<SemesterRegistration>();
        semesterRegistrationsDataModel = new ListDataModel<SemesterRegistration>(listOfSemesterRegistrations);
        listOfCourseToRegister = new ArrayList<Course>();
        listOfCourses = new ArrayList<Course>();
        coursesDataModel = new ListDataModel<Course>(listOfCourses);
        listOfProgramCourses = new ArrayList<ProgramCourse>();
        programCourseDataModel = new ListDataModel<ProgramCourse>(listOfProgramCourses);
        selectedItemHelper = new SelectedItemHelper();
        incompeteCourse = new IncompleteCourse();
        listOfIncompleteCourses = new ArrayList<IncompleteCourse>();
        incompleteCoursesDataModel = new ListDataModel<IncompleteCourse>(listOfIncompleteCourses);
        courseToRegisterDataModel = new ListDataModel<Course>(listOfCourseToRegister);
        pageCommonInputs.showDataRecordsDisplay();
    }

    public void deleteButton() {
    }

    public String selectButton() {
        try {
//            programCourse = programCourseDataModel.getRowData();
//            programCourse.setSelected(true);
        } catch (Exception e) {
        }
        return null;
    }

    public String searchButton() {
        return "";

    }

    public String viewAllButton() {
        return "";
    }

    public void addSelectedCourse() {
        try {
            course = coursesDataModel.getRowData();
            boolean duplicate = false;
            for (Course eachCourseToRegister : listOfCourseToRegister) {
                if (course.getCourseName().equalsIgnoreCase(eachCourseToRegister.getCourseName())) {
                    duplicate = true;
                }
            }
            if (duplicate) {
                JSFMessages.warnMessage(course.getCourseName() + " Has Already Been Selected");
            } else {
                creditRegistered += course.getCreditHours();
                if (creditRegistered > 21) {
                    JSFMessages.warnMessage("Total Credit Hours Should Not Be More Than 21");
                } else {
                    listOfCourseToRegister.add(course);
                    courseToRegisterDataModel = new ListDataModel<Course>(listOfCourseToRegister);
                    course = new Course();

                }
            }

        } catch (Exception e) {
        }
    }

    public void addSelectedIncompleteCourse() {
        System.out.println("INSIDE SINGLE COURSE SELECTION");
        try {
            incompeteCourse = incompleteCoursesDataModel.getRowData();
            boolean duplicate = false;
            for (Course eachRegistratio : listOfCourseToRegister) {
                if (incompeteCourse.getCourse().getCourseName().equalsIgnoreCase(eachRegistratio.getCourseName())) {
                    course = incompeteCourse.getCourse();
                    duplicate = true;
                }
            }
            if (duplicate) {
                JSFMessages.warnMessage(course.getCourseName() + " Has Already Been Selected");
            } else {

                creditRegistered += incompeteCourse.getCourse().getCreditHours();
                listOfCourseToRegister.add(incompeteCourse.getCourse());
                courseToRegisterDataModel = new ListDataModel<Course>(listOfCourseToRegister);
                course = new Course();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addBulkCourses() {
        int counterChecked = 0;

        showRemoveAll = true;
        for (Course eachCourse : listOfCourses) {
            if (eachCourse.getSelected()) {
                counterChecked++;
            }
        }
        if (counterChecked == 0) {
            JSFMessages.warnMessage("Please Tick course(s)");
        } else {
            try {
                String duplicateCourse = "";
                boolean duplicate = false;

                for (Course eachProgramCourse : listOfCourses) {
                    if (eachProgramCourse.getSelected()) {

                        //CHECKS FOR DUPLICATES IN THE COURSES TO REGISTER
                        for (Course eachCourseSemesterRegistration : listOfCourseToRegister) {
                            if (eachProgramCourse.getCourseId().equalsIgnoreCase(eachCourseSemesterRegistration.getCourseId())) {
                                duplicateCourse += eachProgramCourse.getCourseName() + " ";
                                duplicate = true;
                            }
                        }
                        if (duplicate) {
                        } else {
                            creditRegistered += eachProgramCourse.getCreditHours();
                            listOfCourseToRegister.add(eachProgramCourse);
                        }

                    }

                }
                courseToRegisterDataModel = new ListDataModel<Course>(listOfCourseToRegister);
                for (Course eachProgramCourse : listOfCourses) {
                    eachProgramCourse.setSelected(false);
                }
                pageCommonInputs.setCheckAllData(false);
                if (duplicate) {
                    JSFMessages.warnMessage(duplicateCourse + " Have Already Been Selected");
                }

            } catch (Exception e) {
                System.out.println("THE ERROR IS CAUSED BY " + e.getCause());
            }
        }

    }

    public void addBulkIncompleteCourses() {
        int counterChecked = 0;
        for (IncompleteCourse eachCourse : listOfIncompleteCourses) {
            if (eachCourse.getCourse().getSelected()) {
                counterChecked++;
            }
        }
        if (counterChecked == 0) {
            JSFMessages.warnMessage("Please Tick course(s)");
        } else {
            try {
                for (IncompleteCourse eachCourse : listOfIncompleteCourses) {
                    if (eachCourse.getCourse().getSelected()) {

                        boolean duplicate = false;
                        //CHECKS FOR DUPLICATES IN THE COURSES TO REGISTER
                        for (Course eachCourseSemesterRegistration : listOfCourseToRegister) {
                            if (eachCourse.getCourse().getCourseId().equalsIgnoreCase(eachCourseSemesterRegistration.getCourseId())) {
                                duplicate = true;
                            }
                        }
                        if (duplicate) {
                        } else {
                            creditRegistered += eachCourse.getCourse().getCreditHours();
                            listOfCourseToRegister.add(eachCourse.getCourse());
                            course = new Course();
                        }

                    }

                }
                courseToRegisterDataModel = new ListDataModel<Course>(listOfCourseToRegister);

                for (IncompleteCourse eachCourse : listOfIncompleteCourses) {
                    eachCourse.getCourse().setSelected(false);
                }
                pageCommonInputs.setCheckAllData(false);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("THE ERROR IS CAUSED BY " + e.getCause());
            }
        }

    }

    public void removeCourse() {
        System.out.println("INSIDE REMOVE METHOD");
        try {
            course = courseToRegisterDataModel.getRowData();
            creditRegistered -= course.getCreditHours();
            listOfCourseToRegister.remove(course);
            if (listOfCourseToRegister.isEmpty()) {
                creditRegistered = 0;
            }
            courseToRegisterDataModel = new ListDataModel<Course>(listOfCourseToRegister);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeAllSelectedCourses() {
        try {
            creditRegistered = 0;
            listOfCourseToRegister = new ArrayList<Course>();
            courseToRegisterDataModel = new ListDataModel<Course>(listOfCourseToRegister);
            showRemoveAll = false;
//            listOfSemesterRegistrations = new ArrayList<SemesterRegistration>();
//            registrationDataModel = new ListDataModel<SemesterRegistration>(listOfSemesterRegistrations);
        } catch (Exception e) {
        }
    }

    public void resetDataTable() {
    }

    void appLogger(Exception e) {
        e.printStackTrace();
        Logger.getLogger(CourseRegistrationController.class.getName()).log(Level.SEVERE, null, e.getMessage());
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public IncompleteCourse getIncompeteCourse() {
        return incompeteCourse;
    }

    public void setIncompeteCourse(IncompleteCourse incompeteCourse) {
        this.incompeteCourse = incompeteCourse;
    }

    public SemesterRegistration getRegistration() {
        return registration;
    }

    public BinderSplitter getCourseProcessor() {
        return courseProcessor;
    }

    public UserSessionData getUserSessionData() {
        return userSessionData;
    }

    public void setUserSessionData(UserSessionData userSessionData) {
        this.userSessionData = userSessionData;
    }

    public boolean isShowRemoveAll() {
        return showRemoveAll;
    }

    public void setShowRemoveAll(boolean showRemoveAll) {
        this.showRemoveAll = showRemoveAll;
    }

    public void setCourseProcessor(BinderSplitter courseProcessor) {
        this.courseProcessor = courseProcessor;
    }

    public void setRegistration(SemesterRegistration registration) {
        this.registration = registration;
    }

    public AcademicLevel getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(AcademicLevel academicLevel) {
        this.academicLevel = academicLevel;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public SearchInputs getSearchInputs() {
        return searchInputs;
    }

    public void setSearchInputs(SearchInputs searchInputs) {
        this.searchInputs = searchInputs;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public SelectedItemHelper getSelectedItemHelper() {
        return selectedItemHelper;
    }

    public void setSelectedItemHelper(SelectedItemHelper selectedItemHelper) {
        this.selectedItemHelper = selectedItemHelper;
    }

    public PageCommonInputs getPageCommonInputs() {
        return pageCommonInputs;
    }

    public void setPageCommonInputs(PageCommonInputs pageCommonInputs) {
        this.pageCommonInputs = pageCommonInputs;
    }

    public List<ProgramCourse> getListOfProgramCourses() {
        return listOfProgramCourses;
    }

    public void setListOfProgramCourses(List<ProgramCourse> listOfProgramCourses) {
        this.listOfProgramCourses = listOfProgramCourses;
    }

    public List<Course> getListOfCourseToRegister() {
        return listOfCourseToRegister;
    }

    public void setListOfCourseToRegister(List<Course> listOfCourseToRegister) {
        this.listOfCourseToRegister = listOfCourseToRegister;
    }

    public List<IncompleteCourse> getListOfIncompleteCourses() {
        return listOfIncompleteCourses;
    }

    public void setListOfIncompleteCourses(List<IncompleteCourse> listOfIncompleteCourses) {
        this.listOfIncompleteCourses = listOfIncompleteCourses;
    }

    public List<SemesterRegistration> getListOfSemesterRegistrations() {
        return listOfSemesterRegistrations;
    }

    public void setListOfSemesterRegistrations(List<SemesterRegistration> listOfSemesterRegistrations) {
        this.listOfSemesterRegistrations = listOfSemesterRegistrations;
    }

    public List<ProgramCourse> getListOfDepartmentProgramCourses() {
        return listOfDepartmentProgramCourses;
    }

    public void setListOfDepartmentProgramCourses(List<ProgramCourse> listOfDepartmentProgramCourses) {
        this.listOfDepartmentProgramCourses = listOfDepartmentProgramCourses;
    }

    public List<Course> getListOfCourses() {
        return listOfCourses;
    }

    public void setListOfCourses(List<Course> listOfCourses) {
        this.listOfCourses = listOfCourses;
    }

    public DataModel<ProgramCourse> getProgramCourseDataModel() {
        return programCourseDataModel;
    }

    public void setProgramCourseDataModel(DataModel<ProgramCourse> programCourseDataModel) {
        this.programCourseDataModel = programCourseDataModel;
    }

    public DataModel<Course> getCoursesDataModel() {
        return coursesDataModel;
    }

    public void setCoursesDataModel(DataModel<Course> coursesDataModel) {
        this.coursesDataModel = coursesDataModel;
    }

    public DataModel<Course> getCourseToRegisterDataModel() {
        return courseToRegisterDataModel;
    }

    public void setCourseToRegisterDataModel(DataModel<Course> courseToRegisterDataModel) {
        this.courseToRegisterDataModel = courseToRegisterDataModel;
    }

    public DataModel<IncompleteCourse> getIncompleteCoursesDataModel() {
        return incompleteCoursesDataModel;
    }

    public void setIncompleteCoursesDataModel(DataModel<IncompleteCourse> incompleteCoursesDataModel) {
        this.incompleteCoursesDataModel = incompleteCoursesDataModel;
    }

    public DataModel<SemesterRegistration> getSemesterRegistrationsDataModel() {
        return semesterRegistrationsDataModel;
    }

    public void setSemesterRegistrationsDataModel(DataModel<SemesterRegistration> semesterRegistrationsDataModel) {
        this.semesterRegistrationsDataModel = semesterRegistrationsDataModel;
    }

    public int getTotalCreditHours() {
        return totalCreditHours;
    }

    public void setTotalCreditHours(int totalCreditHours) {
        this.totalCreditHours = totalCreditHours;
    }

    public int getCreditRegistered() {
        return creditRegistered;
    }

    public void setCreditRegistered(int creditRegistered) {
        this.creditRegistered = creditRegistered;
    }

    public String getStudentIndexNumber() {
        return studentIndexNumber;
    }

    public void setStudentIndexNumber(String studentIndexNumber) {
        this.studentIndexNumber = studentIndexNumber;
    }

    public Boolean getDisableStudentDetails() {
        return disableStudentDetails;
    }

    public String getStudentPicture() {
        return studentPicture;
    }

    public SelectItem[] getProgramSelectItems() {
        return programSelectItems;
    }

    public void setProgramSelectItems(SelectItem[] programSelectItems) {
        this.programSelectItems = programSelectItems;
    }

    public void setStudentPicture(String studentPicture) {
        this.studentPicture = studentPicture;
    }

    public void setDisableStudentDetails(Boolean disableStudentDetails) {
        this.disableStudentDetails = disableStudentDetails;
    }
    //</editor-fold>
}
