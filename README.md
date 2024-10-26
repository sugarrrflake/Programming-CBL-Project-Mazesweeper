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

    2. ADVANCED ALGORITHMS



HOW TO PLAY:

Welcome to Mazesweeper!
An altered version of the popular game Minesweeper.


To get started, click on the Start button in the main menu. You will see a green grid appear; this is the minefield. 
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