package language;

import java.util.HashMap;
import java.awt.Color;
public class Ruby implements Language{
	private HashMap<String,Color> word_color_map = new HashMap<String, Color>();
	
	private Color languageColor = new Color(206,49,30);
	
	public Ruby(){
		create_word_color_map();
	}
	/*
	 * Check to see is specified string is a keyword
	 */
	public boolean getKeyWord(String s){
		if(word_color_map.containsKey(s))
			return true;
		else
			return false;
	}
	/*
	 * Mapping all keywords to their colors for editing.
	 */
	private void create_word_color_map(){
		word_color_map.put("_FILE_",Color.black);
		word_color_map.put("_LINE_",Color.black);
		word_color_map.put("BEGIN",Color.black);
		word_color_map.put("END",Color.black);
		word_color_map.put("alias",Color.black);
		word_color_map.put("and",Color.black);
		word_color_map.put("begin",Color.black);
		word_color_map.put("break",Color.black);
		word_color_map.put("case",Color.black);
		word_color_map.put("class",Color.black);
		word_color_map.put("def",Color.black);
		word_color_map.put("defined",Color.black);
		word_color_map.put("do",Color.black);
		word_color_map.put("else",Color.black);
		word_color_map.put("elsif",Color.black);
		word_color_map.put("end",Color.black);
		word_color_map.put("ensure",Color.black);
		word_color_map.put("false",Color.black);
		word_color_map.put("for",Color.black);
		word_color_map.put("if",Color.black);
		word_color_map.put("in",Color.black);
		word_color_map.put("module",Color.black);
		word_color_map.put("next",Color.black);
		word_color_map.put("nil",Color.black);
		word_color_map.put("not",Color.black);
		word_color_map.put("or",Color.black);
		word_color_map.put("redo",Color.black);
		word_color_map.put("rescue",Color.black);
		word_color_map.put("retry",Color.black);
		word_color_map.put("return",Color.black);
		word_color_map.put("self",Color.black);
		word_color_map.put("super",Color.black);
		word_color_map.put("then",Color.black);
		word_color_map.put("true",Color.black);
		word_color_map.put("undef",Color.black);
		word_color_map.put("unless",Color.green);
		word_color_map.put("until",Color.black);
		word_color_map.put("when",Color.black);
		word_color_map.put("while",Color.black);
		word_color_map.put("yield",Color.black);
	}
	/*
	 * Ruby is a scripted language so dont need of a compiler.
	 */
	public String getCompiler() {
		return null;
	}
	/*
	 * Returns a Color corresponding to the given String. String is 
	 * case-sensitive.
	 */
	public Color getKeyWordColor(String s) {
		if(word_color_map.containsKey(s))
			return word_color_map.get(s);
		else
			return null;
	}
	/*
	 * Program name to execute. -w for all warnings on.
	 */
	public String getProgramExecutor() {
		return "ruby -w ";
	}
	
	public Color getLanguageColor(){
		return languageColor;
	}
}
