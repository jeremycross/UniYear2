package level;
import dnd.die.D20;
import dnd.die.D6;
import dnd.die.Die;
import dnd.models.Trap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Door implements Serializable {

    /**
     * trap on door.
     */
    private Trap trap;

    /**
     * if the door is trapped or not.
     */
    private boolean trapped;

    /**
     * if the door is open or not.
     */
    private boolean open;

    /**
     * if the door is an archway.
     */
    private boolean archway;

    /**
     * if the door is locked.
     */
    private boolean locked;

    /**
     * used for random generation.
     */
    private Die d20 = new D20();

    /**
     * used for random generation.
     */
    private Random rand = new Random();

    /**
     * used for random generation.
     */
    private Die d6 = new D6();

    /**
     * description of the door.
     */
    private String description;

    /**
     * spaces that the door connects.
     */
    private ArrayList<Space> spaces = new ArrayList<Space>();

    /**
     * Used to track which set of spaces to print for the door.
     */
    private int printIndex = 0;

    /**
     * Basic Constructor for the Door, will create the
     * door object with random attributes.
     */
    public Door() {
        int roll = rand.nextInt(10) + 1; //Used a random number to decide what type of door to make
        if (roll == 10) {
            setRandBooleans(true);
        } else {
            setRandBooleans(false);
        }
    }

    /**
     * Constructor for a door that is used to create a random that will
     * be connected to a chamber exit used to specifically create
     * an archway for the Exit or to generate a door that is not an archway.
     * @param arch true if the door is to be an archway.
     */
    public Door(boolean arch) { //Constructor used when a passage section with a arch is created, we need to add a Door to that section
        setRandBooleans(arch);
    }

    /**
     * Used to set the booleans for the door, such as archway and open. randomly.
     * @param arch a boolean representing if the door is to be an archway or not.
     */
    private void setRandBooleans(boolean arch) {

        if (arch) {
            setArchway(true);
        } else {
            setArchway(false);
            setRandTrap();
            setRandLocked();
        }
    }

    /**
     * Decides to set a trap on the door.
     * Sets a trap if the door is to be trapped.
     */
    private void setRandTrap() {
        int roll = d20.roll();
        //Set a trap or to not set a trap, that is the question
        if (roll == 20) {
            setTrapped(true);
        } else {
            setTrapped(false);
        }
    }

    /**
     * Randomly sets the door to locked or unlocked.
     */
    private void setRandLocked() {
        int roll = d6.roll();

        if (roll == 6) {
            setLocked(true);
            setOpen(false);
        } else {
            setLocked(false);
            setRandOpen();
        }
    }

    /**
     * Randomly sets the door to open or closed (provided not an archway or locked).
     */
    private void setRandOpen() {
        int roll = d6.roll();

        if (roll >= 0 && roll <= 3) {
            setOpen(false);
        } else {
            setOpen(true);
        }
    }

    /**
     * Creates and adds a trap to the door either based on an input value or randomly.
     * @param flag If passed as true will create a trap randomly or passed on input.
     * @param roll An optional input to generate the trap based on the value given (if one is given).
     */
    public void setTrapped(boolean flag, int... roll) {

        int var;

        if (flag && !(archway)) {
            trapped = true;
            if (roll.length == 1) {
                var = roll[0];
            } else {
                var = d20.roll();
            }
            trap = new Trap();
            trap.chooseTrap(var);
        } else {
            trapped = false;
        }
    }

    /**
     * Sets instance variable of open in door based on boolean passed
     * (unless the door is an archway, then it must be open).
     * @param flag Sets open equal to flag if door is not an archway otherwise open will stay true.
     */
    public void setOpen(boolean flag) {

        if (archway) {
            open = true;
        } else {
            open = flag;
        }

    }

    /**
     * If a true is passed sets all attributes in door to be an archway.
     * @param flag If true sets archway to true
     */
    public void setArchway(boolean flag) {
        archway = flag;

        if (archway) {
            setTrapped(false);
            setOpen(true);
            setLocked(false);
        }
    }

    /**
     * Returns true if the door is trapped or false if not trapped.
     * @return Returns true if door is trapped otherwise returns false.
     */
    public boolean isTrapped() {
        return trapped;
    }

    /**
     * Returns true if the door is open or false if closed.
     * @return Returns true if door is open otherwise returns false.
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Returns true if the door is an archway or false if not an archway.
     * @return Returns true if door is an archway otherwise returns false.
     */
    public boolean isArchway() {
        return archway;
    }

    /**
     * Returns a string of trap set on door.
     * @return Returns a string of a description of the trap set on the door.
     */
    public String getTrapDescription() {
        return trap.getDescription();
    }

    /**
     * Add the inputted space to spaces array list of door.
     * @param theSpace space to be added.
     */
    public void addSpace(Space theSpace) {
        spaces.add(theSpace);
    }

    /**
     * Returns am array list of spaces that the door is connected to.
     * @return Returns the array list of spaces that the door is connected to.
     */
    public ArrayList<Space> getSpaces() {
        //returns the two spaces that are connected by the door
        return spaces;
    }

    /**
     * Connects the door to the two spaces that it connects and adds them to the space array list.
     * @param spaceOne The first space that the door is connected to.
     * @param spaceTwo The second space that the door is connected to.
     */
    public void setSpaces(Space spaceOne, Space spaceTwo) {
        //identifies the two spaces with the door
        // this method should also call the addDoor method from Space

        spaceOne.setDoor(this);
        spaceTwo.setDoor(this);
    }

    /**
     * Connects the door to two spaces (used when it has already been connected to two other spaces).
     * @param c chamber to be connected to door.
     * @param p passage to be connected to door.
     */
    public void setAlreadyLinkedSpaces(Chamber c, Passage p) {
        c.setDoorAlreadyLinked(this);
        p.setDoor(this);
    }

    /**
     * Returns a description of the door.
     * @return Returns a string that describes the door and any traps applied to it.
     */
    public String getDescription() {
        StringBuilder string = new StringBuilder();

        formatConnectionString(string);
        formatDoorDescription(string);
        description = string.toString();

        return description;
    }


    /**
     * Appends formatted string of door's passage connections to inputted String builder.
     * @param string string builder being altered.
     */
    private void formatConnectionString(StringBuilder string) {
        if (spaces.size() > 1) {
            string.append("Door has ").append(spaces.size() / 2).append(" connection(s): ");
            while (printIndex < spaces.size()) {
                if (spaces.get(printIndex) instanceof Passage && spaces.get(1 + printIndex) instanceof Passage) {
                    string.append("Door branches from a passage to another passage. ");
                } else if (spaces.get(printIndex) instanceof Chamber && spaces.get(1 + printIndex) instanceof Passage) {
                    string.append("Door leads into chamber ");
                    string.append(((Chamber) spaces.get(printIndex)).getChamberNum());
                    string.append(" from passage ");
                    string.append(((Passage) spaces.get(printIndex + 1)).getPassageNum());
                    string.append(". ");
                }
                incrementPrintIndex();
            }
            resetPrintIndex();
        }
    }

    /**
     * Appends a formatted string description of door based on instance booleans.
     * @param string string builder being altered.
     */
    private void formatDoorDescription(StringBuilder string) {
        if (archway) {
            string.append("doorway is an archway. It is open and does not have a trap");
        } else {
            formatLockedDescription(string);
            formatTrappedDescription(string);
        }
    }

    /**
     * Appends a formatted string description of door based on locked and open instance booleans.
     * @param string string builder being altered.
     */
    private void formatLockedDescription(StringBuilder string) {
        if (locked) {
            string.append("door is closed and is locked.");
        } else {
            if (open) {
                string.append("door is wide open.");
            } else {
                string.append("door is closed shut, however, it is unlocked.");
            }
        }
    }

    /**
     * Appends a formatted string description of door based on trapped instance boolean and trap description.
     * @param string string builder being altered.
     */
    private void formatTrappedDescription(StringBuilder string) {
        if (trapped) {
            string.append(" The door is also trapped. The trap set on it is: ").append(trap.getDescription());
        } else {
            string.append(" The door is not trapped");
        }
    }

    /**
     * Used to check that a door is connected to a passage or a chamber,
     * if it is connected to a chamber will print its description as well.
     * @param passage if true checks that door is connected to a passage if false checks if it is connected to a chamber.
     * @return Returns a true if the desired space to check for was found connected to the door.
     */
    public boolean printIfConnected(boolean passage) { //Will be used to check if a door is connected to a passage or a chamber

        if (spaces.size() > 0) {
            if (passage) {
                if (spaces.get(printIndex) instanceof Passage) { //Check for connected to Passage
                    return true;
                } else if (spaces.get(printIndex + 1) instanceof Passage) {
                    return true;
                }
            } else {
                if (spaces.get(printIndex) instanceof Chamber) { //Check for connected to chamber
                    System.out.println(((Chamber) spaces.get(0)).getDescription());
                    return true;
                } else if (spaces.get(printIndex + 1) instanceof Chamber) {
                    System.out.println(((Chamber) spaces.get(0)).getDescription());
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns true if the door is locked or false if unlocked.
     * @return Returns true if locked, false if unlocked
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Sets the locked instance boolean to flag.
     * @param flag Sets locked instance boolean to flag
     */
    public void setLocked(boolean flag) {

        if (archway) {
            locked = false;
            open = true;
        } else {
            if (flag) {
                open = false;
            }
            locked = flag;
        }

    }

    /**
     * Used to increment the print index of the door by 2.
     * The print index is used when creating a formatted string of the level,
     * it is used to select specific connections in that door and add them to the
     * level description (this method should only be used for formatting purposes).
     * The print index should NEVER be incremented beyond size of spaces array list.
     */
    public void incrementPrintIndex() {
            printIndex += 2;
    }

    /**
     * Resets the print index of the door back to 0.
     */
    public void resetPrintIndex() {
        printIndex = 0;
    }

    /**
     * Returns the current print index of the door.
     * @return the current print index of the door.
     */
    public int getPrintIndex() {
        return printIndex;
    }
}
