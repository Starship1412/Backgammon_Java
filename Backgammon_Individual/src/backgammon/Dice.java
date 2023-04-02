package backgammon;

import java.util.Random;

public class Dice {
	
    private int moveNumber;
    private int[] faces, moveSteps;
    private Random rand;
    private View view;
    
    Dice () {
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
    
    public int getFace (int index) {
    	return switch (index) {
			case 1 -> faces[0];
			case 2 -> faces[1];
			default -> 0;
		};
    }
    
    public void roll () {
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
        view.showDice(faces[0],faces[1]);
    }
    
    public void setZero () {
    	moveSteps[0] = 0;
    	moveSteps[1] = 0;
    	moveNumber = moveSteps[0] + moveSteps[1];
    	faces[0] = 0;
        faces[1] = 0;
    }
    
    public void setFace (int face1, int face2) {
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
        view.showDice(faces[0],faces[1]);
    }
    
    public int getMoveStep (int index) {
    	return switch (index) {
			case 1 -> moveSteps[0];
			case 2 -> moveSteps[1];
			default -> 0;
		};
    }
    
    public int getMoveNumber () {
    	return moveNumber;
    }
    
    public void minusMoveStep (int index) {
    	switch (index) {
			case 1 -> moveSteps[0]--;
			case 2 -> moveSteps[1]--;
		};
		moveNumber = moveSteps[0] + moveSteps[1];
    }
    
    public void addMoveStep (int index) {
    	switch (index) {
			case 1 -> moveSteps[0]++;
			case 2 -> moveSteps[1]++;
		};
		moveNumber = moveSteps[0] + moveSteps[1];
    }
}
