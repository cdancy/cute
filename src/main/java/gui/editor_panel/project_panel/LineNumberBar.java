package gui.editor_panel.project_panel;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JTextArea;

public class LineNumberBar extends JTextArea{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1217138162768365345L;
	
	private int lineCount = 0;
	
	public LineNumberBar(){
		
		this.setBackground(new Color(253, 246, 227));
		this.setSize(new Dimension(15,20));
		this.setPreferredSize(new Dimension(15,20));
		this.setEditable(true);
		this.setLineWrap(true);
	}

	public void addLine(){
		lineCount++;
		this.append(lineCount + "\n");
	}
	public int getLineCount(){
		return lineCount;
	}
	public void removeLine(){
		if(lineCount == 0)
			return;
		lineCount--;
	}
}