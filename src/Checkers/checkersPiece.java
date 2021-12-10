package Checkers;

import java.util.ArrayList;

class CheckersPiece extends Pieces {
    private int king;
    //movesMade[] possibleMoves;
    ArrayList moves;
    //Data data;

    public CheckersPiece(int c, int xx, int yy /*Data d*/){
        color = c;
        x = xx;
        y = yy;
        //data = d;
        newMoves();
    }
    /**
     * Return possible moves for a Checkers piece
     */
    @Override public movesMade[] getPossibleMoves(){//ver como arreglar esto para que sea eficiente
        if (moves.size() == 0){ // if there are no normal moves
            return null;
        }else { // otherwise, an array is created to store all the possible moves
            possibleMoves = new movesMade[moves.size()];
            for (int i = 0; i < moves.size(); i++){
                possibleMoves[i] = (movesMade)moves.get(i);
            }
            return possibleMoves;
        }
        
    }
    /**
     * Returns if a piece is a king
     * @param k the king value
     */
    public void setKing(int k){
        king = k;
    }
    /**
     * Returns the piece's king status
     * @return a king status
     */
    public int getKing(){
        return king;
    }
    /**
     * Generates the moves
     */
    public void newMoves(){
        moves =  new ArrayList();
    }
    /**
     * Add a move to the @moves array
     */
    @Override 
    void addMove(movesMade move){
        moves.add(move);
    }

}