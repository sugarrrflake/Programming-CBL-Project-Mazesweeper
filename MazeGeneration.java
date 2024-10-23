import java.awt.Point;

/**
 * Class that handles the generation the layout of mines on a Mazesweeper board.
 */
public class MazeGeneration {
    
    static void generateMaze(Mazesweeper.Tile[][] maze, Point playerLocation) {
        /*
         * TODO:
         * 3x3 area
         * put goal
         * generate maze
         * solve maze
         */

        // Go through tiles in 3x3 area around player 
        for (int i = playerLocation.x - 1; i <= playerLocation.x + 1; i++) {
            for (int j = playerLocation.y - 1; j <= playerLocation.y + 1; j++) {
                // Make sure the tile isn't out of bounds
                if (i >= 0 && i < 10 && j >= 0 && j < 10) {
                    Mazesweeper.Tile tile = maze[i][j];
                    tile.removeMine();
                    tile.clearTile();
                    tile.repaint();                    
                }
            }
        }
    }
}

/*
 * // Spawning the goal in relation to the player
                Point goal = new Point(player.currentLocation.x, player.currentLocation.y);
                Point oldGoal = new Point(goal);
                ArrayList<Point> visitedTiles = new ArrayList<Point>();

                for (int i = 0; i < 30; i++) {

                    Point newGoal = new Point(goal);
                    // if the current tile has already 
                    // been visited, backtrack
                    if (visitedTiles.contains(newGoal)) {
                        goal.x = oldGoal.x;
                        goal.y = oldGoal.y;
                    // otherwise add it to the visited pile
                    } else {
                        visitedTiles.add(newGoal);
                        oldGoal.x = goal.x;
                        oldGoal.y = goal.y;
                    }

                    // pick a random direction, starting at the player spawn
                    int direction = randomGenerator.nextInt(3);

                    // if the tile in that direction is
                    // not out of bounds, move there
                    switch (direction) {
                        case 0 -> goal.x = goal.x - 1 >= 0 ? goal.x - 1 : goal.x;
                        case 1 -> goal.y = goal.y + 1 <= 9 ? goal.y + 1 : goal.y;
                        case 2 -> goal.x = goal.x + 1 <= 9 ? goal.x + 1 : goal.x;
                        case 3 -> goal.y = goal.y - 1 >= 0 ? goal.y - 1 : goal.y;
                        default -> { }
                    }

                }

                maze[goal.x][goal.y].isGoal = true;
                maze[goal.x][goal.y].repaint();
                goalLoaction = new Point(goal.x, goal.y);

                // Random generation of mines while leaving 
                // out the 3x3 area at the player spawn
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 10; j++) {
                        boolean isMine = randomGenerator.nextInt(3) == 0;
                        if (i < player.currentLocation.x - 1 || i > player.currentLocation.x + 1 || j < player.currentLocation.y - 1 || j > player.currentLocation.y + 1) {
                            if (i != goalLoaction.x && j != goalLoaction.y) {
                                if ((i >= 0 && i < 10) && (j >= 0 && j < 10)) {
                                    maze[i][j].hasMine = isMine;
                                }
                            }
                        }
                    }
                }

                // Clearing a 3x3 safe area around the player
                // if we are in that area
                for (int i = player.currentLocation.x - 1; i <= player.currentLocation.x + 1; i++) {
                    for (int j = player.currentLocation.y - 1; j <= player.currentLocation.y + 1; j++) {
                        // and not out of bounds
                        if ((i >= 0 && i < 10) && (j >= 0 && j < 10)) {
                            // and not on the player tile
                            if (i != player.currentLocation.x || j != player.currentLocation.y) {
                                maze[i][j].clearTile();
                                maze[i][j].repaint();
                            }
                        }
                    }
                }
 */