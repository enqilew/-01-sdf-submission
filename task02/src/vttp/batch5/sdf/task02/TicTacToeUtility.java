package vttp.batch5.sdf.task02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class TicTacToe {


    private static final int SIZE = 3;
    private char[][] board;
    
    public TicTacToe() {
        board = new char[SIZE][SIZE];
        // Initialize the board with empty characters
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public void playMove(int row, int col, char player) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            System.out.println("Move is out of bounds.");
            return;
        }
        if (board[row][col] != ' ') {
            System.out.println("Cell already occupied.");
            return;
        }
        board[row][col] = player;
    }

    
    public void readMovesFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                char player = parts[2].charAt(0);
                
                playMove(row, col, player);
                System.out.println("Move: " + player + " to (" + row + "," + col + ")");
                
                if (checkWin(player)) {
                    System.out.println("Player " + player + " wins!");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    


}

import java.io.*;
import java.util.*;

public class TicTacToeUtility {

    public static void main(String[] args) {
        // Step 1: Read board configuration from file
        TTTBoard tttBoard = TTTBoard.readBoardFromFile("board_config.txt");
        
        // Step 2: Get all empty positions on the board
        List<Integer> emptyPos = tttBoard.getAllEmptyPositions();
        
        // Step 3: Initialize utility map to store evaluation scores for each position
        Map<Integer, Integer> utility = new HashMap<>();

        // Step 4: Evaluate each empty position
        for (int pos : emptyPos) {
            // Clone the board to simulate the new move
            TTTBoard newTTTBoard = tttBoard.clone();
            
            // Place "X" in the current empty position
            newTTTBoard.place("X", pos);
            
            // Step 5: Evaluate horizontal, vertical, and diagonal rows on the new board
            if (newTTTBoard.hasThreeInRow("X")) {
                // If "X" has three in a row, assign utility 1
                utility.put(pos, 1);
            } else if (newTTTBoard.hasTwoOAndOneEmpty()) {
                // If "O" has two in a row and one space, assign utility -1
                utility.put(pos, -1);
            } else {
                // Otherwise, assign utility 0
                utility.put(pos, 0);
            }
        }
        
        // Print utility map for debugging
        System.out.println("Utility for each position: " + utility);
    }
}
