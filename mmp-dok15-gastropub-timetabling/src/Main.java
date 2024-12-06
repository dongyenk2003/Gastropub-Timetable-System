import Roster.*;
import Rota.*;
import java.util.Scanner;

import Menu.MainMenu;
public class Main{

    public static void main( String[] args ){
        // passed by reference. Modifications made to this instance from another class' instance modifies this instance
        EmployeeRoster employeeRoster = new EmployeeRoster();
        TimeTable employeeRota = new TimeTable();
        Scanner systemInputStreamScanner = new Scanner( System.in );

        MainMenu mainMenu = new MainMenu( employeeRoster, employeeRota, systemInputStreamScanner );
        mainMenu.runMenu();
    }
}