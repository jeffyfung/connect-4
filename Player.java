public abstract class Player {
    // explanation: also account for future work: Player is not real but subclass
    // share implementation

    protected final Board board;
    private final Counter.Shape counterShape;
    private String name;

    public Player(String name, Counter.Shape counterShape, Board board) {
        this.name = name;
        this.counterShape = counterShape;
        this.board = board; // loose coupling with Board class
    }

    public Counter.Shape getCounterShape() {
        return counterShape;
    }

    public String getName() {
        return this.name;
    }

    public String getWinMsg() {
        return String.format("%s has won!!", name);
    }

    public abstract int pickPlacementLocation();
}