package versioncontrol;
import java.io.File;
public interface VersionControlSystem {
	String add(File f);
	String commit(String s);
	String init();
	String progExecutor();
	String version();
	String status();
	String log();
	String tag(String tag);
	String branch(String s);
	String currentBranch();
	String installedLocation();
	String help(String s);
	String availableCommands();
}