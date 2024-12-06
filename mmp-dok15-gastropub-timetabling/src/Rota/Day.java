package Rota;
// ALL CODE MINE

import java.io.Serializable;
import Roster.Employee;
import java.util.ArrayList;

public class Day implements Serializable{
    DayOfWeek dayOfWeek;

    Shift[] shifts;

    public Day( DayOfWeek dayOfWeek ){
        this.dayOfWeek = dayOfWeek;
        this.resetShifts();

    }

    public DayOfWeek getDayOfWeek(){
        return this.dayOfWeek;
    }

    public Shift[] getShifts(){
        return this.shifts;
    }

    public void setDayOfWeek( DayOfWeek d ){
        this.dayOfWeek = d;
    }


    public String getDetails(){
        String message = this.dayOfWeek.name() + "\n";
        for( Shift s : this.shifts )
            message += s.getDetails();
        return message;
    }

    public Shift getShiftByIndex( int i ){
        return this.shifts[ i ]; // Handle ArrayIndexOutOfBoundsException in calling place
    }

    public int getNumberOfStaff(){
        int n = 0;
        for( Shift s : this.shifts ){
            n += s.getNumberOfStaff();
        }
        return n;
    }

    public void resetShifts(){
        this.shifts = new Shift[ ShiftTime.values().length ];
        for( int i = 0 ; i < ShiftTime.values().length; i ++ ){
            this.shifts[ i ] = new Shift( ShiftTime.values()[i] );
        }
    }
}
