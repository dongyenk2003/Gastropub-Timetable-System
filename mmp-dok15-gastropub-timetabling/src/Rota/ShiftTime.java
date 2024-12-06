package Rota;
// ALL CODE IS MINE

public enum ShiftTime{
    DAY,
    NIGHT;

    public String getListedTimes(){
        String message = "";
        for( int i = 0; i < ShiftTime.values().length; i ++ ){
            message += "\t" + (i + 1) + ".\t" + ShiftTime.values()[i] + "\n";
        }
        return message;
    }

    public boolean inBounds( int i ){
        return ( 0 <= i && i < ShiftTime.values().length );
    }
}