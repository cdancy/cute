package customcomponents;

import controllers.*;

import java.awt.AlphaComposite;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.MainFrame;
import gui.editor_panel.project_panel.Project_File_TabbedPane;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

import project.Project;

public class SaveFileAsUI extends JComponent implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 635237397070764906L;
	
	private GridBagLayout layout = null;
	
	private GridBagConstraints constraints = null;
	
	private float step = 0.0f;
	
	private Graphics2D gd = null;
	
	private GradientPaint paint = null;
	
	private boolean fadeToggle = true;
	
	private String projectName = null;
	
	private String fileName = null;
	
	private JLabel title = null;
	
	private JLabel fileTitle = null;
	
	private JTextField fileField = null;
	
	private Project_File_TabbedPane pane = null;
	
	public SaveFileAsUI(String projectName, String fileName,Project_File_TabbedPane pane){
		
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);
		
		this.pane = pane;
		this.projectName = projectName;
		this.fileName = fileName;
		
		this.setSize(new Dimension(300,100));
		this.setPreferredSize(new Dimension(300,100));
		
		createComponents();
		
		MainFrame frame = (MainFrame)GuiController.getComponent("MainFrame");
		JLayeredPane layer = frame.getLayeredPane();
		this.setLocation(450,250);
		layer.add(this, (Integer)(JLayeredPane.DEFAULT_LAYER + 401));
		this.openingAnimation();
	}
	public void createComponents(){
		title = new JLabel("Save file as...?");
		title.setFont(new Font("Default",20,30));
		constraints.insets = new Insets(10,5,0,10);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		//constraints.anchor = GridBagConstraints.CENTER;
		addComponent(title,0,0,2,1,1,0);
		
		constraints.insets = new Insets(0,5,0,10);
		addComponent(new Divider(),0,1,2,1,1,0);
		
		constraints.insets = new Insets(0,10,0,0);
		fileTitle = new JLabel("FileName:");
		fileTitle.setFont(new Font("Default",Font.BOLD,15));
		addComponent(fileTitle,0,2,1,1,1,1);
		
		constraints.insets = new Insets(0,0,0,10);
		fileField = new JTextField(getFileName());
		fileField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String newFileName = fileField.getText();
				if(newFileName.equals(getFileName())){
					System.out.println("FileName did not change...");
					closingAnimation();
					return;
				}
				Project p = ProjectController.getProject(getProjectName());
				if(p.doesFileExist(newFileName)){
					System.out.println("FileName already exists...");
					closingAnimation();
					return;
				}
				if(p.forceRenameFile(getFileName(), newFileName)){

					getTabbedPane().addTab(newFileName);
					p.removeFile(getFileName());
					closingAnimation();
				}
				closingAnimation();
			}
		});
		addComponent(fileField,1,2,1,1,1,0);
		
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
	public void openingAnimation(){
		setFadeInTime(true);
		new Thread(this).start();
	}
	public void closingAnimation(){
		setFadeInTime(false);
		new Thread(this).start();
	}
	public String getFileName(){
		return this.fileName;
	}
	public String getProjectName(){
		return this.projectName;
	}
	public Project_File_TabbedPane getTabbedPane(){
		return this.pane;
	}
	public void setFadeInTime(boolean b){
		this.fadeToggle = b;
	}
	public boolean isFadeInTime(){
		return fadeToggle;
	}
	public void clear(){
		
	}
	public void paintComponent(Graphics g){
		gd = (Graphics2D)g.create();
		gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step));
		paint = new GradientPaint(0,0,new Color(252,242,224),getWidth(),getHeight(),new Color(247,218,165),true);
		gd.setPaint(paint);
		gd.fillRoundRect(0, 0, getWidth(), getHeight(),15,15);
		gd.dispose();
	}
	/*
	public void paintBorder(Graphics g){
		gd = (Graphics2D)g.create();
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step));
		gd.setColor(Color.black);
		gd.drawRoundRect(0, 0, getWidth()-1, getHeight()-1,15,15);
		gd.dispose();
	}
	*/
	public void paintChildren(Graphics g){
		gd = (Graphics2D)g.create();
		gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step));
        super.paintChildren(gd);
	}
	public void run() {
		try{
			if(isFadeInTime()){
				for(int num = 0; num < 20;num++){
					Thread.sleep(25);
					step += .05f;
					if(step > 1.0f)
						step = 1.0f;
					this.repaint();
				}
				step = 1.0f;
				this.repaint();
			}else{
				for(int num = 0; num < 20;num++){
					Thread.sleep(25);
					step -= .05f;
					if(step < 0)
						step = 0.0f;
					this.repaint();
				}
				step = 0.0f;
				this.repaint();
				clear();
				JLayeredPane.getLayeredPaneAbove(this).repaint(this.getX(), this.getY(), this.getWidth(), this.getHeight());
				JLayeredPane.getLayeredPaneAbove(this).remove(this);
			}
		}catch(Exception e){
			System.out.println("SaveFileAsUI...Error in Animation.");
		}
	}
}
