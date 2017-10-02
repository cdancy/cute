package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import creations.CreateProject;

public class ProjectListener implements ActionListener{
	
	private String projectName = null;
	
	public ProjectListener(){
		
	}
	public ProjectListener(String name){

		this.projectName = name;
	}
	public void actionPerformed(ActionEvent e){
		
		SwingUtilities.invokeLater(new Runnable(){
				
			public void run(){
		
				new CreateProject(projectName);
							
			}
		});
	}
}