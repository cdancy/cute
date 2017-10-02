package creations;

import java.awt.Color;
import java.awt.Component;

import gui.editor_panel.EditorPanel;
import gui.editor_panel.Project_TabbedPane;
import gui.new_project_start_view.ProjectStartView;

import project.LanguageType;
import project.Project;
import project.VersionControlType;
import controllers.GuiController;
import controllers.ProjectController;

import environment.EnvironmentVariables;

public class CreateProject {
	
	private String projectName = "Untitled";
	
	private String dash = "-";
	
	private LanguageType projectType = LanguageType.None;
	
	private String projectDirectory = null;
	
	private VersionControlType versionType = VersionControlType.None;
	
	private String extensionType = ".txt";
	
	private Color projectColor = null;
	
	private int numberDenomination = 0;

	/*
	 * Creating projects either through a passed name or an "Untitled" given name.
	 * If no name is passed then we will give the apropriately named Untitled to the 
	 * project. If that however is taken for whatever reason, then we will cycle through
	 * the list of available names and append an -0(increasing) to the end of Untitled
	 * to denote the projects given name.
	 */
	public CreateProject(){
		
		initializeProject();
	}
	public CreateProject(String passedName){
		
		if(passedName != null)
			projectName = passedName;
		
		initializeProject();
	}
	public CreateProject(String passedName,LanguageType projectType){
		
		this.projectType = projectType;
		if(passedName != null)
			projectName = passedName;
		
		initializeProject();
	}
	public CreateProject(String passedName,LanguageType projectType,String projectDirectory){
		
		this.projectType = projectType;
		this.projectDirectory = projectDirectory;
		if(passedName != null)
			projectName = passedName;
		
		initializeProject();
	}
	public CreateProject(String passedName,LanguageType projectType,String projectDirectory,VersionControlType versionType,String extensionType, Color projectColor){
		
		this.projectType = projectType;
		this.projectDirectory = projectDirectory;
		this.versionType = versionType;
		if(extensionType.equals(null))
			this.extensionType = ".txt";
		this.extensionType = extensionType;
		this.projectColor = projectColor;
		if(passedName != null)
			projectName = passedName;
		
		initializeProject();
	}
	
	/*
	 * Checks to see if name is already in use. If it is then supply a new name with given
	 * "-#" appended to the end.
	 */
	private String checkAndGetProjectName(String nameToCheck){
		String tempName = nameToCheck;
		
		while(ProjectController.isProjectNameUsed(tempName)){
			
			tempName = nameToCheck + dash + numberDenomination;
			numberDenomination++;
		}
		if(projectDirectory == null){
			String space = EnvironmentVariables.getDefaultWorkspaceDirectory();
			space += tempName;
			space += EnvironmentVariables.getFileSeperator();
			this.projectDirectory = space;
		}
		return tempName;
	}
	
	/*
	 * Will attempt to initialize a project.
	 */
	private void initializeProject(){
			
			this.projectColor = LanguageType.getLanguageColor(projectType);
			
			EditorPanel editPanel = (EditorPanel)GuiController.getComponent("EditorPanel");
			Component [] comp = editPanel.getComponents();
			
			if((comp[0] instanceof Project_TabbedPane)){
				
				
				Project_TabbedPane ptb = (Project_TabbedPane)GuiController.getComponent("Project_TabbedPane");
				String name = checkAndGetProjectName(projectName);
				Project p = new Project(name,projectType,projectDirectory,versionType,extensionType,projectColor);
				ProjectController.addProject(name,p);
				ptb.addTab(name);
				p.setProjectOpen();
					
			}else if(comp[0] instanceof ProjectStartView){
				
				ProjectStartView view = (ProjectStartView)GuiController.getComponent("ProjectStartView");
				editPanel.remove(view);
				
				String name = checkAndGetProjectName(projectName);
				Project p = new Project(name,projectType,projectDirectory,versionType,extensionType,projectColor);
				ProjectController.addProject(name, p);
				if(GuiController.isComponentListed("Project_TabbedPane")){
					
					Project_TabbedPane pane = (Project_TabbedPane)GuiController.getComponent("Project_TabbedPane");
					pane.addTab(name);
					p.setProjectOpen();
					editPanel.addComponent(pane,0,0,1,1,1,1);
				}else{
					
					Project_TabbedPane pane = new Project_TabbedPane();
					pane.addTab(name);
					p.setProjectOpen();
					editPanel.addComponent(pane,0,0,1,1,1,1);
				}
				editPanel.revalidate();
				editPanel.startFadeInAnimation();
			}else{
				System.out.println("CreateProject: Unknown component on EditorPanel..returning");
				return;
			}
	}
}