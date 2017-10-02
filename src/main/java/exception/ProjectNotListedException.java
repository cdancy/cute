package exception;

public class ProjectNotListedException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5981345330631746020L;
	public ProjectNotListedException(){
		
		System.out.println("<Project> is not listed within the ProjectController directory.");
		
	}
	public ProjectNotListedException(String projectName){
		
		System.out.println(projectName + " is not listed within the ProjectController directory.");
		
	}
}
