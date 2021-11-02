package Checkers;

class movesMade {

    int fromRow, fromCol; // declares from row and column as public ints
    int toRow, toCol; // declares to row and column as public ints


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

}
