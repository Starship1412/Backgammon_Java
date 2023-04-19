package backgammon;

import java.util.Random;

public class Dice {
	
    private int moveNumber;
    private int[] faces, moveSteps;
    private Random rand;
    private View view;
    
    Dice () { // Constructor: Initializes a new dice object with default face and move values and a new random number generator.
        rand = new Random();
        view = new View();
        this.faces = new int[2];
        this.moveSteps = new int[2];
		
        moveSteps[0] = 1;
        moveSteps[1] = 1;
        moveNumber = moveSteps[0] + moveSteps[1];
        faces[0] = rand.nextInt(1, 7);
        faces[1] = rand.nextInt(1, 7);
        if (faces[0] == faces[1]) {
        	moveSteps[0] = 4;
        	moveSteps[1] = 0;
        	moveNumber = moveSteps[0] + moveSteps[1];
        }
    }
    
    public int getFace (int index) { // Returns the value of the specified face, given the index (1 or 2).
    	return switch (index) {
			case 1 -> faces[0];
			case 2 -> faces[1];
			default -> 0;
		};
    }
    
    public void roll () { // Rolls the dice, updating the face and move values accordingly.
    	moveSteps[0] = 1;
    	moveSteps[1] = 1;
    	moveNumber = moveSteps[0] + moveSteps[1];
    	faces[0] = rand.nextInt(1, 7);
        faces[1] = rand.nextInt(1, 7);
        if (faces[0] == faces[1]) {
        	moveSteps[0] = 4;
        	moveSteps[1] = 0;
        	moveNumber = moveSteps[0] + moveSteps[1];
        }
    }
    
    public void setZero () { // Sets the face and move values to zero.
    	moveSteps[0] = 0;
    	moveSteps[1] = 0;
    	moveNumber = moveSteps[0] + moveSteps[1];
    	faces[0] = 0;
        faces[1] = 0;
    }
    
    public void setFace (int face1, int face2) { // Sets the face values of the dice and updates the move values accordingly.
    	moveSteps[0] = 1;
    	moveSteps[1] = 1;
    	moveNumber = moveSteps[0] + moveSteps[1];
        faces[0] = face1;
        faces[1] = face2;
        if (faces[0] == faces[1]) {
        	moveSteps[0] = 4;
        	moveSteps[1] = 0;
        	moveNumber = moveSteps[0] + moveSteps[1];
        }
        view.showDice(face1, face2);
    }
    
    public int getMoveStep (int index) { // Returns the move step value for the specified index (1 or 2).
    	return switch (index) {
			case 1 -> moveSteps[0];
			case 2 -> moveSteps[1];
			default -> 0;
		};
    }
    
    public void setMoveStep (int moveStep1, int moveStep2) { // Sets the move step values and updates the total move number accordingly.
    	moveSteps[0] = moveStep1;
    	moveSteps[1] = moveStep2;
    	moveNumber = moveSteps[0] + moveSteps[1];
    }
    
    public int getMoveNumber () { // Returns the total move number based on the move steps.
    	return moveNumber;
    }
    
    public void subtractMoveStep (int index) { // Decreases the move step value for the specified index (1 or 2) and updates the total move number.
    	switch (index) {
			case 1 -> moveSteps[0]--;
			case 2 -> moveSteps[1]--;
		};
		moveNumber = moveSteps[0] + moveSteps[1];
    }
}
