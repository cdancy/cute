package splashscreen;
import java.awt.*;

import javax.swing.*;
public class SplashMainPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6556339913883720004L;
	private GridBagLayout layout;
	private GridBagConstraints constraints;
	public SplashMainPanel(){
		constraints = new GridBagConstraints();
		layout = new GridBagLayout();
		super.setLayout(layout);
		createComponents();
		
	}
	public void createComponents(){
		
	}
	public void addComponent(Component c, int column, int row, int width, int height,int weightx,int weighty){
		constraints.gridx = column;
		constraints.gridy = row;
		constraints.gridheight = height;
		constraints.gridwidth = width;
		constraints.weightx = weightx;
		constraints.weighty = weighty;
		layout.setConstraints(c,constraints);
		add(c);
	}
}
