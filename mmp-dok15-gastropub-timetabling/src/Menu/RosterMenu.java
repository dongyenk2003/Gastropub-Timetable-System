package Menu;
// ALL CODE HERE IS MINE

import MyGui.*;
import Roster.*;
import Rota.DayOfWeek;
import Rota.ShiftTime;

import java.util.Scanner;
public class RosterMenu extends MainMenu{
    // Inherits EmployeeRoster:employeeRosterPassedFromMainMenu, Scanner:systemInputStreamScanner.
    private MainMenu mainMenu;

    public RosterMenu(EmployeeRoster employeeRosterPassedFromMain, Scanner systemInputStreamScanner, MainMenu mainMenu){
        super( employeeRosterPassedFromMain, systemInputStreamScanner );
        this.mainMenu = mainMenu;
    }

    public static void printMenu(){
        System.out.println(
            "===================\n" +
            "=Staff Roster Menu=\n" +
            "===================" +
                    "\n\t0.\tReturn To Main Menu" +
                    "\n\t1.\tAdd Employee To Roster (CLI)" +
                    "\n\t2.\tAdd Employee To Roster (GUI)" +
                    "\n\t3.\tRemove Employee From Roster (CLI)" +
                    "\n\t4.\tQuery Employee Index / Remove Employee From Roster (GUI)" +
                    "\n\t5.\tRemove All Employees From Roster" +
                    "\n\t6.\tView Roster" +
                    "\n\t7.\tSave Roster (DELETES PERSISTED ROSTER DATA)" +
                    "\n\t8.\tLoad Roster (DELETES NON-PERSISTED ROSTER DATA)" +
                    "\n\t9.\tAdd Unavailable Time For Employee" +
                    "\n\t10.\tView Unavailable Times For Employee" +
                    "\n\t11.\tClear Unavailable Times For Employee"
        );
    }

    @Override
    public void runMenu(){
        boolean runAgain = true;
        while( runAgain ){
            RosterMenu.printMenu();
            String userInput = this.systemInputStreamScanner.nextLine();
            try{
                int userCommand = Integer.parseInt( userInput );
                switch( userCommand ){
                    case 0:
                        runAgain = false;
                        break;
                    case 1:
                        this.handleEmployeeForm();
                        break;
                    case 2:
                        RosterAddGui graphicalRosterMenu = new RosterAddGui( employeeRosterPassedFromMain );
                        graphicalRosterMenu.initialise();
                        graphicalRosterMenu.handleEmployeeForm();
                        break;
                    case 3:
                        this.handleEmployeeRemoval();
                        break;
                    case 4:
                        if( this.employeeRosterPassedFromMain.numberOfEmployees() > 0 ) {
                            RosterRemoveGui employeeRemoverGui = new RosterRemoveGui(this.employeeRosterPassedFromMain);
                            employeeRemoverGui.initialise();
                            employeeRemoverGui.processButtonPresses();
                        }else {
                            System.err.println("No employees to remove!");
                        }
                        break;
                    case 5: // dereference old object and leave for garbage collector
                        if( this.employeeRosterPassedFromMain.numberOfEmployees() > 0 ) {
                            this.employeeRosterPassedFromMain = new EmployeeRoster(); // PROBLEM MainMenu references old Roster
                            this.mainMenu.setEmployeeRosterPassedFromMain( this.employeeRosterPassedFromMain );
                            System.out.println("Employee roster cleared.");
                        }else{
                            System.err.println("Employee roster already empty!");
                        }
                        break;
                    case 6:
                        this.employeeRosterPassedFromMain.printDetails();
                        break;
                    case 7:
                        this.employeeRosterPassedFromMain.writeToDb();
                        break;
                    case 8:
                        this.employeeRosterPassedFromMain.readFromDb();
                        break;
                    case 9:
                        if( this.employeeRosterPassedFromMain.numberOfEmployees() > 0 )
                            this.handleAddingUnavailableTimeForEmployee();
                        else
                            System.err.println( "No employees in roster!" );
                        break;
                    case 10:
                        if( this.employeeRosterPassedFromMain.numberOfEmployees() > 0 )
                            this.viewUnavailableTimesForEmployee();
                        else
                            System.err.println( "No employees in roster!" );
                        break;
                    case 11:
                        if( this.employeeRosterPassedFromMain.numberOfEmployees() > 0 )
                            this.clearUnavailableTimesForEmployee();
                        else
                            System.err.println( "No employees in roster!" );
                        break;
                    default:
                        System.err.println("Invalid command!");
                        break;
                }
            }catch( Exception e ){
                System.err.println("Input must be an integer!");
            }
        }
    }

