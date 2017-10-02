package gui.titlebar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TitleButton extends JButton implements MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -416675481260390447L;

	private int width,height;
	
	private Color color1,color2;
	
	private Graphics2D gd;
	
	private GradientPaint paint;
	
	private Paint oldPaint;
	
	private final int staticState = 0,enterState = 1,pressState = 2;
	
	private int paintState = staticState;
	
	public TitleButton(String name){
		
		super(name);
		color1 = Color.gray;
		color2 = Color.orange;
		init();
		
	}
	public TitleButton(int width,int height){
		
		color1 = Color.gray;
		color2 = Color.orange;
		this.width = width;
		this.height = height;
		init();
		
	}
	public TitleButton(String name,int width,int height){
		
		super(name);
		color1 = Color.gray;
		color2 = Color.orange;
		this.width = width;
		this.height = height;
		init();
		
	}
	public TitleButton(String name,Color color1,Color color2){
		
		super(name);
		this.color1 = color1;
		this.color2 = color2;
		init();
		
	}
	public TitleButton(Color color1,Color color2,int width,int height){
		
		this.color1 = color1;
		this.color2 = color2;
		this.width = width;
		this.height = height;
		init();
		
	}
	public TitleButton(String name,Color color1,Color color2,int width,int height){
		
		super(name);
		this.color1 = color1;
		this.color2 = color2;
		this.width = width;
		this.height = height;
		init();
		
	}
	public void init(){
		
		super.setSize(new Dimension(width,height));
		super.setPreferredSize(new Dimension(width,height));
		super.setMaximumSize(new Dimension(width,height));
		super.setMinimumSize(new Dimension(width,height));
		super.setOpaque(false);
		super.addMouseListener(this);
		super.setBorder(new EmptyBorder(2,2,2,2));
	}
	public void paintComponent(Graphics g){
		
		gd = (Graphics2D)g.create();
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		oldPaint = gd.getPaint();
		
		switch(paintState){
		
			case staticState: staticPaint(gd);	break;
			case enterState:  enterPaint(gd); 	break;
			case pressState:  pressPaint(gd);	break;
			default: 		  staticPaint(gd);  break;
		}
		
		
		gd.fillRoundRect(0, 0, this.getWidth(), this.getHeight(),50,50);
		gd.setPaint(oldPaint);
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_DEFAULT); 
		gd.dispose();
		
	}

	public void staticPaint(Graphics2D g){
		
		paint = new GradientPaint(0,0,color1,getWidth(),getHeight(),color2,true);
		gd.setPaint(paint);
	}
	public void enterPaint(Graphics2D g){
		
		paint = new GradientPaint(0,0,color2,getWidth(),getHeight(),color1,true);
		gd.setPaint(paint);
	}
	public void pressPaint(Graphics2D g){
		
		paint = new GradientPaint(0,0,color2.darker(),getWidth(),getHeight(),color1.darker(),true);
		gd.setPaint(paint);
	}
	public void mouseClicked(MouseEvent arg0) {
		paintState = pressState;
		
	}
	public void mouseEntered(MouseEvent arg0) {
		paintState = enterState;
	}
	public void mouseExited(MouseEvent arg0) {
		paintState = staticState;
		
	}
	public void mousePressed(MouseEvent arg0) {
		paintState = pressState;
		
	}
	public void mouseReleased(MouseEvent arg0) {
		paintState = enterState;
		
	}
}