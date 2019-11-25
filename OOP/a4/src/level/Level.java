package level;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Level implements Serializable {


    private ArrayList<Chamber> chambers = new ArrayList<>();
    private ArrayList<Passage> passages = new ArrayList<>();
    private HashMap<Door, ArrayList<Chamber>> doorConnections = new HashMap<>();
    private int currPassage = 1;

    /**
     * Basic constructor for the Level class.
     */
    public Level() {
    }

    /**
     * Creates and prints a level based on the a3 algorithm.
     */
    public void createLevel() {
        int target;
        createInitialChambers();
        for (int i = 0; i < 5; i++) {
            while (chambers.get(i).getNumUnlinked() > 0) {
                target = chooseTargetChamber(i);
                createNewConnection(i, target);
            }
        }
    }

    /**
     * returns array list of chambers in level.
     * @return array list of chambers.
     */
    public ArrayList<Chamber> getChambers() {
        return chambers;
    }

    /**
     * returns array list of passages in level.
     * @return array list of passages.
     */
    public ArrayList<Passage> getPassages() {
        return passages;
    }

    /**
     * prints a formatted description of the level generated.
     */
    private void printLevel() {
        printPassages();
        System.out.println("-------- Chamber descriptions(includes door mapping for each chamber) -------");
        for (int i = 0; i < chambers.size(); i++) {
            System.out.println("-------------------------------------------------");
            System.out.println(chambers.get(i).getDescription());
            printChamberConnections(i);
        }
        System.out.println("-------------- End of level description --------------");
    }

    /**
     * prints a formatted description for all passages.
     */
    private void printPassages() {
        System.out.println("---------------- Passage descriptions -----------------");
        for (int i = 0; i < passages.size(); i++) {
            System.out.println("*******************************************************");
            System.out.println(passages.get(i).getDescription());
            System.out.println("*******************************************************");
        }
        System.out.println("-------------- End of Passage descriptions -----------\n");
    }

    /**
     * prints a formatted description of a chambers doors connections.
     * Prints each door's connection map that was created with algorithm.
     * @param i current index of chamber.
     */
    private void printChamberConnections(int i) {
        System.out.println("\nDoor connection mapping for this chamber: ");
        for (int j = 0; j < chambers.get(i).getLinkedDoors().size(); j++) {
            ArrayList<Chamber> list = doorConnections.get(chambers.get(i).getLinkedDoors().get(j));
            System.out.print("Door " + (j + 1) + ": ");
            for (int k = 0; k < list.size(); k++) {
                System.out.print("Chamber " + list.get(k).getChamberNum());
                if (k < list.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Creates five random chambers for the algorithm to perform.
     */
    private void createInitialChambers() {
        for (int i = 0; i < 5; i++) {
            createAndAddChamber(i);
        }
    }

    /**
     * Creates a random chamber and adds it to the array list of chambers.
     * @param i current index of chamber being created.
     */
    private void createAndAddChamber(int i) {
        Chamber c = new Chamber();
        c.setChamberNum(i + 1);
        chambers.add(c);
    }

    /**
     * Decides for a given starting chamber what chamber to make the target chamber.
     * (For next connection).
     * @param startChamber the index of the chamber we are branching off of.
     * @return index of the target chamber.
     */
    private int chooseTargetChamber(int startChamber) {
        int index = 0;
        boolean foundEmptyDoor = false;

        for (int i = 0; i < 5; i++) {
            if (i != startChamber) { /*Try to find a chamber with an empty chamber*/
                if (chambers.get(i).getNumUnlinked() > 0) {
                    index = i;
                    foundEmptyDoor = true;
                    break;
                }
            }
        }
        index = chooseNextChamber(index, startChamber, foundEmptyDoor);
        return index;
    }

    /**
     * Checks if no chambers with empty doors are left to be targeted.
     * If no chambers with empty doors are available it
     * alters index to startChamber + 1.
     * @param index index of chamber that may be our target.
     * @param startChamber index of chamber we are starting our connection at.
     * @param foundEmptyDoor true if we found a chamber with empty door (we will not need to alter index).
     * @return returns index of our target chamber.
     */
    private int chooseNextChamber(int index, int startChamber, boolean foundEmptyDoor) {
        if (!foundEmptyDoor) {
            if (startChamber == 4) {
                index = 0;
            } else {
                index = startChamber + 1;
            }
        }
        return index;
    }

    /**
     * Verifies if our target chamber has a door with no connections.
     * @param targetChamber index of target chamber.
     * @return returns true if target chamber has a door with no connections.
     */
    private boolean chooseTargetDoor(int targetChamber) {
        Chamber c = chambers.get(targetChamber);
        if (c.getNumUnlinked() > 0) {
            return true;
        }

        return false;
    }

    /**
     * Establishes a new connection between a starting chamber and a target chamber.
     * @param chamberIndex index of starting chamber for new connection.
     * @param targetChamber index of target chamber for new connection.
     */
    private void createNewConnection(int chamberIndex, int targetChamber) {
        ArrayList<Chamber> startList = new ArrayList<>();

        Door startDoor = chambers.get(chamberIndex).getUnlinkedDoors().get(0); //Get unconnected door
        Passage pass = new Passage(true);
        pass.setPassageNum(currPassage);
        currPassage++;
        startDoor.setSpaces(chambers.get(chamberIndex), pass);
        startList.add(chambers.get(targetChamber));
        doorConnections.put(startDoor, startList);
        endConnection(pass, chamberIndex, targetChamber);
        passages.add(pass);
    }

    /**
     * Finalizes the connection at the end of the new passage (for a door with no connections
     * or with connections).
     * @param pass Passage to be our new connection.
     * @param chamberIndex index of starting chamber.
     * @param targetChamber index of target chamber.
     */
    private void endConnection(Passage pass, int chamberIndex, int targetChamber) {
        Door endDoor;
        /*Set spaces and hash map entry for ending door*/
        boolean unlinked = chooseTargetDoor(targetChamber);
        endDoor = getTargetDoor(targetChamber, unlinked);
        if (unlinked) {
           endConnectionUnlinked(pass, endDoor, chamberIndex, targetChamber);
        } else {
            endConnectionLinked(pass, endDoor, chamberIndex, targetChamber);
        }
    }

    /**
     * Finalizes the connection at end of the new passage if the target door has no connections so far.
     * @param pass Passage to be our new connection.
     * @param endDoor target door.
     * @param chamberIndex index of starting chamber.
     * @param targetChamber index of target chamber.
     */
    private void endConnectionUnlinked(Passage pass, Door endDoor, int chamberIndex, int targetChamber) {
        ArrayList<Chamber> endList;
        endList = new ArrayList<>();
        endDoor.setSpaces(chambers.get(targetChamber), pass);
        endList.add(chambers.get(chamberIndex));
        doorConnections.put(endDoor, endList);
    }

    /**
     * Finalizes the connection at end of the new passage if the target door has at least one connection so far.
     * @param pass Passage to be our new connection.
     * @param endDoor target door.
     * @param chamberIndex index of starting chamber.
     * @param targetChamber index of target chamber.
     */
    private void endConnectionLinked(Passage pass, Door endDoor, int chamberIndex, int targetChamber) {
        ArrayList<Chamber> endList;
        endList = doorConnections.get(endDoor);
        endDoor.setAlreadyLinkedSpaces(chambers.get(targetChamber), pass);
        endList.add(chambers.get(chamberIndex));
        doorConnections.put(endDoor, endList);
    }

    /**
     * Finds a door to be the target door for our new connection in our target chamber.
     * (Prefers doors with least amount of connections).
     * @param targetChamber index of target chamber.
     * @param unlinked if the chamber has a door with no connections this will be true.
     *                 It will then return an unlinked door, otherwise it must search for
     *                 door with least connections.
     * @return target door.
     */
    private Door getTargetDoor(int targetChamber, boolean unlinked) {
        Chamber c = chambers.get(targetChamber);

        if (unlinked) {
            return c.getUnlinkedDoors().get(0);
        } else { /*Find a linked door with least amount of connections*/
            int index = 0;
            int min = doorConnections.get(c.getLinkedDoors().get(0)).size();
            for (int i = 1; i < c.getLinkedDoors().size(); i++) {
                if (doorConnections.get(c.getLinkedDoors().get(i)).size() < min) {
                    min = doorConnections.get(c.getLinkedDoors().get(i)).size();
                    index = i;
                }
            }
            return c.getLinkedDoors().get(index);
        }
    }
}
