package backgammon;

import java.util.Random;

public class Die {
	
    public int face1 ,face2;
    public int moveNumber;
    public int dieFace1, dieFace2;
    private Random rand;
    private View view = new View();
    
    Die () {
        rand = new Random();
        moveNumber = 2;
        dieFace1 = 1;
        dieFace2 = 1;
        face1 = rand.nextInt(1, 7);
        face2 = rand.nextInt(1, 7);
        if (face1 == face2) {
        	dieFace1 = 4;
        	moveNumber = 4;
        }
    }
    
    // Return the face on the die
    public int getFace (int index) {
    	return switch(index) {
			case 1 -> face1;
			case 2 -> face2;
			default -> 0;
		};
    }
    
    public void roll () {
    	moveNumber = 2;
    	dieFace1 = 1;
        dieFace2 = 1;
    	face1 = rand.nextInt(1, 7);
        face2 = rand.nextInt(1, 7);
        if (face1 == face2) {
        	dieFace1 = 4;
        	moveNumber = 4;
        }
        view.showDice(face1,face2);
    }
    
    public void setZero () {
    	moveNumber = 0;
    	dieFace1 = 0;
        dieFace2 = 0;
    	face1 = 0;
        face2 = 0;
    }
    
    public void setFace (int face_1, int face_2) {
    	moveNumber = 2;
    	dieFace1 = 1;
        dieFace2 = 1;
        face1 = face_1;
        face2 = face_2;
        if (face1 == face2) {
        	dieFace1 = 4;
        	moveNumber = 4;
        }
        view.showDice(face1,face2);
    }
}
