package gui.editor_panel.project_panel;

import gui.editor_panel.Project_TabbedPane;
import gui.new_file_start_view.FileStartView;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import project.Project;

import controllers.*;
import creations.CreateFile;
import customcomponents.CuteMenuItem;
import customcomponents.TreePopupMenu;

public class ProjectListingTree extends JTree implements TreeSelectionListener,MouseListener, TreeExpansionListener, TreeWillExpandListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4483656057556195364L;
	
	private String projectName = "";
	
	private DefaultMutableTreeNode rootNode = null,projectNode = null,fileNode = null;
	
	public ProjectListingTree(ProjectTreeModel model,String projectName){
		
		super(model);
		this.projectName = projectName;
		init();
	}
	public void init(){
		
		ProjectController.registerProjectAndFileListener(this);
		//super.setCellRenderer(new ProjectTreeCellRenderer());
		//super.setOpaque(false);
		super.addTreeSelectionListener(this);
		super.addTreeExpansionListener(this);
		super.addTreeWillExpandListener(this);
		super.addMouseListener(this);
	}
	public String getProjectName(){
		return this.projectName;
	}
	public void valueChanged(TreeSelectionEvent arg0) {

		//System.out.println(arg0.isAddedPath());
		//System.out.println(arg0.getPath());
		
	}
	
	/*
	 * This method is used by ProjectController to notify this class of 
	 * a project being added.
	 */
	public void projectAddition(String projectName){

		DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getModel().getRoot();
		node.add(new DefaultMutableTreeNode(projectName));
		DefaultTreeModel model = (DefaultTreeModel)this.getModel();
		model.reload(node);
	}
	
	/*
	 * This method is used by ProjectController to notify this class of
	 * a project being removed.
	 */
	public void projectSubtraction(String projectName){
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getModel().getRoot();
		DefaultMutableTreeNode temp = null;
		for(int i = 0;i < node.getChildCount();i++){
			temp = (DefaultMutableTreeNode)node.getChildAt(i);
			if(temp.toString().equals(projectName)){
				node.remove(temp);
				DefaultTreeModel model = (DefaultTreeModel)this.getModel();
				model.reload(node);
				return;
			}
		}
	}
	
	/*
	 * This method is used by ProjectController to notify this class of
	 * a file being added to a project.
	 */
	public void fileAddition(String projectName, String fileName){
		
		rootNode = (DefaultMutableTreeNode)this.getModel().getRoot();
		
		for(int i = 0;i < rootNode.getChildCount();i++){
			
			projectNode = (DefaultMutableTreeNode)rootNode.getChildAt(i);
			if(projectNode.toString().equals(projectName)){
				DefaultMutableTreeNode file = new DefaultMutableTreeNode(fileName);
				file.setAllowsChildren(false);
				projectNode.add(file);
				DefaultTreeModel model = (DefaultTreeModel)this.getModel();
				model.reload(projectNode);
				return;
			}
		}
	}
	
	/*
	 * This method is used by ProjectController to notify this class of
	 * a file being removed from a project.
	 */
	public void fileSubtraction(String projectName, String fileName){
		
		rootNode = (DefaultMutableTreeNode)this.getModel().getRoot();

		for(int i = 0;i < rootNode.getChildCount();i++){
			
			projectNode = (DefaultMutableTreeNode)rootNode.getChildAt(i);
			if(projectNode.toString().equals(projectName)){
				for(int j = 0;j < projectNode.getChildCount();j++){
					
					fileNode = (DefaultMutableTreeNode)projectNode.getChildAt(j);
					if(fileNode.toString().equals(fileName)){
						
						Project_File_TabbedPane tabbedPane = (Project_File_TabbedPane)GuiController.getComponent(projectName + "_Project_File_TabbedPane");
						
						for(int n = 0;n < tabbedPane.getTabCount();n++){
							if(tabbedPane.getTitleAt(n).equals(fileName)){
								tabbedPane.removeTabAt(n);
								projectNode.remove(projectNode.getIndex(fileNode));
								if(tabbedPane.getTabCount() == 0){
									
									ProjectPanel proPanel = (ProjectPanel)GuiController.getComponent(projectName + "_ProjectPanel");
									proPanel.remove(tabbedPane);
									if(GuiController.isComponentListed(projectName+"_FileStartView")){
										
										FileStartView view = (FileStartView)GuiController.getComponent(projectName+"_FileStartView");
										proPanel.addComponent(view,2,2,1,1,.7,1);
									}else{
										
										proPanel.addComponent(new FileStartView(projectName),2,2,1,1,.7,1);
									}
									proPanel.revalidate();
									proPanel.repaint();
								}
								System.out.println("File succesfully removed...");
								DefaultTreeModel model = (DefaultTreeModel)this.getModel();
								model.reload(projectNode);
								return;
							}
						}
					}
				}
			}
		}
		System.out.println("ProjectListingTree:FileSubtraction should not have reached this point....");
	}
	public void mouseClicked(MouseEvent arg0) {
	    if (arg0.getClickCount() == 2) {
	    	
	    	DefaultMutableTreeNode node = (DefaultMutableTreeNode)this.getLastSelectedPathComponent();    	
	    	String selectedNodeName = node.toString();
	    	
	    	/*
	    	 * If a user double clicks the projects icon do nothing.
	    	 */
	    	if(selectedNodeName.equals("Projects"))
	    		return;
	    	
	    	/*
	    	 * path.getPathComponent(1) should always return the actual project name,
	    	 * seeing as how only projects would be listed under the "Projects" node.
	    	 */
	    	TreePath path = this.getSelectionPath();
	    	String selectedNodeParentName = path.getPathComponent(1).toString();
	    	
	    	if(selectedNodeParentName.equals("Projects"))
	    		selectedNodeParentName = selectedNodeName;
	    	
	    	
	    	/*
	    	 * If node has chidren or not. If not then must be a leaf.
	    	 */
	    	if(node.isLeaf()){
	    		
	    		/*
	    		 * It is possible that the node is a leaf and also a project name, which 
	    		 * simply has no files added yet. If so it's parent will be Projects. So
	    		 * we will go to currentTabNotLeaf() which does what we need.
	    		 */
	    		if(node.getParent().toString().equals("Projects")){
	    			currentTabNotLeaf(selectedNodeParentName);
	    			return;
	    		}
	    		/*
	    		 * First check to see if node is part of this project or from another project.
	    		 * We'll first check to see if the selectedNodeParentName is the same as this
	    		 * open tab title.
	    		 */
	    		Project_TabbedPane tabbedPane = (Project_TabbedPane)GuiController.getComponent("Project_TabbedPane");
	    		String currentTabTitle = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
	   
    			for(int i = 0;i < tabbedPane.getTabCount();i++){
    				if(tabbedPane.getTitleAt(i).equals(selectedNodeParentName)){
    					String name = tabbedPane.getTitleAt(i);
    					
    					if(name.equals(currentTabTitle)){
    						currentTabMatches(currentTabTitle, selectedNodeName);
    						return;
    					}
    					else{
    						someTabMatches(i,name,selectedNodeName);
    						return;
    					}
    				}
    			}
    			
    			/*
    			 * If here than there are no open projects tabs with the same name as
    			 * selectedNodeParentName. So we must open a new project tab for the 
    			 * selected fileNode.
    			 */
    			noMatchingTabs(selectedNodeName,selectedNodeParentName);
    			return;
	    	}else{
	    		currentTabNotLeaf(selectedNodeParentName);
	    	}
	    }
	}
	
	/*
	 * There are no project tabs open for the selectedNodeParentName, so we must open one
	 * along with the fileNode that has been clicked.
	 */
	public void noMatchingTabs(String selectedNodeName,String selectedNodeParentName){
		
		Project_TabbedPane projectTabbedPane = (Project_TabbedPane)GuiController.getComponent("Project_TabbedPane");
		projectTabbedPane.addTab(selectedNodeParentName, selectedNodeName);
	
	}
	
	/*
	 * There is a project tab open with the given selectedNodeParentName. 
	 * However it is not the currently selected project.
	 */
	public void someTabMatches(int tabLocation,String tabTitle, String selectedNodeName){
	
		/*
		 * If fileStartView is still in GuiController than it must be on the
		 * projectpanel. We'll remove it and put the project_file_tabbedpane there.
		 */
		if(GuiController.isComponentListed(tabTitle + "_FileStartView")){
			
			FileStartView fileView = (FileStartView)GuiController.getComponent(tabTitle + "_FileStartView");
			ProjectPanel panel = (ProjectPanel)GuiController.getComponent(tabTitle + "_ProjectPanel");
			panel.remove(fileView);
			GuiController.removeComponent(tabTitle + "_FileStartView");
			
			/*
			 * There are times when the project_file_tabbedpane is listed but not
			 * on the projectpanel. so we check to see if its listed. If not
			 * than create a new one and add it.
			 */
			if(GuiController.isComponentListed(tabTitle + "_Project_File_TabbedPane")){
				
				Project_File_TabbedPane tabbedFilePane = (Project_File_TabbedPane)GuiController.getComponent(tabTitle + "_Project_File_TabbedPane");
				tabbedFilePane.addTab(selectedNodeName);
				panel.addComponent(tabbedFilePane,2,2,1,1,.7,1);
				panel.revalidate();
				panel.repaint();
				
			}else{
				
				panel.addComponent(new Project_File_TabbedPane(projectName,selectedNodeName),2,2,1,1,.7,1);
				panel.revalidate();
				panel.repaint();
			}
			
		}else{
			
			Project_File_TabbedPane fileTabbedPane = (Project_File_TabbedPane)GuiController.getComponent(tabTitle + "_Project_File_TabbedPane");
			for(int i = 0; i < fileTabbedPane.getTabCount();i++){
				if(fileTabbedPane.getTitleAt(i).equals(selectedNodeName)){
					return;
				}
			}
			fileTabbedPane.addTab(selectedNodeName);
		}
		
	}
	
	/*
	 * The currently selected project tab has the same name as selectedNodeParentName.
	 */
	public void currentTabMatches(String currentTabTitle,String selectedNodeName){
		
		/*
		 * If fileStartView is still in GuiController than it must be on the
		 * projectpanel. We'll remove it and put the project_file_tabbedpane there.
		 */
		if(GuiController.isComponentListed(currentTabTitle + "_FileStartView")){
			
			FileStartView fileView = (FileStartView)GuiController.getComponent(currentTabTitle + "_FileStartView");
			ProjectPanel panel = (ProjectPanel)GuiController.getComponent(currentTabTitle + "_ProjectPanel");
			panel.remove(fileView);
			GuiController.removeComponent(currentTabTitle + "_FileStartView");
			
			/*
			 * There are times when the project_file_tabbedpane is listed but not
			 * on the projectpanel. so we check to see if its listed. If not
			 * than create a new one and add it.
			 */
			if(GuiController.isComponentListed(currentTabTitle + "_Project_File_TabbedPane")){
				
				Project_File_TabbedPane tabbedFilePane = (Project_File_TabbedPane)GuiController.getComponent(currentTabTitle + "_Project_File_TabbedPane");
				tabbedFilePane.addTab(selectedNodeName);
				panel.addComponent(tabbedFilePane,2,2,1,1,.7,1);
				panel.revalidate();
				panel.repaint();
				
			}else{
				
				panel.addComponent(new Project_File_TabbedPane(projectName,selectedNodeName),2,2,1,1,.7,1);
				panel.revalidate();
				panel.repaint();
			}
			
		}else{
		
			Project_File_TabbedPane fileTabbedPane = (Project_File_TabbedPane)GuiController.getComponent(currentTabTitle + "_Project_File_TabbedPane");
			for(int i = 0; i < fileTabbedPane.getTabCount();i++){
				if(fileTabbedPane.getTitleAt(i).equals(selectedNodeName)){
					return;
				}
			}
			fileTabbedPane.addTab(selectedNodeName);
		}
	}
	public void currentTabNotLeaf(String selectedNodeParentName){
		
		Project_TabbedPane projectTabbedPane = (Project_TabbedPane)GuiController.getComponent("Project_TabbedPane");
		for(int i = 0;i < projectTabbedPane.getTabCount();i++){
			
			if(projectTabbedPane.getTitleAt(i).equals(selectedNodeParentName)){
				System.out.println("Project tab already open...");
				return;
			}
		}
		System.out.println("Project tab not open...so opening...");
		projectTabbedPane.addTab(selectedNodeParentName);
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void mousePressed(MouseEvent e) {
		
		if(e.isPopupTrigger() && environment.EnvironmentVariables.isLinuxOS()){
			
			ProjectListingTree tree = (ProjectListingTree)e.getSource();
			TreePath path = tree.getClosestPathForLocation(e.getX(), e.getY());
			final DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)path.getLastPathComponent();
			tree.setSelectionPath(path);
			
			Point p = e.getPoint();
			SwingUtilities.convertPointToScreen(p, this);
			
			if(!selectedNode.getAllowsChildren()){
				
				final TreePopupMenu filePopup = getFilePopupMenu(selectedNode);
				filePopup.setLocation(p);
				filePopup.setVisible(true);
			}
			else{
				
				final TreePopupMenu projectPopup = getProjectPopupMenu(selectedNode.toString());
				projectPopup.setLocation(p);
				projectPopup.setVisible(true);
			}
		}
	}
	
	private TreePopupMenu getProjectPopupMenu(String selectedNode){
		
		final TreePopupMenu tree = new TreePopupMenu("Projects");
		final String selection = selectedNode;
		JMenuItem name = new CuteMenuItem("Project: "+selectedNode.toString());
		name.setOpaque(false);
		tree.add(name);
		
		tree.addSeparator();
		
		JMenuItem newFile = new CuteMenuItem("New File?");
		newFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new CreateFile(null,selection);
				tree.setVisible(false);
			}
		});
		newFile.setOpaque(false);
		tree.add(newFile);
		
		JMenuItem delete = new CuteMenuItem("Delete Project "+selectedNode+" ?");
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Project pro = ProjectController.getProject(selection);
				pro.deleteProject();
				tree.setVisible(false);
			}
		});
		delete.setOpaque(false);
		tree.add(delete);
		
		tree.addSeparator();
		
		JMenuItem properties = new CuteMenuItem(selectedNode + " Properties");
		properties.setOpaque(false);
		tree.add(properties);
		return tree;
	}
	
	
	private TreePopupMenu getFilePopupMenu(final DefaultMutableTreeNode selectedNode){
		
		final TreePopupMenu tree = new TreePopupMenu("Files");
		
		CuteMenuItem name = new CuteMenuItem("File: " + selectedNode.toString());
		name.setOpaque(false);
		
		CuteMenuItem open = new CuteMenuItem("Open "+selectedNode.toString()+" Tab?");
		open.setOpaque(false);
		open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			
				System.out.println("open");
				tree.setVisible(false);
			}
		});
		
		CuteMenuItem close = new CuteMenuItem("Close "+selectedNode.toString()+" Tab?");
		close.setOpaque(false);
		close.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Project_File_TabbedPane tabPane = (Project_File_TabbedPane)GuiController.getComponent(projectName + "_Project_File_TabbedPane");
				for(int i = 0; i < tabPane.getTabCount();i++){
					if(tabPane.getTitleAt(i).equals(selectedNode.toString())){
						tabPane.remove(i);
						if(tabPane.getTabCount() == 0){
							ProjectPanel panel = (ProjectPanel)GuiController.getComponent(projectName + "_ProjectPanel");
							panel.remove(tabPane);
							if(GuiController.isComponentListed(projectName + "_FileStartView")){
								FileStartView view = (FileStartView)GuiController.getComponent(projectName + "_FileStartView");
								panel.addComponent(view,2,2,1,1,.7,1);
								panel.revalidate();
								view.openingAnimation();
							}else{
								FileStartView view = new FileStartView(projectName);
								panel.addComponent(view,2,2,1,1,.7,1);
								panel.revalidate();
								view.openingAnimation();
							}
						}
						break;
					}
				}
				tree.setVisible(false);
			}
		});
		
		CuteMenuItem delete = new CuteMenuItem("Delete "+selectedNode.toString()+" File?");
		delete.setOpaque(false);
		delete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Project p = ProjectController.getProject(projectName);
				p.removeFile(selectedNode.toString());
				tree.setVisible(false);
			}
		});
		
		CuteMenuItem rename = new CuteMenuItem("Rename "+selectedNode.toString()+"?");
		rename.setOpaque(false);
		rename.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				tree.setVisible(false);
			}
		});
		
		CuteMenuItem setProjectStartLocation = new CuteMenuItem("Set " + selectedNode.toString()+" as projectStartLocation?");
		setProjectStartLocation.setOpaque(false);
		setProjectStartLocation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Project p = ProjectController.getProject(projectName);
				p.setProjectStartLocation(selectedNode.toString());
				tree.setVisible(false);
			}
		});
		
		CuteMenuItem newProjectFromFile = new CuteMenuItem("New Project from "+selectedNode.toString()+" ?");
		newProjectFromFile.setOpaque(false);
	
		tree.add(name);
		tree.addSeparator();
		tree.add(open);
		tree.add(close);
		tree.add(delete);
		tree.add(rename);
		tree.addSeparator();
		tree.add(newProjectFromFile);
		tree.add(setProjectStartLocation);
		return tree;
	}
	public void mouseReleased(MouseEvent e) {
				
		if(e.isPopupTrigger() && environment.EnvironmentVariables.isWindowsOS()){
			
			ProjectListingTree tree = (ProjectListingTree)e.getSource();
			TreePath path = tree.getClosestPathForLocation(e.getX(), e.getY());
			final DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)path.getLastPathComponent();
			tree.setSelectionPath(path);
			Point p = e.getPoint();
			SwingUtilities.convertPointToScreen(p, this);
			if(!selectedNode.getAllowsChildren()){
				
				final TreePopupMenu filePopup = getFilePopupMenu(selectedNode);
				filePopup.setVisible(true);
				filePopup.setLocation(p);
			}
			else{
				
				final TreePopupMenu projectPopup = getProjectPopupMenu(selectedNode.toString());
				projectPopup.setLocation(p);
				projectPopup.setVisible(true);
			}
		}
	}
	public void treeCollapsed(TreeExpansionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void treeExpanded(TreeExpansionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	public void treeWillCollapse(TreeExpansionEvent arg0) throws ExpandVetoException {
		
		/*
		 * how to stop tree from collapsing.
		 * throw new ExpandVetoException(arg0);
		 */
		
	}
	public void treeWillExpand(TreeExpansionEvent arg0) throws ExpandVetoException {
		/*
		 * how to stop tree from expanding.
		 * throw new ExpandVetoException(arg0);
		 */
		
	}
}