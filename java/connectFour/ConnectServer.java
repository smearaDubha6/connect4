package connectFour;

import java.io.*;
import java.net.*;

/**
 * This application provides the server for the Connect 4 game. It will wait for
 * two clients to connect before starting the game and will not allow more than
 * two clients to connect. It allows the first client that connects to choose the
 * colour and take the first go 
 *
 * For each client that connects a new Thread is started which handles interaction
 * with the client
 *
 * @author Richie Duggan
 */
public class ConnectServer {

    public static int numPlayers = 0;

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("You need to pass port to connect to, eg :" + 
            		"`java ConnectServer 1234`");
            return;
        }

        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException exception) {
            System.out.println("You didn't specify a valid port");
            return;
        }
        
        // initialise game board
        Board gameBoard = new Board();

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Server is listening on port " + port);

            Socket socket = serverSocket.accept();
            System.out.println("Player 1 connected");
            ConnectThread t1 = new ConnectThread(socket,gameBoard,new Player());
            t1.start();

            numPlayers++;

            socket = serverSocket.accept();

            if (ConnectThread.gameInterrupted) {
                return;
            }

            System.out.println("Player 2 connected");
            ConnectThread t2 = new ConnectThread(socket,gameBoard,new Player());
            t2.start();

            numPlayers++;

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

}