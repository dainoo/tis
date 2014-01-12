/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.classes.utils;

/**
 *
 * @author Daud
 */
public class WeightedAverageCommons {

    private String indexNumber = null;
    private String studentName = null;
    private String counter=null;
    private String weightedAverage=null;
    private String numberOfPasses = null;
    private String numberOfTrails = null;
    private String incompleteCourses = null;

    public String getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(String indexNumber) {
        this.indexNumber = indexNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getWeightedAverage() {
        return weightedAverage;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public void setWeightedAverage(String weightedAverage) {
        this.weightedAverage = weightedAverage;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getNumberOfPasses() {
        return numberOfPasses;
    }

    public void setNumberOfPasses(String numberOfPasses) {
        this.numberOfPasses = numberOfPasses;
    }

    public String getNumberOfTrails() {
        return numberOfTrails;
    }

    public void setNumberOfTrails(String numberOfTrails) {
        this.numberOfTrails = numberOfTrails;
    }

    public String getIncompleteCourses() {
        return incompleteCourses;
    }

    public void setIncompleteCourses(String incompleteCourses) {
        this.incompleteCourses = incompleteCourses;
    }
    
    
}
