package kaleidoscope;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is the Model class for Kaleidoscope. It is an Observable,
 * which means that it can notifyObservers that something in the
 * model has changed, and they should take appropriate actions.
 * 
 * @author David Matuszek
 * @author Selah Lynch
 * @author Rajveer Parikh
 */
public class Model extends Observable {
    private int xLimit, yLimit;
    private Timer timer;
    ArrayList<Figure> figures;
    
    //constructor
    public Model(){
    	figures = new ArrayList<Figure>(); 
    	figures.add(new Figure(Color.yellow, 3, 2, "circle", 20));
    	figures.add(new Figure(Color.blue, 7, 1, "triangle", 30));
    	figures.add(new Figure(Color.red, 2, 3, "plus", 30));
    }

       
    
    /**
     * Sets the "walls" that the ball should bounce off from.
     * 
     * @param xLimit The position (in pixels) of the wall on the right.
     * @param yLimit The position (in pixels) of the floor.
     */
    public void resizeWindow(int xLimit, int yLimit) {
    	this.xLimit = xLimit;
    	this.yLimit = yLimit;
        for(int i=0; i<figures.size(); i++){
        	figures.get(i).setLimits(xLimit, yLimit);
        }
    }
    
    /**
     * Adds a new figure with random color and size to the JPanel when user wants.
     * 
     * @param xDelta The speed of the figure in the x direction.
     * @param yDelta The speed of the figure in the y direction.
     * @param Shape The shape of the figure to be added.
     */
    public void addNewFigure(int xDelta, int yDelta, String Shape){
    	Color [] color = {Color.blue, Color.green, Color.yellow, Color.red, Color.orange,Color.pink};
    	ArrayList<Color> colorList = new ArrayList<Color>(Arrays.asList(color));
    	Collections.shuffle(colorList);
    	Random r = new Random();
    	int size = 10 + r.nextInt(20);
    	Figure f = new Figure(colorList.get(0), xDelta, yDelta, Shape, size);
    	f.setLimits(xLimit, yLimit);
    	figures.add(f);
    }
    
    // Change color of all figures in JPanel
    public void changeColors(){
    	for(int i=0; i<figures.size(); i++){
    		figures.get(i).shapeColor = Color.getHSBColor((float)Math.random(), 1, 1);
    	}
    }

    // Speed up all shapes in JPanel
    public void speedUp(){
    	for(int i=0; i<figures.size(); i++){
    		figures.get(i).speedUp();
    	}
    }
    
    // Slows down all shapes in JPanel
    public void slowDown(){
    	for(int i=0; i<figures.size(); i++){
    		figures.get(i).slowDown();
    	}
    }
    
    /**
     * Tells the ball to start moving. This is done by starting a Timer
     * that periodically executes a TimerTask. The TimerTask then tells
     * the ball to make one "step."
     */
    public void start() {
        timer = new Timer(true);
        timer.schedule(new Strobe(), 0, 40); // 25 times a second        
    }
    
    /**
     * Tells the ball to stop where it is.
     */
    public void pause() {
        timer.cancel();
    }
    
    /**
     * Tells the model to advance one "step."
     */
    private class Strobe extends TimerTask {
        @Override
        public void run() {
            for(int i=0; i<figures.size(); i++){
            	figures.get(i).makeOneStep();
            }            
            // Notify observers
            setChanged();
            notifyObservers();

        }
    }
}