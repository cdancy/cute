package customcomponents;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPopupMenu;

public class TreePopupMenu extends JPopupMenu implements MouseListener,Runnable{

	private Graphics2D gd = null;
	
	private GradientPaint paint = null;
	
	private BufferedImage background = null;
	
	private BufferedImage border = null;
	
	private static final long serialVersionUID = 5415912463941675950L;
	
	private Thread timer = null;
	
	public TreePopupMenu(String label){
		
		super(label);
		super.addMouseListener(this);
	}
	public void paintComponent(Graphics g){
		
		if(background == null || background.getWidth() != this.getWidth() || background.getHeight() != this.getHeight()){
			
			background = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)background.getGraphics();
			paint = new GradientPaint(0,0,new Color(247,218,165),0,getHeight(),Color.white,true);
			gd.setPaint(paint);
			gd.fillRoundRect(0, 0, getWidth(), getHeight(),10,10);
			gd.dispose();
		}
		g.drawImage(background,0,0,this);
	}
	public void paintBorder(Graphics g){
		
		if(border == null || border.getWidth() != this.getWidth() || border.getHeight() != this.getHeight()){
			
			border = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)border.getGraphics();
			gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			gd.setColor(new Color(227,207,170));
			gd.drawRoundRect(0, 0, getWidth()-1, getHeight()-1,10,10);
			gd.dispose();
		}
		g.drawImage(border,0,0,this);
	}
	public void mouseClicked(MouseEvent arg0) {};
	public void mouseEntered(MouseEvent arg0) {
		if(timer != null && timer.isAlive())
			timer.interrupt();
	}
	public void mouseExited(MouseEvent arg0) {
		timer = new Thread(this);
		timer.start();
	}
	public void mousePressed(MouseEvent arg0) {};
	public void mouseReleased(MouseEvent arg0) {};
	public void run() {
		try{
			Thread.sleep(3000);
		}catch(InterruptedException e){
			return;
		}	
		this.setVisible(false);
	}
}