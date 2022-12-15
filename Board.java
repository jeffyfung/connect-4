import java.util.ArrayList;
import java.util.Collections;

public class Board {

    private final int rowCount;
    private final int colCount;
    private Counter[][] grid;

    public Board(int row, int col) {
        rowCount = row;
        colCount = col;
        grid = new Counter[row][col];
    }

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

    // method overload
    public int getEmptyCounterSlotAmount(int col) {
        int sum = 0;
        for (int row = 0; row < rowCount; row++) {
            if (grid[row][col] == null) {
                sum += 1;
            }
        }
        return sum;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public void insertCounter(int col, Counter.Shape shape) {
        int emptySlotsAtCol = getEmptyCounterSlotAmount(col);
        grid[rowCount - 1 - (rowCount - emptySlotsAtCol)][col] = new Counter(shape);
    }

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

    public int checkConjunctiveCounters(Counter.Shape shape) {
        // generic, boxing and unboxing - easy to add features - detect 2 in row etc
        ArrayList<Integer> list = new ArrayList<>();
        list.add(checkConjunctiveCountersHorizontal(shape));
        list.add(checkConjunctiveCountersVertical(shape));
        list.add(checkConjunctiveCountersDiagonal(shape));
        return Collections.max(list);
    }

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
