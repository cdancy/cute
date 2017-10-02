package environment;

import java.io.File;

public class EnvironmentVariables {
	/* 
	 * Any method and/or variable that should have a need to be system wide shall go here.
	 */
	
	public static boolean isWindowsOS(){
		if(getOperatingSystem().equals(OSType.WindowsXP))
			return true;
		else
			return false;
	}
	public static boolean isLinuxOS(){
		
		if(getOperatingSystem().equals(OSType.Linux))
			return true;
		else
			return false;
	}
	public static OSType getOperatingSystem(){
		String name =  System.getProperty("os.name");
		
		if(name.equals("Linux"))
			return OSType.Linux;
		else if(name.equals("Windows XP"))
			return OSType.WindowsXP;
		else
			return OSType.Linux;
	}
	public static int getCPUCount(){
		return Runtime.getRuntime().availableProcessors();
	}
	public static String getUserName(){
		return System.getProperty("user.name");
	}
	public static String getUserHomeDirectory(){
		return System.getProperty("user.home");
	}
	public static String getFileSeperator(){
		return System.getProperty("file.separator");
	}
	public static String getDefaultWorkspaceDirectory(){
		if(isLinuxOS())
			return getUserHomeDirectory() + "/cute_workspace/";
		else if(isWindowsOS())
			return getUserHomeDirectory() + "\\cute_workspace\\";
		else
			return null;
	}
	public static boolean isGITAvailable(){
		if(isLinuxOS()){
			File f = new File("/usr/bin/git");
			if(f.exists())
				return true;
			else
				return false;
		}else if(isWindowsOS()){
			return false;
			
		}else{
			System.out.println("EnvironmentVariables..Foreign OS...");
			return false;
		}
	}
	public static boolean isCVSAvailable(){
		if(isLinuxOS()){
			File f = new File("/usr/bin/cvs");
			if(f.exists())
				return true;
			else
				return false;
		}else if(isWindowsOS()){
			return false;
			
		}else{
			System.out.println("EnvironmentVariables..Foreign OS...");
			return false;
		}
	}
	public static boolean isSVNAvailable(){
		if(isLinuxOS()){
			File f = new File("/usr/bin/svn");
			if(f.exists())
				return true;
			else
				return false;
		}else if(isWindowsOS()){
			return false;
			
		}else{
			System.out.println("EnvironmentVariables..Foreign OS...");
			return false;
		}
	}
	public static boolean isBZRAvailable(){
		if(isLinuxOS()){
			File f = new File("/usr/bin/bzr");
			if(f.exists())
				return true;
			else
				return false;
		}else if(isWindowsOS()){
			return false;
			
		}else{
			System.out.println("EnvironmentVariables..Foreign OS...");
			return false;
		}
	}
	public static boolean isJavaAvailable(){
		if(isLinuxOS()){
			File f = new File("/usr/bin/java");
			File g = new File("/usr/bin/javac");
			if(g.exists() && f.exists())
				return true;
			else
				return false;
		}else if(isWindowsOS()){
			File f = new File("C:\\Sun\\SDK\\jdk\\bin\\java.exe");
			File g = new File("C:\\Sun\\SDK\\jdk\\bin\\javac.exe");
			if(f.exists() && g.exists())
				return true;
			else
				return false;
		}else{
			System.out.println("EnvironmentVariables...java...Foreign OS...");
			return false;
		}
	}
	public static boolean isRubyAvailable(){
		if(isLinuxOS()){
			File f = new File("/usr/bin/ruby");
			if(f.exists())
				return true;
			else
				return false;
		}else if(isWindowsOS()){
			File f = new File("C:\\ruby\\bin\\ruby.exe");
			if(f.exists())
				return true;
			else
				return false;
		}else{
			System.out.println("EnvironmentVariables...Ruby...Foreign OS...");
			return false;
		}
	}
	public static boolean isPythonAvailable(){
		if(isLinuxOS()){
			File f = new File("/usr/bin/python");
			if(f.isFile())
				return true;
			else
				return false;
		}else if(isWindowsOS()){
			File f = new File("C:\\Python24\\python.exe");
			if(f.exists())
				return true;
			else
				return false;
		}else{
			System.out.println("EnvironmentVariables...python...Foreign OS...");
			return false;
		}
	}
	public static boolean isC_Available(){
		if(isLinuxOS()){
			File f = new File("/usr/bin/gcc");
			if(f.isFile())
				return true;
			else
				return false;
		}else if(isWindowsOS()){
			return false;
		}else{
			System.out.println("EnvironmentVariables...gcc...Foreign OS...");
			return false;
		}
	}
	public static boolean isCplusplus_Available(){
		if(isLinuxOS()){
			File f = new File("/usr/bin/g++");
			if(f.isFile())
				return true;
			else
				return false;
		}else if(isWindowsOS()){
			return false;
		}else{
			System.out.println("EnvironmentVariables...g++...Foreign OS...");
			return false;
		}
	}
	public static OSType[] getProjectTypes(){
		return OSType.values();
	}
}