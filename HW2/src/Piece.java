import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public interface Piece {
	
	void draw(Graphics g);
	void move(Direction direction);
	Point[] getLocations();
	Color getColor();
	boolean canMove(Direction direction);
	Square[] getSquare();
	void drop(Piece piece);
	void rotate();
}
