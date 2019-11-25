package main;

import dnd.die.D20;
import dnd.exceptions.NotProtectedException;
import dnd.exceptions.UnusualShapeException;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Stairs;
import dnd.models.Trap;
import dnd.models.Exit;
import dnd.models.Treasure;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class.
 * Used for generating dnd elements randomly or manually given table rolls from the user
 */
public class Main {

    /**
     * Instance of monster class.
     */
    private Monster monster = new Monster();

    /**
     * Main method for the Main class.
     * @param args array of command line strings. Used when arguments are entered during run command (not used for this program).
     */
    public static void main(String[] args) {
        Main all = new Main();
        Scanner sc = new Scanner(System.in);
        Treasure treasure = new Treasure();
        ChamberContents contents = new ChamberContents();
        D20 d20 = new D20();
        Stairs stairs = new Stairs();
        Trap trap = new Trap();
        String ans;
        int opt;

        do {
            ChamberShape chamber = new ChamberShape();
            //Main menu (a valid number must be entered)
            do {
                System.out.println("\n\nEnter a value from 0-8, using the following options as a to pick the desired action");
                System.out.println("1 = Generate a full chamber randomly");
                System.out.println("2 = Generate a chamber step by step manually");
                System.out.println("3 = Randomly generate the contents of a chamber");
                System.out.println("4 = Select specific contents for the chamber (by providing specific table rolls)");
                System.out.println("5 = Randomly generate the size and shape of chamber");
                System.out.println("6 = Select specific size and shape of a chamber (by providing specific table rolls)");
                System.out.println("7 = Randomly generate a treasure");
                System.out.println("8 = Select a specific treasure (by providing specific table rolls)");
                System.out.println("0 = Exit program");
                ans = sc.next();
                opt = all.checkNum(ans, 0, 8);
            } while (opt < 0);

            //Selection from inputted option
            if (opt == 1) { //Generate full chamber randomly
                System.out.println("\n***RANDOM FULL CHAMBER GENERATION***");
                all.randFullChamber(chamber, contents, treasure, stairs, trap, all);
            } else if (opt == 2) { //Generate chamber step by step
                System.out.println("\n***STEP BY STEP FULL CHAMBER GENERATION***");
                all.manFullChamber(chamber, contents, treasure, stairs, trap, sc, all);
            } else if (opt == 3) { //Set contents randomly
                System.out.println("\n***RANDOM CONTENTS GENERATION***");
                all.randContent(contents, treasure, stairs, trap, all);
                all.printContents(contents, treasure, stairs, trap, all);
            } else if (opt == 4) { //Set contents manually
                System.out.println("\n***MANUAL CONTENTS GENERATION***");
                all.setContentsRoll(contents, sc, all);
                all.manContents(contents, sc, all, stairs, treasure, trap);
                all.printContents(contents, treasure, stairs, trap, all);
            } else if (opt == 5) { //Set size and shape randomly
                System.out.println("\n***RANDOM CHAMBER (SIZE AND SHAPE) GENERATION***");
                all.randShape(chamber);
                all.printShape(chamber);
            } else if (opt == 6) { //Set size and shape manually
                System.out.println("\n***MANUAL CHAMBER (SIZE AND SHAPE) GENERATION***");
                all.manShape(chamber, sc, all);
                all.printShape(chamber);
            } else if (opt == 7) { //Random treasure generation
                System.out.println("\n***RANDOM TREASURE GENERATION***");
                all.randTreasure(treasure);
                all.treasureInfo(treasure);
            } else if (opt == 8) { //Specific treasure generation
                System.out.println("\n***MANUAL TREASURE GENERATION***");
                all.manTreasure(treasure, sc, all);
                all.treasureInfo(treasure);
            }
        } while (opt != 0);
        sc.close();
    } //END OF MAIN METHOD

    /**
     * Main class constructor.
     * Declared to avoid static method uses
     */
    public Main() {

    }

    /**
     * Checks that the user entered a valid integer between min and max (inclusive).
     * @param input a string entered by the user (supposedly a roll value integer)
     * @param min the minimum acceptable roll value
     * @param max the maximum acceptable roll value
     * @return returns the number entered by the user provided it is valid, if invalid returns a negative number
     */
    public int checkNum(String input, int min, int max) {
        int x;

        try {
            x = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1; //Value is not an integer
        }

        if (x >= min && x <= max) { //Value is between min and max (inclusive)
            return x;
        }

        return -2;
    }

