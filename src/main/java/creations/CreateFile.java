package creations;

import java.awt.Component;

import gui.editor_panel.EditorPanel;
import gui.editor_panel.Project_TabbedPane;
import gui.editor_panel.project_panel.FileEditArea;
import gui.editor_panel.project_panel.ProjectPanel;
import gui.editor_panel.project_panel.Project_File_TabbedPane;

import gui.new_file_start_view.*;

import project.Project;

import controllers.*;

public class CreateFile{

	private String fileName = "Untitled";
	
	private String projectTitle = null;
	
	private String folder = null;
	
	private String fileExtension = null;
	
	private final String dash = "-";
	
	private int numberDenomination = 0;
	
	public CreateFile(){
		
		initializeFile();
		
	}
	public CreateFile(String fileName){

		if(fileName != null)
			this.fileName = fileName;
		
		initializeFile();
	}
	public CreateFile(String fileName,String projectName){
		
		this.projectTitle = projectName;
		if(fileName != null && !fileName.equals(""))
			this.fileName = fileName;
		
		initializeFile();
	}
	public CreateFile(String fileName,String projectName, String folder){
		
		this.projectTitle = projectName;
		this.folder = folder;
		if(fileName != null)
			this.fileName = fileName;
		
		initializeFile();
	}
	@SuppressWarnings("unused")
	private String getFolder(){
		return this.folder;
	}
	private String checkAndGetFileName(String nameToCheck,String projectName){
		
		String extension = ProjectController.getProject(projectName).getExtensionType();
		String tempName = nameToCheck;
		tempName += extension;
		while(ProjectController.getProject(projectName).doesFileExist(tempName)){
			
			tempName.replaceAll(extension, "");
			tempName = nameToCheck + dash + numberDenomination;
			tempName += extension;
			numberDenomination++;
		}		
		return tempName;
	}
	private void initializeFile(){
		
		EditorPanel editPanel = (EditorPanel)GuiController.getComponent("EditorPanel");
		Component [] comp = editPanel.getComponents();
		
		if((comp[0] instanceof Project_TabbedPane)){
	
			Project_TabbedPane tabPane = (Project_TabbedPane)GuiController.getComponent("Project_TabbedPane");
			
			/*
			 * Project_TabbedPane is present with at least one tab.
			 */
			if(tabPane.getTabCount() > 0){
				int selectedTab = tabPane.getSelectedIndex();
				
				String projectName = "";
				if(this.projectTitle != null)
					projectName = this.projectTitle;
				else
					projectName = tabPane.getTitleAt(selectedTab);
				
				/*
				 * Get the ProjectPanel associated with the selected Project Tab
				 */				
				ProjectPanel projectPanel = (ProjectPanel)GuiController.getComponent(projectName +"_ProjectPanel");
	
				/*
				 * Check to see if the projectPanel has the Project_File_TabbedPane.
				 */
				Component [] projectPanelComponents = projectPanel.getComponents();
				for(int index = 0; index < projectPanelComponents.length;index++){
					if(projectPanelComponents[index] instanceof Project_File_TabbedPane){
						creationWithProjectFileTabbedPane(projectName);
						return;
					}
				}
				creationWithoutProjectFileTabbedPane(projectPanel,projectName);

			}else{
				System.out.println("CreateFile: No Project Tabs open so no file added.");
				return;
			}
		}else{
			System.out.println("CreateFile:No Project_TabbedPane so no file added.");
			return;
		}
	}
	
	/*
	 * Creating a File when no projectFileTabbedPane is present. However this can have
	 * one of two states.
	 * 1.) ProjectFileTabbedPane was never present. or...
	 * 2.) ProjectFileTabbedPane was present at some point but has been removed...in that
	 *     case it probably is still in the guiController so we can use it again.
	 */
	public void creationWithoutProjectFileTabbedPane(ProjectPanel projectPanel,String projectName){

		FileStartView view = (FileStartView)GuiController.getComponent(projectName+"_FileStartView");
		projectPanel.remove(view);
		GuiController.removeComponent(projectName+"_FileStartView");
		
		if(GuiController.isComponentListed(projectName + "_Project_File_TabbedPane")){
			Project_File_TabbedPane projectFileTabbedPane = (Project_File_TabbedPane)GuiController.getComponent(projectName+"_Project_File_TabbedPane");
			projectFileTabbedPane.removeAll();
			projectFileTabbedPane.setAnimationStepToZero();
			projectPanel.addComponent(projectFileTabbedPane,2,2,1,1,.7,1);
			
			/*
			 * get the project and check the filename.
			 */
			Project project = ProjectController.getProject(projectName);
			String newFileName = checkAndGetFileName(fileName,projectName);
			
			project.addFile(newFileName);
			projectFileTabbedPane.addTab(newFileName, new FileEditArea(projectName,newFileName));
			projectPanel.revalidate();
			projectFileTabbedPane.openingAnimation();
		}else{
			/*
			 * create a new projectFileTabbedPane() and put it on.
			 */
			Project_File_TabbedPane projectFileTabbedPane = new Project_File_TabbedPane(projectName);
			projectFileTabbedPane.setAnimationStepToZero();
			projectPanel.addComponent(projectFileTabbedPane,2,2,1,1,.7,1);
			
			/*
			 * get the project and check the filename.
			 */
			Project project = ProjectController.getProject(projectName);
			String newFileName = checkAndGetFileName(fileName,projectName);
			
			project.addFile(newFileName);
			projectFileTabbedPane.addTab(newFileName, new FileEditArea(projectName,newFileName));
			projectPanel.revalidate();
			projectFileTabbedPane.openingAnimation();
		}
	}
	public void creationWithProjectFileTabbedPane(String projectName){

		Project_File_TabbedPane fileTabPane = (Project_File_TabbedPane)GuiController.getComponent(projectName + "_Project_File_TabbedPane");

		/*
		 * Get the actual project.
		 */
		Project project = ProjectController.getProject(projectName);
		String newFileName = checkAndGetFileName(fileName,projectName);
		
		project.addFile(newFileName);
		fileTabPane.addTab(newFileName, new FileEditArea(projectName,newFileName));			
	}
	public void setFileExtension(String extension){
		this.fileExtension = extension;
	}
	public String getFileExtension(){
		return this.fileExtension;
	}
}