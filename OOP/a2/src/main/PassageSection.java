package main;

import dnd.die.D20;
import dnd.die.Die;
import dnd.models.Monster;
import dnd.models.Stairs;

import java.util.HashMap;

/* Represents a 10 ft section of passageway */

public class PassageSection {

    /**
     * door in passage Section.
     */
    private Door door;

    /**
     * monster in passage section.
     */
    private Monster monster;

    /**
     * stairs in passage section.
     */
    private Stairs stairs;

    /**
     * description of passage section.
     */
    private String description;

    /**
     * table of roll values corresponding to description.
     */
    private HashMap<Integer, String> table = new HashMap();

    /**
     * used for random generation.
     */
    private Die d20 = new D20();

    /**
     * Randomly create passage section.
     */
    public PassageSection() {
        int roll = d20.roll();
        setUpTable();
        description = table.get(roll);

        if (description.contains("monster")) {
            monster = new Monster();
            monster.setType();
        } else if (description.contains("stairs")) {
            stairs = new Stairs();
            stairs.setType();
        } else if (description.contains("door") && !(description.contains("Chamber"))) {

            if (description.contains("archway")) {
                door = new Door(true);
            } else {
                door = new Door(false);
            }

        }
    }

    /**
     * create a passage section based on a string.
     * @param des to be description of passage section.
     */
    public PassageSection(String des) {
        //sets up the 10 foot section based on inputted description

        description = des;

        if (description.contains("monster") || description.contains("Monster")) {
            monster = new Monster();
            monster.setType();
        } else if (description.contains("stairs")) {
            stairs = new Stairs();
            stairs.setType();
        } else if (description.contains("door") || description.contains("Door")) {


            if (description.contains("archway")) {
                door = new Door(true);
            } else {
                door = new Door(false);
            }

        }
    }

    /**
     * used to get the door in section.
     * @return the door in section.
     */
    public Door getDoor() {
        //returns the door that is in the passage section, if there is one
        if (door == null) {
            return null;
        } else {
            return door;
        }

    }

    /**
     * get monster in section.
     * @return the monster in section.
     */
    public Monster getMonster() {
        //returns the monster that is in the passage section, if there is one
        if (monster == null) {
            return null;
        } else {
            return monster;
        }

    }

    /**
     * get description of the section.
     * @return description of the section.
     */
    public String getDescription() {
        return description;
    }


    //My Methods

    private void setUpTable() {
        table.put(1, "passage goes straight for 10 ft");
        table.put(2, "passage goes straight for 10 ft");
        table.put(3, "passage ends in Door to a Chamber");
        table.put(4, "passage ends in Door to a Chamber");
        table.put(5, "passage ends in Door to a Chamber");
        table.put(6, "archway (door) to right (main passage continues straight for 10 ft)");
        table.put(7, "archway (door) to right (main passage continues straight for 10 ft)");
        table.put(8, "archway (door) to left (main passage continues straight for 10 ft)");
        table.put(9, "archway (door) to left (main passage continues straight for 10 ft)");
        table.put(10, "passage turns to left and continues for 10ft");
        table.put(11, "passage turns to left and continues for 10ft");
        table.put(12, "passage turns to right and continues for 10ft");
        table.put(13, "passage turns to right and continues for 10ft");
        table.put(14, "passage ends in archway (door) to a Chamber");
        table.put(15, "passage ends in archway (door) to a Chamber");
        table.put(16, "passage ends in archway (door) to a Chamber");
        table.put(17, "Stairs, (passage goes straight for 10 ft)");
        table.put(18, "Dead End");
        table.put(19, "Dead End");
        table.put(20, "Wandering monster (passage continues straight for 10 ft)");
    }

    /**
     * sets the monster of section to the one passed.
     * @param set monster to be set in section.
     */
    public void setMonster(Monster set) {
        monster = set;

    }

    /**
     * used to get stairs in section.
     * @return the stairs in the section.
     */
    public Stairs getStairs() {
        if (stairs == null) {
            return null;
        } else {
            return stairs;
        }
    }

    /**
     * used to add a door to the section.
     * @param theDoor door to be added.
     */
    public void addDoor(Door theDoor) {
        door = theDoor;
    }
}
