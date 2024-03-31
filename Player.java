
/**
 * Abstract class that represents a player
 */
public abstract class Player {

    protected final Board board;
    private final Counter.Shape counterShape;
    private String name;

    /**
     * Constructor method. Class cannot be instantiated alone.
     * 
     * @param name
     * @param counterShape
     * @parma board
     */
    public Player(String name, Counter.Shape counterShape, Board board) {
        this.name = name;
        this.counterShape = counterShape;
        this.board = board; // loose coupling with Board class
    }

    /**
     * Getter for counter shape
     * 
     * @return count shape of the player
     */
    public Counter.Shape getCounterShape() {
        return counterShape;
    }

    /**
     * Getter for name
     * 
     * @return name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get win message that will be displayed to the user after the player wins
     * 
     * @return win message
     */
    public String getWinMsg() {
        return String.format("%s has won!!", name);
    }

    public abstract int pickPlacementLocation();
    // to be implemented by subclass
}