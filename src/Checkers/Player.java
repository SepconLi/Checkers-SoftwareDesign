package Checkers;

public class Player {

    private String name = ""; // Player's name
    private String color; // Player's color
    private int playerNo = -1; // Player 1, 2, etc
    
    Player(String newName,String newColor, int newPlayerNo){

        name = newName;
        color = newColor;
        playerNo = newPlayerNo;

    }
    /**
     * Returns player's name
     * @return Player's name
     */
    String getName() {
        return name;
    }
    /**
     * Returns player's color
     * @return Player's color
     */
    String getColor() {
        return color;
    }
    /**
     * Returns player's number
     * @return Player's numer
     */
    int getPlayerNo() {
        return playerNo;
    }

}