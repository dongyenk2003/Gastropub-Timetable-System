package Roster;
// ALL CODE IS MINE

public enum Role{
    UNDEFINED, // In my prototyping, I found having this prevented having to handle null errors.
    BAR,
    CHEF,
    SERVER,
    KP,
    ;

    public String getRoleNumberSelectionMessage(){
        String message = "";
        for( int i = 0; i < this.values().length; i ++ ){
            message += "\t" + i + ".\t" + Role.values()[i].name() + "\n";
        }
        return message;
    }

    public boolean roleNumberOutOfBounds( int i ){
        return( i < 0 | i > this.values().length -1 );
    }

    public Role getRoleFromString( String x ){
        Role result = Role.UNDEFINED;
        for( Role currentRole : this.values() ){
            if( x.equalsIgnoreCase( currentRole.toString() ) ){
                result = currentRole;
                break;
            }
        }

        return result;
    }

    public Role getRoleFromIndex( int i  ){
        return Role.values()[ i ]; // throws ArrayIndexOutOfBoundsException. I'll handle in call place.
    }
}