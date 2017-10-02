package gui.editor_panel.project_panel;

import gui.new_file_start_view.FileStartView;

import javax.swing.*;

import project.Project;

import controllers.*;
import customcomponents.Divider;
import java.awt.*;

public class ProjectPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6598170192341146560L;

	private GridBagLayout layout = null;
	
	private GridBagConstraints constraints = null;
	
	private String projectName;
	
	public ProjectPanel(String projectName){
		
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);
		
		this.projectName = projectName;
		
		init();
		createComponents();
	}
	public ProjectPanel(String projectName,String fileName){
		
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);
		
		this.projectName = projectName;
		init();
		createComponents();
	}
	public void init(){
		
		
		this.setOpaque(false);
		this.setName(projectName + "_ProjectPanel");
		GuiController.addComponent(this);
	}
	public void createComponents(){
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(3,3,3,3);
		
		addComponent(new ProjectOptions(projectName),0,0,3,1,1,0);
		
		addComponent(new Divider(),0,1,3,1,1,0);
		
		constraints.fill = GridBagConstraints.VERTICAL;
		addComponent(new ProjectListingPanel(projectName),0,2,1,1,0,1);
		
		constraints.fill = GridBagConstraints.VERTICAL;
		addComponent(new Divider(true),1,2,1,1,0,1);
		
		constraints.fill = GridBagConstraints.BOTH;

		if(!ProjectController.getProject(projectName).getFileMap().isEmpty()){
			
			Project p = ProjectController.getProject(projectName);
			int size = p.getOpenFileList().size();
			if(GuiController.isComponentListed(projectName + "_Project_File_TabbedPane") && size == 0){
				addComponent(new FileStartView(projectName),2,2,1,1,.7,1);
			}else if(GuiController.isComponentListed(projectName + "_Project_File_TabbedPane") && size > 0){
				Project_File_TabbedPane tabbedPane = (Project_File_TabbedPane)GuiController.getComponent(projectName + "_Project_File_TabbedPane");
				addComponent(tabbedPane,2,2,1,1,.7,1);
			}else{
				if(size == 0)
					addComponent(new FileStartView(projectName),2,2,1,1,.7,1);
				else
					addComponent(new Project_File_TabbedPane(projectName),2,2,1,1,.7,1);
			}
			
		}else{
			addComponent(new FileStartView(projectName),2,2,1,1,.7,1);
		}
	}
	public void addComponent(Component c, int column, int row, int width, int height,double weightx,double weighty){
		
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