import java.awt.Point;

/**
 * Class describing a player character.
 */
public class Player {
    
    private Point location;
    
    private boolean defuser;
    private boolean radar;
    private boolean swapper;
    
    /**
     * Creates a new player at the specified location.
     */
    public Player(Point location) {
        this.location = location;
        this.defuser = true;
        this.radar = true;
        this.swapper = true;
    }

    public Point getLocation() {
        return this.location;
    }

    public void setLocation(Point newLocation) {
        this.location = newLocation;
    }

    public boolean hasDefuser() {
        return this.defuser;
    }

    public void useDefuser() {
        //TODO
    }

    public boolean hasRadar() {
        return this.radar;
    }

    public void useRadar() {
        //TODO
    }

    public boolean hasSwapper() {
        return this.swapper;
    }

    public void useSwapper() {
        //TODO
    }
}