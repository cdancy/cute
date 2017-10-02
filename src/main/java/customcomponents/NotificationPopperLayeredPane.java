package customcomponents;

import java.awt.*;

import javax.swing.*;

public class NotificationPopperLayeredPane extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -256239516610328517L;

	private boolean paintingNow = true;
	
	public NotificationPopperLayeredPane(){
		
		//SwingUtilities.invokeLater(this);
		
	}
	public void setShown(boolean b){
		
		paintingNow = b;
	}
	public boolean getShown(){
		return paintingNow;
	}
	public static void pushNotification(String s){
	
		System.out.println(s);
		
	}
	public void paintComponent(Graphics g){
		
		
			g.setColor(Color.black);
			g.fillOval(0, 0, 20, 20);
	
	}
}