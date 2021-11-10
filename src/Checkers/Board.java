package Checkers;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.*;

class Board extends JPanel implements ActionListener, MouseListener { // Board class beings, extends on JPanel class

    Data board; // declares new Data class to store the game's information
    boolean gameInProgress; // boolean to check if game is in progress
    int currentPlayer; // tracks whose turn it is
    int selectedRow, selectedCol; // tracks which squares have been selected
    movesMade[] legalMoves; // declares new movesMade array
    JLabel title; // title JLabel on frame
    JButton newGame; // newGame JButton on frame - starts a new game
    JButton howToPlay; // howToPlay JButton on frame - gives intro to Checkers and how to play
    JButton credits; // credits JButton on frame - displays credits
    JButton saveGame;
    JLabel message; // message JLabel on frame - indicates whose turn it is
    String Player1; // first player's name
    String Player2; // second player's name
    private static final String GAMES_PATH = "saved_games.txt"; //quité el src/
    private static final String BASE_PATH = "game_";

    public Board() { // default constructor

        addMouseListener(this); // implements Mouse Listener

        // assigns all JLabels and JButtons to their values, as well as styles them
        title = new JLabel("Checkers!");
        title.setFont(new Font("Serif", Font.CENTER_BASELINE, 50));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.darkGray);
        howToPlay = new JButton("Rules");
        howToPlay.addActionListener(this);
        newGame = new JButton("New Game");
        newGame.addActionListener(this);
        credits = new JButton("Credits");
        credits.addActionListener(this);
        saveGame = new JButton("Save Game");
        saveGame.addActionListener(this);
        message = new JLabel("", JLabel.CENTER);
        message.setFont(new Font("Serif", Font.BOLD, 14));
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setForeground(Color.darkGray);

        board = new Data(); // assigns to new Data class
        getPlayersColors(); // calls to get players' names
        NewGame(); // calls to start a new game

    }

    /** 
     * implemented from Actions Listener, assigns functions to the buttons
     * @param evt the event that was requested (from the buttons)
     * 
    */
    public void actionPerformed(ActionEvent evt) {

        Object src = evt.getSource();
        if (src == newGame) {//if newGame button is pressed, a new game is created
            getPlayersColors();
            NewGame();
        }else if( src == saveGame ){
            if(save_game(this)){
                System.out.println("GAME SAVED SUCCESFULLY!");
            }else{
                System.out.println("Oops! Something went wrong :( ");
            }
        }else if (src == howToPlay) {//if howToPlay button is pressed, instructions pop up
            instructions();
        }else if (src == credits){ //if credits button is pressed, credits pop up
            showCredits();
        }
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
     * When the board is clicked
     * 
     * @param evt event of the mouse being clicked
     * 
     */
    public void mousePressed(MouseEvent evt) {

        if (!gameInProgress) { // if game is not in progress
            message.setText("Start a new game."); // indicates to start a new game
        } else { // otherwise, calculates which square was pressed
            int col = (evt.getX() - 4) / 80; // calculation of square's column
            int row = (evt.getY() - 4) / 80; // calculation of square's row
            if (col >= 0 && col < 8 && row >= 0 && row < 8) // if square is on the board
                ClickedSquare(row, col); // calls ClickedSquare
        }
    }

    /**
     * processes legal moves
     * 
     * @param row row to be checked
     * @param col column to be checked
     */
    void ClickedSquare(int row, int col) {

        for (int i = 0; i < legalMoves.length; i++) { // runs through all legal moves
            if (legalMoves[i].fromRow == row && legalMoves[i].fromCol == col) { // if selected piece can be moved
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
            if (legalMoves[i].fromRow == selectedRow && legalMoves[i].fromCol == selectedCol // if already selected
                                                                                             // piece can move
                    && legalMoves[i].toRow == row && legalMoves[i].toCol == col) { // and the selected piece's
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
            legalMoves = board.getLegalJumpsFrom(currentPlayer, move.toRow, move.toCol);
            if (legalMoves != null) { // if player must jump again
                if (currentPlayer == Data.player1)
                    message.setText(Player1 + ", you must jump."); // indicates that player 1 must jump
                else
                    message.setText(Player2 + ", you must jump."); // indicates that player 2 must jump
                selectedRow = move.toRow; // assigns selected row to destination row
                selectedCol = move.toCol; // assigns selected column to destination column
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
                if (legalMoves[i].fromRow != legalMoves[0].fromRow // if there are any legal moves besides the selected
                                                                   // row
                        || legalMoves[i].fromCol != legalMoves[0].fromCol) { // and column
                    sameFromSquare = false; // declares sameFromSquare as false
                    break;
                }
            if (sameFromSquare) { // if true, the player's final piece is already selected
                selectedRow = legalMoves[0].fromRow;
                selectedCol = legalMoves[0].fromCol;
            }
        }
        
        if(gameInProgress) {
            repaint(); // repaints board
        }
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
                g.drawRect(4 + legalMoves[i].fromCol * 80, 4 + legalMoves[i].fromRow * 80, 78, 78);
            }

            if (selectedRow >= 0) { // if a square is selected
                g.setColor(Color.white); // the square is highlighted in white
                g.drawRect(4 + selectedCol * 80, 4 + selectedRow * 80, 78, 78);
                g.drawRect(6 + selectedCol * 80, 6 + selectedRow * 80, 74, 74);
                g.setColor(Color.green);
                for (int i = 0; i < legalMoves.length; i++) { // its legal moves are then highlighted in green
                    if (legalMoves[i].fromCol == selectedCol && legalMoves[i].fromRow == selectedRow)
                        g.drawRect(4 + legalMoves[i].toCol * 80, 4 + legalMoves[i].toRow * 80, 78, 78);
                }
            }
        }
    }

    // implements Mouse entered, clicked, released and exited
    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseClicked(MouseEvent evt) {
    }

    public void mouseReleased(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }

}
