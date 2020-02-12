package connectFour;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the board of the Connect 4 game. It handles recording
 * user choices and checking for collisions and has the logic to check for the
 * game winner. It also returns the various board related messages to be sent
 * back to the client 
 *
 * @author Richie Duggan
 */
public class Board {
	
    public static final int NUM_ROWS = 6;
    public static final int NUM_COLS = 9;
    public static final int WINNING_SCORE = 5;
    
    // create 2D array that records state of game
    private  char[][] board = new char[NUM_ROWS][NUM_COLS];
    // record how many spaces have been filled in each column
    private  int[] boardColIndex = new int[NUM_COLS];
    
    public static final int YELLOW = 1;
    public static final int RED = 2;
    
    private final static Map < Integer, String > colourTextMapping = new HashMap < > () {
        {
            put(YELLOW, "yellow");
            put(RED, "red");
        }
    };

    private final static Map < Integer, Character > numberMapping = new HashMap < > () {
        {
            put(YELLOW, 'x');
            put(RED, 'o');
        }
    };
    
    public Board() {
    }
    
    /**
     * 
     * @return a String representation of the board
     */
    @Override
    public String toString() {
        StringBuilder currentBoard = new StringBuilder();

        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
                if (board[i][j] == 0) {
                    currentBoard.append("[ ]");
                } else {
                    currentBoard.append("[" + board[i][j] + "]");
                }
            }
            // TODO : see if you can improve this by getting client to
            // be able to handle more than one line for each response
            currentBoard.append("NEWLINE");
        }
        return currentBoard.toString();
    }

    /**
     * clears the board.
     * currently only used for testing purposes
     */
    public void clearBoard() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLS; j++) {
            	board[i][j] = 0;
            	boardColIndex[j] = 0;
            }
    	}
    	
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return boolean outlining if we have a winner
     */
    public boolean checkForWinner(int x, int y) {
    	
        int numConsecutive = 0;

	// TODO : add return statements whenever the value of 
	// haveWinner changes to true as we don't need to do
	// any more checks when this happens
        boolean haveWinner = false;

        // check verticals
        // only need to check the column that changed
        numConsecutive = 0;
        for (int i = 0; i < NUM_ROWS; i++) {
            if (board[i][y] == board[x][y]) {
                 numConsecutive++;
            } else {
                numConsecutive = 0;
            }
            
            if (numConsecutive == WINNING_SCORE) {
               haveWinner = true;
            }
        }

        // check horizontals
        // only need to check the row that changed
        numConsecutive = 0;

        for (int i = 0; i < NUM_COLS; i++) {
        	if (board[x][i] == board[x][y]) {
                 numConsecutive++;
            } else {
                 numConsecutive = 0;
            }
        	
            if (numConsecutive == WINNING_SCORE) {
               haveWinner = true;
            }
        }

        // check diagonals. For these focus on where the update has been
        // made and check relevant diagonals in this area

        numConsecutive = 1;

        // left to right diagonal. 
        // Check to the left first and then the right.
        // make sure it is a valid position to check too
        // left
        int i = x - 1;
        int j = y - 1;

        while (i >= 0 && j >= 0) {
        	if (board[i][j] != 0 &&
        			board[i][j] == board[x][y]) {
        		numConsecutive++;
        		if (numConsecutive == WINNING_SCORE) {
        			haveWinner = true;
        		} 
        	} else {
        		break;
        	}
            i--;
            j--;
        }

        // right
        i = x + 1;
        j = y + 1;

        while (i < NUM_ROWS && j < NUM_COLS) {
        	if (board[i][j] != 0 &&
        			board[i][j] == board[x][y]) {
                numConsecutive++;
                if (numConsecutive == WINNING_SCORE) {
                    haveWinner = true;
                }
            } else {
                break;
            }
            i++;
            j++;
        }

        numConsecutive = 1;

        // right to left diagonal.
        // Check to the right first and then the left.
        // make sure it is a valid position to check too.
        // right
        i = x - 1;
        j = y + 1;

        while (i >= 0 && j < NUM_COLS) {
        	if (board[i][j] != 0 &&
        			board[i][j] == board[x][y]) {
                numConsecutive++;
                if (numConsecutive == WINNING_SCORE) {
                    haveWinner = true;
                }
            } else {
                break;
            }
            i--;
            j++;
        }

        // left
        i = x + 1;
        j = y - 1;

        while (i < NUM_ROWS && j >= 0) {
        	if (board[i][j] != 0 &&
        			board[i][j] == board[x][y]) {
                numConsecutive++;
                if (numConsecutive == WINNING_SCORE) {
                    haveWinner = true;
                }
            } else {
                break;
            }
            i++;
            j--;
        }

        if (haveWinner) {
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @param colChoice
     * @return boolean indicating if slot is free
     */
    public boolean isValidChoice(int colChoice) {
    	if (colChoice >= 1 && colChoice <= NUM_COLS && boardColIndex[colChoice - 1] < NUM_ROWS) {
            return true;
        }
    	return false;
    }

    /**
     * record the player's choice of column
     * 
     * @param colChoice
     * @param marker
     * @return the slot that the piece was given
     */
    public Slot recordMove(int colChoice,int colour) {
    	
    	if (isValidChoice(colChoice)) {	
    		char symbol = numberMapping.get(colour);
    		int x = (NUM_ROWS - 1) - boardColIndex[colChoice - 1];
    		int y = colChoice - 1;
       
    		board[x][y] = symbol;
        	boardColIndex[y]++;
        
        	return new Slot(x,y);
    	} else {
    		return null;
    	}
    }
    
    public String welcomeMessage1(Player player) {
    	StringBuilder msg = new StringBuilder("Welcome to the game! You need to connect " + this.WINNING_SCORE + " pieces to win. ");

        if (player.getId() == 1) {
            msg.append("Please enter your name : ");
        } else {
        	msg.append("We will show you the board when the first player makes his first choice. Please enter your name : ");
        }
        
        return msg.toString();
    }
    
    public String welcomeMessage2(String player_name) {
    	String msg = player_name + ", we will show you the board when another player joins. " + 
    			"Please choose a colour. Use " + YELLOW + " for " + 
    			colourTextMapping.get(YELLOW) + " and " + RED + " for " + 
    			colourTextMapping.get(RED) + " : ";
    	return msg;
    }
    
    public String boardMessage(Player player) {
    	String msg = this.toString() + player.getName() + ",here is board above. You" +
    			" are colour " + colourTextMapping.get(player.getColour()) +
    			" represented as '" + numberMapping.get(player.getColour()) + 
    			"' on the board. Pick a column (1-" + NUM_COLS + ") that has free space : ";
    	return msg;
    }
    
    public String winningMessage(Player player,String winningName) {

        StringBuilder resultText = new StringBuilder();
        
        if (player.getWinner()) {
            resultText.append("You are the winner " + player.getName() + "!!");
        } else {
            resultText.append("The game is over, " + winningName + " won. " +
                "Thank you for playing.");
        }

        // TODO : see if you can improve this by getting client to be able
        // to handle more than one line for each response
        return(resultText.toString() + " Final board is : NEWLINE" + this.toString() + "exit");
    }
}
