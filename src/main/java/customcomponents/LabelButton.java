package customcomponents;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

public class LabelButton extends JButton implements MouseListener,Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -29494246392658437L;

	private String buttonName = "";
	
	private BufferedImage staticStateImage = null;
	
	private BufferedImage rolloverStateImage = null;
	
	private BufferedImage pressedStateImage = null;

	private final int staticState = 0,rolloverState = 1,pressedState = 2;
	
	private int currentState = staticState;
	
	private GradientPaint paint = null;
	
	private Graphics2D gd = null;
	
	private boolean fadeTrigger = false;
	
	private float fadeStep = 1.0f;
	
	private boolean downCycle = true;
	
	public LabelButton(String label){
		
		this.buttonName = label;
		super.setBorder(new EmptyBorder(2,2,2,2));
		super.setContentAreaFilled(false);
		init();
	}
	public void init(){
		
		super.addMouseListener(this);
		int nameLength = buttonName.length();
		super.setSize(new Dimension(nameLength,30));
		super.setPreferredSize(new Dimension(nameLength,30));
	}
	public void paintBorder(Graphics g){
	
		super.paintBorder(g);
	}
	
	@SuppressWarnings("unused")
	public void paintComponent(Graphics g){
		
		switch(currentState){
		
			case staticState: paintStaticState(g);break;
			case rolloverState: paintRolloverState(g);break;
			case pressedState: paintPressedState(g);break;
			default: staticState: paintStaticState(g);break;
		} 
	}
	public void paintStaticState(Graphics g){
		
		if(staticStateImage == null || staticStateImage.getWidth() != getWidth() || staticStateImage.getHeight() != getHeight()){
			staticStateImage = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)staticStateImage.getGraphics();
			gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			paint = new GradientPaint(0,0,new Color(247,218,165),0,getHeight()+5,Color.black,true);
			gd.setPaint(paint);
			gd.setFont(new Font(null,1,15));
			gd.drawString(buttonName, 0, 20);
			gd.dispose();
		}
		g.drawImage(staticStateImage,0,0,this);
	}
	public void paintRolloverState(Graphics g){
		
		if(rolloverStateImage == null || rolloverStateImage.getWidth() != getWidth() || rolloverStateImage.getHeight() != getHeight()){
			rolloverStateImage = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)rolloverStateImage.getGraphics();
			gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			paint = new GradientPaint(0,0,new Color(247,218,165),0,getHeight(),Color.black,true);
			gd.setPaint(paint);
			gd.setFont(new Font(null,1,20));
			gd.drawString(buttonName, 0, 20);
			gd.dispose();
		}
		if(!getFadeTrigger())
			g.drawImage(rolloverStateImage,0,0,this);
		else{
			gd = (Graphics2D)g.create();
			gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeStep));
			gd.drawImage(rolloverStateImage,0,0,this);
			gd.dispose();
		}
	}
	public void paintPressedState(Graphics g){
		
		if(pressedStateImage == null || pressedStateImage.getWidth() != getWidth() || pressedStateImage.getHeight() != getHeight()){
			pressedStateImage = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)pressedStateImage.getGraphics();
			gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			paint = new GradientPaint(0,0,new Color(247,218,165),0,getHeight(),Color.darkGray,true);
			gd.setPaint(paint);
			gd.setFont(new Font(null,1,15));
			gd.drawString(buttonName, 0, 20);
			gd.dispose();
		}
		g.drawImage(pressedStateImage,0,0,this);
	}
	public float getFadeStep(){
		return fadeStep;
	}
	public boolean getFadeTrigger(){
		return fadeTrigger;
	}
	public void setFadeTrigger(boolean trigger){
		this.fadeTrigger = trigger;
	}
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseEntered(MouseEvent arg0) {
		currentState = rolloverState;
		setFadeTrigger(true);
		new Thread(this).start();
	}
	public void mouseExited(MouseEvent arg0) {
		currentState = staticState;
		setFadeTrigger(false);
	}
	public void mousePressed(MouseEvent arg0) {
		currentState = pressedState;
		
	}
	public void mouseReleased(MouseEvent arg0) {
		currentState = rolloverState;
		
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
					
					Thread.sleep(40);
					fadeStep -= .05f;
					if(fadeStep < .4f){
						setCycle(false);
					}
					this.repaint();
				}else{
					
					Thread.sleep(40);
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
