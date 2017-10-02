package gui.user_options_panel;

import gui.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import customcomponents.Divider;
import customcomponents.LabelButton;

import controllers.GuiController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UserOptionsPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2338448213859566040L;

	private GridBagLayout layout;
	
	private GridBagConstraints constraints;
	
	private LabelButton projects,preferences,createFile,help,DVCS,chat,todo_list;
	
	private int width = 100,height = 55;
	
	public UserOptionsPanel(){
		
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);

		init();
		createComponents();

	}
	public void createComponents(){
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(2,10,2,10);
		
		projects = new LabelButton("Project?");
		projects.setToolTipText("New Project?");
		projects.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MainFrame frame = (MainFrame)GuiController.getComponent("MainFrame");
				JLayeredPane layer = frame.getLayeredPane();
				Component [] comp = layer.getComponentsInLayer((Integer)(JLayeredPane.DEFAULT_LAYER + 240));
				if(comp.length < 1 ){
					
					if(GuiController.isComponentListed("CreateProjectUI")){
						
						CreateProjectUI createProjectUI = (CreateProjectUI)GuiController.getComponent("CreateProjectUI");
						layer.add(createProjectUI, (Integer)(JLayeredPane.DEFAULT_LAYER + 240));
						createProjectUI.openingAnimation();
					}else{
						
						CreateProjectUI createProjectUI = new CreateProjectUI();
						layer.add(createProjectUI, (Integer)(JLayeredPane.DEFAULT_LAYER + 240));
						createProjectUI.openingAnimation();
					}
				}else if(comp.length == 1 && comp[0] instanceof CreateProjectUI){
					
					CreateProjectUI createProjectUI = (CreateProjectUI)GuiController.getComponent("CreateProjectUI");
					createProjectUI.closingAnimation();
				}else{
					System.out.println("UserOptionsPanel: Too many components!!!");
				}
			}
		});
		addComponent(projects,0,0,1,1,1,0);
		
		createFile = new LabelButton("File?");
		createFile.setToolTipText("Create a File?");
		addComponent(createFile,1,0,1,1,1,0);
		createFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MainFrame frame = (MainFrame)GuiController.getComponent("MainFrame");
				JLayeredPane layer = frame.getLayeredPane();
				Component [] comp = layer.getComponentsInLayer((Integer)(JLayeredPane.DEFAULT_LAYER + 245));
				if(comp.length < 1 ){
					
					if(GuiController.isComponentListed("CreateFileUI")){
						
						CreateFileUI createFileUI = (CreateFileUI)GuiController.getComponent("CreateFileUI");
						layer.add(createFileUI, (Integer)(JLayeredPane.DEFAULT_LAYER + 245));
						createFileUI.openingAnimation();
					}else{
						
						CreateFileUI createFileUI = new CreateFileUI();
						layer.add(createFileUI, (Integer)(JLayeredPane.DEFAULT_LAYER + 245));
						createFileUI.openingAnimation();
					}
				}else if(comp.length == 1 && comp[0] instanceof CreateFileUI){
					
					CreateFileUI createFileUI = (CreateFileUI)GuiController.getComponent("CreateFileUI");
					createFileUI.closingAnimation();
				}else{
					System.out.println("UserOptionsPanel: Too many components!!!");
				}
			}
		});
		
		preferences = new LabelButton("Options");
		preferences.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MainFrame frame = (MainFrame)GuiController.getComponent("MainFrame");
				JLayeredPane layer = frame.getLayeredPane();
			
				Component [] comp = layer.getComponentsInLayer((Integer)(JLayeredPane.DEFAULT_LAYER + 250));
				if(comp.length < 1 ){
					
					if(GuiController.isComponentListed("PreferencesUI")){
						
						PreferencesUI preferencesUI = (PreferencesUI)GuiController.getComponent("PreferencesUI");
						layer.add(preferencesUI, (Integer)(JLayeredPane.DEFAULT_LAYER + 250));
						preferencesUI.openingAnimation();
					}else{
						
						PreferencesUI preferencesUI = new PreferencesUI(frame);
						layer.add(preferencesUI, (Integer)(JLayeredPane.DEFAULT_LAYER + 250));
						preferencesUI.openingAnimation();
					}
				}else if(comp.length == 1 && comp[0] instanceof PreferencesUI){
					
					PreferencesUI preferencesUI = (PreferencesUI)GuiController.getComponent("PreferencesUI");
					preferencesUI.closingAnimation();
				}else{
					System.out.println("UserOptionsPanel: Too many components!!!");
				}
			}
		});
		addComponent(preferences,2,0,1,1,1,0);
	
		DVCS = new LabelButton("DVCS");
		addComponent(DVCS,3,0,1,1,1,0);
		DVCS.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MainFrame frame = (MainFrame)GuiController.getComponent("MainFrame");
				JLayeredPane layer = frame.getLayeredPane();
				Component [] comp = layer.getComponentsInLayer((Integer)(JLayeredPane.DEFAULT_LAYER + 255));
				if(comp.length < 1 ){
					
					if(GuiController.isComponentListed("DVCSUI")){
						
						DVCSUI dvcsUI = (DVCSUI)GuiController.getComponent("DVCSUI");
						layer.add(dvcsUI, (Integer)(JLayeredPane.DEFAULT_LAYER + 255));
						dvcsUI.openingAnimation();
					}else{
						
						DVCSUI dvcsUI = new DVCSUI(frame);
						layer.add(dvcsUI, (Integer)(JLayeredPane.DEFAULT_LAYER + 255));
						dvcsUI.openingAnimation();
					}
				}else if(comp.length == 1 && comp[0] instanceof DVCSUI){
					
					DVCSUI dvcsUI = (DVCSUI)GuiController.getComponent("DVCSUI");
					dvcsUI.closingAnimation();
				}else{
					System.out.println("UserOptionsPanel: Too many components!!!");
				}
			}
		});
		
		chat = new LabelButton("Chat");
		addComponent(chat,4,0,1,1,1,0);
		chat.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MainFrame frame = (MainFrame)GuiController.getComponent("MainFrame");
				JLayeredPane layer = frame.getLayeredPane();
				Component [] comp = layer.getComponentsInLayer((Integer)(JLayeredPane.DEFAULT_LAYER + 260));
				if(comp.length < 1 ){
					
					if(GuiController.isComponentListed("ChatUI")){
						
						ChatUI chatUI = (ChatUI)GuiController.getComponent("ChatUI");
						layer.add(chatUI, (Integer)(JLayeredPane.DEFAULT_LAYER + 260));
						chatUI.openingAnimation();
					}else{
						
						ChatUI chatUI = new ChatUI();
						layer.add(chatUI, (Integer)(JLayeredPane.DEFAULT_LAYER + 260));
						chatUI.openingAnimation();
					}
				}else if(comp.length == 1 && comp[0] instanceof ChatUI){
					
					ChatUI chatUI = (ChatUI)GuiController.getComponent("ChatUI");
					chatUI.closingAnimation();
				}else{
					System.out.println("UserOptionsPanel: Too many components!!!");
				}
			}
		});
		
		help = new LabelButton("Help");
		addComponent(help,5,0,1,1,1,0);
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MainFrame frame = (MainFrame)GuiController.getComponent("MainFrame");
				JLayeredPane layer = frame.getLayeredPane();
				Component [] comp = layer.getComponentsInLayer((Integer)(JLayeredPane.DEFAULT_LAYER + 265));
				if(comp.length < 1 ){
					
					if(GuiController.isComponentListed("HelpUI")){
						
						HelpUI helpUI = (HelpUI)GuiController.getComponent("HelpUI");
						layer.add(helpUI, (Integer)(JLayeredPane.DEFAULT_LAYER + 265));
						helpUI.openingAnimation();
					}else{
						
						HelpUI helpUI = new HelpUI(frame);
						layer.add(helpUI, (Integer)(JLayeredPane.DEFAULT_LAYER + 265));
						helpUI.openingAnimation();
					}
				}else if(comp.length == 1 && comp[0] instanceof HelpUI){
					
					HelpUI helpUI = (HelpUI)GuiController.getComponent("HelpUI");
					helpUI.closingAnimation();
				}else{
					System.out.println("UserOptionsPanel: Too many components!!!");
				}
			}
		});
		
		constraints.fill = GridBagConstraints.VERTICAL;
		addComponent(new Divider(true),6,0,1,1,0,1);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		todo_list = new LabelButton("ToDo.list");
		todo_list.setToolTipText("Cute:Todo-list");
		todo_list.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				MainFrame frame = (MainFrame)GuiController.getComponent("MainFrame");
				JLayeredPane layer = frame.getLayeredPane();
				Component [] comp = layer.getComponentsInLayer((Integer)(JLayeredPane.DEFAULT_LAYER + 270));
				if(comp.length < 1 ){
					
					if(GuiController.isComponentListed("CuteToDoListUI")){
						
						CuteToDoListUI cuteList = (CuteToDoListUI)GuiController.getComponent("CuteToDoListUI");
						layer.add(cuteList, (Integer)(JLayeredPane.DEFAULT_LAYER + 270));
						cuteList.openingAnimation();
					}else{
						
						CuteToDoListUI cuteList = new CuteToDoListUI(frame);
						layer.add(cuteList, (Integer)(JLayeredPane.DEFAULT_LAYER + 270));
						cuteList.openingAnimation();
					}
				}else if(comp.length == 1 && comp[0] instanceof CuteToDoListUI){
					
					CuteToDoListUI cuteListUI = (CuteToDoListUI)GuiController.getComponent("CuteToDoListUI");
					cuteListUI.closingAnimation();
				}else if(comp.length > 1){
					System.out.println("UserOptionsPanel: Too many components!!!");
				}
			}
		});
		addComponent(todo_list,7,0,1,1,1,0);
		
	}
	public void init(){
		
		super.setOpaque(false);
		super.setPreferredSize(new Dimension(width,height));
		super.setMaximumSize(new Dimension(width,height));
		super.setMinimumSize(new Dimension(width,height));
		super.setSize(new Dimension(width,height));
		
		super.setBorder(new EmptyBorder(2,2,2,2));	
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