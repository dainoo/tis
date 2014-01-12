/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.locale.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daud
 */
public class PlaceLocality {

    private static final Logger LOG = Logger.getLogger(PlaceLocality.class.getName());

    public static List<WorldCountry> getListOfWorldCountries() {
        try {
            List<WorldCountry> listOfWorldCountries = new ArrayList<WorldCountry>();
            WorldCountry worldCountry = new WorldCountry();
            String[] locales = Locale.getISOCountries();
            for (String countryCode : locales) {

                Locale obj = new Locale("", countryCode);
                worldCountry.setCode(obj.getCountry());
                worldCountry.setName(obj.getDisplayCountry());
                listOfWorldCountries.add(worldCountry);
                worldCountry = new WorldCountry();

            }
            return listOfWorldCountries;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "ERROR GETTING COUNTRIES", e.getCause());
        }
        return null;
    }
}
