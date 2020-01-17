# UniYear2
All my academic projects that were created in my second year of university. 

## Data Structures
All programs in the 'DataStructures' folder were created in c, and makefiles are used for compilation. The programs were created for my 2nd year Data Structures course.

### Assignments
Assignment 1 - 
Assignment 2 - 
Assignment 3 - 
Assignment 4 -

## OOP
All programs in the 'OOP' folder were created in java and are compiled using the build.xml (ant) file included within that folder. These programs were created for my 2nd year Object-Oriented programming course.  

### Assignments

#### Assignment 1
Generate a random element from a DnD game (from a treasure to a whole chamber with treasures/monsters/etc. which is to be selected via user input) and prints out a description of the element generated.  
#### Assignment 2
Generates a level of dungeon for a DnD DM that has 5 Chambers (this will be done ignoring geometries) and prints out a formatted description of the level generated.  
#### Assignment 3
Refactoring Assignment 2 so that it follows the single responsibility principle as well as reduces coupling wherever possible, a new level generating algorithm is implemented so that every exit from a chamber is connected to another chamber's exit, this will then be stored as a hashmap (a door is the key and a list of chambers that it connects to is the value) and the level generated is printed out.  

#### Assignment 4
Utilizing your code from assingment 3 as your backend for dungeon generation create a GUI using JavaFX that will allow a user to generate a random level upon startup and be able to select a specific space (chamber or passage) within the level from a dropdown menu. When a space is selected it can be edited by adding/removing specific monsters or treasures to/from that space and a description of that space is visible within the centre text area. The level generated/edited should be able to able to be saved using serialization and then loaded later into the program where further editing/inspection can occur.  
  
##### Future improvements
The Last assingment in this course was quite a large step up in difficulty from the previous assignments, because of this I would like to pursue adding to this project in my sparetime and make the following improvements to the GUI/Back-end:  
 - Integrate the monster/treasure removal and addition using a data base  
 - CSS formatting to improve the look and feel of the GUI
 - Spend more time improving the usuability of the editing pop-up
