package environment;

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.ObjectOutputStream;
import java.util.HashMap;

import project.Project;

import controllers.*;

public class SystemClose {

	private enum shutdownState {ShutdownReady,ShutdownFinished};
	
	private static shutdownState systemClose = shutdownState.ShutdownReady;
	
	public SystemClose(){
		
		if(systemClose == shutdownState.ShutdownReady){
			
			System.out.println("Initializing System Close...");
			
			SaveProjectController();
			
			systemClose = shutdownState.ShutdownFinished;
			
		}else{
			
			System.out.println("Shutdown State already initialized...");
			return;
		}
		
	}
	private void SaveProjectController(){

		
		if(ProjectController.getProjectMap().isEmpty()){
			System.out.println("Project Map is empty.... no need to save...");
			return;
		}
		
		System.out.println("Saving Project to projects.dat ...");
		HashMap<String, Project> map = ProjectController.getProjectMap();
		
		try{
			
			if(map == null){
				System.out.println("Trying to save a null map...");
				throw new NullPointerException("Can not save null map...");
			}
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("projects.dat"));

			output.writeObject(map);
			
		}catch(IOException e){
			System.out.println(e);
			System.out.println("EXCEPTION: Error in writing projects.dat ...");
			return;
			
		}catch(Exception e){
			
			System.out.println(e);
		}
		
		System.out.println("Successful write to projects.dat ...");
	}
}
