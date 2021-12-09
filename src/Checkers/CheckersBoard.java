package Checkers;

import java.io.*;

public class CheckersBoard extends BoardMaker{
    public static final int white = 1;
    public static final int black = 3;
    public static final int kingWhite = 2;
    public static final int kingBlack = 4;
    int currentPlayer = -1;


    public CheckersBoard() {
        board = new Pieces[8][8];
        generateBoard();
    }

    /**
     * Generates a Checkers board
     */
    @Override
    void generateBoard() {
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++){
                if ( row % 2 == col % 2 ) { // for all dark squares
                    if (row < 3) // if in top 3 rows
                        board[row][col] = new CheckersPiece(black, row, col); // _squares are assigned to player 2
                    else if (row > 4) // if in bottom 3 rows
                        board[row][col] = new CheckersPiece(white, row, col); // squares are assigned to player 1
                    else // otherwise, middle rows are empty
                        board[row][col] = new CheckersPiece(blank, row, col);
                } else // and all light squares are empty
                    board[row][col] = new CheckersPiece(blank, row, col);
            }
        } 
    }
    /**
     * Returns 2 players of a checkers game
     */
    @Override
    int getNoOfPlayers() {
        return 2;
    }
    /**
     * Loads a saved game's board
     */
    @Override
    Pieces[][] loadBoard(String filename){
        Pieces[][] newBoard = new Pieces[8][8];
        currentPlayer = -1;
        try{
            FileReader fr = new FileReader(filename);
            currentPlayer = Character.getNumericValue(fr.read());
            fr.read();
            for(int i = 0; i < 8; i++) {
                for (int k = 0; k < 8; k++) {
                    int piece = Character.getNumericValue(fr.read());
                    if(piece != -1 || piece != 0){
                        //newBoard[i][k] = piece;
                        newBoard[i][k] = new CheckersPiece(piece, i, k);
                    }else if (piece == 0){
                        newBoard[i][k] = null;
                    }
                }
                fr.read();
            }
            fr.close();
        } catch(Exception e) {

        }
        return newBoard;
    }
    /**
     * returns the currentPlayer of the laoded board
     */
    @Override
    int getPlayer() {
        return currentPlayer;
    }
    /**
     * Returns the Checker's instructions
     */
    @Override
    String getInstructions() {
        return "Rules: \n"
        + "* Each player has a total of 12 pieces, the first player to eliminate all the opponent's pieces is the winner.\n"
        + "* Pieces can only be moved one square diagonally forward, either to the left or right if it's possible. \n"
        + "* When a piece reaches the end of the board, it is crowned. A crowned piece has the option of also moving backwards.\n"
        + "* To eliminate an opponent's piece, one of your pieces must be diagonal to it and it must be possible to place your piece behind your opponent's.\n"
        + "If these conditions are met, it is mandatory to eliminate your opponent's piece. It is possible to do more than one elimination if it after\n"
        + "eliminating one piece meets the same conditions with another, which you will also be obliged to eliminate.";
    }

}