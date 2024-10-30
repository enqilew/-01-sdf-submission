package vttp.batch5.sdf.task02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

	private static final String[] args = null;
	static String fileName = args[0];
    private char[][] board;
    private char currentPlayer;
    private boolean gameOn;
    private static final char PLAYER = 'X';     // Human player
    private static final char COMPUTER = 'O';   // Computer player

    public Main() {
        board = new char[3][3];
        currentPlayer = PLAYER; 
        gameOn = true;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public void displayBoard() {
        System.out.println("Current board:");
        Reader r = new FileReader(fileName);
        BufferedReader br = new BufferedReader(r);
        String line;

        while ((line = br.readLine())!= null) {
            line = line.trim();
        
            String terms[] = line.split(" ");
                populateBoard(br);
                System.out.println();

            }
            br.close();

		if (args.length <= 0) {
            System.err.println("Missing txt file");
            System.exit(1);
        }
    }
		
    public boolean playMove(int row, int col) {
        if (row < 0 || row >= 3 || col < 0 || col >= 3 || board[row][col] != '-') {
            System.out.println("Invalid move. Try again.");
            return false;
        }
        board[row][col] = currentPlayer;
        return true;
    }

    private int minimax(char[][] board, boolean isMaximizing) {
        if (checkWin(COMPUTER)) return 10;
        if (checkWin(PLAYER)) return -10;
        if (checkDraw()) return 0;

        if (isMaximizing) {  // Computer's turn
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = COMPUTER;
                        int score = minimax(board, false);
                        board[i][j] = '-';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {  // Human's turn
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = PLAYER;
                        int score = minimax(board, true);
                        board[i][j] = '-';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    private void playComputerMove() {
        int bestScore = Integer.MIN_VALUE;
        int moveRow = -1;
        int moveCol = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = COMPUTER;
                    int score = minimax(board, false);
                    board[i][j] = '-';
                    if (score > bestScore) {
                        bestScore = score;
                        moveRow = i;
                        moveCol = j;
                    }
                }
            }
        }

        board[moveRow][moveCol] = COMPUTER;
        System.out.println("Computer played move at (" + moveRow + ", " + moveCol + ")");
    }

    private boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }

        if ((board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
            (board[0][2] == player && board[1][1] == player && board[2][0] == player)) {
            return true;
        }

        return false;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private void togglePlayer() {
        currentPlayer = (currentPlayer == PLAYER) ? COMPUTER : PLAYER;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        while (gameOn) {
            displayBoard();
            if (currentPlayer == PLAYER) {
                System.out.println("Player " + currentPlayer + ", enter your move (row and column): ");
                int row = scanner.nextInt();
                int col = scanner.nextInt();

                if (playMove(row, col)) {
                    if (checkWin(PLAYER)) {
                        displayBoard();
                        System.out.println("Player " + PLAYER + " wins!");
                        gameOn = false;
                    } else if (checkDraw()) {
                        displayBoard();
                        System.out.println("It's a draw!");
                        gameOn = false;
                    } else {
                        togglePlayer();
                    }
                }
            } else {  // Computer's turn
                playComputerMove();
                if (checkWin(COMPUTER)) {
                    displayBoard();
                    System.out.println("Computer wins!");
                    gameOn = false;
                } else if (checkDraw()) {
                    displayBoard();
                    System.out.println("It's a draw!");
                    gameOn = false;
                } else {
                    togglePlayer();
                }
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        System.out.printf("Processing: %s\n", fileName);
        System.out.printf("Board:");
        System.out.println(br);
        System.out.println("-----------------------------");
        
        for (every empty space int i; i < empty position space.length; i++){ // From HashMap
            System.out.printf("y=%d, x=%d, utility=%d", y, x, utility);
        }
        
    }
}


//tttboard = read boards configuration file
TTTBoard tttBoard = TTTBoard.readFile(fileName);
//empty_pos: =tttboard.get_all_emptty_pos()
List<Integer> emptyPos = tttBoard.getAllEmptyPositions();
//utility: = map
Map<Integer, Integer> utility = new HashMap<>();
//for every pos in empty_pos begin


//	new_tttboard = clone tttboard
TTTBoard newTTTBoard = tttBoard.clone();

//	new_tttboard.place(X,pos)
newTTTBoard.place("X", pos);
// 	evaluate horizontal, vertical, and diagonal rows on new_tttboard
//		if there are 3 X
//			utility[pos]: =1
//		else if there are 2 O and 1 SPACE
//			utility[pos]: =-1
//		else
//			utility[pos] :=0
if (newTTTBoard.hasThreeInRow("X")) {
utility.put(pos, 1);
} else if (newTTTBoard.hasTwoOAndOneEmpty()) {
    utility.put(pos, -1);
} else {
    utility.put(pos, 0);
            }


      
