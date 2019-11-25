package level;

import dnd.exceptions.NotProtectedException;
import dnd.exceptions.UnusualShapeException;
import dnd.models.Monster;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Treasure;
import dnd.models.Trap;
import dnd.models.Stairs;

import java.util.ArrayList;
import java.util.Random;

public class Chamber extends Space {
    /**
     * This is the contents of the chamber.
     */
    private ChamberContents myContents;

    /**
     * Shape of the chamber.
     */
    private ChamberShape mySize;

    /**
     * Index of chamber.
     */
    private int chamberNum;

    /**
     * description of chamber.
     */
    private String description;

    /**
     * trap of in chamber.
     */
    private Trap trap;

    /**
     * stairs in chamber.
     */
    private Stairs stairs;

    /**
     * treasures to be stored in chamber.
     */
    private ArrayList<Treasure> treasures = new ArrayList<>();

    /**
     * monsters in chamber.
     */
    private ArrayList<Monster> monsters = new ArrayList<>();

    /**
     * all doors that are linked to a passage.
     */
    private ArrayList<Door> linkedDoors = new ArrayList<>();

    /**
     * all doors that are not linked to a passage.
     */
    private ArrayList<Door> unlinkedDoors = new ArrayList<Door>();

    /**
     * used for random generation.
     */
    private Random rand = new Random();

    /**
     * Randomly generates a chamber object.
     */
    public Chamber() {
        //SIZE AND SHAPE
        int roll = rand.nextInt(19) + 1;
        mySize =  ChamberShape.selectChamberShape(roll);
        roll = rand.nextInt(20) + 1;
        mySize.setNumExits(roll);

        //CONTENTS GENERATION
        myContents = new ChamberContents();
        roll = rand.nextInt(20) + 1;
        myContents.chooseContents(roll);
        setMyContents();
        setUpExits();
    }

    /**
     * Generates a chamber based on theShape and theContents.
     * @param theShape the shape that the chamber should have.
     * @param theContents the contents that the chamber should have.
     */
    public Chamber(ChamberShape theShape, ChamberContents theContents) {
        mySize = theShape;
        myContents = theContents;

        if (mySize.getNumExits() == 0) {
            mySize.setNumExits(1);
        }

        setMyContents();
        setUpExits();
    }

    /**
     * Generates the appropriate contents for the chamber.
     */
    private void setMyContents() {
        if (myContents.getDescription().contains("monster")) { //If there is a monster
            addAndCreateMonster();
        }
        if (myContents.getDescription().contains("treasure")) { //If there is a treasure
            addAndCreateTreasure();
        }
        if (myContents.getDescription().contains("stairs")) { //If there is stairs
            setRandStairs();
        }
        if (myContents.getDescription().contains("trap")) { //If there is a trap
            setRandTrap();
        }
    }

    /**
     * adds monster to chamber.
     * @param theMonster monster to be added.
     */
    public void addMonster(Monster theMonster) {
        monsters.add(theMonster);
    }

    /**
     * Creates a random monster and adds it to the chamber.
     */
    private void addAndCreateMonster() {
        int roll;
        Monster monster = new Monster();
        roll = rand.nextInt(100) + 1;

        monster.setType(roll);
        addMonster(monster);
    }

    /**
     * used to retrieve monsters in chamber.
     * @return array list of monsters
     */
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    /**
     * Used to add treasure to chamber.
     * @param theTreasure treasure to be added.
     */
    public void addTreasure(Treasure theTreasure) {
        treasures.add(theTreasure); //Adds new treasure to the list
    }

    /**
     * Creates a random treasure and adds it to the chamber.
     */
    private void addAndCreateTreasure() {
        int roll;
        Treasure treasure = new Treasure();
        roll = rand.nextInt(100) + 1;
        treasure.chooseTreasure(roll);
        roll = rand.nextInt(20) + 1;
        treasure.setContainer(roll);
        addTreasure(treasure);
    }

