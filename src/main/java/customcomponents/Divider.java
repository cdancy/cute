package customcomponents;

import java.awt.*;
import javax.swing.*;

public class Divider extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8232032603224162461L;

	private Color color1,color2;
	
	private GradientPaint paint;
	
	private Paint oldPaint;
	
	private Graphics2D gd;
	
	private boolean isVertical = false;
	
	public Divider(){
		
		color1 = Color.gray;
		color2 = Color.white;
		init();
	}
	public Divider(boolean isVert){
		
		this.isVertical = isVert;
		color1 = Color.gray;
		color2 = Color.white;
		init();
	}
	public Divider(Color col1,Color col2){
		this.color1 = col1;
		this.color2 = col2;
		init();
	}
	public Divider(Color col1,Color col2,boolean isVert){
		
		this.isVertical = isVert;
		this.color1 = col1;
		this.color2 = col2;
		init();
	}
	public void init(){
		
	}
	public void paintComponent(Graphics g){
		
		gd = (Graphics2D)g.create();
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		oldPaint = gd.getPaint();
		
		if(!isVertical){
			paint = new GradientPaint(0,0,color1,getWidth(),getHeight()/10,color2,true);
			gd.setPaint(paint);
			gd.fillRoundRect(0, 0, getWidth(), 3, 10, 10);
		}else{
			paint = new GradientPaint(0,0,color1,getWidth(),getHeight(),color2,true);
			gd.setPaint(paint);
			gd.fillRoundRect(0, 0, 3, getHeight(), 10, 10);
		}
		gd.setPaint(oldPaint);
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_DEFAULT);
		gd.dispose();
	}
}
