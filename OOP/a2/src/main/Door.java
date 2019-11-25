package main;
import dnd.die.D20;
import dnd.die.D6;
import dnd.die.Die;
import dnd.models.Exit;
import dnd.models.Trap;
import java.util.ArrayList;
import java.util.Random;

public class Door {

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
     * direction of the exit it is tied to.
     */
    private String direction;

    /**
     * location of the exit it is tied to.
     */
    private String location;

    /**
     * description of the door.
     */
    private String description;

    /**
     * spaces that the door connects.
     */
    private ArrayList<Space> spaces = new ArrayList<Space>();

    /**
     * Basic Constructor for the Door, will create the
     * a door object with random attributes.
     */
    public Door() {
        //needs to set defaults for door constructed


        int roll = rand.nextInt(10) + 1; //Used a random number to decide what type of door to make

        if (roll == 10) {
            setArchway(true);
            setTrapped(false);
            setOpen(true);
            setLocked(false);
        } else {
            roll = d20.roll();
            //Set a trap or to not set a trap, that is the question
            if (roll == 20) {
                setTrapped(true);
            } else {
                setTrapped(false);
            }

            roll = d6.roll();

            if (roll == 6) {
                setLocked(true);
                setOpen(false);
            } else {
                setLocked(false);

                roll = d6.roll();

                if (roll >= 0 && roll <= 3) {
                    setOpen(false);
                } else {
                    setOpen(true);
                }
            }
        } //End of boolean setting

        //Set descriptive strings
    }

    /**
     * Constructor for a door that is used to create a door with
     * random attributes that will be connected to a chamber exit.
     * @param theExit an Exit object that the Door will be connected to
     */
    public Door(Exit theExit) {
        //sets up the door based on the Exit from the tables

        int roll = rand.nextInt(10) + 1;

        if (roll == 10) {
            setArchway(true);
            setTrapped(false);
            setOpen(true);
            setLocked(false);
        } else {
            setArchway(false);
            roll = d20.roll();
            //Set a trap or to not set a trap, that is the question
            if (roll == 20) {
                setTrapped(true);
            } else {
                setTrapped(false);
            }

            roll = d6.roll();

            if (roll == 6) {
                setLocked(true);
                setOpen(false);
            } else {
                setLocked(false);

                roll = d6.roll();

                if (roll >= 0 && roll <= 3) {
                    setOpen(false);
                } else {
                    setOpen(true);
                }
            }
        } //End of boolean setting

        //Set descriptive strings, appends Description of exit that door is attached to the door
        direction = theExit.getDirection();
        location = theExit.getLocation();
    }

    /**
     * Constructor for a door that is used to create a door with random
     * attributes that will be connected to a chamber exit
     * Used to specifically create an archway for the Exit
     * or to generate a door that is not an archway.
     *
     * @param arch    A boolean to determine if an archway is to be created (true to make an archway)
     * @param theExit The Exit that will be connected to the Door
     */
    public Door(boolean arch, Exit theExit) { //Constructor used when a passage section with a arch is created (that leads to chamber), we need to add a Door to that section
        if (arch) {
            setArchway(true);
            setTrapped(false);
            setOpen(true);
            setLocked(false);
        } else {
            int roll = d20.roll();
            //Set a trap or to not set a trap, that is the question
            if (roll == 20) {
                setTrapped(true);
            } else {
                setTrapped(false);
            }

            roll = d6.roll();

            if (roll == 6) {
                setLocked(true);
                setOpen(false);
            } else {
                setLocked(false);

                roll = d6.roll();

                if (roll >= 0 && roll <= 3) {
                    setOpen(false);
                } else {
                    setOpen(true);
                }
            }
        }

        direction = theExit.getDirection();
        location = theExit.getLocation();
    }

