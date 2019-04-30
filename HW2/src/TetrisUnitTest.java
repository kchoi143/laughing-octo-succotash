import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;

public class TetrisUnitTest {

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
    //L 90 deg. cw rotation
    l.rotate();
    assertTrue(lSquares[1].getRow() == 1 && lSquares[1].getCol() == 1
        && lSquares[0].getRow() == 1 && lSquares[0].getCol() == 2
        && lSquares[2].getRow() == 1 && lSquares[2].getCol() == 0
        && lSquares[3].getRow() == 2 && lSquares[3].getCol() == 0);
    //L 180 deg. cw rotation
    l.rotate();
    assertTrue(lSquares[1].getRow() == 1 && lSquares[1].getCol() == 1
        && lSquares[0].getRow() == 2 && lSquares[0].getCol() == 1
        && lSquares[2].getRow() == 0 && lSquares[2].getCol() == 1
        && lSquares[3].getRow() == 0 && lSquares[3].getCol() == 0);
    //L 270 deg. cw rotation
    l.rotate();
    assertTrue(lSquares[1].getRow() == 1 && lSquares[1].getCol() == 1
        && lSquares[0].getRow() == 1 && lSquares[0].getCol() == 0
        && lSquares[2].getRow() == 1 && lSquares[2].getCol() == 2
        && lSquares[3].getRow() == 0 && lSquares[3].getCol() == 2);
    
    //barshape
    BarShape b = new BarShape(2,1,g);
    Square[] bs = b.getSquareArray();
    //barshape 90 deg. cw rotation
    b.rotate();
    assertTrue(bs[1].getRow() == 2 && bs[1].getCol() == 2
        && bs[0].getRow() == 1 && bs[0].getCol() == 2
        && bs[2].getRow() == 3 && bs[2].getCol() == 2
        && bs[3].getRow() == 4 && bs[3].getCol() == 2
        );
    //barshape 180 deg. cw rotation
    b.rotate();
    assertTrue(bs[1].getRow() == 2 && bs[1].getCol() == 2
        && bs[0].getRow() == 2 && bs[0].getCol() == 3
        && bs[2].getRow() == 2 && bs[2].getCol() == 1
        && bs[3].getRow() == 2 && bs[3].getCol() == 0
        );
    //barshape 270 deg. cw rotation
    b.rotate();
    assertTrue(bs[1].getRow() == 2 && bs[1].getCol() == 2
        && bs[0].getRow() == 3 && bs[0].getCol() == 2
        && bs[2].getRow() == 1 && bs[2].getCol() == 2
        && bs[3].getRow() == 0 && bs[3].getCol() == 2
        );
    
    //S shape 
    SShape s = new SShape(1,1,g);
    Square[] ss = s.getSquareArray();
    // 90 deg.
    s.rotate();
    assertTrue(ss[1].getRow() == 1 && ss[1].getCol() == 1
        && ss[0].getRow() == 2 && ss[0].getCol() == 1
        && ss[2].getRow() == 1 && ss[2].getCol() == 0
        && ss[3].getRow() == 0 && ss[3].getCol() == 0
        );
    // 180 deg.
    s.rotate();
    assertTrue(ss[1].getRow() == 1 && ss[1].getCol() == 1
        && ss[0].getRow() == 1 && ss[0].getCol() == 0
        && ss[2].getRow() == 0 && ss[2].getCol() == 1
        && ss[3].getRow() == 0 && ss[3].getCol() == 2
        );
    // 270 deg.
    s.rotate();
    assertTrue(ss[1].getRow() == 1 && ss[1].getCol() == 1
        && ss[0].getRow() == 0 && ss[0].getCol() == 1
        && ss[2].getRow() == 1 && ss[2].getCol() == 2
        && ss[3].getRow() == 2 && ss[3].getCol() == 2
        );

    //T shape
    TShape t = new TShape(1, 1, g);
    Square[] ts = t.getSquareArray();
    //90 deg.
    t.rotate();
    assertTrue(ts[1].getRow() == 1 && ts[1].getCol() == 1
        && ts[0].getRow() == 0 && ts[0].getCol() == 1
        && ts[2].getRow() == 2 && ts[2].getCol() == 1
        && ts[3].getRow() == 1 && ts[3].getCol() == 0
        );
    //180 deg.
    t.rotate();
    assertTrue(ts[1].getRow() == 1 && ts[1].getCol() == 1
        && ts[0].getRow() == 1 && ts[0].getCol() == 2
        && ts[2].getRow() == 1 && ts[2].getCol() == 0
        && ts[3].getRow() == 0 && ts[3].getCol() == 1
        );
    //270 deg. 
    t.rotate();
    assertTrue(ts[1].getRow() == 1 && ts[1].getCol() == 1
        && ts[0].getRow() == 2 && ts[0].getCol() == 1
        && ts[2].getRow() == 0 && ts[2].getCol() == 1
        && ts[3].getRow() == 1 && ts[3].getCol() == 2
        );
    
