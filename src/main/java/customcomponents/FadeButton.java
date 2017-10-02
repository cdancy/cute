package customcomponents;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class FadeButton extends JButton implements MouseListener,Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3965440331486821129L;

	private BufferedImage background = null;
	
	private BufferedImage border = null;
	
	private Graphics2D gd = null;
	
	private GradientPaint paint = null;
	
	private Color gradientColor1 = null;
	
	private Color gradientColor2 = null;
	
	private boolean fadeTrigger = false;
	
	private float fadeStep = 1.0f;
	
	private boolean downCycle = true;
	
	private boolean pressedState = false;

	public FadeButton(String t,int newWidth,int newHeight, Color col1,Color col2){
		
		super(t);
		this.gradientColor1 = col1;
		this.gradientColor2 = col2;
		init(newWidth,newHeight);
	}
	public FadeButton(String t,int newWidth,int newHeight){
		
		super(t);
		this.gradientColor1 = Color.white;
		this.gradientColor2 = new Color(161,156,156);
		init(newWidth,newHeight);
	}
	public void init(int width,int height){
		
		super.setContentAreaFilled(false);
		super.addMouseListener(this);
		super.setSize(new Dimension(width,height));
		super.setPreferredSize(new Dimension(width,height));
		super.setMaximumSize(new Dimension(width,height));
		super.setMinimumSize(new Dimension(width,height));
		
	}
	public void paintComponent(Graphics g){
		
		if(background == null || background.getWidth() != getWidth() || background.getHeight() != getHeight()){
			
			background = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)background.getGraphics();
			paint = new GradientPaint(0,0, gradientColor1, getWidth(),getHeight(),gradientColor2, true);
			gd.setPaint(paint);
			gd.fillOval(0, 0, getWidth(), getHeight());
			gd.dispose();
		}
		if(!getFadeTrigger() && !this.isPressed())
			g.drawImage(background,0,0,this);
		else if(getFadeTrigger() && !this.isPressed()){
			gd = (Graphics2D)g.create();
			gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeStep));
			gd.drawImage(background,0,0,this);
			gd.dispose();
		}else if(this.isPressed()){
			gd = (Graphics2D)g.create();
			gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
			paint = new GradientPaint(0,0, gradientColor2, getHeight(),getWidth(),gradientColor1, true);
			gd.setPaint(paint);
			gd.fillOval(0, 0, getWidth(), getHeight());
			gd.dispose();
		}
	}
	
	public void paintBorder(Graphics g){
		if(border == null || border.getWidth() != this.getWidth() || border.getHeight() != this.getHeight()){
			border = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)border.getGraphics();
			gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			gd.setPaint(new GradientPaint(0,0,Color.gray.darker(), 0,getHeight(),Color.white, true));
			gd.setStroke(new BasicStroke(1.3f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER));
			gd.drawOval(0, 0, getWidth()-1, getHeight()-1);
			gd.dispose();
		}	
		g.drawImage(border,0,0,this);
	}
	public void mouseClicked(MouseEvent arg0) {
		
		
	}
	public void mouseEntered(MouseEvent arg0) {
		setFadeTrigger(true);
		new Thread(this).start();
		return;
	}
	public void mouseExited(MouseEvent arg0) {
		this.setDepressed();
		setFadeTrigger(false);
		fadeStep = 1.0f;
	}
	public void mousePressed(MouseEvent arg0) {
		setFadeTrigger(false);
		fadeStep = 1.0f;
		this.setPressed();
	}
	public boolean isPressed(){
		return pressedState;
	}
	public void setPressed(){
		if(!pressedState)
			pressedState = true;
	}
	public void setDepressed(){
		if(isPressed())
			pressedState = false;
	}
	public void mouseReleased(MouseEvent arg0) {};
	
	private void setFadeTrigger(boolean trigger){
		this.fadeTrigger = trigger;
	}
	private boolean getFadeTrigger(){
		return fadeTrigger;
	}
	private boolean isDownCycle(){
		return downCycle;
	}
	private void setCycle(boolean cycle){
		downCycle = cycle;
	}
	public void run() {
			while(getFadeTrigger()){
				try {
					if(isDownCycle()){
						
						Thread.sleep(25);
						fadeStep -= .05f;
						if(fadeStep < .4f){
							setCycle(false);
						}
						this.repaint();
					}else{
						
						Thread.sleep(25);
						fadeStep += .05f;
						if(fadeStep > 1.0f){
							fadeStep = 1.0f;
							setCycle(true);
						}
						this.repaint();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	}
}
