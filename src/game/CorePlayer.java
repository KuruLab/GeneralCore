package game;

import map.Level;
import map.Location;
import map.Room;

/**
 *
 * @author Kurumin
 */
public class CorePlayer {

    private String name;
    private Location location, previous;
    private Iventory iventory;
    
    public CorePlayer() {
        this.name = "Unamed Player";
        this.iventory = new Iventory(this);
    }
    
    public CorePlayer(String _name) {
        this();
        this.name = _name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getPrevious() {
        return previous;
    }

    public void setPrevious(Location previous) {
        this.previous = previous;
    }

    public Iventory getIventory() {
        return iventory;
    }

    public void setIventory(Iventory iventory) {
        this.iventory = iventory;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
