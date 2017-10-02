package gui.new_file_start_view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import controllers.*;
import listeners.FileListener;
import customcomponents.*;

public class FileStartView extends JComponent implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8183328254598011327L;

	private GridBagLayout layout;
	
	private GridBagConstraints constraints;
	
	private FileStartViewDragDropPanel fileStartViewDragDropPanel;
	
	private RoundButton newFile;
	
	private int width = 400, height = 300;
	
	private String projectName = null;
	
	private float step = 1.0f;
	
	private boolean fadeStep = true;
	
	private Graphics2D gd = null;

	public FileStartView(String name){
	
		this.projectName = name;
		
		init();
		createComponents();
	}
	
	public void init(){
		
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);
		
		super.setOpaque(false);
		super.setBorder(new EmptyBorder(2,2,2,2));
		
		super.setName(projectName+"_FileStartView");
		GuiController.addComponent(this);
		
		super.setSize(new Dimension(width,height));
		super.setPreferredSize(new Dimension(width,height));
		super.setMaximumSize(new Dimension(width,height));
	}
	
	public void createComponents(){
		
		constraints.fill = GridBagConstraints.NONE;
		constraints.insets = new Insets(2,2,2,2);
		fileStartViewDragDropPanel = new FileStartViewDragDropPanel();
		addComponent(fileStartViewDragDropPanel,0,0,2,1,0,0);
		
		newFile = new RoundButton("Give-Me-a-File!",getWidth(),50);
		newFile.addActionListener(new FileListener());
		addComponent(newFile,1,1,1,1,1,0);
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
    protected void paintChildren(Graphics g) {
		gd = (Graphics2D)g.create();
		gd.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, step));
        super.paintChildren(gd);
    }
	public void run() {
		try{
			if(getFadeStep()){
				step = 0.0f;
				for(int num = 0; num < 20;num++){
					Thread.sleep(25);
					step += .05f;
					if(step > 1.0f)
						step = 1.0f;
					this.repaint();
				}
				step = 1.0f;
				this.repaint();
				this.setFadeStep(false);
			}else{
				for(int num = 0; num < 20;num++){
					Thread.sleep(25);
					step -= .05f;
					if(step < 0)
						step = 0.0f;
					this.repaint();
				}
				step = 0.0f;
				this.repaint();
				this.setFadeStep(true);
			}
		}catch(Exception e){
			System.out.println("FileStartView...Error in Animation.");
		}
	}
	public void setAnimationStepToZero(){
		this.step = 0.0f;
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