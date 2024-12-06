package unitTests;
// Based on https://www.vogella.com/tutorials/JUnit/article.html

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static Roster.Email.formatIsValid;

/** Checks correctness of email format enforcement.
 * */
public class EmailTests {
    @Test
    @DisplayName( "Check valid email: dok15@csmail.com" )
    void checkValidEmail(){
        assertTrue( formatIsValid( "dok15@csmail.com" ) );
    }

    @Test
    @DisplayName( "Check valid email: dongyen.keay@csail.com." )
    void checkValidEmail2(){
        assertTrue( formatIsValid( "dongyen.keay@csail.com" ) );
    }

    @Test
    @DisplayName( "Check valid email: dok15@aber.ac.uk" )
    void checkValidEmail3(){
        assertTrue( formatIsValid( "dok15@aber.ac.uk" ) );
    }

    @Test
    @DisplayName( "Check valid email: dok.15@aber.ac.uk" )
    void checkValidEmail4(){
        assertTrue( formatIsValid( "dok.15@aber.ac.uk" ) );
    }

    @Test
    @DisplayName( "Check valid email: DOK15@CSMAIL.COM" )
    void checkValidEmail5(){ assertTrue( formatIsValid( "DOK15@ABER.AC.UK" ) ); }

    @Test
    @DisplayName( "Check valid email: DOK15@ABER.AC.UK" )
    void checkValidEmail6(){ assertTrue( formatIsValid( "DOK15@ABER.AC.UK" ) ); }

    @Test
    @DisplayName( "Check valid email: DOK_15_DON.CASHEW-EATER-2003..@ABER.AC.UK" )
    void checkValidEmail7(){ assertTrue( formatIsValid( "DOK_15_DON.CASHEW-EATER-2003..@ABER.AC.UK" ) ); }

    @Test
    @DisplayName( "Check valid email: dok_15_don.cashew-eater-2003..@aber.ac.uk" )
    void checkValidEmail8(){ assertTrue( formatIsValid( "dok_15_don.cashew-eater-2003..@aber.ac.uk" ) ); }

    @Test
    @DisplayName( "Check valid email: DOK_15_DON.CASHEW-EATER-2003..@GMAIL.COM" )
    void checkValidEmail9(){ assertTrue( formatIsValid( "DOK_15_DON.CASHEW-EATER-2003..@GMAIL.COM" ) ); }

    @Test
    @DisplayName( "Check valid email: dok_15_don.cashew-eater-2003..@gmail.com" )
    void checkValidEmail10(){ assertTrue( formatIsValid( "dok_15_don.cashew-eater-2003..@gmail.com" ) ); }

    @Test
    @DisplayName( "Check invalid email: dok15@aber" )
    void checkInvalidEmail1(){
        assertFalse( formatIsValid( "dok15@aber" ) );
    }

    @Test
    @DisplayName( "Check invalid email: dok15@aber." )
    void checkInvalidEmail2(){
        assertFalse( formatIsValid( "dok15@aber." ) );
    }

    @Test
    @DisplayName( "Check invalid email: dok15@aber.ac." )
    void checkInvalidEmail3(){
        assertFalse( formatIsValid( "dok15@aber.ac." ) );
    }

    @Test
    @DisplayName( "Check invalid email: dok15@aber.4c.uk" )
    void checkInvalidEmail4(){
        assertFalse( formatIsValid( "dok15@aber.4c.uk" ) );
    }

    @Test
    @DisplayName( "Check invalid email: DOK15@ABER" )
    void checkInvalidEmail5(){
        assertFalse( formatIsValid( "DOK15@ABER" ) );
    }

    @Test
    @DisplayName( "Check invalid email: DOK15@ABER.4C.UK" )
    void checkInvalidEmail6(){
        assertFalse( formatIsValid( "DOK15@ABER.4C.UK" ) );
    }

    @Test
    @DisplayName( "Check valid email: DOK_15_DON.CASHEW-EATER-2003..@ABER.AC.CO.UK" )
    void checkInvalidEmail7(){ assertFalse( formatIsValid( "DOK_15_DON.CASHEW-EATER-2003..@ABER.AC.CO.UK" ) ); }

    @Test
    @DisplayName( "Check valid email: dok_15_don.cashew-eater-2003..@aber.ac.co.uk" )
    void checkInvalidEmail8(){ assertFalse( formatIsValid( "dok_15_don.cashew-eater-2003..@aber.ac.co.uk" ) ); }

    @Test
    @DisplayName( "Check valid email: DOK_15_DON.CASHEW-EATER-2003..@ABER" )
    void checkInvalidEmail9(){ assertFalse( formatIsValid( "DOK_15_DON.CASHEW-EATER-2003..@ABER" ) ); }

    @Test
    @DisplayName( "Check valid email: dok_15_don.cashew-eater-2003..@aber" )
    void checkInvalidEmail10(){ assertFalse( formatIsValid( "dok_15_don.cashew-eater-2003..@aber" ) ); }
}
