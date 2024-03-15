import java.util.*;

public class Solver {

    // States
	protected static final State GOAL_STATE = new State(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 0}, -1, 'A', null);
    private static State initialState;
    
    // Lists
    private PriorityQueue<State> openList = new PriorityQueue<>(Comparator.comparingInt(State::getFValue)); // A priority queue that sorts states from smallest to largest according to their f values
    private Queue<State> closedList = new LinkedList<>(); // A linked list that stores discovered states and branches inside of it
    private Stack<State> shortestPath; // A stack that stores shortest path of states that leads to the goal state

    public Solver() {
    	
    	// Declaring a random initial state
        Board board = new Board();
        int[] initial = board.getStateArray();
        initialState = new State(initial, 0, 'A', null);
        
    }  

    public Queue<State> solve(State initialState) {
    	
    	openList.add(initialState);

        while (!openList.isEmpty()) {
            State currentState = openList.poll();
            
            // Checking if the current state is the goal state
            if (currentState.compareStates(GOAL_STATE)) {
            	closedList.add(currentState);
                break;
            }
            
            closedList.add(currentState);
            
            // Generating child states
            List<State> children = currentState.getChildStates();
            
            for (State child : children) { // Checking if the child state is already in the closed list
                boolean inClosedList = false;
                for (State closedState : closedList) {
                    if (child.compareStates(closedState)) {
                        inClosedList = true;
                        break; // Skipping to the next child state if current state is already in the closed list
                    }
                }
                if (inClosedList) {
                    continue;
                }
                
                // Checking if the child state is already in the open list
                boolean inOpenList = false;
                for (State openState : openList) {
                    if (child.compareStates(openState)) {
                        inOpenList = true;
                        // Updating the current state's g value if the new path to the current state is better
                        if (child.getFValue() < openState.getFValue()) {
                            openState.setGCost(child.getGCost());
                        }
                        break;
                    }
                }
                if (!inOpenList) { // Adding current state into the open list
                    openList.add(child);
                }
            }
        }
        
        return closedList;

    }
    
    protected Stack<State> getShortestPath(Queue<State> list) {
        
    	// Creating a stack to store the states in "reverse order"
        shortestPath = new Stack<>();
        State goalState = null;
        State initialState = list.poll(); // Acquiring initial state from the list parameter
        
        while(!list.isEmpty()) { // Acquiring goal state with the direction of creation value
        	goalState = list.poll();
        }
        
        // Adding goal state into the stack
        shortestPath.push(goalState);
        
        // Traversing back through the parent states until reaching the initial state
        State currentState = goalState;
        while (currentState.getParent() != initialState.getParent()) {
            currentState = currentState.getParent();
            shortestPath.push(currentState);
        }
        
        return shortestPath;
        
    }

    public static void main(String[] args) {
        
    	Solver solver = new Solver();
    	if (initialState.findInversions() % 2 != 0) { // Checking if the initial state is solvable or not
    		System.out.println("This initial state cannot be solved: " +initialState.toString());
    	} else {
    		// Solving and printing the initial state
    		Queue<State> solution = solver.solve(initialState);
    		Stack<State> shortestPath = solver.getShortestPath(solution);
    		while(!shortestPath.isEmpty()) {
    			shortestPath.pop().printState();
    		}
    	}
         
    }
}
