package connectFour;

import java.net.*;
import java.io.*;

/**
 * This application connects to a server which provides an implementation
 * of the Connect 4 game allowing a client to play against another client
 *
 * @author Richie Duggan
 */
public class ConnectClient {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("You need to pass the hostname and port to " +
                "connect to, eg 'java ConnectClient localhost 1234`");
            return;
        }

        String hostname = args[0];
        int port;

        try {
            port = Integer.parseInt(args[1]);
        } catch (NumberFormatException exception) {
            System.out.println("You didn't specify a valid port");
            return;
        }

        try (Socket socket = new Socket(hostname, port)) {

            // set up input and output streams for communication
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            Console console = System.console();

            StringBuilder userInput = new StringBuilder();
            String serverResponse = "";

            boolean gameOver = false;

            // begin the game by reading response from the server and asking 
            // the user to give the input asked for by the server. This continues
            // until the server sends the client a message containing 'exit'
            // which indicates that the game is over
            while (!gameOver) {

                serverResponse = reader.readLine();

                if (serverResponse == null) {
                    System.out.println("Game has finished prematurely");
                    socket.close();
                    return;
                }

                // TODO : could be more efficient to use a StringBuilder
                // instead of a String here
                serverResponse = serverResponse.replaceAll("NEWLINE", "\n");

                if (serverResponse.indexOf("exit") > -1) {
                    serverResponse = serverResponse.replace("exit", "");
                    System.out.println(serverResponse);
                    gameOver = true;
                } else {
                    userInput.setLength(0);
                    userInput.append(console.readLine(serverResponse));

                    writer.println(userInput.toString());
                    writer.flush();
                    System.out.println("Thank you");
                }

            }
            socket.close();


        } catch (UnknownHostException ex) {

            System.out.println("Server not found: " + ex.getMessage());

        } catch (IOException ex) {

            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}