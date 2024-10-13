import javax.swing.JPanel;

/**
 * Class describing the tiles that make up the maze.
 */
public class Tile extends JPanel {

    private final boolean hasMine;

    /**
     * Initiates a tile, with or without a mine.
     * 
     * @param hasMine does the tile have a mine on it or not
     */
    public Tile(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public boolean getHasMine() {
        return this.hasMine;
    }
}