    /**
     * Checks that the string is a "yes" or "no".
     * @param input a string entered by the user
     * @return returns false if the string is "yes" or "no" (not capital sensitive), otherwise returns true
     */
    public boolean checkStr(String input) {
        String y = "Yes";
        String n = "No";
        if (input.equalsIgnoreCase(y) || input.equalsIgnoreCase(n)) { //Inputted string is yes or no (not cap sensitive)
            return false;
        }

        return true;
    }

    /**
     * Generates a full chamber randomly and prints a description of the chamber.
     * @param chamber object for Chamber size and shape
     * @param contents object for the Chamber contents
     * @param treasure object for a Treasure
     * @param stairs object for Stairs
     * @param trap object for a Trap
     * @param all object for Main class, used to run instance methods
     */
    private void randFullChamber(ChamberShape chamber, ChamberContents contents, Treasure treasure, Stairs stairs, Trap trap, Main all) {
        //Set values for chamber
        all.randShape(chamber);
        all.randContent(contents, treasure, stairs, trap, all);
        //Print everything
        all.printShape(chamber);
        System.out.println();
        all.printContents(contents, treasure, stairs, trap, all);
    }

    /**
     * Generates a full chamber, a user can choose to enter a roll values for certain generations or not.
     * @param chamber object for Chamber size and shape
     * @param contents object for the Chamber contents
     * @param treasure object for a Treasure
     * @param stairs object for Stairs
     * @param trap object for a Trap
     * @param sc Scanner to take in user input
     * @param all object for Main class, used to run instance methods
     */
    private void manFullChamber(ChamberShape chamber, ChamberContents contents, Treasure treasure, Stairs stairs, Trap trap, Scanner sc, Main all) {
        String ans;

        do {
            System.out.println("Would you like to enter a roll value for the chamber shape (yes or no - case does not matter)");
            ans = sc.next();
        } while (all.checkStr(ans));

        if (ans.equalsIgnoreCase("Yes")) { //Ask them for a roll value for size and shape
            all.manShape(chamber, sc, all);
        } else {
            all.randShape(chamber); //Generate size and shape randomly
        }

        do {
            System.out.println("Would you like to enter a roll value for the chamber contents (yes or no - case does not matter)");
            ans = sc.next();
        } while (all.checkStr(ans));

        if (ans.equalsIgnoreCase("Yes")) { //Ask them for a roll value
            all.setContentsRoll(contents, sc, all);
        } else {
            contents.setDescription(); //Set contents randomly
        }

        all.manContents(contents, sc, all, stairs, treasure, trap); //Ask them to set values to each generated object (if they so please)

        //Print everything
        all.printShape(chamber);
        System.out.println();
        all.printContents(contents, treasure, stairs, trap, all);
    }

    /**
     * Generates contents of a chamber randomly.
     * @param contents object for the Chamber contents
     * @param treasure object for a Treasure
     * @param stairs object for Stairs
     * @param trap object for a Trap
     * @param all object for Main class, used to run instance methods
     */
    public void randContent(ChamberContents contents, Treasure treasure, Stairs stairs, Trap trap, Main all) {
        contents.setDescription();
        if (contents.getDescription().contains("monster")) { //If there is a monster
            monster.setType();
        }
        if (contents.getDescription().contains("treasure")) { //If there is a treasure
            all.randTreasure(treasure);
        }
        if (contents.getDescription().contains("stairs")) { //If there is stairs
            stairs.setType();
        }
        if (contents.getDescription().contains("trap")) { //If there is a trap
            trap.setDescription();
        }
    }

    /**
     * Sets the roll value for contents generation to the inputted roll value.
     * @param contents object for the Chamber contents
     * @param sc Scanner to take in user input
     * @param all object for Main class, used to run instance methods
     */
    public void setContentsRoll(ChamberContents contents, Scanner sc, Main all) {
        int roll;
        String ans;

        System.out.println("Roll value for content description");
        do {
            System.out.println("Please enter a roll between 1 and 20 (inclusive)");
            ans = sc.next();
            roll = all.checkNum(ans, 1,  20);
        } while (roll < 0);
        contents.setDescription(roll);
    }

