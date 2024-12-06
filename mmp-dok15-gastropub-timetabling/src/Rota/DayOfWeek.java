package Rota;
// ALL CODE MINE

public enum DayOfWeek{
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY;

    public String getListedDays(){ // programmatically generating list so I'm not tediously typing it up.
        String message = "";
        for( int i = 1; i <= DayOfWeek.values().length; i ++ ){
            message += "\t" + i + ".\t" + DayOfWeek.values()[ i - 1 ] + "\n";
        }
        return message;
    }

    public boolean inBounds( int i ){
        return ( 0 <= i && i < DayOfWeek.values().length );
    }
}