    /**
     * used to get the array list of treasures in the chamber.
     * @return array list of treasures.
     */
    public ArrayList<Treasure> getTreasureList() {
        return treasures;
    }

    /**
     * Creates new random stairs and adds them to the chamber.
     */
    private void setRandStairs() {
        int roll;
        stairs = new Stairs();
        roll = rand.nextInt(20) + 1;
        stairs.setType(roll);
    }

    /**
     * Creates a new random trap and adds it to chamber.
     */
    private void setRandTrap() {
        int roll;
        trap = new Trap();
        roll = rand.nextInt(20) + 1;
        trap.chooseTrap(roll);
    }

    /**
     * Gets the description of the chamber.
     * @return the description of the chamber.
     */
    @Override
    public String getDescription() {
        StringBuilder string = new StringBuilder();
        string.append("Chamber ").append(chamberNum).append("\n");

        getChamberSizeDescription(string);
        getChamberFormattedExitsDescription(string);
        getFormattedContentsDescription(string);

        description = string.toString(); //Set description to string builder

        return description;
    }

    /**
     * Appends a formatted description of chamber size/shape to inputted StringBuilder.
     * @param string StringBuilder to be altered.
     */
    private void getChamberSizeDescription(StringBuilder string) {
        string.append("Chamber is ").append(mySize.getShape());
        try {
            string.append(", of dimensions ").append(mySize.getLength()).append(" X ").append(mySize.getWidth());
            string.append(", with an area of ").append(mySize.getArea());
            string.append("\n");
        } catch (UnusualShapeException s) {
            string.delete(string.lastIndexOf(","), string.length());
            string.append(" with an area of ").append(mySize.getArea());
            string.append("\n");
        }
    }

    /**
     * Appends a formatted description of chamber exits/doors to inputted StringBuilder.
     * @param string StringBuilder to be altered.
     */
    private void getChamberFormattedExitsDescription(StringBuilder string) {
        string.append("The chamber has ").append(mySize.getNumExits()).append(" exits (doorways): \n");
        formatExitsDescription(linkedDoors, string);
        formatExitsDescription(unlinkedDoors, string);
        string.append("\n");
    }

    /**
     * Appends a formatted description of chamber to inputted StringBuilder.
     * @param doors array list of doors in chamber.
     * @param string StringBuilder to be altered
     */
    private void formatExitsDescription(ArrayList<Door> doors, StringBuilder string) {
        for (int i = 0; i < doors.size(); i++) {
            //Adds the description of the door
            if (i == (doors.size() - 1)) {
                string.append("Door ").append(i).append(": ");
                string.append(doors.get(i).getDescription()).append(".\n");
            } else {
                string.append("Door ").append(i).append(": ");
                string.append(doors.get(i).getDescription()).append(",\n");
            }
        }
    }

    /**
     * Appends a formatted description of chamber contents to inputted StringBuilder.
     * @param string StringBuilder to be altered.
     */
    private void getFormattedContentsDescription(StringBuilder string) {
        if (monsters.size() == 0 && treasures.size() == 0) { //Empty chamber
            string.append("The chamber is empty");
        } else {
            string.append("The chamber contains:\n");
            if (monsters.size() > 0) { //Monster only
                string.append("Monster:\n");
                getFormattedMonstersDescription(string);
            }
            if (treasures.size() > 0) { //Monster and treasure
                string.append("Treasure:\n");
                getFormattedTreasuresDescription(string);
            }
        }
    }

    /**
     * Appends a formatted description of chamber contents to inputted StringBuilder.
     * @param string StringBuilder to be altered.
     */
    private void getMoreFormattedContentsDescription(StringBuilder string) {
        if (myContents.getDescription().contains("treasure")) { //Only treasure
            getFormattedTreasuresDescription(string);
        } else if (myContents.getDescription().contains("stairs")) { //Stairs
            string.append("The stairs can be described as: ").append(stairs.getDescription());
        } else { //Trap
            string.append("The trap is: ").append(trap.getDescription());
        }
    }


