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
 
    private JFrame frame;
    private JPanel inventoryPanel;
    private Tile[][] maze;
    private Player player = null;

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



    public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();

    public static final int TILE_SIZE = SCREEN_DIMENSION.height / 15;

    public static final Color LIGHT_GREEN = new Color(50, 215, 30);
    public static final Color DARK_GREEN = new Color(35, 150, 25);
    public static final Color LIGHT_BEIGE = new Color(231, 229, 131);
    public static final Color DARK_BEIGE = new Color(216, 214, 119);

    /**
     * Constructor for the main Mazesweeper game.
     * Initializing the game with an empty "maze".
     */
    public Mazesweeper() {

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
        
        frame.setLayout(null);
        int frameWidth = (TILE_SIZE * 10) + 16;
        int frameHeight = (TILE_SIZE * 10) + 139;
        frame.setSize(frameWidth, frameHeight);
        int frameX = (SCREEN_DIMENSION.width - frameWidth) / 2;
        int frameY = (SCREEN_DIMENSION.height - frameHeight) / 2;
        frame.setLocation(frameX, frameY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        this.inventoryPanel = new JPanel();
        inventoryPanel.setSize(TILE_SIZE * 10, 100);
        inventoryPanel.setBackground(Color.GRAY);
        inventoryPanel.setLocation(0, 0);
        inventoryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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

        /*mazePanel.getInputMap().put(KeyStroke.getKeyStroke("2"), "radar");
        mazePanel.getActionMap().put("radar", this.useRadar);

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("3"), "swapper");
        mazePanel.getActionMap().put("swapper", this.useSwapper);*/

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
                        maze[i][j] = new Tile(false, i, j, LIGHT_GREEN);
                        maze[i][j].setSize(TILE_SIZE, TILE_SIZE);
                        maze[i][j].setBackground(LIGHT_GREEN);
                    } else {
                        maze[i][j] = new Tile(false, i, j, DARK_GREEN);
                        maze[i][j].setSize(TILE_SIZE, TILE_SIZE);
                        maze[i][j].setBackground(DARK_GREEN);
                    }
                } else {
                    if (j % 2 == 0) {
                        maze[i][j] = new Tile(false, i, j, DARK_GREEN);
                        maze[i][j].setSize(TILE_SIZE, TILE_SIZE);
                        maze[i][j].setBackground(DARK_GREEN);
                    } else {
                        maze[i][j] = new Tile(false, i, j, LIGHT_GREEN);
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
        maze[player.currentLocation.x][player.currentLocation.y].isCleared = true;

        maze[player.oldLocation.x][player.oldLocation.y].repaint();
        maze[player.currentLocation.x][player.currentLocation.y].repaint();
            
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

        // De-select tile if one was selected
        for (Tile[] row : maze) {
            for (Tile col : row) {
                col.selected = false;
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
        // De-select other tiles
        for (Tile[] row : maze) {
            for (Tile col : row) {
                col.selected = false;
                col.repaint();
            }
        }

        // set the selected tile and paint it a different colour
        if (selectedTile != null) {
            maze[selectedTile.x][selectedTile.y].selected = true;
            maze[selectedTile.x][selectedTile.y].repaint();
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
                    if (col.selected) {
                        col.marked = !col.marked;
                        col.selected = false;
                        col.repaint();
                    }
                }
            }
        }
    }

    class UseDefuser extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Tile[] row : maze) {
                for (Tile col : row) {
                    if (player.hasDefuser && col.selected) {
                        //player.hasDefuser = false;
                        col.hasMine = false;
                        col.selected = false;
                        col.isCleared = true;
                        col.repaint();
                    }
                }
            }
        }
    }








    /**
     * Class describing the tiles that make up the maze.
     */
    class Tile extends JPanel implements MouseListener {

        private final int row;
        private final int col;

        private boolean hasMine;
        private boolean hasPlayer = false;
        private boolean isCleared = false; // player has been on this tile before
        private boolean selected = false;
        private boolean marked = false;

        private final Color mainColor;

        
        /**
         * Initiates a tile, with or without a mine.
         * 
         * @param hasMine does the tile have a mine on it or not
         * @param row the row coordinate of the tile
         * @param col the column coordinate of the tile
         */
        public Tile(boolean hasMine, int row, int col, Color mainColor) {
            this.hasMine = hasMine;
            this.row = row;
            this.col = col;
            this.mainColor = mainColor;
            this.addMouseListener(this);
        }
    
        public boolean getHasMine() {
            return this.hasMine;
        }
    
        public void plantMine() {
            this.hasMine = true;
        }
     
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (this.hasPlayer) {
                this.setBackground(Color.MAGENTA);
            } else if (this.selected) {
                this.setBackground(Color.CYAN);
            } else if (this.marked) {
                this.setBackground(Color.RED);
            } else if (this.isCleared && mainColor == LIGHT_GREEN) {
                this.setBackground(LIGHT_BEIGE);
            } else if (this.isCleared && mainColor == DARK_GREEN) {
                this.setBackground(DARK_BEIGE);
            } else {
                this.setBackground(mainColor);
            }
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            if (!this.hasPlayer) {
                this.setBorder(BorderFactory.createLoweredBevelBorder());
            }
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
            } else if (SwingUtilities.isRightMouseButton(e)) { //TODO right click spawns mine for debug
                this.hasMine = true;
            } else {
                System.out.println("player already spawned");
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
    
        public void useDefuser() {
            //TODO
        }
    
        public void useRadar() {
            //TODO
        }
    
        public void useSwapper() {
            //TODO not on tile ur standing on
        }
    }
}