package splashscreen;
import java.awt.Component;
import java.awt.*;
import javax.swing.*;
public class SplashMainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7031174573511413577L;
	private GridBagConstraints constraints;
	private GridBagLayout layout;
	private SplashMainPanel smp;
	public SplashMainFrame(){
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);
		createComponents();
		init();
		
	}
	public void createComponents(){
		smp = new SplashMainPanel();
		constraints.fill = GridBagConstraints.BOTH;
		addComponent(smp,0,0,1,1,1,1);
	}
	public void init(){
		super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		super.pack();
		super.setVisible(true);
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
