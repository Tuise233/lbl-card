package cn.tuise233.util;

import java.util.Calendar;
import java.util.Date;

public class DateCompare {
    public static boolean IsToday(Date date){
        Date currentDate = new Date();
        Calendar curCal = Calendar.getInstance();
        Calendar thatCal = Calendar.getInstance();

        curCal.setTime(currentDate);
        thatCal.setTime(date);

        if(curCal.get(Calendar.YEAR) == thatCal.get(Calendar.YEAR) &&
            curCal.get(Calendar.MONTH) == thatCal.get(Calendar.MONTH) &&
            curCal.get(Calendar.DAY_OF_MONTH) == thatCal.get(Calendar.DAY_OF_MONTH)) return true;
        return false;
    }
}