    //J shape
    JShape j = new JShape(1,0,g); 
    Square[] js = j.getSquareArray();
    //90 deg.
    j.rotate();
    assertTrue(js[1].getRow() == 1 && js[1].getCol() == 1
        && js[0].getRow() == 1 && js[0].getCol() == 2
        && js[2].getRow() == 1 && js[2].getCol() == 0
        && js[3].getRow() == 0 && js[3].getCol() == 0
        );
    //180 deg.
    j.rotate();
    assertTrue(js[1].getRow() == 1 && js[1].getCol() == 1
        && js[0].getRow() == 2 && js[0].getCol() == 1
        && js[2].getRow() == 0 && js[2].getCol() == 1
        && js[3].getRow() == 0 && js[3].getCol() == 2
        );
    //270 deg.
    j.rotate();
    assertTrue(js[1].getRow() == 1 && js[1].getCol() == 1
        && js[0].getRow() == 1 && js[0].getCol() == 0
        && js[2].getRow() == 1 && js[2].getCol() == 2
        && js[3].getRow() == 2 && js[3].getCol() == 2
        );
    
    //Z shape
    ZShape z = new ZShape(1,1,g);
    Square[] zs = z.getSquareArray();
    //90 deg. 
    z.rotate();
    assertTrue(zs[1].getRow() == 1 && zs[1].getCol() == 1
        && zs[0].getRow() == 0 && zs[0].getCol() == 1
        && zs[2].getRow() == 1 && zs[2].getCol() == 0
        && zs[3].getRow() == 2 && zs[3].getCol() == 0
            );
    //180 deg.
    z.rotate();
    assertTrue(zs[1].getRow() == 1 && zs[1].getCol() == 1
        && zs[0].getRow() == 1 && zs[0].getCol() == 2
        && zs[2].getRow() == 0 && zs[2].getCol() == 1
        && zs[3].getRow() == 0 && zs[3].getCol() == 0
            );
    //270 deg.
    z.rotate();
    assertTrue(zs[1].getRow() == 1 && zs[1].getCol() == 1
        && zs[0].getRow() == 2 && zs[0].getCol() == 1
        && zs[2].getRow() == 1 && zs[2].getCol() == 2
        && zs[3].getRow() == 0 && zs[3].getCol() == 2
            );
    
  }
  
  //check for illegal rotation 
  
  @Test
  public void canRotateTest() {
    Grid g = new Grid();
    //4 square tall wall at col 2 
    for(int i=0;i<4;i++) {
      g.set(i, 2, Color.BLUE);
    }
    
    // L shape
    LShape l = new LShape(2,0,g);
    Square[] ls = l.getSquareArray();
    l.rotate();
    assertTrue(ls[0].getRow() == 1 && ls[0].getCol() == 0
        && ls[1].getRow() == 2 && ls[1].getCol() == 0
        && ls[2].getRow() == 3 && ls[2].getCol() == 0
        && ls[3].getRow() == 3 && ls[3].getCol() == 1
        );
    l.delete();
    
    //bar shape
    g.set(3,6,Color.BLUE);
    
    BarShape b = new BarShape(2,3,g);
    Square[] bs = b.getSquareArray();
    b.rotate();
    assertTrue(bs[0].getRow() == 2 && bs[0].getCol() == 3
        && bs[1].getRow() == 2 && bs[1].getCol() == 4
        && bs[2].getRow() == 2 && bs[2].getCol() == 5
        && bs[3].getRow() == 2 && bs[3].getCol() == 6
        );
    b.delete();
    //S shape
    g.set(1, 3, Color.BLUE);
    SShape s = new SShape(1,4,g);
    Square[] ss = s.getSquareArray();
    s.rotate();
    assertTrue(ss[0].getRow() == 1 && ss[0].getCol() == 5
        && ss[1].getRow() == 1 && ss[1].getCol() == 4
        && ss[2].getRow() == 2 && ss[2].getCol() == 4
        && ss[3].getRow() == 2 && ss[3].getCol() == 3
        );
    s.delete();
   
    //T shape 
    TShape t = new TShape(2,4,g);
    Square[] ts = t.getSquareArray();
    t.rotate();
    assertTrue(ts[0].getRow() == 2 && ts[0].getCol() == 3
        && ts[1].getRow() == 2 && ts[1].getCol() == 4
        && ts[2].getRow() == 2 && ts[2].getCol() == 5
        && ts[3].getRow() == 3 && ts[3].getCol() == 4
        );
    t.delete();
    
    //J shape
    JShape j = new JShape(2,0,g);
    Square[] js = j.getSquareArray();
    j.rotate();
    assertTrue(js[0].getRow() == 1 && js[0].getCol() == 1
        && js[1].getRow() == 2 && js[1].getCol() == 1
        && js[2].getRow() == 3 && js[2].getCol() == 1
        && js[3].getRow() == 3 && js[3].getCol() == 0
        );
    j.delete();
    //Z shape
    g.set(2, 0, Color.BLUE);
    ZShape z = new ZShape(3,1, g);
    Square[] zs = z.getSquareArray();
    z.rotate();
    assertTrue(zs[0].getRow() == 3 && zs[0].getCol() == 0
        && zs[1].getRow() == 3 && zs[1].getCol() == 1
        && zs[2].getRow() == 4 && zs[2].getCol() == 1
        && zs[3].getRow() == 4 && zs[3].getCol() == 2
        );
  }
  
  
}