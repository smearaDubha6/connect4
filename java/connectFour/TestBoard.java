package connectFour;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestBoard {

	@BeforeAll
	/*
	 * have a fresh board for each set of tests
	 */
	public static void clearBoard() {
		Board b1 = new Board();
		b1.clearBoard();
	}
	
	/*
	 * pass valid and out of bound choices to the board
	 */
	@Test
	void checkChoices() {
		
		Board b1 = new Board();		
		
		boolean isValidChoice;
		isValidChoice = b1.isValidChoice(100);
		assertEquals(false,isValidChoice);
		
		isValidChoice = b1.isValidChoice(-100);
		assertEquals(false,isValidChoice);
		
		// check choice just below lower limit
		isValidChoice = b1.isValidChoice(0);
		assertEquals(false,isValidChoice);
		
		// check choice just above upper limit
		isValidChoice = b1.isValidChoice(Board.NUM_COLS + 1);
		assertEquals(false,isValidChoice);
		
		// valid choice
		isValidChoice = b1.isValidChoice(1);
		assertEquals(true,isValidChoice);
	}
	
	/*
	 * mimic a vertical winner
	 */
	@Test
	void verticalWinner() {
		
		Board b1 = new Board();
		//b1.clearBoard();
		
		Slot slot;
		boolean winner;
		
		// 
		slot = b1.recordMove(1, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(1, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(1, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(1, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(1, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,true);
	}
	
	/*
	 * mimic a horizontal winner
	 */
	@Test
	void horizontalWinner() {
		Board b1 = new Board();
		
		Slot slot;
		boolean winner;
	
		slot = b1.recordMove(1, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		
		slot = b1.recordMove(2, Board.YELLOW);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(2, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		
		slot = b1.recordMove(3, Board.YELLOW);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(3, Board.YELLOW);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(3, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		
		slot = b1.recordMove(4, Board.YELLOW);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(4, Board.YELLOW);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(4, Board.YELLOW);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(4, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
	
		
		slot = b1.recordMove(5, Board.YELLOW);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(5, Board.YELLOW);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(5, Board.YELLOW);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(5, Board.YELLOW);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(5, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,true);
	}
	
	/*
	 * produce a diagonal winner
	 */
	@Test
	void diagonalWinner() {
		Board b1 = new Board();
		
		Slot slot;
		boolean winner;
	
		slot = b1.recordMove(1, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(2, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(3, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(4, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,false);
		
		slot = b1.recordMove(5, Board.RED);
		assertNotSame(null,slot);
		winner = b1.checkForWinner(slot.xPos, slot.yPos);
		assertEquals(winner,true);
	}

	/*
	 * choose more slots than exist on a column
	 */
	@Test
	void overfillColumn() {
		Board b1 = new Board();
		Slot slot;
		
		for (int i=0; i<Board.NUM_ROWS; i++) {
			slot = b1.recordMove(1, Board.RED);
			assertNotSame(null,slot);
		}
		
		slot = b1.recordMove(1, Board.RED);
		assertEquals(null,slot);
		
	}
}
