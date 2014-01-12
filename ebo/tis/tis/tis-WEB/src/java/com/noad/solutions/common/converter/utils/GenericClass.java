/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.common.converter.utils;

/**
 *
 * @author Daud
 */
public class GenericClass {

    Integer[] integerArray = new Integer[10];// creates integer array

    public void testGeneric() {

        integerArray[0] = new Integer(23);
        int primitiveValue = integerArray[0].intValue();

        integerArray[2] = 45;//auto-boxing ; automatic casting from primitive type to type wrapper
        int value = integerArray[2];//auto-unboxing ; automatic casting from type wrapper to primitive type

    }
}
