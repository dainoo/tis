/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sabonay.common.formating;

/**
 *
 * @author Edwin
 */
public class ObjectFormat
{

    public static String getStringObject(Object object)
    {
        if(object == null)
            return "";
        else
            return object.toString();
    }


    public static double getDoubleObject(Object object)
    {
        if(object == null)
            return 0.0;
        else
        {
            try {
                return Double.parseDouble(object.toString());
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return 0.0;
    }

    public static int getObjectAsInt(Object object)
    {
        if(object == null)
            return 0;
        else
        {
            try {
                return Integer.parseInt(object.toString());
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return 0;
    }
}
