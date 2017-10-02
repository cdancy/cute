package gui.titlebar;

import javax.swing.*;

import java.awt.*;

public class TitleBar extends JPanel{

		/**
	 * 
	 */
	private static final long serialVersionUID = 3046290793923579566L;

		private GridBagLayout layout;
		
		private GridBagConstraints constraints;
		
		private Graphics2D gd;
		
		private GradientPaint paint;
		
		private JPanel buttonHolder;
		
		private TitleButton minimize,maximize,close;
		
		public TitleBar(){
			
			layout = new GridBagLayout();
			constraints = new GridBagConstraints();
			super.setLayout(layout);
			
			init();
			createComponents();
			
		}
		
		public void init(){
			
			/*
			MoveMouseListener mml = new MoveMouseListener(this,guiC.getMainFrame());
			this.addMouseListener(mml);
			this.addMouseMotionListener(mml);
			
			super.setOpaque(false);
			*/
		}
		
		public void createComponents(){
			
			constraints.fill = GridBagConstraints.NONE;
			constraints.anchor = GridBagConstraints.EAST;
			//constraints.insets = new Insets(6,6,6,6);
			
			buttonHolder = new JPanel();
			buttonHolder.setOpaque(false);
			
			minimize = new TitleButton(Color.yellow,new Color(233,187,106),20,20);
			minimize.addActionListener(new MinimizeListener());
			
			maximize = new TitleButton(Color.green,new Color(233,187,106),20,20);
			maximize.addActionListener(new MaximizeListener());
			
			close = new TitleButton(Color.red,new Color(233,187,106),20,20);
			close.addActionListener(new CloseListener());
			
			buttonHolder.add(minimize);
			buttonHolder.add(maximize);
			buttonHolder.add(close);
			
			addComponent(buttonHolder,0,0,1,1,1,1);
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
