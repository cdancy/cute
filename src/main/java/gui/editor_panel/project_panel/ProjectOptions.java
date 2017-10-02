package gui.editor_panel.project_panel;

import javax.swing.*;

import project.Project;

import controllers.*;

import customcomponents.Divider;
import customcomponents.FadeButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProjectOptions extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8356922503345669194L;

	private GridBagLayout layout;
	
	private GridBagConstraints constraints;
	
	private Graphics2D gd;
	
	private GradientPaint paint;
	
	private FadeButton git_add,git_commit,git_branch,git_merge,git_tag,git_rebase,git_diff,git_push,git_pull;
	
	private FadeButton todo_list,project_goals,blue_prints;
	
	private FadeButton execute_program,terminate_program;
	
	private Divider div,div1;
	
	private String projectName = null;
	
	public ProjectOptions(String projectName){
		
		this.projectName = projectName;
		
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);
		
		createComponents();
	}

	public void createComponents(){
		
		constraints.fill = GridBagConstraints.NONE;
		constraints.insets = new Insets(10,10,10,10);
		
		execute_program = new FadeButton("",25,25,Color.white,new Color(151,228,56));
		execute_program.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Project pro = ProjectController.getProject(projectName);
				pro.executeProject();
			}
		});
		execute_program.setToolTipText("execute");
		addComponent(execute_program,0,0,1,1,0,0);
		
		terminate_program = new FadeButton("",25,25,Color.white,new Color(225,40,27));
		terminate_program.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Project pro = ProjectController.getProject(projectName);
				if(!pro.getExecution().equals(null))
					pro.terminateProject();
			}
		});
		terminate_program.setToolTipText("terminate");
		addComponent(terminate_program,1,0,1,1,0,0);
		
		constraints.fill = GridBagConstraints.VERTICAL;
		div1 = new Divider(true);
		addComponent(div1,2,0,1,1,0,1);
		
		constraints.fill = GridBagConstraints.NONE;
		git_add = new FadeButton("",25,25);
		git_add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		git_add.setToolTipText("git:add");
		addComponent(git_add,3,0,1,1,0,0);
		
		git_commit = new FadeButton("",25,25);
		git_commit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		git_commit.setToolTipText("git:commit");
		addComponent(git_commit,4,0,1,1,0,0);
		
		git_branch = new FadeButton("",25,25);
		git_branch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		git_branch.setToolTipText("git:branch");
		addComponent(git_branch,5,0,1,1,0,0);
		
		git_merge = new FadeButton("",25,25);
		git_merge.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		git_merge.setToolTipText("git:merge");
		addComponent(git_merge,6,0,1,1,0,0);
		
		git_tag = new FadeButton("",25,25);
		git_tag.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		git_tag.setToolTipText("git:tag");
		addComponent(git_tag,7,0,1,1,0,0);
		
		git_rebase = new FadeButton("",25,25);
		git_rebase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		git_rebase.setToolTipText("git:rebase");
		addComponent(git_rebase,8,0,1,1,0,0);
		
		git_diff = new FadeButton("",25,25);
		git_diff.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		git_diff.setToolTipText("git:diff");
		addComponent(git_diff,9,0,1,1,0,0);
		
		git_push = new FadeButton("",25,25);
		git_push.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		git_push.setToolTipText("git:push");
		addComponent(git_push,10,0,1,1,0,0);
		
		git_pull = new FadeButton("",25,25);
		git_pull.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		git_pull.setToolTipText("git:pull");
		addComponent(git_pull,11,0,1,1,0,0);
		
		constraints.fill = GridBagConstraints.VERTICAL;
		div = new Divider(true);
		addComponent(div,12,0,1,1,0,1);
		
		constraints.fill = GridBagConstraints.NONE;
		todo_list = new FadeButton("",25,25,Color.white,Color.yellow);
		todo_list.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		todo_list.setToolTipText("todo notes");
		addComponent(todo_list,13,0,1,1,0,0);
		
		project_goals = new FadeButton("",25,25,Color.white,Color.yellow);
		project_goals.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		project_goals.setToolTipText("project goals");
		addComponent(project_goals,14,0,1,1,0,0);
		
		blue_prints = new FadeButton("",25,25,Color.white,new Color(103,121,230));
		blue_prints.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
			}
		});
		blue_prints.setToolTipText("Blue-Prints");
		addComponent(blue_prints,15,0,1,1,0,0);
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
	public void paintBorder(Graphics g){
		gd = (Graphics2D)g.create();
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		gd.setStroke(new BasicStroke(1));
		paint = new GradientPaint(0,0,Color.gray,getWidth(),0,Color.white,true);
		gd.setPaint(paint);
		gd.drawRoundRect(0, 0, getWidth()-2, getHeight()-2, 20, 20);
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_DEFAULT); 
		gd.dispose();
	}
	public void paintComponent(Graphics g){
		gd = (Graphics2D)g.create();
		paint = new GradientPaint(0,0,new Color(247,218,165),0,getHeight(),Color.white,true);
		gd.setPaint(paint);
		gd.fillRoundRect(0, 0, getWidth()-2, getHeight()-2,20,20);
		gd.dispose();
	}
}
