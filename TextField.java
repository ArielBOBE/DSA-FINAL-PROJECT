package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class TextField extends JPanel{
	
	// max number of characters on window
	public int maxCharAcross = 75;
	public int maxCharAlong = 50;
	
	// char width and height
	public int charSize = 16; // in pixels
	
	// screen dimensions
	public int screenWidth = charSize * maxCharAcross;
	public int screenHeight = charSize * maxCharAlong;
	
	// FPS
	final int FPS = 120;
	
	// Creating new Thread
	Thread tfThread;
	
	// Keyboard and mouse inputs
	KeyHandler KeyH = new KeyHandler();
	
	// TextWriter object (handles insertion and deletion of text)
	TextWriter tw = new TextWriter(this, KeyH);
	
	// Fonts
	Font small = new Font("Times New Roman", Font.PLAIN, 14);
	
	public TextField() {
		// set window size
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.WHITE);
		
		// all drawings from this component will be done in an off screen painting buffer (makes faster) 
		this.setDoubleBuffered(true);
		this.addKeyListener(KeyH);
		this.setFocusable(true);
		
	}
	
	// using thread in order to have access to Thread.sleep to set frame rate
	public void startThread() {
		tfThread = new Thread();
		run();
	}
	
	public void run() {
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(tfThread != null) {
			
			this.update();
			this.repaint();
			
			double remainingTime = nextDrawTime - System.nanoTime();
			remainingTime = remainingTime/1000000;
			
			if (remainingTime < 0) {
				remainingTime = 0;
			}
			
			// pauses the program for remainingTime seconds
			try {
				Thread.sleep((long) remainingTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			nextDrawTime += drawInterval;
			
		}
	}
	
	public void update() {
		tw.update();
	}
	
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		tw.draw(g2);
		g2.dispose();
	}
}
