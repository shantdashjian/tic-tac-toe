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
			if (playerWon(board, currentPlayer)) {
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

	private static void resetBoard(char[][] board) {
		for (int row = 0; row < SIZE; row++) {
			for (int column = 0; column < SIZE; column++) {
				board[row][column] = EMPTY;
			}
		}
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

	private static void printBoard(char[][] board) {
		for (int row = 0; row < SIZE; row++) {
			for (int column = 0; column < SIZE; column++) {
				System.out.print(board[row][column]);
			}
			System.out.println();
		}
	}

	private static void makeMove(char[][] board, Player player, Scanner keyboardInput) {
		System.out.println(player + "'s turn:");
		while (true) {
			int row = getValidIntInput(keyboardInput, "Which row (1-" + SIZE + ")? ") - 1;
			int col = getValidIntInput(keyboardInput, "Which column (1-" + SIZE + ")? ") - 1;
			if (board[row][col] == EMPTY) {
				board[row][col] = player.symbol();
				break;
			}
			System.out.println("That spot is already taken. Please try again.");
		}
	}

	private static int getValidIntInput(Scanner scanner, String prompt) {
		while (true) {
			System.out.print(prompt);
			if (scanner.hasNextInt()) {
				int input = scanner.nextInt();
				if (input >= 1 && input <= SIZE) {
					return input;
				} else {
					System.out.println("Input must be between " + 1 + " and " + SIZE + " inclusive.");
				}
			} else {
				System.out.println("Invalid input. Please enter a number.");
				scanner.next();
			}
		}
	}

	private static boolean playerWon(char[][] board, Player player) {
		// Check rows
		for (int row = 0; row < SIZE; row++) {
			if (playerWonRow(player, board, row)) {
				return true;
			}
		}

		// Check columns
		for (int column = 0; column < SIZE; column++) {
			if (playerWonColumn(player, board, column)) {
				return true;
			}
		}

		// Check diagonals
		return playerWonPrimaryDiagonal(board, player) || playerWonSecondaryDiagonal(board, player);
	}

	private static boolean playerWonRow(Player player, char[][] board, int row) {
		for (int column = 0; column < SIZE; column++) {
			if (board[row][column] != player.symbol) {
				return false;
			}
		}
		return true;
	}

	private static boolean playerWonColumn(Player player, char[][] board, int column) {
		for (int row = 0; row < SIZE; row++) {
			if (board[row][column] != player.symbol) {
				return false;
			}
		}
		return true;
	}

	private static boolean playerWonPrimaryDiagonal(char[][] board, Player player) {
		for (int i = 0; i < SIZE; i++) {
			if (board[i][i] != player.symbol) {
				return false;
			}
		}
		return true;
	}

	private static boolean playerWonSecondaryDiagonal(char[][] board, Player player) {
		for (int i = 0; i < SIZE; i++) {
			if (board[i][SIZE - i - 1] != player.symbol) {
				return false;
			}
		}
		return true;
	}

	private static boolean itIsATie(char[][] board) {
		for (int row = 0; row < SIZE; row++) {
			for (int column = 0; column < SIZE; column++) {
				if (board[row][column] == EMPTY) {
					return false;
				}
			}
		}
		return true;
	}

	private static Player switchPlayer(Player player1, Player player2, Player currentPlayer) {
		return (currentPlayer == player1) ? player2 : player1;
	}

	private static boolean playAgain(Scanner keyboardInput) {
		System.out.print("Play again (y/n)? ");
		return keyboardInput.next().toLowerCase().startsWith("y");
	}

	private record Player(int number, char symbol) {

		@Override
		public String toString() {
			return "Player " + number + " (" + symbol + ")";
		}
	}
}
