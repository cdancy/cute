package gui.editor_panel.project_panel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import project.Project;
import controllers.ProjectController;
import customcomponents.CuteScrollPane;

public class ProjectListingPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3401597303738869717L;

	private GridBagLayout layout;
	
	private GridBagConstraints constraints;
	
	private ProjectListingTree projectJList;
	
	private Graphics2D gd;
	
	private GradientPaint paint;
	
	private int width = 150,height = 60;
	
	private CuteScrollPane listScrollPane;
	
	private String projectName = null;
	
	public ProjectListingPanel(String projectName){
		
		this.projectName = projectName;
		
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);
		
		init();
		createComponents();
	}
	
	public void init(){
		
		super.setPreferredSize(new Dimension(width,height));
		super.setMaximumSize(new Dimension(width,height));
		super.setMinimumSize(new Dimension(width,height));
		super.setSize(new Dimension(width,height));
		
	}
	
	public void createComponents(){
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.insets = new Insets(6,6,6,6);
		ProjectTreeModel model = createTreeModel();
		projectJList = new ProjectListingTree(model,projectName);
		
		listScrollPane = new CuteScrollPane(projectJList);
		listScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		listScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		addComponent(listScrollPane,0,0,1,1,1,1);
		
	}
	@SuppressWarnings("unchecked")
	public ProjectTreeModel createTreeModel(){
		
		/*
		 * Get the projectMap's keySet and put it in Array to cycle through.
		 */
		HashMap<String,Project> map = ProjectController.getProjectMap();
		ArrayList projectList = new ArrayList(map.keySet());
			
		DefaultMutableTreeNode RootNode = new DefaultMutableTreeNode("Projects");
		
		for(int i = 0; i < projectList.size(); i ++){
			
			String currentProjectName = projectList.get(i).toString();
			DefaultMutableTreeNode project = new DefaultMutableTreeNode(currentProjectName);
			project.setAllowsChildren(true);
			
			ArrayList fileList = new ArrayList(map.get(currentProjectName).getFileMap().keySet());
			for(int n = 0; n < fileList.size(); n++){
				
				DefaultMutableTreeNode child = new DefaultMutableTreeNode(fileList.get(n).toString());
				child.setAllowsChildren(false);
				project.add(child);
			}
			RootNode.add(project);
		}
		return new ProjectTreeModel(RootNode);
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
		
		gd = (Graphics2D)g.create();
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		gd.setStroke(new BasicStroke(1));
		paint = new GradientPaint(0,0,new Color(214,133,38),getWidth(),0,new Color(214,133,38),true);
		gd.setPaint(paint);
		gd.drawRoundRect(0, 0, getWidth()-2, getHeight()-2, 20, 20);
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_DEFAULT); 
		gd.dispose();
	}
	public void paintComponent(Graphics g){
		
		gd = (Graphics2D)g.create();
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		paint = new GradientPaint(0,0,new Color(247,218,165),0,getHeight(),Color.white,true);
		gd.setPaint(paint);
		gd.fillRoundRect(0, 0, getWidth()-2, getHeight()-2, 20, 20);
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_DEFAULT);
		gd.dispose();
		
	}
	public ProjectListingTree getprojectList(){
		
		return projectJList;
	}

}
