package gui.user_options_panel;

import gui.MainFrame;

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
import java.util.Date;

import javax.swing.*;

import controllers.GuiController;
import customcomponents.Divider;
import customcomponents.FadeButton;
/*
 * This class is intended to be only added to the MainFrame class. Its added to a
 * JLayeredPane to provide a To-Do list for all Cute system wide purposes.
 * 
 * This JComponent is initially set to setVisible(false) because we simply dont want
 * to see unless the user click the todo list button. Than we set it to setVisible(true);
 */
public class CuteToDoListUI extends JComponent implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8806048547184630053L;
	
	private GridBagLayout layout = null;
	
	private GridBagConstraints constraints = null;
	
	private BufferedImage background = null;
	
	private BufferedImage border = null;
	
	private float step = .0f;
	
	private boolean fadeStep = true;
	
	private Graphics2D gd = null;
	
	private ButtonOptionsPanel optionsPanel = null;
	
	private NotePanel notePanel = null;
	
	public CuteToDoListUI(){
		
		init();
		
		super.setSize(new Dimension(600,400));
		super.setPreferredSize(new Dimension(600,400));
		super.setLocation(50, 50);
		
		createComponents();
	}
	public CuteToDoListUI(MainFrame frame){
		
		init();
		
		super.setSize(new Dimension(600,400));
		super.setPreferredSize(new Dimension(600,400));
		super.setLocation(60, 60);
		
		createComponents();
	}
	
	public void init(){
		
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);
		
		super.setName("CuteToDoListUI");
		GuiController.addComponent(this);
	}
	public void createComponents(){
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(3,10,3,10);
		JLabel label = new JLabel("TODO.list");
		label.setFont(new Font("Default",Font.BOLD,40));
		addComponent(label,0,0,1,1,1,0);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(3,10,3,10);
		addComponent(new Divider(),0,1,1,1,1,0);
		
		optionsPanel = new ButtonOptionsPanel();
		addComponent(optionsPanel,0,2,1,1,1,0);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(10,10,10,10);
		notePanel = new NotePanel();
		addComponent(notePanel,0,3,1,1,1,1);
		
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
	public void loadToDoList(){
		
	}
	public void saveToDoList(){
		
	}
	public class NotePanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4487775874197981413L;

		private Box b = null;
		
		private JScrollPane scrollPane = null;
		
		public NotePanel(){
			
			b = new Box(BoxLayout.Y_AXIS);
			super.setSize(new Dimension(100,100));
			super.setPreferredSize(new Dimension(100,100));
			super.setOpaque(false);
			
			scrollPane = new JScrollPane(notePanel);
			scrollPane.getViewport().setOpaque(false);
			scrollPane.setBorder(null);
			scrollPane.setOpaque(false);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			b.add(scrollPane);
			
			super.add(b);
		}
		public void addNote(Note n){
			b.add(n);
			this.revalidate();
			this.repaint();
		}
	}
	public class ButtonOptionsPanel extends JPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 3729633081056513810L;

		private BufferedImage border = null;
		
		private Graphics2D gd = null;
		
		private FadeButton newNote = null,removeNote = null;
		
		private GridBagLayout layout = null;
		
		private GridBagConstraints constraints = null;
		
		public ButtonOptionsPanel(){
			
			layout = new GridBagLayout();
			constraints = new GridBagConstraints();
			super.setLayout(layout);
			
			super.setSize(new Dimension(100,35));
			super.setPreferredSize(new Dimension(100,35));
			super.setOpaque(false);
			
			constraints.fill = GridBagConstraints.BOTH;
			constraints.anchor = GridBagConstraints.EAST;
			constraints.insets = new Insets(0,2,0,2);
			newNote = new FadeButton("",25,25,Color.white,new Color(151,228,56));
			newNote.addActionListener(new ActionListener(){
				 public void actionPerformed(ActionEvent e){
					 notePanel.addNote(new Note());
				 }
			 });
			addComponent(newNote,1,0,1,1,0,0);
			
			removeNote = new FadeButton("",25,25,Color.white,new Color(225,40,27));
			removeNote.addActionListener(new ActionListener(){
				 public void actionPerformed(ActionEvent e){
					 
				 }
			 });
			addComponent(removeNote,0,0,1,1,0,0); 
			
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
	}
	public class Note extends JPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -4578274131107287962L;

		private GridBagLayout layout = null;
		
		private GridBagConstraints constraints = null;
		
		private JTextArea noteArea = null;
		
		private JLabel timeLabel = null;
		
		public Note(){
			
			layout = new GridBagLayout();
			constraints = new GridBagConstraints();
			super.setLayout(layout);
	        
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.WEST;
			timeLabel = new JLabel(new Date().toString());
			addComponent(timeLabel,0,0,1,1,0,0);
			
			constraints.fill = GridBagConstraints.BOTH;
			noteArea = new JTextArea(40,40);
			noteArea.setLineWrap(true);
			addComponent(noteArea,0,1,1,1,1,1);
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
	}
}
