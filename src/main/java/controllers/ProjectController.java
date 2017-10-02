package controllers;

import gui.editor_panel.EditorPanel;
import gui.editor_panel.Project_TabbedPane;
import gui.editor_panel.project_panel.ProjectListingTree;
import gui.new_project_start_view.ProjectStartView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.management.BadStringOperationException;

import project.Project;

public class ProjectController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1404991050594331441L;

	private static HashMap <String,Project> projectList = new HashMap<String, Project>();
	
	private static ArrayList<ProjectListingTree> listeningTrees = new ArrayList<ProjectListingTree>();
	
	private static File directory = null;
	
	public ProjectController(){
		
	}
	public ProjectController(HashMap<String,Project> map){
		
		projectList.putAll(map);
		
	}
	public static Collection<Project> getProjectCollection(){
		
		return projectList.values();
	}
	
	public static boolean isProjectControllerEmpty(){
		
		return projectList.isEmpty();
	}
	public static boolean isProjectNameUsed(String projectName){
		
		if(projectList.containsKey(projectName)){
			
			return true;
			
		}else{
		
			return false;
		}
	}
	
	public static void removeProject(String projectName){
		
		if(projectName.equals(null))
			return;
		projectList.remove(projectName);
		fireProjectSubtraction(projectName);
	}
	public static void removeProject(Project project){
		
		if(project.getProjectName().equals(null))
			return;
		projectList.remove(project.getProjectName());
		fireProjectSubtraction(project.getProjectName());
	}
	public static void addProject(String projectName,Project project){
		
		if(projectName.equals(null)){
			
			System.out.println("Project Name cannot be null");
			return;
		}
		if(isProjectNameUsed(projectName)){
	
			System.out.println("Project Name "+projectName+" being used");
			return;	
		}
			
		directory = new File(project.getProjectWorkspaceDirectory());
		if(directory.mkdir()){
			projectList.put(projectName,project);
			fireProjectAddition(projectName);
			System.out.println("Directory "+directory.toString()+" created...");
		}else{
			System.out.println("Project dropped... could not make directory...");
			return;
		}

	}
	public static void addProject(Project project) throws BadStringOperationException,NullPointerException{
		
		if(project.getProjectName().equals(null)){
			
			throw new NullPointerException("Project Name cannot be null");	
		}
		if(isProjectNameUsed(project.getProjectName())){
			
			throw new BadStringOperationException("Project Name "+project.getProjectName()+" being used");			
		}
		directory = new File(project.getProjectWorkspaceDirectory());
		if(directory.mkdir()){
			projectList.put(project.getProjectName(),project);
			fireProjectAddition(project.getProjectName());
		}else{
			System.out.println("Project dropped... could not make directory...");
			return;
		}
	}
	public static Project getProject(String projectName){
		
		if(projectList.containsKey(projectName)){
			
			return projectList.get(projectName);		
		}else{
			
			return null;
		}	
	}
	private static void fireProjectAddition(String projectName){
		
		for(int i = 0;i < listeningTrees.size();i++){
			
			((ProjectListingTree) listeningTrees.get(i)).projectAddition(projectName);
			
		}
	}
	private static void fireProjectSubtraction(String projectName){
		
		for(int i = 0;i < listeningTrees.size();i++){
			
			((ProjectListingTree) listeningTrees.get(i)).projectSubtraction(projectName);
		}
		Project_TabbedPane pane = (Project_TabbedPane)GuiController.getComponent("Project_TabbedPane");
		for(int i = 0; i < pane.getTabCount();i++){
			if(pane.getTitleAt(i).equals(projectName)){
				pane.remove(i);
				if(pane.getTabCount() == 0){
					EditorPanel panel = (EditorPanel)GuiController.getComponent("EditorPanel");
					if(GuiController.isComponentListed(projectName + "_FileStartView")){
						ProjectStartView view = (ProjectStartView)GuiController.getComponent("ProjectStartView");
						panel.remove(pane);
						panel.addComponent(view,0,0,1,1,1,1);
						panel.revalidate();
						panel.startFadeInAnimation();
					}else{
						ProjectStartView view = new ProjectStartView();
						panel.remove(pane);
						panel.addComponent(view,0,0,1,1,1,1);
						panel.revalidate();
						panel.startFadeInAnimation();
					}
				}
				return;
			}
		}
	}
	/*
	 * Components registering need to implements the method
	 */
	public static void registerProjectAndFileListener(ProjectListingTree tree){
		
		if(!listeningTrees.contains(tree))
				listeningTrees.add(tree);
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String,Project> getProjectMap(){
		
		return projectList;
	}
	
	/*
	 * Method is only intended for class Project to use to notify of a file added to a 
	 * specific project so that ProjectListingTree can be updated to reflect the 
	 * changes. 
	 */
	public static void projectAddFile(String projectName,String fileName){
		
		for(int i = 0;i < listeningTrees.size();i++){
			
			((ProjectListingTree) listeningTrees.get(i)).fileAddition(projectName,fileName);
		}	
	}
	
	public static void projectRemoveFile(String projectName,String fileName){
		
		for(int i = 0;i < listeningTrees.size();i++){
			
			((ProjectListingTree) listeningTrees.get(i)).fileSubtraction(projectName,fileName);
		}
	}
	public static String getLasDirectoryMade(){
		return directory.getAbsolutePath();
	}
}