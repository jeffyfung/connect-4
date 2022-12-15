import java.lang.Math;

public class ComputerPlayer extends Player {

    public ComputerPlayer(String name, Counter.Shape counterShape, Board board) {
        super(name, counterShape, board);
    }

    public int pickPlacementLocation() {
        int columnSelected = -1;
        while (columnSelected == -1 || board.getEmptyCounterSlotAmount(columnSelected) == 0) {
            columnSelected = (int) Math.floor(Math.random() * board.getColCount());
        }
        return columnSelected;
    }
}
