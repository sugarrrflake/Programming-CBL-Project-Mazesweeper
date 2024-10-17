import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

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

    public static final int TILE_SIZE = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight()) / 15;

    public static final Color LIGHT_GREEN = new Color(50, 215, 30);
    public static final Color DARK_GREEN = new Color(35, 150, 25);

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
        
        frame.setLayout(null);
        frame.setSize((TILE_SIZE * 10) + 16, (TILE_SIZE * 10) + 139);
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

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveUP");
        mazePanel.getActionMap().put("moveUP", this.moveUp);

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveDOWN");
        mazePanel.getActionMap().put("moveDOWN", this.moveDown);

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRIGHT");
        mazePanel.getActionMap().put("moveRIGHT", this.moveRight);

        mazePanel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLEFT");
        mazePanel.getActionMap().put("moveLEFT", this.moveLeft);

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
     * Runs a fresh game of Mazesweeper.
     */
    public void runGame() {
        SwingUtilities.invokeLater(() -> {

        });
    }

    /**
     * Moving the player charater up one tile.
     */
    class MoveUp extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (player.currentLocation.x > 0) {
                player.oldLocation = player.currentLocation;

                Point newLocation = new Point(player.currentLocation.x - 1, player.currentLocation.y);
    
                player.currentLocation = newLocation;
    
                maze[player.oldLocation.x][player.oldLocation.y].hasPlayer = false;
                maze[player.currentLocation.x][player.currentLocation.y].hasPlayer = true;
            }
        }
    }

    /**
     * Moving the player charater down one tile.
     */
    class MoveDown extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (player.currentLocation.x < 9) {
                player.oldLocation = player.currentLocation;

                Point newLocation = new Point(player.currentLocation.x + 1, player.currentLocation.y);
    
                player.currentLocation = newLocation;
    
                maze[player.oldLocation.x][player.oldLocation.y].hasPlayer = false;
                maze[player.currentLocation.x][player.currentLocation.y].hasPlayer = true;
            }
        }
    }

    /**
     * Moving the player charater right one tile.
     */
    class MoveRight extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (player.currentLocation.y < 9) {
                player.oldLocation = player.currentLocation;

                Point newLocation = new Point(player.currentLocation.x, player.currentLocation.y + 1);
    
                player.currentLocation = newLocation;
    
                maze[player.oldLocation.x][player.oldLocation.y].hasPlayer = false;
                maze[player.currentLocation.x][player.currentLocation.y].hasPlayer = true;
            }
        }
    }

    /**
     * Moving the player charater left one tile.
     */
    class MoveLeft extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (player.currentLocation.y > 0) {
                player.oldLocation = player.currentLocation;

                Point newLocation = new Point(player.currentLocation.x, player.currentLocation.y - 1);
    
                player.currentLocation = newLocation;
    
                maze[player.oldLocation.x][player.oldLocation.y].hasPlayer = false;
                maze[player.currentLocation.x][player.currentLocation.y].hasPlayer = true;
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

        private final Color mainColor;

        
        /**
         * Initiates a tile, with or without a mine.
         * 
         * @param hasMine does the tile have a mine on it or not
         * @param row the row coordinate of the tile
         * @param col the column coordinate of the tile
         */
        public Tile(boolean hasMine, int row, int col, Color maianColor) {
            this.hasMine = hasMine;
            this.row = row;
            this.col = col;
            this.mainColor = maianColor;
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
            if (player == null) {
                player = new Player(new Point(this.row, this.col));
                this.hasPlayer = true;
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
        
        private boolean defuser;
        private boolean radar;
        private boolean swapper;
        
        /**
         * Creates a new player at the specified location.
         */
        public Player(Point location) {
            this.currentLocation = location;
            this.defuser = true;
            this.radar = true;
            this.swapper = true;
        }
    
        public void useDefuser() {
            //TODO
        }
    
        public void useRadar() {
            //TODO
        }
    
        public void useSwapper() {
            //TODO
        }
    }
}