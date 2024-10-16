import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Main class implementing the Mazesweeper game.
 */
public class Mazesweeper implements MouseListener {
 
    private JFrame frame;
    private JPanel inventoryPanel;
    private Tile[][] maze;
    private Player player = null;
    

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
        mazePanel.addMouseListener(this);
        mazeInit();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                mazePanel.add(maze[i][j]);
            }
        }
        frame.add(mazePanel);

        frame.setVisible(true);
    }

    /**
     * Initialize the maze of the game.
     */
    public void mazeInit() {

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {

                maze[i][j] = new Tile(false);
                maze[i][j].setSize(TILE_SIZE, TILE_SIZE);

                // Set the color each tile depending on its position:
                // Light - Dark  - Light...
                // Dark  - Light - Dark ...
                if (i % 2 == 0) {
                    if (j % 2 == 0) {
                        maze[i][j].setBackground(LIGHT_GREEN);
                    } else {
                        maze[i][j].setBackground(DARK_GREEN);
                    }
                } else {
                    if (j % 2 == 0) {
                        maze[i][j].setBackground(DARK_GREEN);
                    } else {
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

    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    /**
     * Spawns a player at the click location if there is no player yet.
     * Otherwise //AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA//
     * 
     * @param e mouse click
     */
    public void mousePressed(MouseEvent e) {

        if (this.player == null) {
            Point spawPoint = new Point(e.getX(), e.getY());
            this.player = new Player(spawPoint);
            System.out.println(this.player.getLocation().getX() + " ");
            System.out.println(this.player.getLocation().getY());
        } else {

        }

    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}
}