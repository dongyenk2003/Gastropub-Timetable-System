package Rota;
// ALL CODE IS MINE

import Roster.Employee;

import java.io.Serializable;
import java.lang.StringBuilder;
import java.util.ArrayList;

public class TimeTable implements Serializable{
    private Day[] daysOfWeek;
    private String notes;

    public TimeTable(){
        this.daysOfWeek = new Day[ DayOfWeek.values().length ];
        for( int i = 0; i < DayOfWeek.values().length; i ++ ){
            this.daysOfWeek[ i ] = new Day( DayOfWeek.values()[ i ] );
        }
        this.notes = "No notes set";
    }

    public Day[] getDaysOfWeek(){ return this.daysOfWeek; }

    public void setNotes( String newNotes ){
        this.notes = newNotes;
    }

    public String getDetails(){
        String message = "============\n" +
            "=Staff Rota=\n" +
            "============\n";
        message += "Notes:\t" + this.notes + "\n";
        for( Day d : daysOfWeek ){
            message += d.getDetails();
        }
        return message;
    }

    public String getNotes(){ return this.notes; }

    public Day getDayByIndex( int i ){
        return this.daysOfWeek[ i ];
    }

    public void setDayByIndex( Day d, int i ){
        this.daysOfWeek[ i ] = d;
    }

    public boolean dayIndexInBounds( int i ){
        return( 0 <= i && i < this.daysOfWeek.length );
    }

    public int getNumberOfEntries(){
        int n = 0;
        for( Day d : this.daysOfWeek ){
            n += d.getNumberOfStaff();
        }
        return n;
    }

}