    /**
     * Sets the contents of a chamber using a roll value from the user. A user can then enter a roll value for any objects generated.
     * @param contents object for the Chamber contents
     * @param sc Scanner to take in user input
     * @param all object for Main class, used to run instance methods
     * @param stairs object for Stairs
     * @param treasure object for a Treasure
     * @param trap object for a Trap
     */
    public void manContents(ChamberContents contents, Scanner sc, Main all, Stairs stairs, Treasure treasure, Trap trap) {
        int roll;
        String ans;
        //This method will run after setContentRoll which sets the contents, now to set each object values
        System.out.println("The following contents have been generated: " + contents.getDescription());

        if (contents.getDescription().contains("monster")) { //Monster in chamber
            do {
                System.out.println("Would you like to enter a roll value for the monster generated (yes or no - case does not matter)");
                ans = sc.next();
            } while (all.checkStr(ans));

            if (ans.equalsIgnoreCase("Yes")) {
                all.manMonster(sc, all);
            } else {
                monster.setType();
            }
        }
        if (contents.getDescription().contains("treasure")) { //Treasure in chamber
            do {
                System.out.println("Would you like to enter a roll value for the treasure generated (yes or no - case does not matter)");
                ans = sc.next();
            } while (all.checkStr(ans));

            if (ans.equalsIgnoreCase("Yes")) {
                all.manTreasure(treasure, sc, all);
            } else {
                all.randTreasure(treasure);
            }
        }
        if (contents.getDescription().contains("stairs")) { //Stairs in chamber
            do {
                System.out.println("Would you like to enter a roll value for the stairs generated (yes or no - case does not matter)");
                ans = sc.next();
            } while (all.checkStr(ans));

            if (ans.equalsIgnoreCase("Yes")) {
                all.manStairs(stairs, sc, all);
            } else {
                stairs.setType();
            }
        }
        if (contents.getDescription().contains("trap")) { //Trap in chamber
            do {
                System.out.println("Would you like to enter a roll value for the trap generated (yes or no - case does not matter)");
                ans = sc.next();
            } while (all.checkStr(ans));

            if (ans.equalsIgnoreCase("Yes")) {
                all.manTrap(trap, sc, all);
            } else {
                trap.setDescription();
            }
        }
    }

    /**
     * Prints all contents in the chamber.
     * @param contents object for the Chamber contents
     * @param treasure object for a Treasure
     * @param stairs object for Stairs
     * @param trap object for a Trap
     * @param all object for Main class, used to run instance methods
     */
    public void printContents(ChamberContents contents, Treasure treasure, Stairs stairs, Trap trap, Main all) {

        if (contents.getDescription().contains("empty")) { //Empty chamber
            System.out.println("The chamber is empty");
        } else if (contents.getDescription().contains("monster") && !(contents.getDescription().contains("treasure"))) { //Monster only
            System.out.println("There is a minimum of " + monster.getMinNum() + " and a maximum of " + monster.getMaxNum() + " " + monster.getDescription());
        } else if (contents.getDescription().contains("monster") && contents.getDescription().contains("treasure")) { //Monster and treasure
            System.out.println("There is a minimum of " + monster.getMinNum() + " and a maximum of " + monster.getMaxNum() + " " + monster.getDescription());
            all.treasureInfo(treasure);
        } else if (contents.getDescription().contains("treasure")) { //Only treasure
            all.treasureInfo(treasure);
        } else if (contents.getDescription().contains("stairs")) { //Stairs
            System.out.print("The stairs can be described as: " + stairs.getDescription());
        } else { //Trap
            System.out.print("The trap is: " + trap.getDescription());
        }
    }

    /**
     * Prompts user for a roll value for the stairs and sets stair type with roll value.
     * @param stairs object for Stairs
     * @param sc Scanner to take in user input
     * @param all object for Main class, used to run instance methods
     */
    public void manStairs(Stairs stairs, Scanner sc, Main all) {

        int roll;
        String ans;

        System.out.println("Roll value for stairs description");
        do {
            System.out.println("Please enter a roll between 1 and 20 (inclusive)");
            ans = sc.next();
            roll = all.checkNum(ans, 1,  20);
        } while (roll < 0);
        stairs.setType(roll);
    }

    /**
     * Prompts user for a roll value for the trap and sets trap with roll value.
     * @param trap object for a Trap
     * @param sc Scanner to take in user input
     * @param all object for Main class, used to run instance methods
     */
    public void manTrap(Trap trap, Scanner sc, Main all) {

        int roll;
        String ans;

        System.out.println("Roll value for trap description");
        do {
            System.out.println("Please enter a roll between 1 and 20 (inclusive)");
            ans = sc.next();
            roll = all.checkNum(ans, 1,  20);
        } while (roll < 0);
        trap.setDescription(roll);
    }

