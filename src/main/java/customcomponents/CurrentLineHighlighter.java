/*
 * This class can be used to highlight the current line for any JTextComponent. 
 * 
 * @author Santhosh Kumar T 
 * @author Peter De Bruycker 
 * @version 1.0 
 */ 
package customcomponents;

import gui.editor_panel.project_panel.LineNumberBar;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.event.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
public class CurrentLineHighlighter{ 
	
    private static final String LINE_HIGHLIGHT = "linehilight"; //NOI18N - used as clientproperty 
    private static final String PREVIOUS_CARET = "previousCaret"; //NOI18N - used as clientproperty 
    private static Color col = new Color(247, 222, 174); //Color used for highlighting the line 
    
    private static LineNumberBar lineBar = null;
    
    // to be used as static utility 
    private CurrentLineHighlighter(){} 
 
    // Installs CurrentLineHilighter for the given JTextComponent 
    public static void install(JTextComponent c, LineNumberBar bar){ 
        try{ 
        	lineBar = bar;
            Object obj = c.getHighlighter().addHighlight(0, 0, painter); 
            c.putClientProperty(LINE_HIGHLIGHT, obj); 
            c.putClientProperty(PREVIOUS_CARET, new Integer(c.getCaretPosition())); 
            c.addCaretListener(caretListener); 
            c.addMouseListener(mouseListener); 
            c.addMouseMotionListener(mouseListener); 
            c.addPropertyChangeListener(propertyListener);
           // c.getDocument().addDocumentListener(documentListener);
            c.setCaret(new CustomCaret());	
            
        } catch(BadLocationException ignore){} 
    } 
 
    // Uninstalls CurrentLineHighligher for the given JTextComponent 
    public static void uninstall(JTextComponent c){ 
        c.putClientProperty(LINE_HIGHLIGHT, null); 
        c.putClientProperty(PREVIOUS_CARET, null); 
        c.removeCaretListener(caretListener); 
        c.removeMouseListener(mouseListener); 
        c.removeMouseMotionListener(mouseListener); 
        c.removePropertyChangeListener(propertyListener);
    } 
    
    private static DocumentListener documentListener = new DocumentListener(){

		public void changedUpdate(DocumentEvent e) {
			Document d = e.getDocument();
			try {
				if(d.getText(0, d.getLength()).equals("\n")){
					System.out.println("made a change");
				}
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			
		};
		public void insertUpdate(DocumentEvent e){
			Document d = e.getDocument();
			try {
				String s = d.getText(e.getOffset(), e.getLength());
				if(s.equals("\n"))
					lineBar.addLine();
			} catch (BadLocationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		};
		public void removeUpdate(DocumentEvent e){
			Document d = e.getDocument();
			try {
				
				String s = d.getText(e.getOffset(), e.getLength());
				if(s.equals("\n"))
					System.out.println("removed a line");
			} catch (BadLocationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
		};
    };
    private static PropertyChangeListener propertyListener = new PropertyChangeListener(){

		public void propertyChange(PropertyChangeEvent e) {
			
		}
    };
    private static CaretListener caretListener = new CaretListener(){ 
        public void caretUpdate(CaretEvent e){ 
      
            JTextComponent c = (JTextComponent)e.getSource(); 
            currentLineChanged(c); 
        } 
    }; 
 
    private static MouseInputAdapter mouseListener = new MouseInputAdapter(){ 
        public void mousePressed(MouseEvent e){ 
            JTextComponent c = (JTextComponent)e.getSource(); 
            currentLineChanged(c); 
        } 
        public void mouseDragged(MouseEvent e){ 
            JTextComponent c = (JTextComponent)e.getSource(); 
            currentLineChanged(c); 
        } 
    }; 
 
    /** 
     * Fetches the previous caret location, stores the current caret location, 
     * If the caret is on another line, repaint the previous line and the current line 
     * 
     * @param c the text component 
     */ 
    private static void currentLineChanged(JTextComponent c){ 
        try{ 
            int previousCaret = ((Integer)c.getClientProperty(PREVIOUS_CARET)).intValue(); 
            Rectangle prev = c.modelToView(previousCaret); 
            Rectangle r = c.modelToView(c.getCaretPosition()); 
            c.putClientProperty(PREVIOUS_CARET, new Integer(c.getCaretPosition())); 
 
            if(prev.y != r.y){ 
                c.repaint(0, prev.y, c.getWidth(), r.height); 
                c.repaint(0, r.y, c.getWidth(), r.height); 
            } 
        } catch(BadLocationException ignore){} 
    } 
 
    private static Highlighter.HighlightPainter painter = new Highlighter.HighlightPainter(){ 
        public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c){ 
            try{ 
                Rectangle r = c.modelToView(c.getCaretPosition()); 
                g.setColor(col); 
                g.fillRect(0, r.y, c.getWidth(), r.height); 
            } catch(BadLocationException ignore){} 
        } 
    };
    private static class CustomCaret extends DefaultCaret{

		/**
		 * 
		 */
		private static final long serialVersionUID = -5388643392385815928L;

    }
 }
