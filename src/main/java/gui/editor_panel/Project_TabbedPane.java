package gui.editor_panel;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import controllers.*;
import gui.editor_panel.project_panel.ProjectPanel;
import gui.new_project_start_view.ProjectStartView;

import javax.swing.*;

import creations.CreateFile;

import customcomponents.ProjectTabbedPaneUI;

import project.Project;

public class Project_TabbedPane extends JTabbedPane{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6080265632898839117L;
	
	public Project_TabbedPane(){
		
		init();
	}
	public Project_TabbedPane(String projectName){
		
		init();
		this.addTab(projectName);
		
	}
	public Project_TabbedPane(HashMap<String,Project> map){
		
		init();
		
		for(Iterator i =  map.keySet().iterator(); i.hasNext();){
			
			String projectName = (String)i.next();
			this.addTab(projectName);
		}
	}
	public void init(){

		registerKeyListeners();
		
		super.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		super.setUI((ProjectTabbedPaneUI) ProjectTabbedPaneUI.createUI(this));
	
		super.setName("Project_TabbedPane");
		GuiController.addComponent(this);
	}
	private Project_TabbedPane getProject_TabbedPane(){
		return this;
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
		 * KeyEvent to start a new File in the current focused Project
		 */
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK),"Current_Project_New_File"); 
		this.getActionMap().put("Current_Project_New_File", Current_Project_New_File);
		
		
		/*
		 * KeyEvent to close current selected Project.
		 */
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK),"Current_Project_Close_Project"); 
		this.getActionMap().put("Current_Project_Close_Project", Current_Project_Close_Project);
		
		/*
		 * KeyEvent to execute(if possible) current selected Project.
		 */
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_DOWN_MASK),"Current_Project_Execute"); 
		this.getActionMap().put("Current_Project_Execute", Current_Project_Execute);
		
		/*
		 * KeyEvent to terminate(if possible) current selected projects running program.
		 */
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.ALT_DOWN_MASK),"Current_Project_Terminate"); 
		this.getActionMap().put("Current_Project_Terminate", Current_Project_Terminate);
		
	}
	public void addTab(String projectName){

			super.addTab(projectName, new ProjectPanel(projectName));
	}
	public void addTab(String projectName, String fileName){
		
			super.addTab(projectName, new ProjectPanel(projectName,fileName));
	}
	public void reloadPane(){
		HashMap<String,Project> map = ProjectController.getProjectMap();
		for(Iterator i =  map.keySet().iterator(); i.hasNext();){
			
			String projectName = (String)i.next();
			this.addTab(projectName);
		}
	}
	/*
	 * Action to create a new File within the current focused project.
	 */
	Action Current_Project_New_File = new AbstractAction(){

		/**
		 * 
		 */
		private static final long serialVersionUID = -244571895895046865L;

		public void actionPerformed(ActionEvent arg0) {
			
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					
					new CreateFile();
				}
			});
		}	
	};
	
	
	/*
	 * Action to close the tab of the current focused project.
	 */
	Action Current_Project_Close_Project = new AbstractAction(){

		/**
		 * 
		 */
		private static final long serialVersionUID = -6351106988222118421L;

		public void actionPerformed(ActionEvent arg0) {
			
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					
					EditorPanel editPanel = (EditorPanel)GuiController.getComponent("EditorPanel");
					
					Project_TabbedPane pane = getProject_TabbedPane();
					
					if(pane.getTabCount() == 0){
						System.out.println("RegisterKeyBinding: Error CurrentProjectCloseProject: no tabs...");
						return;
					}
					else{
						
						String projectName = pane.getTitleAt(pane.getSelectedIndex());
						Project p = ProjectController.getProject(projectName);
						pane.remove(pane.getSelectedIndex());
						p.setProjectClose();
						
						/*
						 * These may or may not be present at this time, but simply
						 * give the call to remove them anyway just to be safe.
						 */
						GuiController.removeComponent(projectName + "_FileStartView");
						GuiController.removeComponent(projectName + "_ProjectPanel");
						
						if(pane.getTabCount() == 0){
							
							EditorPanel ep = (EditorPanel)GuiController.getComponent("EditorPanel");
							ep.remove((Project_TabbedPane)GuiController.getComponent("Project_TabbedPane"));
							if(GuiController.isComponentListed("ProjectStartView")){
								ProjectStartView view = (ProjectStartView)GuiController.getComponent("ProjectStartView");
								
								ep.addComponent(view,0,0,1,1,1,1);
							}else{
								ep.addComponent(new ProjectStartView(),0,0,1,1,1,1);
							}
							editPanel.revalidate();
							editPanel.startFadeInAnimation();
						}
					}
				}
			});
		}	
	};
	
	/*
	 * Action to execute the currently selectd project.
	 */
	Action Current_Project_Execute = new AbstractAction(){
		/**
		 * 
		 */
		private static final long serialVersionUID = -209288321228050988L;

		public void actionPerformed(ActionEvent e){
			String projectName = getProject_TabbedPane().getTitleAt(getProject_TabbedPane().getSelectedIndex());
			Project pro = ProjectController.getProject(projectName);
			pro.executeProject();
		}
	};
	
	/*
	 * Action to terminate the currently selected projects running program.
	 */
	Action Current_Project_Terminate = new AbstractAction(){


		/**
		 * 
		 */
		private static final long serialVersionUID = -7697101626087012422L;

		public void actionPerformed(ActionEvent e){
			String projectName = getProject_TabbedPane().getTitleAt(getProject_TabbedPane().getSelectedIndex());
			Project pro = ProjectController.getProject(projectName);
			if(!pro.getExecution().equals(null))
				pro.terminateProject();
		}
	};
}