# Assignment 4
Utilizing the code from assingment 3 as your backend for dungeon generation create a GUI using JavaFX that will allow a user to generate a random level upon startup and be able to select a specific space (chamber or passage) within the level from a list menu.  
When a space is selected it can be edited by adding/removing specific monsters or treasures to/from that space and a description of that space is visible within the centre text area. When adding treasure/monsters a list of all possible treasures/monsters can be selected from edit pop-up. The level generated/edited should be able to able to be saved using serialization and then loaded later into the program where further editing/inspection can occur.

A pop-up can be shown with information about a specific door in that space. 

## Images of GUI/Features

### Main GUI
![alt text](https://github.com/jeremycross/UniYear2/blob/master/OOP/a4/mainGUI.PNG)

### Edit Pop-up
**The edit window appears upon clicking the 'Edit selected space' button:**
![alt text](https://github.com/jeremycross/UniYear2/blob/master/OOP/a4/editMenu1.PNG)
  
**Upon clicking 'Add treasures/Add monsters' a list of all available treasures/monsters appears (under heading of add treasure/add monster). Upon clicking 'Remove treasure/monster a list of all treasure(s)/monster(s) in the space appears (under heading remove treasures/remove monsters):**
![alt text](https://github.com/jeremycross/UniYear2/blob/master/OOP/a4/editMenu2.PNG)
  
**To avoid too many edits at once, when a monster/treasure has been selected from the list it will be disabled (grayed out). It will remain in operable until cancelling/exiting the edit or saving the edit to the selected space.**
![alt text](https://github.com/jeremycross/UniYear2/blob/master/OOP/a4/editMenu3.PNG)

### Door info Pop-up
![alt text](https://github.com/jeremycross/UniYear2/blob/master/OOP/a4/doorInfo.PNG)

## Future improvements
The Last assingment in this course was quite a large step up in difficulty from the previous assignments, because of this I would like to pursue adding to this project in my sparetime and make the following improvements to the GUI/Back-end:

 - Integrate the monster/treasure removal and addition using a data base
 - CSS formatting to improve the look and feel of the GUI
 - Spend more time improving the usuability of the editing pop-up
