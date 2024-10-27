# Programming CBL Project Mazesweeper
 TU/e 2024-2025 Q1 Programming CBL Project

 ADVANCED TOPICS:

    1. VERSION CONTROL
        During the development of our CBL Project, Mazesweeper, we utilized Git using GitHub and its desktop program for version control.
        This enabled us to work independently on separate parts of the project without having to worry about our code being different every time.
        Once we were satisfied with the progress we had made, we just pushed our new version of the code to the GitHub respository, and once the other person
        pulled that new version Git automatically merged that with the progress they have made in a different part of the code.
        We also regularly used branches to make sure we both are working in our own zones without any interference with the other person's work.
        Ultimately, version control has made our time working on the project much simpler, more efficient and enjoyable.

        To learn about how to use GitHub Desktop we searched the internet and found a Youtube tutorial about it:
        https://www.youtube.com/watch?v=MaqVvXv6zrU

        Our repository for the project can be found through this link:
        https://github.com/sugarrrflake/Programming-CBL-Project-Mazesweeper

    2. ADVANCED ALGORITHMS
        The generation of the board (i.e. the distribution of the mines) is handled by an advanced algorithm.
        Besides just distributing the mines randomly, it makes sure the maze has a solution and even allows for customisation of difficulty levels.
        The maze generation process can be split up into a few parts:
            1. Determining the locations of the player and the goal
            2. Distribution of the mines
            3. Solving the maze and evaluation of the solution
        The code for the entire algorithm can be found in MazeGenerator.java. It is called in Mazesweeper.java on line 808.
        Difficulty level can be set in-game. The exact settings, along with debug settings showShortestPath and showMines can be set
        in the code (MazeGenerator.java lines  21 - 26). To change amountOfMines and minimumPathLength, make sure to set
        doDifficulty to false so they are not overridden.

        Step 1: Set-up, locations of player and goal
            MazeGenerator.generateMaze(int difficultyLevel) is called when the player clicks a tile (Mazesweeper.java, line 808)
            The player is spawned on this tile, and the 3x3 area around the player is cleared 
            (meaning: the tiles are confirmed not to have mines and are coloured beige) (MazeGenerator.java, lines 79 - 90).
            Then, the location for the goal tile is chosen (MazeGenerator.java, lines 92 - 108). 
            The goal tile has to be at least 3 tiles away from the player's spawn tile, 
            so that it is not on or right next to the 3x3 area of safe tiles around the player.

        Step 2: Distribution of the mines
            Now, the mines are distributed. This is handled by the method distributeMines(int amountOfMines). This method is defined
            in MazeGenerator.java on line 297 and called on line 113.
            The program picks a random tile, makes sure it doesn't already have a mine, isn't the goal, and isn't part of the spawn area. 
            If the tile meets these criteria, a mine is placed there. This process is repeated until the amount of mines set in the parameter is placed.
            The value of amountOfMines depends on the selected difficulty level using the slider on the pre-game settings screen.
            These values are pre-set and can be found in the switch statement in MazeGenerator.java on lines 50 - 75.

        Step 3: Solving the maze. 
        This is the most complex part of the generation process. The selected difficulty level does not only affect the amount of mines. 
        It also determines the minimum length of the path to the goal. A higher difficulty means the goal will take more steps to get to, forcing the player to take detours or use items. 
        On line 114, solveIt() is called. 
        MazeGenerator lines 141 - 236:
            - The program assigns an int value "distance" to each reachable tile. This number represents the amount of steps it would
              take to get from the start tile
              to the tile that the number corresponds to. These values are stored in a HashMap.
              It also keeps an ArrayList of "unchecked" tiles.
            - The starting tile is added to the list of unchecked tiles and given the distance value 0.
            - Each adjacent tile is then added to the list and given the value 1, provided it does not have a mine 
              (checking for mines is done in checkTile(), lines 238 - 255).
            - The starting tile is removed from the list of unchecked tiles
            - The next tile is taken from the list of unchecked tiles. Each tile adjacent to it, 
              provided it does not have a mine, is added to the list of unchecked tiles.
              and given the distance value of the original tile + 1. If the adjacent tile already had a distance value, the lower of
              the 2 values is chosen (also checked in checkTile()).
            - This is repeated until the ArrayList uncheckTiles is empty.
            - When this process is complete, each reachable tile has a distance value that represents the length of the shortest
              possible path from the player spawn to that tile.
        If the goal tile does not have a distance value, or if its value is lower than the minimum set by the difficulty level,
        all the mines are removed and a new board is generated.


HOW TO PLAY:

Welcome to Mazesweeper!
An altered version of the popular game Minesweeper.


To get started, click on the Start button in the main menu. You will see a settings menu appear.
Here you can set the seed and the difficulty of your next game.
The seed is an integer and is related to random generation. Setting it is not necesarry, with no seed set a random one is generated.
However, setting the same every time can make the generation similar between games.
The difficulty is a 1-5 slider which decides the amount of mines and the minimum distance to the goal.

After you have set you settings, click on the Start button.
You will see a green grid appear; this is the minefield. 
Click on one of the tiles, this will spawn your player character, a magenta square.

You will also see the eight tiles around you light up in beige, this means they are cleared, there are no mines on them.
The numbers on these cleared tiles are mine hints, they tell you how many mines are on the eight tiles surrounding that tile.
A yellow tile will also spawn, this is your goal, you have to navigate the minefield to this goal, without stepping on a mine.

You can move around using the [W] [A] [S] [D] keys. When you move over a tile that has no mine on it,
it will  become cleared, displaying its mine hint.

When you are certain there is a mine on a tile, you can select it using the [Arrow] keys, this will light it up in cyan.
Once a tile is selected, you can press the [Enter] key to mark it, this will make it light up in red.
NOTE: This is only a visual mark for you to keep track of the tiles you think have mines. Nothing is stopping you from stepping on marked tiles.

If you walk over a tile with a mine on it, its game over. You will have to restart the game with a freshly generated minefield.
Similarly, if you walk over the yellow goal tile, you win.

There are three items aiding your journey through the minefield. You can see whether you can still use these items, on the top of the window.

First is the Defuser: To use the Defuser, you will have to select a tile and press the [1] key. This will attempt to defuse a mine on the selected tile.
If there was a mine there, it gets defused, removed from the game. If there was no mine, nothing happens, but either way, the selected tile is cleared
and the Defuser and used up.

Second is your trusty Radar: Once a game, you can press the [2] key to use your Radar to immediately clear all empty tiles and mark
all mines in a 5x5 area around the player. The Radar is used up.

Last is the Swapper, a risky item: After selecting a tile, you can press the [3] key. This changes the status of the selected tile.
If there was a mine there, it gets swapped with a random empty tile from the minefield and that new tile is now cleared.
However, if you use the Swapper on an empty tile, it gets swapped with a random mine from the minefield.
In either case, the Swapper is used up.

Good luck!