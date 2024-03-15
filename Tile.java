import java.awt.Color; // used for coloring the tile and the number on it
import java.awt.Font; // used for setting the font to show the number on the tile

public class Tile {

	// Design Properties
	private static final Color tileColor = new Color(15, 76, 129);
	private static final Color numberColor = new Color(31, 160, 239);
	private static final Color boxColor = new Color(31, 160, 239);
	private static final double lineThickness = 0.01;
	private static final Font numberFont = new Font("Arial", Font.BOLD, 50);
	
	// Tile Properties
	private int number; 

	public Tile(int number) { // A constructor that creates a tile with a given number
		this.number = number;
	}
   
	public void draw(int posX, int posY) { // A method for drawing the tile centered on a given position
		
		// Drawing the tile as a filled square
		StdDraw.setPenColor(tileColor);
		StdDraw.filledSquare(posX, posY, 0.5);
		
		// Drawing the bounding box of the tile as a square
		StdDraw.setPenColor(boxColor);
		StdDraw.setPenRadius(lineThickness);
		StdDraw.square(posX, posY, 0.5);
		StdDraw.setPenRadius(); // Resetting pen radius to its default value
		
		// Drawing the number on the tile
		StdDraw.setPenColor(numberColor);
		StdDraw.setFont(numberFont);
		StdDraw.text(posX, posY, String.valueOf(number));
		
	}
   
	public int getTileNumber() { // Returns the number on the tile
		return this.number; 
	}
   
	public Tile getTile() { // Returns the tile object
		return this;
	}
   
	public void setTile(int number) { // Sets the tile object
		this.number = number;
	}
   
}