package Checkers;

import java.util.ArrayList;

class Checkers_Piece extends Pieces {
    private int king;
    //movesMade[] possibleMoves;
    ArrayList moves;
    //Data data;

    public Checkers_Piece(int c, int xx, int yy /*Data d*/){
        color = c;
        x = xx;
        y = yy;
        //data = d;
        newMoves();
    }

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

    public void setKing(int k){
        king = k;
    }

    public int getKing(){
        return king;
    }

    public void newMoves(){
        moves =  new ArrayList();
    }

    @Override public void addMove(movesMade move){
        moves.add(move);
    }

}