package test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.Font;
import java.awt.Graphics2D;

public class TextWriter {
	TextField tf;
	KeyHandler KeyH;
	LinkedList<Character> charMap = new LinkedList<>();
	ArrayList<Integer> newLineIndex = new ArrayList<>();
	
	// counter (frames) for rapid insertion
	int addCounter = 0;
	
	// counter (frames) for deletion
	int delCounter = 0;
	
	// Fonts
	Font small = new Font("Times New Roman", Font.PLAIN, 25);
	
	// cursor (index of LL) position
	int cursor = 0;
	
	// start X/Y coordinates of text
	int startX = 10;
	int startY = 20;
	
	// next X/Y coordinates of text
	int nextX;
	int nextY = 20;
	
	// current and previous characters
	char currChar;
	char prevChar;
	
	// String to be produced
	StringBuilder page;
	
	public TextWriter(TextField tf, KeyHandler KeyH) {
		this.tf = tf;
		this.KeyH = KeyH;
		this.page = new StringBuilder();
	}
	
	// recursive function to DRAW new lines "\n" in separate lines 
	public void newLine(String str, int Y, Graphics2D g2) {
		// index of \n in the string
		int k;
		
		// str1 is substring of str until the first \n encountered
		String str1 = str;
		
		// str2 is the rest of str to be used as input
		String str2 = "";
		
		// the Y-pos of the line
		int currY = Y;
		
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '\n') {
				k = i;
				str1 = str.substring(0, k);
				str2 = str.substring(k);
				break;
			}
		}
		
		g2.drawString(str1, startX, currY);
		currY += nextY;
		if (str2.equals("")) {
			return;
		}
				
		newLine(str2.substring(1), currY, g2);
	}
	
	// recursive function to DRAW new lines "\n" in separate lines 
		public void newLine2(String str, int Y, Graphics2D g2) {
			// index of \n in the string
			int k;
			
			// str1 is substring of str until the first \n encountered
			String str1 = str;
			
			// str2 is the rest of str to be used as input
			String str2 = "";
			
			// the Y-pos of the line
			int currY = Y;
			
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == '\n') {
					k = i;
					str1 = str.substring(0, k);
					str2 = str.substring(k);
					break;
				}
			}
			
			g2.drawString(str1, startX, currY);
			currY += nextY;
			if (str2.equals("")) {
				return;
			}
					
			newLine2(str2.substring(1), currY, g2);
		}
	
	// adds char to LL by returning each character pressed when pressed
	public void addChar() {
		if (KeyH.getKeyPress() && !KeyH.getBSpace() && !KeyH.isUndefined()) {
			currChar = KeyH.getChar();
			
			// counter is used to prevent rapid insertion of characters, ensures its only one character
			// per key press
			if (addCounter < 1 || currChar != prevChar) {
				prevChar = currChar;
				if (currChar == '\n') {
					// linked list of indexes.add(cursor position)
					// need to make a cursor class first?
				}
				charMap.add(KeyH.getChar());
				System.out.print(charMap);
			}
			// if user holds down the key for 72 frames, rapid insertion will occur 
			else if (addCounter >= 72 && currChar == prevChar) {
				charMap.add(KeyH.getChar());
				System.out.println(charMap);
			}
			addCounter++;
		}
		// if key release, counter resets to 0;
		else if (!KeyH.getKeyPress()) {
			addCounter = 0;
		}
	}
	
	// deletion method
	public void delete() {
		if (KeyH.getBSpace() && cursor >= 0 && cursor != charMap.size()) {
			if(delCounter < 1) { // to ensure that one char deleted per key press; not rapid
				charMap.remove(cursor);
				page.deleteCharAt(cursor);
			}
			else if (delCounter >= 72) { // after holding for 72 frames, rapid deletion
				charMap.remove(cursor);
				page.deleteCharAt(cursor);
			}
			delCounter++;
		}
		else if (!KeyH.getBSpace()) {
			delCounter = 0;
		}
	}
	
	
	// converts string builder to String
	public String getString() {
		
		if (KeyH.getKeyPress()) {
			// cursor position == latest character unless made otherwise
			if (charMap.size() > 0) {
				cursor = charMap.size() - 1;
			}
			else {
				System.out.println(charMap.size());
			}
			
			// index of LL and StringBuilder (SB) corresponds each other
			// Iteration starts from latest index of SB in order to avoid duplication
			for(int i = page.length(); i < charMap.size(); i++) {
				System.out.println(charMap.size());
				page.append(charMap.get(i));
			} 	
		}
		
		return page.toString();
	}
		
	public void update() {
		addChar();
		delete();
	}
	
	public void draw(Graphics2D g2) {
		g2.setFont(this.small);
		newLine(getString(), startY, g2);
		
	}
}
