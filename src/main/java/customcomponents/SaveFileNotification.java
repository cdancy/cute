package customcomponents;

import gui.MainFrame;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import controllers.GuiController;

public class SaveFileNotification extends JComponent implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2703676471686977132L;
	
	private Graphics2D gd = null;
	
	private GradientPaint paint = null;
	
	private float step = 0.0f;
	
	public SaveFileNotification(String fileName){
		
		super.setLayout(new GridLayout(1,0));
		
		JLabel label = new JLabel(fileName + " has been saved!");
		label.setFont(new Font("Default",Font.BOLD,15));
		this.add(label);
		
		super.setSize(new Dimension(250,75));
		super.setPreferredSize(new Dimension(250,75));
		
		MainFrame frame = (MainFrame)GuiController.getComponent("MainFrame");
		int width = frame.getWidth()/2;
		int height = frame.getHeight()/2;
		JLayeredPane layer = frame.getLayeredPane();
		this.setLocation(width,height);
		layer.add(this, (Integer)(JLayeredPane.DEFAULT_LAYER + 402));

		new Thread(this).start();
	}

	public void paintComponent(Graphics g){
		gd = (Graphics2D)g.create();
		gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step));
		paint = new GradientPaint(0,0,new Color(252,242,224),getWidth(),getHeight(),new Color(247,218,165),true);
		gd.setPaint(paint);
		gd.fillRoundRect(0, 0, getWidth(), getHeight(),15,15);
		gd.dispose();
	}
	public void paintChildren(Graphics g){
		gd = (Graphics2D)g.create();
		gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step));
        super.paintChildren(gd);
	}
	public void run() {
		try{
				for(int num = 0; num < 20;num++){
					Thread.sleep(25);
					step += .05f;
					if(step > 1.0f)
						step = 1.0f;
					this.repaint();
				}
				step = 1.0f;
				this.repaint();
				
				try{
					Thread.sleep(1000);
						
					}catch(InterruptedException e){
						System.out.println(e);
					}
					
				for(int num = 0; num < 20;num++){
					Thread.sleep(25);
					step -= .05f;
					if(step < 0)
						step = 0.0f;
					this.repaint();
				}
				step = 0.0f;
				this.repaint();
				JLayeredPane.getLayeredPaneAbove(this).repaint(this.getX(), this.getY(), this.getWidth(), this.getHeight());
				JLayeredPane.getLayeredPaneAbove(this).remove(this);
		}catch(Exception e){
			System.out.println("SaveFileNotification...Error in Animation.");
		}
	}
}
