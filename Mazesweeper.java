import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Main class implementing the Mazesweeper game.
 */
public class Mazesweeper {
 
    private final JFrame frame;
    private final Tile[][] maze;
    private Player player = null;
    private Point goalLoaction = null;

    private final MoveUp moveUp;
    private final MoveDown moveDown;
    private final MoveRight moveRight;
    private final MoveLeft moveLeft;

    private final LookUp lookUp;
    private final LookDown lookDown;
    private final LookRight lookRight;
    private final LookLeft lookLeft;

    private final MarkTile markTile;
    private final UseDefuser useDefuser;
    private JLabel defuserUI;
    private final UseRadar useRadar;
    private JLabel radarUI;
    private final UseSwapper useSwapper;
    private JLabel swapperUI;

    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int TILE_SIZE = SCREEN_DIMENSION.height / 15;

    public static final Color LIGHT_GREEN = new Color(50, 215, 30);
    public static final Color DARK_GREEN = new Color(35, 150, 25);
    public static final Color LIGHT_BEIGE = new Color(231, 229, 131);
    public static final Color DARK_BEIGE = new Color(216, 214, 119);

    private Random randomGenerator = new Random();
    private long seed = randomGenerator.nextLong();

    /**
     * Constructor for the main Mazesweeper game.
     * Initializing the game with an empty "maze".
     */
    public Mazesweeper() {

        // TODO SET RANDOM SEED IN DEBUG MENU
        //seed = -4963815611691238200L;
        randomGenerator = new Random(seed);
        System.out.println(seed);

        this.maze = new Tile[10][10];
        this.frame = new JFrame("Mazesweeper");

        this.moveUp = new MoveUp();
        this.moveDown = new MoveDown();
        this.moveRight = new MoveRight();
        this.moveLeft = new MoveLeft();

        this.lookUp = new LookUp();
        this.lookDown = new LookDown();
        this.lookRight = new LookRight();
        this.lookLeft = new LookLeft();

        this.markTile = new MarkTile();
        this.useDefuser = new UseDefuser();
        this.useRadar = new UseRadar();
        this.useSwapper = new UseSwapper();
        
        frame.setLayout(null);
        int frameWidth = (TILE_SIZE * 10) + 16;
        int frameHeight = (TILE_SIZE * 10) + 139;
        frame.setSize(frameWidth, frameHeight);
        int frameX = (SCREEN_DIMENSION.width - frameWidth) / 2;
        int frameY = (SCREEN_DIMENSION.height - frameHeight) / 2;
        frame.setLocation(frameX, frameY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setSize(TILE_SIZE * 10, 100);
        inventoryPanel.setBackground(Color.GRAY);
        inventoryPanel.setLocation(0, 0);
        inventoryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        inventoryPanel.setLayout(new GridLayout(1, 3));

        this.defuserUI = new JLabel("Press [1] to use Defuser");
        defuserUI.setFont(new Font("Sans_Serif", Font.BOLD, TILE_SIZE / 4));
        defuserUI.setForeground(Color.WHITE);

        this.radarUI = new JLabel("Press [2] to use Radar");
        radarUI.setFont(new Font("Sans_Serif", Font.BOLD, TILE_SIZE / 4));
        radarUI.setForeground(Color.WHITE);

        this.swapperUI = new JLabel("Press [3] to use Swapper");
        swapperUI.setFont(new Font("Sans_Serif", Font.BOLD, TILE_SIZE / 4));
        swapperUI.setForeground(Color.WHITE);

        inventoryPanel.add(defuserUI);
        inventoryPanel.add(radarUI);
        inventoryPanel.add(swapperUI);

        frame.add(inventoryPanel);

        JPanel mazePanel = new JPanel();
        mazePanel.setSize(TILE_SIZE * 10, TILE_SIZE * 10);
        mazePanel.setLayout(new GridLayout(10, 10));
        mazePanel.setLocation(0, 100);
        mazeInit();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mazePanel.add(maze[i][j]);
            }
        }

