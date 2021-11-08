package Checkers;

import java.awt.*;
import javax.swing.*;

public class Checkers extends JFrame { //Checkers class begins, extends on JFrame class
    /**
    * @author Rodrigo Li
    * @author Asdrubal Villegas
    * @author Jorim Wilson
    * @author Axel Matus
    */
    public static void main (String [] args) { //main method to start the board game

        JFrame game = new JFrame(); //creates new frame

        //set the frame's main settings
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.getContentPane();
        game.pack();
        game.setSize(1000,1000);
        game.setResizable(true);
        game.setLayout(null);
        game.setVisible(true);
        game.setBackground(new Color(225, 225, 225));

        //creates new Board and adds its components
        Board board = new Board();
        game.add(board);
        game.add(board.getTitle());
        game.add(board.getNewGame());    
        game.add(board.getHowToPlay());
        game.add(board.getCredits());
        game.add(board.getMessage());

        //places components on the frame in the correct places
        board.setBounds(0,160,648,648);
        board.getTitle().setBounds(0,0,360,75);
        board.getNewGame().setBounds(6, 120, 150, 30);
        board.getHowToPlay().setBounds(162, 120, 100, 30);
        board.getCredits().setBounds(268, 120, 100, 30);
        board.getMessage().setBounds(0, 806, 250, 30);

    }
}
