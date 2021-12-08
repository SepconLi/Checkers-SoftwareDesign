package Checkers;

public class CheckersBoard extends BoardMaker{
    public static final int white = 1;
    public static final int black = 2;
    public static final int kingWhite = 3;
    public static final int kingBlack = 4;

    @Override
    void generateBoard() {
        for(int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++){
                if ( row % 2 == col % 2 ) { // for all dark squares
                    if (row < 3) // if in top 3 rows
                        board[row][col] = new CheckersPiece(white, row, col); // _squares are assigned to player 2
                    else if (row > 4) // if in bottom 3 rows
                        board[row][col] = new CheckersPiece(black, row, col); // squares are assigned to player 1
                    else // otherwise, middle rows are empty
                        board[row][col] = new CheckersPiece(blank, row, col);
                } else // and all light squares are empty
                    board[row][col] = new CheckersPiece(blank, row, col);
            }
        } 
    }
    
    
}