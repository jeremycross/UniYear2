package main;

public abstract class Space {

    /**
     * gets the description of the space.
     * @return description of the space.
     */
    public abstract String getDescription();

    /**
     * sets/connects a door to the space.
     * @param theDoor the door to be set.
     */
    public abstract void setDoor(Door theDoor);

}
