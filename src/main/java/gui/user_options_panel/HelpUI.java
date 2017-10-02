package gui.user_options_panel;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import gui.MainFrame;

import javax.swing.*;

import controllers.GuiController;

public class HelpUI extends JComponent implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7761878344844557528L;

	private GridBagLayout layout = null;
	
	private GridBagConstraints constraints = null;
	
	private BufferedImage background = null;
	
	private BufferedImage border = null;
	
	private float step = .0f;
	
	private boolean fadeStep = true;
	
	private Graphics2D gd = null;
	
	public HelpUI(MainFrame frame){
		
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);
		
		super.setSize(new Dimension(600,400));
		super.setPreferredSize(new Dimension(600,400));
		super.setLocation(50, 50);
		
		super.setName("HelpUI");
		GuiController.addComponent(this);
		
		createComponents();
	}
	public void createComponents(){
		
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.CENTER;
		JLabel label = new JLabel("Help is on the way...");
		label.setFont(new Font("Default",Font.BOLD,40));
		addComponent(label,0,0,1,1,1,1);
	}
	public void addComponent(Component c, int column, int row, int width, int height,int weightx,int weighty){
		
		constraints.gridx = column;
		constraints.gridy = row;
		constraints.gridheight = height;
		constraints.gridwidth = width;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		layout.setConstraints(c,constraints);
		this.add(c);
	}
	public void paintComponent(Graphics g){
		
		if(background == null || this.getWidth() != background.getWidth() || this.getHeight() != background.getHeight()){
			background = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)background.getGraphics();
			GradientPaint paint = new GradientPaint(0,0,new Color(247,218,165),0,getHeight(),Color.white,true);
			gd.setPaint(paint);
			gd.fillRoundRect(0, 0, getWidth(), getHeight(),20,20);
		}
		gd = (Graphics2D)g.create();
		gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step));
		gd.drawImage(background,0,0,this);
		gd.dispose();
	}
    protected void paintChildren(Graphics g) {
		gd = (Graphics2D)g.create();
		gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step));
        super.paintChildren(gd);
    }
	public void paintBorder(Graphics g){
		
		if(border == null || border.getWidth() != this.getWidth() || border.getHeight() != this.getHeight()){
			border = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)border.getGraphics();
			gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			gd.setStroke(new BasicStroke(1.5f));
			gd.setPaint(Color.gray);
			gd.drawRoundRect(0, 0, getWidth()-1, getHeight()-1,20,20);
			gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_DEFAULT);
		}
		gd = (Graphics2D)g.create();
		gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step));
		gd.drawImage(border,0,0,this);
		gd.dispose();
	}
	public void run() {
		try{
			if(getFadeStep()){
				for(int num = 0; num < 20;num++){
					Thread.sleep(25);
					step += .05f;
					if(step > 1.0f)
						step = 1.0f;
					this.repaint();
				}
				this.setFadeStep(false);
			}else{
				for(int num = 0; num < 20;num++){
					Thread.sleep(25);
					step -= .05f;
					if(step < 0)
						step = 0.0f;
					this.repaint();
				}
				JLayeredPane.getLayeredPaneAbove(this).repaint(this.getX(), this.getY(), getWidth(), getHeight());
				JLayeredPane.getLayeredPaneAbove(this).remove(this);
				this.setFadeStep(true);
			}
		}catch(Exception e){
			System.out.println("ChatUI...Error in Animation.");
		}
	}
	public void setFadeStep(boolean step){
		fadeStep = step;
	}
	public boolean getFadeStep(){
		return fadeStep;
	}
	public void closingAnimation(){
		new Thread(this).start();
	}
	public void openingAnimation(){
		new Thread(this).start();
	}
}
