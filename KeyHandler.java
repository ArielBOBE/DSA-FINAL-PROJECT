package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class KeyHandler implements KeyListener {
	
	// character attribute; what gets typed onto screen
	private char character;
	
	// attribute that shows if key is pressed
	private boolean keyPress = false;
	
	// attribute that shows if back space is pressed; if pressed deletion in TW class
	private boolean bSpace = false;
	
	// attribute that shows if an undefined character is pressed; if pressed, will NOT get typed 
	private boolean undefined = false;
	
	// attribute that shows if enter key is pressed; if true, new line
	private boolean enter = false;
	
	public boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false;
	
	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		this.keyPress = true;
		int keyCode = e.getKeyCode();
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			this.bSpace = true;
		}
		
		// is true only if character is defined
		if (keyCode != KeyEvent.CHAR_UNDEFINED) {
			this.undefined = false;
			this.character = e.getKeyChar();
		}
		else {
			this.undefined = true;
		}
		
		if (keyCode == KeyEvent.VK_SHIFT) {
			this.undefined = true;
		}
		
		if (keyCode == KeyEvent.VK_ALT) {
			this.undefined = true;
		}
		
		if (keyCode == KeyEvent.VK_ENTER) {
			this.enter = true;
		}
		
		if (keyCode == KeyEvent.VK_UP) {
			this.upPressed = true;
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			this.downPressed = true;	
		}
		if (keyCode == KeyEvent.VK_LEFT) {
			this.leftPressed = true;
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			this.rightPressed = true;
		}

		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		this.keyPress = false;
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_BACK_SPACE) {
			this.bSpace = false;
		}
		else if (keyCode == KeyEvent.VK_ENTER) {
			this.enter = false;
		}
		
		if (keyCode == KeyEvent.VK_UP) {
			this.upPressed = false;
		}
		if (keyCode == KeyEvent.VK_DOWN) {
			this.downPressed = false;	
		}
		if (keyCode == KeyEvent.VK_LEFT) {
			this.leftPressed = false;
		}
		if (keyCode == KeyEvent.VK_RIGHT) {
			this.rightPressed = false;
		}
	}
	
	// returns defined character
	public char getChar() {
		return this.character;
	}
	
	// if true keyboard key is pressed, else not
	public boolean getKeyPress() {
		return this.keyPress;
	}
	
	public boolean getArrowRelease() {
		if (!this.upPressed && !this.rightPressed && !this.leftPressed && !this.downPressed) {
			return true;
		}
		return false;
	}
	
	// returns true if back space is pressed
	// if true will delete from string builder && linked list
	public boolean getBSpace() {
		return this.bSpace;
	}
	
	public boolean getEnter() {
		return this.enter;
	}
	
	// returns true if a key pressed is undefined
	// if true will NOT add to the string builder && linked list
	public boolean isUndefined() {
		return this.undefined;
	}

}