    // ONLY when all valid data gathered, create Employee and add it.
    private void handleEmployeeForm(){
        String stringEmail;
        Email email;
        String firstname;
        String lastname;
        String stringRoleNumber;
        int roleNumber;
        Role role;

        while( true ){
            System.out.print( "Input email:\t" );
            stringEmail = systemInputStreamScanner.nextLine();
            if( Email.formatIsValid( stringEmail ) ){
                email = new Email( stringEmail );
            }else{
                System.err.println( "Email format invalid!" );
                break;
            }
            // Valid email provided, but is it unique?
            if( !employeeRosterPassedFromMain.emailIsUnique( email ) ){
                System.err.println("Each employee must have a unique email!");
                break;
            }

            System.out.print("Input firstname:\t");
            firstname = systemInputStreamScanner.nextLine();
            System.out.print("Input lastname:\t");
            lastname = systemInputStreamScanner.nextLine();

            // CHECK FOR SQL INJECTION ATTEMPT
            if( this.employeeRosterPassedFromMain.semiColonAnywhereInArray( new String[]{ stringEmail, firstname, lastname } ) ){
                System.err.println( "Possible SQL injection:\tSemi-colon in input!" );
                break;
            }

            System.out.print("Input role:" + Role.BAR.getRoleNumberSelectionMessage() + ":\t");
            stringRoleNumber = systemInputStreamScanner.nextLine(); // number either NAN or out of bounds, or correct
            try{
                roleNumber = Integer.parseInt( stringRoleNumber );
                if( Role.BAR.roleNumberOutOfBounds( roleNumber ) ){
                    System.err.println("Role number out of bounds!");
                    break;
                }else{ // VALID ROLE NUMBER PROVIDED
                    role = Role.values()[ roleNumber ];
                }
            }catch( NumberFormatException e ){
                System.err.println("Must provide a number corresponding with a role.");
                break;
            }

            Employee newEmployee = new Employee( email, firstname, lastname, role );
            employeeRosterPassedFromMain.addEmployee( newEmployee );
            System.out.println( "New employee added! Details below." );
            newEmployee.printDetails();

            break; // THE FINAL BREAK only reached if all inputs valid.
        }

    }

    private void handleEmployeeRemoval(){

        if( this.employeeRosterPassedFromMain.numberOfEmployees() > 0 ) {
            String input;
            int command;
            this.employeeRosterPassedFromMain.printDetails();

            System.out.print("Enter an employee number:\t");
            input = systemInputStreamScanner.nextLine();
            try { // it's an integer but is it in bounds?
                command = Integer.parseInt(input);
                // convert from 1-N to 0-N
                command --;
                if (command < 0 || command >= this.employeeRosterPassedFromMain.numberOfEmployees()) {
                    System.err.println("Number out of bounds!");
                } else {
                    System.out.println("Removing employee! Details below:\n" + this.employeeRosterPassedFromMain.getEmployee(command).getDetails());
                    this.employeeRosterPassedFromMain.removeEmployee(command);
                    System.out.println("Employee removed!");

                }
            } catch (NumberFormatException e) {
                System.err.println("Employee number must be an integer!");
            }
        }else{
            System.err.println( "No employees to remove!" );
        }
    }

    private void handleAddingUnavailableTimeForEmployee(){
        DayOfWeek unavailableDay;
        ShiftTime unavailableShift;
        Employee selectedEmployee = getSelectedEmployee();

        if( selectedEmployee != null ){ // ALTERNATIVE TO WHILE LOOP WITH BREAKS. Means code only executed if dependents successfully acquired.
            unavailableDay = this.getSelectedDayOfWeek();
            if( unavailableDay != null ){
                unavailableShift = this.getSelectedShiftTime();
                if( unavailableShift != null ){
                    selectedEmployee.addUnavailableTime( unavailableDay, unavailableShift );
                }
            }
        }


    }

    private Employee getSelectedEmployee(){
        Employee selectedEmployee = null;
        String employeeIndexString;
        int employeeIndex;

        System.out.println( this.employeeRosterPassedFromMain.getDetailsCondensed() );
        System.out.print( "Input employee index:\t" );
        employeeIndexString = this.systemInputStreamScanner.nextLine();
        try{
            employeeIndex = Integer.parseInt(employeeIndexString);
            employeeIndex --; // 1-N to 0-N.
            if( this.employeeRosterPassedFromMain.employeeIndexInbounds( employeeIndex ) ) {
                selectedEmployee = this.employeeRosterPassedFromMain.getEmployee( employeeIndex );
            }else
                System.err.println("Employee index out of bounds!");
        }catch( NumberFormatException e ){
            System.err.println( "You failed to input an integer!" );
        }

        return selectedEmployee;
    }

    private DayOfWeek getSelectedDayOfWeek(){
        DayOfWeek selectedDay = null;
        String dayIndexString;
        int dayIndex;
        System.out.print( DayOfWeek.MONDAY.getListedDays() );
        System.out.print( "Enter day index:\t" );
        dayIndexString = this.systemInputStreamScanner.nextLine();
        try{
            dayIndex = Integer.parseInt( dayIndexString );
            dayIndex --;
            if( DayOfWeek.MONDAY.inBounds( dayIndex ) )
                selectedDay = DayOfWeek.values()[dayIndex];
            else
                System.err.println( "Day index out of bounds!" );
        }catch( NumberFormatException e ){
            System.err.println( "Failed to enter integer!" ); }

        return selectedDay;
    }

    private ShiftTime getSelectedShiftTime(){
        ShiftTime selectedShiftTime = null;
        String shiftTimeIndexString;
        int shiftTimeIndex;

        System.out.println( ShiftTime.DAY.getListedTimes() );
        System.out.print("Insert shift index:\t");
        shiftTimeIndexString = this.systemInputStreamScanner.nextLine();
        try{
            shiftTimeIndex = Integer.parseInt( shiftTimeIndexString );
            shiftTimeIndex --;
            if( ShiftTime.DAY.inBounds( shiftTimeIndex ) ){
                selectedShiftTime = ShiftTime.values()[ shiftTimeIndex ];
            }else{
                System.err.println("The day integer you entered is out of bounds!");
            }
        }catch( NumberFormatException e ){
            System.err.println( "You failed to input an integer for a day!" );
        }

        return selectedShiftTime;
    }

    public void viewUnavailableTimesForEmployee(){
        Employee selectedEmployee = this.getSelectedEmployee();
        System.out.println( selectedEmployee.getUnavailableTimesDetails() );
    }

    public void clearUnavailableTimesForEmployee(){
        Employee selectedEmployee = this.getSelectedEmployee();
        selectedEmployee.clearUnavailableTimes();
    }
}
