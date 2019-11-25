package level;

import dnd.models.Monster;
import java.util.ArrayList;

public class Passage extends Space {
    /**
     * Array list of passage sections that make up Passage.
     */
    private ArrayList<PassageSection> thePassage = new ArrayList<>();

    /**
     * Array list of doors in Passage.
     */
    private ArrayList<Door> doors = new ArrayList<Door>();

    /**
     * Index of passage.
     */
    private int passageNum;

    /**
     * Creates a passage based on boolean.
     * @param basic if true will make a simple passage that is two sections long
     *  (specification for assignment 3), otherwise does not populate passage.
     */
    public Passage(boolean basic) {

        if (basic) {
            PassageSection pass1 = new PassageSection("passage goes straight for 10 ft");
            PassageSection pass2 = new PassageSection("passage ends in Door to a Chamber");
            addPassageSection(pass1);
            addPassageSection(pass2);
        }

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
        if (thePassage.size() <= i) {
            return null;
        }

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

        formatBeginDoor(string);
        string.append("The passage contains ");
        string.append(thePassage.size());
        string.append(" Sections\n");
        for (int i = 0; i < thePassage.size(); i++) {
            j = i + 1;
            string.append("Section ");
            string.append(j).append(": ");
            formatSectionContents(string, i);
        }
        return string.toString();
    }

    /**
     * Format a description of the beginning door connection and append it to string.
     * @param string StringBuilder to build description of passage.
     */
    private void formatBeginDoor(StringBuilder string) {
        string.append("Passage ").append(passageNum).append("\n");
        if (doors.size() > 0) {
            string.append("The passage begins with a door, ").append(doors.get(0).getDescription());
            string.append("\n");
        }
    }

    /**
     * Format a description of the sections and their contents and append it to string.
     * @param string StringBuilder to build description of passage.
     * @param i current section.
     */
    private void formatSectionContents(StringBuilder string, int i) {
        string.append(thePassage.get(i).getDescription());
        if (thePassage.get(i).getMonster() != null) {
            formatMonster(string, i);
        } else if (thePassage.get(i).getDescription().contains("stairs")) {
            string.append("\nThe stairs can be described as: ").append(thePassage.get(i).getStairs().getDescription()).append("\n");
        } else if (thePassage.get(i).getDescription().contains("door") && !(thePassage.get(i).getDescription().contains("Chamber"))) {
            formatJustDoor(string, i);
        } else if (thePassage.get(i).getDescription().contains("Chamber")) {
            formatChamber(string);
        } else {
            string.append("\n");
        }
    }

    /**
     * Format monster description of current section and append it to string.
     * @param string StringBuilder to build description of passage.
     * @param i current section.
     */
    private void formatMonster(StringBuilder string, int i) {
        string.append("\nThere is a minimum of ").append(thePassage.get(i).getMonster().getMinNum());
        string.append(" and a maximum of ").append(thePassage.get(i).getMonster().getMaxNum());
        string.append(" ").append(thePassage.get(i).getMonster().getDescription()).append("\n");
    }

    /**
     * Format ending connection of passage (if it connects to a chamber) and append it string.
     * @param string StringBuilder to build description of passage.
     */
    private void formatChamber(StringBuilder string) {
        string.append("\n").append(doors.get(doors.size() - 1).getDescription()); //Access last door in Passage
        string.append("\n");
    }

    /**
     * Format door description in current section and append it to string.
     * @param string StringBuilder to build description of passage.
     * @param i current section.
     */
    private void formatJustDoor(StringBuilder string, int i) {
        string.append("\n").append(thePassage.get(i).getDoor().getDescription());
        string.append("\n");
    }

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
