# Battleship_Game-using-TCP

The above code implements a two-way communication with TCP protocol in JAVA to play Battleship game simultaneously with multiple players which are connected to different servers.

There are 3 main parts of the code - 
1. Main Algorithm - The algorithm of battleship game is included.
2. TCP Protocol - The code to initialize the TCP protocol.
3. Multiple players - The code which connects multiple player to the battleship game.

The design of the battleship game is as follows:
1. The game field is rectangle in which boats and water are included.
2. The boats are verticle or horizontal straight lines between the sea.
3. The boats are represented by numbers while the sea by '.' or dot.

The normal rules of the battleship game are as follows:
1. If a player strikes the co-ordinate which is a part of the boat, then that part of the boat sinks.
2. The player who sinks all the boats is the winner of the game. 
3. If a player strikes the co-ordinate where only water is present then that chance of the player is gone waste.

Winning rule - 
The player who sinks all the boats in the sea is the winner of the game.

# Extra rule added:
 There is a little twist added to the game - 
 If the player hits the middle of the boat and if it is the first hit of the boat
