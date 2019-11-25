Class  Passage

| Method signature | Responsibility | Instance variables used | Other class methods called | Objects used with method calls | Lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
| public Passage(boolean basic) | Creates a passage based on boolean. | None | addPassageSection | PassageSection.PassageSection() | 4 |
| public ArrayList<Door> getDoors() | get doors in the passage. | ArrayList\<Door> doors | None | None | 1 |
| public Door getDoor(int) | get a door in array list of doors at a specific index. | ArrayList\<Door> doors | None | ArrayList.get(), ArrayList.size() | 6 |
| public void addMonster(Monster, int) | add a monster to a passage section in passage. | ArrayList\<PassageSection> thePassage | None | ArrayList.get(), PassageSection.setMonster() | 1 |
| public Monster getMonster(int) | retrieve the monster at passage section of index i. | ArrayList\<PassageSection> thePassage | None | ArrayList.get(), PassageSection.getMonster() | 6 |
| public void addPassageSection(PassageSection) | add a passage section to the passage. | ArrayList\<PassageSection> thePassage | None | ArrayList.add() | 1 |
| public void setDoor(Door) | connects a door to the passage. | ArrayList\<Door> doors | None | ArrayList.add(), Door.getSpaces() | 2 |
| public String getDescription() | returns a description of the passage. | ArrayList\<PassageSection> thePassage | formatBeginDoor(), formatSectionContents() | StringBuilder.append(), StringBuilder.toString()| 12 |
| private void formatBeginDoor(StringBuilder) | Format a description of the beginning door connection and append it to stringbuilder. | int passageNum, ArrayList\<Door> doors | None | ArrayList.get(), ArrayList.size(), Door.getDescription() | 4 |
| private void formatSectionContents(StringBuilder, int)| Format a description of the sections and their contents and append it to stringbuilder. | ArrayList\<PassageSection> thePassage | formatMonster(), formatJustDoor(), formatChamber() | ArrayList.get(), PassageSection.getDescription(), PassageSection.getMonster(), PassageSection.getStairs(), Stairs.getDescription() | 10 |
| private void formatMonster(StringBuilder, int) | Format monster description of current section and append it to stringbuilder. | ArrayList\<PassageSection> thePassage | None | ArrayList.get(), Monster.getDescription(), Monster.getMin(), Monster.getMax() | 3 |
| private void formatChamber(StringBuilder) | Format ending connection of passage (if it connects to a chamber) and append it string. | ArrayList\<Door> doors | None | ArrayList.get(), ArrayList.size(), Door.getDescription() | 2 |
| private void formatJustDoor(StringBuilder, int) | Format door description in current section and append it to stringbuilder. | ArrayList\<PassageSection> thePassage | None | ArrayList.get(), PassageSection.getDoor(), Door.getDescription() | 2 |
| public void setPassageNum(int) | sets index of passage. | int passageNum | None | None | 1 |
| public int getPassageNum() | gets index of passage. | int passageNum | None | None | 1 |
| public ArrayList\<PassageSection> getThePassage() | gets the array list of passage sections in passage. | None | None | None | 1 |
