package Checkers;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.*;
import java.util.*;

    /**
     * Controls the piece's movements between Data and Visual (Board)
     */
public class Controller implements ActionListener, MouseListener
{
    private Board board;
    private BoardMaker boardType;
    private Data data;
    private Pieces[][] table;
    private static final String GAMES_PATH = "saved_games.txt"; 
    private static final String BASE_PATH = "game_";
    public Controller(){}
    /**
     * Constructor for objects of class Controller
     */
    public Controller(Board newBoard)
    {
        // DEFAULT TABLE SELECTOR
        boardType = new CheckersBoard(); //Checkers table
        table = boardType.getBoard();
        // END OF DEFAULT TABLE SELECTOR

        board = newBoard;
        data = new Data();
        board.addMouseListener(this); // implements Mouse Listener
        board.getHowToPlay().addActionListener(this);
        board.getNewGame().addActionListener(this);
        board.getCredits().addActionListener(this);
        board.getSave().addActionListener(this);
        board.getLoad().addActionListener(this);

        newGame();
        
        /*
        for (int i = 0; i < 8; i++) {
            for (int k = 0; k < 8; k++) {
                if(table[i][k] != null) {
                    System.out.print(table[i][k].getColor());
                }else{
                    System.out.print("0");
                }
            }
            System.out.println();
        }*/

        
    }
    public int getNoOfPlayers() {
        return boardType.getNoOfPlayers();
    }
    
    /**
     * 
     */
    private void newGame() {
        board.newGame(table,this);
    }
    /**
     * 
     * @param move
     * @return
     */
    public Pieces[][] makeMove(movesMade move) {
        data.makeMove(move);
        return data.getBoard();
    }
    /**
     * 
     * @return
     */
    public Pieces[][] getBoard() {
        return data.getBoard();
    }
    /**
     * 
     * @param player
     * @return
     */
    public movesMade[] getMovesFrom(int player) {
        movesMade[] result = data.getLegalMoves(player);
        //System.out.println(result.length);
        return result;
    }
    /**
     * 
     * @param player
     * @param row
     * @param col
     * @return
     */
    public movesMade[] getJumpsFrom(int player,int row, int col) {
        return data.getLegalJumpsFrom(player, row, col);
    }
    /**
     * 
     * @param player
     * @return
     */
    public int getLostPiecesFrom(int player) {
        return data.getLostPieces(player);
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
            data.setUpBoard();
            board.newGame(data.getBoard(),this);
        }else if (src == board.getHowToPlay()) {//if howToPlay button is pressed, instructions pop up
            board.instructions(Data.CHECKERS_INSTRUCTIONS);
        }else if (src == board.getCredits()){ //if credits button is pressed, credits pop up
            board.showCredits();
        }else if (src == board.getSave()) {
            if(saveGame(board)) {
                System.out.println("Guardado");
            } else {
                System.out.println("Guardadon't");
            }
        } else if (src == board.getLoad()) {
            String[] file_names = returnSavedGames();
            int x = JOptionPane.showOptionDialog(null, "Choose game file to load",
                    "Click a button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, file_names, file_names[0]);
            loadGame(file_names[x]);
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
                board.clickedSquare(row, col); // calls ClickedSquare
        }
    }
    /**
     * Saves the current game
     * @param game_info the board's current status
     * @return a success value of the save function
     */
    public boolean saveGame(Board game_info){
        try {
            String filename = getFilename();
            System.out.println(filename);
            FileWriter fw = new FileWriter(filename);
            fw.write(game_info.toString());
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
    /**
     * Generates a filename for a game to be saved
     * @return The filename to be used for the saved game
     */
    public String getFilename(){
        String saving_name;
        int saved_games = getNumberOfGame();
        saving_name = BASE_PATH + saved_games++;
        return saving_name;
    }
    /**
     * Gets the number of the game to be saved
     * @return the game's number according to the saved_games.txt
     */
    public int getNumberOfGame(){
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
    /**
     * Gets the list of saved games
     * @return a list of saved games
     */
    public String[] returnSavedGames(){
        String[] file_names = new String[0];
        ArrayList<String> myList = new ArrayList<String>(Arrays.asList(file_names));

        try{
            File f = new File("saved_games.txt");
            Scanner scanner = new Scanner(f);

            while (scanner.hasNextLine()) {
                String name = scanner.nextLine();
                myList.add(name);
            }
        } catch(Exception e){

        }

        file_names = myList.toArray(file_names);
        return file_names;
    }
    /**
     * Loads a saved game
     * @param filename the game to be loaded
     */
    public void loadGame(String filename){
        //int[][] newBoard = new int[8][8];
        Pieces[][] newBoard = new Pieces[8][8];
        int currentPlayer = -1;
        try{
            FileReader fr = new FileReader(filename);
            currentPlayer = Character.getNumericValue(fr.read());
            fr.read();
            for(int i = 0; i < 8; i++) {
                for (int k = 0; k < 8; k++) {
                    int piece = Character.getNumericValue(fr.read());
                    if(piece != -1 || piece != 0){
                        //newBoard[i][k] = piece;
                        newBoard[i][k] = new CheckersPiece(piece, i, k);
                    }else if (piece == 0){
                        newBoard[i][k] = null;
                    }
                }
                fr.read();
            }
            fr.close();
            data.loadBoard(newBoard);
            this.board.loadGame(currentPlayer, newBoard);
        } catch(Exception e) {

        }
    }

    public Data getData(){
        return data;
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
