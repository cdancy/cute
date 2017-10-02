package customcomponents;

import gui.editor_panel.Project_TabbedPane;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import controllers.ProjectController;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ProjectTabbedPaneUI extends BasicTabbedPaneUI{
	
	private final Insets insets = new Insets(1,1,1,1);
	
	private Graphics2D gd = null;

	private Font boldFont;

	private FontMetrics boldFontMetrics;

	private GradientPaint defaultUnselectedColorBackground = new GradientPaint(0,0,new Color(247,218,165),0,40,Color.gray.brighter(),true);

	private GradientPaint defaultSelectedColorBackground = new GradientPaint(0,5,new Color(247,218,165),0,this.maxTabHeight + 20,Color.white,true);
	
	private GradientPaint backgroundPaint = null;
	
	private BufferedImage background = null;
	
	public static ComponentUI createUI(JComponent c){
		
		return new ProjectTabbedPaneUI();
	}

	protected void installDefaults(){
	
		super.installDefaults();
		tabAreaInsets.left = (calculateTabHeight(0, 0, tabPane.getFont().getSize()) / 4) + 1;
		selectedTabPadInsets = new Insets(0, 0, 0, 0);

		boldFont = tabPane.getFont().deriveFont(Font.BOLD);
		boldFontMetrics = tabPane.getFontMetrics(boldFont);
	}

	protected Insets getContentBorderInsets(int tabPlacement){
		
		return insets;
	}

	protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight){
		
		int vHeight = fontHeight + 2;
		if (vHeight % 2 == 0){
			
			vHeight += 1;
		}
		return vHeight + 4;
	}

	protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics){
		
		return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + metrics.getHeight();
	}
	
	
	public void paint(Graphics g,JComponent j){
		
		if(background == null || background.getWidth() != tabPane.getWidth() || background.getHeight() != tabPane.getHeight()){
			
			background = new BufferedImage(j.getWidth(),j.getHeight(),BufferedImage.TYPE_INT_ARGB);
			backgroundPaint = new GradientPaint(0,0,new Color(247,218,165).darker(),0,j.getHeight(),new Color(247,218,165),true);
			gd = (Graphics2D)background.getGraphics();
			gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			gd.setPaint(backgroundPaint);
			gd.fillRoundRect(0, 0 + this.maxTabHeight + 2, j.getWidth(), j.getHeight()-this.maxTabHeight, 20, 20);
			gd.dispose();
		}
		g.drawImage(background,0,0,null);
	}

	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected){
		
		String title = tabPane.getTitleAt(tabIndex);
		if(tabPane instanceof Project_TabbedPane){
			Color c = ProjectController.getProject(title).getProjectColor();
			if(c != null){
				defaultSelectedColorBackground = new GradientPaint(0,-4,c.brighter(),0,this.maxTabHeight + 3 ,Color.white,true);
			}	
		}
		Graphics2D gd = (Graphics2D)g.create();

		if (isSelected){
			gd.setPaint(defaultSelectedColorBackground);
		}else{
			gd.setPaint(defaultUnselectedColorBackground);
		}

		gd.fillArc(x, y-2, w, h, -180, -180);
		gd.fillRect(x, y+7, w, h);

		gd.dispose();
	}

	protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected){
		
		Graphics2D gd = (Graphics2D)g.create();
		gd.setStroke(new BasicStroke(1.3f));
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(isSelected){
			gd.setColor(new Color(247,229,212));
		}else
			gd.setColor(darkShadow);

		gd.drawLine(x, y+7, x, y+h);
		gd.drawArc(x, y-2, w, h, -180, -180);
		gd.drawLine(x+w,y+7,x+w,y+h);
		gd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_DEFAULT);
		gd.dispose();
	}

	protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int selectedIndex, int x, int y, int w, int h){
		
		Rectangle selectedRect = selectedIndex < 0 ? null : getTabBounds(selectedIndex, calcRect);
		g.setColor(darkShadow);
		g.drawLine(x, y, selectedRect.x - (selectedRect.height / 4), y);
		g.drawLine(selectedRect.x + selectedRect.width + (selectedRect.height / 4), y, x + w, y);
		g.setColor(Color.white);
		g.drawLine(selectedRect.x - (selectedRect.height / 4)+1, y,selectedRect.x + selectedRect.width + (selectedRect.height / 4)-1, y);		

	}
	
	protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected){
		/*
		 * We don't want that goofy looking square over textarea when selected, so 
		 * do nothing.
		 */
	}

	protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected){
		
		if (isSelected){
		
			int vDifference = (int) (boldFontMetrics.getStringBounds(title, g).getWidth()) - textRect.width;
			textRect.x -= (vDifference / 2);
			super.paintText(g, tabPlacement, new Font("Default",Font.BOLD,13), boldFontMetrics, tabIndex, title, textRect, isSelected);
		}
		else{
			
			super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
		}
	}
}