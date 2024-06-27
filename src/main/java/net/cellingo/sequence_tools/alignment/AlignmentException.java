/**
 * 
 */
package net.cellingo.sequence_tools.alignment;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class AlignmentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public AlignmentException() {
	}

	/**
	 * @param arg0
	 */
	public AlignmentException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public AlignmentException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public AlignmentException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
