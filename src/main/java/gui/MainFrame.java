package gui;

import javax.swing.*;

import controllers.GuiController;

import environment.SystemClose;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainFrame extends JFrame implements WindowListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3445813639778025452L;

	private int x = 900,y = 600;
	
	private GridBagLayout layout;
	
	private GridBagConstraints constraints;
	
	private MainPanel mp;
	
	public MainFrame(){
		
		constraints = new GridBagConstraints();
		layout = new GridBagLayout();
		super.setLayout(layout);
		
		init();
		createComponents();
		
		super.pack();
		super.setVisible(true);
	}
	public void createComponents(){
		
		mp = new MainPanel();
		constraints.fill = GridBagConstraints.BOTH;
		addComponent(mp,0,0,1,1,1,1);
	}

	public void init(){
	
		super.setPreferredSize(new Dimension(x,y));
		super.setMinimumSize(new Dimension(x,y));
		super.setSize(new Dimension(x,y));
		
		super.setName("MainFrame");
		GuiController.addComponent(this);
		
		super.addWindowListener(this);
		super.setTitle("Cute...");
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		super.setResizable(true);
	}
	public void addComponent(Component c, int column, int row, int width, int height,int weightx,int weighty){
		constraints.gridx = column;
		constraints.gridy = row;
		constraints.gridheight = height;
		constraints.gridwidth = width;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		layout.setConstraints(c,constraints);
		this.getContentPane().add(c);
	}
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void windowClosing(WindowEvent arg0) {
		
		new SystemClose();
		
	}
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
