package project;

import java.awt.Color;

import environment.OSType;

public enum LanguageType {
	Ruby,Java,Python,C,C_plusplus,None;
	
	public static String getLanguageCommand(LanguageType type){
		
		if(environment.EnvironmentVariables.getOperatingSystem().equals(OSType.Linux)){
			
			switch(type){
			case Ruby: return "/usr/bin/ruby";
			case Java: return "/usr/bin/java";
			case Python: return "/usr/bin/python";
			case C: return "/usr/bin/gcc";
			case C_plusplus: return "/usr/bin/g++";
			case None: return "";
			default : return "";
			}
		}
		else if(environment.EnvironmentVariables.getOperatingSystem().equals(OSType.WindowsXP)){
			
			switch(type){
			case Ruby: return "C:\\ruby\\bin\\ruby.exe";
			case Java: return "C:\\Sun\\SDK\\jdk\\bin\\java.exe";
			case Python: return "C:\\Python24\\python.exe";
			case C: return "/usr/bin/gcc";
			case C_plusplus: return "/usr/bin/g++";
			case None: return "";
			default : return "";
			}
		}else if(environment.EnvironmentVariables.getOperatingSystem().equals(OSType.MacOSX)){
			
			switch(type){
			case Ruby: return "/usr/bin/ruby";
			case Java: return "/usr/bin/java";
			case Python: return "/usr/bin/python";
			case C: return "/usr/bin/gcc";
			case C_plusplus: return "/usr/bin/g++";
			case None: return "";
			default : return "";
			}
		}else
			return "";
	}
	public static LanguageType getLanguageType(String type){
		
		if(type.equals("Ruby"))
			return LanguageType.Ruby;
		else if(type.equals("Java"))
			return LanguageType.Java;
		else if(type.equals("Python"))
			return LanguageType.Python;
		else if(type.equals("C"))
			return LanguageType.C;
		else if(type.equals("C++"))
			return LanguageType.C_plusplus;
		else if(type.equals("None"))
			return LanguageType.None;
		else if(type.equals(null))
			return LanguageType.None;
		else if(type.equals(""))
			return LanguageType.None;
		else
			return LanguageType.None;
	}
	
	public static Color getLanguageColor(LanguageType type){
		
		switch(type){
		case Ruby: return new Color(239,48,48);
		case Java: return new Color(221,67,5);
		case Python: return new Color(55,106,148);
		case C: return Color.gray;
		case C_plusplus: return new Color(70,182,62);
		case None: return new Color(242,191,133).darker();
		default : return new Color(242,191,133).darker();
		}
	}
	
	public static String getLanguageCompilerCommand(LanguageType type){
		if(environment.EnvironmentVariables.getOperatingSystem().equals(OSType.Linux)){
			
			switch(type){
			case Java: return "/usr/bin/javac";
			case C: return "/usr/bin/gcc";
			case C_plusplus: return "/usr/bin/g++";
			case None: return "";
			default : return "";
			}
		}
		else if(environment.EnvironmentVariables.getOperatingSystem().equals(OSType.WindowsXP)){
			
			switch(type){
			case Java: return "C:\\Sun\\SDK\\jdk\\bin\\javac.exe";
			case C: return "C:\\";
			case C_plusplus: return "C:\\";
			case None: return "";
			default : return "";
			}
		}else if(environment.EnvironmentVariables.getOperatingSystem().equals(OSType.MacOSX)){
			
			switch(type){
			case Java: return "/usr/bin/javac";
			case C: return "/usr/bin/gcc";
			case C_plusplus: return "/usr/bin/g++";
			case None: return "";
			default : return "";
			}
		}else
			return "";
	}
	public static String getLanuageExtension(LanguageType type){
		switch(type){
		case Ruby: return ".rb";
		case Java: return ".java";
		case Python: return ".py";
		case C: return ".c";
		case C_plusplus: return ".cpp";
		case None: return "";
		default : return "";
		}
	}
	public static String getLanguageCommandBasedOnExtension(String extension){
		
		if(extension.equals(".rb")){
			return getLanguageCommand(LanguageType.Ruby);
		}else if(extension.equals(".py")){
			return getLanguageCommand(LanguageType.Python);
		}else if(extension.equals(".java")){
			return getLanguageCommand(LanguageType.Java);
		}else if(extension.equals(".c")){
			return getLanguageCommand(LanguageType.C);
		}else if(extension.equals(".cpp")){
			return getLanguageCommand(LanguageType.C_plusplus);
		}else if(extension.equals(".txt")){
			return getLanguageCommand(LanguageType.None);
		}else{
			return getLanguageCommand(LanguageType.None);
		}
	}
}