package backgammon;

import java.util.Random;

public class Die {
	
    private int face1 ,face2, moveNumber, moveStep1, moveStep2;
    private Random rand;
    private View view;
    
    Die () {
        rand = new Random();
        view = new View();
        
        moveNumber = 2;
        moveStep1 = 1;
        moveStep2 = 1;
        face1 = rand.nextInt(1, 7);
        face2 = rand.nextInt(1, 7);
        if (face1 == face2) {
        	moveStep1 = 4;
        	moveNumber = 4;
        }
    }
    
    // Return the face on the die
    public int getFace (int index) {
    	return switch (index) {
			case 1 -> face1;
			case 2 -> face2;
			default -> 0;
		};
    }
    
    public void roll () {
    	moveNumber = 2;
    	moveStep1 = 1;
    	moveStep2 = 1;
    	face1 = rand.nextInt(1, 7);
        face2 = rand.nextInt(1, 7);
        if (face1 == face2) {
        	moveStep1 = 4;
        	moveNumber = 4;
        }
        view.showDice(face1,face2);
    }
    
    public void setZero () {
    	moveNumber = 0;
    	moveStep1 = 0;
    	moveStep2 = 0;
    	face1 = 0;
        face2 = 0;
    }
    
    public void setFace (int face_1, int face_2) {
    	moveNumber = 2;
    	moveStep1 = 1;
    	moveStep2 = 1;
        face1 = face_1;
        face2 = face_2;
        if (face1 == face2) {
        	moveStep1 = 4;
        	moveNumber = 4;
        }
        view.showDice(face1,face2);
    }
    
    public int getMoveStep(int index) {
    	return switch (index) {
			case 1 -> moveStep1;
			case 2 -> moveStep2;
			default -> 0;
		};
    }
    
    public int getMoveNumber () {
    	return moveNumber;
    }
    
    public void minusMoveStep (int index) {
    	switch (index) {
			case 1 -> moveStep1--;
			case 2 -> moveStep2--;
		};
    }
    
    public void addMoveStep (int index) {
    	switch (index) {
			case 1 -> moveStep1++;
			case 2 -> moveStep2++;
		};
    }
    
    public void minusMoveNumber () {
    	moveNumber--;
    }
}
