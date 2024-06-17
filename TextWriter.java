package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Canvas;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class TextWriter {
	TextField tf;
	KeyHandler KeyH;
	MouseHandler mouseH;
	
	LinkedList<ArrayList<Character>> lines = new LinkedList<>(); 
	
	// used to store the information per line as string so that don't have to convert many rows of char to string
	// every loop
	LinkedList<String> newStrings = new LinkedList<>();
	
	// counter (frames) for rapid insertion
	int addCounter = 0;
	
	// counter (frames) for deletion
	int delCounter = 0;
	
	// counter (frames) for cursor movement 
	int cursorCounter = 0;
	
	// Fonts
	Font small = new Font("Times New Roman", Font.PLAIN, 25);
	FontMetrics fm;
	Canvas c = new Canvas();
	
	// cursor (index of string builder) position
	int cursorX = 0;
	
	// cursor (index of Linked List of SB) position
	int cursorY = 0;
	
	// start X/Y coordinates of text
	int startX = 10;
	int startY = 30;
	int currentY = startY;
	
	// next X/Y coordinates of text
	int nextX;
	int nextY = 20;
	
	// current and previous characters
	char currChar;
	char prevChar;
	
	// up down left right count
	int vertCount = 0;
	int horCount = 0;
	
	long nanoTime = 0;
	
	// String to be produced
	StringBuilder sb;
	
	public TextWriter(TextField tf, KeyHandler KeyH, MouseHandler mouseH) {
		this.tf = tf;
		this.KeyH = KeyH;
		this.mouseH = mouseH;
		this.sb = new StringBuilder();
		this.autoAdd(0, 10000, 100, 'a');
	}
	
	public void newLine(Graphics2D g2) {
		for(int i = 0; i < this.lines.size(); i++) {
			
			String currentString = this.newStrings.get(i);
			
			this.currentY = this.startY + (this.startY * i);
			
			// to draw and automatically scroll when going past the border screen
			if (this.lines.size() > 25) {
				this.currentY = this.currentY - (this.startY * (this.lines.size() - 25));
			}
			
			// to scroll up and down the screen when text is too long
			if (this.lines.size() > 25 && isScrolling()) {
				if ((scrollValue()) < (this.lines.size() - 25)) {
					this.currentY = this.currentY - (this.startY * (scrollValue()));
				}
				if ((scrollValue()) > 0) {
					mouseH.scrollValue--;
				}
				if ((scrollValue())*-1 > (this.lines.size() - 25)) {
					mouseH.scrollValue++;
				}
//				System.out.println("Scroll v -1: "+ scrollValue()*-1);
//				System.out.println("Input size diff: "+(this.lines.size() - 25));
			}	
			
			if (this.lines.size() <= 25) {
				mouseH.scrollValue = 0;
			}
			
			g2.drawString(currentString, this.startX, this.currentY);
		}
	}
	
	public int scrollValue() {
		return mouseH.scrollValue;
	}
		
	public boolean isScrolling() {
		if (mouseH.scrollValue != 0) {
			return true;
		}
		return false;
	}
		
	public boolean isAtBorder(String line) {
		fm = c.getFontMetrics(this.small);
		int width = fm.stringWidth(line);
		if (width >= this.tf.screenWidth - 30) {
			return true;
		}
		return false;
	}
		
	public void drawCursor(Graphics2D g2) {
		fm = c.getFontMetrics(this.small);

		int widthAtX = 0;
		if (cursorY == newStrings.size()) {
			widthAtX = fm.stringWidth(this.newStrings.get(cursorY - 1).substring(0, cursorX));
		}
		else if (cursorY < newStrings.size()){
			widthAtX = fm.stringWidth(this.newStrings.get(cursorY).substring(0, cursorX));
		}
		g2.drawString("|", widthAtX + startX, currentY + (this.startY * vertCount));
	}
		
	public void moveCursor() {
		if (cursorCounter >= 1) {
			cursorCounter++;
		}
		
		if (KeyH.getArrowRelease()) {
			cursorCounter = 0;
		}
		
		if (cursorCounter < 1 || cursorCounter >= 72) {
			if (KeyH.upPressed && cursorY > 0) {
				cursorCounter++;
				cursorY --;
				vertCount --;
				if (this.lines.get(cursorY).size() - 1 <= 0) {
					cursorX = 0;
					}
				else if (this.lines.get(cursorY).size() - 1 < cursorX) {
					cursorX = this.lines.get(cursorY).size() - 2;
				}
			}
			if (KeyH.downPressed && cursorY < this.lines.size() - 1) {
				cursorCounter++;
				cursorY ++;
				vertCount++;
				if (this.lines.get(cursorY).size() - 1 <= 0) {
					cursorX = 0;
				}
				else if (this.lines.get(cursorY).size() - 1 < cursorX) {
					cursorX = this.lines.get(cursorY).size();
				}
			}
			if (KeyH.leftPressed && cursorX > 0) {
				cursorCounter++;
				cursorX --;
				horCount --;
				
			}
			if (KeyH.rightPressed && cursorX < this.lines.get(cursorY).size()) {
				cursorCounter++;
				cursorX ++;
				horCount++;
			}	
		}
		
//		System.out.println("Cursor Y:"+cursorY);
//		System.out.println("Curosr X:"+cursorX);
	}
		
	public int getNumChar() {
		int numChar = 0;
		for (int i = 0; i < this.lines.size(); i++) {
			for (int j = 0; j < this.lines.get(i).size(); j++) {
				numChar++;
			}
		}
		return numChar;
	}

	
		
	public int getNumCharRow(int curY) {
		int numChar = 0;
		
		for (int j = 0; j < this.lines.get(curY).size(); j++) {
			numChar++;
		}
		
		return numChar;
	}
		
	public void addChar0() {
		// new line character to be added to the end of each line
		long startTime = System.nanoTime();
		if (currChar == '\n') {
			// cursorY increases, new Line!
			cursorY ++;
			
			// cursorX resets to 0, start from the first column
			cursorX = 0;
			
			if (lines.size() <= cursorY) {
				lines.add(new ArrayList<>());
				long elapsedTime = (System.nanoTime() - startTime);
				System.out.println("Inserting new row time: "+elapsedTime);
				nanoTime += elapsedTime;
				newStrings.add(new String());
			}
			
			else if (cursorY < lines.size()) {
				lines.add(cursorY, new ArrayList<>());
				long elapsedTime = (System.nanoTime() - startTime);
				System.out.println("Inserting new row time: "+elapsedTime);
				nanoTime += elapsedTime;
				newStrings.add(cursorY, new String());
			}
		}
		else {
			// if the currChar is NOT new line, append as normal 
			// adding to linkedlist index y and index x of that LL
			lines.get(cursorY).add(cursorX, currChar);
			long elapsedTime = (System.nanoTime() - startTime);
			nanoTime += elapsedTime;
			
			System.out.println("Approximate time in nano time: "+(elapsedTime));
			System.out.println("Cumulative time: "+nanoTime);
			
			System.out.println("Number of characters: "+getNumChar());
			System.out.println("Line number: "+(cursorY +1));
//			System.out.println("Num char at "+(cursorY+1)+" : "+getNumCharRow(cursorY));
//			System.out.println("Shifting from X at: "+cursorX);
			// cursorX is incremented
			cursorX ++;
		}
		System.out.println("Cumulative time: "+nanoTime);
		
		System.out.println("Number of characters: "+getNumChar());
	}
	
	public void autoAdd(int startRow, int endRow, int numChar, char c) {
		while (lines.size() < startRow) {
			// add blank line
			lines.add(new ArrayList<>());
			newStrings.add(new String());
			newStrings.set(cursorY, getString(lines.get(cursorY)));
			cursorY++;
		}
		int currRow = startRow;
		while (currRow < endRow) {
			// add blank line
			long startTime = System.nanoTime();
			lines.add(new ArrayList<>());
			long elapsedTime = (System.nanoTime() - startTime);
			newStrings.add(new String());
			
			
			
			
			while (lines.get(currRow).size() < numChar) {
				startTime = System.nanoTime();
				lines.get(currRow).add(c);
				elapsedTime = (System.nanoTime() - startTime);
				nanoTime += elapsedTime;
				cursorX++;
				System.out.println("Approximate time in nano time: "+(elapsedTime));
				System.out.println("Cumulative time: "+nanoTime);
			}
			newStrings.set(currRow, getString(lines.get(currRow)));
			cursorY++;
			cursorX = 0;
			currRow++;
		}
	}
		
	public void delete0() {
		if (cursorY > 0 && this.lines.get(cursorY).isEmpty()) {
			// LinkedList<Character> is removed from the lines LinkedList
			long startTime = System.nanoTime();
			lines.remove(cursorY);
			long elapsedTime = System.nanoTime() - startTime;
			System.out.println("Approximate time to delete: "+(elapsedTime));
			
			newStrings.remove(cursorY);
			cursorY--;
			
			int latestLine = cursorY;
			
			// if size of latest line > 0, cursorX is very right of line
			if (lines.get(latestLine).size() > 0) {
				cursorX = lines.get(latestLine).size();
			}
			// if size is 0, cursorX is at the beginning
			else if (lines.get(latestLine).size() == 0) {
				cursorX = 0;
			}
		}
		
		if (!this.lines.get(cursorY).isEmpty() && cursorX == 0 && cursorY != 0) {
			if (this.lines.get(cursorY - 1).size() != 0) {
				cursorX = this.lines.get(cursorY - 1).size()-1;
				// see time here?
				long startTime = System.nanoTime();
				lines.get(cursorY - 1).remove(cursorX);
				long elapsedTime = System.nanoTime() - startTime;
				System.out.println("Approximate time to delete: "+(elapsedTime));
				cursorY = cursorY - 1;
				vertCount--;
			}
			else {
				// or see time here
				long startTime = System.nanoTime();
				lines.remove(cursorY - 1);
				long elapsedTime = System.nanoTime() - startTime;
				System.out.println("Approximate time to delete: "+(elapsedTime));
				cursorY--;

			}
		}
		else if (!this.lines.get(cursorY).isEmpty() && cursorX > 0) {
			long startTime = System.nanoTime();
			lines.get(cursorY).remove(cursorX-1);
			long elapsedTime = System.nanoTime() - startTime;
			System.out.println("Approximate time to delete: "+(elapsedTime));
			cursorX --;
		}
	}
		
	// adds char to LL by returning each character pressed when pressed
	public void addChar() {
		if (KeyH.getKeyPress() && !KeyH.getBSpace() && !KeyH.isUndefined() && 
				!KeyH.upPressed && !KeyH.downPressed &&!KeyH.leftPressed &&!KeyH.rightPressed) {
			
			currChar = KeyH.getChar();
			
			// counter is used to prevent rapid insertion of characters, ensures its only one character
			// per key press
			if (lines.size() <= cursorY) {
				
				// maybe measure time here?
				long startTime = System.nanoTime();
				lines.add(new ArrayList<>());
				long elapsedTime = System.nanoTime() - startTime;
				System.out.println("Inserting new row time: "+elapsedTime);
				newStrings.add(new String());
			}
			
			if (addCounter < 1 || currChar != prevChar) {
				prevChar = currChar;
				
				// measure time here
				addChar0();
			}
			
			// if user holds down the key for 72 frames, rapid insertion will occur 
			else if (addCounter >= 72 && currChar == prevChar) {
				prevChar = currChar;
				
				// here too
				addChar0();
			}
			
			if (isAtBorder(newStrings.get(cursorY))) {
				long startTime = System.nanoTime();
				lines.add(cursorY + 1, new ArrayList<>());
				long elapsedTime = System.nanoTime() - startTime;
				System.out.println("Inserting new row time: "+elapsedTime);
				newStrings.add(cursorY + 1, new String());
				cursorY++;
				cursorX = 0;
			}
			
//			System.out.println(lines.toString());
//			System.out.println("cursorX:"+cursorX);
//			System.out.println("cursorY: "+cursorY);
			
			// call method to convert current line into string and add to the linked list of strings
			newStrings.set(cursorY, getString(lines.get(cursorY)));
			addCounter++;
		}
		// if key release, counter resets to 0;
		else if (!KeyH.getKeyPress()) {
			addCounter = 0;
		}
	}
	
	// deletion method
	public void delete() {
		if (KeyH.getBSpace()) {
			if(delCounter < 1 && cursorX >= 0) { // to ensure that one char deleted per key press; not rapid
				// measure time here
				delete0();
			}
			else if (delCounter >= 72 && cursorX >= 0) { // after holding for 72 frames, rapid deletion
				// measure time here
				delete0();
			}
//			System.out.println(lines.toString());
//			System.out.println("CursorX:"+cursorX);
//			System.out.println("CursorY:"+cursorY);
			newStrings.set(cursorY, getString(lines.get(cursorY)));
			delCounter++;
		}
		else if (!KeyH.getBSpace()) {
			delCounter = 0;
		}
	}
		
	// converts string builder to String
	public String getString(ArrayList<Character> row) {
		
		// number of lines == number of elements in the linked list
		int numChars = row.size();
		
		for (int i = 0; i < numChars; i++) {
			sb.append(row.get(i));	
		}
		
		// storing contents of string builder in local variable
		String lineAsString= sb.toString();
		
		// clearing string builder to be used in next iteration
		sb.setLength(0);

		return lineAsString;
	}
		
	public void update() {
		moveCursor();
		addChar();
		delete();
	}
	
	public void draw(Graphics2D g2) {
		g2.setFont(this.small);
		newLine(g2);
		if (this.lines.size() > 0) {
			drawCursor(g2);
		}
		
	}
}
