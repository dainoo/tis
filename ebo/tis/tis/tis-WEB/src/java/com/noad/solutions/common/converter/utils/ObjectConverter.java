package com.noad.solutions.common.converter.utils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Daud
 */
public class ObjectConverter {

    private static final Map<String, Method> CONVERTERS = new HashMap<String, Method>();

    static {
        Method[] methods = ObjectConverter.class.getDeclaredMethods();
        for (Method method : methods) {
            if (method.getParameterTypes().length == 1) {
                //converter should accept 1 argument:This skips the convert() method.
                CONVERTERS.put(method.getParameterTypes()[0].getName() + "_" + method.getReturnType().getName(), method);
            }
        }
    }

    public ObjectConverter() {
    }

    /**
     * Converts the given object value to the given class
     *
     * @param from the object value to be converted
     * @param to the type class which the given object should be converted to
     * @return the converted object value
     */
    public static <T> T convertObject(Object from, Class<T> to) {

//       return null if from is null
        if (from == null) {
            return null;
        }

//        checks if the given class can be cast: if yes then cast
        if (to.isAssignableFrom(from.getClass())) {
            return to.cast(from);
        }

//        look up the suitable converter
        String converterId = from.getClass().getName() + "_" + to.getName();
        Method converter = CONVERTERS.get(converterId);
        if (converter == null) {
            throw new UnsupportedOperationException("Cannot convert from " + from.getClass().getName() + " to " + to.getName()
                    + ". Requested Converter does not exist.");
        }

        // convert the value
        try {
            return to.cast(converter.invoke(to, from));
        } catch (Exception e) {
            throw new RuntimeException("Cannot convert from " + from.getClass().getName() + " to "
                    + to.getName() + ". Conversion failed with " + e.getMessage(), e);
        }

    }
    //Converters

    /**
     * Converts Integer to Boolean.If integer value is 0 ,then return FALSE,
     * else return TRUE
     *
     * @param value the integer to be converted
     * @return the converted Boolean value
     */
    public static Boolean integerToBoolean(Integer integerValue) {
        return integerValue.intValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    /**
     * Converts Boolean to Integer.If boolean value is TRUE,then return 1,else
     * return 0.
     *
     * @param The Boolean value to be converted .
     * @return the converted Integer value
     */
    public static Integer booleanToInteger(Boolean booleanValue) {
        return booleanValue.booleanValue() ? Integer.valueOf(1) : Integer.valueOf(0);
    }

    /**
     * Converts Double to BigDecimal
     *
     * @param The Double value to be converted
     * @return the converted BigDecimal value
     */
    public static BigDecimal doubleToBigDecimal(Double doubleValue) {
        return new BigDecimal(doubleValue.doubleValue());
    }

    /**
     * Converts BigDecimal to Double.
     *
     * @paramvalue The BigDecimal to be converted.
     * @returnThe converted Double value.
     */
    public static Double bigDecimalToDouble(BigDecimal value) {
        return new Double(value.doubleValue());
    }

    /**
     * Converts Integer to String.
     *
     * @paramvalue The Integer to be converted.
     * @returnThe converted String value.
     */
    public static String integerToString(Integer value) {
        return value.toString();
    }

    /**
     * Converts String to Integer.
     *
     * @paramvalue The String to be converted.
     * @returnThe converted Integer value.
     */
    public static Integer stringToInteger(String value) {
        return Integer.valueOf(value);
    }

    /**
     * Converts Boolean to String.
     *
     * @param value The Boolean to be converted.
     * @return The converted String value.
     */
    public static String booleanToString(Boolean value) {
        return value.toString();
    }

    /**
     * Converts String to Boolean.
     *
     * @paramvalue The String to be converted.
     * @returnThe converted Boolean value.
     */
    public static Boolean stringToBoolean(String value) {
        return Boolean.valueOf(value);
    }
// You can implement more converter methods here.
}
