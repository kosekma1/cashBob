package cz.cvut.fel.restauracefel.library.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Vojta
 */
public class DateFunctions {
    
    private DateFunctions() {
        
    }
    
    public static Date changeDateByNumberOfDays(Date d, int days) {
        Calendar c = new GregorianCalendar();
        c.setTime((Date) d.clone());
        c.add(Calendar.DAY_OF_MONTH, days);
        return c.getTime();
    }
}
