package Menu;
// CODE FOR DEEP COPYING A Day OBJECT BASED ON https://www.baeldung.com/java-deep-copy
/* Code for serialisation based on https://www.w3schools.com/java/java_files_create.asp, https://www.w3schools.com/java/java_files_read.asp and
* https://www.baeldung.com/java-serialization
* */
/* Code for Regexp based on https://www.w3schools.com/java/java_regex.asp and https://www.rexegg.com/regex-quickstart.html
* */
// CODE FOR PDF BASED ON 2024. Creating PDF Files in Java. Baeldung. Accessed on: 19/02/2024. https://www.baeldung.com/java-pdf-creation.

import ExceptionReporter.ExceptionReporter;
import Roster.*;
import Rota.*;

import java.util.ArrayList;
import java.util.Scanner;
import MyGui.RotaViewGui;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.lang3.SerializationUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import com.itextpdf.text.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RotaMenu extends MainMenu{
    // inherits EmployeeRoster:employeeRosterPassedFromMain, TimeTable:employeeRotaPassedFromMain, Scanner systemInputStreamScanner
    private MainMenu mainMenu;

    public RotaMenu( EmployeeRoster employeeRoster, TimeTable employeeRota, Scanner systemInputStreamScanner, MainMenu mainMenu ){
        super( employeeRoster, employeeRota, systemInputStreamScanner );
        this.mainMenu = mainMenu;
    }

    /** For unit testing purposes.
     */
    public RotaMenu(){
    }

    public static void printMenu(){
        String message = "===========\n" + "=Rota Menu=\n" + "===========";
        message += "\n\t0.\tBack to main menu";
        message += "\n\t1.\tSet note about this rota.";
        message += "\n\t2.\tView Rota (CLI)";
        message += "\n\t3.\tView Rota (GUI)";
        message += "\n\t4.\tAdd Employee";
        message += "\n\t5.\tCopy Day Across Week";
        message += "\n\t6.\tReset Rota";
        message += "\n\t7.\tClear Day";
        message += "\n\t8.\tClear Shift";
        message += "\n\t9.\tSave Rota";
        message += "\n\t10.\tLoad Rota";
        message += "\n\t11.\tExport Rota to PDF";
        System.out.println( message );
    }

    @Override
    public void runMenu(){
        boolean runAgain = true;

        while( runAgain ){
            RotaMenu.printMenu();
            String userInput = this.systemInputStreamScanner.nextLine();
            try{
                int userCommand = Integer.parseInt( userInput ); // throws NumberFormatException
                switch( userCommand ){
                    case 0:
                        runAgain = false;
                        break;
                    case 1:
                        System.out.print("Enter new note:\t");
                        String newNotes = this.systemInputStreamScanner.nextLine();
                        this.employeeRotaPassedFromMain.setNotes( newNotes );
                        break;
                    case 2:
                        System.out.print( this.employeeRotaPassedFromMain.getDetails() );
                        break;
                    case 3:
                        RotaViewGui rotaViewGui = new RotaViewGui( this.employeeRotaPassedFromMain );
                        rotaViewGui.initialise();
                        break;
                    case 4:
                        if( this.employeeRosterPassedFromMain.numberOfEmployees() <= 0 )
                            System.err.println( "Cannot draw employees from an empty roster!" );
                        else{
                            // Add employee by getting day of week, then shift of day to get Shift. Then get an Employee
                            this.handleAddingEmployeeToRota();
                        }
                        break;
                    case 5:
                        this.handleCopyDayAcrossWeek();
                        break;
                    case 6:
                        this.resetRota();
                        break;
                    case 7:
                        this.clearDay();
                        break;
                    case 8:
                        this.clearShift();
                        break;
                    case 9:
                        this.saveOrLoadRota( true );
                        break;
                    case 10:
                        this.saveOrLoadRota( false );
                        break;
                    case 11:
                        this.handlePdfExport();
                        break;
                    default:
                        System.err.println( "That command is invalid!" );
                        break;
                }
            }catch( java.lang.NumberFormatException e ){
                System.err.println( "Inputs must be integer!" );
            }
        }
    }

    private void handleAddingEmployeeToRota(){
        while( true ) {
            Day theDay = this.getSelectedDay(); // REFACTORING TO MAKE UNAVAILABLE TIME CODE WORK
            if( theDay == null){
                System.err.println( "Could not get day. Aborting." );
                break;
            }

            Shift theShift = this.getShiftToAddEmployeeTo( theDay );
            if( theShift == null ) {
                System.err.println("Could not get shift. Aborting.");
                break; // Break out of loop if can't get shift
            }

            Employee theEmployee = this.getEmployeeToAddToRota();
            if( theEmployee == null ) {
                System.err.println("Could not get employee. Aborting.");
                break; // Break out of loop if can't get Employee
            }
            // CHECK FOR CONFLICTS WITH TIME CONSTRAINTS
            if( theEmployee.unavailableTimeAlreadyThere( theDay.getDayOfWeek(), theShift.getShiftTime() ) ){
                System.err.println( Employee.getProperCase( theEmployee.getFirstname() ) + " is unavailable for work on " +
                    Employee.getProperCase( theDay.getDayOfWeek().name() ) + " "
                    + Employee.getProperCase( theShift.getShiftTime().name() ) + "s!"
                );
                break;
            }

            this.addEmployeeToShift(theEmployee, theShift);
            break; // This break only reached if Shift and Employee instances were obtained and program tried to combine them
        }

    }

    private Day getSelectedDay(){ // OOPS there was a method doing the same that I forgot about but dont want to tinker now
        Day selectedDay = null;
        String dayOfWeekIndexString;
        int dayOfWeekIndex;

        System.out.println( DayOfWeek.MONDAY.getListedDays() );
        System.out.print( "Insert day index:\t" );
        dayOfWeekIndexString = this.systemInputStreamScanner.nextLine();
        try{
            dayOfWeekIndex = Integer.parseInt( dayOfWeekIndexString );
            dayOfWeekIndex --;
            if( DayOfWeek.MONDAY.inBounds( dayOfWeekIndex ) ){
                selectedDay = this.employeeRotaPassedFromMain.getDayByIndex( dayOfWeekIndex );
            }else{
                System.err.println( "Day index integer out of bounds!" );
            }
        }catch( NumberFormatException e ){
            System.err.println("Input an integer for day index!");
        }

        return selectedDay;
    }

    private Shift getShiftToAddEmployeeTo( Day selectedDay ){
        Shift selectedShift = null;

        // Get Shift by getting day of week, then time of day.
        String timeOfDayIndexString;
        int timeOfDayIndex;

        System.out.print( ShiftTime.DAY.getListedTimes() );
        System.out.print( "Input time of day index:\t" );
        timeOfDayIndexString = this.systemInputStreamScanner.nextLine();

        // CHECK THESE ARE OK
        try{
            timeOfDayIndex = Integer.parseInt( timeOfDayIndexString ); // throws java.lang.NumberFormatException
            // Convert from 1-N to 0-N.
            timeOfDayIndex --;
            selectedShift = selectedDay.getShiftByIndex( timeOfDayIndex );
        }catch( NumberFormatException e){
            System.err.println( "Time index must be an integer!" );
        }catch( ArrayIndexOutOfBoundsException e ){
            System.err.println( "Time index must use numbers shown in list!" );
        }

        return selectedShift; // null if errors encountered
    }

    private Employee getEmployeeToAddToRota(){
        Employee selectedEmployee = null;

        String employeeIndexString;
        int employeeIndex;

        System.out.print( this.employeeRosterPassedFromMain.getDetailsCondensed() );
        System.out.print( "Input employee index:\t" );
        employeeIndexString = this.systemInputStreamScanner.nextLine();

        try{
            employeeIndex = Integer.parseInt( employeeIndexString ); // throws java.lang.NumberFormatException
            employeeIndex --; // convert 1-N to 0-N

            selectedEmployee = this.employeeRosterPassedFromMain.getEmployee( employeeIndex ); //  java.lang IndexOutOfBoundsException
        }catch( NumberFormatException e ){
            System.err.println("You failed to input an integer for employee index!");
        }catch( IndexOutOfBoundsException e ){
            System.err.println("Input a number corresponding with an employee!");
        }

        return selectedEmployee;
    }

    private void addEmployeeToShift( Employee theEmployee, Shift theShift ){
        String roleIndexString;
        int roleIndex;
        Role selectedRole;

        System.out.print( Role.BAR.getRoleNumberSelectionMessage() );
        System.out.print("What role do you want to add " + theEmployee.getFirstname() + " (" + theEmployee.getRole().name() + ") to?\t");
        roleIndexString = this.systemInputStreamScanner.nextLine();
        try{
            roleIndex = Integer.parseInt( roleIndexString ); // java.lang.NumberFormatException thrown.
            selectedRole = Role.BAR.getRoleFromIndex( roleIndex ); // ArrayIndexOutOfBoundsException thrown.
            boolean success = true;
            switch( selectedRole ){
                case UNDEFINED:
                    System.err.println( "Cannot add employees with undefined roles!" );
                    success = false;
                    break;
                case BAR:
                    success = theShift.addBar( theEmployee );
                    break;
                case CHEF:
                    success = theShift.addChef( theEmployee );
                    break;
                case SERVER:
                    success = theShift.addServer( theEmployee );
                    break;
                case KP:
                    success = theShift.addKp( theEmployee );
                    break;
                default:
                    System.err.println( "Some error encountered." ); // should never reach this
                    success = false;
                    break;
            }
            if( success )
                System.out.println("Employee added to rota!");
            else
                System.err.println("Employee not added to rota!");
        }catch( NumberFormatException e ){
            System.err.println( "You must input a role number!" );
        }catch( ArrayIndexOutOfBoundsException e ){
            System.err.println( "The role number you entered is invalid!" );
        }
    }

    private void handleCopyDayAcrossWeek(){
        String dayIndexString;
        int dayIndex;
        System.out.print( DayOfWeek.MONDAY.getListedDays() );
        System.out.print( "Insert the number for the day you want copied across:\t" );
        dayIndexString = this.systemInputStreamScanner.nextLine();
        try{
            dayIndex = Integer.parseInt( dayIndexString ); // NumberFormatException
            dayIndex --; // 1-N to 0-N
            if( this.employeeRotaPassedFromMain.dayIndexInBounds( dayIndex ) ){
                for( int i = 0; i < this.employeeRotaPassedFromMain.getDaysOfWeek().length; i ++ ){ // MUST MAKE DEEP COPY
                    Day modelDayCopy = SerializationUtils.clone( this.employeeRotaPassedFromMain.getDayByIndex( dayIndex ) );
                    modelDayCopy.setDayOfWeek( DayOfWeek.values()[i] );
                    this.employeeRotaPassedFromMain.setDayByIndex( modelDayCopy, i );
                }
                System.out.println("Day copied across!");
            }else
                System.err.println( "Day index out of bounds!" );
        }catch( NumberFormatException e ){
            System.err.println( "Input must be integer!" );
        }
    }

    private void resetRota(){
        this.employeeRotaPassedFromMain = new TimeTable(); // but old instance exists in MainMenu
        this.mainMenu.setEmployeeRotaPassedFromMain( employeeRotaPassedFromMain ); // SOLVES PROBLEM OF MainMenu instance referencing the old rota: WHen I backed out to MainMenu then back to RotaMenu, the old rota would still be loaded.
        System.out.println("Rota Cleared!");
    }

    private void clearDay(){
        Day dayToClear = this.getSelectedDay();
        if( dayToClear == null ){
            System.err.println("Bad inputs provided! Day not cleared.");
        }else{
            dayToClear.resetShifts();
            System.out.println("Day cleared!");
        }
    }

    private void clearShift(){ // get day to clear, then get shift to clear, and call shift's clear() method
        while( true ) {
            Day dayToClearFrom = this.getSelectedDay();
            if( dayToClearFrom == null )
                break;
            Shift shiftToClear = this.getShiftToClearFromDay( dayToClearFrom );
            if( shiftToClear == null )
                break;
            shiftToClear.resetShift();
            System.out.println("Shift reset!");
            break;
        }
    }

    private Shift getShiftToClearFromDay( Day d ){
        Shift shiftToClear = null;
        String shiftIndexString;
        int shiftIndex;
        System.out.print(ShiftTime.DAY.getListedTimes());
        System.out.print( "Which shift to clear?\t" );
        shiftIndexString = this.systemInputStreamScanner.nextLine();
        try{
            shiftIndex = Integer.parseInt( shiftIndexString );
            shiftIndex --;
            if( 0 <= shiftIndex && shiftIndex < ShiftTime.values().length ){
                shiftToClear = d.getShifts()[ shiftIndex ];
            }else{
                System.err.println("The number you gave does not correspond to a shift!");
            }
        }catch( NumberFormatException e ){
            System.err.println("You must input a number corresponding with a shift!");
        }

        return shiftToClear;
    }

    private void saveOrLoadRota( boolean save ){
        try {
            File rotaDataFile = new File("rotaDataFile");
            if (save) {
                this.saveRota( rotaDataFile );
            }
            else {
                if( !rotaDataFile.createNewFile() ){ // Both creates a file and checks if file exists (boolean return).
                    this.loadRota( rotaDataFile );
                }else{
                    rotaDataFile.delete(); // I don't want loader to create file but was using createNewFile() to check presence
                    System.err.println("Rota data file is missing!");
                }
            }
        }catch( Exception e ){
            ExceptionReporter.reportException(e);
        }
    }

    private void saveRota( File rotaDataFile ){
        try{

            // Make FileOutputStream for file then wrap it with ObjectOutputStream for serialisation.
            FileOutputStream fileOutputStream = new FileOutputStream( rotaDataFile );
            ObjectOutputStream objectOutputStream = new ObjectOutputStream( fileOutputStream );
            objectOutputStream.writeObject( this.employeeRotaPassedFromMain );

            fileOutputStream.flush();
            fileOutputStream.close();
            objectOutputStream.flush();
            objectOutputStream.close();

            System.out.println( "Rota saved!" );

        }catch( Exception e ){ // Will catch any subclass instance.
            ExceptionReporter.reportException( e );
        }
    }

    private void loadRota( File rotaDataFile ){
        try {
            FileInputStream fileInputStream = new FileInputStream(rotaDataFile);
            ObjectInputStream objectInputStream = new ObjectInputStream( fileInputStream );
            this.employeeRotaPassedFromMain = (TimeTable) objectInputStream.readObject();

            fileInputStream.close(); // no flush methods available.
            objectInputStream.close();

            System.out.println( "Rota loaded!" );
        }catch( Exception e ){
            ExceptionReporter.reportException( e );
        }
    }

    private void handlePdfExport(){
        System.out.print( "Insert pdf file name (alphanumerics only!):\t" );
        String pdfFileName = this.systemInputStreamScanner.nextLine();

        if( this.pdfFileNameValid( pdfFileName ) ) {
            pdfFileName += ".pdf";
            this.exportPdf(pdfFileName);
        }else{
            System.err.println( "The given filename breached the alphanumeric constraint. No PDF created." );
        }
    }

    public boolean pdfFileNameValid(String pdfFileName){
        Pattern alphaNumerics = Pattern.compile( "^[a-z0-9]+$", Pattern.CASE_INSENSITIVE );
        Matcher checkAgainstPattern = alphaNumerics.matcher( pdfFileName );
        return checkAgainstPattern.find();
    }

    private void exportPdf( String filename ){
        Font primaryHeadingFont = FontFactory.getFont( FontFactory.TIMES_BOLD, 18, BaseColor.RED );
        Font secondaryHeadingFont = FontFactory.getFont( FontFactory.TIMES_BOLD, 16, BaseColor.BLUE );
        Font tertiaryHeadingFont = FontFactory.getFont( FontFactory.TIMES_BOLD, 14, BaseColor.DARK_GRAY );
        Font bodyFont = FontFactory.getFont( FontFactory.HELVETICA, 12, BaseColor.BLACK );


        try{
            Document myPdfDoc = new Document();
            PdfWriter.getInstance( myPdfDoc, new FileOutputStream(filename) );

            myPdfDoc.open();

            Paragraph rotaHeader = new Paragraph("Staff Rota: ", primaryHeadingFont);
            rotaHeader.setAlignment( 1 ); // just guessed how to do this (0 left, 1 mid, 2 right). Also can use Paragraph.ALIGN_CENTER etc
            myPdfDoc.add( rotaHeader );
            Paragraph notesBit = new Paragraph( "Notes: " + this.employeeRotaPassedFromMain.getNotes(), bodyFont );
            myPdfDoc.add( notesBit );
            for( Day d : this.employeeRotaPassedFromMain.getDaysOfWeek() ){
                Paragraph dayHeader = new Paragraph( d.getDayOfWeek().name() , secondaryHeadingFont );
                dayHeader.setIndentationLeft(20);
                myPdfDoc.add( dayHeader );
                for( Shift s : d.getShifts() ){
                    Paragraph shiftHeader = new Paragraph( s.getShiftTime().name() + ": " + s.getNumberOfStaff() + " working.", tertiaryHeadingFont );
                    shiftHeader.setIndentationLeft(40);
                    myPdfDoc.add( shiftHeader );
                    for( Employee e : s.getAllStaff() ){
                        Paragraph employeeEntry = new Paragraph( e.getRole().name() + ": " + Employee.getProperCase(e.getFirstname()) + " " + Employee.getProperCase(e.getLastname()), bodyFont );
                        employeeEntry.setIndentationLeft(80);
                        myPdfDoc.add( employeeEntry );
                    }
                }
            }
            myPdfDoc.close();
            System.out.println("PDF'd!");
        }catch( Exception e ){
            ExceptionReporter.reportException( e );
        }

    }
}