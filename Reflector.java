package kaleidoscope;

/**
 * This is the Reflector class for Kaleidoscope. It sets a new coordinate
 * system for the figures with respect to the center of the JPanel's current size.
 * It then creates reflections for all the shapes.
 * 
 * @author David Matuszek
 * @author Selah Lynch
 * @author Rajveer Parikh
 */
public class Reflector {

	int width, height;
	
	public Reflector(int width, int height){
		this.width = width;
		this.height = height;
	}
	
	public int[] centerCoords(int[] JPanelCoords){
		int[] newCoords = {JPanelCoords[0] - width/2, JPanelCoords[1] - height/2};
		return newCoords;
	}

	public int[] JPanelCoords(int[] centerCoords){
		int[] newCoords = {centerCoords[0] + width/2, centerCoords[1] + height/2};
		return newCoords;
	}
	
	int[] get(int[] JPanelCoords, boolean swap, boolean negateX, boolean negateY){
		int[] centerCoords = centerCoords(JPanelCoords);
		int x = centerCoords[0];
		int y = centerCoords[1];
		if(swap){
			int temp = x;
			x = y;
			y = temp;
		}
		if(negateX) x = -x;
		if(negateY) y = -y;
		int[] newCenterCoords = new int[2];
		newCenterCoords[0] = x;
		newCenterCoords[1] = y;
		return JPanelCoords(newCenterCoords);		
	}
	
	int[][] getAll(int[] JPanelCoords){
		int[][] reflections = new int[8][2];
		reflections[0] = get(JPanelCoords, false, false, false);
		reflections[1] = get(JPanelCoords, false, false, true);
		reflections[2] = get(JPanelCoords, false, true, false);
		reflections[3] = get(JPanelCoords, false, true, true);
		reflections[4] = get(JPanelCoords, true, false, false);
		reflections[5] = get(JPanelCoords, true, false, true);
		reflections[6] = get(JPanelCoords, true, true, false);
		reflections[7] = get(JPanelCoords, true, true, true);
		return reflections;
	}

	int[][][] getAll(int[][] JPanelCoordArray){
		int numCoords = JPanelCoordArray.length;
		int[][][] allReflections = new int [numCoords][8][2];
		for(int i = 0; i< JPanelCoordArray.length; i++){
			int[] JPanelCoords = JPanelCoordArray[i]; 
			allReflections[i] = getAll(JPanelCoords);
		}
		return allReflections;
	}
	
}
