package connectFour;

import java.io.*;
import java.net.*;

/**
 * This class handles interaction with a connected client to the game
 * It will send instructions to the client and process responses.
 * It sends messages to the Board class to make moves requested by the user
 * and to check if the game is over
 *
 * @author Richie Duggan
 */
public class ConnectThread extends Thread {
    private Socket socket;
    private Board board;
    private Player player;

    // record who has the current go
    public static int currentGo = 1;

    // create some variables to record various states of the game
    private static volatile boolean winner = false;
    
    // record the winner of the game for display
    private static String winningName = "";

    private static volatile int colourPicked = -1;
    private static final Object colourChosenLock = new Object();

    // the following variable is to record issues when clients disconnects.
    // The game is considered over when this happens
    public static volatile boolean gameInterrupted = false;
    private static final Object gameInterruptedLock = new Object();

    public ConnectThread(Socket socket, Board board,Player player) {
        this.socket = socket;
        this.board = board;
        this.player = player;
    }

    public void run() {
    	
        String userText = "";
        int colChoice = -1;
        Slot pos;

        String colourChosen = "";
        int colourChosenInt = 0;
        int colourIndex = 0;

        String player_name;
    	
        try {
            // set up input and output streams
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            
            writer.println(board.welcomeMessage1(player));
            writer.flush();

            player_name = reader.readLine();

            if (gameInterrupted) {
                // this can be caused from another thread being stopped
                socket.close();
                return;
            }

            if (player_name == null) {
                // this indicates client closed the connection.
                // Close the thread
                System.out.println("killing thread");

                synchronized(gameInterruptedLock) {
                    gameInterrupted = true;
                }
                socket.close();
                return;
            }

            player.setName(player_name);

            try {
                if (player.getId() == 1) {
                    // first player, we let this player choose the colour

                    while (colourIndex == 0) {
                        writer.println(board.welcomeMessage2(player.getName()));
                        writer.flush();
                        colourChosen = reader.readLine();

                        if (gameInterrupted) {
                            socket.close();
                            return;
                        }

                        try {
                            colourChosenInt = Integer.parseInt(colourChosen);

                            if (colourChosenInt == 1  || colourChosenInt == 2) {
                                colourIndex = colourChosenInt;
                                player.setColour(colourIndex);

                                synchronized(colourChosenLock) {
                                    colourPicked = colourIndex;
                                }
                            }
                        } catch (NumberFormatException exception) {
                            if (colourChosen == null) {
                                // this indicates the client has disconnected.
                                // We should kill the thread
                                System.out.println("killing thread");

                                synchronized(gameInterruptedLock) {
                                    gameInterrupted = true;
                                }
                                socket.close();
                                return;
                            }
                        }
                    }
                } else {
                    // second player
                    // we need to wait for the first player to pick a colour. Once
                    // that is done we will give the second player the other colour

                    while (colourPicked == -1) {
                        try {
                            if (gameInterrupted) {
                                socket.close();
                                return;
                            }
                            // TODO :  it would be more efficient to use observers
                            //instead of this
                            Thread.sleep(1000);
                            
                        } catch (InterruptedException e) {
                        	throw new RuntimeException("Unexpected interrupt", e);
                        }
                    }

                    if (colourPicked == Board.RED) {
                        player.setColour(Board.YELLOW);
                    } else {
                        player.setColour(Board.RED);
                    }
                }
            } catch (IOException ex) {
                System.out.println("Server exception: " + ex.getMessage());
                ex.printStackTrace();
                socket.close();
                return;
            }

            boolean validChoice;

            // don't show player 1 the board until player 2 joins
            while (player.getNumPlayers() < 2) {
                try {
                    if (gameInterrupted) {
                        socket.close();
                        return;
                    }
                    // TODO :  it would be more efficient to use observers
                    //instead of this
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                	throw new RuntimeException("Unexpected interrupt", e);
                }

            }

            while (!winner) {
                validChoice = false;
                if (currentGo == player.getId()) {

                    while (!validChoice) {
                        try {
                        	writer.println(board.boardMessage(player));               
                            writer.flush();
                            userText = reader.readLine();

                            // make sure it is a valid number
                            colChoice = Integer.parseInt(userText);
                            validChoice = board.isValidChoice(colChoice);

                        } catch (NumberFormatException exception) {
                            if (userText == null) {
                                // this indicates client has closed the connection.
                                // kill the thread
                                synchronized(gameInterruptedLock) {
                                    gameInterrupted = true;
                                }
                                socket.close();
                                return;
                            }
                        }
                    }

                    // record the player's choice
                    pos = board.recordMove(colChoice,player.getColour());                
                    // check if we now have a winner
                    winner = board.checkForWinner(pos.xPos, pos.yPos);

                    if (winner) {
                    	player.setWinner(true);    
                    	winningName = player.getName();                 
                    }

                    if (player.getId() == 1) {
                        currentGo = 2;
                    } else {
                        currentGo = 1;
                    }
                } else {
                    try {
                        if (gameInterrupted) {
                            socket.close();
                            return;
                        }

                        // TODO :  it would be more efficient to use observers
                        //instead of this
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    	throw new RuntimeException("Unexpected interrupt", e);
                    }
                }
            }

            // Game is over. Show results
            writer.println(board.winningMessage(player, winningName));
            writer.flush();
            socket.close();
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
