import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.lang.Math;

public class MyConnectFour {
	// TODO: check encapsulation

	static final int CONJUNCTIVE_COUNTER_TO_WIN = 4;
	static final String QUIT_CMD = ".quit";

	Board board;
	HumanPlayer human;
	ComputerPlayer computer;
	BufferedReader input;
	boolean humanStartFirst;
	String winner;

	public MyConnectFour() {
		System.out.println("Welcome to Connect 4.");
		System.out.println("You will be playing against a computer player.");

		board = new Board(6, 7);
		input = new BufferedReader(new InputStreamReader(System.in));

		// let user choose name and counter shape
		String userName = getUserName();
		Counter.Shape userCounterShape = getUserCounterShape();
		Counter.Shape computerCounterShape = Counter.getOther(userCounterShape);
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

	// could have generalised getUserName & getUserCounterShape the resultant code
	// will be even longer
	// build a generic method and a custom class to hold the validation criterions
	private String getUserName() {
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

	public void play() {
		try {
			System.out.println("To play the game, type in the number of the column you want to drop your counter in.");
			System.out.println(
					"A player wins by connecting 4 counters in a row - vertically, horizontally or diagonally");
			board.printBoard();

			boolean humanTurn = humanStartFirst;
			while (winner == null) {
				Player player = humanTurn ? human : computer;
				runTurn(player);
				humanTurn = !humanTurn;
			}
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace();
		}
	}

	// private int getUserInput() {
	// String userInput = null;
	// System.out.println(String.format("Enter a column number between 1 and %d:",
	// board.getColCount()));
	// System.out.println(String.format("Enter %s to quit the game:",
	// MyConnectFour.QUIT_CMD));

	// try {
	// userInput = input.readLine();

	// // check if the user wants to terminate the game
	// if (userInput.equals(MyConnectFour.QUIT_CMD)) {
	// System.out.println("\nThis game is terminated early.");
	// System.exit(0);
	// }

	// int position = Integer.parseInt(userInput);
	// // check if the input number is a valid column number
	// if (!(position >= 1 && position <= board.getColCount())) {
	// throw new InputMismatchException(
	// String.format("Invalid input - only accept a number between 1 and %d",
	// board.getColCount()));
	// }
	// // check if the target column is full
	// if (board.getEmptyCounterSlotAmount(position - 1) == 0) {
	// throw new InputMismatchException(String.format("Invalid input - column %d is
	// full", position));
	// }
	// return position;
	// } catch (IOException e) {
	// System.err.println("IO Error when obtaining user input:");
	// return getUserInput();
	// } catch (NumberFormatException e) {
	// System.err.println(String.format("Failed to parse integer from str [%s]:",
	// userInput));
	// return getUserInput();
	// } catch (InputMismatchException e) {
	// System.err.println(e.getMessage());
	// return getUserInput();
	// }
	// }

	// 0 indexed column
	private void runTurn(Player player) {
		System.out.println();
		System.out.println(String.format("%s's turn", player.getName()));

		int colSelected = player.pickPlacementLocation();
		board.insertCounter(colSelected, player.getCounterShape());

		board.printBoard();
		System.out.println();

		int maxConjunctiveCounters = board.checkConjunctiveCounters(player.getCounterShape());
		if (maxConjunctiveCounters >= CONJUNCTIVE_COUNTER_TO_WIN) {
			System.out.println(player.getWinMsg());
			winner = player.getName();
			return;
		}
		// else if (maxConjunctiveCounters == CONJUNCTIVE_COUNTER_TO_WIN - 1 &&
		// player.getName().equals("Computer")) {
		// System.out.println("Be careful! Computer has gotten 3 conjunctive
		// counters!");
		// }

		if (board.getEmptyCounterSlotAmount() == 0) {
			System.out.println("The game has ended in a draw");
			winner = "draw";
			return;
		}
	}
}
