import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Class that handles the generation the layout of mines on a Mazesweeper board.
 */
public class MazeGenerator {
    
    Mazesweeper.Tile[][] maze;
    Point playerLocation;
    Random randomGenerator;
    Point goalTile;

    //settings
    int amountOfMines = 25;
    int minimumPathLength = 15;
    boolean showMines = false;
    boolean showShortestPath = false;

    // <= 10: 70 | <= 15: 55 | <= 20: 50 | <= 25 : 45 | <= 30: 40
    /* 
     * very easy: 10 mines, length 0 
     * easy: 10 mines, length 10
     * medium: 25 mines, length 15
     * hard: 30 mines, length 20 
     * very hard: 40 mines, length 20
     * stupidly hard: 40 mines, length 30
     * The real difficulty comes from not using items! All of these are easy with the radar.
     */

    /**
     * Constructor for the MazeGenerator.
     * @param maze the game grid
     * @param playerLocation the spawn location of the player
     * @param randomGenerator the Random object to use for seeded generation
     */
    public MazeGenerator(Mazesweeper.Tile[][] maze, Point playerLocation, 
        Random randomGenerator) {
        this.maze = maze;
        this.playerLocation = playerLocation;
        this.randomGenerator = randomGenerator;
        System.out.println("player spawn: " + playerLocation.x + ", " + playerLocation.y);
    }

    /**
     * Generates a goal tile, mines and a 3x3 spawn area around the given player location in the 
     * given grid using the given Random.
     */
    void generateMaze(int difficultyLevel) {

        //set amount of mines and path length according to difficulty level
        switch (difficultyLevel) {
            case 1:
                this.amountOfMines = 10;
                this.minimumPathLength = 0;
                break;
            case 2:
                this.amountOfMines = 10;
                this.minimumPathLength = 10;
                break;
            case 3:
                this.amountOfMines = 25;
                this.minimumPathLength = 15;
                break;
            case 4:
                this.amountOfMines = 30;
                this.minimumPathLength = 20;
                break;
            case 5:
                this.amountOfMines = 40;
                this.minimumPathLength = 20;
                break;
            case 6:
                this.amountOfMines = 40;
                this.minimumPathLength = 30;
                break;
            default:
                System.out.println("Difficulty out of bounds (somehow)"); //not possible I think
                break;
        }  
        System.out.println("difficulty " + difficultyLevel + ", "  
            + amountOfMines  + " mines, " + "minimum path length " + minimumPathLength);

        // Go through tiles in 3x3 area around player 
        for (int i = playerLocation.x - 1; i <= playerLocation.x + 1; i++) {
            for (int j = playerLocation.y - 1; j <= playerLocation.y + 1; j++) {
                // Make sure the tile isn't out of bounds
                if (i >= 0 && i < 10 && j >= 0 && j < 10) {
                    Mazesweeper.Tile tile = maze[i][j];
                    tile.setHasMine(false);
                    tile.setIsCleared(true);
                    tile.repaint();                    
                }
            }
        }

        // Generate the location for the goal tile
        int goalX;
        int goalY;
        int xDistanceToPlayer;
        int yDistanceToPlayer;
        // do-while loop is to make sure the goal tile is not right next to/in the spawn area
        do {
            goalX = randomGenerator.nextInt(0, 10);
            goalY = randomGenerator.nextInt(0, 10);
            xDistanceToPlayer = Math.abs(goalX - playerLocation.x);
            yDistanceToPlayer = Math.abs(goalY - playerLocation.y);
        } while (xDistanceToPlayer <= 2 || yDistanceToPlayer <= 2);

        // set the goal tile
        this.goalTile = new Point(goalX, goalY);
        maze[goalX][goalY].setIsGoal(true);
        maze[goalX][goalY].repaint();

        int pathLength; // solveIt() returns length of path to goal
        do {
            removeMines();
            distributeMines(amountOfMines);
            pathLength = solveIt();
        } while (pathLength <= minimumPathLength);
        System.out.println(pathLength + " steps to goal");

        for (Mazesweeper.Tile tile : Mazesweeper.Tile.getClearedTiles(maze)) {
            tile.clearTile();
            tile.repaint();
        }
    }

