package level;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class DoorTest {

    Door door;

    public DoorTest() {
    }

    @Before
    public void setup() {
        door = new Door();
    }


    @Test
    public void testConstructor() {
        System.out.println("testConstructor");
        assertNotNull(door);
    }

    @Test
    public void testOtherConstructor() {
        System.out.println("testOtherConstructor");
        Door d = new Door(true);
        assertTrue(d.isArchway());
    }

    @Test
    public void testSetArchway() {
        System.out.println("testSetArchway");
        door.setArchway(true);

        assertTrue(door.isArchway());
    }

    @Test
    public void testIsArchway() {
        System.out.println("testIsArchway");
        door.setArchway(true);

        assertTrue(door.isArchway());
    }

    @Test
    public void testSetTrappedOne() {
        System.out.println("testSetTrapped");
        door.setArchway(false);
        door.setTrapped(true);

        assertTrue(door.isTrapped());
    }

    @Test
    public void testSetTrappedTwo() {
        System.out.println("testSetTrapped");
        door.setArchway(false);
        door.setTrapped(true, 1);
        String expectedDescription = "Secret Door: Non-elf locates 3 in 20, elf locates 5 in 20, magical device locates 18 in 20 ";

        assertEquals(expectedDescription, door.getTrapDescription());
    }

    @Test
    public void testSetTrappedThree() {
        System.out.println("testSetTrapped");
        door.setArchway(true);
        door.setTrapped(true, 1);

        assertFalse(door.isTrapped());
    }

    @Test
    public void testIsTrapped() {
        System.out.println("testIsTrapped");
        door.setArchway(true);
        door.setTrapped(true, 1);

        assertFalse(door.isTrapped());
    }

    @Test
    public void testSetOpenOne() {
        System.out.println("testSetOpen");
        door.setArchway(false);
        door.setOpen(false);

        assertFalse(door.isOpen());
    }

    @Test
    public void testSetOpenTwo() {
        System.out.println("testSetOpen");
        door.setArchway(true);
        door.setOpen(false);

        assertTrue(door.isOpen());
    }

    @Test
    public void testIsOpen() {
        System.out.println("testIsOpen");
        door.setArchway(true);
        door.setOpen(false);

        assertTrue(door.isOpen());
    }

    @Test
    public void testSetLockedOne() {
        System.out.println("testSetLocked");
        door.setArchway(false);
        door.setLocked(true);

        assertTrue(door.isLocked());
    }

    @Test
    public void testSetLockedTwo() {
        System.out.println("testSetLocked");
        door.setArchway(true);
        door.setLocked(true);

        assertFalse(door.isLocked());
    }

    @Test
    public void testSetLockedThree() {
        System.out.println("testSetLocked");
        door.setArchway(false);
        door.setOpen(true);
        door.setLocked(true);

        assertFalse(door.isOpen());
    }

    @Test
    public void testIsLocked() {
        System.out.println("testIsLocked");
        door.setArchway(true);
        door.setLocked(true);

        assertFalse(door.isLocked());
    }

    @Test
    public void testGetTrapDescription() {
        System.out.println("testGetTrapDescription");
        door.setArchway(false);
        door.setTrapped(true, 6);
        String expectedDescription = " Pit, 10' deep, 3 in 6 to fall in.";

        assertEquals(expectedDescription, door.getTrapDescription());
    }

    @Test
    public void testAddSpace() {
        System.out.println("testAddSpace");
        int numSpacesBefore = door.getSpaces().size();
        door.addSpace(new Chamber());

        assertEquals(numSpacesBefore + 1, door.getSpaces().size());
    }

    @Test
    public void testGetSpaces() {
        System.out.println("testGetSpaces");
        int numSpacesBefore = door.getSpaces().size();
        door.addSpace(new Chamber());

        assertEquals(numSpacesBefore + 1, door.getSpaces().size());
    }

    @Test
    public void testSetSpacesOne() {
        System.out.println("testSetSpaces");
        Passage p1 = new Passage(false);
        Chamber c2 = new Chamber();
        int numSpacesBefore = door.getSpaces().size();

        door.setSpaces(c2, p1);

        assertEquals(numSpacesBefore + 2, door.getSpaces().size());
    }

    @Test
    public void testSetSpacesTwo() {
        System.out.println("testSetSpaces");
        Passage p1 = new Passage(false);
        Chamber c2 = new Chamber();
        int unlinkedBefore = c2.getNumUnlinked();

        door.setSpaces(c2, p1);

        assertEquals(unlinkedBefore - 1, c2.getNumUnlinked());
    }

    @Test
    public void testGetDescription() {
        System.out.println("testGetDescription");
        door.setArchway(true);
        String s = door.getDescription();

        assertTrue(s.contains("archway"));
    }

    @Test
    public void testSetAlreadyLinkedSpaces() {
        Passage p1 = new Passage(false);
        Chamber c2 = new Chamber();
        int unlinkedBefore = c2.getNumUnlinked();

        door.setAlreadyLinkedSpaces(c2, p1);

        assertEquals(unlinkedBefore, c2.getNumUnlinked());
    }

    @Test
    public void testPrintIfConnectedOne() {
        System.out.println("testPrintIfConnected");
        Chamber c1 = new Chamber();
        Chamber c2 = new Chamber();

        door.setSpaces(c2, c1);

        assertEquals(true, door.printIfConnected(false));
    }

    @Test
    public void testPrintIfConnectedTwo() {
        System.out.println("testPrintIfConnected");
        Chamber c1 = new Chamber();
        Chamber c2 = new Chamber();

        door.setSpaces(c2, c1);

        assertEquals(false, door.printIfConnected(true));
    }

    @Test
    public void testPrintIfConnectedThree() {
        System.out.println("testPrintIfConnected");
        Passage p2 = new Passage(false);
        Passage p1 = new Passage(false);

        door.setSpaces(p2, p1);

        assertEquals(false, door.printIfConnected(false));
    }

    @Test
    public void testIncrementPrintIndexOne() {
        System.out.println("testIncrementPrintIndex");
        door.resetPrintIndex();
        door.setSpaces(new Chamber(), new Chamber());
        door.setSpaces(new Chamber(), new Chamber());
        door.incrementPrintIndex();

        assertEquals(2, door.getPrintIndex());
    }

    @Test
    public void testIncrementPrintIndexTwo() {
        System.out.println("testIncrementPrintIndex");
        door.resetPrintIndex();
        door.setSpaces(new Chamber(), new Chamber());
        door.incrementPrintIndex();

        assertEquals(2, door.getPrintIndex());
    }

    @Test
    public void testResetPrintIndex() {
        System.out.println("testResetPrintIndex");
        door.resetPrintIndex();
        door.setSpaces(new Chamber(), new Chamber());
        door.setSpaces(new Chamber(), new Chamber());
        door.incrementPrintIndex();

        door.resetPrintIndex();

        assertEquals(0, door.getPrintIndex());
    }

}
