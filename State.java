import java.util.ArrayList;
import java.util.List;

public class State {

	// Goal State
	private static final int[] GOAL_STATE = {1, 2, 3, 4, 5, 6, 7, 8, 0};
	
	// Object Properties
	private int[] state;
	private State parent;
	private int gCost;
	private char direction;
	
	public State(int[] state, int gCost, char direction, State parent) { // Constructor
		this.state = state;
		this.gCost = gCost;
		this.direction = direction;
		this.parent = parent;
	}
	
	public int getDistance() { // Calculates the Manhattan distance
		
		int distance = 0;
        State gState = new State(GOAL_STATE, 0, 'A', null);
        
        for (int i = 0; i < state.length; i++) {
            int goalIndex = gState.getIndex(state[i]);
            if (goalIndex != -1) {
                int rowDiff = Math.abs(i / 3 - goalIndex / 3);
                int colDiff = Math.abs(i % 3 - goalIndex % 3);
                distance += rowDiff + colDiff;
            }
        }
        
        return distance;
        
    }
	
	public int getIndex(int value) { // Returns the index of specified element
        for (int i = 0; i < getArray().length; i++) {
            if (getElement(i) == value) {
                return i;
            }
        }
        return -1;
    }
	
	public char getCreatingDirection() { // Returns the direction of creation of object
		return direction;
	}
	
	public void setCreatingDirection(char direction) { // Sets the direction of creation of object
		this.direction = direction;
	}

    public int getFValue() { // Returns the f value
        return gCost + getDistance();
    }

    public int[] getArray() { // Returns the array
        return state;
    }
    
    public int getElement(int index) { // Returns the element with index given
    	return state[index];
    }
    
    public void setElement(int index, int value) { // Sets the element with index given
        getArray()[index] = value;
    }

    public int getGCost() { // Returns the generation of the object
        return gCost;
    }
    
    public void setGCost(int gCost) { // Sets the generation of the object
        this.gCost = gCost;
    }
    
    public int findZeroIndex() { // Returns the index of zero
        for (int i = 0; i < getArray().length; i++) {
            if (getArray()[i] == 0) {
                return i;
            }
        }
        return -1; // Should never reach here
    }

    public boolean canMoveRight() { // Checks if it can move right
        if (this.findZeroIndex() % 3 != 2) {
        	return true;
        } else {
        	return false;
        }
    }

    public boolean canMoveLeft() { // Checks if it can move left
        if (this.findZeroIndex() % 3 != 0) {
        	return true;
        } else {
        	return false;
        }
    }
    
    public boolean canMoveUp() { // Checks if it can move up
    	if (this.findZeroIndex() > 2) {
        	return true;
        } else {
        	return false;
        }
    }
    
    public boolean canMoveDown() { // Checks if it can move down
    	if (this.findZeroIndex() < 6) {
        	return true;
        } else {
        	return false;
        }
    }
    
    public State moveRight() { // Moves it right
        int[] newStateArray = this.getArray().clone(); // Creating a copy of the current state array
        int zeroIndex = this.findZeroIndex();
        int temp = newStateArray[zeroIndex + 1];
        newStateArray[zeroIndex + 1] = 0;
        newStateArray[zeroIndex] = temp;

        return new State(newStateArray, this.getGCost() + 1, 'R', this); // Return a new State object with the modified array
    }

    public State moveLeft() { // Moves it left
        int[] newStateArray = this.getArray().clone();
        int zeroIndex = this.findZeroIndex();
        int temp = newStateArray[zeroIndex - 1];
        newStateArray[zeroIndex - 1] = 0;
        newStateArray[zeroIndex] = temp;

        return new State(newStateArray, this.getGCost() + 1, 'L', this);
    }

    public State moveUp() { // Moves it up
        int[] newStateArray = this.getArray().clone();
        int zeroIndex = this.findZeroIndex();
        int temp = newStateArray[zeroIndex - 3];
        newStateArray[zeroIndex - 3] = 0;
        newStateArray[zeroIndex] = temp;

        return new State(newStateArray, this.getGCost() + 1, 'U', this);
    }

    public State moveDown() { // Moves it down
        int[] newStateArray = this.getArray().clone();
        int zeroIndex = this.findZeroIndex();
        int temp = newStateArray[zeroIndex + 3];
        newStateArray[zeroIndex + 3] = 0;
        newStateArray[zeroIndex] = temp;

        return new State(newStateArray, this.getGCost() + 1, 'D', this);
    }
    
    public List<State> getChildStates() { // Returns all the possible children
        
    	List<State> childStates = new ArrayList<>();
        State childState;

        // Creating children
        if (canMoveRight()) {
        	childState = this.moveRight();
        	childState.setGCost(this.getGCost() + 1);
        	childState.setCreatingDirection('R');
        	childStates.add(childState);
        }
        if (canMoveLeft()) {
        	childState = this.moveLeft();
        	childState.setGCost(this.getGCost() + 1);
        	childState.setCreatingDirection('L');
        	childStates.add(childState);
        }
        if (canMoveUp()) {
        	childState = this.moveUp();
        	childState.setGCost(this.getGCost() + 1);
        	childState.setCreatingDirection('U');
        	childStates.add(childState);
        }
        if (canMoveDown()) {
        	childState = this.moveDown();
        	childState.setGCost(this.getGCost() + 1);
        	childState.setCreatingDirection('D');
        	childStates.add(childState);
        }

        return childStates;
        
    }
    
    public State getParent() { // Returns the parent
    	return parent;
    }
    
    public boolean compareStates(State state) { // Compares only arrays
    	
    	if (this.getArray().length != state.getArray().length) {
    		return false;
    	}
    	for (int i = 0; i < this.getArray().length; i++) {
    		if (this.getArray()[i] != state.getArray()[i]) {
    			return false;
    		}
    	}
    	return true;
    	
    }
    
    public int findInversions() { // Finds number of inversions according to goal state parameter.
    	
    	int inversions = 0;
    	for (int i = 0; i < getArray().length; i++) {
    		for (int k = i + 1; k < this.getArray().length; k++) {
    			if (getElement(i) != 0 && getElement(k) != 0 && getElement(i) > getElement(k)) {
    				inversions++;    			}
    		}
    	}
    	
    	return inversions;
    	
    }
    
    @Override
    public String toString() { // Converts the object to a string
    	
    	String str = "";
    	for (int element : this.getArray()) {
    		str += element;
    		str += ", ";
    	}
    	
    	str += "Generation = "+ getGCost()+ ", ";
    	str += "Manhattan Distance = "+ getDistance()+ ", ";
    	str += "Created Direction = "+ String.valueOf(getCreatingDirection());
    	
    	return str;
    	
    }
    
    public void printState() { // Prints the object properties
    	System.out.println(this.toString());
    }

}
