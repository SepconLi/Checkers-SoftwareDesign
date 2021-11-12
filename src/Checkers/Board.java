package Checkers;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class Board extends JPanel{ // Board class beings, extends on JPanel class

    private Data board; // declares new Data class to store the game's information
    private boolean gameInProgress; // boolean to check if game is in progress
    private int currentPlayer; // tracks whose turn it is
    private int selectedRow, selectedCol; // tracks which squares have been selected
    private movesMade[] legalMoves; // declares new movesMade array
    private JLabel title; // title JLabel on frame
    private JButton saveGame;
    private JButton loadGame;
    private JButton newGame; // newGame JButton on frame - starts a new game
    private JButton howToPlay; // howToPlay JButton on frame - gives intro to Checkers and how to play
    private JButton credits; // credits JButton on frame - displays credits
    private JLabel message; // message JLabel on frame - indicates whose turn it is
    private String Player1; // first player's name
    private String Player2; // second player's name

    public Board() { // default constructor

        // assigns all JLabels and JButtons to their values, as well as styles them
        title = new JLabel("Checkers!");
        title.setFont(new Font("Serif", Font.CENTER_BASELINE, 50));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.darkGray);
        howToPlay = new JButton("Rules");
        newGame = new JButton("New Game");
        credits = new JButton("Credits");
        saveGame = new JButton("Save Game");
        loadGame = new JButton("Load Game");
        message = new JLabel("", JLabel.CENTER);
        message.setFont(new Font("Serif", Font.BOLD, 14));
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setForeground(Color.darkGray);

        board = new Data(); // assigns to new Data class
        getPlayersColors(); // calls to get players' names
        NewGame(); // calls to start a new game

    }
    
    /** 
     * getter of the title atribute
     * @return return the title atribute
     * 
     */
    JLabel getTitle() {
        return title;
    }
    
    /** 
     * getter of the title atribute
     * @return return the title atribute
     * 
     */
    JButton getNewGame() {
        return newGame;
    }
    
    /** 
     * getter of the howToPlay atribute
     * @return return the howToPlay atribute
     * 
     */
    JButton getHowToPlay() {
        return howToPlay;
    }
    
    /** 
     * getter of the credits atribute
     * @return return the credits atribute
     * 
     */
    JButton getCredits() {
        return credits;
    }

    JButton getSave() {
        return saveGame;
    }

    JButton getLoad() {
        return loadGame;
    }
    /** 
     * getter of the message atribute
     * @return return the message atribute
     * 
     */
    JLabel getMessage() {
        return message;
    }

    
    /** 
     * getter of the message atribute
     * @return return the message atribute
     * 
     */
    boolean getGameInProgress() {
        return gameInProgress;
    }
    
    /**
     * creates new game
     * 
     */
    void NewGame() {

        board.setUpBoard(); // sets up board
        currentPlayer = Data.player1; // indicates its player 1's move
        legalMoves = board.getLegalMoves(Data.player1); // searches for legal moves
        selectedRow = -1; // no square is selected
        message.setText("It's " + Player1 + "'s turn."); // indicates whose turn it is
        gameInProgress = true; // sets gameInProgress as true
        newGame.setEnabled(true); // enables newGame button
        howToPlay.setEnabled(true); // enables howToPlayButton
        credits.setEnabled(true); // enables credits button
        repaint(); // repaints board

    }

    /**
     * gets players colors through JOptionPane
     */
    public void getPlayersColors() {
        JTextField player1Name = new JTextField("White");
        JTextField player2Name = new JTextField("Black");
        JLabel msg = new JLabel("Enter Your Names In Your Desired Color!\n");
        //creates new JPanel to store the JTextFields
        JPanel getNames = new JPanel();
        getNames.setLayout(new BoxLayout(getNames, BoxLayout.PAGE_AXIS));
        getNames.add(msg);
        getNames.add(player1Name);
        getNames.add(player2Name);

        //player inputs name through Confirm Dialog
        int result = JOptionPane.showConfirmDialog(null, getNames, "Names", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        
        if (result == JOptionPane.OK_OPTION) { //if players give names, names are assigned
            Player1 = player1Name.getText() + " - White";
            Player2 = player2Name.getText() + " - Black";
            if(Player1.compareTo("White - White") == 0) {
                Player1 = "Player 1 - White";
            }
            if(Player2.compareTo("Black - Black") == 0) {
                Player2 = "Player 2 - Black";
            }
        } else { //otherwise default names are given
            Player1 = "Player 1 - White";
            Player2 = "Player 2 - Black";
        }
    }

    /**
     * when howToPlay button is pressed, instruction Message Dialog appears
     */
    void instructions() {

        String intro = "Rules: \n"
                + "* Each player has a total of 12 pieces, the first player to eliminate all the opponent's pieces is the winner.\n"
                + "* Pieces can only be moved one square diagonally forward, either to the left or right if it's possible. \n"
                + "* When a piece reaches the end of the board, it is crowned. A crowned piece has the option of also moving backwards.\n"
                + "* To eliminate an opponent's piece, one of your pieces must be diagonal to it and it must be possible to place your piece behind your opponent's.\n"
                + "If these conditions are met, it is mandatory to eliminate your opponent's piece. It is possible to do more than one elimination if it after\n"
                + "eliminating one piece meets the same conditions with another, which you will also be obliged to eliminate.";

        JOptionPane.showMessageDialog(null, intro, "What is Checkers", JOptionPane.PLAIN_MESSAGE); // shows message

    }

    /**
     * when credits button is pressed, credits Message Dialog appears
     */
    void showCredits() {

        String credits = "Universidad de Costa Rica - CI-0136 - 2021\n"
        +"Rodrigo Li Qiu B94263\n"
        + "Jorim G. Wilson Ellis B98615\n"
        + "Asdrúbal Villegas Molina B88583\n" 
        + "Axel Matus Vargas B74597\n"
        + "And also:"
        + "Toby Thomas\n" + "in 01/23/14"; // credits of game
        JOptionPane.showMessageDialog(null, credits, "Credits", JOptionPane.PLAIN_MESSAGE); // shows message

    }

    public boolean loadGame(String filename){
        try{
            int[][] newBoard = new int[8][8];
            FileReader fr = new FileReader(filename);
            currentPlayer = fr.read(); //Valor del jugador que le toca
            for(int i = 0; i < 8; i++) {
                for (int k = 0; k < 8; k++) {
                    newBoard[i][k] = fr.read();
                }
            }
            fr.close();
        } catch(Exception e) {

        }

        return true;
    }

    void showGames() {
        //TODO: Mostrar una lista con los games
    }
    /**
     * when game is over
     * 
     * @param str the string of the game winner
     */
    void gameOver(String str) {

        message.setText("GAME OVER! " + str); // indicates who
        // Game over message
        JLabel gameOverLabel = new JLabel();
        gameOverLabel.setText("GAME OVER! " + str + " Close this window and press 'new game!' for a new start!");
        gameOverLabel.setVisible(true);
        JPanel gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.PAGE_AXIS));
        gameOverPanel.add(gameOverLabel);
        JOptionPane.showConfirmDialog(null, gameOverPanel, "GAME OVER", JOptionPane.CLOSED_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        message.setText(str); // indicates who won
        //newGame.setEnabled(true); // enables newGame button
        //howToPlay.setEnabled(true); // enables howToPlayButton
        //credits.setEnabled(true); // enables credits button
        gameInProgress = false; // sets gameInProgress as false, until new game is initialized

    }

    /**
     * processes legal moves
     * 
     * @param row row to be checked
     * @param col column to be checked
     */
    void ClickedSquare(int row, int col) {

        for (int i = 0; i < legalMoves.length; i++) { // runs through all legal moves
            if (legalMoves[i].getFromRow() == row && legalMoves[i].getFromCol() == col) { // if selected piece can be moved
                selectedRow = row; // assigns selected row
                selectedCol = col; // assigns selected column
                if (currentPlayer == Data.player1) // indicates whose turn it is
                    message.setText("It's " + Player1 + "'s turn.");
                else
                    message.setText("It's " + Player2 + "'s turn.");
                repaint(); // repaints board
                return;
            }
        }

        if (selectedRow < 0) { // if no square is selected
            message.setText("Select a piece to move."); // indicates player to pick a piece to move
            return;
        }

        for (int i = 0; i < legalMoves.length; i++) { // runs through all legal moves
            if (legalMoves[i].getFromRow() == selectedRow && legalMoves[i].getFromCol() == selectedCol // if already selected
                                                                                             // piece can move
                    && legalMoves[i].getToRow() == row && legalMoves[i].getToCol() == col) { // and the selected piece's
                                                                                   // destination is legal
                MakeMove(legalMoves[i]); // make the move
                return;
            }
        }

        // when a piece is selected and player clicks elsewhere besides legal
        // destination, program encourages player to move piece
        message.setText("Where do you want to move it?");

    }

    /**
     * moves the piece
     * 
     * @param move the movement to be executed
     */
    void MakeMove(movesMade move) {

        board.makeMove(move); // calls makeMove method in Data class

        if (move.isJump()) { // checks if player must continue jumping
            legalMoves = board.getLegalJumpsFrom(currentPlayer, move.getToRow(), move.getToCol());
            if (legalMoves != null) { // if player must jump again
                if (currentPlayer == Data.player1)
                    message.setText(Player1 + ", you must jump."); // indicates that player 1 must jump
                else
                    message.setText(Player2 + ", you must jump."); // indicates that player 2 must jump
                selectedRow = move.getToRow(); // assigns selected row to destination row
                selectedCol = move.getToCol(); // assigns selected column to destination column
                repaint(); // repaints board
                return;
            }
        }

        if (currentPlayer == Data.player1) { // if it was player 1's turn
            currentPlayer = Data.player2; // it's now player 2's
            legalMoves = board.getLegalMoves(currentPlayer); // gets legal moves for player 2
            if (legalMoves == null) // if there aren't any moves, player 1 wins
                gameOver(Player1 + " wins!");
            else if (legalMoves[0].isJump()) // if player 2 must jump, it indicates so
                message.setText(Player2 + ", you must jump.");
            else // otherwise, it indicates it's player 2's turn
                message.setText("It's " + Player2 + "'s turn.");
        } else { // otherwise, if it was player 2's turn
            currentPlayer = Data.player1; // it's now player 1's turn
            legalMoves = board.getLegalMoves(currentPlayer); // gets legal moves for player 1
            if (legalMoves == null) // if there aren't any moves, player 2 wins
                gameOver(Player2 + " wins!");
            else if (legalMoves[0].isJump()) // if player 1 must jump, it indicates so
                message.setText(Player1 + ", you must jump.");
            else // otherwise, it indicates it's player 1's turn
                message.setText("It's " + Player1 + "'s turn.");
        }

        selectedRow = -1; // no squares are not selected

        if (legalMoves != null) { // if there are legal moves
            boolean sameFromSquare = true; // declares boolean sameFromSquare
            for (int i = 1; i < legalMoves.length; i++) // runs through all legal moves
                if (legalMoves[i].getFromRow() != legalMoves[0].getFromRow() // if there are any legal moves besides the selected
                                                                   // row
                        || legalMoves[i].getFromCol() != legalMoves[0].getFromCol()) { // and column
                    sameFromSquare = false; // declares sameFromSquare as false
                    break;
                }
            if (sameFromSquare) { // if true, the player's final piece is already selected
                selectedRow = legalMoves[0].getFromRow();
                selectedCol = legalMoves[0].getFromCol();
            }
        }
        
        if(gameInProgress) {
            repaint(); // repaints board
        }
    }
    //to string board data to save game
    public String to_String(){
        String game_info;
        game_info = currentPlayer + "\n";
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                game_info += board.getBoard()[row][col];
            }
            game_info += "\n";
        }
        return game_info;
    }
    

    /**
     * paints board
     */
    public void paintComponent(Graphics g) {
        if(gameInProgress){
        // boarder around game board
        g.setColor(new Color(139, 119, 101));
        g.fillRect(0, 0, 648, 648);

        // creates checkered effect
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                // paints squares
                if (row % 2 == col % 2)
                    g.setColor(new Color(139, 119, 101));
                else
                    g.setColor(new Color(238, 203, 173));
                g.fillRect(4 + col * 80, 4 + row * 80, 80, 80);

                // paints squares with pieces on them
                switch (board.pieceAt(row, col)) {
                case Data.player1:
                    g.setColor(Color.lightGray);
                    g.fillOval(8 + col * 80, 8 + row * 80, 72, 72);
                    break;
                case Data.player2:
                    g.setColor(Color.darkGray);
                    g.fillOval(8 + col * 80, 8 + row * 80, 72, 72);
                    break;
                case Data.playerKing1:
                    g.setColor(Color.lightGray);
                    g.fillOval(8 + col * 80, 8 + row * 80, 72, 72);
                    g.setColor(Color.white);
                    g.drawString("K", 54 + col * 80, 72 + row * 80);
                    break;
                case Data.playerKing2:
                    g.setColor(Color.darkGray);
                    g.fillOval(8 + col * 80, 8 + row * 80, 72, 72);
                    g.setColor(Color.white);
                    g.drawString("K", 54 + col * 80, 72 + row * 80);
                    break;
                }
            }
        }
    }
        if (gameInProgress) { // if game is in progress

            g.setColor(new Color(0, 255, 0));
            for (int i = 0; i < legalMoves.length; i++) { // runs through all legal move
                // highlights, in green, all the possible squares the player can move
                // g.drawRect(2 + legalMoves[i].fromCol*80, 2 + legalMoves[i].fromRow*80, 39,
                // 39);
                g.drawRect(4 + legalMoves[i].getFromCol() * 80, 4 + legalMoves[i].getFromRow() * 80, 78, 78);
            }

            if (selectedRow >= 0) { // if a square is selected
                g.setColor(Color.white); // the square is highlighted in white
                g.drawRect(4 + selectedCol * 80, 4 + selectedRow * 80, 78, 78);
                g.drawRect(6 + selectedCol * 80, 6 + selectedRow * 80, 74, 74);
                g.setColor(Color.green);
                for (int i = 0; i < legalMoves.length; i++) { // its legal moves are then highlighted in green
                    if (legalMoves[i].getFromCol() == selectedCol && legalMoves[i].getFromRow() == selectedRow)
                        g.drawRect(4 + legalMoves[i].getToCol() * 80, 4 + legalMoves[i].getToRow() * 80, 78, 78);
                }
            }
        }
    }
}
