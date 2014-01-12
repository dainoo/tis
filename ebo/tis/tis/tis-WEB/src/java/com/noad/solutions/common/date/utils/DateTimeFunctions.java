package com.noad.solutions.common.date.utils;



import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daud
 */
public class DateTimeFunctions {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM");
    private static SimpleDateFormat fullDateFormat = new SimpleDateFormat("EEE, d MMM, yyyy");
    private static SimpleDateFormat monthFormat = new SimpleDateFormat("MMM, yyyy");
//    private static SimpleDateFormat monthFormat = new SimpleDateFormat("MMM, yyyy");
    private static SimpleDateFormat sdf = new SimpleDateFormat();
    private static String YEAR = "yyyy";
    private static String TO_DAY_DATE = "EEE, d MMM, yyyy";
    private static String TIME_FORMAT = "h:mm a";
    public static String DAY = "d";
    private static String MONTH_PATTERN = "M";
    private static Calendar cal = Calendar.getInstance();
    private static Date toDay = new Date();
    private static String SUNDAY = "SUNDAY";
    private static String SATURDAY;
    private static NumberFormat nf = NumberFormat.getInstance();

    public static int getDifferenceBetweenDates(Date dtStart, Date dtEnd) {
        int difference = 0;

        long differenceInMilisecs = dtEnd.getTime() - dtStart.getTime();

        difference = (int) (differenceInMilisecs / (60 * 60 * 24 * 1000));


        return difference;
    }

    public static int getAverageMonthDifference(Date dtStart, Date dtEnd) {
        int difference = 0;

        long differenceInMilisecs = dtEnd.getTime() - dtStart.getTime();

        difference = (int) ((differenceInMilisecs / (60 * 60 * 24 * 1000)) / 30);


        return difference;
    }

    public static String getFormatedDate(Date d) {
        if (d == null) {
            return "<NOT SET>";
        }

        return dateFormat.format(d);
    }

    public static String getMonthYear(Date date) {
        if (date == null) {
            return "<NOT SET>";
        }

        return monthFormat.format(date);
    }

    public static String getFullDate(Date date) {
        if (date == null) {
            return "<NOT SET>";
        }

        return fullDateFormat.format(date);
    }

    public static Date formatFullDate(String dateString) {
        try {
            return fullDateFormat.parse(dateString);
        } catch (ParseException ex) {
            Logger.getLogger(DateTimeFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Date();
    }

    public static boolean isDateWeekendDate(Date date) {
        cal.setTime(date);

        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == Calendar.SUNDAY) {
            return true;
        }
        if (dayOfWeek == Calendar.SATURDAY) {
            return true;
        }

        return false;
    }

    public static String getTimeFormat(Date date) {
        if (date == null) {
            return "<NOT SET>";
        }

        sdf.applyPattern(TIME_FORMAT);

        return sdf.format(date);
    }

    public static String getCurrentYear() {
        sdf.applyPattern(YEAR);
        return sdf.format(toDay);
    }

    public static String getTodaysDate() {
        sdf.applyPattern(TO_DAY_DATE);
        return sdf.format(toDay);
    }

    public static String getCurrentMonthDigit() {
        sdf.applyPattern(MONTH_PATTERN);
        return sdf.format(toDay);
    }

    public static String getCurrentMonth2digFormat() {
        nf.setMinimumIntegerDigits(2);
        return nf.format(Integer.parseInt(getCurrentMonthDigit()));
    }

    public static int getMonthOfDateInDigit(Date date) {
        cal.setTime(date);

        int month = cal.get(Calendar.MONTH);

        return month;
    }

    public static int getCurrentMonthOfDateInDigit() {
        cal.setTime(toDay);
        int month = cal.get(Calendar.MONTH);

        return month;
    }

    public static int getCurrentYearInDigit() {
        cal.setTime(toDay);
        int month = cal.get(Calendar.YEAR);

        return month;
    }

    public static String getCustomeDate(String pattern, Date date) {
        sdf.applyPattern(pattern);
        return sdf.format(date);
    }

    public static String getCustomeDate(Date date, String pattern) {
        sdf.applyPattern(pattern);
        return sdf.format(date);
    }

    public static Date getExactDate(Date date) {
        cal.setTime(date);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    // <editor-fold defaultstate="collapsed" desc="Date[] calculateDateRangeForMonth(int selectedMonth, int year)">
    /**
     *
     *
     *
     * @return an array of dates, Date[], date[0] is the begin date and date[1]
     * is the end date
     */
    public static Date[] calculateDateRangeForMonth(int selectedMonth, int year) {
        Date[] periodDates = new Date[2];


        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        cal.set(Calendar.MONTH, selectedMonth);
        cal.set(Calendar.YEAR, year);

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        periodDates[0] = cal.getTime();

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        periodDates[1] = cal.getTime();

        return periodDates;

    }
    // </editor-fold>
}
