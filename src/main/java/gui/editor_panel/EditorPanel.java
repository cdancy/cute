package gui.editor_panel;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import controllers.*;

import gui.new_project_start_view.ProjectStartView;

import javax.swing.*;

import project.Project;
import creations.CreateProject;

public class EditorPanel extends JPanel implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6432685678066785348L;
	
	private BufferedImage background = null;

	private GridBagLayout layout = null;
	
	private GridBagConstraints constraints = null;
	
	private Graphics2D gd = null;
	
	private GradientPaint paint = null;
	
	private boolean animation = false;
	
	private float step = 1.0f;
	
	private final int animationSteps = 60;
	
	private boolean fadeIn = false,fadeOut = false;

	public EditorPanel(){

		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);
		
		init();
		createComponents();
	}
	public void init(){
		
		registerKeyListeners();
		
		super.setName("EditorPanel");
		GuiController.addComponent(this);
	}
	
	@SuppressWarnings("unchecked")
	public void registerKeyListeners(){
		/*
		 * Removing the traversal keys which are tab and shift so that I can use them
		 * for tabbing through project tabs.
		 */
		 Set forwardKeys = this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);  
		 Set newForwardKeys = new HashSet(forwardKeys);  
		 newForwardKeys.clear();  
		 this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,newForwardKeys); 
		 
		 forwardKeys = this.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS);  
		 newForwardKeys = new HashSet(forwardKeys);  
		 newForwardKeys.clear();  
		 this.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,newForwardKeys);
	
		 /*
			 * KeyEvent to create a new Project
			 */
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK),"New_Project"); 
			this.getActionMap().put("New_Project", New_Project);
			
			/*
			 * KeyEvent to open the project view.
			 */
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK),"Toggle_Project_View"); 
			this.getActionMap().put("Toggle_Project_View", Toggle_Project_View);
	}
	
	public void createComponents(){
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(5,7,7,7);
		
		if(ProjectController.isProjectControllerEmpty()){
			
			ProjectStartView projectStartView = new ProjectStartView();
			addComponent(projectStartView,0,0,1,1,1,1);
			
		}else{
			Project_TabbedPane project_TabbedPane = new Project_TabbedPane(ProjectController.getProjectMap());
			addComponent(project_TabbedPane,0,0,1,1,1,1);
		}
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

		if(background == null || background.getWidth() != this.getWidth() || background.getHeight() != this.getHeight()){
			background = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)background.getGraphics();
			gd.setColor(new Color(242,191,133));
			gd.fillRoundRect(0,0,getWidth(),getHeight(),20,20);
		}
		g.drawImage(background, 0, 0, this);
	}
	public void paintChildren(Graphics g){
		if(!isAnimationTime())
			super.paintChildren(g);
		else{
			gd = (Graphics2D)g.create();
			gd.setClip(g.getClip());
			gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step));
			super.paintChildren(gd);
		}
	}
	public void paintBorder(Graphics g){
		
		gd = (Graphics2D)g.create();
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		gd.setStroke(new BasicStroke(1.5f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
		paint = new GradientPaint(0,0,new Color(214,133,38),getWidth(),0,new Color(214,133,38),true);
		gd.setPaint(paint);
		gd.drawRoundRect(0, 0, getWidth()-2, getHeight()-2, 20, 20);
 
		gd.dispose();
	}
	public void startFadeInAnimation(){
		setFadeInAnimation(true);
		setAnimationTime(true);
		Thread t = new Thread(this);
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}
	public void startFadeOutAnimation(){
		setFadeOutAnimation(true);
		setAnimationTime(true);
		Thread t = new Thread(this);
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}
	private boolean isAnimationTime(){
		return animation;
	}
	private void setAnimationTime(boolean yes){
		this.animation = yes;
	}
	public void run() {
		
		Project_TabbedPane pane = (Project_TabbedPane)GuiController.getComponent("Project_TabbedPane");
		if(isFadeInAnimation()){

			this.step = 0.0f;
			for(int i = 0;i < this.getAnimationSteps();i++){
				try {
					Thread.sleep(9);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				step += .016f;
				this.repaint(pane.getX(), pane.getY(),pane.getWidth(),pane.getHeight());
			}
			if(step < 1){
				step = 1.0f;
				this.repaint(pane.getX(), pane.getY(),pane.getWidth(),pane.getHeight());
			}
			setAnimationTime(false);		
			setFadeInAnimation(false);
			
		}else if(isFadeOutAnimation()){

			this.step = 1.0f;
			for(int i = 0;i < this.getAnimationSteps();i++){
				try {
					Thread.sleep(9);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				step -= .016f;
				this.repaint(pane.getX(), pane.getY(),pane.getWidth(),pane.getHeight());
				System.out.println(step);
			}
			if(step < 0){
				step = 0.0f;
			}
			setAnimationTime(false);		
			setFadeOutAnimation(false);
		}else{
			System.out.println("Unrecognized animation type....");
		}
	}
	public int getAnimationSteps(){
		return animationSteps;
	}
	public boolean isFadeOutAnimation(){
		return fadeOut;
	}
	public void setFadeOutAnimation(boolean fadeOut){
		this.fadeOut = fadeOut;
	}
	public boolean isFadeInAnimation(){
		return fadeIn;
	}
	public void setFadeInAnimation(boolean fadeIn){
		this.fadeIn = fadeIn;
	}
	private EditorPanel getEditorPanel(){
		return this;
	}
	
	/*
	 * Action to display the Setup-A-Project view.
	 */
	Action Toggle_Project_View = new AbstractAction(){
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -4560185693643563317L;

		public void actionPerformed(ActionEvent arg0){

			SwingUtilities.invokeLater(new Runnable(){
				public void run(){

						EditorPanel editor = getEditorPanel();
						Component[] comp = editor.getComponents();
						if(comp.length > 1)
							return;
						if(comp[0] instanceof Project_TabbedPane){
							editor.remove((Project_TabbedPane)GuiController.getComponent("Project_TabbedPane"));
							if(GuiController.isComponentListed("ProjectStartView")){
								
								ProjectStartView view = (ProjectStartView)GuiController.getComponent("ProjectStartView");
								editor.addComponent(view, 0, 0, 1, 1, 1, 1);
							}else{
								
								editor.addComponent(new ProjectStartView(), 0, 0, 1, 1, 1, 1);
							}
							editor.revalidate();
							editor.startFadeInAnimation();
							return;
							
						}else if(comp[0] instanceof ProjectStartView){
							/*
							 * Check to see if any projects are available otherwise no
							 * point in bringing up Project_TabbedPane.
							 */
							if(ProjectController.getProjectMap().isEmpty()){
								
								System.out.println("RegisterKey: ProjectController is empty...return");
								return;
							}
							ProjectStartView view = (ProjectStartView)GuiController.getComponent("ProjectStartView");
							editor.remove(view);
							
							if(GuiController.isComponentListed("Project_TabbedPane")){
							
								Project_TabbedPane pane = (Project_TabbedPane)GuiController.getComponent("Project_TabbedPane");
								if(pane.getTabCount() >= 1)
									editor.addComponent(pane, 0, 0, 1, 1, 1, 1);
								else{
									pane.reloadPane();
									editor.addComponent(pane, 0, 0, 1, 1, 1, 1);
								}
							}else{
								HashMap<String, Project> table = ProjectController.getProjectMap();
								Project_TabbedPane pane = new Project_TabbedPane(table);
								editor.addComponent(pane, 0, 0, 1, 1, 1, 1);
							}
							editor.revalidate();
							editor.startFadeInAnimation();
							return;
						}else{
							System.out.println("Something is not right");
						}
					}
			});
		}
	};
	/*
	 * Action to create a new project.
	 */
	Action New_Project = new AbstractAction(){

		/**
		 * 
		 */
		private static final long serialVersionUID = 7475753900039762061L;

		public void actionPerformed(ActionEvent arg0) {
			
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					new CreateProject();
				}
			});
		}	
	};
}