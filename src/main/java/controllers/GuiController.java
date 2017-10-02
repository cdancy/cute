package controllers;

import java.awt.Component;
import java.util.HashMap;
import java.util.Set;

/*
 * This class will hold all necessary panels,buttons,frames and whatever else may be needed
 * system wide.
 */
public class GuiController {

	private static HashMap <String,Component> componentController = new HashMap<String, Component>();
	
	public GuiController(){
	
	}
	public static Set getGuiControllerKeySet(){
		
		return componentController.keySet();
	}
	public static void addComponent(Component component){
		
		String comp_name = component.getName();

		if(!comp_name.equals(null) && !componentController.containsKey(comp_name))
			componentController.put(comp_name, component);
		else
			return;
	}
	
	public static Component getComponent(String comp_name){
		
		if(componentController.containsKey(comp_name))
			return componentController.get(comp_name);
		else{
			return null;
		}
	}
	public static boolean isComponentListed(String name){
		
		if(componentController.containsKey(name)){
			return true;
		}else
			return false;
		
	}
	public static boolean isComponentListed(Component comp){
		
	
		if(componentController.containsKey(comp.getName())){
			return true;
		}else
			return false;
		
	}


	public static boolean removeComponent(String name){
		
		/*
		 * Project_TabbedPane and ProjectStartView are to expensive of components to be 
		 * removed. They are also, potentially removed from the application many times
		 * per session each. So why remove them when we can reuse them.
		 */
		if(name.equals("Project_TabbedPane"))
			return false;
		else if(name.equals("ProjectStartView"))
			return false;
		if(isComponentListed(name)){
			componentController.remove(name);
			return true;
		}else{
			System.out.println(name + " is not listed, so not removing from GuiController.");
			return false;
		}
			
	}
	public static void updateProjectGUI(String name){
		
	
	
	}
	public static HashMap <String,Component> getTableMap(){
		
		return componentController;
	}
}
