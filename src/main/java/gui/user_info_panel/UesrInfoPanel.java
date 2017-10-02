package gui.user_info_panel;

import javax.swing.*;

import customcomponents.RoundButton;

import java.awt.*;

public class UesrInfoPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6210664384847264857L;

	private GridBagLayout layout;
	
	private GridBagConstraints constraints;
	
	private Graphics2D gd;
	
	private GradientPaint paint;
	
	private RoundButton online_indicator;
	
	private int width = 100,height = 40;
	
	public UesrInfoPanel(){
		
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		super.setLayout(layout);
		
		init();
		createComponents();
		
	}
	public void init(){
		
		super.setPreferredSize(new Dimension(width,height));
		super.setMaximumSize(new Dimension(width,height));
		super.setMinimumSize(new Dimension(width,height));
		super.setSize(new Dimension(width,height));
		super.setOpaque(false);
	}
	public void createComponents(){
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(3,3,3,3);
		
		online_indicator = new RoundButton(30,30);
		online_indicator.setToolTipText("Online/Offline");
		addComponent(online_indicator,0,0,1,1,1,1);
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
		paint = new GradientPaint(0,0,new Color(214,133,38),getWidth(),0,new Color(214,133,38),true);
		gd.setPaint(paint);
		gd.drawRoundRect(0, 0, getWidth()-2, getHeight()-2, 20, 20);
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_DEFAULT); 
		gd.dispose();
	}
}