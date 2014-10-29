package com.syca.apps.gob.denunciamx.utils;

/**
 * Created by JARP on 10/29/14.
 */
public class MonthUtil {

    public static String getMonth(int month)
    {
        switch (month)
        {
            case 1:
                return "ENE";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "ABR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AGO";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DIC";
            default:
                return "";
        }
    }
}
