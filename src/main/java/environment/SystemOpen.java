package environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import javax.swing.SwingUtilities;

import project.Project;

import controllers.Controller;

public class SystemOpen {

	private HashMap<String,Project> map = null;
	
	private File workspace = null;
	
	public SystemOpen(){
		
		System.out.println("Preparing System Open...");
		
		if(!isWorkSpacePresent())
			createWorkSpace();
		
		if(isProjectControllerSaved()){
			
			initializeController(map);
			
		}else{
			
			initializeController();
		}
		
		initializeMainFrame();

		System.out.println("System succesfully opened...");
	}

	private void initializeMainFrame() {
		
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run(){
				
				 new gui.MainFrame();
			}
		});	
		
	}

	private void initializeController(HashMap<String,Project> map) {
		
		System.out.println("Previous Controller found...");
		new Controller(map);
		
	}

	private void initializeController() {
		
		System.out.println("Previous Controller was not found...");
		new Controller();
		
	}
	@SuppressWarnings("unchecked")
	private boolean isProjectControllerSaved() {

		try{
			
			FileInputStream fileInput = new FileInputStream("projects.dat");

			ObjectInputStream objectInput = new ObjectInputStream (fileInput);

			HashMap<String, Project> readObject = (HashMap<String, Project>)objectInput.readObject();

			this.map = readObject;
			
			if(map == null){
				
				System.out.println("Opened a null map...");
				return false;
			}
			
			
		}catch(FileNotFoundException e){
			
			System.out.println("EXCEPTION: Either projects.dat does not exist or was not found...");
			return false;
			
		}catch(IOException e){
			
			System.out.println("EXCEPTION: Error with restoring object from input stream... " + e);
			return false;
			
		}catch(ClassNotFoundException e){
			
			System.out.println("EXCEPTION: Class Not found upon restoring object... ");
			return false;
		}
		return true;
	}
	public void createWorkSpace(){
		System.out.println("cute_workspace does not exist...");
		System.out.println("Creating workspace at " + EnvironmentVariables.getDefaultWorkspaceDirectory());
		if(workspace.mkdir())
			System.out.println("cute_workspace initialized...");
		else
			System.out.println("cute_workspace was not created...");
	}
	public boolean isWorkSpacePresent(){
		
		String directory = EnvironmentVariables.getDefaultWorkspaceDirectory();
		System.out.println(directory);
		workspace = new File(directory);
	
		if(workspace.exists() && workspace.isDirectory())
			return true;
		else
			return false;
	}
}
