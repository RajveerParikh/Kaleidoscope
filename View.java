package kaleidoscope;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * The View "observes" and displays what is going on in the Model.
 * 
 * @author David Matuszek
 * @author Selah Lynch
 * @author Rajveer Parikh
 */
public class View extends JPanel implements Observer {
    
    /** This is what we will be observing. */
    Model model;

    Reflector reflector;
    
    /**
     * Constructor.
     * @param model The Model whose working is to be displayed.
     */
    View(Model model) {
        this.model = model;
    }

    /**
     * Displays what is going on in the Model. Note: This method should
     * NEVER be called directly; call repaint() instead.
     * 
     * @param g The Graphics on which to paint things.
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        reflector = new Reflector(getWidth(), getHeight());
        for(int i=0; i<model.figures.size(); i++){
        	Figure figure = model.figures.get(i);
            g.setColor(figure.shapeColor);
            int coords[] = {figure.getX(), figure.getY()};
    		int size = figure.size;
        	if(figure.shape=="circle") drawCircle(g, coords, size);
        	if(figure.shape=="triangle") drawTriangle(g, coords, size);
        	if(figure.shape=="plus") drawPlus(g, coords, size);            	
        }
    }
    // Draw a circle
    public void drawCircle(Graphics g, int[] coords, int size){
    	int[][] allCoords = reflector.getAll(coords);
    	for(int i = 0; i<allCoords.length; i++){
    		int[] theseCoords = allCoords[i];
        	g.fillOval(theseCoords[0]-size/2, theseCoords[1]-size/2, size, size);                		    		
    	}
    }
    
    // Draw a triangle
    public void drawTriangle(Graphics g, int[] coords, int size){
    	int x_center = coords[0];
    	int y_center = coords[1];
    	int[][] triCoords = new int[3][2]; 
    	triCoords[0][0] = x_center + 0;
    	triCoords[0][1] = (int)(y_center - 2*size/Math.sqrt(3));
    	triCoords[1][0] = x_center - size;
    	triCoords[1][1] = (int)(y_center + size/Math.sqrt(3));
    	triCoords[2][0] = x_center + size;
    	triCoords[2][1] = (int)(y_center + size/Math.sqrt(3));
    	int[][][] reflectedTriCoords = reflector.getAll(triCoords);    	
    	for(int i=0; i<8; i++){
        	int[] xArray = new int[3];
        	xArray[0] =	reflectedTriCoords[0][i][0];
        	xArray[1] =	reflectedTriCoords[1][i][0];
        	xArray[2] =	reflectedTriCoords[2][i][0];
        	int[] yArray = new int[3];
        	yArray[0] =	reflectedTriCoords[0][i][1];
        	yArray[1] =	reflectedTriCoords[1][i][1];
        	yArray[2] =	reflectedTriCoords[2][i][1];
        	g.fillPolygon(xArray, yArray, 3);                		    		
    	}
    }
    
    // Draw a plus
    public void drawPlus(Graphics g, int[] coords, int size){
    	size = size*2;
    	int[][] allCoords = reflector.getAll(coords);
    	for(int i = 0; i<allCoords.length; i++){
    		int[] theseCoords = allCoords[i];
        	g.fillRect(theseCoords[0]-size/2, theseCoords[1]-size/4, size, size/2);
        	g.fillRect(theseCoords[0]-size/4, theseCoords[1]-size/2, size/2, size);
    	}
    	
    }
    
    /**
     * When an Observer notifies Observers (and this View is an Observer),
     * this is the method that gets called.
     * 
     * @param obs Holds a reference to the object being observed.
     * @param arg If notifyObservers is given a parameter, it is received here.
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable obs, Object arg) {
        repaint();
    }
}