package gui.editor_panel.project_panel;

import java.awt.Component;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;

import project.Project;

import controllers.ProjectController;

import customcomponents.CurrentLineHighlighter;

public class FileEditArea extends JScrollPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6496343556649552215L;
	
	private String projectName = null;
	
	private String fileName = null;
	
	private EditPane editPane = null;
	
	private GridBagLayout layout = null;
	
	private GridBagConstraints constraints = null;
	
	public FileEditArea(String projectName,String fileName){
		
		this.projectName = projectName;
		this.fileName = fileName;
		init();
	}
	public String getProjectName(){
		return projectName;
	}
	public String getFileName(){
		return fileName;
	}
	
	public void init(){
		
		super.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		super.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editPane = new EditPane();
		super.setViewportView(editPane);
	}
	
	public EditPane getEditPane(){
		return this.editPane;
	}
	public String getCurrentText(){
		return this.getEditPane().getText();
	}
	public class EditPane extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 2267220355219488894L;
		
		public JTextArea textArea = null;
		
		public LineNumberBar numberBar = null;
		
		public EditPane(){
			
			init();
		}
		public String getText(){
			return textArea.getText();
		}
		public JTextArea getTextArea(){
			return this.textArea;
		}
		public LineNumberBar getLineNumberBar(){
			return this.numberBar;
		}
		public void init(){
			
			layout = new GridBagLayout();
			constraints = new GridBagConstraints();
			super.setLayout(layout);
			
			constraints.fill = GridBagConstraints.VERTICAL;
			constraints.anchor = GridBagConstraints.WEST;
			numberBar = new LineNumberBar();
			addComponent(numberBar,0,0,1,1,0,1);
			
			constraints.fill = GridBagConstraints.BOTH;
			constraints.anchor = GridBagConstraints.EAST;
			textArea = new JTextArea(40,40);

			Project pro = ProjectController.getProject(getProjectName());
			textArea.setFont(new Font("Dialog",Font.BOLD,15));
			textArea.setText(pro.readFile(getFileName()));
			CurrentLineHighlighter.install(textArea,numberBar);
			addComponent(textArea,1,0,1,1,1,1);
			
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
}