package main;

import dnd.models.Monster;
import java.util.ArrayList;

public class Passage extends Space {
    /**
     * Array list of passage sections that make up Passage.
     */
    private ArrayList<PassageSection> thePassage = new ArrayList<PassageSection>();

    /**
     * Array list of doors in Passage.
     */
    private ArrayList<Door> doors = new ArrayList<Door>();

    /**
     * Index of passage.
     */
    private int passageNum;

    /**
     * Default constructor for passage object.
     */
    public Passage() {

    }

    /**
     * get doors in the passage.
     * @return array list of doors.
     */
    public ArrayList<Door> getDoors() {
        //gets all of the doors in the entire passage
        return doors;
    }

    /**
     * get a door in array list of doors at a specific index.
     * @param i index to retrieve door from.
     * @return null if i is out of bounds, otherwise the door at index i.
     */
    public Door getDoor(int i) {
        //returns the door in section 'i'. If there is no door, returns null
        if (doors.size() <= i) {
            return null;
        }

        if (doors.get(i) == null) {
            return null;
        } else {
            return doors.get(i);
        }
    }

    /**
     * add a monster to a passage section in passage.
     * @param theMonster to be added.
     * @param i index of passage section monster is to be added to.
     */
    public void addMonster(Monster theMonster, int i) {
        // adds a monster to section 'i' of the passage
        thePassage.get(i).setMonster(theMonster);

    }

    /**
     * retrieve the monster at passage section of index i.
     * @param i index of monster.
     * @return if i is out of bounds returns null, otherwise returns the monster at passage section i.
     */
    public Monster getMonster(int i) {
        //returns Monster door in section 'i'. If there is no Monster, returns null
        if (thePassage.get(i).getMonster() == null) {
            return null;
        } else {
            return thePassage.get(i).getMonster();
        }
    }

    /**
     * add a passage section to the passage.
     * @param toAdd section to be added.
     */
    public void addPassageSection(PassageSection toAdd) {
        //adds the passage section to the passageway
        thePassage.add(toAdd);
    }

    /**
     * connects a door to the passage.
     * @param newDoor door to be connected.
     */
    @Override
    public void setDoor(Door newDoor) {
        newDoor.getSpaces().add(this);
        doors.add(newDoor);
    }

    /**
     * returns a description of the passage.
     * @return description of the passage.
     */
    @Override
    public String getDescription() {

        StringBuilder string = new StringBuilder();
        int j;

        string.append("Passage ").append(passageNum).append("\n");
        if (doors.size() > 0) {
            string.append("The passage begins with a door, ").append(doors.get(0).getDescription());
            string.append("\n");
        }
        string.append("The passage contains ");
        string.append(thePassage.size());
        string.append(" Sections\n");
        for (int i = 0; i < thePassage.size(); i++) {
            j = i + 1;
            string.append("Section ");
            string.append(j).append(": ");
            string.append(thePassage.get(i).getDescription());
            if (thePassage.get(i).getMonster() != null) {
                string.append("\nThere is a minimum of ").append(thePassage.get(i).getMonster().getMinNum());
                string.append(" and a maximum of ").append(thePassage.get(i).getMonster().getMaxNum());
                string.append(" ").append(thePassage.get(i).getMonster().getDescription()).append("\n");
            } else if (thePassage.get(i).getDescription().contains("stairs")) {
                string.append("\nThe stairs can be described as: ").append(thePassage.get(i).getStairs().getDescription()).append("\n");
            } else if (thePassage.get(i).getDescription().contains("door") && !(thePassage.get(i).getDescription().contains("Chamber"))) {
                string.append("\n").append(thePassage.get(i).getDoor().getDescription());
                string.append("\n");
            } else if (thePassage.get(i).getDescription().contains("Chamber")) {
                string.append("\n").append(doors.get(doors.size() - 1).getDescription()); //Access last door in Passage
                string.append("\n");
            } else {
                string.append("\n");
            }


        }

        return string.toString();
    }

    //My Methods

    /**
     * sets index of passage.
     * @param i index of passage.
     */
    public void setPassageNum(int i) {
        passageNum = i;
    }

    /**
     * gets index of passage.
     * @return the index of passage.
     */
    public int getPassageNum() {
        return passageNum;
    }

    /**
     * gets the array list of passage sections in passage.
     * @return array list of passage sections.
     */
    public ArrayList<PassageSection> getThePassage() {
        return thePassage;
    }
}
