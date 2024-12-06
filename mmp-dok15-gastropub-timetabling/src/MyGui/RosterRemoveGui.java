package MyGui;

/* BASED ON CODE FROM
https://www.geeksforgeeks.org/jlabel-java-swing/,
https://www.javatpoint.com/java-jbutton,
https://www.javatpoint.com/java-jspinner
*/

import Menu.MainMenu;
import Menu.RosterMenu;
import Roster.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RosterRemoveGui extends MyGui{
    // inherits JFrame:myGuiWindow, EmployeeRoster:employeeRosterPassedFromMainMenu, JLabel:title;
    private JLabel emailLabel;
    private JLabel emailField;
    private JLabel fNameLabel;
    private JLabel fNameField;
    private JLabel lNameLabel;
    private JLabel lNameField;
    private JButton employeeInfoGetter;
    private JButton employeeDeleter;
    private JLabel spinnerLabel;
    private SpinnerNumberModel spinnerRules;
    private JSpinner employeeIndexSpinner;

    public RosterRemoveGui(EmployeeRoster employeeRosterPassedFromMainMenu ){
        super( employeeRosterPassedFromMainMenu );
    }

    public void initialise(){
        this.myGuiWindow = new JFrame( "Employee Remover" );
        this.myGuiWindow.setSize( 800, 600 );
        this.myGuiWindow.setLayout( null );
        this.myGuiWindow.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

        this.title = new JLabel( "Employee Remover" );
        this.title.setBounds( 300, 25, 200, 50 );
        this.myGuiWindow.add( this.title );

        this.spinnerLabel = new JLabel("Employee Index");
        this.spinnerLabel.setBounds(100, 100, 100, 50);
        this.myGuiWindow.add( this.spinnerLabel );

        this.spinnerRules = new SpinnerNumberModel(
            1,
            1,
            this.employeeRosterPassedFromMainMenu.numberOfEmployees(),
            1
        );

        this.employeeIndexSpinner = new JSpinner( this.spinnerRules );
        this.employeeIndexSpinner.setBounds( 225, 100, 50, 50 );
        myGuiWindow.add( this.employeeIndexSpinner );

        this.employeeInfoGetter = new JButton("Who is this?");
        this.employeeInfoGetter.setBounds( 300, 175, 200, 50 );
        this.myGuiWindow.add( this.employeeInfoGetter );

        this.fNameLabel = new JLabel("Selected employee first name:");
        this.fNameLabel.setBounds( 100, 250, 200, 50 );
        this.myGuiWindow.add( fNameLabel );

        this.fNameField = new JLabel();
        this.fNameField.setBounds( 325, 250, 200, 50 );
        this.myGuiWindow.add( fNameField );

        this.lNameLabel = new JLabel( "Selected employee last name:" );
        this.lNameLabel.setBounds( 100, 325, 200, 50 );
        this.myGuiWindow.add( lNameLabel );

        this.lNameField = new JLabel();
        this.lNameField.setBounds( 325, 325, 200, 50 );
        this.myGuiWindow.add( this.lNameField );

        this.emailLabel = new JLabel("Selected employee email:");
        this.emailLabel.setBounds( 100, 400, 200, 50 );
        this.myGuiWindow.add( emailLabel );

        this.emailField = new JLabel();
        this.emailField.setBounds( 325, 400, 200, 50 );
        this.myGuiWindow.add( this.emailField );

        this.employeeDeleter = new JButton( "Delete this employee." );
        this.employeeDeleter.setBounds( 300, 475, 200, 50 );
        this.myGuiWindow.add( employeeDeleter );

        this.myGuiWindow.setVisible( true ); // have at end or some elements won't show
    }

    public void processButtonPresses(){
        this.employeeInfoGetter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedNumber = (int) employeeIndexSpinner.getValue();
                // Convert from 1-N to 0-N.
                selectedNumber --;
                // CHECK NUMBER IS IN BOUNDS INCASE SOMETHING DELETED WHILE THIS WINDOW OPEN
                if( employeeRosterPassedFromMainMenu.inBounds( selectedNumber ) ){
                    fNameField.setText( employeeRosterPassedFromMainMenu.getEmployee( selectedNumber ).getFirstname() );
                    lNameField.setText( employeeRosterPassedFromMainMenu.getEmployee( selectedNumber ).getLastname() );
                    // Get email String from Email instance.
                    emailField.setText( employeeRosterPassedFromMainMenu.getEmployee( selectedNumber ).getEmail().getEmail() );
                }else{
                    System.err.println( "Employee index out of bounds! Window data wrong. Closing" );
                    RosterMenu.printMenu();
                    myGuiWindow.dispose();
                }
            }
        });

        this.employeeDeleter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedNumber = (int) employeeIndexSpinner.getValue();
                selectedNumber --;
                if(employeeRosterPassedFromMainMenu.inBounds( selectedNumber ) ){
                    Employee removedEmployee = employeeRosterPassedFromMainMenu.getEmployee( selectedNumber );
                    employeeRosterPassedFromMainMenu.removeEmployee( selectedNumber );
                    System.out.println("Employee removed! Their details below:\n" + removedEmployee.getDetails());
                    removedEmployee = null; // helping garbage collector
                }else{
                    System.err.println( "Employee index out of bounds! This window's number model is wrong. Closing." );
                }
                RosterMenu.printMenu();
                myGuiWindow.dispose();
                // close regardless of removal operation status because successful removal operation makes number model of spinner incorrect
            }
        });
    }

}