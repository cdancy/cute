package gui.splash_screen;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

public class SplashMainFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7837603931728497702L;
	public SplashMainFrame(){
		init();
	}
	public void init(){
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		super.setPreferredSize(new Dimension(300,300));
		super.setTitle("SplashScreen");
		super.setLocation((int)screen.getWidth()/3,(int)screen.getHeight()/4);
	}

}
