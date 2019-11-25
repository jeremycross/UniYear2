package level;

import dnd.models.Exit;
import dnd.models.Monster;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class PassageSectionTest {

    PassageSection sect;
    public PassageSectionTest() {
    }

    @Before
    public void setup() {
        sect = new PassageSection();
    }

    @Test
    public void testConstructor() {
        PassageSection p = new PassageSection();

        assertNotNull(p);
    }

    @Test
    public void testAddDoor() {
        System.out.println("testAddDoor");
        Door newDoor = new Door();
        sect.addDoor(newDoor);

        assertEquals(newDoor, sect.getDoor());
    }

    @Test
    public void testGetDoor() {
        System.out.println("testGetDoor");
        sect = new PassageSection("");

        assertNull(sect.getDoor());
    }

    @Test
    public void testGetDescription() {
        System.out.println("testGetDescription");
        sect = new PassageSection("passage goes straight for 10 ft");
        String expected = "passage goes straight for 10 ft";

        assertEquals(expected, sect.getDescription());
    }

    @Test
    public void testSetMonster() {
        System.out.println("testSetMonster");
        Monster monster = new Monster();
        monster.setType(1);
        sect.setMonster(monster);

        assertEquals(monster, sect.getMonster());
    }

    @Test
    public void testGetMonsterOne() {
        System.out.println("testGetMonster");
        sect = new PassageSection("");

        assertNull(sect.getMonster());
    }

    @Test
    public void testGetMonsterTwo() {
        System.out.println("testGetMonster");
        sect = new PassageSection("Wandering monster (passage continues straight for 10 ft)");
        boolean test = false;

        if(sect.getMonster() != null) {
            test = true;
        }
        assertTrue(test);
    }

    @Test
    public void testGetStairsOne() {
        System.out.println("testGetStairs");
        sect = new PassageSection("");

        assertNull(sect.getStairs());
    }

    @Test
    public void testGetStairsTwo() {
        System.out.println("testGetStairs");
        sect = new PassageSection("Stairs, (passage goes straight for 10 ft)");
        boolean test = false;

        if(sect.getStairs() != null) {
            test = true;
        }
        assertTrue(test);
    }
}
