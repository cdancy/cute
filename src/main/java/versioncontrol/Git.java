package versioncontrol;
import java.io.File;
public class Git implements VersionControlSystem{
	/*
	 * All methods should return a string of the proper git command. All strings
	 * should append a " " or a single trailing whitespace on top of each string.
	 */
	public Git(){
		
	}
	public String tag(String tag){//needs work
		return tag;
	}
	public String add(File f) {
		return "add " + f.getAbsolutePath();
	}
	public String add(String fileName){
		fileName = fileName.replaceAll(" ", "") + " ";
		return "add " + fileName;
	}
	public String log(int m){
		return "log -l " + m;
	}
	public String log(){
		return "log ";
	}
	public String availableCommands() {

		return null;
	}

	public String commit(String s) {
		return "commit -m " + s;
		
	}
	public String status(){
		return "status ";
	}
	public String help(String s) {
		return "--help " + s;
	}

	public String init() {
		return "init ";
	}

	public String installedLocation() {
		return null;
	}

	public String progExecutor() {
		return "git ";
	}

	public String version() {
		return "--version ";
	}
	/*
	 * Returns Command to create a branch from the current working branch.
	 */
	public String branch(String newBranch) {
		newBranch = newBranch.replaceAll(" ", "") + " ";
		return showAllBranches() + newBranch + masterBranchGrep();
	}
	/*
	 * Returns Command to create a branch. newBranch is the newBranch name
	 * and oldBranch is the branch were going to create from.
	 */
	public String branch(String newBranch, String oldBranch){
		newBranch = newBranch.replaceAll(" ", "") + " ";
		oldBranch = oldBranch.replaceAll(" ", "") + " ";
		return "branch " + newBranch + oldBranch;
	}
	/*
	 * Used by other methods to get the current branch.
	 * (non-Javadoc)
	 * @see versioncontrol.VersionControlSystem#currentBranch()
	 */
	private String masterBranchGrep(){
		return "| grep '*' ";
	}
	/*
	 * Returns the current working branch
	 * (non-Javadoc)
	 * @see versioncontrol.VersionControlSystem#currentBranch()
	 */
	public String currentBranch() {
		return showAllBranches() + masterBranchGrep();
	}
	/*
	 * Returns String of all available branches.
	 */
	public String showAllBranches(){
		return "branch ";
	}
}