    /**
     * Constructor for a door that is used to create a random that will
     * be connected to a chamber exit used to specifically create
     * an archway for the Exit or to generate a door that is not an archway.
     * @param arch true if the door is to be an archway.
     */
    public Door(boolean arch) { //Constructor used when a passage section with a arch is created, we need to add a Door to that section
        if (arch) {
            setArchway(true);
            setTrapped(false);
            setOpen(true);
            setLocked(false);
        } else {
            int roll = d20.roll();
            //Set a trap or to not set a trap, that is the question
            if (roll == 20) {
                setTrapped(true);
            } else {
                setTrapped(false);
            }

            roll = d6.roll();

            if (roll == 6) {
                setLocked(true);
                setOpen(false);
            } else {
                setLocked(false);

                roll = d6.roll();

                if (roll >= 0 && roll <= 3) {
                    setOpen(false);
                } else {
                    setOpen(true);
                }
            }
        }

    }


    //Required methods

    /**
     * Creates and adds a trap to the door either based on an input value or randomly.
     * @param flag If passed as true will create a trap randomly or passed on input.
     * @param roll An optional input to generate the trap based on the value given (if one is given).
     */
    public void setTrapped(boolean flag, int... roll) {

        int var;

        if (flag) {
            trapped = true;
            if (roll.length == 1) {
                var = roll[0];
            } else {
                var = d20.roll();
            }
            trap = new Trap();
            trap.setDescription(var);
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
     * Returns if the door is trapped or not trapped.
     * @return Returns true if door is trapped otherwise returns false.
     */
    public boolean isTrapped() {
        return trapped;
    }

    /**
     * Returns if the door is open or closed.
     * @return Returns true if door is open otherwise returns false.
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Returns if the door is an archway or not an archway.
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
     * Returns a description of the door.
     * @return Returns a string that describes the door and any traps applied to it.
     */
    public String getDescription() {
        StringBuilder string = new StringBuilder();

        if (spaces.size() > 1) {
            if (spaces.get(0) instanceof Passage && spaces.get(1) instanceof Passage) {
                string.append("Door branches from a passage to another passage. ");
            }
            if (spaces.get(0) instanceof Chamber && spaces.get(1) instanceof Passage) {
                string.append("Door leads into chamber ");
                string.append(((Chamber) spaces.get(0)).getChamberNum());
                string.append(" from passage ");
                string.append(((Passage) spaces.get(1)).getPassageNum());
                string.append(". ");
            }
        }

        if (archway) {
            string.append("doorway is an archway. It is open and does not have a trap.");
        } else {

            if (locked) {
                string.append("door is closed and is locked.");
            } else {
                if (open) {
                    string.append("door is wide open.");
                } else {
                    string.append("door is closed shut, however, it is unlocked.");
                }
            }

            if (trapped) {
                string.append(" The door is also trapped. The trap set on it is: ").append(trap.getDescription());
                string.append(".");
            } else {
                string.append(" The door is not trapped.");
            }
        }

        //Include location and direction in description if associated with an exit of a chamber
        if (location != null) {
            string.append(" The doorway is located at the ");
            string.append(location).append(" ");
            string.append(direction);
            string.append(" (relative to centre of chamber)");
        }

        description = string.toString();

        return description;
    }


    //My Methods

    /**
     * Used to check that a door is connected to a passage or a chamber,
     * if it is connected to a chamber will print its description as well.
     * @param passage if true checks that door is connected to a chamber if false checks if it is connected to a chamber.
     * @return Returns a true if the desired space to check for was found connected to the door.
     */
    public boolean getSpace(boolean passage) { //Will be used to check if a door is connected to a passage or a chamber


        if (spaces.size() > 0) {
            if (passage) {
                if (spaces.get(0) instanceof Passage) { //Check for connected to Passage
                    return true;
                } else if (spaces.get(1) instanceof Passage) {
                    return true;
                }

            } else {
                if (spaces.get(0) instanceof Chamber) { //Check for connected to chamber
                    System.out.println(((Chamber) spaces.get(0)).getDescription());
                    return true;
                } else if (spaces.get(1) instanceof Chamber) {
                    System.out.println(((Chamber) spaces.get(0)).getDescription());
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns if the door is locked or unlocked.
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
        locked = flag;
    }

}
