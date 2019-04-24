import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

public class TetrisUnitTest {
  
  //dsfdfsfd

	// the motion of a square (does it move to the right location? does canMove work correctly)
	@Test
	public void canMoveTest() {
		Grid g = new Grid();
		Square s = new Square(g, 0, 0, Color.RED, true);
		Square s2 = new Square(g, 0, 9, Color.RED, true);
		for (int r = 0; r < Grid.HEIGHT; r ++) {
			for (int c = 0; c < Grid.WIDTH; c ++) {
				g.set(r, c, Color.BLUE);
			}
		}
		// Sets right square to empty, checks, and moves to the right
		// able to move because square is empty
		g.set(0, 1, Color.WHITE);
		assertTrue(s.canMove(Direction.RIGHT));
		s.move(Direction.RIGHT);
		
		// Sets left square to empty, checks, and moves to the left
		// able to move because square is empty
		g.set(0, 0, Color.WHITE);
		assertTrue(s.canMove(Direction.LEFT));
		s.move(Direction.LEFT);
		
		// Checks if square can move, unable to move because of a wall
		assertFalse(s.canMove(Direction.LEFT));
		
		// Sets below square to empty and checks if it can move
		g.set(1, 0, Color.WHITE);
		assertTrue(s.canMove(Direction.DOWN));
		
		// Sets below square to be occupied and checks if it can move,
		// cannot move because of an occupied square
		g.set(1, 0, Color.BLUE);
		assertFalse(s.canMove(Direction.DOWN));
		
		// Checks if square can move, unable to move because of a wall
		assertFalse(s2.canMove(Direction.RIGHT));
	}
	
	/**
	 * the motion of an L-shaped piece (does canMove work correctly? 
	 * starting from the left, 
	 * does it move all the way to the right with the right number of moves, 
	 * does the drop down feature work well?)
	 */
	@Test
	public void LShapeTest() {
		Grid g = new Grid();
		LShape l = new LShape(1, 0, g);
		Square[] lSquare = l.getSquare();
		
	    int counter = 0; 
			while (lSquare[0].getCol() != 8) {
				l.move(Direction.RIGHT);
	      counter += 1; 
			}
	    assertTrue(counter == 8);
		l.drop(l);
		assertTrue(lSquare[0].getRow() == 17);
		assertTrue(lSquare[1].getRow() == 18);
		assertTrue(lSquare[2].getRow() == 19);
		assertTrue(lSquare[3].getRow() == 19);
		assertTrue(lSquare[3].getCol() == 9);
	}
	
	// the removal of one or more rows within the grid.
	@Test
	public void checkRowsTest() {
		Grid g = new Grid();
		for (int r = 0; r < Grid.HEIGHT; r ++) {
			for (int c = 0; c < Grid.WIDTH; c ++) {
				g.set(r, c, Color.BLUE);
			}
		}
		g.checkRows();
		for (int r = 0; r < Grid.HEIGHT; r ++) {
			for (int c = 0; c < Grid.WIDTH; c ++) {
				assertFalse(g.isSet(r, c));
			}
		}
	}
	
/**
 * - test the rotation of all of the pieces.
 * - tests that check that the rotation goes as planned when it is allowed.
 * - tests that check that no rotation is done when the piece can't rotate because of a lack of space. 
 */
	@Test
	public void checkRotationTest() {
		Grid g = new Grid();
		LShape l = new LShape(1, 1, g);
		Square[] lSquares = l.getSquareArray();
		l.rotate();
		assertTrue(lSquares[1].getRow() == 1 && lSquares[1].getCol() == 1
				&& lSquares[0].getRow() == 1 && lSquares[0].getCol() == 2
				&& lSquares[2].getRow() == 1 && lSquares[2].getCol() == 0
				&& lSquares[3].getRow() == 2 && lSquares[3].getCol() == 0);
		
	}
	
	
}