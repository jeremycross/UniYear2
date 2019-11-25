Class  PassageSection

| Method signature | Responsibility | Instance variables used | Other class methods called | Objects used with method calls | Lines of code |
|:----------:|:--------------:|:------------------:|:--------------------------:|:------------------------------:|:-------------:|
| public PassageSection() | Randomly create passage section. | String description, Door door | addRandMonster(), addRandStairs() | Door.Door(), HashMap.get() | 11 |
| public PassageSection(String) | create a passage section based on a string. | String description, Door door | addRandMonster(), addRandStairs() | Door.Door() | 10 |
| private void addRandMonster() | Creates a new random monster and adds it to the section. | Monster monster | None  | Monster.Monster(), Monster.setType() | 4 |
| private void setRandStairs() | Creates new random stairs and adds them to the section. | Stairs stairs | None | Stairs.Stairs(), Stairs.setType() | 4 |
| public Door getDoor() | used to get the door in section. | Door door | None | None | 4 |
| public Monster getMonster() |  get monster in section. | Monster monster | None | None | 4 |
| public String getDescription() | get description of the section. | String description | None | None | 1 |
| static | Used for initializing static HashMap which is used for getting a random description | HashMap\<Integer, String> table | None | HashMap.put() | 20 |
| public void setMonster(Monster) | sets the monster of section to the one passed. | Monster monster | None | None | 1 |
| public Stairs getStairs() | used to get stairs in section. | Stairs stairs | None | None | 4 |
| public void addDoor(Door) | used to add a door to the section. (set door in section to door that is passed) | Door door | None | None | 1 |