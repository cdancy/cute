package customcomponents;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JMenuItem;

public class CuteMenuItem extends JMenuItem implements MouseListener,MouseMotionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1336122646978594054L;

	public CuteMenuItem(String s){
		
		super(s);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	public void mouseClicked(MouseEvent arg0) {
		this.fireStateChanged();
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {

		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent arg0) {
		
	}
}
