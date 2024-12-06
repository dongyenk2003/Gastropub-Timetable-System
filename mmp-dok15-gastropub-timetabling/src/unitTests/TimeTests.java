package unitTests;
// BASED ON https://www.vogella.com/tutorials/JUnit/article.html

import Roster.Time;
import Rota.DayOfWeek;
import Rota.ShiftTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TimeTests {
    private Time unavailableTime;
    @BeforeEach
    void setup(){
        this.unavailableTime = new Time( DayOfWeek.MONDAY, ShiftTime.NIGHT );
    }

    @Test
    @DisplayName( "Cannot work Monday night." )
    void test1(){
        assertFalse( this.unavailableTime.canWork( DayOfWeek.MONDAY, ShiftTime.NIGHT ) );
    }

    @Test
    @DisplayName( "Can work Monday day." )
    void test2(){
        assertTrue( this.unavailableTime.canWork( DayOfWeek.MONDAY, ShiftTime.DAY) );
    }

    @Test
    @DisplayName( "Can work Sunday night." )
    void test3(){
        assertTrue( this.unavailableTime.canWork( DayOfWeek.SUNDAY, ShiftTime.NIGHT ) );
    }
}
