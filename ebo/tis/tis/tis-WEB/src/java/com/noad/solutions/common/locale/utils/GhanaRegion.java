/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.locale.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daud
 */
public class GhanaRegion {

    public GhanaRegion(String regionName, String regionCapital) {
        this.regionName = regionName;
        this.regionCapitatal = regionCapital;
    }
    private String regionName = null;
    private String regionCapitatal = null;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionCapitatal() {
        return regionCapitatal;
    }

    public void setRegionCapitatal(String regionCapitatal) {
        this.regionCapitatal = regionCapitatal;
    }
}
