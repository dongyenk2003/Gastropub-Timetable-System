package unitTests;
// Based on https://www.vogella.com/tutorials/JUnit/article.html

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import Roster.EmployeeRoster;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class SemiColonInputDetection {

    private EmployeeRoster employeeRoster;

    @BeforeEach
    public void setup(){
        this.employeeRoster = new EmployeeRoster();
    }

    @Test
    @DisplayName("String has no semi-colon 1")
    public void stringNoSemiColon1(){
        assertFalse( this.employeeRoster.semiColonAnyWhereInString("Gareth had a bale of hay.") );
    }

    @Test
    @DisplayName("String has semi-colon at start")
    public void stringHasSemi1(){
        assertTrue( this.employeeRoster.semiColonAnyWhereInString( ";dok15@aber.ac.uk" ) );
    }

    @Test
    @DisplayName( "String has semi-colon at end" )
    public void stringHasSemi2(){
        assertTrue( this.employeeRoster.semiColonAnyWhereInString( "DOK15@ABER.AC.UK;" ) );
    }

    @Test
    @DisplayName( "String has semi-colon in middle" )
    public void stringHasSemi3(){
        assertTrue( this.employeeRoster.semiColonAnyWhereInString( "dok15'); DROP TABLE staff; --" ) );
    }

    @Test
    @DisplayName( "String array has no semi-colon" )
    public void stringArrayNoSemi(){
        assertFalse( this.employeeRoster.semiColonAnywhereInArray( new String[]{"dok15@aber.ac.uk", "do", "k"} ) );
    }

    @Test
    @DisplayName( "String array has semi-colon at start" )
    public void stringArraySemiStart(){
        assertTrue( this.employeeRoster.semiColonAnywhereInArray( new String[]{"dok15@aber.ac.uk'); --", "do", "k"} ) );
    }

    @Test
    @DisplayName( "String array has semi-colon at end" )
    public void stringArraySemiEnd(){
        assertTrue( this.employeeRoster.semiColonAnywhereInArray( new String[]{"dok15", "do", "k15'); --"} ) );
    }

    @Test
    @DisplayName( "String array has semi-colon in middle" )
    public void stringArraySemiMiddle(){
        String[] data = new String[]{ "dok15@aber.ac.uk", "dongyen'); DROP TABLE staff; --", "keay" };
        assertTrue( this.employeeRoster.semiColonAnywhereInArray( data ) );
    }
}
