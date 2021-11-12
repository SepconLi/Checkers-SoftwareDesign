package Checkers;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.*;

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
    private static final String GAMES_PATH = "saved_games.txt"; //quité el src/
    private static final String BASE_PATH = "game_";
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
        board.getSave().addActionListener(this);
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
        }else if (src == board.getSave()) {
            if(save_game(board)) {
                System.out.println("Guardado");
            } else {
                System.out.println("Guardadon't");
            }
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

    public boolean save_game(Board game_info){//partida abstracta?para cargar el juego podríamos crear un new Board y le metemos la matriz de data del archivo
        try {
            String filename = get_filename();
            System.out.println(filename);
            FileWriter fw = new FileWriter(filename);
            fw.write(game_info.to_String());
            fw.close();
            fw = new FileWriter(GAMES_PATH, true);
            fw.write(filename + "\n");
            fw.close();
            return true;
        } catch (Exception e) {
            //TODO: handle exception
            return false;
        }
    }

    public String get_filename(){
        String saving_name;
        int saved_games = get_number_of_game();
        saving_name = BASE_PATH + saved_games++;
        return saving_name;
    }

    public int get_number_of_game(){
        int games = 0;
        BufferedReader buff = null;
        try {
            buff = new BufferedReader(new FileReader(GAMES_PATH));
            while((buff.readLine()) != null){
                games++;
            }
            buff.close();

        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("ERROR READING FILE");
        }
        return games;
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
