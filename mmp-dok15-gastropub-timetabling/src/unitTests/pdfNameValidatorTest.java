package unitTests;
// BASED ON https://www.vogella.com/tutorials/JUnit/article.html

import Menu.RotaMenu;

import Roster.EmployeeRoster;
import Rota.TimeTable;
import java.util.Scanner;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class pdfNameValidatorTest {

    private RotaMenu rotaMenu;

    @BeforeEach
    void setup(){
        this.rotaMenu = new RotaMenu();
    }

    @Test
    @DisplayName("Valid test 1: abc123")
    void valid1(){
        assertTrue( this.rotaMenu.pdfFileNameValid( "abc123" ) );
    }

    @Test
    @DisplayName("Valid test 2: ABC123")
    void valid2(){
        assertTrue( this.rotaMenu.pdfFileNameValid("ABC123") );
    }

    @Test
    @DisplayName("Invalid test 1: ")
    void invalid1(){
        assertFalse( this.rotaMenu.pdfFileNameValid("") );
    }

    @Test
    @DisplayName("Invalid test 2: _abc123")
    void invalid2(){
        assertFalse( this.rotaMenu.pdfFileNameValid("_abc123") );
    }

    @Test
    @DisplayName("Invalid test 3: ABC-123")
    void invalid3(){
        assertFalse( this.rotaMenu.pdfFileNameValid("ABC_123") );
    }

    @Test
    @DisplayName("Invalid test 4: abc 123")
    void invalid4(){
        assertFalse( this.rotaMenu.pdfFileNameValid("abc 123") );
    }
}
