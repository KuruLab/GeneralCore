package game;

/**
 *
 * @author Kurumin
 */
public class CorePlayer {

    private String name;
    
    public CorePlayer() {
        this.name = "Unamed Player";
    }
    
    public CorePlayer(String _name) {
        this.name = _name;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
