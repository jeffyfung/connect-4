import java.util.ArrayList;
import java.util.Collections;

/**
 * Board class that represents a board of a Connect4 game
 */
public class Board {

    private final int rowCount;
    private final int colCount;
    // represents the layout of the board - set as private such that it cant be
    // directly manipulated by other classes
    private Counter[][] grid;

    /**
     * Constructor method.
     * 
     * @param row number of row in the board
     * @param col number of columns in the board
     * @return a board object
     */
    public Board(int row, int col) {
        rowCount = row;
        colCount = col;
        grid = new Counter[row][col];
    }

    /**
     * Get the amount of empty slot left in the board.
     * 
     * @return amount of empty slot left
     */
    public int getEmptyCounterSlotAmount() {
        int sum = 0;
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                if (grid[row][col] == null) {
                    sum += 1;
                }
            }
        }
        return sum;
    }

    /**
     * Method overload.
     * Get the amount of empty slot left in the board at the provided column.
     * 
     * @param col column index to check
     * @return amount of empty slot left
     */
    public int getEmptyCounterSlotAmount(int col) {
        int sum = 0;
        for (int row = 0; row < rowCount; row++) {
            if (grid[row][col] == null) {
                sum += 1;
            }
        }
        return sum;
    }

    /**
     * Getter for rowCount
     * 
     * @return number of row in the board
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Getter for colCount
     * 
     * @return number of columns in the board
     */
    public int getColCount() {
        return colCount;
    }

    /**
     * Insert a counter of the given shape to the board
     * 
     * @param col   index of the selected column
     * @param shape counter shape to insert
     */
    public void insertCounter(int col, Counter.Shape shape) {
        // reuse the getEmptyCounterSlotAmount method
        int emptySlotsAtCol = getEmptyCounterSlotAmount(col);
        // create a new Counter object
        grid[rowCount - 1 - (rowCount - emptySlotsAtCol)][col] = new Counter(shape);
    }

    /**
     * Displays the board.
     */
    public void printBoard() {
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                Counter counter = grid[row][col];
                if (counter == null) {
                    System.out.print("|   ");
                } else if (counter.getShape() == Counter.Shape.CIRCLE) {
                    System.out.print("| O ");
                } else {
                    System.out.print("| X ");
                }
            }
            System.out.println("|");
        }

        StringBuilder columnAxis = new StringBuilder();
        for (int col = 1; col <= colCount; col++) {
            columnAxis.append("   ").append(col);
        }
        System.out.println(columnAxis.toString());
    }

    /**
     * Check how many counters are connected vertically, horizontally or diagonally.
     * 
     * @param shape counter shape
     * @return max number of conjunctive counters in the board of the given counter
     *         shape
     */
    public int checkConjunctiveCounters(Counter.Shape shape) {
        // generic, boxing and unboxing - easy to add features - detect 2 in row etc
        ArrayList<Integer> list = new ArrayList<>();
        list.add(checkConjunctiveCountersHorizontal(shape));
        list.add(checkConjunctiveCountersVertical(shape));
        list.add(checkConjunctiveCountersDiagonal(shape));
        return Collections.max(list);
    }

    /**
     * Check maximum how many counters are connected horizontally.
     * 
     * @param shape counter shape
     * @return max number of horizontally conjunctive counters in the board of the
     *         given counter
     *         shape
     */
    private int checkConjunctiveCountersHorizontal(Counter.Shape shape) {
        int maxCount = 0;
        for (int row = 0; row < rowCount; row++) {
            int count = 0;
            for (int col = 0; col < colCount; col++) {
                Counter counter = grid[row][col];
                count = counter != null && counter.getShape() == shape ? count + 1 : 0;
                maxCount = Math.max(maxCount, count);
            }
        }
        return maxCount;
    }

    /**
     * Check maximum how many counters are connected vertically.
     * 
     * @param shape counter shape
     * @return max number of vertically conjunctive counters in the board of the
     *         given counter shape
     */
    private int checkConjunctiveCountersVertical(Counter.Shape shape) {
        int maxCount = 0;
        for (int col = 0; col < colCount; col++) {
            int count = 0;
            for (int row = 0; row < rowCount; row++) {
                Counter counter = grid[row][col];
                count = counter != null && counter.getShape() == shape ? count + 1 : 0;
                maxCount = Math.max(maxCount, count);
            }
        }
        return maxCount;
    }

    /**
     * Check maximum how many counters are connected diagonally.
     * 
     * @param shape counter shape
     * @return max number of diagonally conjunctive counters in the board of the
     *         given counter shape
     */
    private int checkConjunctiveCountersDiagonal(Counter.Shape shape) {
        // check diagonals from bottom left to top right
        int maxCount = 0;
        for (int startPointCol = 0; startPointCol < colCount; startPointCol++) {
            int count = 0;
            for (int row = rowCount - 1, col = startPointCol; col < colCount && row >= 0; row--, col++) {
                Counter counter = grid[row][col];
                count = counter != null && counter.getShape() == shape ? count + 1 : 0;
                maxCount = Math.max(maxCount, count);
            }
        }

        // check diagonals from bottom right to top left
        for (int startPointCol = colCount - 1; startPointCol >= 0; startPointCol--) {
            int count = 0;
            for (int row = rowCount - 1, col = startPointCol; col >= 0 && row >= 0; row--, col--) {
                Counter counter = grid[row][col];
                count = counter != null && counter.getShape() == shape ? count + 1 : 0;
                maxCount = Math.max(maxCount, count);
            }
        }

        return maxCount;
    }
}
