import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Graphics;

import org.junit.Test;

/**
 * One Square on our Tetris Grid or one square in our Tetris game piece
 * 
 * @author CSC 143
 */
public class Square {
	private Grid grid; // the environment where this Square is

	private int row, col; // the grid location of this Square

	private boolean ableToMove; // true if this Square can move

	private Color color; // the color of this Square

	// possible move directions are defined by the Game class

	// dimensions of a Square
	public static final int WIDTH = 20;

	public static final int HEIGHT = 20;
	
	// a dictionary of booleans to  path first. (converting to matrix angles is too hard)
	private static final boolean[][] horizontalPathFirst = {
	    {true, true, true, true, false},
	    {false, true, true, false, false},
	    {false, false, false, false, false},
	    {false, false, true, true, false},
	    {false, true, true, true, true}
	};

	/**
	 * Creates a square
	 * 
	 * @param g
	 *            the Grid for this Square
	 * @param row
	 *            the row of this Square in the Grid
	 * @param col
	 *            the column of this Square in the Grid
	 * @param c
	 *            the Color of this Square
	 * @param mobile
	 *            true if this Square can move
	 * 
	 * @throws IllegalArgumentException
	 *             if row and col not within the Grid
	 */
	public Square(Grid g, int row, int col, Color c, boolean mobile) {
		if (row < 0 || row > Grid.HEIGHT - 1)
			throw new IllegalArgumentException("Invalid row =" + row);
		if (col < 0 || col > Grid.WIDTH - 1)
			throw new IllegalArgumentException("Invalid column  = " + col);

		// initialize instance variables
		grid = g;
		this.row = row;
		this.col = col;
		color = c;
		ableToMove = mobile;
	}

	/**
	 * Returns the row for this Square
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column for this Square
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Returns true if this Square can move 1 spot in direction d
	 * 
	 * @param direction
	 *            the direction to test for possible move
	 */
	public boolean canMove(Direction direction) {
		if (!ableToMove)
			return false;

		boolean move = true;
		// if the given direction is blocked, we can't move
		// remember to check the edges of the grid
		switch (direction) {
		case DOWN:
			if (row == (Grid.HEIGHT - 1) || grid.isSet(row + 1, col))
				move = false;
			break;

		// currently doesn't support checking LEFT or RIGHT
		// MODIFY so that it correctly returns if it can move left or right
		case LEFT:
			if (col == 0 || grid.isSet(row, col - 1)) {
				move = false;
				// can't go left if along the left edge or
				// the square to the left is taken
			}
			break;
		case RIGHT:
			if (col == Grid.WIDTH - 1 || grid.isSet(row, col + 1)) {
				// can't go right if along the right edge or
				// the square to the right is taken
				move = false;
			}
			break;
		}
		return move;
	}
	
	/**
	 * moves this square in the given direction if possible.
	 * 
	 * The square will not move if the direction is blocked, or if the square is
	 * unable to move.
	 * 
	 * If it attempts to move DOWN and it can't, the square is frozen and cannot
	 * move anymore
	 * 
	 * @param direction
	 *            the direction to move
	 */
	public void move(Direction direction) {
		if (canMove(direction)) {
			switch (direction) {
			case DOWN:
				row = row + 1;
				break;
			// currently doesn't support moving LEFT or RIGHT
			// MODIFY so that the Square moves appropriately
			case RIGHT:
				col ++;
				break;
			case LEFT:
				col --;
				break;
			case UP:
				row = row - 1;
				break;
			}
		}
	}

	/**
	 * Changes the color of this square
	 * 
	 * @param c
	 *            the new color
	 */
	public void setColor(Color c) {
		color = c;
	}

	/**
	 * Gets the color of this square
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Draws this square on the given graphics context
	 */
	public void draw(Graphics g) {

		// calculate the upper left (x,y) coordinate of this square
		int actualX = Grid.LEFT + col * WIDTH;
		int actualY = Grid.TOP + row * HEIGHT;
		g.setColor(color);
		g.fillRect(actualX, actualY, WIDTH, HEIGHT);
		// black border (if not empty)
		if (!color.equals(Grid.EMPTY)) {
			g.setColor(Color.BLACK);
			g.drawRect(actualX, actualY, WIDTH, HEIGHT);
		}
	}
	
	/**
	 * utilizes matrices, and rotates 90 degrees
	 * [x] - row
	 * [y] - column
	 */
	
