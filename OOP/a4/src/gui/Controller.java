package gui;
import dnd.models.Monster;
import dnd.models.Treasure;
import level.Door;
import level.Level;
import level.Chamber;
import level.Passage;

import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Controller {

    /**
     * gui.
     */
    private DungeonGUI myGui;

    /**
     * level generated.
     */
    private Level dungeon = new Level();

    /**
     * HashMap with all monsters and their roll values.
     */
    private static HashMap<String, Integer> monsterMap = new HashMap<>();

    /**
     * HashMap with all treasures and their roll values.
     */
    private static HashMap<String, Integer> treasureMap = new HashMap<>();

    /**
     * Array list monsters used for editing.
     */
    private ArrayList<Monster> monsters = new ArrayList<>();

    /**
     * Array list treasures used for editing.
     */
    private ArrayList<Treasure> treasures = new ArrayList<>();

    /**
     * Addition or removal of a treasures has occurred.
     */
    private boolean alteredTreasures = false;

    /**
     * Addition or removal of monsters.
     */
    private boolean alteredMonsters = false;

    /**
     * constructor for controller which creates a level.
     * @param theGui gui for controller.
     */
    public Controller(DungeonGUI theGui) {
        myGui = theGui;
        dungeon.createLevel();
    }

    /**
     * serializes level to output file that was selected.
     * @param file absolute path of output file.
     */
    public void serialOut(String file) {
        //This code can be found at the following link:
        //https://www.tutorialspoint.com/java/java_serialization.htm
        try {
            FileOutputStream fOutput = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fOutput);
            out.writeObject(dungeon);
            out.close();
            fOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reads in a serialized level from file.
     * @param file absolute path of input file.
     */
    public void serialIn(String file) {
        //This code can be found at the following link:
        //https://www.tutorialspoint.com/java/java_serialization.htm
        try {
            FileInputStream fInput = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fInput);
            dungeon = (Level) in.readObject();
            in.close();
            fInput.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * creates list of string reps for spaces in level.
     * @return list of string reps of spaces in level.
     */
    public ArrayList<String> getNameList() {
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<Chamber> chambers = dungeon.getChambers();
        ArrayList<Passage> passages = dungeon.getPassages();

        for (Chamber c: chambers) {
            nameList.add(c.getTitle());
        }

        for (Passage p: passages) {
            nameList.add(p.getTitle());
        }
        return nameList;
    }

    /**
     * gets list of strings for doors in the current space.
     * @param currentSpace string rep of selected space.
     * @return list of strings of doors in the selected space.
     */
    public ArrayList<String> getDoorList(String currentSpace) {

        ArrayList<Door> doors;
        ArrayList<String> doorStrings = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        int index = Integer.parseInt(currentSpace.replaceAll("[\\D]", "")) - 1;

        if (currentSpace.contains("Passage")) {
            doors = dungeon.getPassages().get(index).getDoors();
        } else {
            doors = dungeon.getChambers().get(index).getLinkedDoors();
        }

        for (int i = 0; i < doors.size(); i++) {
            str.append("Door ");
            str.append(i);
            doorStrings.add(str.toString());
            str.setLength(0);
        }

        return doorStrings;
    }

    /**
     * get description of current space.
     * @param currentSpace string rep of selected space.
     * @return description of selected space.
     */
    public String getSpaceDescription(String currentSpace) {
        //Find index (within array list) of the space passed in
        int index = Integer.parseInt(currentSpace.replaceAll("[\\D]", "")) - 1;

        if (currentSpace.contains("Passage")) {
            return dungeon.getPassages().get(index).getDescription();
        } else {
            return dungeon.getChambers().get(index).getDescription();
        }
    }

    /**
     * gets door description of the one chosen in the combo box on gui.
     * @param currentDoor string of selected door.
     * @param currentSpace string rep of selected space.
     * @return description of selected door.
     */
    public String getDoorDescription(String currentDoor, String currentSpace) {
        int doorIndex = Integer.parseInt(currentDoor.replaceAll("[\\D]", ""));
        int spaceIndex = Integer.parseInt(currentSpace.replaceAll("[\\D]", "")) - 1;

        if (currentSpace.contains("Passage")) {
            return dungeon.getPassages().get(spaceIndex).getDoors().get(doorIndex).getDescription();
        } else {
            return dungeon.getChambers().get(spaceIndex).getLinkedDoors().get(doorIndex).getDescription();
        }
    }

    /**
     * creates a array list of strings based on monsters in selected space.
     * @param currentSpace string rep of currently selected space.
     * @return array list of strings of monsters.
     */
    public ArrayList<String> allSpaceMonsters(String currentSpace) {
        int index = Integer.parseInt(currentSpace.replaceAll("[\\D]", "")) - 1;
        ArrayList<Monster> mons;
        ArrayList<String> descriptions = new ArrayList<>();
        if (currentSpace.contains("Passage")) {
            mons = dungeon.getPassages().get(index).getMonsters();
            for (Monster m: mons) {
                descriptions.add(m.getDescription());
            }
        } else {
            mons = dungeon.getChambers().get(index).getMonsters();
            for (Monster m: mons) {
                descriptions.add(m.getDescription());
            }
        }

        return descriptions;
    }

    /**
     * creates a array list of strings based on treasures in selected space.
     * @param currentSpace string rep of currently selected space.
     * @return array list of strings of treasures.
     */
    public ArrayList<String> allSpaceTreasures(String currentSpace) {
        int index = Integer.parseInt(currentSpace.replaceAll("[\\D]", "")) - 1;
        ArrayList<Treasure> treas;
        ArrayList<String> descriptions = new ArrayList<>();
        if (currentSpace.contains("Passage")) {
            treas = dungeon.getPassages().get(index).getTreasures();
            for (Treasure t: treas) {
                descriptions.add(t.getDescription());
            }
        } else {
            treas = dungeon.getChambers().get(index).getTreasureList();
            for (Treasure t: treas) {
                descriptions.add(t.getDescription());
            }
        }

        return descriptions;
    }

    /**
     * adds a monster to selected chamber.
     * @param monster string rep of monster to be added.
     * @param currentSpace title string of current space.
     */
    public void addMonster(String monster, String currentSpace) {
        int roll = monsterMap.get(monster);
        Monster newMonster = new Monster();
        ArrayList<Monster> temp;
        int spaceIndex = Integer.parseInt(currentSpace.replaceAll("[\\D]", "")) - 1;

        newMonster.setType(roll);

        if (currentSpace.contains("Passage")) {
            temp = dungeon.getPassages().get(spaceIndex).getMonsters();
            if (!alteredMonsters) {
                monsters.addAll(temp);
            }
            monsters.add(newMonster);
        } else {
            temp = dungeon.getChambers().get(spaceIndex).getMonsters();
            if (!alteredMonsters) {
                monsters.addAll(temp);
            }
            monsters.add(newMonster);
        }

        alteredMonsters = true;
    }

    /**
     * save changes to monsters in selected space or ignore changes.
     * @param saveChanges true to save changes.
     * @param currentSpace title string of current space.
     */
    public void finishEditMonster(boolean saveChanges, String currentSpace) {
        if (saveChanges) {
            int spaceIndex = Integer.parseInt(currentSpace.replaceAll("[\\D]", "")) - 1;

            if (currentSpace.contains("Passage")) {
                dungeon.getPassages().get(spaceIndex).getMonsters().clear();
                dungeon.getPassages().get(spaceIndex).getMonsters().addAll(monsters);
            } else {
                dungeon.getChambers().get(spaceIndex).getMonsters().clear();
                dungeon.getChambers().get(spaceIndex).getMonsters().addAll(monsters);
            }
        }
        alteredMonsters = false;
        monsters.clear();
    }

    /**
     * adds a treasure to the selected space.
     * @param treasure string rep of treasure to be added.
     * @param currentSpace title string of current space.
     */
    public void addTreasure(String treasure, String currentSpace) {
        int roll = treasureMap.get(treasure);
        Random rand = new Random();
        Treasure newTreasure = new Treasure();
        ArrayList<Treasure> temp;
        int spaceIndex = Integer.parseInt(currentSpace.replaceAll("[\\D]", "")) - 1;

        newTreasure.chooseTreasure(roll);
        roll = rand.nextInt(20) + 1;
        newTreasure.setContainer(roll);

        if (currentSpace.contains("Passage")) {
            temp = dungeon.getPassages().get(spaceIndex).getTreasures();
            if (!alteredTreasures) {
                treasures.addAll(temp);
            }
            treasures.add(newTreasure);
        } else {
            temp = dungeon.getChambers().get(spaceIndex).getTreasureList();
            if (!alteredTreasures) {
                treasures.addAll(temp);
            }
            treasures.add(newTreasure);
        }

        alteredTreasures = true;
    }

    /**
     * save changes to treasures in selected space or ignore changes.
     * @param saveChanges true to save changes.
     * @param currentSpace title string of current space.
     */
    public void finishEditTreasure(boolean saveChanges, String currentSpace) {
        if (saveChanges) {
            int spaceIndex = Integer.parseInt(currentSpace.replaceAll("[\\D]", "")) - 1;

            if (currentSpace.contains("Passage")) {
                dungeon.getPassages().get(spaceIndex).getTreasures().clear();
                dungeon.getPassages().get(spaceIndex).getTreasures().addAll(treasures);
            } else {
                dungeon.getChambers().get(spaceIndex).getTreasureList().clear();
                dungeon.getChambers().get(spaceIndex).getTreasureList().addAll(treasures);
            }
        }

        alteredTreasures = false;
        treasures.clear();
    }

    /**
     * removes a monster that was selected from the list view in the gui.
     * @param index index of monster to be removed.
     * @param currentSpace title string of current space.
     */
    public void removeMonster(int index, String currentSpace) {
        int spaceIndex = Integer.parseInt(currentSpace.replaceAll("[\\D]", "")) - 1;

        if (currentSpace.contains("Passage")) {
            if (dungeon.getPassages().get(spaceIndex).getMonsters().size() > index && index >= 0) {
                ArrayList<Monster> temp = dungeon.getPassages().get(spaceIndex).getMonsters();
                if (!alteredMonsters) {
                    monsters.addAll(temp);
                }
                monsters.remove(index);
            }
        } else {
            if (dungeon.getChambers().get(spaceIndex).getMonsters().size() > index && index >= 0) {
                ArrayList<Monster> temp = dungeon.getChambers().get(spaceIndex).getMonsters();
                if (!alteredMonsters) {
                    monsters.addAll(temp);
                }
                monsters.remove(index);
            }
        }

        alteredMonsters = true;
    }

    /**
     * removes a treasure that was selected from the list view in the gui.
     * @param index index of treasure to be removed.
     * @param currentSpace title string of current space.
     */
    public void removeTreasure(int index, String currentSpace) {
        int spaceIndex = Integer.parseInt(currentSpace.replaceAll("[\\D]", "")) - 1;

        if (currentSpace.contains("Passage")) {
            if (dungeon.getPassages().get(spaceIndex).getTreasures().size() > index && index >= 0) {
                ArrayList<Treasure> temp = dungeon.getPassages().get(spaceIndex).getTreasures();
                if (!alteredTreasures) {
                    treasures.addAll(temp);
                }
                treasures.remove(index);
            }
        } else {
            if (dungeon.getChambers().get(spaceIndex).getTreasureList().size() > index && index >= 0) {
                ArrayList<Treasure> temp = dungeon.getChambers().get(spaceIndex).getTreasureList();
                if (!alteredTreasures) {
                    treasures.addAll(temp);
                }
                treasures.remove(index);
            }
        }

        alteredTreasures = true;
    }

    /**
     * returns array list with all types of monsters.
     * @return array list of strings of monster types.
     */
    public ArrayList<String> allMonsterTypes() {
        ArrayList<String> allMonsters = new ArrayList<>();

        allMonsters.add("Ant, giant");
        allMonsters.add("Badger");
        allMonsters.add("Beetle, fire");
        allMonsters.add("Demon, manes");
        allMonsters.add("Dwarf");
        allMonsters.add("Ear Seeker");
        allMonsters.add("Elf");
        allMonsters.add("Gnome");
        allMonsters.add("Goblin");
        allMonsters.add("Hafling");
        allMonsters.add("Hobgoblin");
        allMonsters.add("Human Bandit");
        allMonsters.add("Kobold");
        allMonsters.add("Orc");
        allMonsters.add("Piercer");
        allMonsters.add("Rat, giant");
        allMonsters.add("Rot grub");
        allMonsters.add("Shrieker");
        allMonsters.add("Skeleton");
        allMonsters.add("Zombie");

        return allMonsters;
    }

    /**
     * returns array list with all types of treasure.
     * @return array list of strings of treasure types.
     */
    public ArrayList<String> allTreasureTypes() {
        ArrayList<String> allTreasures = new ArrayList<>();

        allTreasures.add("1000 copper pieces/level");
        allTreasures.add("1000 silver pieces/level");
        allTreasures.add("750 electrum pieces/level");
        allTreasures.add("250 gold pieces/level");
        allTreasures.add("100 platinum pieces/level");
        allTreasures.add("1-4 gems/level");
        allTreasures.add("1 piece jewellery/level");
        allTreasures.add("1 magic item (roll on Magic item table");

        return allTreasures;
    }

    static {
        monsterMap.put("Ant, giant", 1);
        monsterMap.put("Badger", 4);
        monsterMap.put("Beetle, fire", 10);
        monsterMap.put("Demon, manes", 15);
        monsterMap.put("Dwarf", 16);
        monsterMap.put("Ear Seeker", 18);
        monsterMap.put("Elf", 19);
        monsterMap.put("Gnome", 20);
        monsterMap.put("Goblin", 22);
        monsterMap.put("Hafling", 28);
        monsterMap.put("Hobgoblin", 33);
        monsterMap.put("Human Bandit", 45);
        monsterMap.put("Kobold", 51);
        monsterMap.put("Orc", 60);
        monsterMap.put("Piercer", 70);
        monsterMap.put("Rat, giant", 80);
        monsterMap.put("Rot grub", 85);
        monsterMap.put("Shrieker", 96);
        monsterMap.put("Skeleton", 98);
        monsterMap.put("Zombie", 99);
        treasureMap.put("1000 copper pieces/level", 1);
        treasureMap.put("1000 silver pieces/level", 26);
        treasureMap.put("750 electrum pieces/level", 64);
        treasureMap.put("250 gold pieces/level", 76);
        treasureMap.put("100 platinum pieces/level", 90);
        treasureMap.put("1-4 gems/level", 93);
        treasureMap.put("1 piece jewellery/level", 97);
        treasureMap.put("1 magic item (roll on Magic item table", 99);
    }
}

