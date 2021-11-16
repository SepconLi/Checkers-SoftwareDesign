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
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();

        //set the frame's main settings
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.getContentPane();
        game.pack();
        game.setSize(width,height);
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
        game.add(board.getSave());
        game.add(board.getLoad());
        game.add(board.getWhite());
        game.add(board.getBlack());
        
        //places components on the frame in the correct places
        board.setBounds(width/4,40,900,900);
        board.getTitle().setBounds(0,40,360,75);
        board.getMessage().setBounds(20, 120, 250, 30);
        board.getBlack().setBounds(1300,180,120,60);
        board.getWhite().setBounds(1300,480,120,60);
        board.getNewGame().setBounds(50, 180, 150, 30);
        board.getSave().setBounds(50,220,100,30);
        board.getLoad().setBounds(50,260,100,30);
        board.getHowToPlay().setBounds(50, 300, 100, 30);
        board.getCredits().setBounds(50, 340, 100, 30);


        Controller controller = new Controller(board);

    }
}
