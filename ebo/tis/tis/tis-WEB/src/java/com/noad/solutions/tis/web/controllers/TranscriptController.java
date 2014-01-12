/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.web.controllers;

import com.noad.solutions.common.classes.utils.PageCommonInputs;
import com.noad.solutions.common.classes.utils.SearchInputs;
import com.noad.solutions.common.classes.utils.TranscriptData;
import com.noad.solutions.common.classes.utils.TranscriptWrapper;
import com.noad.solutions.common.jsf.utils.JSFMessages;
import com.noad.solutions.tis.ejb.entities.AcademicYear;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.ejb.entities.StudentMark;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author gyamfi
 */
@ManagedBean
@SessionScoped
public class TranscriptController {

    private SearchInputs searchInputs = new SearchInputs();
    private StudentMark studentMark = new StudentMark();
    private Student student = new Student();
    private PageCommonInputs pageCommonInputs = new PageCommonInputs();
    private TranscriptWrapper transcriptWrapper = new TranscriptWrapper();
    private List<StudentMark> listOfStudentMarks = new ArrayList<StudentMark>();
    private List<Student> listOfStudents = new ArrayList<Student>();
    private DataModel<TranscriptWrapper> transcriptWrappersDataModel = new ListDataModel<TranscriptWrapper>();
    private DataModel<TranscriptData> transcriptDatasDataModel;
    private List<TranscriptWrapper> listOfTranscriptWrappers;
    private StreamedContent studentViewPicture;
    private List<String> listOfDistinctAcademicYears;
    private List<String> listOfDistinctAcademicLevels;
    private String semGpaCwa = "";

    public TranscriptController() {
    }

