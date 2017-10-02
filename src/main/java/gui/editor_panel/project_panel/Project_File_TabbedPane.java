package gui.editor_panel.project_panel;

import gui.MainFrame;
import gui.new_file_start_view.FileStartView;

import java.awt.AlphaComposite;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.*;

import project.Project;

import controllers.*;
import customcomponents.ProjectTabbedPaneUI;
import customcomponents.SaveFileAsUI;
import customcomponents.SaveFileNotification;

public class Project_File_TabbedPane extends JTabbedPane implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1972690477187310155L;
	
	private String projectName = null;
	
	private String openTab = null;
	
	private int selectedTab = 0;
	
	private float step = 1.0f;
	
	private Project_File_TabbedPane fileTabbedPane = null;
	
	private Graphics2D gd = null;
	
	/*
	 * Opens project_file_tabbedpane with all file tabs open
	 */
	public Project_File_TabbedPane(String projectName){
		
		this.projectName = projectName;
		init();
		
	}
	
	/*
	 * Opens project_file_tabbedpane with only one tab open, which corresponds
	 * to the string for openTab. This is mainly for Other classes which want to 
	 * pop up the Project_File_TabbedPane with only one open tab. It's a convenience
	 * constructor of sorts.
	 */
	public Project_File_TabbedPane(String projectName,String openTab){
		
		this.projectName = projectName;
		this.openTab = openTab;
		init();
		
	}
	public void init(){
		
		/*
		 * Needed for the KeyListener within registerKeyListeners
		 */
		fileTabbedPane = this;
	
		registerKeyListeners();
		
		super.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		super.setUI((ProjectTabbedPaneUI) ProjectTabbedPaneUI.createUI(this));
		super.setName(projectName + "_Project_File_TabbedPane");
		GuiController.addComponent(this);
		
		Project pro = ProjectController.getProject(projectName);
		if(openTab == null){
			for(Iterator i =  pro.getOpenFileList().iterator(); i.hasNext();){
				
				String fileName = (String)i.next();
				this.addTab(fileName, new FileEditArea(projectName,fileName));
			}
		}else{
			
			this.addTab(openTab, new FileEditArea(projectName,openTab));
		}
	}
	public String getProjectName(){
		return this.projectName;
	}
	public Project_File_TabbedPane getProject_File_TabbedPane(){
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
		  * KeyEvent to save the currently selected Tab's File.
		  */
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK),"Current_Project_Save_File"); 
		this.getActionMap().put("Current_Project_Save_File", Current_Project_Save_Selected_File_Tab);
	
		/*
		 * KeyEvent to save the currently selected Tab's File as another name.
		 */
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK),"Current_Project_Save_File_As"); 
		this.getActionMap().put("Current_Project_Save_File_As", Current_Project_Save_File_As);
		
		/*
		 * KeyEvent to tab to the next file in the current Projects file tab
		 */
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.CTRL_DOWN_MASK),"Tab_Current_Project_Files"); 
		this.getActionMap().put("Tab_Current_Project_Files", Tab_Current_Project_Files);
	
		/*
		 * KeyEvent to close the current projects focused file.
		 */
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK),"Current_Project_Close_File_Tab"); 
		this.getActionMap().put("Current_Project_Close_File_Tab", Current_Project_Close_File_Tab);
	}
	
	/*
	 * Over riding default addTab for our purposes.
	 */
	public void addTab(String fileName){
		
		Project pro = ProjectController.getProject(this.projectName);
		pro.addOpenFile(fileName);
		super.addTab(fileName, new FileEditArea(projectName,fileName));
	}
	
	/*
	 * Over riding default addTab for our purposes.
	 */
	public void addTab(String fileName,Component comp){
		
		Project pro = ProjectController.getProject(this.projectName);
		pro.addOpenFile(fileName);
		super.addTab(fileName, comp);
	}
	
	/*
	 * Over riding default remove at index for our purposes.
	 */
	public void remove(int index){
		String tempName = this.getTitleAt(index);
		Project pro = ProjectController.getProject(this.projectName);
		pro.removeOpenFile(tempName);
		super.remove(index);
	}

	protected void paintChildren(Graphics g){
		gd = (Graphics2D)g.create();
		gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step));
        super.paintChildren(gd);
	}
	public void setAnimationStepToZero(){
		this.step = 0.0f;
	}
	public void run() {
		try{
				step = 0.0f;
				for(int num = 0; num < 20;num++){
					Thread.sleep(25);
					step += .05f;
					if(step > 1.0f)
						step = 1.0f;
					this.repaint();
				}
				step = 1.0f;
				this.repaint();
		}catch(Exception e){
			System.out.println("Project_File_TabbedPane...animation error");
		}
	}
	public void openingAnimation(){
		new Thread(this).start();
	}
	
	Action Current_Project_Save_File_As = new AbstractAction(){
		/**
		 * 
		 */
		private static final long serialVersionUID = -4121789263470841574L;

		public void actionPerformed(ActionEvent e){
			
			MainFrame frame = (MainFrame)GuiController.getComponent("MainFrame");
			JLayeredPane layer = frame.getLayeredPane();
			Component [] comp = layer.getComponentsInLayer((Integer)(JLayeredPane.DEFAULT_LAYER + 401));
			if(comp.length < 1 ){
				
				Project_File_TabbedPane pane = getProject_File_TabbedPane();
				Point p = pane.getLocation();
				SwingUtilities.convertPointToScreen(p,frame);

				new SaveFileAsUI(getProjectName(),pane.getTitleAt(pane.getSelectedIndex()),pane);
					
			}else if(comp.length >= 1 && comp[0] instanceof SaveFileAsUI){
				
				SaveFileAsUI save = (SaveFileAsUI)comp[0];
				save.closingAnimation();
			}
		}
	};
	Action Current_Project_Save_Selected_File_Tab = new AbstractAction(){

		/**
		 * 
		 */
		private static final long serialVersionUID = 1403271122765268143L;

		public void actionPerformed(ActionEvent arg0) {
			
				selectedTab = fileTabbedPane.getSelectedIndex();
				FileEditArea area = (FileEditArea)fileTabbedPane.getComponentAt(selectedTab);
				Project pro = ProjectController.getProject(area.getProjectName());
				pro.saveFile(area.getFileName(), area.getCurrentText());
				new SaveFileNotification(area.getProjectName());
		}	
	};
	
	/*
	 * Action to tab through the list of tabbed files in the current focused project.
	 */
	Action Tab_Current_Project_Files = new AbstractAction(){

		/**
		 * 
		 */
		private static final long serialVersionUID = 5537555811718593189L;

		public void actionPerformed(ActionEvent arg0) {
			
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					
					Project_File_TabbedPane filePane = getProject_File_TabbedPane();
					
					//If no tabs then return. If 1 tab then no need to tab, so return.
					//If multiple tabs, check to see if selected tab is last in line
					//or not. if last go back to zero if not move up one.
				
					if(filePane.getTabCount() == 0)
						return;
					if(filePane.getTabCount() == 1)
						return;
					if(filePane.getTabCount() > 1){
				
						int filePaneIndex = filePane.getSelectedIndex();
						if(filePaneIndex == filePane.getTabCount() - 1){

							filePane.setSelectedIndex(0);
						}else{

							filePane.setSelectedIndex(++filePaneIndex);
						}
					}else{
						System.out.println("RegisterKeyBindings: something strange is going on...");
						return;
					}
				}
			});
		}	
	};
	
	/*
	 * Action to close the tab of current focused file of the current focused project.
	 */
	Action Current_Project_Close_File_Tab = new AbstractAction(){

		/**
		 * 
		 */
		private static final long serialVersionUID = -8190857978187785271L;

		public void actionPerformed(ActionEvent arg0) {
			
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					
					
					Project_File_TabbedPane filePane = getProject_File_TabbedPane();
					int fileCount = filePane.getTabCount();
					if(fileCount == 0)
						return;
					else{
						
						int fileIndex = filePane.getSelectedIndex();
						filePane.remove(fileIndex);
						
						/*
						 * If there are no more tabs on the panel after removing a tab
						 * than display the FileStarView panel.
						 */
						if(filePane.getTabCount() == 0){
							ProjectPanel panel = (ProjectPanel)GuiController.getComponent(getProjectName() + "_ProjectPanel");
							panel.remove(filePane);
							if(GuiController.isComponentListed(getProjectName() + "_FileStartView")){
								FileStartView view = (FileStartView)GuiController.getComponent(getProjectName()+"_FileStartView");
								panel.addComponent(view,2,2,1,1,.7,1);
								panel.revalidate();
								view.openingAnimation();
							}else{
								FileStartView view = new FileStartView(getProjectName());
								panel.addComponent(view,2,2,1,1,.7,1);
								panel.revalidate();
								view.openingAnimation();
							}
						}
					}
				}
			});
		}	
	};
}