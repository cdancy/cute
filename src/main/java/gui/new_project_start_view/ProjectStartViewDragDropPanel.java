package gui.new_project_start_view;

import javax.swing.*;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/*
 * This class is only meant to be added to ProjectStartView() before a project is started
 * . Any use outside of this is not recommended and will probably act in some odd way. 
 * Its mearly here to implement drag and drop of outside workspace folders into cute.
 * Though more functionality will be added in the future.
 */
public class ProjectStartViewDragDropPanel extends JComponent implements MouseListener,DropTargetListener,Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -259685330268084697L;

	private Graphics2D gd = null;
	
	private int ClickCounter = 0;
	
	private int width = 400, height = 225;
	
	private float fadeStep = 0.0f;
	
	private boolean cycle = false;
	
	private boolean fadeTrigger = false;
	
	private BufferedImage border = null;
	
	public ProjectStartViewDragDropPanel(){
		
		init();
	}
	private void init(){
		
		super.addMouseListener(this);
		
		new DropTarget(this,this);
		
		super.setPreferredSize(new Dimension(width,height));
		super.setMaximumSize(new Dimension(width,height));
		super.setMinimumSize(new Dimension(width,height));
		super.setSize(new Dimension(width,height));
	}
	public void paintComponent(Graphics g){
		
		gd = (Graphics2D)g.create();
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		gd.setColor(new Color(242,191,133));
		gd.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
		gd.setColor(Color.black);
		gd.drawString("Drag and Drop Projects here!", 10, 15);
		gd.drawString("-Need to import an external project?", 10, 50);
		gd.drawString(" Drag the Folder here!!!", 10, 80);
		gd.drawString("-Need to QuickStart a new Project?", 10, 110);
		gd.drawString(" Click on New-Project!!!", 10, 140);
		gd.drawString("-Need to Hack on a File?", 10, 170);
		gd.drawString(" Click on Hack-A-File!!!", 10, 200);
		gd.dispose();
	}
	public void paintBorder(Graphics g){
		
		if(border == null || border.getWidth() != this.getWidth() || border.getHeight() != this.getHeight()){
			border = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)border.getGraphics();
			gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			gd.setStroke(new BasicStroke(5,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
			gd.setPaint(new GradientPaint(0,0,new Color(252,242,224),getWidth(),getHeight(),new Color(247,218,165),true));	gd.drawRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
			gd.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 40, 40);
			gd.dispose();
		}
		if(!getFadeTrigger())
			super.paintBorder(g);
		else{
			gd = (Graphics2D)g.create();
			gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeStep));
			gd.drawImage(border,0,0,null);
			gd.dispose();
		}
	}

	public void mouseClicked(MouseEvent arg0) {
		
		if(ClickCounter >= 1 || ClickCounter < 0){
			ClickCounter = 0;
		}else
			ClickCounter++;
		
	}
	public int getClickCount(){
		
		return ClickCounter;
	}
	public void mouseEntered(MouseEvent arg0) {
		
		setFadeTrigger(true);
		new Thread(this).start();
		return;
	}
	public void mouseExited(MouseEvent arg0) {
		
		setFadeTrigger(false);
	}
	public void mousePressed(MouseEvent arg0) {
		
		return;
	}
	public void mouseReleased(MouseEvent arg0) {
			
		return;
	}
	public void dragEnter(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void drop(DropTargetDropEvent dtde) {
		Transferable tr = dtde.getTransferable();
	    DataFlavor[] flavors = tr.getTransferDataFlavors();
	    	    
	    for(int i = 0;i< flavors.length;i++){
	    	
	    	if(flavors[i].isFlavorTextType()){
	    		System.out.println("1 "+flavors[i].getPrimaryType());
	    		System.out.println("2 "+flavors[i].getSubType());
	    		System.out.println("3 "+flavors[i].getHumanPresentableName());
		
	    	}
	    }
		
	}
	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	private void setFadeTrigger(boolean newTrigger){
		this.fadeTrigger = newTrigger;
	}
	private boolean getFadeTrigger(){
		return fadeTrigger;
	}
	private boolean isDownCycle(){
		return cycle;
	}
	private void setCycle(boolean newCycle){
		this.cycle = newCycle;
	}
	public void run(){
		while(getFadeTrigger()){
			try {
				if(isDownCycle()){
					
					Thread.sleep(50);
					fadeStep -= .05f;
					if(fadeStep < .4f){
						setCycle(false);
					}
					this.repaint();
				}else{
					
					Thread.sleep(50);
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
