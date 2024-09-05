package main;

import java.util.Scanner;

public class TicTacToe {
	final static int SIZE = 3;
	final static char EMPTY = '*';

	public static void main(String[] args) {
		try (Scanner keyboardInput = new Scanner(System.in)) {
			do {
				playGame(keyboardInput);
			} while (playAgain(keyboardInput));
		}
		System.out.println("Great playing, folks!!");
	}

	private static void playGame(Scanner keyboardInput) {
		char[][] board = new char[SIZE][SIZE];
		resetBoard(board);
		Player player1 = new Player(1, chooseSymbol(keyboardInput));
		Player player2 = new Player(2, (player1.symbol() == 'X') ? 'O' : 'X');
		Player currentPlayer = player1;

		while (true) {
			printBoard(board);
			makeMove(board, currentPlayer, keyboardInput);
			if (playerWon(board, currentPlayer.symbol())) {
				printBoard(board);
				System.out.println(currentPlayer + " WINS!!!!!");
				break;
			}
			if (itIsATie(board)) {
				printBoard(board);
				System.out.println("It's a tie!");
				break;
			}
			currentPlayer = switchPlayer(player1, player2, currentPlayer);
		}
	}

	private static Player switchPlayer(Player player1, Player player2, Player currentPlayer) {
		return (currentPlayer == player1) ? player2 : player1;
	}

	private static char chooseSymbol(Scanner keyboardInput) {
		System.out.print("Player 1, select 'X' or 'O': ");
		while (true) {
			char symbol = Character.toUpperCase(keyboardInput.next().charAt(0));
			if (symbol == 'X' || symbol == 'O') {
				return symbol;
			}
			System.out.print("Invalid input. Please enter 'X' or 'O': ");
		}
	}

	private static void makeMove(char[][] board, Player player, Scanner keyboardInput) {
		System.out.println(player + "'s turn:");
		while (true) {
			int row = getValidIntInput(keyboardInput, "Which row (1-" + SIZE + ")? ", 1, SIZE) - 1;
			int col = getValidIntInput(keyboardInput, "Which column (1-" + SIZE + ")? ", 1, SIZE) - 1;
			if (board[row][col] == EMPTY) {
				board[row][col] = player.symbol();
				break;
			}
			System.out.println("That spot is already taken. Please try again.");
		}
	}

	private static boolean playAgain(Scanner keyboardInput) {
		System.out.print("Play again (y/n)? ");
		return keyboardInput.next().toLowerCase().startsWith("y");
	}

	public static void printBoard(char[][] board) {
		for (int row = 0; row < SIZE; row++) {
			for (int column = 0; column < SIZE; column++) {
				System.out.print(board[row][column]);
			}
			System.out.println();
		}
	}

	public static boolean playerWon(char[][] board, char playerChar) {
		// Check rows and columns
		for (int i = 0; i < SIZE; i++) {
			if ((board[i][0] == playerChar && board[i][1] == playerChar && board[i][2] == playerChar) ||
				(board[0][i] == playerChar && board[1][i] == playerChar && board[2][i] == playerChar)) {
				return true;
			}
		}

		// Check diagonals
		return (board[0][0] == playerChar && board[1][1] == playerChar && board[2][2] == playerChar) ||
			(board[0][2] == playerChar && board[1][1] == playerChar && board[2][0] == playerChar);
	}

	public static boolean itIsATie(char[][] board) {
		for (int row = 0; row < SIZE; row++) {
			for (int column = 0; column < SIZE; column++) {
				if (board[row][column] == EMPTY) {
					return false;
				}
			}
		}

		return true;
	}

	public static void resetBoard(char[][] board) {
		for (int row = 0; row < SIZE; row++) {
			for (int column = 0; column < SIZE; column++) {
				board[row][column] = EMPTY;
			}
		}
	}

	public static int getValidIntInput(Scanner scanner, String prompt, int min, int max) {
		while (true) {
			System.out.print(prompt);
			if (scanner.hasNextInt()) {
				int input = scanner.nextInt();
				if (input >= min && input <= max) {
					return input;
				} else {
					System.out.println("Input must be between " + min + " and " + max + " inclusive.");
				}
			} else {
				System.out.println("Invalid input. Please enter a number.");
				scanner.next();
			}
		}
	}

	private record Player(int number, char symbol) {

		@Override
		public String toString() {
			return "Player " + number + " (" + symbol + ")";
		}
	}
}
