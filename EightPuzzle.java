import java.awt.event.KeyEvent; // For the constants of the keys on the keyboard
import java.util.Stack; // For storing the solution path

public class EightPuzzle {
	
	// Game Properties
	private boolean userPlaying = true; // Checks if user or computer is playing at the moment.
	private boolean gameOver = false; // Checks if puzzle is solved or not.
	
	// Solution Stack
	private Stack<State> solution;
	
	// Objects
	private Board currentBoard;
	private static State GOAL_STATE;
	private Solver solver;
	
	public EightPuzzle() {
		   
		  // StdDraw setup
	      StdDraw.setCanvasSize(500, 500);
	      StdDraw.setScale(0.5, 3.5);
	      StdDraw.enableDoubleBuffering();
	      StdDraw.setTitle("8-Puzzle Game");
	      
	      // Declaring goal state
	      GOAL_STATE = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0}, -1, 'A', null);
	      
	      // Creating board and checking if the number sort s solvable or not
	      currentBoard = new Board();
	      while (currentBoard.getBoardState().findInversions() % 2 != 0 || currentBoard.getBoardState().compareStates(GOAL_STATE)) {
	    	  currentBoard = new Board(); // Making sure that the initial state is solvable and is not same with the goal state.
	      }
	      
	}

	public static void main(String[] args) {

		// Creating the game environment
		EightPuzzle puzzle = new EightPuzzle();
		
		// Update method
		while (true) {
    	  
			while (!puzzle.getGameOverState()) { 
        	  
				if (puzzle.getPlayingState()) { // Checking if player has the control
					puzzle.draw(100);
					puzzle.moveTiles(); 
					if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
						puzzle.setPlayingState(false);
					}
					if (StdDraw.isKeyPressed(KeyEvent.VK_R)) {
						puzzle.restartGame();
					}
				} else { // Checking if computer has the control
					puzzle.solveAndDisplay();
				}

				if (puzzle.getBoard().getBoardState().compareStates(GOAL_STATE)) { // Checking if the puzzle is solved
					puzzle.draw(1500);
					if (puzzle.getPlayingState()) {
						System.out.println("Congratulations, you solved the puzzle in "+puzzle.getBoard().getBoardState().getGCost()+ " steps.");
					}
					puzzle.setGameOverState(true);
				}
                
			}
    	  
			if(puzzle.getGameOverState() && StdDraw.isKeyPressed(KeyEvent.VK_R)) { // For restarting the game
				puzzle.restartGame();
			}
    	  
		}
      
	}
   
   
	public void draw(int time) { // Drawing the board object

		getBoard().draw();
		StdDraw.show();
		StdDraw.pause(time); 
	   
	}
   
	public void moveTiles() { // Moving tiles if needed
	   
		if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {getBoard().moveRight();}   
		if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {getBoard().moveLeft();}   
	   	if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {getBoard().moveUp();}  
	   	if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {getBoard().moveDown();}

	}
   
	public void solveAndDisplay() { // For solving the puzzle and displaying the steps
	   
		// Solving the puzzle
		solver = new Solver();
		setSolution(solver.getShortestPath(solver.solve(getBoard().getBoardState())));
		System.out.println("Solution steps: ");
	   
		// Displaying and printing solution steps
		while(!getSolution().isEmpty()) {
			setBoard(new Board(getSolution().pop()));
	   		getBoard().getBoardState().printState();
	   		this.draw(1500); 
		}
	   
		// Printing the direction values that needed to solve the puzzle in shortest way
		State directions = getBoard().getBoardState();
		int numberOfSteps = directions.getGCost();
		System.out.println("");
		System.out.print("Actions taken in order to reach the goal state: ");
		String directionsSolution = "";
		while(directions.getCreatingDirection() != 'A') {
			directionsSolution += String.valueOf(directions.getCreatingDirection());
			directionsSolution += " ";
			directions = directions.getParent();
		}
		StringBuffer sb = new StringBuffer(directionsSolution);
		sb.deleteCharAt(sb.length() - 1);  
		sb.reverse(); // Reversing the string that contains directions for shortest solution
		System.out.println(sb);
		System.out.println("Solved in "+numberOfSteps+" steps.");
		System.out.println("------------------------------------------------");
	   
	}
   
	public void restartGame() { // For restarting the game any time
	   
		setBoard(new Board());
		while (getBoard().getBoardState().findInversions() % 2 != 0 || getBoard().getBoardState().compareStates(GOAL_STATE)) {
			setBoard(new Board()); // Making sure that the new initial state is solvable and is not same with the goal state.
		}
		setGameOverState(false);
		setPlayingState(true);
	   
	}
   
	public void setGameOverState(boolean value) { // Sets the gameOver value 
		gameOver = value;
	}
   
	public boolean getGameOverState() { // Returns the gameOver value
		return gameOver;
	}
   
	public void setPlayingState(boolean value) { // Sets the userPlaying value
	   	userPlaying = value;
	}
   
	public boolean getPlayingState() { // Returns the userPlaying value
		return userPlaying;
	}
   
	public void setSolution(Stack<State> solution) { // Sets the solution stack
		this.solution = solution;
	}
   
	public Stack<State> getSolution() { // Returns the solution stack
		return solution;
	}
   
	public void setBoard(Board board) { // Sets the board
		currentBoard = board;
	}
   
	public Board getBoard() { // Returns the board
		return currentBoard;
	}
   
}