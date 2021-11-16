package Checkers;

import java.util.ArrayList;

class Data { //Data class begins
    /**
    * Declares board tiles status values 
    */
    public static final int 
    blank = 0,
    player1 = 1,
    playerKing1 = 2,
    player2 = 3,
    playerKing2 = 4;

    private int[][] board; // Declares an int array called board
    private int player1Lost;
    private int player2Lost;

    public Data() {
        player1Lost = 0;
        player2Lost = 0;
        board = new int[8][8]; // creates an 8x8 board
        setUpBoard(); // Call setUpBoard

    }
    
    /**
    * Sets up board assigning tiles to players
    */
    public void setUpBoard() { 

        for (int row = 0; row < 8; row++) {

            for (int col = 0; col < 8; col++) {

                if ( row % 2 == col % 2 ) { // for all dark squares

                    if (row < 3) // if in top 3 rows
                        board[row][col] = player2; // squares are assigned to player 2
                    else if (row > 4) // if in bottom 3 rows
                        board[row][col] = player1; // squares are assigned to player 1
                    else // otherwise, middle rows are empty
                        board[row][col] = blank;

                } else // and all light squares are empty
                    board[row][col] = blank;

            }

        }

    }
    /**
     * Checks what is in the given position tile
     * @param row X-axis position of the piece to be found
     * @param col Y-axis position of the piece to be found
     * @return returns the state of the tile
     */
    public int pieceAt(int row, int col) { //method that returns pieces' location

        return board[row][col];

    }
    /**
     * 
     * @param player
     * @return
     */
    public int getPlayerPieces(int player) {
        int total = 0;
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                if(board[i][k] == player || board[i][k] == (player + 1)) {
                    total++;
                }
            }
        }
        return total;
    }
    /**
     * Method that takes in movesMade type and makes a move
     * @param move the move to be executed
     */
    public void makeMove(movesMade move) {

        makeMove(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());

    }
    /**
     * Executes a movement of a piece
     * @param fromRow initial row of the piece
     * @param fromCol initial column of the piece
     * @param toRow new row to be moved
     * @param toCol new column to be moved
     */
    public void makeMove(int fromRow, int fromCol, int toRow, int toCol) {

        board[toRow][toCol] = board[fromRow][fromCol]; // piece that was in original square is now in new square
        board[fromRow][fromCol] = blank; // the original square is now blank

        if (fromRow - toRow == 2 || fromRow - toRow == -2){ // if a move is a jump

            // the player jumps
            int jumpRow = (fromRow + toRow) / 2;
            int jumpCol = (fromCol + toCol) / 2;

            if (board[jumpRow][jumpCol] == player1 || board[jumpRow][jumpCol] == playerKing1) {
                this.player1Lost++;

            } else {
                if (board[jumpRow][jumpCol] == player2 || board[jumpRow][jumpCol] == playerKing2) {
                    this.player2Lost++;
                }
            }
                
            board[jumpRow][jumpCol] = blank; // the original square is not blank


        }

        if (toRow == 0 && board[toRow][toCol] == player1){ // if a player 1 piece reaches top row
            board[toRow][toCol] = playerKing1;// it becomes a king
        }

        if (toRow == 7 && board[toRow][toCol] == player2){ // if a player 2 piece reaches bottom row
            board[toRow][toCol] = playerKing2; // it becomes a king
        }
    }
    /**
     * 
     * @param player
     * @return
     */
    public int getLostPieces(int player) {
        if(player == 1) {
            return player1Lost;
        } else {
            return player2Lost;
        }
    }
    /**
     * Determines legal moves for a player
     * @param player the player to be checked for legal moves
     * @return an array of moves that the player can execute
     */
    public movesMade[] getLegalMoves(int player) { 

        if (player != player1 && player != player2) // if method is not called with a player
            return null; 

        int playerKing;

        // identifies player's kings
        if (player == player1){
            playerKing = playerKing1;
        } else {
            playerKing = playerKing2;
        }

        ArrayList moves = new ArrayList(); // creates a new Array to story legal moves

        for (int row = 0; row < 8; row++){ // looks through all the squares of the boards

            for (int col = 0; col < 8; col++){

                if (board[row][col] == player || board[row][col] == playerKing){ // if a square belongs to the player

                    // check all possible jumps around the piece - if one found the player must jump
                    if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                        moves.add(new movesMade(row, col, row+2, col+2));
                    if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                        moves.add(new movesMade(row, col, row-2, col+2));
                    if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                        moves.add(new movesMade(row, col, row+2, col-2));
                    if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                        moves.add(new movesMade(row, col, row-2, col-2));

                }

            }

        }

        if (moves.size() == 0){ // if there are no jumps

            for (int row = 0; row < 8; row++){ // look through all the squares again

                for (int col = 0; col < 8; col++){

                    if (board[row][col] == player || board[row][col] == playerKing){ // if a square belongs to the player

                        // check all possible normal moves around the piece - if one found, add it to the list
                        if (canMove(player,row,col,row+1,col+1))
                            moves.add(new movesMade(row,col,row+1,col+1));
                        if (canMove(player,row,col,row-1,col+1))
                            moves.add(new movesMade(row,col,row-1,col+1));
                        if (canMove(player,row,col,row+1,col-1))
                            moves.add(new movesMade(row,col,row+1,col-1));
                        if (canMove(player,row,col,row-1,col-1))
                            moves.add(new movesMade(row,col,row-1,col-1));

                    }

                }

            }

        }

        if (moves.size() == 0){ // if there are no normal moves
            return null;
        }else { // otherwise, an array is created to store all the possible moves
            movesMade[] moveArray = new movesMade[moves.size()];
            for (int i = 0; i < moves.size(); i++){
                moveArray[i] = (movesMade)moves.get(i);
            }
            return moveArray;
        }

    }
    /**
     * determines legal jumps for player
     * @param player the player to be checked for legal jumps
     * @param row the row to be checked
     * @param col the colum to be checked
     * @return an array of possible jumps for the given player
     */
    public movesMade[] getLegalJumpsFrom(int player, int row, int col){

        if (player != player1 && player != player2) //if method is not called with a player
            return null; //null is returned

        int playerKing;

        //identifies player's kings
        if (player == player1){
            playerKing = playerKing1;
        }else {
            playerKing = playerKing2;
        }

        ArrayList moves = new ArrayList(); //creates a new Array to story legal moves

        if (board[row][col] == player || board[row][col] == playerKing){

            //if there is a possible jump, add it to list
            if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                moves.add(new movesMade(row, col, row+2, col+2));
            if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                moves.add(new movesMade(row, col, row-2, col+2));
            if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                moves.add(new movesMade(row, col, row+2, col-2));
            if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                moves.add(new movesMade(row, col, row-2, col-2));

        }

        if (moves.size() == 0){ //if there are no possible moves
            return null; //null is returned
        }else { //otherwise, an array is created to store all the possible moves
            movesMade[] moveArray = new movesMade[moves.size()];
            for (int i = 0; i < moves.size(); i++){
                moveArray[i] = (movesMade)moves.get(i);
            }
            return moveArray;
        }
    }
    /**
     * method checks for possible jumps (eat an opposition's piece)
     * @param player the player to be checked for possible jumps
     * @param r1 initial piece row
     * @param c1 initial piece column
     * @param r2 middle piece row
     * @param c2 middle piece column
     * @param r3 destination row
     * @param c3 destination column
     * @return a boolean to know if the player can possibly jump to the given tiles
     */
    private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3){

        if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8) //if destination row or column is off board
            return false; //there is no jump, as the destination doesn't exist

        if (board[r3][c3] != blank) //if the destination isn't blank
            return false; //there is no jump, as the destination is taken

        if (player == player1) { //in the case of player 1
            if (board[r1][c1] == player1 && r3 > r1) //if destination row is greater than the original
                return false; //there is no jump, as player 1 can only move upwards
            if (board[r2][c2] != player2 && board[r2][c2] != playerKing2) //if the middle piece isn't player 2's
                return false; //there is no jump, as player 1 can't jump his own pieces
            return true; //otherwise, jump is legal
        }else { //in the case of player 2
            if (board[r1][c1] == player2 && r3 < r1) //if destination row is less than the original
                return false; //there is no jump, as player 2 can only move downwards
            if (board[r2][c2] != player1 && board[r2][c2] != playerKing1) //if the middle piece isn't player 1's
                return false; //there is no jump, as player 2 can't jump his own pieces
            return true; //otherwise, jump is legal
        }

    }
    /**
     * 
     * @return
     */
    public int[][] getBoard(){
        return board;
    }

    public void loadBoard(int[][] loaded){

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++){
                board[row][col] = loaded[row][col];
            }
        }
        
    }
    /**
     * method checks for possible normal moves
     * @param player the player to be checked for the possible move
     * @param r1 initial row position
     * @param c1 initial column position
     * @param r2 destination row position
     * @param c2 destination column position
     * @return a boolean if the move can be possibly made by player
     */
    private boolean canMove(int player, int r1, int c1, int r2, int c2){ 

        if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8) //if destination row or column is off board
            return false; //there is no move, as the destination doesn't exist

        if (board[r2][c2] != blank) //if the destination isn't blank
            return false; //there is no move, as the destination is taken

        if (player == player1) { //in the case of player 1
            if (board[r1][c1] == player1 && r2 > r1) //if destination row is greater than the original
                return false; //there is no move, as player 1 can only move upwards
            return true; //otherwise, move is legal
        }else { //in the case of player 2
            if (board[r1][c1] == player2 && r2 < r1) //if destination row is less than the original
                return false; //there is no move, as player 2 can only move downwards
            return true; //otherwise, move is legal
        }

    }

}
