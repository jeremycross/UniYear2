# UniYear2
All my academic projects that were created in my second year of university. 

## Data Structures
All programs in the 'DataStructures' folder were created in C, and makefiles are used for compilation. The programs were created for my 2nd year Data Structures course.

### [Assignments](./DataStructures)

#### [Assignment 1](./DataStructures/a1)
A simple memory system which can track a specified number of blocks of memory and ulilizes a binary file to continuously read/write the information required. This assignment also includes many array commands such as swapping, inserting, removing, adding, etc. at specific indices that will be used in conjuction with the memory system to track the reads/writes used for the commands. There is also the same commands for a linked list that is also used in conjunction with the memory system in the same way. This assignment was used to highlight the pros and cons of arrays and linked lists.  
  
Supplementary testing files were also created and used to run tests and produce csv files with the reads/writes from running the tests.

#### [Assignment 2](./DataStructures/a2)
Read in a text file of unspecified length and read in every word into memory and add it to dynamically allocated linked list (each word is a node). This list can have multiple functions performed on it such as searching for a specific string, creating a soft copy based on the starting and ending nodes, removing consecutive repeating words and outputting linked list to a file in a formatted fashion.

#### [Assignment 3](./DataStructures/a3)
Reads in information from 3 tsv's (the location of these is passed as an argument on the command to run) from IMDB with information surrounding movies, actors and their roles in movies. Creates three binary trees one corresponding to actor name and actor id# (name tree), actor id# and movie id# and their character(s) in that movie (roles tree), movie id# and movie title (title tree).  
  
The program will continuously run until an improper command is entered (proper commands are "name" and "title").
Given the input "name {actors full name}" it will search the actor name/id# tree and find the actors id by traversing the tree. It will then use this number to find the movie id# and the role of the actor in that movie, using the movie id it will print out the the following output: "{Movie title} : {Role(s) in that movie}". This will continue until every movie that actor has been in is found/printed and no more are left to be found.

In a similar process, given the input "title {full movie title}" it will find title id# from title tree. Use that to find the actor id#s in that movie and their roles and use the actor id#s to print out the following output: "{Actor name} : {Character(s) in movie}." This will continue until every actor in this movie and their role(s) have been printed out.

#### [Assignment 4](./DataStructures/a3) 
Simply create 3 hash functions. One will be used to hash strings of names. One will be used to hash dates and the last one will be used to hash license plates.

## OOP
All programs in the 'OOP' folder were created in Java and are compiled using the build.xml (ant) file included within that folder. These programs were created for my 2nd year Object-Oriented programming course.  

### [Assignments](./OOP)

#### Assignment 1
Generates a random element from a DnD game (from a treasure to a whole chamber with treasures/monsters/etc. which is to be selected via user input) and prints out a description of the element generated.  
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
