package MyGui;

/* CODE BASED ON / ADAPTED FROM FROM
https://www.javatpoint.com/java-jlabel,
https://www.javatpoint.com/java-jtextfield,
https://www.javatpoint.com/java-jbutton,
https://www.javatpoint.com/java-jcombobox
*/
import javax.swing.*;

import Menu.RosterMenu;
import Roster.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Menu.MainMenu.printMenu;

public class RosterAddGui extends MyGui {

    private JLabel emailLabel;
    private JTextField emailField;

    private JLabel fNameLabel;
    private JTextField fNameTextField;
    private JLabel lNameLabel;
    private JTextField lNameTextField;
    private JLabel roleDropDownLabel;
    private JComboBox roleDropDown;

    private JButton submitButton;

    private boolean allDataOk;
    private String emailInput;
    private Email newEmail;
    private String firstname;
    private String lastName;
    private Role roleInput;

    public RosterAddGui( EmployeeRoster employeeRosterPassedFromMainMenu ){
        super( employeeRosterPassedFromMainMenu );
    }

    public void initialise(){
        this.myGuiWindow = new JFrame("Add employee to roster.");
        this.myGuiWindow.setLayout( null );
        this.myGuiWindow.setSize( 800, 600 );
        this.myGuiWindow.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );

        this.title = new JLabel("Employee Roster Form");
        this.title.setBounds( 300, 50, 200, 50 );
        this.myGuiWindow.add( this.title );

        this.emailLabel = new JLabel( "Email:" );
        this.emailLabel.setBounds( 50, 120, 100, 50);
        this.myGuiWindow.add( this.emailLabel );

        this.emailField = new JTextField( "" );
        this.emailField.setBounds( 200, 120, 200, 50 );
        this.myGuiWindow.add( this.emailField );

        this.fNameLabel = new JLabel("Firstname:");
        this.fNameLabel.setBounds( 50, 220, 100, 50 );
        this.myGuiWindow.add( this.fNameLabel );

        this.fNameTextField = new JTextField();
        this.fNameTextField.setBounds( 200, 220, 200, 50 );
        this.myGuiWindow.add( this.fNameTextField );

        this.lNameLabel = new JLabel("Lastname:");
        this.lNameLabel.setBounds( 50, 320, 100, 50 );
        this.myGuiWindow.add( this.lNameLabel );

        this.lNameTextField = new JTextField();
        this.lNameTextField.setBounds( 200, 320, 200, 50 );
        this.myGuiWindow.add( this.lNameTextField );

        this.roleDropDownLabel = new JLabel( "Role" );
        this.roleDropDownLabel.setBounds( 50, 420, 100, 50 );
        this.myGuiWindow.add( this.roleDropDownLabel );

        this.roleDropDown = new JComboBox<Role>( Role.values() );
        this.roleDropDown.setBounds( 200, 420, 200, 50 );
        this.myGuiWindow.add( this.roleDropDown );


        this.submitButton = new JButton("Add Employee");
        this.submitButton.setBounds( 500, 420, 200, 50 );
        this.myGuiWindow.add( this.submitButton );

        this.myGuiWindow.setVisible( true ); // have this at end or frame components won't be visible.
    }

    public void handleEmployeeForm(){
        // Gather all data from form, and if it's all valid, create an employee object and add it to roster.
        // If any invalid code
        this.allDataOk = true;

        this.submitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // errorFieldLabel.setText("You pressed the button :D"); // Test
                        emailInput = emailField.getText();
                        firstname = fNameTextField.getText();
                        lastName = lNameTextField.getText();
                        roleInput = Role.values()[ roleDropDown.getSelectedIndex() ]; // gets around Object : Role incompatibility

                        if( inputsAreOk( emailInput, firstname, lastName ) ){ // Role constrained to valid values only
                            Employee newEmployee = new Employee( newEmail, firstname, lastName, roleInput );
                            employeeRosterPassedFromMainMenu.addEmployee( newEmployee );
                            System.out.println( "Employee added! Details below:\n" + newEmployee.getDetails() );
                        }

                        myGuiWindow.dispose();
                        RosterMenu.printMenu();

                    } // END OF ACTION PERFOMED CODE
                }// END OF ACTION LISTENER OBJECT
        ); // END OF ADD ACTION LISTENER
    }

    private boolean inputsAreOk( String emailInput, String firstname, String lastName ){
        boolean dataOk = true;
        // Is email missing?
        if( emailInput.length() <= 0 ){
            System.err.println( "Email missing!" );
            dataOk = false;
        }
        // Is email invalid?
        if( !Email.formatIsValid( emailInput ) ){
            System.err.println( "Email invalid!" );
            dataOk = false;
        }
        // Is email used already?
        newEmail = new Email( emailInput );
        if( !employeeRosterPassedFromMainMenu.emailIsUnique( newEmail ) ){
            System.err.println("Email not unique!");
            dataOk = false;
        }

        // Names missing
        if( firstname.length() <= 0 ){
            System.err.println( "Firstname missing!" );
            dataOk = false;
        }
        if( lastName.length() <= 0 ){
            System.err.println( "Lastname missing!" );
            dataOk = false;
        }

        if( this.employeeRosterPassedFromMainMenu.semiColonAnywhereInArray( new String[]{ emailInput, firstname, lastName } ) ){
            System.err.println("Possible SQL injection attempt: Semi-colon in input!");
            dataOk = false;
        }

        return dataOk;
    }

}

