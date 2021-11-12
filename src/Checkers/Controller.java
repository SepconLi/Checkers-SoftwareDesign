package Checkers;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;

    /**
     * Write a description of class Controller here.
     * 
     * @author (your name) 
     * @version (a version number or a date)
     */
public class Controller implements ActionListener, MouseListener
{
    // instance variables - replace the example below with your own
    private Board board;
    /**
     * Constructor for objects of class Controller
     */
    public Controller(Board newBoard)
    {
        board = newBoard;
        board.addMouseListener(this); // implements Mouse Listener
        board.getHowToPlay().addActionListener(this);
        board.getNewGame().addActionListener(this);
        board.getCredits().addActionListener(this);
    }
    
    /** 
     * implemented from Actions Listener, assigns functions to the buttons
     * @param evt the event that was requested (from the buttons)
     * 
     */
    public void actionPerformed(ActionEvent evt) {

        Object src = evt.getSource();
        if (src == board.getNewGame()) {//if newGame button is pressed, a new game is created
            board.getPlayersColors();
            board.NewGame();
        }else if (src == board.getHowToPlay()) {//if howToPlay button is pressed, instructions pop up
            board.instructions();
        }else if (src == board.getCredits()){ //if credits button is pressed, credits pop up
            board.showCredits();
        }
    }
    
    /**
     * When the board is clicked
     * 
     * @param evt event of the mouse being clicked
     * 
     */
    public void mousePressed(MouseEvent evt) {

        if (!board.getGameInProgress()) { // if game is not in progress
            board.getMessage().setText("Start a new game."); // indicates to start a new game
        } else { // otherwise, calculates which square was pressed
            int col = (evt.getX() - 4) / 80; // calculation of square's column
            int row = (evt.getY() - 4) / 80; // calculation of square's row
            if (col >= 0 && col < 8 && row >= 0 && row < 8) // if square is on the board
                board.ClickedSquare(row, col); // calls ClickedSquare
        }
    }
    
    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseClicked(MouseEvent evt) {
    }

    public void mouseReleased(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }
}
