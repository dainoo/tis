/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.classes.utils;

/**
 *
 * @author Daud
 */
public class ResultsSummary {

    private String breadDown = null;
    private double semCrdtReg=0, semCrdtObt=0, cummCrdtReg=0, cummCrdtObt=0;
    private double semWtdMark=0.0, semAvg=0.0, cummWtdMark=0.0, cummAvg=0.0;

    public String getBreadDown() {
        return breadDown;
    }

    public void setBreadDown(String breadDown) {
        this.breadDown = breadDown;
    }

    public double getSemCrdtReg() {
        return semCrdtReg;
    }

    public void setSemCrdtReg(double semCrdtReg) {
        this.semCrdtReg = semCrdtReg;
    }

    public double getSemCrdtObt() {
        return semCrdtObt;
    }

    public void setSemCrdtObt(double semCrdtObt) {
        this.semCrdtObt = semCrdtObt;
    }

    public double getCummCrdtReg() {
        return cummCrdtReg;
    }

    public void setCummCrdtReg(double cummCrdtReg) {
        this.cummCrdtReg = cummCrdtReg;
    }

    public double getCummCrdtObt() {
        return cummCrdtObt;
    }

    public void setCummCrdtObt(double cummCrdtObt) {
        this.cummCrdtObt = cummCrdtObt;
    }


    public void setCummCrdtObt(int cummCrdtObt) {
        this.cummCrdtObt = cummCrdtObt;
    }

    public double getSemWtdMark() {
        return semWtdMark;
    }

    public void setSemWtdMark(double semWtdMark) {
        this.semWtdMark = semWtdMark;
    }

    public double getSemAvg() {
        return semAvg;
    }

    public void setSemAvg(double semAvg) {
        this.semAvg = semAvg;
    }

    public double getCummWtdMark() {
        return cummWtdMark;
    }

    public void setCummWtdMark(double cummWtdMark) {
        this.cummWtdMark = cummWtdMark;
    }

    public double getCummAvg() {
        return cummAvg;
    }

    public void setCummAvg(double cummAvg) {
        this.cummAvg = cummAvg;
    }
    
    
}
