package MyGui;
// THIS CODE IS MINE

import javax.swing.*;

import Roster.*;
import Rota.*;

public class MyGui {
    // package private so inheritable
    JFrame myGuiWindow;
    EmployeeRoster employeeRosterPassedFromMainMenu;
    TimeTable employeeRotaPassedFromMainMenu;
    JLabel title;

    public MyGui(){ // so intellij stops saying "no default constructor"
    }

    public MyGui( EmployeeRoster employeeRosterPassedFromMainMenu ){
        this.employeeRosterPassedFromMainMenu = employeeRosterPassedFromMainMenu;
    }

}

