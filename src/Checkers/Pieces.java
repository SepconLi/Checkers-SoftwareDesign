package Checkers;

public class Pieces{

    int color;
    char type ;
    int x;
    int y;
    movesMade[] possibleMoves;

    public void setColor(int c){
        color = c;
    }

    public int getColor(){
        return color;
    }

    public void setX(int xx){
        x = xx;
    }

    public int getX(){
        return x;
    }

    public void setY(int yy){
        y = yy;
    }

    public int getY(){
        return y;
    }

    public movesMade[] getPossibleMoves(){
        return possibleMoves;
    }

    public void addMove(movesMade move){
        
    }
    
}