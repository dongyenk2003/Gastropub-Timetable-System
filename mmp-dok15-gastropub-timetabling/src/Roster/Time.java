package Roster;
// ALL CODE IS MINE

import Rota.DayOfWeek;
import Rota.ShiftTime;

import java.io.Serializable;

/**
 * Represent a time when an Employee instance cannot work.
 */
public class Time implements Serializable {
    private DayOfWeek unavailableDay;
    private ShiftTime unavailableShift;

    public Time( DayOfWeek day, ShiftTime shift ){
        this.unavailableDay = day;
        this.unavailableShift = shift;
    }

    public DayOfWeek getUnavailableDay(){ return this.unavailableDay; }
    public ShiftTime getUnavailableShift(){ return this.unavailableShift; }

    /**
     * Return true if given day and shift don't coincide with an unavailable time.
     * @param day
     * @param shift
     * @return true if given time doesn't match the unavailable time.
     */
    public boolean canWork( DayOfWeek day, ShiftTime shift ){
        return !(
                day.name().equalsIgnoreCase(  this.unavailableDay.name()  )
                &&
                shift.name().equalsIgnoreCase(  this.unavailableShift.name()  )
        );
    }
}
