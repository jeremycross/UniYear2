Class  Chamber

| Method signature | Responsibility | Instance variables used | Other class methods called | Objects used with method calls | Lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
| public Chamber() |  Randomly generates a chamber object. |   ChamberShape mySize, ChamberContents myContents | setMyContents(), setUpExits()  |  ChamberShape.selectChamberShape(), ChamberShape.setNumExits(), ChamberContents.ChamberContents(), ChamberContents.chooseContents() | 9 |
| public Chamber(ChamberShape, ChamberContents) | Generates a chamber based on theShape and theContents. |  ChamberShape mySize, ChamberContents myContents| setMyContents(), setUpExits() | ChamberShape.getNumExits(), ChamberShape.setNumExits() | 6 |
| private void setMyContents() | Generates the appropriate contents for the chamber. | ChamberContents myContents | addAndCreateMonster(), addAndCreateTreasure(), setRandStairs(), setRandTrap() | None | 8 |
| public void addMonster(Monster) | Adds monster to chamber. | ArrayList\<Monster> monsters | None | ArrayList.add() | 1 |
| private void addAndCreateMonster()| Creates a random monster and adds it to the chamber. | None | addMonster() | Monster.Monster(), Monster.setType() | 5 |
| public ArrayList\<Monster> getMonsters() | used to retrieve monsters in chamber. | ArrayList\<Monster> monsters | None | None | 1 |
| public void addTreasure(Treasure)| Used to add treasure to chamber. | ArrayList\<Treasure> treasures | None | ArrayList.add() | 1 |
| private void addAndCreateTreasure() | Creates a random treasure and adds it to the chamber. | None | addTreasure() | Treasure.Treasure(), Treasure.chooseTreasure(), Treasure.setContainer | 7 |
| public ArrayList\<Treasure> getTreasureList() | used to get the array list of treasures in the chamber. | ArrayList\<Treasure> treasures | None | None | 1 |
| private void setRandStairs() | Creates new random stairs and adds them to the chamber. | Stairs stairs | None | Stairs.Stairs(), Stairs.setType() | 4 |
| private void setRandTrap() | Creates a new random trap and adds it to chamber. | Trap trap | None | Trap.Trap(), Trap.chooseTrap() | 4 |
| public String getDescription() | Gets the description of the chamber. | String description, int chamberNum | getChamberSizeDescription(), getChamberFormattedExitsDescription(), getFormattedContentsDescription() | StringBuilder.toString() | 7 |
| private void getChamberSizeDescription(StringBuilder)| Appends a formatted description of chamber size/shape to inputted StringBuilder. | ChamberShape mySize | None | ChamberShape.getShape(), ChamberShape.getLength(), ChamberShape.getWidth(), ChamberShape.getArea(), StringBuilder.append(), StringBuilder.delete() | 9 |
| private void getChamberFormattedExitsDescription(StringBuilder)| Appends a formatted description of chamber exits/doors to inputted StringBuilder. | ArrayList\<Door> linkedDoors, ArrayList\<Door> unlinkedDoors, ChamberShape mySize | formatExitsDescription() | ChamberShape.getExits(), StringBuilder.append() | 4 |
| private void formatExitsDescription(ArrayList\<Door>, StringBuilder) | Appends a formatted description of chamber to inputted StringBuilder. | None | None | StringBuilder.append(), ArrayList.size(), ArrayList.get(), Door.getDescription() | 7 |
| private void getFormattedContentsDescription(StringBuilder) | Appends a formatted description of chamber contents to inputted StringBuilder. | ChamberContents myContents | getFormattedMonstersDescription(), getFormattedTreasuresDescription(), getMoreFormattedContentsDescription() | ChamberContents.getDescription(), StringBuilder.append() | 11 |
| private void getMoreFormattedContentsDescription(StringBuilder) | Appends a formatted description of chamber contents to inputted StringBuilder. | ChamberContents myContents | getFormattedTreasureDescription() | StringBuilder.append(), Stairs.getDescription(), Trap.getDescription() | 6 |
| private void getFormattedMonstersDescription(StringBuilder) | Appends a formatted description of monsters in chamber to inputted StringBuilder. | ArrayList\<Monster> monsters | None | StringBuilder.append(), ArrayList.get(), Monster.getMinNum(), Monster.getMaxNum(), Monster.getDescription() | 4 |
| private void getFormattedTreasuresDescription(StringBuilder) | Appends a formatted description of treasure in chamber to inputted StringBuilder. | ArrayList\<Treasure> treasures | getFormattedTreasureProtection() | , Treasure.getDescription(), Treasure.getContainer() | 9 |
| private void getFormattedTreasureProtection(StringBuilder, int) | Appends a formatted description of treasure's protection in chamber to inputted StringBuilder. | ArrayList\<Treasure> treasures | None | Treasure.getProtection(), StringBuilder.append(), ArrayList.get() | 5 |
| public void setDoor(Door) | connects door to the chamber (Through both setting things in chamber and door) | None | addDoor() | Door.addSpace() | 2 |
| public void setDoorAlreadyLinked(Door) | connects a door that is already linked to at least one passage to this chamber again. | None | None | Door.addSpace() | 1 |
| public void setChamberNum(int) | set the index of the chamber. | int chamberNum | None | None | 1 |
| public int getChamberNum() | get the index of the chamber. | int chamberNum | None | None | 1 |
| public ArrayList\<Door> getLinkedDoors() | get the doors that are connected to at least one passage. | ArrayList\<Door> linkedDoors | None | None | 1 |
| public int getNumUnlinked() | used to get number of doors as exits of the chamber that are not connected to a passage. | ArrayList\<Door> unlinkedDoors | None | ArrayList.size() | 1 |
| public void addDoor(Door, boolean) | adds a door to the a list of linked doors or list of unlinked doors. | ArrayList\<Door> unlinkedDoors, ArrayList\<Door> linkedDoors | None | ArrayList.add(), ArrayList.remove() | 5 |
| private void setUpExits() | used to populate unlinked doors array list (upon creation of chamber). | ChamberShape mySize | ChamberShape.getNumExits(), addDoor() | Door.Door() | 3 |
