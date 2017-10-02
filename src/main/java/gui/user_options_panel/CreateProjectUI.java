package gui.user_options_panel;


import controllers.*;

import environment.EnvironmentVariables;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import project.LanguageType;
import project.VersionControlType;

import creations.CreateProject;

import customcomponents.Divider;
import customcomponents.RoundButton;

public class CreateProjectUI extends JComponent implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1054711695731814995L;
	
	private BufferedImage border = null;
	
	private BufferedImage background = null;
	
	private Graphics2D gd = null;
	
	private GridBagLayout layout = null;
	
	private GridBagConstraints constraints = null;
	
	private float step = .0f;
	
	private boolean fadeStep = true;
	
	private JLabel createProjectTitle = null,projectName = null,projectType = null,
					defaultProjectDirectory = null,versionType = null;
	
	private JRadioButton ruby,python,java,c,c_plusplus,no_lang;
	
	private JRadioButton git,cvs,svn,bzr,no_vcs;
	
	private ButtonGroup languageGroup = null, versionControlGroup = null;
	
	private JTextField projectNameField = null,defaultProjectDirectoryField = null;
	
	private Divider titleDivide = null;
	
	private RoundButton createProject = null,cancel = null;
	
	private LanguageType lang = LanguageType.None;
	
	private VersionControlType verType = VersionControlType.None;
	
	public CreateProjectUI(){
		
		init();
		
		super.setSize(new Dimension(600,450));
		super.setPreferredSize(new Dimension(600,450));
		super.setLocation(100, 50);
		
		createComponents();
	}
	
	public void createComponents(){
		
		languageGroup = new ButtonGroup();
		versionControlGroup = new ButtonGroup();
		
		constraints.insets = new Insets(5,5,0,5);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		createProjectTitle = new JLabel("Create a Project?");
		createProjectTitle.setFont(new Font(null,Font.BOLD,40));
		addComponent(createProjectTitle,0,0,6,1,1,0);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		titleDivide = new Divider(Color.gray,new Color(248,223,178));
		addComponent(titleDivide,0,1,6,1,1,0);
		
		constraints.insets = new Insets(10,5,20,5);
		constraints.fill = GridBagConstraints.NONE;
		projectName = new JLabel("Project Name:");
		projectName.setFont(new Font(null,20,20));
		addComponent(projectName,0,2,1,1,0,0);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		projectNameField = new JTextField(20);
		projectNameField.getDocument().addDocumentListener(new DocumentListener(){

			public void changedUpdate(DocumentEvent arg0) {};
			public void insertUpdate(DocumentEvent arg0) {
				Document d = arg0.getDocument();
				try {
					String text = d.getText(d.getLength()-1, 1);
					String currentText = defaultProjectDirectoryField.getText();
					defaultProjectDirectoryField.setText(currentText += text);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			};
			public void removeUpdate(DocumentEvent arg0){
				if(arg0.getOffset() == 0 && arg0.getLength() == 0)
					return;
				String currentText = defaultProjectDirectoryField.getText();
				defaultProjectDirectoryField.setText(currentText.substring(0, currentText.length()-1));
			};
		});
		addComponent(projectNameField,1,2,2,1,0,0);
		
		constraints.fill = GridBagConstraints.NONE;
		projectType = new JLabel("Project Type:");
		projectType.setFont(new Font(null,20,20));
		addComponent(projectType,0,3,1,1,0,0);
		
		addComponent(new LanguageRadioPanel(),1,3,1,1,0,0);
		
		constraints.fill = GridBagConstraints.NONE;
		versionType = new JLabel("Version Type:");
		versionType.setFont(new Font(null,20,20));
		addComponent(versionType,2,3,1,1,0,0);
		
		addComponent(new VersionControlRadioPanel(),3,3,1,1,0,0);
		
		defaultProjectDirectory = new JLabel("Default Directory:");
		defaultProjectDirectory.setFont(new Font(null,20,20));
		addComponent(defaultProjectDirectory,0,4,1,1,0,0);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		defaultProjectDirectoryField = new JTextField(20);
		defaultProjectDirectoryField.setText
			(environment.EnvironmentVariables.getDefaultWorkspaceDirectory());
		defaultProjectDirectoryField.setEditable(false);
		addComponent(defaultProjectDirectoryField,1,4,2,1,1,0);
		
		constraints.insets = new Insets(10,10,10,10);
		constraints.fill = GridBagConstraints.NONE;
		createProject = new RoundButton("Create This Project?",150,50);
		createProject.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String name = projectNameField.getText().trim();
				String directory = defaultProjectDirectoryField.getText().trim();

				if(environment.EnvironmentVariables.isLinuxOS())
					directory += "/";
				else if(environment.EnvironmentVariables.isWindowsOS())
					directory += "\\";
				else
					System.out.println("CreateProjectUI...unrecognized OS...");
				
				Enumeration<AbstractButton> but = languageGroup.getElements();
				while(but.hasMoreElements()){
					JRadioButton button = (JRadioButton)but.nextElement();
					if(button.isSelected()){
						lang = LanguageType.getLanguageType(button.getText());
						break;
					}
				}

				Enumeration<AbstractButton> ver = versionControlGroup.getElements();
				while(ver.hasMoreElements()){
					JRadioButton button = (JRadioButton)ver.nextElement();
					if(button.isSelected()){
						verType = VersionControlType.getVersionControlType(button.getText());
						break;
					}
				}
				if(name.equals(null) || name.equals("")){
					clearUI();
					closingAnimation();
					return;
				}
				
				String extType = LanguageType.getLanuageExtension(lang);
				Color color = LanguageType.getLanguageColor(lang);
				new CreateProject(name,lang,directory,verType,extType,color);
				clearUI();
				closingAnimation();
			}
		});
		addComponent(createProject,0,5,1,1,0,0);
		
		cancel = new RoundButton("Cancel This Project?",150,50);
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clearUI();
				closingAnimation();
			}
		});
		addComponent(cancel,2,5,1,1,0,0);
	
	}
	public void clearUI(){
		projectNameField.setText("");
		String direc = environment.EnvironmentVariables.getDefaultWorkspaceDirectory();
		defaultProjectDirectoryField.setText(direc);
		no_lang.setSelected(true);
		no_vcs.setSelected(true);
	}

	public class LanguageRadioPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 3327078792539028709L;

		public LanguageRadioPanel(){
			
			Box b = new Box(BoxLayout.Y_AXIS);
			super.setOpaque(false);
			super.setLayout(new FlowLayout(FlowLayout.TRAILING));
			
			ruby = new JRadioButton("Ruby");
			if(!EnvironmentVariables.isRubyAvailable())
				ruby.setEnabled(false);
			ruby.setMnemonic('R');
			ruby.setContentAreaFilled(false);
			languageGroup.add(ruby);
			b.add(ruby);
			
			python = new JRadioButton("Python");
			if(!EnvironmentVariables.isPythonAvailable())
				python.setEnabled(false);
			python.setMnemonic('P');
			python.setContentAreaFilled(false);
			languageGroup.add(python);
			b.add(python);
			
			java = new JRadioButton("Java");
			if(!EnvironmentVariables.isJavaAvailable())
				java.setEnabled(false);
			java.setMnemonic('J');
			java.setContentAreaFilled(false);
			languageGroup.add(java);
			b.add(java);
			
			c = new JRadioButton("C");
			if(!EnvironmentVariables.isC_Available())
				c.setEnabled(false);
			c.setMnemonic('C');
			c.setContentAreaFilled(false);
			languageGroup.add(c);
			b.add(c);
			
			c_plusplus = new JRadioButton("C++");
			if(!EnvironmentVariables.isCplusplus_Available())
				c_plusplus.setEnabled(false);
			c_plusplus.setMnemonic('C');
			c_plusplus.setContentAreaFilled(false);
			languageGroup.add(c_plusplus);
			b.add(c_plusplus);
			
			no_lang = new JRadioButton("None");
			no_lang.setSelected(true);
			no_lang.setMnemonic('N');
			no_lang.setContentAreaFilled(false);
			languageGroup.add(no_lang);
			b.add(no_lang);
			
			super.add(b);
		}
	}
	public class VersionControlRadioPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = -496369138505546423L;

		public VersionControlRadioPanel(){
			
			Box b = new Box(BoxLayout.Y_AXIS);
			super.setOpaque(false);
			super.setLayout(new FlowLayout(FlowLayout.TRAILING));
			
			git = new JRadioButton("GIT");
			if(!EnvironmentVariables.isGITAvailable())
				git.setEnabled(false);
			git.setMnemonic('G');
			git.setContentAreaFilled(false);
			versionControlGroup.add(git);
			b.add(git);
			
			cvs = new JRadioButton("CVS");
			if(!EnvironmentVariables.isCVSAvailable())
				cvs.setEnabled(false);
			cvs.setMnemonic('C');
			cvs.setContentAreaFilled(false);
			versionControlGroup.add(cvs);
			b.add(cvs);
			
			svn = new JRadioButton("SVN");
			if(!EnvironmentVariables.isSVNAvailable())
				svn.setEnabled(false);
			svn.setMnemonic('S');
			svn.setContentAreaFilled(false);
			versionControlGroup.add(svn);
			b.add(svn);
			
			bzr = new JRadioButton("BZR");
			if(!EnvironmentVariables.isBZRAvailable())
				bzr.setEnabled(false);
			bzr.setMnemonic('B');
			bzr.setContentAreaFilled(false);
			versionControlGroup.add(bzr);
			b.add(bzr);
			
			no_vcs = new JRadioButton("None");
			no_vcs.setSelected(true);
			no_vcs.setMnemonic('N');
			no_vcs.setContentAreaFilled(false);
			versionControlGroup.add(no_vcs);
			b.add(no_vcs);
			
			super.add(b);
		}
	}
	public void init(){
		
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);
		
		super.setName("CreateProjectUI");
		GuiController.addComponent(this);
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
	public void paintComponent(Graphics g){
				
		if(background == null || this.getWidth() != background.getWidth() || this.getHeight() != background.getHeight()){
			background = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)background.getGraphics();
			GradientPaint paint = new GradientPaint(0,0,new Color(247,218,165),0,getHeight(),Color.white,true);
			gd.setPaint(paint);
			gd.fillRoundRect(0, 0, getWidth(), getHeight(),20,20);
		}
		gd = (Graphics2D)g.create();
		gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step));
		gd.drawImage(background,0,0,this);
		gd.dispose();
	}
    protected void paintChildren(Graphics g) {
		gd = (Graphics2D)g.create();
		gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step));
        super.paintChildren(gd);
    }
	public void paintBorder(Graphics g){
		
		if(border == null || border.getWidth() != this.getWidth() || border.getHeight() != this.getHeight()){
			border = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
			gd = (Graphics2D)border.getGraphics();
			gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			gd.setStroke(new BasicStroke(1.5f));
			gd.setPaint(Color.gray);
			gd.drawRoundRect(0, 0, getWidth()-1, getHeight()-1,20,20);
			gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_DEFAULT);
		}
		gd = (Graphics2D)g.create();
		gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step));
		gd.drawImage(border,0,0,this);
		gd.dispose();
	}
	public void run() {
		try{
			if(getFadeStep()){
				for(int num = 0; num < 20;num++){
					Thread.sleep(25);
					step += .05f;
					if(step > 1.0f)
						step = 1.0f;
					this.repaint();
				}
				this.setFadeStep(false);
			}else{
				for(int num = 0; num < 20;num++){
					Thread.sleep(25);
					step -= .05f;
					if(step < 0)
						step = 0.0f;
					this.repaint();
				}
				JLayeredPane.getLayeredPaneAbove(this).repaint(this.getX(), this.getY(), getWidth(), getHeight());
				JLayeredPane.getLayeredPaneAbove(this).remove(this);
				this.setFadeStep(true);
			}
		}catch(Exception e){
			System.out.println("CreateProjectUI...Error in Animation.");
		}
	}
	public void setFadeStep(boolean step){
		fadeStep = step;
	}
	public boolean getFadeStep(){
		return fadeStep;
	}
	public void closingAnimation(){
		new Thread(this).start();
	}
	public void openingAnimation(){
		new Thread(this).start();
	}
}