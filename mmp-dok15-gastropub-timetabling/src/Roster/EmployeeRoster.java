package Roster;
/* DATABASE CODE BASED ON https://www.tutorialspoint.com/postgresql/postgresql_java.htm
Regex code based on https://www.w3schools.com/java/java_regex.asp and
https://www.rexegg.com/regex-quickstart.html
*/

import java.util.ArrayList;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ExceptionReporter.ExceptionReporter.reportException;

/** Represents a staff roster.
 */
public class EmployeeRoster{
    private ArrayList<Employee> roster;

    public EmployeeRoster(){
        this.roster = new ArrayList<Employee>();
    }

    public void addEmployee( Employee newEmployee ){
        this.roster.add( newEmployee );
    }

    public void removeEmployee( int arrayListIndex ){
        this.roster.remove( arrayListIndex );
    }

    public Employee getEmployee( int arrayListIndex ){ return this.roster.get( arrayListIndex ); }

    public void printDetails(){
        StringBuilder message = new StringBuilder(
            "Employee Roster\n" +
            "===============\n" +
            "Number of staff:\t" + this.roster.size() + "\n"
        );
        for( int i = 0; i < this.roster.size(); i ++  ){
            // Employee selectedEmployee = this.roster.get( i );
            message.append( this.roster.get(i).getDetails() );
            // Thinking ahead about how I will remove employees from roster.
            message.append( "\n\tNumber:\t" );
            //int employeeNumber = i ++; increments the loop index!
            int employeeNumber = i; // passed by value
            employeeNumber ++;
            message.append( employeeNumber );
            message.append( "\n" );
        }
        System.out.print( message.toString() );
    }

    public String getDetailsCondensed(){
        String message = "";
        for( int i = 0; i < this.roster.size(); i ++ ){
            Employee selectedE = this.roster.get( i );
            message += "\t" + (i+1) + ".\t" + selectedE.getEmail().getEmail() + "\t" + selectedE.getFirstname() + "\t" +
                selectedE.getLastname() + "\t" + selectedE.getRole().name() + "\n";
        }
        return message;
    }

    public boolean emailIsUnique( Email subjectEmail ){
        boolean emailIsUnique = true;
        for( Employee e : this.roster ){
            if( subjectEmail.equals( e.getEmail() ) ){ // I overwrote equals method from Object.
                emailIsUnique = false;
                break;
            }
        }
        return emailIsUnique;
    }

    public int numberOfEmployees(){
        return this.roster.size();
    }

    public boolean inBounds( int x ){
        return ( 0 <= x && x < this.numberOfEmployees() );
    }

    public void writeToDb(){
        Connection myDbConnection = null;
        Statement myStatement = null;
        try{
            myDbConnection = this.setupDriverAndGetConnection();
            myDbConnection.setAutoCommit( false ); // commit all statements as a group at the end

            String tableName = "staff";

            myStatement = myDbConnection.createStatement();

            myStatement.executeUpdate( "DELETE FROM " + tableName + ";" );
            myDbConnection.commit();

            for( Employee e : this.roster ){
                String email = e.getEmail().getEmail();
                String fname = e.getFirstname();
                String lname = e.getLastname();
                String role = e.getRole().toString();
                // Check any of these for a semi colon. SQL injections have semi colons.
                myStatement.executeUpdate( "INSERT INTO staff VALUES ( '"+email+"', '"+fname+"', '"+lname+"', '"+role+"' );" );
            }

            myDbConnection.commit();
            System.out.println( "Roster saved to database!" );

            myStatement.close();
            myDbConnection.close();

        }catch( Exception e ){
            reportException( e );
        }
    }

    public void readFromDb(){
        Connection myDbConnection = null;
        Statement myStatement = null;
        ResultSet myTable = null;

        try{
            myDbConnection = this.setupDriverAndGetConnection();
            myStatement = myDbConnection.createStatement();
            myTable = myStatement.executeQuery( "SELECT * FROM staff" );
            this.roster.clear();
            while( myTable.next() ){ // FOR EACH ROW IN THE TABLE
                String email = myTable.getString("email");
                String firstname = myTable.getString("firstname");
                String lastname = myTable.getString("lastname");
                String stringRole = myTable.getString("role");
                Role roleRole = Role.BAR.getRoleFromString( stringRole );
                Employee newEmployee = new Employee( new Email(email), firstname, lastname, roleRole );
                this.addEmployee( newEmployee );
            }
            System.out.println("Roster loaded!");

        }catch( Exception e ){
            reportException( e );
        }

    }

    private Connection setupDriverAndGetConnection() throws Exception{ // subclass instances will be handled by super class variable
        Class.forName( "org.postgresql.Driver" );
        return DriverManager.getConnection( "jdbc:postgresql://db.dcs.aber.ac.uk:5432/cs39440_23_24_dok15", "dok15", "dongyen" );
    }

    public boolean semiColonAnywhereInArray( String[] dataForDb ){
        boolean dangerous = false;

        for( String s : dataForDb ){
            if( semiColonAnyWhereInString( s ) ){
                dangerous = true;
                break;
            }
        }
        return dangerous;
    }

    public boolean semiColonAnyWhereInString( String s ){
        boolean result = false;

        Pattern containsSemiColonAnywhere = Pattern.compile( ";", Pattern.CASE_INSENSITIVE );
        Matcher stringChecker;
        stringChecker = containsSemiColonAnywhere.matcher( s );
        if(stringChecker.find() )
            result = true;

        return result;
    }

    public boolean employeeIndexInbounds( int i ){
        return ( 0 <= i && i < this.numberOfEmployees() );
    }
}