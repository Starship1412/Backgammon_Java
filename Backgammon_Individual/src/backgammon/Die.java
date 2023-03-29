package backgammon;

import java.util.Random;

public class Die {
	
    private int moveNumber;
    private int[] face, moveStep;
    private Random rand;
    private View view;
    
    Die () {
        rand = new Random();
        view = new View();
        this.face = new int[2];
        this.moveStep = new int[2];
		
        moveNumber = 2;
        moveStep[0] = 1;
        moveStep[1] = 1;
        face[0] = rand.nextInt(1, 7);
        face[1] = rand.nextInt(1, 7);
        if (face[0] == face[1]) {
        	moveStep[0] = 4;
        	moveNumber = 4;
        }
    }
    
    public int getFace (int index) {
    	return switch (index) {
			case 1 -> face[0];
			case 2 -> face[1];
			default -> 0;
		};
    }
    
    public void roll () {
    	moveNumber = 2;
    	moveStep[0] = 1;
    	moveStep[1] = 1;
    	face[0] = rand.nextInt(1, 7);
        face[1] = rand.nextInt(1, 7);
        if (face[0] == face[1]) {
        	moveStep[0] = 4;
        	moveNumber = 4;
        }
        view.showDice(face[0],face[1]);
    }
    
    public void setZero () {
    	moveNumber = 0;
    	moveStep[0] = 0;
    	moveStep[1] = 0;
    	face[0] = 0;
        face[1] = 0;
    }
    
    public void setFace (int face_1, int face_2) {
    	moveNumber = 2;
    	moveStep[0] = 1;
    	moveStep[1] = 1;
        face[0] = face_1;
        face[1] = face_2;
        if (face[0] == face[1]) {
        	moveStep[0] = 4;
        	moveNumber = 4;
        }
        view.showDice(face[0],face[1]);
    }
    
    public int getMoveStep(int index) {
    	return switch (index) {
			case 1 -> moveStep[0];
			case 2 -> moveStep[1];
			default -> 0;
		};
    }
    
    public int getMoveNumber () {
    	return moveNumber;
    }
    
    public void minusMoveStep (int index) {
    	switch (index) {
			case 1 -> moveStep[0]--;
			case 2 -> moveStep[1]--;
		};
    }
    
    public void addMoveStep (int index) {
    	switch (index) {
			case 1 -> moveStep[0]++;
			case 2 -> moveStep[1]++;
		};
    }
    
    public void minusMoveNumber () {
    	moveNumber--;
    }
}
