package vttp.batch5.sdf.task02;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Error: No TTT configuration file provided.");
            return;
        }

        String fileName = args[0];
        char[][] board;
        
        // Read the board from the file
        try {
            board = readBoard(fileName);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Display the board
        System.out.println("Processing: " + fileName);
        System.out.println("Board:");
        displayBoard(board);
        System.out.println("-----------------------------");

        // Get all legal moves and evaluate utility
        List<int[]> legalMoves = getLegalMoves(board);
        for (int[] move : legalMoves) {
            int x = move[0];
            int y = move[1];
            int utility = evaluateMoveUtility(board, x, y);
            System.out.printf("y=%d, x=%d, utility=%d%n", y, x, utility);
        }
    }

    // Method to read the board from a file
    private static char[][] readBoard(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));
        char[][] board = new char[lines.size()][];
        for (int i = 0; i < lines.size(); i++) {
            board[i] = lines.get(i).toCharArray();
        }
        return board;
    }

    // Method to display the board
    private static void displayBoard(char[][] board) {
        for (char[] row : board) {
            System.out.println(new String(row));
        }
    }

    // Method to get all legal moves (empty positions)
    private static List<int[]> getLegalMoves(char[][] board) {
        List<int[]> moves = new ArrayList<>();
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                if (board[y][x] == '.') {
                    moves.add(new int[]{x, y});
                }
            }
        }
        return moves;
    }

    // Method to evaluate the utility of placing an X at a given position
    private static int evaluateMoveUtility(char[][] board, int x, int y) {
        char[][] newBoard = cloneBoard(board);
        newBoard[y][x] = 'X';

        if (checkWin(newBoard, 'X')) {
            return 1; // Positive utility (you win)
        } else if (checkPotentialLoss(newBoard, 'O')) {
            return -1; // Negative utility (opponent wins)
        } else {
            return 0; // Neutral utility
        }
    }

    // Method to clone the board for simulation
    private static char[][] cloneBoard(char[][] board) {
        char[][] clone = new char[board.length][];
        for (int i = 0; i < board.length; i++) {
            clone[i] = board[i].clone();
        }
        return clone;
    }

    // Method to check if the specified player has won
    private static boolean checkWin(char[][] board, char player) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) || 
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
               (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    // Method to check if the opponent has a potential win
    private static boolean checkPotentialLoss(char[][] board, char opponent) {
        for (int i = 0; i < 3; i++) {
            if (countRowPotential(board, opponent, i, true) || 
                countRowPotential(board, opponent, i, false)) {
                return true;
            }
        }
        return checkDiagonalsPotential(board, opponent);
    }

    // Method to count potential winning rows or columns
    private static boolean countRowPotential(char[][] board, char opponent, int index, boolean isRow) {
        int countOpponent = 0, countEmpty = 0;
        for (int j = 0; j < 3; j++) {
            char cell = isRow ? board[index][j] : board[j][index];
            if (cell == opponent) countOpponent++;
            else if (cell == '.') countEmpty++;
        }
        return countOpponent == 2 && countEmpty == 1;
    }

    // Method to check diagonals for potential winning moves
    private static boolean checkDiagonalsPotential(char[][] board, char opponent) {
        return ((board[0][0] == opponent && board[1][1] == opponent && board[2][2] == '.') ||
                (board[0][0] == opponent && board[1][1] == '.' && board[2][2] == opponent) ||
                (board[0][0] == '.' && board[1][1] == opponent && board[2][2] == opponent) ||
                (board[0][2] == opponent && board[1][1] == opponent && board[2][0] == '.') ||
                (board[0][2] == opponent && board[1][1] == '.' && board[2][0] == opponent) ||
                (board[0][2] == '.' && board[1][1] == opponent && board[2][0] == opponent));
    }
}
