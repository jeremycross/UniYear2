package level;

import dnd.models.Monster;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


public class PassageTest {

    Passage pass;
    public PassageTest() {
    }

    @Before
    public void setup(){
        pass = new Passage(false);
    }

    @Test
    public void testConstructor() {
        System.out.println("testConstructor");
        Passage p = new Passage(true);

        assertEquals(2, p.getThePassage().size());
    }

    @Test
    public void testGetDoors() {
        System.out.println("testGetDoors");
        int sizeBefore = pass.getDoors().size();
        pass.setDoor(new Door());

        assertEquals(sizeBefore + 1, pass.getDoors().size());
    }

    @Test
    public void testGetDoor() {
        System.out.println("testGetDoor");
        assertNull(pass.getDoor(10));

    }

    @Test
    public void testGetThePassageOne() {
        System.out.println("testGetThePassage");
        PassageSection p1 = new PassageSection("door");
        PassageSection p2 = new PassageSection("door");
        PassageSection p3 = new PassageSection("door");
        pass.addPassageSection(p1);
        pass.addPassageSection(p2);
        pass.addPassageSection(p3);
        boolean test = false;

        if(pass.getThePassage().get(0).getDoor() != null) {
            test = true;
        }

        assertTrue(test);
    }

    @Test
    public void testGetThePassageTestTwo() {
        System.out.println("testGetThePassage");
        PassageSection p1 = new PassageSection("");
        PassageSection p2 = new PassageSection("door");
        PassageSection p3 = new PassageSection("door");
        pass.addPassageSection(p1);
        pass.addPassageSection(p2);
        pass.addPassageSection(p3);

        boolean test = false;

        if(pass.getThePassage().get(0).getDoor() != null) {
            test = true;
        }

        assertFalse(test);
    }

    @Test
    public void testGetThePassageTestThree() {
        System.out.println("testGetThePassage");
        PassageSection p1 = new PassageSection("");
        PassageSection p2 = new PassageSection("door");
        PassageSection p3 = new PassageSection("door");
        pass.addPassageSection(p1);
        pass.addPassageSection(p2);
        pass.addPassageSection(p3);

        assertEquals(3, pass.getThePassage().size());
    }


    @Test
    public void testSetPassageNum() {
        System.out.println("testSetPassageNum");
        int test = 10;
        pass.setPassageNum(test);

        assertEquals(test, pass.getPassageNum());
    }

    @Test
    public void testGetPassageNum() {
        System.out.println("testGetPassageNum");
        int test = 3;
        pass.setPassageNum(test);

        assertEquals(test, pass.getPassageNum());
    }

    @Test
    public void testAddPassageSection() {
        System.out.println("testAddPassageSection");
        pass.addPassageSection(new PassageSection());
        assertEquals(1, pass.getThePassage().size());
    }

    @Test
    public void testGetDescription() {
        System.out.println("testGetDescription");
        PassageSection p1 = new PassageSection("");
        PassageSection p2 = new PassageSection("door");
        PassageSection p3 = new PassageSection("door");
        pass.addPassageSection(p1);
        pass.addPassageSection(p2);
        pass.addPassageSection(p3);
        boolean test = false;

        if(pass.getDescription().contains("door")) {
            test = true;
        }

        assertTrue(test);
    }

    @Test
    public void testSetDoor() {
        System.out.println("testSetDoor");
        Door door = new Door();
        int sizeBefore = pass.getDoors().size();
        pass.setDoor(door);

        assertEquals(sizeBefore + 1, pass.getDoors().size());
    }

    @Test
    public void testGetDoorOne() {
        System.out.println("testGetDoor");
        Door door = new Door();
        pass.setDoor(door);

        assertNull(pass.getDoor(3));
    }

    @Test
    public void testGetDoorTwo() {
        System.out.println("testGetDoor");
        Door door = new Door();
        int sizeBefore = pass.getDoors().size();
        pass.setDoor(door);

        assertEquals(door, pass.getDoor(sizeBefore));
    }

    @Test
    public void testAddMonster() {
        System.out.println("testAddMonster");
        PassageSection p1 = new PassageSection("Monster");
        PassageSection p2 = new PassageSection("door");
        pass.addPassageSection(p1);
        pass.addPassageSection(p2);
        Monster monster = new Monster();
        pass.addMonster(monster, 1);

        assertEquals(monster, pass.getMonster(1));
    }

    @Test
    public void testGetMonster() {
        System.out.println("testGetMonster");
        PassageSection p1 = new PassageSection("Monster");
        PassageSection p2 = new PassageSection("door");
        pass.addPassageSection(p1);
        pass.addPassageSection(p2);

        assertNull(pass.getMonster(3));
    }

}
