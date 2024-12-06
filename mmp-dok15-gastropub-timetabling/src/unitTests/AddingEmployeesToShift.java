package unitTests;
// CODE BASED ON https://www.vogella.com/tutorials/JUnit/article.html

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Roster.*;
import Rota.*;

public class AddingEmployeesToShift{
    private Shift testShift;
    private Employee bar1;
    private Employee chef1;
    private Employee server1;
    private Employee kp1;
    @BeforeEach
    public void setup(){
        this.testShift = new Shift( ShiftTime.DAY );
        this.bar1 = new Employee( new Email("pjthompson@gmail.com"), "Paul", "Thompson", Role.BAR);
        this.chef1 = new Employee( new Email("rah31@aber.ac.uk"), "rachel", "horton", Role.CHEF );
        this.server1 = new Employee( new Email("garry@gmail.com"), "garry", "garryson", Role.SERVER );
        this.kp1 = new Employee( new Email("dok15@aber.ac.uk"), "dongyen", "keay", Role.KP );
    }

    @DisplayName("Adding bar to bar slot.")
    @Test
    public void addBarToBarSlot(){
        assertTrue( this.testShift.addBar( bar1 ) );
    }

    @DisplayName( "Adding chef to chef slot." )
    @Test
    public void addChefToChefSlot(){
        assertTrue( this.testShift.addChef( chef1 ) );
    }

    @DisplayName( "Adding server to server slot." )
    @Test
    public void addServerToServerSlot(){
        assertTrue( this.testShift.addServer( server1 ) );
    }

    @DisplayName("Adding kp to kp slot.")
    @Test
    public void addKpToKpSlot(){
        assertTrue( this.testShift.addKp( kp1 ) );
    }

    @DisplayName( "Adding chef to bar slot." )
    @Test
    public void addChefToBarSlot(){
        assertFalse( this.testShift.addBar( chef1 ) );
    }

    @DisplayName("Adding Server to bar slot.")
    @Test
    public void addServerToBarSlot(){
        assertFalse( this.testShift.addBar( server1 ) );
    }

    @DisplayName("Adding kp to bar slot.")
    @Test
    public void addKpToBarSlot(){
        assertFalse( this.testShift.addBar( kp1 ) );
    }

    @DisplayName("Adding bar to chef slot.")
    @Test
    public void addBarToChefSlot(){
        assertFalse( this.testShift.addChef( bar1 ) );
    }

    @DisplayName("Adding server to chef slot.")
    @Test
    public void addServerToChefSlot(){
        assertFalse( this.testShift.addChef( server1 ) );
    }

    @DisplayName("Adding kp to chef slot.")
    @Test
    public void addKpToChefSlot(){
        assertFalse( this.testShift.addChef( kp1 ) );
    }

    @DisplayName("Adding bar to server slot.")
    @Test
    public void addBarToServerSlot(){
        assertFalse( this.testShift.addServer( bar1 ) );
    }

    @DisplayName("Adding chef to server slot.")
    @Test
    public void addChefToServerSlot(){
        assertFalse( this.testShift.addServer( chef1 ) );
    }

    @DisplayName( "Adding kp to server slot." )
    @Test
    public void addKpToServerSlot(){
        assertFalse( this.testShift.addServer( kp1 ) );
    }

    @DisplayName("Adding bar to kp slot. ")
    @Test
    public void addBarToKpSlot(){
        assertFalse( this.testShift.addKp( bar1 ) );
    }
    @DisplayName("Adding chef to kp slot.")
    @Test
    public void addChefToKpSlot(){
        assertFalse( this.testShift.addKp( chef1 ) );
    }

    @DisplayName("Adding server to kp slot. THIS IS LEGIT.")
    @Test
    public void addServerToKpSlot(){
        assertTrue( this.testShift.addKp( server1 ) );
    }



}