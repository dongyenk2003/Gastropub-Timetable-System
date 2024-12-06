package Menu;
// ALL CODE HERE IS MINE

import Roster.*;
import Rota.*;

import java.util.Scanner;
// I thought switch case only worked with not Strings but I am wrong :0

import MyGui.*;

public class MainMenu {

    EmployeeRoster employeeRosterPassedFromMain;
    TimeTable employeeRotaPassedFromMain;

    Scanner systemInputStreamScanner;

    public MainMenu( EmployeeRoster employeeRoster, Scanner systemInputStreamScanner){
        this.employeeRosterPassedFromMain = employeeRoster;
        this.systemInputStreamScanner = systemInputStreamScanner;
    }

    public MainMenu( EmployeeRoster employeeRoster, TimeTable employeeRota, Scanner systemInputStreamScanner ){
        this.employeeRosterPassedFromMain = employeeRoster;
        this.employeeRotaPassedFromMain = employeeRota;
        this.systemInputStreamScanner = systemInputStreamScanner; // read keyboard inputs
    }

    /** For unit testing purposes.
     * */
    public MainMenu(){

    }

    public static void printMenu(){
        System.out.println(
                "========================================\n" +
                        "=Gastro Pub Staff Rota System Main Menu=\n" +
                        "========================================" +
                        "\n\t0.\tQuit" +
                        "\n\t1.\tOpen Roster Menu" +
                        "\n\t2.\tOpen Rota Menu"
        );
    }
    public void runMenu(){
        boolean keepRunning = true;

        while( keepRunning ){
            this.printMenu();
            String userInput = systemInputStreamScanner.nextLine();
            try {
                int userCommand = Integer.parseInt( userInput ); // throws NumberFormatException
                switch (userCommand) {
                    case 0:
                        keepRunning = false;
                        System.out.println( "Thank you for using :)" );
                        break;
                    case 1:
                        RosterMenu rosterMenu = new RosterMenu( this.employeeRosterPassedFromMain, this.systemInputStreamScanner, this );
                        rosterMenu.runMenu();
                        break;
                    case 2:
                        RotaMenu rotaMenu = new RotaMenu( this.employeeRosterPassedFromMain, this.employeeRotaPassedFromMain, this.systemInputStreamScanner, this );
                        rotaMenu.runMenu();
                        break;
                    default:
                        System.err.println("Invalid command!");
                        break;
                }
            }catch( Exception e ){
                System.err.println( "Input must be an integer!" );
            }
        }
    }

    public void setEmployeeRosterPassedFromMain( EmployeeRoster newRoster ){ this.employeeRosterPassedFromMain = newRoster; }

    public void setEmployeeRotaPassedFromMain( TimeTable newRota ){
        this.employeeRotaPassedFromMain = newRota;
    }
}

