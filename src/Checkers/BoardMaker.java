package Checkers;


abstract class BoardMaker{
 
    public static final int blank = 0;
    //Clase abstracta de tipos de tablero
    Pieces[][] board;

    /**
     * Generates the game board
     */
    abstract void generateBoard();
    /**
     * returns the game board
     * @return The game board
     */
    public Pieces[][] getBoard(){
        return this.board;
    }
    /**
     * Returns a specific piece in the board
     * @param row The row of the piece 
     * @param col The col of the piece 
     * @return The piece that is being looked for
     */    
    public Pieces getPieces(int row, int col){
        return this.board[row][col];
    }
    /**
     * Returns the number of players in the board
     * @return The number of players
     */
    abstract int getNoOfPlayers();
    /**
     * Loads a saved game board
     * @param filename the name of the file to be checked
     * @return the loaded board
     */
    abstract Pieces[][] loadBoard(String filename);
    /**
     * Returns the instructions of the Game in the board
     * @return The instructions of the game
     */
    abstract String getInstructions();
    /**
     * Returns the current player of a loaded game
     * @return The current player of a loaded game
     */
    abstract int getPlayer();

}