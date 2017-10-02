package customcomponents;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class RoundButton extends JButton implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4152460100277793923L;

	private int width = 40,height = 40;
	
	private int arcwidth = 40,archeight = 40;
	
	private int clickCounter = 0;
	
	private Color gradientColor1,gradientColor2;
	
	private BufferedImage static_image = null,rollover_image = null,
							pressed_image = null,border = null;
	
	private final int static_state = 0,rollover_state = 1,pressed_state = 2;
	
	private int current_state = static_state;
	
	private GradientPaint staticPaint,rolloverPaint,pressedPaint;
	
	private Graphics2D gd = null;
	
	private final int exitState = 0,enterState = 1;
	
	private int mouseState = 0;
	
	public RoundButton(String t){
		
		super(t);
		this.gradientColor1 = Color.white;
		this.gradientColor2 = Color.gray;
		init();
		
	}
	public RoundButton(String t, Color col1,Color col2){
		
		super(t);
		this.gradientColor1 = col1;
		this.gradientColor2 = col2;
		init();
	}
	public RoundButton(Color col1,Color col2){
		
		this.gradientColor1 = col1;
		this.gradientColor2 = col2;
		init();
	}
	public RoundButton(String t,int newWidth,int newHeight){
		
		super(t);
		this.width = newWidth;
		this.height = newHeight;
		this.gradientColor1 = Color.white;
		this.gradientColor2 = Color.LIGHT_GRAY;
		init();
	}
	public RoundButton(int newWidth,int newHeight){
		
		this.width = newWidth;
		this.height = newHeight;
		this.gradientColor1 = Color.white;
		this.gradientColor2 = Color.gray;
		init();
	}
	
	public RoundButton(String t,int newWidth,int newHeight, Color col1,Color col2){
		
		super(t);
		this.width = newWidth;
		this.height = newHeight;
		this.gradientColor1 = col1;
		this.gradientColor2 = col2;
		init();
	}
	public void init(){
		
		super.setContentAreaFilled(false);
		super.addMouseListener(this);
		super.setSize(new Dimension(width,height));
		super.setPreferredSize(new Dimension(width,height));
		super.setMaximumSize(new Dimension(width,height));
		super.setMinimumSize(new Dimension(width,height));
		
	}
	public void paintBorder(Graphics g){
		
		if(border == null || border.getWidth() != this.getWidth() || border.getHeight() != this.getHeight()){
			border = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)border.getGraphics();
			gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			gd.setPaint(new GradientPaint(0,0,Color.gray.darker(), 0,getHeight(),Color.white, true));
			gd.setStroke(new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
			gd.drawRoundRect(0, 0, getWidth()-1, getHeight()-1,arcwidth,archeight);
			gd.dispose();
		}
		g.drawImage(border,0,0,this);
	}
	public void paintComponent(Graphics g){
		
		switch(current_state){
		
		case	static_state: 	paint_static_state(g); 		break;
		case	rollover_state:	paint_rollover_state(g);	break;
		case	pressed_state:	paint_pressed_state(g);		break;
		default:				paint_static_state(g);		break;	
		}
	}
	public void paint_static_state(Graphics g){
		
		if(static_image == null || static_image.getWidth() != getWidth() || static_image.getHeight() != getHeight()){
			
			static_image = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)static_image.getGraphics();
			staticPaint = new GradientPaint(0,0, gradientColor1, getWidth(),getHeight(),gradientColor2, true);
			gd.setPaint(staticPaint);
			gd.fillRoundRect(0,0,getWidth(),getHeight(),arcwidth,archeight);
			paintText(gd);
			gd.dispose();
		}
		g.drawImage(static_image,0,0,null);
	}
	public void paint_rollover_state(Graphics g){
		
		if(rollover_image == null || rollover_image.getWidth() != getWidth() || rollover_image.getHeight() != getHeight()){
			
			rollover_image = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)rollover_image.getGraphics();
			rolloverPaint = new GradientPaint(0,0,Color.white,getWidth(),getHeight(),gradientColor2.darker(), true);
			gd.setPaint(rolloverPaint);
			gd.fillRoundRect(0,0,getWidth(),getHeight(),arcwidth,archeight);
			paintText(gd);
			gd.dispose();
		}
		g.drawImage(rollover_image,0,0,null);
	}
	public void paint_pressed_state(Graphics g){
		
		if(pressed_image == null || pressed_image.getWidth() != getWidth() || pressed_image.getHeight() != getHeight()){
			
			pressed_image = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)pressed_image.getGraphics();
			pressedPaint = new GradientPaint(0,0,gradientColor2.brighter(), 0,getHeight() ,new Color(254,218,151), true);
			gd.setPaint(pressedPaint);
			gd.fillRoundRect(0,0,getWidth(),getHeight(),arcwidth,archeight);
			paintText(gd);
			gd.dispose();
		}
		g.drawImage(pressed_image,0,0,null);
	}
	public void paintText(Graphics2D gd){
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		String text = this.getText();
		if(text != null){
			FontMetrics fm = gd.getFontMetrics();
			int h = fm.getAscent();
			gd.setColor(Color.black);
			gd.drawString(text,20,(this.getHeight()+h)/2);
		}
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_DEFAULT); 
	}
	public int getClickCount(){
		
		return clickCounter;
	}
	public void mouseClicked(MouseEvent arg0) {
		
		clickCounter++;
		if(clickCounter == 2 || clickCounter > 2)
			clickCounter = 0;
		
	}
	public void mouseEntered(MouseEvent arg0) {
		
		mouseState = enterState;
		current_state = rollover_state;
		
	}
	public void mouseExited(MouseEvent arg0) {
		
		mouseState = exitState;
		current_state = static_state;
		
	}
	public void mousePressed(MouseEvent arg0) {
		
		current_state = pressed_state;
		
	}
	public void mouseReleased(MouseEvent arg0) {
		
		if(mouseState == exitState){
			current_state = static_state;
		}else{
			current_state = rollover_state;
		}
	}
}
