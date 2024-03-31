import java.lang.Math;

/**
 * Subclass of player
 */
public class ComputerPlayer extends Player {

    /**
     * Constructor method..
     * 
     * @return a ComputerPlayer object.
     */
    public ComputerPlayer(String name, Counter.Shape counterShape, Board board) {
        super(name, counterShape, board);
    }

    /**
     * pick location of the placement of the counter by random.
     * 
     * @return column index of the board.
     */
    public int pickPlacementLocation() {
        int columnSelected = -1;
        while (columnSelected == -1 || board.getEmptyCounterSlotAmount(columnSelected) == 0) {
            columnSelected = (int) Math.floor(Math.random() * board.getColCount());
        }
        return columnSelected;
    }
}
