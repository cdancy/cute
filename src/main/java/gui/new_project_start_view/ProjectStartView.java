package gui.new_project_start_view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.*;
import listeners.ProjectListener;
import customcomponents.*;

public class ProjectStartView extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1955451183700177180L;

	private GridBagLayout layout;
	
	private GridBagConstraints constraints;
	
	private ProjectStartViewDragDropPanel projectStartViewDragDropPanel;
	
	private RoundButton newProject,newFile;
	
	private int width = 500, height = 300;

	public ProjectStartView(){
		
		init();
		createComponents();
	}
	
	public void init(){
		
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);
		
		super.setBorder(new EmptyBorder(2,2,2,2));
		
		super.setName("ProjectStartView");
		GuiController.addComponent(this);
		
		super.setSize(new Dimension(width,height));
		super.setPreferredSize(new Dimension(width,height));
		super.setMaximumSize(new Dimension(width,height));
	}
	
	public void createComponents(){
		
		constraints.fill = GridBagConstraints.NONE;
		constraints.insets = new Insets(2,2,2,2);
		projectStartViewDragDropPanel = new ProjectStartViewDragDropPanel();
		addComponent(projectStartViewDragDropPanel,0,0,2,1,0,0);
		
		newProject = new RoundButton("New-Project",200,50);
		newProject.addActionListener(new ProjectListener());
		addComponent(newProject,0,1,1,1,0,0);
		
		newFile = new RoundButton("Text-Editor",200,50);
		newFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		addComponent(newFile,1,1,1,1,0,0);
	}
	
	public void addComponent(Component c, int column, int row, int width, int height,int weightx,int weighty){
		
		constraints.gridx = column;
		constraints.gridy = row;
		constraints.gridheight = height;
		constraints.gridwidth = width;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		layout.setConstraints(c,constraints);
		this.add(c);
	}	
}