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
public class Region {

    public static List<GhanaRegion> getRegions() {
        
        List<GhanaRegion> listOfRegions = new ArrayList<GhanaRegion>();
        listOfRegions.add(new GhanaRegion("Ashanti", "Kumasi"));
        listOfRegions.add(new GhanaRegion("Upper West", "Wa"));
        listOfRegions.add(new GhanaRegion("Greater Accra", "Accra"));
        listOfRegions.add(new GhanaRegion("Western", "Takoradi"));
        listOfRegions.add(new GhanaRegion("Eastern", "Koforidua"));
        listOfRegions.add(new GhanaRegion("Brong Ahafo", "Sunyani"));
        listOfRegions.add(new GhanaRegion("Northern", "Temale"));
        listOfRegions.add(new GhanaRegion("Central", "Cape Coast"));
        listOfRegions.add(new GhanaRegion("Volta", "Ho"));
        listOfRegions.add(new GhanaRegion("Upper East", "Bolgatanga"));
        return listOfRegions;


    }
}