	public void rotate(Square[] square) {
		if (!canRotate(square)) {
			return;
		}
		for (int i = 0; i <= 3; i++) {
			if (i != 1) {
				// vr = vector relative to pivot square (1 in this case)
				int vrX = square[i].getRow() - square[1].getRow();
				int vrY = square[i].getCol() - square[1].getCol();
				// vt = transformed vector
				int vtX = 0 * vrX + 1 * vrY;
				int vtY = -1 * vrX + 0 * vrY;
				// v2 = new vector
				int v2X = square[1].getRow() + vtX;
				int v2Y = square[1].getCol() + vtY;
				if (v2X - square[i].getRow() > 0) {
					for (int j = 0; j <= v2X - square[i].getRow(); j++) {
						square[i].move(Direction.DOWN);
					}
				} else if (v2X - square[i].getRow() < 0) {
					for (int j = 0; j <= -(v2X - square[i].getRow()); j++) {
						square[i].move(Direction.UP);
					}
				}
				if (v2Y - square[i].getCol() > 0) {
					for (int j = 0; j <= v2Y - square[i].getCol(); j++) {
						square[i].move(Direction.RIGHT);
					}
				} else if (v2Y - square[i].getCol() < 0) {
					for (int j = 0; j <= -(v2Y - square[i].getCol()); j++) {
						square[i].move(Direction.LEFT);
					}
				} 
			}
		}
	}
	
	//is the horizontal path clear?
	public boolean horizontalTraverse(int[] pathTraveler, int[] path) {
    //positive direction -->
    if(path[1]>0){
     for(int j=path[1]; j>0;j--) {
       pathTraveler[1]+=1;
       if(grid.isSet(pathTraveler[0],pathTraveler[1]) ) {
         return false;
       }
     }
    }
  //Negative direction <--
    else if(path[1]<0) {
      for(int j=path[1]; j<0;j++) {
        pathTraveler[1]-=1;
        if(grid.isSet(pathTraveler[0],pathTraveler[1]) ) {
          return false;
        }
      }
    }
    return true;
  }

	//is the vertical path clear?
	public boolean verticalTraverse(int[] pathTraveler, int[] path) {
	  // positive direction (down)
	  if(path[0]>0){
      for(int j=path[0]; j>0;j--) {
        pathTraveler[0] += 1;
        if(grid.isSet(pathTraveler[0], pathTraveler[1]) ) {
          return false;
        }
      }
    }
   //Negative direction (up)
    else if(path[0]<0) {
      for(int j=path[0]; j<0;j++) {
        pathTraveler[0] -=1;
        if(grid.isSet(pathTraveler[0], pathTraveler[1]) ) {
         return false;
        }
      }
    }
    return true;	  
}
	
	public boolean canRotate(Square[] square){
	  // {row, col}
	  int[] pathTraveler = new int[2];
		for (int i = 0; i <= 3; i++) {
			if (i != 1) {
			  pathTraveler[0] = square[i].getRow();
			  pathTraveler[1] = square[i].getCol();
				// vr = vector relative to pivot square (1 in this case)
			  // origin vector - pivot origin vector = vector relative to pivot(vr) 
				int vrX = square[i].getRow() - square[1].getRow();
				int vrY = square[i].getCol() - square[1].getCol();
				// vt = transformed vector
				// cw 90 degrees vr[row,col] * [[0 1], [-1 0]] = vt(vr rotated 90 deg. cw)
				int vtX = 0 * vrX + 1 * vrY;
				int vtY = -1 * vrX + 0 * vrY;
				// v2 = new vector
				// v2 = vt as a vector relative to origin = vt + pivot origin vector
				int v2X = square[1].getRow() + vtX;
				int v2Y = square[1].getCol() + vtY;
				//[delta row, delta col] or the path that square[i] will take during rotation
				int [] path = {v2X-square[i].getRow(), v2Y-square[i].getCol()};
				//traverse delta x or delta y direction first?
				if( horizontalPathFirst[vrX+2][vrY+2] ) {
			    //check the horizontal squares first
				  if(!horizontalTraverse(pathTraveler, path) || !verticalTraverse(pathTraveler, path)) {
				    return false;
				  }
				}else {
				  //check the squares vertically first
          if(!verticalTraverse(pathTraveler, path) || !horizontalTraverse(pathTraveler, path)) {
            return false;
          }
				}
			}
		}
		return true;
	}
}