    /**
     * Appends a formatted description of monsters in chamber to inputted StringBuilder.
     * @param string StringBuilder to be altered.
     */
    private void getFormattedMonstersDescription(StringBuilder string) {
        for (int i = 0; i < monsters.size(); i++) {
            string.append("There is a minimum of ").append(monsters.get(i).getMinNum());
            string.append(" and a maximum of ").append(monsters.get(i).getMaxNum());
            string.append(" ").append(monsters.get(i).getDescription()).append("\n");
        }
    }

    /**
     * Appends a formatted description of treasure in chamber to inputted StringBuilder.
     * @param string StringBuilder to be altered.
     */
    private void getFormattedTreasuresDescription(StringBuilder string) {
        for (int i = 0; i < treasures.size(); i++) {
            if (treasures.get(i).getContainer().contains("Loose")) {
                string.append(treasures.get(i).getDescription()).append(" which is ");
                string.append(treasures.get(i).getContainer()); //Fix grammar if treasure is loose
            } else {
                string.append(treasures.get(i).getDescription()).append(" which is contained within ");
                string.append(treasures.get(i).getContainer());
            }
            getFormattedTreasureProtection(string, i);
        }
    }

    /**
     * Appends a formatted description of treasure's protection in chamber to inputted StringBuilder.
     * @param string StringBuilder to be altered.
     * @param i current index of treasures.
     */
    private void getFormattedTreasureProtection(StringBuilder string, int i) {
        try {
            string.append(" and is protected by ");
            string.append(treasures.get(i).getProtection()).append("\n");
        } catch (NotProtectedException e) {
            string.append(" and is unprotected\n");
        }
    }

    /**
     * connects door to the chamber.
     * @param newDoor door to be connected.
     */
    @Override
    public void setDoor(Door newDoor) {
        //should add a door connection to this room
        newDoor.addSpace(this);
        addDoor(newDoor, true);
    }

    /**
     * connects a door that is already linked to at least one passage to this chamber again.
     * @param door door to be linked again.
     */
    public void setDoorAlreadyLinked(Door door) {
        door.addSpace(this);
    }

    /**
     * set the index of the chamber.
     * @param i index of chamber.
     */
    public void setChamberNum(int i) {
        chamberNum = i;
    }

    /**
     * get the index of the chamber.
     * @return index of chamber.
     */
    public int getChamberNum() {
        return chamberNum;
    }

    /**
     * get the doors that are connected to at least one passage.
     * @return array list of doors connected to at least one passage.
     */
    public ArrayList<Door> getLinkedDoors() {
        return linkedDoors;
    }

    /**
     * num of doors not connected to a passage.
     * @return number of doors not connected to a passage.
     */
    public int getNumUnlinked() {
        return unlinkedDoors.size();
    }

    /**
     * used to get number of doors as exits of the chamber that are not connected to a passage.
     * @return array list of doors not connected to a passage.
     */
    public ArrayList<Door> getUnlinkedDoors() {
        return unlinkedDoors;
    }

    /**
     * adds a door to the a list of linked doors or list of unlinked doors.
     * @param door door to be added to list.
     * @param linked if true will add to linked doors.
     */
    public void addDoor(Door door, boolean linked) {

        if (linked) {
            linkedDoors.add(door);
            unlinkedDoors.remove(0);
        } else {
            unlinkedDoors.add(door);
        }

    }

    /**
     * used to populate unlinked doors array list (upon creation of chamber).
     */
    private void setUpExits() {
        //Create an exit for all doors other than entrance
        for (int i = 0; i < mySize.getNumExits(); i++) {
            Door door = new Door();
            addDoor(door, false);
        }
    }

    /**
     * Returns a string with the format "Chamber 'index'".
     * @return returns a title representation of the chamber.
     */
    public String getTitle() {
        StringBuilder str = new StringBuilder();

        str.append("Chamber ");
        str.append(chamberNum);

        return str.toString();
    }

}
