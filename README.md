# connect4
This repo provides an implementation of the Connect 4 Game.  It is currently configured to check for 5 instead of 4 pieces.
It has nine columns and six rows. All of this can be changed in Board.java as desired.

It is a text-based client-server implementation.

To start the server, pass the port to listen for requests in the command-line, for example :

*java connectFour.ConnectServer 1234*

To start a client, pass the host name and port to connect to (which should match the server above) :

*java connectFour.ConnectClient localhost 1234*

The server will allow at most 2 clients to connect.

JUnit test classes have also been provided. 
