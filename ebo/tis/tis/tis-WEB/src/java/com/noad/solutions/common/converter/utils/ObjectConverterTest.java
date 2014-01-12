/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.converter.utils;

/**
 *
 * @author Daud
 */
public class ObjectConverterTest {

    public static void main(String[] args) {
        System.out.println("THE " + ObjectConverter.booleanToString(true));
        System.out.println("THE " + ObjectConverter.stringToInteger("5"));
        System.out.println("THE " + ObjectConverter.convertObject(args, null));
    }
}
