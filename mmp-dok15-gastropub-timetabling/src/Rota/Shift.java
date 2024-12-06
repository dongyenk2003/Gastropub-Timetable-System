package Rota;
// ALL CODE IS MINE

import Roster.Employee;
import Roster.Role;

import java.util.ArrayList;
import java.io.Serializable;

public class Shift implements Serializable{
    private ShiftTime shiftTime;
    // Hard coded employee slot variables or arrays or ArrayLists
    private ArrayList<Employee> barStaff;
    private ArrayList<Employee> chefStaff;
    private ArrayList<Employee> serverStaff;
    private ArrayList<Employee> kpStaff;

    public ArrayList<Employee> getBarStaff() {
        return barStaff;
    }

    public void setBarStaff(ArrayList<Employee> barStaff) {
        this.barStaff = barStaff;
    }

    public ArrayList<Employee> getChefStaff() {
        return chefStaff;
    }

    public void setChefStaff(ArrayList<Employee> chefStaff) {
        this.chefStaff = chefStaff;
    }

    public ArrayList<Employee> getServerStaff() {
        return serverStaff;
    }

    public void setServerStaff(ArrayList<Employee> serverStaff) {
        this.serverStaff = serverStaff;
    }

    public ArrayList<Employee> getKpStaff() {
        return kpStaff;
    }

    public void setKpStaff(ArrayList<Employee> kpStaff) {
        this.kpStaff = kpStaff;
    }

    public Shift(ShiftTime shiftTime ){
        this.shiftTime = shiftTime;
        // Staff in ArrayLists for expandability.
        this.barStaff = new ArrayList<Employee>();
        this.chefStaff = new ArrayList<Employee>();
        this.serverStaff = new ArrayList<Employee>();
        this.kpStaff = new ArrayList<Employee>();
    }

    // added for RotaViewGui
    public ShiftTime getShiftTime(){ return this.shiftTime; }

    // Added for use in RotaViewGui
    public ArrayList<Employee> getAllStaff(){
        ArrayList<Employee> allStaff = new ArrayList<Employee>();
        allStaff.addAll( this.barStaff );
        allStaff.addAll( this.chefStaff );
        allStaff.addAll( this.serverStaff );
        allStaff.addAll( this.kpStaff );
        return allStaff;
    }

    // When adding Employees, their role must be suitable!
    public boolean addBar( Employee e ){
        boolean success = true;
        if( this.employeeAlreadyOnShift( e ) ){
            System.err.println( "Employee already working!" );
            success = false;
        }else if( e.getRole().name().equalsIgnoreCase( Role.BAR.name() ) )
            this.barStaff.add( e );
        else {
            System.err.println("Only bar staff can work on the bar!");
            success = false;
        }
        return success;
    }

    public boolean addChef( Employee e ){
        boolean success = true;
        if( this.employeeAlreadyOnShift( e ) ){
            System.err.println( "Employee already working!" );
            success = false;
        }else if( e.getRole().name().equalsIgnoreCase(Role.CHEF.name() ) )
            this.chefStaff.add( e );
        else {
            System.err.println("Only chefs can be assigned to chef slots!");
            success = false;
        }
        return success;
    }

    public boolean addServer( Employee e ){
        boolean success = true;
        if( this.employeeAlreadyOnShift( e ) ){
            System.err.println( "Employee already working!" );
            success = false;
        }else if( e.getRole().name().equalsIgnoreCase( Role.SERVER.name() )  )
            this.serverStaff.add( e );
        else {
            System.err.println("Only servers can serve!");
            success = false;
        }
        return success;
    }

    /***
     * Servers can do both KP and serving work!
     * @param e
     * @return
     */
    public boolean addKp( Employee e ) {
        boolean success = true;
        if( this.employeeAlreadyOnShift( e ) ){
            System.err.println( "Employee already working!" );
            success = false;
        }else if(
                e.getRole().name().equalsIgnoreCase(Role.KP.name())
                || e.getRole().name().equalsIgnoreCase(Role.SERVER.name())
        )
            this.kpStaff.add(e);
        else {
            System.err.println("Only KPs can do KP work!");
            success = false;
        }
        return success;
    }

    private boolean employeeAlreadyOnShift( Employee e ){
        return this.getAllStaff().contains( e );
    }

    public String getDetails(){
        String message = "\t" + this.shiftTime.name() + ". ";

        // Saves having to write 4 for-each loops.
        ArrayList<Employee> allStaff = new ArrayList<Employee>();
        allStaff.addAll( this.barStaff );
        allStaff.addAll( this.chefStaff );
        allStaff.addAll( this.serverStaff );
        allStaff.addAll( this.kpStaff );

        int staffOn = allStaff.size();
        message += "\tStaff on:\t" + staffOn + "\n";

        for( Employee e : allStaff ){
            message += "\t\t" + e.getRole().toString() + ":\t" +
                e.getFirstname() + " " + e.getLastname() + " (" + e.getEmail().getEmail() + ")\n";
        }

        return message;
    }

    public int getNumberOfStaff(){
        return ( this.barStaff.size() + this.chefStaff.size() + this.serverStaff.size() + this.kpStaff.size() );
    }

    public void resetShift(){
        this.barStaff = new ArrayList<Employee>();
        this.chefStaff = new ArrayList<Employee>();
        this.serverStaff = new ArrayList<Employee>();
        this.kpStaff = new ArrayList<Employee>();
    }
}
