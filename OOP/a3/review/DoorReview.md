Class  Door

| Method signature | Responsibility | Instance variables used | Other class methods called | Objects used with method calls | Lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
| public Door() | Basic Constructor for the Door, will create the door object with random attributes. | None | setRandBooleans() | None | 5 |
| public Door(boolean) | Constructor for a door that is used to create a random that will be connected to a chamber exit used to specifically create an archway for the Exit or to generate a door that is not an archway. | None | setRandBoolean() | None | 1 |
| private void setRandBooleans(boolean) | Used to set the booleans for the door, such as archway and open. randomly. | None | setArchway(), setRandTrap(), setRandLocked() | None | 6 |
| private void setRandTrap() | Decides to set a trap on the door. Sets a trap if the door is to be trapped. | None | setTrapped() | None | 5 |
| private void setRandLocked() | Randomly sets the door to locked or unlocked. | None | setLocked(), setOpen(), setRandOpen() | None | 7 |
| private void setRandOpen() | Randomly sets the door to open or closed (provided not an archway or locked). | None | setOpen() | None | 5 |
| public void setTrapped(boolean, int...) | Creates and adds a trap to the door either based on an input value or randomly. | boolean archway, boolean trapped, Trap trap | None | Trap.Trap(), Trap.chooseTrap() | 11 |
| public void setOpen(boolean) | Sets instance variable of open in door based on boolean passed (unless the door is an archway, then it must be open). | boolean archway, boolean open | None | None | 4 |
| public void setArchway(boolean) | If a true is passed sets all attributes in door to be an archway. | boolean archway | setTrapped(), setOpen(), setLocked() | None | 5 |
| public boolean isTrapped() | Returns true if the door is trapped or false if not trapped. | boolean trapped | None | None | 1 |
| public boolean isOpen() | Returns true if the door is open or false if closed. | boolean open | None | None | 1 |
| public boolean isArchway() | Returns true if the door is an archway or false if not an archway. | boolean archway| None | None | 1 |
| public String getTrapDescription() | Returns a string of trap set on door. | Trap trap | None | Trap.getDescription() | 1 |
| public void addSpace(Space) | Add the inputted space to spaces array list of door. | ArrayList\<Space> spaces | None | ArrayList.add() | 1 |
| public ArrayList\<Space> getSpaces() | Returns am array list of spaces that the door is connected to. | ArrayList\<Space> spaces | None | None | 1 |
| public void setSpaces(Space, Space) | Connects the door to the two spaces that it connects and adds them to the space array list. | None | None | Space.setDoor() | 2 |
| public void setAlreadyLinkedSpaces(Chamber, Passage) | Connects the door to two spaces (used when it has already been connected to two other spaces). | None | None | Chamber.setDoorAlreadyLinked(), Passage.setDoor() | 2 |
| public String getDescription() | Returns a description of the door. | String description | formatConnectionString(), formatDoorDescription() | StringBuilder.toString() | 5 |
| private void formatConnectionString(StringBuilder) | Appends formatted string of door's passage connections to inputted String builder | ArrayList\<Space> spaces, int printIndex | None | Spaces.size() | 13 |
| private void formatDoorDescription(StringBuilder) | Appends a formatted string description of door based on instance booleans. | boolean archway | formatLockedDescription(), formatTrappedDescription() | StringBuilder.append() | 5 |
| private void formatLockedDescription(StringBuilder) | Appends a formatted string description of door based on locked and open instance booleans. | boolean locked, boolean open | None | StringBuilder.append() | 7 |
| private void formatTrappedDescription(StringBuilder) | Appends a formatted string description of door based on trapped instance boolean and trap description. | boolean trapped, Trap trap | None | Trap.getDescription(), StringBuilder.append() | 4 |
| public boolean printIfConnected(boolean) | Used to check that a door is connected to a passage or a chamber, if it is connected to a chamber will print its description as well. | ArrayList\<Space> spaces, int printIndex | None | Chamber.getDescription() | 13 |
| public boolean isLocked() | Returns true if the door is locked or false if unlocked. | locked | None | None | 1 |
| public void setLocked(boolean) | Sets the locked instance boolean to flag. | boolean archway, boolean open, boolean locked | None | None | 7 |
| public void incrementPrintIndex() | Used to increment the print index of the door by 2. | int printIndex | None | None | 1 |
| public void resetPrintIndex() | Resets the print index of the door back to 0. | int printIndex | None | None | 1 |
| public int getPrintIndex() | Returns the current print index of the door. | int printIndex | None | None | 1 |

