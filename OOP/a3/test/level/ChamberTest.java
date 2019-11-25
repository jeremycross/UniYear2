package level;

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Treasure;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Random;

public class ChamberTest {
    ChamberShape theShape;
    ChamberContents theContents;

    Random rand = new Random();

    public ChamberTest() {
    }

    @Before
    public void setup() {
        int roll = rand.nextInt(19) + 1;
        theShape =  ChamberShape.selectChamberShape(roll);
        theContents = new ChamberContents();
        roll = rand.nextInt(20) + 1;
        theContents.chooseContents(roll);
    }

    @Test
    public void testConstructor() {
        System.out.println("testDefaultConstructor");
        Chamber c = new Chamber();
        String s = c.getDescription();

        assertNotNull(s);
    }

    @Test
    public void testOtherConstructor() {
        System.out.println("testOtherConstructor");
        Chamber c = new Chamber(theShape, theContents);
        String s = c.getDescription();

        assertNotNull(s);
    }

    @Test
    public void testAddMonsterOne() {
        System.out.println("testAddMonster");
        int roll = rand.nextInt(100) + 1;
        int sizeBefore;
        ArrayList<Monster> monstersArrayList;
        Chamber c = new Chamber();
        sizeBefore = c.getMonsters().size();

        c.addMonster(new Monster());
        c.addMonster(new Monster());
        c.addMonster(new Monster());
        monstersArrayList = c.getMonsters();

        assertEquals(monstersArrayList.size(), sizeBefore + 3);
    }

    @Test
    public void testAddMonsterTwo() {
        System.out.println("testAddMonster");
        int roll = rand.nextInt(100) + 1;
        int sizeBefore;
        ArrayList<Monster> monstersArrayList;
        Chamber c = new Chamber();
        sizeBefore = c.getMonsters().size();
        Monster testMonster = new Monster();
        testMonster.setType(roll);
        c.addMonster(testMonster);

        String expected = testMonster.getDescription();
        String actual = c.getMonsters().get(sizeBefore).getDescription();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetMonsters() {
        System.out.println("testGetMonsters");
        ArrayList<Monster> list;
        Chamber c = new Chamber();
        c.addMonster(new Monster());

        list = c.getMonsters();

        assertNotNull(list.get(0));
    }

    @Test
    public void testAddTreasureOne() {
        System.out.println("testAddTreasure");
        int roll = rand.nextInt(100) + 1;
        int sizeBefore;
        Chamber c = new Chamber();
        sizeBefore = c.getTreasureList().size();

        c.addTreasure(new Treasure());
        c.addTreasure(new Treasure());
        c.addTreasure(new Treasure());

        assertEquals(c.getTreasureList().size(), sizeBefore + 3);
    }

    @Test
    public void testAddTreasureTwo() {
        System.out.println("testAddTreasure");
        int roll = rand.nextInt(100) + 1;
        int sizeBefore;
        Chamber c = new Chamber();
        sizeBefore = c.getTreasureList().size();
        Treasure treasure = new Treasure();
        treasure.chooseTreasure(roll);
        c.addTreasure(treasure);

        String expected = treasure.getDescription();
        String actual = c.getTreasureList().get(sizeBefore).getDescription();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetTreasuresList() {
        System.out.println("testGetTreasuresList");
        ArrayList<Treasure> list;
        Chamber c = new Chamber();
        c.addTreasure(new Treasure());

        list = c.getTreasureList();

        assertNotNull(list.get(0));
    }

    @Test
    public void testGetUnlinkedDoors() {
        System.out.println("testGetUnlinkedDoors");
        int numExits = theShape.getNumExits();
        Chamber c = new Chamber(theShape, theContents);
        int numDoors = c.getNumUnlinked();

        assertEquals(numDoors, numExits);
    }

    @Test
    public void testAddDoorOne() {
        System.out.println("testAddDoor");
        Chamber c = new Chamber(theShape, theContents);
        int numExits = theShape.getNumExits();

        c.addDoor(new Door(), false);
        int numDoors = c.getNumUnlinked();

        assertEquals(numExits + 1, numDoors);
    }

    @Test
    public void testAddDoorTwo() {
        System.out.println("testAddDoor");
        Chamber c = new Chamber(theShape, theContents);
        int numExits = theShape.getNumExits();

        c.addDoor(new Door(), true);
        int numDoors = c.getNumUnlinked();
        assertEquals(numExits - 1, numDoors);
    }

    @Test
    public void testSetChamberNum() {
        System.out.println("testSetChamberNum");
        Chamber c = new Chamber(theShape, theContents);
        int changeNum = 5;
        c.setChamberNum(changeNum);

        assertEquals(changeNum, c.getChamberNum());
    }

    @Test
    public void testGetChamberNum() {
        System.out.println("testGetChamberNum");
        Chamber c = new Chamber(theShape, theContents);
        int changeNum = 10;
        c.setChamberNum(changeNum);

        assertEquals(changeNum, c.getChamberNum());
    }

    @Test
    public void testGetDescription() {
        System.out.println("testGetDescription");
        Chamber c = new Chamber(theShape, theContents);
        String conDescription = theContents.getDescription();

        assertTrue(c.getDescription().contains(conDescription));
    }

    @Test
    public void testSetDoor() {
        System.out.println("testSetDoor");
        Chamber c = new Chamber(theShape, theContents);
        Door doorToAdd = new Door();
        int oldValue = doorToAdd.getSpaces().size();
        c.setDoor(doorToAdd);
        int actual = doorToAdd.getSpaces().size();

        assertEquals(oldValue + 1, actual);
    }

    @Test
    public void testSetDoorAlreadyLinked() {
        System.out.println("testSetDoorAlreadyLinked");
        Chamber c = new Chamber(theShape, theContents);
        Door doorToAdd = new Door();
        int oldValue = doorToAdd.getSpaces().size();
        c.setDoor(doorToAdd);
        int actual = doorToAdd.getSpaces().size();

        assertEquals(oldValue + 1, actual);
    }
    
}