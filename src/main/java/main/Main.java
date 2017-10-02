package main;

import environment.SystemOpen;

public class Main {
	
	public static void main(String [] args){
		/*
		 * This seems like a great idea however on my machine it is awfully slow... Must
		 * be doing something wrong.
		 */
		//System.setProperty("sun.java2d.opengl","true");
		new SystemOpen();
	}
}
