package MyGui;
/* BASED ON CODE FROM
https://www.roseindia.net/java/example/java/swing/ScrollableJTable.shtml
*/

import Roster.*;
import Rota.*;

import javax.swing.*;

public class RotaViewGui extends MyGui{
    // inherits JFrame:MyGuiWindow, EmployeeRoster:employeeRosterPassedFromMainMenu, TimeTable:employeeRotaPassedFromMain, JLabel:title.
    public RotaViewGui( TimeTable employeeRotaPassedFromMain ){
        this.employeeRotaPassedFromMainMenu = employeeRotaPassedFromMain;
    }

    public void initialise(){
        this.myGuiWindow = new JFrame("Employee Rota:\t" + this.employeeRotaPassedFromMainMenu.getNotes());
        this.myGuiWindow.setSize( 800, 800 );
        this.myGuiWindow.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

        String[] columns = { "Day", "Time", "Role", "Firstname", "Lastname", "Email" };
        // TEST DATA COMMENT OUT WHEN DEVVING REAL DATA
        /* String[][] data = {
                {DayOfWeek.MONDAY.name(), ShiftTime.DAY.name(), Role.KP.name(), "DONGYEN", "KEAY", "DOK15@ABER.AC.UK"},
                {DayOfWeek.MONDAY.name(), ShiftTime.DAY.name(), Role.CHEF.name(), "RACHEL", "HORTON", "RAH31@ABER.AC.UK"}
        }; */
        // If data just an empty array, then only the header shown
        String[][] data = new String[this.employeeRotaPassedFromMainMenu.getNumberOfEntries()][6];
        int dataIndex = 0; // increment after 1 insertion
        for( Day d : this.employeeRotaPassedFromMainMenu.getDaysOfWeek() ){
            for( Shift s : d.getShifts() ){
                // Now access rota entry and insert into thing
                for( Employee e : s.getAllStaff() ){
                    data[ dataIndex ][0] = d.getDayOfWeek().name();
                    data[ dataIndex ][1] = s.getShiftTime().name();
                    data[ dataIndex ][2] = e.getRole().name();
                    data[ dataIndex ][3] = e.getFirstname();
                    data[ dataIndex ][4] = e.getLastname();
                    data[ dataIndex ][5] = e.getEmail().getEmail();
                    dataIndex ++;
                }
            }
        }

        JTable staffRotaTable = new JTable( data, columns );

        JScrollPane scrollPaneForTable = new JScrollPane( staffRotaTable ); // NEEDED FOR COLUMN HEADERS TO SHOW

        this.myGuiWindow.add( scrollPaneForTable );


        this.myGuiWindow.setVisible( true );
    }
}