    private void removeMines() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (maze[i][j].getHasMine()) {
                    maze[i][j].setHasMine(false);
                    maze[i][j].setIsMarked(false);
                    maze[i][j].repaint();
                }
            }
        }
    }

    /**
     * Finds the fastest path from the player's location to the goal by trying every possible path.
     * @return returns the length of the shortest path from the player to the goal. 
     Returns -1 if there's no solution.
     */
    private int solveIt() {
        boolean hasSolution = false;

        // List of tiles that still need to be checked for paths.
        ArrayList<Point> uncheckedTiles = new ArrayList<Point>();
        uncheckedTiles.add(playerLocation); // start with spawn tile
        // Stores the amount of steps it would take for the player to get to a tile
        HashMap<Mazesweeper.Tile, Integer> tileDistances = new HashMap<Mazesweeper.Tile, Integer>();
        tileDistances.put(maze[playerLocation.x][playerLocation.y], 0);

        // Tiles around each tile in uncheckedTiles
        Mazesweeper.Tile tile;
        Mazesweeper.Tile tileAbove;
        Mazesweeper.Tile tileToRight;
        Mazesweeper.Tile tileBelow;
        Mazesweeper.Tile tileToLeft;

        int distance; // the amount of steps it would take for the player to get to this tile

        // loop that gets the minimum amount of moves it takes to get to each tile on the board
        // minimum amount of moves is called "distance" from here on out
        while (uncheckedTiles.size() > 0) {
            Point tilePoint = uncheckedTiles.get(0);
            tile = maze[tilePoint.x][tilePoint.y];
            distance = tileDistances.get(tile);

            if (tile.getIsGoal()) {
                hasSolution = true;
            }

            // if there is a tile above this tile
            if (tilePoint.x > 0) {
                tileAbove = maze[tilePoint.x - 1][tilePoint.y];
                // and it is not a mine or the goal and this is the shortest path to it so far
                if (checkTile(tileAbove, tilePoint, tileDistances)) {
                    // set its distance to the distance of this tile + 1
                    tileDistances.put(tileAbove, distance + 1);
                    // add it to unchecked tiles so it can be checked for further paths (again)
                    uncheckedTiles.add(new Point(tilePoint.x - 1, tilePoint.y));
                }
            }

            // if there is a tile to the right of this tile
            if (tilePoint.y < 9) {
                tileToRight = maze[tilePoint.x][tilePoint.y + 1];
                // and it is not a mine or the goal and this is the shortest path to it so far
                if (checkTile(tileToRight, tilePoint, tileDistances)) {
                    // set its distance to the distance of this tile + 1
                    tileDistances.put(tileToRight, distance + 1);
                    // add it to unchecked tiles so it can be checked for further paths (again)
                    uncheckedTiles.add(new Point(tilePoint.x, tilePoint.y + 1));
                }
            }
            
            // if there is a tile below this tile
            if (tilePoint.x < 9) {
                tileBelow = maze[tilePoint.x + 1][tilePoint.y];
                // and it is not a mine or the goal and this is the shortest path to it so far
                if (checkTile(tileBelow, tilePoint, tileDistances)) {
                    // set its distance to the distance of this tile + 1
                    tileDistances.put(tileBelow, distance + 1);
                    // add it to unchecked tiles so it can be checked for further paths (again)
                    uncheckedTiles.add(new Point(tilePoint.x + 1, tilePoint.y));
                }
            }
            
            // if there is a tile to the left of this tile
            if (tilePoint.y > 0) {
                tileToLeft = maze[tilePoint.x][tilePoint.y - 1];
                // and it is not a mine or the goal and this is the shortest path to it so far
                if (checkTile(tileToLeft, tilePoint, tileDistances)) {
                    // set its distance to the distance of this tile + 1
                    tileDistances.put(tileToLeft, distance + 1);
                    // add it to unchecked tiles so it can be checked for further paths (again)
                    uncheckedTiles.add(new Point(tilePoint.x, tilePoint.y - 1));
                }
            }

            // resets white tiles
            for (Mazesweeper.Tile[] row : maze) {
                for (Mazesweeper.Tile col : row) {
                    col.setIsOnShortestPath(false);
                }
            }
            // this tile has been fully checked
            uncheckedTiles.remove(tilePoint);
        }


        if (hasSolution) {
            //System.out.println("distance to goal: " + tileDistances.get(maze[goalTile.x][goalTile.y]));
            showShortestPath(this.goalTile, tileDistances);
            return tileDistances.get(maze[goalTile.x][goalTile.y]);
        } else {
            return -1;
        }
    }


    private boolean checkTile(Mazesweeper.Tile tileToCheck, Point tilePoint, 
        HashMap<Mazesweeper.Tile, Integer> tileDistances) {
        
        // the amount of steps it would take for the player to get to current tile
        int distance = tileDistances.get(maze[tilePoint.x][tilePoint.y]);

        // if the tile being checked does not have a mine
        if (!tileToCheck.getHasMine()) {
            // and it doesn't have a distance assigned yet or if this is a shorter path
            if (!tileDistances.containsKey(tileToCheck) 
                || distance + 1 < tileDistances.get(tileToCheck)) {

                return true;
            } 
        }
        return false;
    }

    private boolean showShortestPath(Point tilePoint, 
        HashMap<Mazesweeper.Tile, Integer> distances) {
            
        Mazesweeper.Tile tile = maze[tilePoint.x][tilePoint.y];
        if (distances.get(tile) == 0) {
            return true;
        }

        Point[] nexts = {new Point(tilePoint.x + 1, tilePoint.y), 
                         new Point(tilePoint.x, tilePoint.y + 1),
                         new Point(tilePoint.x - 1, tilePoint.y), 
                         new Point(tilePoint.x, tilePoint.y - 1)};

        for (Point nextPoint : nexts) {
            // if within bounds
            if (nextPoint.x >= 0 && nextPoint.x < 10 && nextPoint.y >= 0 && nextPoint.y < 10) {
                Mazesweeper.Tile nextTile = maze[nextPoint.x][nextPoint.y];

                //if this tile is on the shortest path (recursive) colour it white
                if (distances.get(nextTile) != null
                    && distances.get(nextTile) < distances.get(tile) 
                    && showShortestPath(nextPoint, distances)) {

                    tile.setIsOnShortestPath(showShortestPath);
                    tile.repaint();
                    return true;
                }
            }
        }
        return false;
    }

    
    private void distributeMines(int amountOfMines) {
        int mineX; // the location coordinates of the mine being placed
        int mineY;
        Mazesweeper.Tile tile; // The tile at that location

        while (amountOfMines > 0) {
            mineX = randomGenerator.nextInt(0, 10);
            mineY = randomGenerator.nextInt(0, 10);
            tile = maze[mineX][mineY];

            /*
             * Place mine if: 
             * the targeted tile doesn't already have one, isn't cleared and isn't the goal
            */
            if (!tile.getHasMine() && !tile.getIsCleared() && !tile.getIsGoal()) {
                tile.setHasMine(true);
                tile.setIsMarked(showMines);
                tile.repaint();
                amountOfMines--;
            }
        }
    }
}