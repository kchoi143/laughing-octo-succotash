import java.awt.Color;

public class JShape extends AbstractPiece {
  public JShape(int r, int c, Grid g) {
    grid = g;
    square = new Square[PIECE_COUNT];
    ableToMove = true;

    // Create the squares
    square[0] = new Square(g, r - 1, c + 1, Color.blue, true);
    square[1] = new Square(g, r, c + 1, Color.blue, true);
    square[2] = new Square(g, r + 1, c + 1, Color.blue, true);
    square[3] = new Square(g, r + 1, c, Color.blue, true);
  }
}