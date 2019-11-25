package main;

import java.util.ArrayList;

final class Main {
    private Main() {
        //Never called
    }

    /**
     * main method of program, is used to generate a dungeon level
     * with 5 chambers and print out the resulting level.
     * @param args an array of arguments when the program is run
     * (this program requires no arguments to be inputted).
     */
    public static void  main(final String[] args) {
        ArrayList<Chamber> chambers = new ArrayList<>();
        ArrayList<Passage> passages = new ArrayList<>();
        boolean toBacktrack = false;
        boolean firstPass = true;
        boolean noChamberExits = true;
        boolean completeRefresh = false;
        String endPass = "passage ends in Door to a Chamber";
        PassageSection passSect;
        Chamber c;
        Door door;
        int currChamber = -1;
        int currSection;
        int currPassage = -1;
        do {

            Passage passage1 = new Passage();
            passage1.setPassageNum(currPassage + 2);
            currSection = 0;

            if (firstPass) {
                completeRefresh = false;
                door = new Door(true);  //Create initial door
                passage1.setDoor(door); //Add door to passage
            } else {

                if (chambers.size() > 0) {

                    int i = currChamber;
                    do {
                        if (chambers.get(i).getNumUnlinked() > 0) {

                            //Get an next unconnected door in chamber
                            door = chambers.get(i).getUnlinkedDoors().get(0);
                            //Link chamber and door and passage
                            door.setSpaces(chambers.get(i), passage1);
                            toBacktrack = false;
                        } else {
                            toBacktrack = true;
                            //Did not find any exits in this chamber
                        }
                        i--;
                    } while (i >= 0 && toBacktrack);

                    noChamberExits = toBacktrack;
                }

             completeRefresh = noChamberExits;
            }

            if (completeRefresh) {
                currPassage = -1;
                currChamber = -1;
                chambers.clear();
                passages.clear();
                firstPass = true;
                continue;
            }

            do {

                if (currSection == 9) {
                    passSect = new PassageSection(endPass);
                } else {
                    passSect = new PassageSection();
                }
                passage1.addPassageSection(passSect);

                if (isDeadEnd(passSect)) {
                    break;
                } else if (toMakeChamber(passSect)) { //Make chamber

                    //Generate an archway to chamber
                    if (passSect.getDescription().contains("archway")) {
                        c = new Chamber();
                        c.setChamberNum(currChamber + 2);
                        door = new Door(true, c.getChamberExits().get(0));
                        door.setSpaces(c, passage1);
                        chambers.add(c);
                    } else { //Generate a normal door to chamber
                        c = new Chamber();
                        c.setChamberNum(currChamber + 2);
                        door = new Door(c.getChamberExits().get(0));
                        door.setSpaces(c, passage1);
                        chambers.add(c);
                    }

                    currChamber++;
                    break;
                } else if (toMakeDoor(passSect)) {
                    door = new Door();
                    passSect.addDoor(door);
                    passage1.setDoor(door);
                }
                currSection++;
            } while (currSection < 10);

            passages.add(passage1);

            if (firstPass && isDeadEnd(passSect)) {
                passages.clear();
                currPassage--;
                firstPass = true;
            } else {
                firstPass = false;
            }


            currPassage++;


        } while (chambers.size() < 5);

        int last;
        System.out.println("\n\n*************Level of chamber description**************\n\n");

        for (int i = 0; i < passages.size(); i++) {
            System.out.println(passages.get(i).getDescription());
            last = passages.get(i).getThePassage().size() - 1;
            //If the final passage section in passage (i)
            //has word chamber in its description, we need to print that chamber
            if (passages.get(i).getThePassage().get(last)
                    .getDescription().contains("Chamber")) {
                System.out.println("--------- Chamber connected to passage ----------");

                last = passages.get(i).getDoors().size() - 1;
                //Retrieve chamber connected to last door and print it
                if (passages.get(i).getDoor(last).getSpace(false)) {
                    System.out.println("\n--------------------------------------\n");
                }
            }
        }


    }

    /**
     * Checks to see if the algorithm has reached
     * dead end based on the inputted passage Section.
     * @param currPassageSect the current passage section within the passage.
     * @return Returns true if the current passage
     * section is a dead end, else returns false.
     */
    public static boolean isDeadEnd(final PassageSection currPassageSect) {

        if (currPassageSect.getDescription().contains("Dead")) {
            return true;
        }
        return false;
    }

    /**
     * Checks to see if the algorithm requires a chamber to be created
     * (if a passage section created leads to a chamber).
     * @param currPassageSect the current passage section within the passage.
     * @return Returns true if a chamber should be created, else returns false.
     */
    public static boolean toMakeChamber(final PassageSection currPassageSect) {

        if (currPassageSect.getDescription().contains("Chamber")) {
            return true;
        }
        return false;
    }

    /**
     * Checks to see if the algorithm requires a door to
     * be created for the current passage section.
     * @param currPassageSect the current passage section within the passage.
     * @return Returns true if a door is required for
     * the current passage section.
     */
    public static boolean toMakeDoor(final PassageSection currPassageSect) {

        if (currPassageSect.getDescription().contains("door")) {
            return true;
        }
        return false;
    }

}
