import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.lang.Math;

/**
 * Overarching class for the game.
 */
public class MyConnectFour {

	// constant - fixed game setting
	static final String QUIT_CMD = ".quit";

	Board board;
	HumanPlayer human;
	ComputerPlayer computer;
	BufferedReader input;
	boolean humanStartFirst;
	String winner;
	int conjunctiveCounterToWin;

	/**
	 * Constructor method. Run the set up of the game.
	 * 
	 * @return a MyConnectFour object.
	 */
	public MyConnectFour() {
		System.out.println("Welcome to Connect 4.");
		System.out.println("You will be playing against a computer player.");

		conjunctiveCounterToWin = 4;

		// create board - tight coupling of modules
		board = new Board(6, 7);
		input = new BufferedReader(new InputStreamReader(System.in));

		// let user choose name and counter shape
		String userName = getUserName();
		Counter.Shape userCounterShape = getUserCounterShape();
		Counter.Shape computerCounterShape = Counter.getOther(userCounterShape);
		// create players - tight coupling of modules
		human = new HumanPlayer(userName, userCounterShape, board);
		computer = new ComputerPlayer("Computer", computerCounterShape, board);

		// randomize the starting player
		humanStartFirst = (int) Math.round(Math.random()) == 1;

		System.out.println();
		System.out.println(String.format("%s 's counter shape: %s", userName, userCounterShape));
		System.out.println(String.format("Computer' s counter shape: %s", computerCounterShape));
		String startOrderText = humanStartFirst
				? String.format("%s will start first this time", userName)
				: "Computer will start first this time";
		System.out.println(startOrderText);
		System.out.println();
	}

	/**
	 * Get user name from user input.
	 * 
	 * @return user name.
	 */
	private String getUserName() {
		// could have generalised getUserName & getUserCounterShape but the resultant
		// code will be longer
		String userInput = null;
		System.out.println();
		System.out.println(String.format("What is your name?:"));

		try {
			userInput = input.readLine();
			if (userInput.length() == 0) {
				throw new InputMismatchException("Your name cannot be empty");
			}
			return userInput;
		} catch (IOException e) {
			System.err.println("IO Error when obtaining user input:");
			return getUserName();
		} catch (InputMismatchException e) {
			System.err.println(e.getMessage());
			return getUserName();
		}
	}

	/**
	 * Get user preferred counter shape from user input.
	 * 
	 * @return counter shape.
	 */
	private Counter.Shape getUserCounterShape() {
		String userInput = null;
		System.out.println();
		System.out.println("Enter the counter shape you wish to choose (O/X):");

		try {
			userInput = input.readLine().toUpperCase();
			if (userInput.equals("X")) {
				return Counter.Shape.CROSS;
			} else if (userInput.equals("O")) {
				return Counter.Shape.CIRCLE;
			} else {
				throw new InputMismatchException("Only X or O is accepted");
			}
		} catch (IOException e) {
			System.err.println("IO Error when obtaining user input:");
			return getUserCounterShape();
		} catch (InputMismatchException e) {
			System.err.println(e.getMessage());
			return getUserCounterShape();
		}
	}

	/**
	 * Run the game by looping the turns until a player wins or the game ends in a
	 * draw.
	 */
	public void play() {
		try {
			System.out.println("To play the game, type in the number of the column you want to drop your counter in.");
			System.out.println(
					"A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally");
			board.printBoard();

			boolean humanTurn = humanStartFirst;
			while (winner == null) {
				// each iteration is a player's turn
				Player player = humanTurn ? human : computer;
				runTurn(player);
				humanTurn = !humanTurn;
			}
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	/**
	 * Run a player (human / computer)'s turn. Generalised to accomodate both
	 * HumanPlayer's turn and ComputerPlayer's turn.
	 * 
	 * @param player human or computer player
	 */
	private void runTurn(Player player) {
		System.out.println();
		System.out.println(String.format("%s's turn", player.getName()));

		int colSelected = player.pickPlacementLocation();
		board.insertCounter(colSelected, player.getCounterShape());

		board.printBoard();
		System.out.println();

		// check how many counters are connected vertically, horizontally or diagonally
		int maxConjunctiveCounters = board.checkConjunctiveCounters(player.getCounterShape());
		if (maxConjunctiveCounters >= conjunctiveCounterToWin) {
			System.out.println(player.getWinMsg());
			winner = player.getName();
			return;
		} else if (maxConjunctiveCounters > 1 && player.getName().equals("Computer")) {
			// provide hint to the user if computer has gotten more than 1 consecutive
			// tokens
			System.out.println(
					String.format("Hint: Computer has gotten %s conjunctive counters", maxConjunctiveCounters));
		}

		// check if the board is filled up
		if (board.getEmptyCounterSlotAmount() == 0) {
			System.out.println("The game has ended in a draw");
			winner = "draw";
			return;
		}
	}
}
