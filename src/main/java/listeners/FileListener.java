package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import creations.CreateFile;

public class FileListener implements ActionListener{
	
	private String fileName = null;
	
	public FileListener(){
		
	}
	public FileListener(String fileName){
		
		this.fileName = fileName;
	}
	public void actionPerformed(ActionEvent e){
		
		SwingUtilities.invokeLater(new Runnable(){
			
			public void run(){
		
				new CreateFile(fileName);
							
			}
		});	
	}
}