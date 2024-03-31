import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;

/**
 * Subclass of player
 */
public class HumanPlayer extends Player {

    private BufferedReader input;

    /**
     * Constructor method..
     * 
     * @return a HumanPlayer object.
     */
    public HumanPlayer(String name, Counter.Shape counterShape, Board board) {
        super(name, counterShape, board);
        input = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Get user input on the location of placement for the counter
     * 
     * @return column index of the board.
     */
    public int pickPlacementLocation() {
        String userInput = null;
        System.out.println();
        System.out.println(String.format("Enter a column number between 1 and %d:", board.getColCount()));
        System.out.println(String.format("Enter %s to quit the game:", MyConnectFour.QUIT_CMD));

        try {
            userInput = input.readLine();

            // check if the user wants to terminate the game
            if (userInput.equals(MyConnectFour.QUIT_CMD)) {
                System.out.println("\nThis game is terminated early.");
                System.exit(0);
            }

            int position = Integer.parseInt(userInput);
            // check if the input number is a valid column number
            if (!(position >= 1 && position <= board.getColCount())) {
                throw new InputMismatchException(
                        String.format("Invalid input - only accept a number between 1 and %d", board.getColCount()));
            }

            // check if the target column is full
            int colIndex = position - 1;
            if (board.getEmptyCounterSlotAmount(colIndex) == 0) {
                throw new InputMismatchException(String.format("Invalid input - column %d is full", position));
            }

            return colIndex;
        } catch (IOException e) {
            System.err.println("IO Error when obtaining user input:");
            return pickPlacementLocation();
        } catch (NumberFormatException e) {
            System.err.println(String.format("Failed to parse integer from str [%s]:", userInput));
            return pickPlacementLocation();
        } catch (InputMismatchException e) {
            System.err.println(e.getMessage());
            return pickPlacementLocation();
        }
    }

}
