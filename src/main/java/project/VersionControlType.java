package project;

public enum VersionControlType {
	GIT,SVN,CVS,BZR,None;
	
	public static VersionControlType getVersionControlType(String type){
		
		if(type.equals("GIT"))
			return VersionControlType.GIT;
		else if(type.equals("SVN"))
			return VersionControlType.SVN;
		else if(type.equals("CVS"))
			return VersionControlType.CVS;
		else if(type.equals("BZR"))
			return VersionControlType.BZR;
		else if(type.equals(null))
			return VersionControlType.None;
		else if(type.equals(""))
			return VersionControlType.None;
		else
			return VersionControlType.None;
	}
}