    public void loadStudentTrancript() {

        try {
            if (searchInputs.getSearchParameter().equalsIgnoreCase("studentIndexNumber")) {
                student = TisEjbSource.getBasicQuariesInstance().findStudentByIndexNumber(searchInputs.getSearchValue());
            } else if (searchInputs.getSearchParameter().equalsIgnoreCase("studentId")) {
                student = TisEjbSource.getCrudInstance().studentFind(searchInputs.getSearchValue());
            }
            if (student == null) {
                JSFMessages.warnMessage("No Student Found With " + searchInputs.getSearchParameter() + "\t" + searchInputs.getSearchValue());
            } else {
                String academicLevel = null;
                listOfDistinctAcademicLevels = new ArrayList<String>();
                listOfDistinctAcademicLevels = TisEjbSource.getExamsProcessingInstance().getDistinctStudentAcademicLevel(student.getStudentId());
                listOfDistinctAcademicYears = new ArrayList<String>();
                listOfDistinctAcademicYears = TisEjbSource.getExamsProcessingInstance().getDistinctStudentAcademicYear(student.getStudentId());
                System.out.println("THE SIZE OF AYs is " + listOfDistinctAcademicYears.size());
                if (listOfDistinctAcademicYears.isEmpty()) {
                    JSFMessages.errorMessage("No Marks For Transcript For " + student.getSurname() + "\t" + student.getOtherNames());
                } else {
                    listOfTranscriptWrappers = new ArrayList<TranscriptWrapper>();
                    semGpaCwa = "";
                    if (student.getProgram().getGradingSystem().equalsIgnoreCase("CWA")) {
                        semGpaCwa += "Mark";
                    } else if (student.getProgram().getGradingSystem().equalsIgnoreCase("GPA")) {
                        semGpaCwa += "GPA";
                    }


                    for (String eachAcademicYear : listOfDistinctAcademicYears) {
                        AcademicYear academicYear = TisEjbSource.getCrudInstance().academicYearFind(eachAcademicYear);
                        listOfStudentMarks = TisEjbSource.getExamsProcessingInstance().getStudentTranscriptMark(student.getStudentId(), eachAcademicYear);
                        academicLevel = listOfStudentMarks.get(0).getAcademicLevel().getAcademicLevelId().toUpperCase();
                        System.out.println("THE SIZE OF STUDENT MARKS " + listOfStudentMarks.size());
                        transcriptWrapper.setAcademicLevel(academicLevel);
                        transcriptWrapper.setAcademicYear(academicYear.getAcademicYear());
                        transcriptWrapper.setSemester(academicYear.getSemester());
                        System.out.println("THE AL IS " + transcriptWrapper.getAcademicLevel());
                        System.out.println("THE AP IS " + transcriptWrapper.getAcademicYear());
                        System.out.println("THE SM IS " + transcriptWrapper.getSemester());
                        TranscriptData transcriptData = new TranscriptData();
                        List<TranscriptData> listOfTranscriptDatas = new ArrayList<TranscriptData>();
                        transcriptDatasDataModel = new ListDataModel<TranscriptData>(listOfTranscriptDatas);
                        for (StudentMark eachMark : listOfStudentMarks) {
                            if (student.getProgram().getGradingSystem().equalsIgnoreCase("CWA")) {
//                                weightedMark += (eachMark.getTotalMark() * eachMark.getCourse().getCreditHours());
                                transcriptData.setMark(String.valueOf(eachMark.getTotalMark()));
                            } else if (student.getProgram().getGradingSystem().equalsIgnoreCase("GPA")) {
//                                weightedMark += (eachMark.getGradePoint() * eachMark.getCourse().getCreditHours());
                                transcriptData.setMark(String.valueOf(eachMark.getGradePoint()));
                            }
                            transcriptData.setCourseCode((eachMark.getCourse().getCourseInitials() + " " + eachMark.getCourse().getCourseCode()));
                            transcriptData.setCourseName(eachMark.getCourse().getCourseName());
                            transcriptData.setCredit(String.valueOf(eachMark.getCourse().getCreditHours()));
                            transcriptData.setGrade(eachMark.getGrade());
                            listOfTranscriptDatas.add(transcriptData);
                            transcriptDatasDataModel = new ListDataModel<TranscriptData>(listOfTranscriptDatas);
                            transcriptData = new TranscriptData();
                        }

                        transcriptWrapper.setListOfTranscriptDatas(listOfTranscriptDatas);
                        transcriptWrapper.setTranscriptDatasDataModel(transcriptDatasDataModel);
                        listOfTranscriptWrappers.add(transcriptWrapper);
                        transcriptWrappersDataModel = new ListDataModel<TranscriptWrapper>(listOfTranscriptWrappers);
                        transcriptWrapper = new TranscriptWrapper();

                    }
                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printTranscript() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void resetTranscript() {
        
        listOfDistinctAcademicLevels = new ArrayList<String>();
        listOfDistinctAcademicYears = new ArrayList<String>();
        listOfStudentMarks = new ArrayList<StudentMark>();
        listOfStudents = new ArrayList<Student>();
        listOfTranscriptWrappers = new ArrayList<TranscriptWrapper>();
        pageCommonInputs = new PageCommonInputs();
        searchInputs = new SearchInputs();
        semGpaCwa = null;
        student = new Student();
        studentMark = new StudentMark();
        studentViewPicture = null;
        transcriptDatasDataModel = new ListDataModel<TranscriptData>(null);
        transcriptWrapper = new TranscriptWrapper();
        transcriptWrappersDataModel = new ListDataModel<TranscriptWrapper>();
    }

    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public SearchInputs getSearchInputs() {
        return searchInputs;
    }

    public void setSearchInputs(SearchInputs searchInputs) {
        this.searchInputs = searchInputs;
    }

    public StudentMark getStudentMark() {
        return studentMark;
    }

    public PageCommonInputs getPageCommonInputs() {
        return pageCommonInputs;
    }

    public void setPageCommonInputs(PageCommonInputs pageCommonInputs) {
        this.pageCommonInputs = pageCommonInputs;
    }

    public void setStudentMark(StudentMark studentMark) {
        this.studentMark = studentMark;
    }

    public DataModel<TranscriptData> getTranscriptDatasDataModel() {
        return transcriptDatasDataModel;
    }

    public void setTranscriptDatasDataModel(DataModel<TranscriptData> transcriptDatasDataModel) {
        this.transcriptDatasDataModel = transcriptDatasDataModel;
    }

    public String getSemGpaCwa() {
        return semGpaCwa;
    }

    public void setSemGpaCwa(String semGpaCwa) {
        this.semGpaCwa = semGpaCwa;
    }

    public Student getStudent() {
        return student;
    }

    public DataModel<TranscriptWrapper> getTranscriptWrappersDataModel() {
        return transcriptWrappersDataModel;
    }

    public List<String> getListOfDistinctAcademicYears() {
        return listOfDistinctAcademicYears;
    }

    public void setListOfDistinctAcademicYears(List<String> listOfDistinctAcademicYears) {
        this.listOfDistinctAcademicYears = listOfDistinctAcademicYears;
    }

    public List<String> getListOfDistinctAcademicLevels() {
        return listOfDistinctAcademicLevels;
    }

    public void setListOfDistinctAcademicLevels(List<String> listOfDistinctAcademicLevels) {
        this.listOfDistinctAcademicLevels = listOfDistinctAcademicLevels;
    }

    public void setTranscriptWrappersDataModel(DataModel<TranscriptWrapper> transcriptWrappersDataModel) {
        this.transcriptWrappersDataModel = transcriptWrappersDataModel;
    }

    public List<TranscriptWrapper> getListOfTranscriptWrappers() {
        return listOfTranscriptWrappers;
    }

    public void setListOfTranscriptWrappers(List<TranscriptWrapper> listOfTranscriptWrappers) {
        this.listOfTranscriptWrappers = listOfTranscriptWrappers;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<StudentMark> getListOfStudentMarks() {
        return listOfStudentMarks;
    }

    public StreamedContent getStudentViewPicture() {
        return studentViewPicture;
    }

    public void setStudentViewPicture(StreamedContent studentViewPicture) {
        this.studentViewPicture = studentViewPicture;
    }

    public void setListOfStudentMarks(List<StudentMark> listOfStudentMarks) {
        this.listOfStudentMarks = listOfStudentMarks;
    }

    public TranscriptWrapper getTranscriptWrapper() {
        return transcriptWrapper;
    }

    public void setTranscriptWrapper(TranscriptWrapper transcriptWrapper) {
        this.transcriptWrapper = transcriptWrapper;
    }

    public List<Student> getListOfStudents() {
        return listOfStudents;
    }

    public void setListOfStudents(List<Student> listOfStudents) {
        this.listOfStudents = listOfStudents;
    }
    //</editor-fold>
}
