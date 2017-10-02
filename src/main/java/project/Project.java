package project;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import controllers.ProjectController;
import execution.Execute;

public class Project implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8651701942195832367L;
	
	private transient BufferedWriter writer = null;
	
	private transient BufferedReader reader = null;
	
	private boolean projectOpen = false;

	private String projectName = "";
	
	private HashMap <String,File> fileList = new HashMap<String, File>();
	
	private ArrayList<String> openFiles = new ArrayList<String>();
	
	private Color projectColor;
	
	private LanguageType projectType = LanguageType.None;
	
	private VersionControlType versionControlType = VersionControlType.None;
	
	private String extensionType = ".txt";
	
	private String projectWorkspaceDirectory = null;
	
	private File projectStartLocation = null;
	
	private transient Execute execute = null;
	
	public Project(String projectName){
			
		this.projectName = projectName;
		projectColor = null;
	}
	
	public Project(String projectName, Color projectColor){
		
		this.projectName = projectName;
		this.projectColor = projectColor;
	}
	public Project(String projectName,LanguageType projectType, Color projectColor){
		
		this.projectName = projectName;
		this.projectType = projectType;
		this.projectColor = projectColor;
	}
	public Project(String projectName,LanguageType projectType,String projectWorkspaceDirectory){
		
		this.projectName = projectName;
		this.projectType = projectType;
		this.projectWorkspaceDirectory = projectWorkspaceDirectory;
	}
	public Project(String projectName,LanguageType projectType,String projectWorkspaceDirectory, Color projectColor){
		
		this.projectName = projectName;
		this.projectType = projectType;
		this.projectWorkspaceDirectory = projectWorkspaceDirectory;
		this.projectColor = projectColor;
	}
	public Project(String projectName,LanguageType projectType,String projectWorkspaceDirectory,VersionControlType versionType,String extensionType, Color projectColor){
		
		this.projectName = projectName;
		this.projectType = projectType;
		this.projectWorkspaceDirectory = projectWorkspaceDirectory;
		this.versionControlType = versionType;
		this.extensionType = extensionType;
		this.projectColor = projectColor;
			
	}
	public void setProjectStartLocation(File name){
		if(this.doesFileExist(name))
			this.projectStartLocation = name;
	}
	public void setProjectStartLocation(String name){
		if(this.doesFileExist(name))
			this.projectStartLocation = this.getFile(name);
	}
	public File getProjectStartLocation(){
		return this.projectStartLocation;
	}
	public boolean isProjectOpen(){
		return projectOpen;
	}
	public void setProjectOpen(){
		projectOpen = true;
	}
	public void setProjectClose(){
		projectOpen = false;
	}
	public void setProjectWorkspaceDirectory(String s){
		this.projectWorkspaceDirectory = s;
	}
	public String getProjectWorkspaceDirectory(){
		return this.projectWorkspaceDirectory;
	}
	public void setProjectType(LanguageType s){
		LanguageType [] projectTypes = LanguageType.values();
		for(int i = 0;i < projectTypes.length;i++){
			if(s.equals(projectTypes[i])){
				this.projectType = projectTypes[i];
				return;
			}
		}
	}
	public LanguageType getLanguageType(){
		return projectType;
	}
	public void setProjectName(String s){
		
		this.projectName = s;
	}
	public void setVersionControlType(VersionControlType s){
		this.versionControlType = s;
	}
	public VersionControlType getVersionControlType(){
		return this.versionControlType;
	}
	public String getProjectName(){
		
		return projectName;
	}

	public boolean forceRenameFile(String oldName,String newName){
		
		String fileContent = readFile(oldName);
		forceAddFile(newName);
		saveFile(newName,fileContent);

		return true;
	}
	public void forceAddFile(String newFile){
		String fileNameToWrite = this.getProjectWorkspaceDirectory() + newFile;
		
		File temp = new File(fileNameToWrite);
		try {
			if(temp.createNewFile()){
				fileList.put(newFile,new File(fileNameToWrite));
				ProjectController.projectAddFile(projectName, newFile);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addFile(File newFile){
		
		if(!doesFileExist(newFile)){
			fileList.put(newFile.getName(),newFile);
			ProjectController.projectAddFile(projectName, newFile.getName());	
		}else
			System.out.println(newFile.getName() + " alreIt is also responsible for writing the initial file to disk.ady exists...");
	}
	public void addFile(String newFile){
		
		if(!doesFileExist(newFile)){
			String fileNameToWrite = this.getProjectWorkspaceDirectory() + newFile;
			
			File temp = new File(fileNameToWrite);
			try {
				if(temp.createNewFile()){
					fileList.put(newFile,new File(fileNameToWrite));
					ProjectController.projectAddFile(projectName, newFile);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}else
			System.out.println(newFile + " already exists...!");
	}
	public File getFile(String s){
		
		if(doesFileExist(s)){
			
			return fileList.get(s);
		}
		return null;
	}
	public boolean doesFileExist(File f){
		
		if(fileList.containsKey(f.getName()))
			return true;
		else
			return false;
	}
	public boolean doesFileExist(String fileName){
		
		if(fileList.containsKey(fileName))
			return true;
		else
			return false;
	}
	public void removeFile(File fileName){
		
		if(fileList.containsKey(fileName.getName())){
			String temp = fileName.getName();
			fileName.delete();
			fileList.remove(fileName.getName());
			ProjectController.projectRemoveFile(projectName, temp);
			removeOpenFile(fileName);
		}else
			return;
	}
	public void removeFile(String fileName){
		
		if(fileList.containsKey(fileName)){
			File fileToDelete = fileList.get(fileName);
			fileToDelete.delete();
			fileList.remove(fileName);
			ProjectController.projectRemoveFile(projectName, fileName);
			removeOpenFile(fileName);
		}else
			return;
	}
	
	@SuppressWarnings("unchecked")
	public HashMap <String,File> getFileMap(){
		
		return (HashMap <String,File>)fileList.clone();
	}

	public String toString(){
		
		return "Project " + getProjectName() + ": File Listing:" + getFileMap();		
	}
	
	public void setProjectColor(Color color){
		
		this.projectColor = color;
	}
	
	public Color getProjectColor(){
		
		return projectColor;
	}
	public void addOpenFile(File f){
		
		if(fileList.containsKey(f.toString()) && !openFiles.contains(f.getName()))
			openFiles.add(f.toString());
	}
	
	public void addOpenFile(String s){
		if(fileList.containsKey(s) && !openFiles.contains(s))
			openFiles.add(s);
	}
	
	public void removeOpenFile(String s){
		openFiles.remove(s);
	}
	
	public void removeOpenFile(File f){
		openFiles.remove(f.getName());
	}
	
	public ArrayList<String> getOpenFileList(){
		return this.openFiles;
	}
	public void setExtensionType(String type){
		this.extensionType = type;
	}
	
	public String getExtensionType(){
		return this.extensionType;
	}
	
	public void saveFile(String name,String text){
		
		if(!fileList.containsKey(name))
			return;
		File f = this.getFile(name);
		try {
			writer = new BufferedWriter(new FileWriter(f));
			writer.write(text);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Project: File could not be written...");
			return;
		}finally{
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Project: Writer could not close...");
				return;
			}
		}
		System.out.println(name + " successfully written...");
	}
	
	public String readFile(String name){
		if(!fileList.containsKey(name))
			return "";
		File f = this.getFile(name);
		String returnText = "";
		String temp = "";
		try{
			reader = new BufferedReader(new FileReader(f));
			while((temp = reader.readLine()) != null){
				returnText += temp;
				returnText += "\n";
			}
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Project: File could not be read...");
			return "";
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Project: Reader could not close...");
			}
		}
		return returnText;
	}
	public void deleteProject(){
		File f = new File(this.getProjectWorkspaceDirectory());
		if(f.delete()){
			ProjectController.removeProject(this.getProjectName());
		}else{
			
		}
	}
	public void executeProject(){
		execute = new Execute(this.getProjectName());
	}
	public void terminateProject(){
		execute.killExecution();
	}
	public Execute getExecution(){
		return this.execute;
	}
}