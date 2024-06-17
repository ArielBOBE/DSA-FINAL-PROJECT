package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseHandler implements MouseListener, MouseWheelListener{

	int currentScroll = 0, lastScroll = currentScroll;
	int scrollValue;
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		lastScroll = currentScroll;
		// adding to current scroll
	
		int scrollState = e.getWheelRotation();
		
		currentScroll += scrollState;
		
		if (scrollState == 1) {
			scrollValue++;
		}
		else if (scrollState == -1) {
			scrollValue--;
		}
		
//		if (e.getWheelRotation() == 1) {
//			this.scrollDown = true;
//		}
//		else if (e.getWheelRotation() == -1) {
//			this.scrollUp = true;
//		}
//		else {
//			this.scrollDown = false;
//			this.scrollUp = false;
//		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Scroll value: "+scrollValue);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
