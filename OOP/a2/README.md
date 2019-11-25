Name: Jeremy Cross
Email: jcross04@uoguelph.ca
Stu ID: 1048495

-------------------------------------------------------
Academic Honesty Statement:
I, Jeremy Cross, attest that all work for this assignment
was completed through my own aspirations and was not copied
from other sources.
-------------------------------------------------------

Improvements:
1
I would have removed the requirement of an array list containing all doors in the Chamber class. My code utlizes two array lists of doors.
A list of unlinked doors (not linked to a passage) and linked doors. This allowed me to easily access a chamber from a passage and vice versa.
This was utilized for printing as well as building the level.

By removing this list of all doors and replacing it with two separate lists it would reduce the extra processing in the Chamber class as it requires extra thought about when
to add a door/remove a door with just one list of doors.
It would also allow for easy addition to either the unlinked list or the linked list of doors allowing for more streamline class design. Since when a new passage is to be added off of a Chamber
we can simply add the first item in the unlinked door list to the linked list and then remove that item from the unlinked list, this allowed for easier navigation of a Chamber in a coding aspect.
Instead of keeping track of which doors have a passage connected to them the two lists already do this for you.
---------------------------------------------------------
2
I would have made an additional constructor for the Door class that utilizes a boolean as an indicator if this door is to be an archway or not an arhcway.
Since a PassageSection can have specifically archways on either the left/right or leading to a chamber, I found that this extra constructor was very important
to the consistency of information within my program. This constructor has two versions just like the Door contructors now, one with an Exit as well as the isArchway boolean
and one with just the isArchway boolean.

I did this in my program to make it easier as a designer to desingate when an archway door should be created or not. (this was used instead of setting the archway boolean
directly through the setArchway method in my main method). Allowing me to create an archway as an exit specifically from instantiating the door object was very useful.
-------------------------------------------------------------