import java.awt.event.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Class describing the tiles that make up the maze.
 */
public class Tile extends JPanel implements MouseListener {

    private final boolean hasMine;

    /**
     * Initiates a tile, with or without a mine.
     * 
     * @param hasMine does the tile have a mine on it or not
     */
    public Tile(boolean hasMine) {
        this.hasMine = hasMine;
        this.addMouseListener(this);
    }

    public boolean getHasMine() {
        return this.hasMine;
    }

    public void mouseEntered(MouseEvent e) {
        this.setBorder(BorderFactory.createLoweredBevelBorder());
    }

    public void mouseExited(MouseEvent e) {
        this.setBorder(BorderFactory.createEmptyBorder());;
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}
}
