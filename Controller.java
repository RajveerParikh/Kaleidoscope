/**
 * This is the main controller class for the program. It sets the JPanel
 * and adds functionality to all the buttons within the JPanel.
 * use of the Model-View-Controller design pattern and the Timer and
 * Observer/Observable classes.
 */
package kaleidoscope;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The Controller sets up the GUI and handles all the controls (buttons,
 * menu items, etc.)
 * 
 * @author David Matuszek
 * @author Selah Lynch
 * @author Rajveer Parikh
 */
public class Controller extends JFrame {
    JPanel buttonPanel = new JPanel();
    JButton runButton = new JButton("Run");
    JButton stopButton = new JButton("Stop");
    JButton newBallButton = new JButton("New Ball");
    JButton newTriButton = new JButton ("New Triangle");
    JButton newPlusButton = new JButton ("New Plus");
    JButton changeColorsButton = new JButton ("Change Colors");
    JButton speedUpButton = new JButton ("Speed Up");
    JButton slowDownButton = new JButton ("Slow Down");
    Timer timer;

    /** The Model is the object that does all the computations. It is
     * completely independent of the Controller and View objects. */
    Model model;
    
    /** The View object displays what is happening in the Model. */
    View view;
    
    /**
     * Runs the Kaleidoscope program.
     * @param args Ignored.
     */
    public static void main(String[] args) {
        Controller c = new Controller();
        c.init();
        c.display();
    }

    /**
     * Sets up communication between the components.
     */
    private void init() {
        model = new Model();     // The model is independent from the other classes
        view = new View(model);  // The view needs to know what model to look at
        model.addObserver(view); // The model needs to give permission to be observed
    }

    /**
     * Displays the GUI.
     */
    private void display() {
        layOutComponents();
        attachListenersToComponents();
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Arranges the various components in the GUI.
     */
    private void layOutComponents() {
        this.setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridLayout(3,3));
        this.add(BorderLayout.SOUTH, buttonPanel);
        buttonPanel.add(runButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(newBallButton);
        buttonPanel.add(newTriButton);
        buttonPanel.add(newPlusButton);
        buttonPanel.add(changeColorsButton);
        buttonPanel.add(speedUpButton);
        buttonPanel.add(slowDownButton);
        stopButton.setEnabled(false);
        this.add(BorderLayout.CENTER, view);
    }
    
    /**
     * Attaches listeners to the components, and schedules a Timer.
     */
    private void attachListenersToComponents() {
        // The Run button tells the Model to start
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                runButton.setEnabled(false);
                stopButton.setEnabled(true);
                model.start();
            }
        });
        // The Stop button tells the Model to pause
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                runButton.setEnabled(true);
                stopButton.setEnabled(false);
                model.pause();
            }
        });
        // The New Ball button tells the Model to add a new ball
        newBallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	//model.newBall();
            	model.addNewFigure(3,4, "circle");
            }
        });
        // The New Triangle button tells the Model to add a new triangle
        newTriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	model.addNewFigure(3,4, "triangle");
            }
        });
        // The New Plus button tells the Model to add a new plus
        newPlusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	//model.newBall();
            	model.addNewFigure(3,4, "plus");
            }
        });
        // The change color button tells the Model to randomly change colors for all objects
        changeColorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	//model.newBall();
            	model.changeColors();
            }
        });
        // The Speed up button tells the Model to increase speed for all objects
        speedUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	//model.newBall();
            	model.speedUp();
            }
        });
        // The Slow down button tells the Model to decrease speed for all objects
        slowDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	//model.newBall();
            	model.slowDown();
            }
        });
        // When the window is resized, the Model is given the new limits
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent arg0) {
                model.resizeWindow(view.getWidth(), view.getHeight());
            }
        });
    }
}