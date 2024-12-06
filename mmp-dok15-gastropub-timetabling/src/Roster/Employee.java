package Roster;
/* Code based on
https://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java - Rekin's response
Mostly mine tho
*/

import java.util.ArrayList;
import Rota.DayOfWeek;
import Rota.ShiftTime;

import java.io.Serializable;

/** Represents an employee.
 */
public class Employee implements Serializable {
    private Email email; // Intellij automatically implemented Serializable for all Day component classes except Email, hence failure
    private String firstname; // String implements Serializable
    private String lastname;

    private Role role;

    private ArrayList<Time> unavailableTimes;

    public Employee( Email email, String firstname, String lastname, Role role ){
        this.email = email;
        this.firstname = firstname.toUpperCase();
        this.lastname = lastname.toUpperCase();
        this.role = role;
        this.unavailableTimes = new ArrayList<Time>();
    }

    // Auto generated getters and setters.
    public Email getEmail() {
        return email;
    }
    public void setEmail(Email email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname.toUpperCase();
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname.toUpperCase();
    }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public ArrayList<Time> getUnavailableTimes(){ return this.unavailableTimes; }

    public String getDetails(){
        return
            "Employee Details:" +
            "\n\tEmail:\t" + this.email.getEmail() +
            "\n\tName:\t" + this.firstname + " " + this.lastname +
            "\n\tRole:\t" + this.role
        ;
    }

    public void printDetails(){
        System.out.println( getDetails() );
    }

    public static String getProperCase( String aString ){
        String properCase = "";
        if( aString.length() > 1 ){
            properCase += aString.substring(0, 1).toUpperCase() + aString.substring(1).toLowerCase();
        }
        return properCase;
    }

    public void addUnavailableTime( DayOfWeek day, ShiftTime shift ){
        if( this.unavailableTimeAlreadyThere( day, shift ) ){
            System.err.println("That unavailable time is already recorded!");
        }else {
            Time unavailableTime = new Time(day, shift);
            this.unavailableTimes.add(unavailableTime);
            System.out.println("Unavailable time added for " + Employee.getProperCase(this.firstname) + ":\t"
                    + unavailableTime.getUnavailableDay().name() + "\t" + unavailableTime.getUnavailableShift().name());
        }
    }

    public boolean unavailableTimeAlreadyThere( DayOfWeek day, ShiftTime shift ){
        boolean answer = false;
        for( Time t : this.unavailableTimes ){
            if( t.getUnavailableDay().name().equalsIgnoreCase( day.name() )
                &&
                t.getUnavailableShift().name().equalsIgnoreCase( shift.name() )
            ){
                answer = true;
                break;
            }
        }
        return answer;
    }

    public void clearUnavailableTimes(){
        this.unavailableTimes.clear();
        System.out.println( "Unavailable times removed for " + Employee.getProperCase( this.firstname ) + "." );
    }

    public String getUnavailableTimesDetails(){
        String message = this.firstname + " " + this.lastname + "\n\tUnavailable Times:\t" + this.unavailableTimes.size();
        for( Time t : this.unavailableTimes )
            message += "\n\t\t" + t.getUnavailableDay().name() + "\t" + t.getUnavailableShift().name();
        return message;
    }
}