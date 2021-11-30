package Checkers;

class Abstract_Piece {
    movesMade[] possibleMoves;
    int color;
    int x;
    int y;

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

}