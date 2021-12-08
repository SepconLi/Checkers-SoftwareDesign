package Checkers;

abstract class BoardMaker{
 
    public static final int blank = 0;
    //Clase abstracta de tipos de tablero
    Pieces[][] board;

    public BoardMaker(){
        generateBoard();
    }

    abstract void generateBoard();

    public Pieces[][] getBoard(){
        return this.board;
    }
    
    public Pieces getPieces(int row, int col){
        return this.board[row][col];
    }
}