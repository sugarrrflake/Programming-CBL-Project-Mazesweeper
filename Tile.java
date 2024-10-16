import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Class describing the tiles that make up the maze.
 */
public class Tile extends JPanel implements MouseListener {

    private final int row;
    private final int col;
    private boolean hasMine;
    
    private static boolean playerSpawned = false;

    /**
     * Initiates a tile, with or without a mine.
     * 
     * @param hasMine does the tile have a mine on it or not
     * @param row the row coordinate of the tile
     * @param col the column coordinate of the tile
     */
    public Tile(boolean hasMine, int row, int col) {
        this.hasMine = hasMine;
        this.row = row;
        this.col = col;
        this.addMouseListener(this);
    }

    public boolean getHasMine() {
        return this.hasMine;
    }

    public void plantMine() {
        this.hasMine = true;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public void mouseEntered(MouseEvent e) {
        this.setBorder(BorderFactory.createLoweredBevelBorder());
    }

    public void mouseExited(MouseEvent e) {
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    public void mousePressed(MouseEvent e) {
        if (!playerSpawned) {
            System.out.println(this.row + " " + this.col);
            playerSpawned = true;
        } else {
            System.out.println("player already spawned");
        }
    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}
}
