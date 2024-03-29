package Checkers;

class movesMade {

    private int fromRow;
    private int fromCol;
    private int toRow; 
    private int toCol; 
    
    /**
     * movesMade constructor takes in selected squares and assigns them to public ints (Like a swap)
     * @param r1 initial row
     * @param c1 initial column
     * @param r2 new row
     * @param c2 new column
     */
    movesMade(int r1, int c1, int r2, int c2) {
        fromRow = r1;
        fromCol = c1;
        toRow = r2;
        toCol = c2;
    }
    
    /**
     * checks if move is a jump
     * @return if the executed move was a jump
     */
    boolean isJump() {
        return (fromRow - toRow == 2 || fromRow - toRow == -2);
    }
    
    /** 
     * getter of the fromRow atribute
     * @return return the fromRow atribute
     * 
     */
    int getFromRow() {
        return fromRow;
    }
    
    /** 
     * getter of the fromCol atribute
     * @return return the fromCol atribute
     * 
     */
    int getFromCol() {
        return fromCol;
    }
    
    /** 
     * getter of the toRow atribute
     * @return return the toRow atribute
     * 
     */
    int getToRow() {
        return toRow;
    }
    
    /** 
     * getter of the toCol atribute
     * @return return the toCol atribute
     * 
     */
    int getToCol() {
        return toCol;
    }
}
