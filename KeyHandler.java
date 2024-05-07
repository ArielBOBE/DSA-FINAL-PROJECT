package test;

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
	
	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		this.keyPress = true;
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			this.bSpace = true;
		}
		
		// is true only if character is defined
		if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
			this.undefined = false;
			this.character = e.getKeyChar();
		}
		else {
			this.undefined = true;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.enter = true;
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		this.keyPress = false;
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			this.bSpace = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.enter = false;
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
