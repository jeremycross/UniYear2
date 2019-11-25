package main;

import dnd.exceptions.NotProtectedException;
import dnd.exceptions.UnusualShapeException;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.models.Treasure;
import dnd.models.Monster;
import dnd.models.Exit;

import java.util.ArrayList;

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
     * all doors in chamber.
     */
    private ArrayList<Door> allDoors = new ArrayList<Door>();

    /**
     * all doors that are linked to a passage.
     */
    private ArrayList<Door> linkedDoors = new ArrayList<>();

    /**
     * all doors that are not linked to a passage.
     */
    private ArrayList<Door> unlinkedDoors = new ArrayList<Door>();

    /**
     * Randomly generates a chamber object.
     */
    public Chamber() {
        //SIZE AND SHAPE
        mySize = new ChamberShape();
        setShape(mySize);

        //CONTENTS GENERATION
        myContents = new ChamberContents();
        myContents.setDescription();
        if (myContents.getDescription().contains("monster")) { //If there is a monster
            Monster monster = new Monster();
            monster.setType();
            addMonster(monster);
        }
        if (myContents.getDescription().contains("treasure")) { //If there is a treasure
            Treasure treasure = new Treasure();
            setTreasure(treasure);
            addTreasure(treasure);
        }
        if (myContents.getDescription().contains("stairs")) { //If there is stairs
            stairs = new Stairs();
            stairs.setType();
        }
        if (myContents.getDescription().contains("trap")) { //If there is a trap
            trap = new Trap();
            trap.setDescription();
        }

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

        if (myContents.getDescription().contains("monster")) { //If there is a monster
            Monster monster = new Monster();
            monster.setType();
            addMonster(monster);
        }
        if (myContents.getDescription().contains("treasure")) { //If there is a treasure
            Treasure treasure = new Treasure();
            setTreasure(treasure);
            addTreasure(treasure);
        }
        if (myContents.getDescription().contains("stairs")) { //If there is stairs
            stairs = new Stairs();
            stairs.setType();
        }
        if (myContents.getDescription().contains("trap")) { //If there is a trap
            trap = new Trap();
            trap.setDescription();
        }

        setUpExits();
    }

    /**
     * Randomly sets attributes of theShape.
     * @param theShape ChamberShape object to be altered.
     */
    public void setShape(ChamberShape theShape) {
        theShape.setShape();


        if (theShape.getNumExits() == 0) {
            theShape.setNumExits(1);
        }

        if (allDoors.size() > 0) {
            allDoors.clear();
            ArrayList<Exit> chamberExits;
            chamberExits = theShape.getExits();


            for (int i = 0; i < chamberExits.size(); i++) {
                Door door = new Door(chamberExits.get(i));
                allDoors.add(door);
            }
        }


    }

    /**
     * used to retrieve all doors in the chamber.
     * @return returns array list of all doors.
     */
    public ArrayList<Door> getDoors() {
        return allDoors;
    }

    /**
     * adds monster to chamber.
     * @param theMonster monster to be added.
     */
    public void addMonster(Monster theMonster) {
        monsters.add(theMonster);
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
     * used to get array list of treasures in chamber.
     * @return array list of treasures.
     */
    public ArrayList<Treasure> getTreasureList() {
        return treasures;
    }

    /**
     * Gets the description of the chamber.
     * @return the description of the chamber.
     */
    @Override
    public String getDescription() {
        StringBuilder string = new StringBuilder();

        string.append("Chamber ").append(chamberNum).append("\n");

        try {
            string.append("Chamber is ").append(mySize.getShape()).append(", of dimensions ");
            string.append(mySize.getLength()).append(" X ").append(mySize.getWidth());
            string.append(", with an area of ").append(mySize.getArea());
            string.append("\n");
        } catch (UnusualShapeException s) {
            string.append("Chamber is ").append(mySize.getShape());
            string.append(" with an area of ").append(mySize.getArea());
            string.append("\n");
        }

        //Print description of chamber exits
        string.append("The chamber has ").append(mySize.getNumExits()).append(" exits (doorways): ");

        if (linkedDoors.size() > 0) {

            for (int i = 0; i < linkedDoors.size(); i++) {

                //Adds the description of the door
                if (i == (linkedDoors.size() - 1)) {
                    string.append("one (linked) ").append(linkedDoors.get(i).getDescription()).append(".\n");


                } else {
                    string.append("one (linked) ").append(linkedDoors.get(i).getDescription()).append(",\n");

                }
            }

            for (int i = 0; i < unlinkedDoors.size(); i++) {
                //Adds the description of the door
                if (i == (unlinkedDoors.size() - 1)) {
                    string.append("one (unlinked) ").append(unlinkedDoors.get(i).getDescription());
                } else {
                    string.append("one (unlinked) ").append(unlinkedDoors.get(i).getDescription()).append(",\n");
                }
            }

        } else {

            string.append("Adding exits");
            for (int i = 0; i < mySize.getNumExits(); i++) {
                string.append("one located at the ").append(mySize.getExits().get(i).getLocation());
                string.append(" "); //Adds the location of exit to the description of chamber
                if (i == (mySize.getNumExits() - 1)) {
                    string.append(mySize.getExits().get(i).getDirection()); //Prints the direction of the exit
                } else {
                    string.append(mySize.getExits().get(i).getDirection()).append(", ");
                }
            }
        }
        string.append("\n");

        if (myContents.getDescription().contains("empty")) { //Empty chamber
            string.append("The chamber is empty");
        } else if (myContents.getDescription().contains("monster") && !(myContents.getDescription().contains("treasure"))) { //Monster only
            string.append("The chamber contains ").append(myContents.getDescription()).append("\n");

            for (int i = 0; i < monsters.size(); i++) {
                string.append("There is a minimum of ").append(monsters.get(i).getMinNum());
                string.append(" and a maximum of ").append(monsters.get(i).getMaxNum());
                string.append(" ").append(monsters.get(i).getDescription()).append("\n");
            }

        } else if (myContents.getDescription().contains("monster") && myContents.getDescription().contains("treasure")) { //Monster and treasure
            string.append("The chamber contains ").append(myContents.getDescription()).append("\n");

            for (int i = 0; i < monsters.size(); i++) {
                string.append("There is a minimum of ").append(monsters.get(i).getMinNum());
                string.append(" and a maximum of ").append(monsters.get(i).getMaxNum());
                string.append(" ").append(monsters.get(i).getDescription()).append("\n");
            }

            string.append("The treasure is: ");
            for (int i = 0; i < treasures.size(); i++) {
                if (treasures.get(i).getContainer().contains("Loose")) {
                    string.append(treasures.get(i).getDescription()).append(" which is ");
                    string.append(treasures.get(i).getContainer()); //Fix grammar if treasure is loose
                } else {
                    string.append(treasures.get(i).getDescription()).append(" which is contained within ");
                    string.append(treasures.get(i).getContainer());
                }

                try {
                    string.append(" and is protected by ");
                    string.append(treasures.get(i).getProtection()).append("\n");
                } catch (NotProtectedException e) {
                    string.append(" and is unprotected\n");
                }
            }
        } else if (myContents.getDescription().contains("treasure")) { //Only treasure
            string.append("The chamber contains ").append(myContents.getDescription()).append("\n");

            string.append("The treasure is: ");
            for (int i = 0; i < treasures.size(); i++) {
                if (treasures.get(i).getContainer().contains("Loose")) {
                    string.append(treasures.get(i).getDescription()).append(" which is ");
                    string.append(treasures.get(i).getContainer()); //Fix grammar if treasure is loose
                } else {
                    string.append(treasures.get(i).getDescription()).append(" which is contained within ");
                    string.append(treasures.get(i).getContainer());
                }

                try {
                    string.append(" and is protected by ");
                    string.append(treasures.get(i).getProtection()).append("\n");
                } catch (NotProtectedException e) {
                    string.append(" and is unprotected\n");
                }
            }
        } else if (myContents.getDescription().contains("stairs")) { //Stairs
            string.append("The chamber contains ").append(myContents.getDescription()).append("\n");
            string.append("The stairs can be described as: ").append(stairs.getDescription());
        } else { //Trap
            string.append("The chamber contains ").append(myContents.getDescription()).append("\n");
            string.append("The trap is: ").append(trap.getDescription());
        }

        description = string.toString(); //Set description to string builder

        return description;
    }

    /**
     * conencts door to the chamber.
     * @param newDoor door to be connected.
     */
    @Override
    public void setDoor(Door newDoor) {
        //should add a door connection to this room
        newDoor.getSpaces().add(this);

        addDoor(newDoor, true);

    }

    //My Methods

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
     * Sets treasure randomly.
     * @param treasure treasure to be randomized.
     */
    public void setTreasure(Treasure treasure) {
        treasure.setDescription();
        treasure.setContainer();
    }

    /**
     * used to get array list of chamber exits.
     * @return array list of exits.
     */
    public ArrayList<Exit> getChamberExits() {
        return mySize.getExits();
    }

    /**
     * num of doors not connected to a passage.
     * @return number of doors not connected to a passage.
     */
    public int getNumUnlinked() {
        return unlinkedDoors.size();
    }

    /**
     * used to get doors that are not connected to a passage.
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
            allDoors.add(door);
        } else {
            unlinkedDoors.add(door);
            allDoors.add(door);
        }

    }


    /**
     * used to populate unlinked doors array list.
     */
    public void setUpExits() {
        ArrayList<Exit> chamberExits;
        chamberExits = getChamberExits();
        //Create an exit for all doors other than entrance
        for (int i = 0; i < chamberExits.size(); i++) {
            Door door = new Door(chamberExits.get(i));
            addDoor(door, false);
        }
    }

}
