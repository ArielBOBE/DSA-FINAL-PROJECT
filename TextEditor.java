package test;

import javax.swing.text.Document;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TextEditor{
	
	public static void main(String[] args) {
		JFrame window = new JFrame();
		
		// cannot resize
		window.setResizable(false);
		
		// close upon exit (x)
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setLocationRelativeTo(null);
		
		TextField textfield = new TextField();
		window.add(textfield);
		window.pack();
		window.setVisible(true);
		textfield.startThread();
	}
	
}