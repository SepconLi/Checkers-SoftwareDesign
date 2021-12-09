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
    
    }
    /**
     * Gets the total number of players
     * @return The number of players
     */
    public int getNoOfPlayers() {
        return boardType.getNoOfPlayers();
    }
    
    /**
     * Creates a new game
     */
    private void newGame() {
        board.newGame(table,this);
    }
    /**
     * Makes a move creating a communication between Visual and intern data
     * @param move The move to be executed
     * @return The modified board
     */
    public Pieces[][] makeMove(movesMade move) {
        data.makeMove(move);
        return data.getBoard();
    }
    /**
     * Gets the actual state of the board
     * @return The actual board
     */
    public Pieces[][] getBoard() {
        return data.getBoard();
    }
    /**
     * Gets the legal possible moves for a specific player
     * @param player The player to have its moves calculated
     * @return An array of moves that are legal for the given player
     */
    public movesMade[] getMovesFrom(int player) {
        movesMade[] result = data.getLegalMoves(player);
        //System.out.println(result.length);
        return result;
    }
    /**
     * Gets the legal Jumps for a specific player (Special move that removes opposite player piece)
     * @param player The piece whose jumps are going to be calculated 
     * @param row The row of the piece to be checked
     * @param col The col of the piecce to be checked
     * @return An array of jumps that the player can possibly do
     */
    public movesMade[] getJumpsFrom(int player,int row, int col) {
        return data.getLegalJumpsFrom(player, row, col);
    }
    /**
     * Communicates visual with data to calculate how many pieces a player has lost
     * @param player The player whose lost pieces are going to be calculated
     * @return The total pieces that the given player has lost
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
            board.instructions(boardType.getInstructions());
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
        Pieces[][] newBoard = boardType.loadBoard(filename);
        int currentPlayer = boardType.getPlayer();
        data.loadBoard(newBoard);
        this.board.loadGame(currentPlayer, newBoard);
    }
    /**
     * Returns an instance of data
     * @return an instance of data that is being used
     */
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