    /**
     * Prompts user for a roll value for the trap and sets trap with roll value.
     * @param sc Scanner to take in user input
     * @param all object for Main class, used to run instance methods
     */
    public void manMonster(Scanner sc, Main all) {

        int roll;
        String ans;

        System.out.println("Monster type roll value");
        do {
            System.out.println("Please enter a roll between 1 and 100 (inclusive)");
            ans = sc.next();
            roll = all.checkNum(ans, 1,  100);
        } while (roll < 0);
        monster.setType(roll);
    }

    /**
     * Prompts user for a roll value for the treasure description and container and sets the treasure object with the roll values.
     * @param treasure object for a Treasure
     * @param sc Scanner to take in user input
     * @param all object for Main class, used to run instance methods
     */
    public void manTreasure(Treasure treasure, Scanner sc, Main all) {

        int roll;
        String ans;

        //Set description
        System.out.println("Roll value for treasure description");
        do {
            System.out.println("Please enter a roll between 1 and 100 (inclusive)");
            ans = sc.next();
            roll = all.checkNum(ans, 1,  100);
        } while (roll < 0);

        treasure.setDescription(roll);

        System.out.println("Roll value for treasure container");
        //Set container
        do {
            System.out.println("Please enter a roll between 1 and 20 (inclusive)");
            ans = sc.next();
            roll = all.checkNum(ans, 1,  20);
        } while (roll < 0);

        treasure.setContainer(roll);

    }

    /**
     * Generates a random treasure and a container for it.
     * @param treasure object for a Treasure
     */
    public void randTreasure(Treasure treasure) {

        treasure.setDescription();
        treasure.setContainer();
    }

    /**
     * Prints the description, container and protection of the treasure object.
     * @param treasure object for a Treasure
     */
    public void treasureInfo(Treasure treasure) {
        System.out.print("Treasure is " + treasure.getDescription());

        if (treasure.getContainer().contains("Loose")) {
            System.out.print(" which is " + treasure.getContainer()); //Fix grammar if treasure is loose
        } else {
            System.out.print(" which is contained within " + treasure.getContainer());
        }

        try {
            System.out.print(" and is protected by " + treasure.getProtection());
        } catch (NotProtectedException e) {
            System.out.print(" and is unprotected");
        }
    }

    /**
     * Prompts user for roll values for shape and number of exits in chamber, sets chamber size and shape and exits using roll values.
     * @param chamber object for Chamber size and shape
     * @param sc Scanner to take in user input
     * @param all object for Main class, used to run instance methods
     */
    public void manShape(ChamberShape chamber, Scanner sc, Main all) {

        int roll;
        String ans;
        System.out.println("Roll value for size and shape");
        do {
            System.out.println("Please enter a roll between 1 and 20 (inclusive)");
            ans = sc.next();
            roll = all.checkNum(ans, 1,  20);
        } while (roll < 0);

        chamber.setShape(roll);

        System.out.println("Roll value for number of exits");

        do {
            System.out.println("Please enter a roll between 1 and 20 (inclusive)");
            ans = sc.next();
            roll = all.checkNum(ans, 1,  20);
        } while (roll < 0);

        chamber.setNumExits(roll);
    }

    /**
     * Set the size and shape as well as number of exits for a chamber randomly.
     * @param chamber object for Chamber size and shape
     */
    public void randShape(ChamberShape chamber) {

        chamber.setShape();
        chamber.setNumExits();
    }

    /**
     * Prints the size and shape and a description of the exits of a chamber.
     * @param chamber object for Chamber size and shape
     */
    public void printShape(ChamberShape chamber) {
        //Check for unusual shape
        try {
            System.out.println("\nChamber created is " + chamber.getShape() + ", of dimensions " + chamber.getLength() + " X " + chamber.getWidth() +  ", with an area of " + chamber.getArea());
        } catch (UnusualShapeException s) {
            System.out.println("\nChamber created is " + chamber.getShape() + " with an area of " + chamber.getArea());
        }

        //Print description of chamber exits
        System.out.print("The chamber has " + chamber.getNumExits() + " exits: ");
        for (int i = 0; i < chamber.getNumExits(); i++) {
            System.out.print("one located at the " + chamber.getExits().get(i).getLocation() + " "); //Prints the location of exit
            if (i == (chamber.getNumExits() - 1)) {
                System.out.print(chamber.getExits().get(i).getDirection()); //Prints the direction of the exit
            } else {
                System.out.print(chamber.getExits().get(i).getDirection() + ", ");
            }
        }
    }
}

