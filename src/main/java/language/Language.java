package language;

public interface Language {
	/*
	 * Interface for generic programming languages to implemented.
	 */
	String getCompiler();
	String getProgramExecutor();
	boolean getKeyWord(String s);
}
