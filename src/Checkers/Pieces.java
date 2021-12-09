package Checkers;

public class Pieces{

    int color;
    char type ;
    int x;
    int y;
    movesMade[] possibleMoves;
    /**
     * Sets the piece's color
     * @param c the color to be set
     */
    public void setColor(int c){
        color = c;
    }
    /**
     * Returns the piece's color
     * @return The color of the piece
     */
    public int getColor(){
        return color;
    }
    /**
     * Set's X parameter (Horizontal)
     * @param xx The X position
     */
    public void setX(int xx){
        x = xx;
    }
    /**
     * Returns the X position of the piece
     * @return the X position of the piece
     */
    public int getX(){
        return x;
    }
    /**
    * Sets the Y position of the piece (Vertical)
    * @param yy The Y position to be set to the piece
    */
    public void setY(int yy){
        y = yy;
    }
    /**
     * Returns the Y position of the piece
     * @return The Y position of the piece
     */
    public int getY(){
        return y;
    }
    /**
     * Returns the possible moves for the piece
     * @return the possible moves for the piece
     */
    public movesMade[] getPossibleMoves(){
        return possibleMoves;
    }
    /**
     * Adds a move for the piece
     * @param move The move to be added
     */
    public void addMove(movesMade move){
        
    }
    
}