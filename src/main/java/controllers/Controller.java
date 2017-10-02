package controllers;

import java.util.HashMap;
import project.Project;

public class Controller {

	private static HashMap <String,Object> controllerHolder = new HashMap<String, Object>();
	
	public Controller(){
		
		setupProjectController();
		setupGuiController();
	
	}
	public Controller(HashMap<String,Project> map){
		
		setupProjectController(map);
		setupGuiController();
		
	}

	private void setupProjectController() {

		controllerHolder.put("ProjectController", new ProjectController());
	}

	private void setupProjectController(HashMap<String,Project> map) {

		controllerHolder.put("ProjectController", new ProjectController(map));
	}
	private void setupGuiController() {
		
		controllerHolder.put("GuiController", new GuiController());
		
	}
	public static GuiController getGuiController(){
		
		return (GuiController)controllerHolder.get("GuiController");
	}
	public static ProjectController getProjectController(){
		
		return (ProjectController)controllerHolder.get("ProjectController");
	}
}
