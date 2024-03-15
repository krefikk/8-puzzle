import java.awt.Color; // Used for coloring the board
import java.awt.Point; // Used for the positions of the tiles and the empty cell

public class Board {

   // Design Properties
   private static final Color backgroundColor = new Color(145, 234, 255);
   private static final Color boxColor = new Color(31, 160, 239);
   private static final double lineThickness = 0.02;
   
   // Object Properties
   private State boardState;
   protected Tile[][] tiles = new Tile[3][3];
   private int emptyCellRow, emptyCellCol;

   public Board() { // First constructor for creating a board object with random number sort
      
      int[] boardNumbers = new int[9];
      for (int i = 0; i < 9; i++) {
         boardNumbers[i] = i;
      }
      randomShuffling(boardNumbers);
      boardState = new State(boardNumbers, 0, 'A', null);

      int arrayIndex = 0;
      for (int row = 0; row < 3; row++) {
         for (int col = 0; col < 3; col++) {
            if (boardState.getArray()[arrayIndex] != 0) {
               tiles[row][col] = new Tile(boardState.getArray()[arrayIndex]);
            } else {
               emptyCellRow = row;
               emptyCellCol = col;
            }
            arrayIndex++;
         }
      }
   }
   
   public Board(State state) { // Second constructor for creating a board object using a state object
	   
	   boardState = state;
	   
	   int arrayIndex = 0;
	      for (int row = 0; row < 3; row++) {
	         for (int col = 0; col < 3; col++) {
	            if (boardState.getArray()[arrayIndex] != 0) {
	               tiles[row][col] = new Tile(boardState.getArray()[arrayIndex]);
	            } else {
	               emptyCellRow = row;
	               emptyCellCol = col;
	            }
	            arrayIndex++;
	         }
	      }
   }

   protected void randomShuffling(int[] array) { // A method for shuffling an array randomly

      for (int i = 0; i < array.length; i++) {
         int randIndex = (int) (Math.random() * array.length);
         if (i != randIndex) {
            int temp = array[i];
            array[i] = array[randIndex];
            array[randIndex] = temp;
         }
      }
      
   }


   public boolean moveRight() { // Moves the zero in the state object and the empty tile in the tiles array to the right

      if (emptyCellCol == 2) {
         return false;
      }
      
      // Updating the board object
      setTile(emptyCellRow, emptyCellCol, getTile(emptyCellRow, emptyCellCol + 1));
      setTile(emptyCellRow, emptyCellCol + 1, null);
      /*
      tiles[emptyCellRow][emptyCellCol] = tiles[emptyCellRow][emptyCellCol + 1];
      tiles[emptyCellRow][emptyCellCol + 1] = null;
      */
      emptyCellCol++;
      
      // Updating the state object stored inside of the board
      setBoardState(boardState.moveRight());

      return true;
      
   }

   public boolean moveLeft() { // Moves the zero in the state object and the empty tile in the tiles array to the left

      if (emptyCellCol == 0) {
         return false; 
      }
      
      // Updating the board object
      setTile(emptyCellRow, emptyCellCol, getTile(emptyCellRow, emptyCellCol - 1));
      setTile(emptyCellRow, emptyCellCol - 1, null);
      emptyCellCol--;

      // Updating the state object stored inside of the board
      setBoardState(boardState.moveLeft());
      
      return true;
      
   }

   public boolean moveUp() { // Moves the zero in the state object and the empty tile in the tiles array to the up

      if (emptyCellRow == 0) {
         return false;
      }
      
      // Updating the board object
      setTile(emptyCellRow, emptyCellCol, getTile(emptyCellRow - 1, emptyCellCol));
      setTile(emptyCellRow - 1, emptyCellCol, null);
      emptyCellRow--;
      
      // Updating the state object stored inside of the board
      setBoardState(boardState.moveUp());
      
      return true;
      
   }


   public boolean moveDown() { // Moves the zero in the state object and the empty tile in the tiles array to the down

      if (emptyCellRow == 2) {
         return false;
      }
      
      // Updating the board object
      setTile(emptyCellRow, emptyCellCol, getTile(emptyCellRow + 1, emptyCellCol));
      setTile(emptyCellRow + 1, emptyCellCol, null);
      emptyCellRow++;
      
      // Updating the state object stored inside of the board
      setBoardState(boardState.moveDown());
      
      return true;
      
   }

   public void draw() { // Draws the board into the frame

      StdDraw.clear(backgroundColor);

      for (int row = 0; row < 3; row++) {
         for (int col = 0; col < 3; col++) {
            // Skipping the empty cell
            if (tiles[row][col] == null) {
               continue;
            }
            Point tilePosition = getTilePosition(row, col);
            tiles[row][col].draw(tilePosition.x, tilePosition.y);

         }
      }
      
      // Drawing the box around the board
      StdDraw.setPenColor(boxColor);
      StdDraw.setPenRadius(lineThickness);
      StdDraw.square(2, 2, 1.5);
      StdDraw.setPenRadius(); // Resetting pen radius to its default value
      
   }

   private Point getTilePosition(int rowIndex, int columnIndex) { // Returns the tile position as a point object
      int posX = columnIndex + 1, posY = 3 - rowIndex;
      return new Point(posX, posY);
   }
   
   protected int[] getStateArray() { // Returns the array of the board's state
	   return boardState.getArray();
   }
   
   public void setBoardState(State newState) { // Sets the state object of the board
	    boardState = newState;
	}

   
   public State getBoardState() { // Returns the state object of the board
	   return boardState;
   }
   
   public Tile[][] getTilesArray() { // Returns the tiles array of the board
	   return tiles;
   }
   
   public void setTile(int x, int y, Tile newValue) { // Sets the specified tile object inside of the tiles array
	   if (x > 2 || y > 2) {
		   System.out.println("There's no index like this in the tiles array");
		   return;
	   }
	   tiles[x][y] = newValue;
   }
   
   public Tile getTile(int x, int y) { // Returns the specified tile object inside of the tiles array
	   if (x > 2 || y > 2) {
		   System.out.println("There's no index like this in the tiles array");
		   return null;
	   }
	   return tiles[x][y];
   }

}