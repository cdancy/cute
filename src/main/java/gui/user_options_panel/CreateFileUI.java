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
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import gui.editor_panel.Project_TabbedPane;

import javax.swing.*;

import controllers.GuiController;
import controllers.ProjectController;
import creations.CreateFile;
import customcomponents.Divider;

public class CreateFileUI extends JComponent implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3429426728755267354L;

	private GridBagLayout layout = null;
	
	private GridBagConstraints constraints = null;
	
	private BufferedImage background = null;
	
	private BufferedImage border = null;
	
	private float step = .0f;
	
	private boolean fadeStep = true;
	
	private Graphics2D gd = null;
	
	private JLabel createFile = null;
	
	private Divider topDivider = null;
	
	private JLabel fileName = null;
	
	private JTextField fileEnterField = null;
	
	private JLabel projectWelcomeTitle = null;
	
	private JLabel projectName = null;

	public CreateFileUI(){
		
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);
		
		super.setSize(new Dimension(600,400));
		super.setPreferredSize(new Dimension(600,400));
		super.setLocation(50, 50);
		
		super.setName("CreateFileUI");
		GuiController.addComponent(this);
		
		createComponents();
	}
	private void createComponents(){
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		constraints.insets = new Insets(5,5,0,5);
		createFile = new JLabel("Create a File?");
		createFile.setFont(new Font(null,Font.BOLD,40));
		addComponent(createFile,0,0,2,1,1,0);
		
		topDivider = new Divider(Color.gray,new Color(248,223,178));
		addComponent(topDivider,0,1,2,1,1,0);
		
		Project_TabbedPane pane = (Project_TabbedPane)GuiController.getComponent("Project_TabbedPane");
		String name = "";
		if(pane.getTabCount() == 0)
			name = null;
		else
			name = pane.getTitleAt(pane.getSelectedIndex());
		
		constraints.insets = new Insets(0,50,0,0);
		projectWelcomeTitle = new JLabel("Current Project Name:");
		projectWelcomeTitle.setFont(new Font(null,Font.BOLD,20));
		addComponent(projectWelcomeTitle,0,2,1,1,1,0);
		
		if(name == null)
			projectName = new JLabel("No Project Currently Selected...");
		else
			projectName = new JLabel("" + name);
		final String tempName = name;
		
		projectName.setForeground(Color.blue);
		projectName.setFont(new Font(null,Font.BOLD,20));
		addComponent(projectName,0,3,1,1,1,0);
		
		constraints.insets = new Insets(30,80,0,0);
		fileName = new JLabel("File Name:");
		fileName.setFont(new Font(null,Font.BOLD,20));
		addComponent(fileName,0,4,1,1,1,0);
		
		constraints.insets = new Insets(35,0,0,80);
		fileEnterField = new JTextField(20);
		fileEnterField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(tempName == null)
					closingAnimation();
				else{
					new CreateFile(fileEnterField.getText(),tempName);
					closingAnimation();
				}
			}
		});
		addComponent(fileEnterField,1,4,1,1,1,0);
		
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
