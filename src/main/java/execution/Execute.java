package execution;

import java.io.*;
import java.util.ArrayList;

import controllers.GuiController;
import controllers.ProjectController;

import project.LanguageType;
import project.Project;

import environment.OSType;
import gui.editor_panel.project_panel.ProjectPanel;
import gui.editor_panel.project_panel.Project_File_TabbedPane;

public class Execute implements Runnable {
	
	private Process process = null;
	
	private ProcessBuilder builder = null;
	
	private ArrayList <String>list = new ArrayList<String>();
	
	public Execute(String projectName){
		
		String programCommand = null;
		String absoluteFileLocation = null;
		
		Project pro = ProjectController.getProject(projectName);
		LanguageType langType = pro.getLanguageType();
		
		/*
		 * Check to see if the project has a starting Point. If not, than try and execute
		 * the currently selected tab's file.
		 */
		if(pro.getProjectStartLocation() == null){
			ProjectPanel panel = (ProjectPanel)GuiController.getComponent(projectName + "_ProjectPanel");
			for(int i = 0; i < panel.getComponentCount();i++){
				if(panel.getComponent(i) instanceof Project_File_TabbedPane){
					Project_File_TabbedPane pane = (Project_File_TabbedPane)GuiController.getComponent(projectName + "_Project_File_TabbedPane");
					String fileName = pane.getTitleAt(pane.getSelectedIndex());
					absoluteFileLocation = pro.getFile(fileName).getAbsolutePath();
					break;
				}
			}
		}else{
			absoluteFileLocation = pro.getProjectStartLocation().getAbsolutePath();
		}
		
		/*
		 * Check and make sure the project has a languageType.. if not than grab its file
		 * extension and execute based on that.
		 */
		if(langType == LanguageType.None){
			
			int num = absoluteFileLocation.lastIndexOf('.');
			String extension = "";
			for(;num < absoluteFileLocation.length();num++){
				extension += absoluteFileLocation.charAt(num);
			}
			programCommand = LanguageType.getLanguageCommandBasedOnExtension(extension);
			if(programCommand.length() == 0){
				System.out.println("Not an executable file type...");
				return;
			}
			
			/*
			 * If were here than the project has a LanguageType. However the given
			 * absoluteFileLocation may not have the same fileExtension as the
			 * projects LangugeType implies. If not the same then get the programCommand
			 * based on the fileExtension.
			 */
		}else{
			int num = absoluteFileLocation.lastIndexOf('.');
			String extension = "";
			for(;num < absoluteFileLocation.length();num++){
				extension += absoluteFileLocation.charAt(num);
			}
			if(pro.getExtensionType().equals(extension))
				programCommand = LanguageType.getLanguageCommand(langType);
			else{
				programCommand = LanguageType.getLanguageCommandBasedOnExtension(extension);
			}
			
		}
		
		OSType type = environment.EnvironmentVariables.getOperatingSystem();
		
		switch(type){
		case Linux : setupLinuxList(programCommand,absoluteFileLocation);break;
		case WindowsXP: setupWindowsXPList(programCommand,absoluteFileLocation);break;
		default: setupLinuxList(programCommand,absoluteFileLocation);break;
		}

		new Thread(this).start();
	}
	private void setupLinuxList(String programCommand,String absoluteFileLocation){
		
		
		if(new File("/usr/bin/xterm").exists()){
			list.add("xterm");
			list.add("-geometry");
			list.add("80x20+300+300");
			list.add("-hold");
			list.add("-e");
		}else if( new File("/usr/bin/konsole").exists()){
			list.add("konsole");
			list.add("--geometry");
			list.add("80x40");
			list.add("--hold");
			list.add("-e");
		}else{
			System.out.println("No compatible terminal found...");
			return;
		}

		list.add(programCommand);
		list.add(absoluteFileLocation);
	}
	private void setupWindowsXPList(String programCommand,String absoluteFileLocation){
		list.add("cmd.exe");
		list.add("/c");
		list.add("start");
		absoluteFileLocation = "\""+absoluteFileLocation+"\"";
		list.add(programCommand);
		list.add(absoluteFileLocation);
	}
	public void run(){

		builder = new ProcessBuilder(list);
		
		try{
			process = builder.start();
		}catch(IOException e){
			System.out.println("Class Execute:Run Method:builder.start() " + e);
		}
	}
	public void killExecution(){
		if(process == null)
			return;
		process.destroy();
		process = null;
	}
}