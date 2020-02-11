package connectFour;

/**
 * This class represents a player in the game.
 * @author Richie Duggan
 */
public class Player {

    private int id;
    private String name;
    private int colour;
    private boolean winner;

    private static int numPlayers;

    public Player() {
        numPlayers++;
        this.id = numPlayers;
        this.name = "";
        this.winner = false;
    }

    public Player(String name) {
        numPlayers++;
        this.id = numPlayers;
        this.name = name;
        this.winner = false;
    }

    public Player(String name, int colour) {
        numPlayers++;
        this.id = numPlayers;
        this.name = name;
        this.colour = colour;
        this.winner = false;
    }

    public void setName(String newName) {
        name = newName;
    }

    public void setColour(int newColour) {
        colour = newColour;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getColour() {
        return colour;
    }
    
    public boolean getWinner() {
    	return winner;
    }
    
    public void setWinner(boolean newWinner) {
    	winner = newWinner;
    }
 
    public int getNumPlayers() {
        return numPlayers;
    }
}