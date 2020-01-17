# Assignment 3
Refactoring Assignment 2 so that it follows the single responsibility principle as well as reduces coupling wherever possible, a new level generating algorithm is implemented so that every exit from a chamber is connected to another chamber's exit, this will then be stored as a hashmap (a door is the key and a list of chambers that it connects to is the value) and the level generated is printed out.

## Compilation instructions
Command to run after compilation:
'java -cp lib/dnd-20191025b.jar:.:build level.Main'

build.xml file information:
default target is to compile source code.
to compile source code: 'ant'

to run and compile junits: 'ant junit'
