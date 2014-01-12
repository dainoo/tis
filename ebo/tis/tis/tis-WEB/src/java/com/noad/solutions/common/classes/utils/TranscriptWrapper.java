/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.classes.utils;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author gyamfi
 */
public class TranscriptWrapper {

    private String academicYear = null;
    private String semester = null;
    private String academicLevel = null;
    private String semesterAvg = null;
    private List<TranscriptData> listOfTranscriptDatas = new ArrayList<TranscriptData>();
    private DataModel<TranscriptData> transcriptDatasDataModel = new ListDataModel<TranscriptData>();

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public List<TranscriptData> getListOfTranscriptDatas() {
        return listOfTranscriptDatas;
    }

    public DataModel<TranscriptData> getTranscriptDatasDataModel() {
        return transcriptDatasDataModel;
    }

    public void setTranscriptDatasDataModel(DataModel<TranscriptData> transcriptDatasDataModel) {
        this.transcriptDatasDataModel = transcriptDatasDataModel;
    }

    public void setListOfTranscriptDatas(List<TranscriptData> listOfTranscriptDatas) {
        this.listOfTranscriptDatas = listOfTranscriptDatas;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public String getSemesterAvg() {
        return semesterAvg;
    }

    public void setSemesterAvg(String semesterAvg) {
        this.semesterAvg = semesterAvg;
    }
}
