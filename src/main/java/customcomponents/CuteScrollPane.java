package customcomponents;

import java.awt.Component;

import javax.swing.JScrollPane;

public class CuteScrollPane extends JScrollPane{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3602625958622325799L;

	public CuteScrollPane(Component view){
		super(view);
	}
	/*
	public JScrollBar createHorizontalScrollBar(){
		
		JScrollBar bar = new JScrollBar();
		bar.setOrientation(JScrollBar.HORIZONTAL);
		bar.setUI(new CuteScrollBarUI());
		return bar;
	}
	public JScrollBar createVerticalScrollBar(){
		
		JScrollBar bar = new JScrollBar();
		bar.setOrientation(JScrollBar.VERTICAL);
		bar.setUI(new CuteScrollBarUI());
		return bar;
	}
	class CuteScrollBarUI extends BasicScrollBarUI{
	
		
		public void paintTrack(Graphics g,JComponent c,Rectangle r){
			g.setColor(Color.red);
			g.drawRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10);
		}
		
		public void paintThumb(Graphics g,JComponent c,Rectangle r){
	
			g.setColor(Color.blue);
			g.fillRoundRect(0, 0, (int)r.getWidth(), (int)r.getHeight(), 10, 10);
		}
		public JButton createIncreaseButton(int orientation){
			return new JButton();
		}
		public JButton createDecreaseButton(int orientation){
			return new JButton();
		}
		public void paint(Graphics g,JComponent c){
			g.setColor(Color.red);
			g.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10);
			super.paint(g, c);
		}
	}
	*/

}
