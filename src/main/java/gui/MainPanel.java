package gui;

import gui.editor_panel.EditorPanel;
import gui.editor_panel.Project_TabbedPane;
import gui.user_info_panel.UesrInfoPanel;
import gui.user_options_panel.UserOptionsPanel;
import controllers.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1358899091734112264L;

	private BufferedImage image;
	
	private Graphics2D gd;
	
	private GradientPaint paint;
	
	private GridBagLayout layout;
	
	private GridBagConstraints constraints;
	
	/*
	 * Leaving this out of the initial release. I wanted to make my own titleBar, but
	 * simply ran out of time. Bummer...
	 */
	
	//private TitleBar tbar;
	
	private UserOptionsPanel uop;
	
	private EditorPanel ep;
	
	private UesrInfoPanel np;
	
	public MainPanel(){

		constraints = new GridBagConstraints();
		layout = new GridBagLayout();
		
		super.setLayout(layout);
		super.setBorder(new EmptyBorder(2,2,2,2));
		
		init();
		registerKeyListeners();
		createComponents();

	}

	public void init(){
		
		super.setName("MainPanel");
		GuiController.addComponent(this);
	}
	public void createComponents(){
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		
		/*
		tbar = new TitleBar(guiC);
		addComponent(tbar,0,0,1,1,1,0);
		*/
		
		uop = new UserOptionsPanel();
		addComponent(uop,0,0,1,1,1,0);
		
		constraints.insets = new Insets(2,2,2,2);
		constraints.fill = GridBagConstraints.BOTH;
		ep = new EditorPanel();
		addComponent(ep,0,1,1,1,1,1);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		np = new UesrInfoPanel();
		addComponent(np,0,2,1,1,1,0);
		
	}
	@SuppressWarnings("unchecked")
	public void registerKeyListeners(){
		/*
		 * Removing the traversal keys which are tab and shift so that I can use them
		 * for tabbing through project tabs.
		 */
		 Set forwardKeys = this.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);  
		 Set newForwardKeys = new HashSet(forwardKeys);  
		 newForwardKeys.clear();  
		 this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,newForwardKeys); 
		 
		 forwardKeys = this.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS);  
		 newForwardKeys = new HashSet(forwardKeys);  
		 newForwardKeys.clear();  
		 this.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,newForwardKeys);

			/*
			 * KeyEvent to tab to the next project tab
			 */
			this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK),"Tab_Projects"); 
			this.getActionMap().put("Tab_Projects", Tab_Projects);
	}
	public void paintComponent(Graphics g){
		
		if(image == null || image.getWidth() != getWidth() || image.getHeight() != getHeight()){
			
			image = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
			gd = (Graphics2D)image.getGraphics();
			paint = new GradientPaint(0,0,new Color(252,242,224),getWidth(),getHeight(),new Color(247,218,165),true);
			gd.setPaint(paint);
			gd.fillRect(0, 0, getWidth(), getHeight());
			gd.dispose();
		}
		g.drawImage(image,0,0,this);
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
	/*
	 * Action to tab through the list of projects.
	 */
	Action Tab_Projects = new AbstractAction(){

		/**
		 * 
		 */
		private static final long serialVersionUID = -654389915310561884L;

		public void actionPerformed(ActionEvent arg0) {
				if(!GuiController.isComponentListed("Project_TabbedPane"))
					return;
				Project_TabbedPane tabbedPane = (Project_TabbedPane)GuiController.getComponent("Project_TabbedPane");
				int count = tabbedPane.getTabCount();

				//count == 0 no tabs in pane
				if(count == 0)
					return;
				//count == 1 only 1 tab so no need to move
				else if(count == 1)
					return;
				else{
					int index = tabbedPane.getSelectedIndex();
					//if at the last index than move back to beginning
					//else move to the next tab.
					if(index == (count - 1))
						tabbedPane.setSelectedIndex(0);
					else{
						tabbedPane.setSelectedIndex(++index);
					}
				}
		}	
	};
}