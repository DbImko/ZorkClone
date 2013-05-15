ZorkClone
=========

Clone of text-based game Zork

Commands in the game
====================
* n, s, e, w - movement commands to put the player in a different room. If a room is bordered in the direction indicated, the description of
the new room is to be printed to the screen. Otherwise print "Can't go that way."
* i - short for "inventory", lists all items in the player's inventory separated by commas (if more than one). If there are no items in the 
inventory, print "Inventory: empty".
* take (item) - changes item ownership from room or container to inventory. If successful print "Item (item) added to inventory". (Hint: 
this can be written as shortcut for put command)
* open (container) - prints contents of container in format "(container) contains (item), (item), ..." and makes those items available to 
pick up. If empty, should output "(container) is empty."
* open exit - if room is of type exit prints "Game Over" and gracefully ends the program.
* read (item) - prints writing on object if any available, else prints "Nothing written." if command is executed on an existing item in the 
player's inventory that does not contain writing.
* drop (item) - changes item ownership from inventory to present room and prints "(item) dropped."
* put (item) in (container) - changes item ownership from inventory to declared container if container is open and prints "Item (item) 
added to (container)."

Build and run
=============
Build `mvn install`
Run `java -jar org-zorkclone-0.0.1-SNAPSHOT-jar-with-dependencies.jar`
Run tests `mvn test`

You will need Java 7 and Maven to build and run this application.