        // Movement action input keys
        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("W"), "moveUP");
        mazePanel.getActionMap().put("moveUP", this.moveUp);

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("S"), "moveDOWN");
        mazePanel.getActionMap().put("moveDOWN", this.moveDown);

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("D"), "moveRIGHT");
        mazePanel.getActionMap().put("moveRIGHT", this.moveRight);

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("A"), "moveLEFT");
        mazePanel.getActionMap().put("moveLEFT", this.moveLeft);

        // Other action input keys
        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("UP"), "lookUP");
        mazePanel.getActionMap().put("lookUP", this.lookUp);

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "lookDOWN");
        mazePanel.getActionMap().put("lookDOWN", this.lookDown);

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "lookLEFT");
        mazePanel.getActionMap().put("lookLEFT", this.lookLeft);

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "lookRIGHT");
        mazePanel.getActionMap().put("lookRIGHT", this.lookRight);

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("ENTER"), "markTile");
        mazePanel.getActionMap().put("markTile", this.markTile);

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("1"), "defuser");
        mazePanel.getActionMap().put("defuser", this.useDefuser);

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("2"), "radar");
        mazePanel.getActionMap().put("radar", this.useRadar);

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("3"), "swapper");
        mazePanel.getActionMap().put("swapper", this.useSwapper);

        frame.add(mazePanel);

        frame.setVisible(true);
    }

    /**
     * Initialize the maze of the game.
     */
    public final void mazeInit() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // Set the color each tile depending on its position:
                // Light - Dark  - Light...
                // Dark  - Light - Dark ...
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        maze[i][j] = new Tile(i, j, LIGHT_GREEN);
                        maze[i][j].setSize(TILE_SIZE, TILE_SIZE);
                        maze[i][j].setBackground(LIGHT_GREEN);
                    } else {
                        maze[i][j] = new Tile(i, j, DARK_GREEN);
                        maze[i][j].setSize(TILE_SIZE, TILE_SIZE);
                        maze[i][j].setBackground(DARK_GREEN);
                    }
                } else {
                    if (j % 2 == 0) {
                        maze[i][j] = new Tile(i, j, DARK_GREEN);
                        maze[i][j].setSize(TILE_SIZE, TILE_SIZE);
                        maze[i][j].setBackground(DARK_GREEN);
                    } else {
                        maze[i][j] = new Tile(i, j, LIGHT_GREEN);
                        maze[i][j].setSize(TILE_SIZE, TILE_SIZE);
                        maze[i][j].setBackground(LIGHT_GREEN);
                    }
                }
            }
        }
    }

    /**
     * Moving the player on tile in a given direction.
     * 
     * @param direction the direction the player wants to move
     */
    public void move(String direction) {


        player.oldLocation = player.currentLocation;
        Point newLocation = null;

        switch (direction) {
            case "UP" -> 
                newLocation = new Point(player.currentLocation.x - 1, player.currentLocation.y);
            case "DOWN" -> 
                newLocation = new Point(player.currentLocation.x + 1, player.currentLocation.y);
            case "RIGHT" -> 
                newLocation = new Point(player.currentLocation.x, player.currentLocation.y + 1);
            case "LEFT" -> 
                newLocation = new Point(player.currentLocation.x, player.currentLocation.y - 1);
            default -> { }
        }

        player.currentLocation = newLocation;

        maze[player.oldLocation.x][player.oldLocation.y].hasPlayer = false;
        maze[player.currentLocation.x][player.currentLocation.y].hasPlayer = true;
        maze[player.currentLocation.x][player.currentLocation.y].clearTile();

        maze[player.oldLocation.x][player.oldLocation.y].repaint();
        maze[player.currentLocation.x][player.currentLocation.y].repaint();
        
        // if the player stepped on a mine, display the game over screen
        if (maze[player.currentLocation.x][player.currentLocation.y].hasMine) {
            JDialog gameOver = new JDialog(frame, "Game Over!", Dialog.ModalityType.DOCUMENT_MODAL);

            int overWidth = SCREEN_DIMENSION.width / 5;
            int overHeight = SCREEN_DIMENSION.height / 5;
            int overX = (SCREEN_DIMENSION.width - overWidth) / 2;
            int overY = (SCREEN_DIMENSION.height - overHeight) / 2;

            gameOver.setLocation(overX, overY);
            gameOver.setSize(overWidth, overHeight);
            gameOver.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            gameOver.setLayout(new FlowLayout());

            JLabel gameOverText = new JLabel("Game Over!");
            gameOverText.setFont(new Font("Sans_Serif", Font.BOLD, overHeight / 4));
            gameOver.add(gameOverText);

            JLabel mineText = new JLabel("You stepped on a mine");
            mineText.setFont(new Font("Sans_Serif", Font.ITALIC, overHeight / 8));
            gameOver.add(mineText);

            gameOver.add(Box.createHorizontalStrut(overWidth));

            JButton menuReturn = new JButton("Return to main menu");
            menuReturn.setFont(new Font("Sans_Serif", Font.PLAIN, overHeight / 10));
            ReturnToMenu returnToMenu = new ReturnToMenu();
            menuReturn.addActionListener(returnToMenu);
            gameOver.add(menuReturn);

            gameOver.setVisible(true);

        }

        //if the player reached the goal, display the victory screen
        if (maze[player.currentLocation.x][player.currentLocation.y].isGoal) {
            JDialog victory = new JDialog(frame, "Victory!", Dialog.ModalityType.DOCUMENT_MODAL);

            int overWidth = SCREEN_DIMENSION.width / 5;
            int overHeight = SCREEN_DIMENSION.height / 5;
            int overX = (SCREEN_DIMENSION.width - overWidth) / 2;
            int overY = (SCREEN_DIMENSION.height - overHeight) / 2;

            victory.setLocation(overX, overY);
            victory.setSize(overWidth, overHeight);
            victory.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            victory.setLayout(new FlowLayout());

            JLabel victoryText = new JLabel("You won!");
            victoryText.setFont(new Font("Sans_Serif", Font.BOLD, overHeight / 4));
            victory.add(victoryText);

            JLabel goalText = new JLabel("You reached the goal");
            goalText.setFont(new Font("Sans_Serif", Font.ITALIC, overHeight / 8));
            victory.add(goalText);

            victory.add(Box.createHorizontalStrut(overWidth));

            JButton menuReturn = new JButton("Return to main menu");
            menuReturn.setFont(new Font("Sans_Serif", Font.PLAIN, overHeight / 10));
            ReturnToMenu returnToMenu = new ReturnToMenu();
            menuReturn.addActionListener(returnToMenu);
            victory.add(menuReturn);

            victory.setVisible(true);
        }

        // De-select tile if one was selected
        for (Tile[] row : maze) {
            for (Tile col : row) {
                col.isSelected = false;
                col.repaint();
            }
        }
    }

    public void look(String direction) {

        Point selectedTile = null;
        switch (direction) {
            case "UP" -> 
                selectedTile = new Point(player.currentLocation.x - 1, player.currentLocation.y);
            case "DOWN" -> 
                selectedTile = new Point(player.currentLocation.x + 1, player.currentLocation.y);
            case "RIGHT" -> 
                selectedTile = new Point(player.currentLocation.x, player.currentLocation.y + 1);
            case "LEFT" -> 
                selectedTile = new Point(player.currentLocation.x, player.currentLocation.y - 1);
            default -> { }
        }

        // Go through all tiles
        for (Tile[] row : maze) {
            for (Tile col : row) {
                /* If tile is the selected tile, set selected to true 
                (or deselect if it was already selected)*/
                if (col == maze[selectedTile.x][selectedTile.y]) {
                    col.isSelected = !col.isSelected;

                //De-select other tiles
                } else {   
                    col.isSelected = false;
                }
                col.repaint();
            }
        }
    }

    class ReturnToMenu implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            Menu.showFrame();
        }
    }

    /**
     * Detecting that the player wants to move upwards.
     */
    class MoveUp extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (player.currentLocation.x > 0) {
                move("UP");
            }
        }
    }

    /**
     * Detecting that the player wants to move downwards.
     */
    class MoveDown extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (player.currentLocation.x < 9) {
                move("DOWN");
            }
        }
    }

    /**
     * Detecting that the player wants to move right.
     */
    class MoveRight extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (player.currentLocation.y < 9) {
                move("RIGHT");
            }
        }
    }

    /**
     * Detecting that the player wants to move left.
     */
    class MoveLeft extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (player.currentLocation.y > 0) {
                move("LEFT");
            }
        }
    }

    /**
     * Detecting that the player wants to perform an action on the tile above them.
     */
    class LookUp extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            look("UP");
        }
    }

    /**
     * Detecting that the player wants to perform an action on the tile above them.
     */
    class LookDown extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            look("DOWN");
        }
    }

    /**
     * Detecting that the player wants to perform an action on the tile above them.
     */
    class LookLeft extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            look("LEFT");
        }
    }

    /**
     * Detecting that the player wants to perform an action on the tile above them.
     */
    class LookRight extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            look("RIGHT");
        }
    }

    class MarkTile extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Tile[] row : maze) {
                for (Tile col : row) {
                    if (col.isSelected) {
                        col.isMarked = !col.isMarked;
                        col.isSelected = false;
                        col.repaint();
                    }
                }
            }
        }
    }

    class UseDefuser extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            // loops to find selected tile
            for (Tile[] row : maze) {
                for (Tile col : row) {
                    if (player.hasDefuser && col.isSelected) {
                        player.hasDefuser = false; // use up defuser
                        col.hasMine = false;
                        col.isSelected = false;
                        col.isMarked = false;
                        col.clearTile();
                        col.repaint();

                        //Clear the tiles around it again to update the mine hints
                        for (int i = col.row - 1; i <= col.row + 1; i++) {
                            for (int j = col.col - 1; j <= col.col + 1; j++) {
                                if (maze[i][j].isCleared) {
                                    maze[i][j].clearTile();
                                }
                            }
                        }
                    }
                }
            }
            defuserUI.setText("Defuser has been used up!");
            defuserUI.setForeground(Color.RED);
        }
    }

    class UseRadar extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

            Tile tile; //the tile being checked by the radar
            if (player.hasRadar) {
                // loop through tiles in a 5x5 radius around player.
                for (int i = -2; i <= 2; i++) { 
                    for (int j = -2; j <= 2; j++) {
                        /* if player is too close to edge, tile doesn't exist. 
                        code no like that. need try catch */
                        try {
                            tile = maze[player.currentLocation.x + i][player.currentLocation.y + j];
                            if (tile.hasMine) {
                                tile.isMarked = true;
                                tile.isCleared = false;
                            } else {
                                tile.isMarked = false;
                                tile.clearTile();
                            }
                            tile.repaint();
                        } catch (java.lang.ArrayIndexOutOfBoundsException exception) { 
                            System.err.println("Array out of bounds exception when using radar.");
                        }
                    }
                }
                player.hasRadar = false; // only have one radar which gets used up
            }
            radarUI.setText("Radar has been used up!");
            radarUI.setForeground(Color.RED);
        }
    }

    class UseSwapper extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Tile[] row : maze) {
                for (Tile col : row) {
                    if (player.hasSwapper && col.isSelected) {
                        /* NOTE: breaks if there's only 1 mine/free tile, because it can't find one
                        This should never happen in a game anyway, but watch it while debugging */

                        // select another tile with a mine if tile does not have mine and vice versa
                        ArrayList<Tile> clearTiles = Tile.getClearedTiles(maze);
                        int row2;
                        int col2;
                        Tile tile2;
                        do {
                            // If possible, we want to swap the mine tile with a cleared tile
                            if (col.hasMine && clearTiles.size() > 0) {
                                // Picks a random tile from the ArrayList of cleared tiles
                                tile2 = clearTiles.get(randomGenerator.nextInt(clearTiles.size()));

                                /* in case something goes wrong and this is called again, 
                                ensures that it can't pick the same tile */
                                clearTiles.remove(tile2); 

                            // If not enough cleared tiles, or the selected tile isn't a mine
                            } else {
                                // Pick any tile
                                row2 = randomGenerator.nextInt(10);
                                col2 = randomGenerator.nextInt(10);
                                tile2 = maze[row2][col2];
                            }
                        } while (tile2.hasMine == col.hasMine || tile2.hasPlayer); 

                        player.hasSwapper = false; // use up swapper
                        col.isSelected = false;
                        col.hasMine = !col.hasMine;
                        tile2.hasMine = !tile2.hasMine;

                        if (col.isMarked ^ col.isCleared) {
                            col.isCleared = !col.isCleared;
                            col.isMarked = !col.isMarked;
                        }

                        col.repaint();
                        tile2.repaint();
                        
                        //Clear the tiles around it again to update the mine hints
                        for (int i = col.row - 1; i <= col.row + 1; i++) {
                            for (int j = col.col - 1; j <= col.col + 1; j++) {
                                if (maze[i][j].isCleared) {
                                    maze[i][j].clearTile();
                                }
                            }
                        }

                        //Clear the tiles around it again to update the mine hints
                        for (int i = tile2.row - 1; i <= tile2.row + 1; i++) {
                            for (int j = tile2.col - 1; j <= tile2.col + 1; j++) {
                                if (maze[i][j].isCleared) {
                                    maze[i][j].clearTile();
                                }
                            }
                        }
                    }
                }
            }
            swapperUI.setText("Swapper has been used up!");
            swapperUI.setForeground(Color.RED);
        }
    }































    /**
     * Class describing the tiles that make up the maze.
     */
    class Tile extends JPanel implements MouseListener {

        private final int row;
        private final int col;

        private boolean hasMine = false;
        private boolean isGoal = false;
        private boolean hasPlayer = false;
        private boolean isCleared = false; // player has been on this tile before
        private boolean isSelected = false;
        private boolean isMarked = false;
        private boolean isOnShortestPath = false;
        JLabel mineHint = new JLabel();

        private final Color mainColor;

        
        /**
         * Initiates a tile, with or without a mine.
         * 
         * @param hasMine does the tile have a mine on it or not
         * @param row the row coordinate of the tile
         * @param col the column coordinate of the tile
         */
        public Tile(int row, int col, Color mainColor) {
            this.row = row;
            this.col = col;
            this.mainColor = mainColor;
            this.addMouseListener(this);
            
            this.mineHint.setFont(new Font("Sans_Serif", Font.BOLD, TILE_SIZE / 2));
            this.mineHint.setVisible(true);
            this.add(this.mineHint);
        }
    
        public boolean getHasMine() {
            return this.hasMine;
        }
    
        public void setHasMine(boolean hasMine) {
            this.hasMine = hasMine;
        }

        public boolean getIsGoal() {
            return this.isGoal;
        }

        public void setIsGoal(boolean isGoal) {
            this.isGoal = isGoal;
        }

        public boolean getIsMarked() {
            return this.isMarked;
        }

        public void setIsMarked(boolean isMarked) {
            this.isMarked = isMarked;
        }
     
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (this.hasPlayer) {
                this.setBackground(Color.MAGENTA);
                this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            } else if (this.isGoal) {
                this.setBackground(Color.YELLOW);
                this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            } else if (this.isSelected) {
                this.setBackground(Color.CYAN);
            } else if (this.isMarked) {
                this.setBackground(Color.RED);
            } else if (this.isCleared && mainColor == LIGHT_GREEN) {
                this.setBackground(LIGHT_BEIGE);
                this.setBorder(BorderFactory.createEmptyBorder());
            } else if (this.isCleared && mainColor == DARK_GREEN) {
                this.setBackground(DARK_BEIGE);
                this.setBorder(BorderFactory.createEmptyBorder());
            } else if (this.isOnShortestPath) {
                this.setBackground(Color.WHITE);
            } else {
                this.setBackground(mainColor);
            }
        }

        /**
         * Clears a tile and gives its mine hint.
         */
        public void clearTile() {
            this.isCleared = true;
            int mineNumber = 0;
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = col - 1; j <= col + 1; j++) {
                    if ((i >= 0 && i < 10) && (j >= 0 && j < 10)) {
                        if (maze[i][j].hasMine) {
                            mineNumber++;
                        }
                    }
                }
            }
            this.mineHint.setText("" + mineNumber);
        }

        /**
         * Set the isCleared property of a tile without making a mine hint.
         */
        public void setIsCleared(boolean isCleared) {
            this.isCleared = isCleared;
        }

        public boolean getIsCleared() {
            return this.isCleared;
        }

        public void setIsOnShortestPath(boolean isOnShortestPath) {
            this.isOnShortestPath = isOnShortestPath;
        }

        public boolean getIsOnShortestPath() {
            return this.isOnShortestPath;
        }

        /**
         * Gets all the cleared tiles in the maze.
         * 
         * @param maze the maze in which to look for cleared tiles (just the normal game maze)
         * @return returns arraylist with all the cleared tiles.
         */
        static ArrayList<Tile> getClearedTiles(Tile[][] maze) {
            ArrayList<Tile> clearedTiles = new ArrayList<Tile>();
            for (Tile [] row : maze) {
                for (Tile col : row) {
                    if (col.isCleared) {
                        clearedTiles.add(col);
                    }
                }
            }
            return clearedTiles;
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            this.setBorder(BorderFactory.createLoweredBevelBorder());
        }
    
        @Override
        public void mouseExited(MouseEvent e) {
            this.setBorder(BorderFactory.createEmptyBorder());
        }
    
        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && player == null) {
                player = new Player(new Point(this.row, this.col));
                this.hasPlayer = true;
                // Place the mines and the goal. TODO: generation method select
                MazeGenerator mazeGenerator 
                    = new MazeGenerator(maze, player.currentLocation, randomGenerator);
                mazeGenerator.generateMaze();

            } else if (SwingUtilities.isRightMouseButton(e)) { //TODO remove: right click spawns mine for debug
                this.hasMine = true;
                //TODO remove before submiting
            } else {
                System.err.println("player already spawned");
            }
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {}
    
        @Override
        public void mouseClicked(MouseEvent e) {}
    }
































    /**
     * Class describing a player character.
     */
    class Player {
    
        private Point oldLocation;
        private Point currentLocation;
        
        private boolean hasDefuser = true;
        private boolean hasRadar = true;
        private boolean hasSwapper = true;
        
        /**
         * Creates a new player at the specified location.
         */
        public Player(Point location) {
            this.currentLocation = location;
            this.hasDefuser = true;
            this.hasRadar = true;
            this.hasSwapper = true;
        }
    }
}