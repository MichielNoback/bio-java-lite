/**
 * 
 */
package net.cellingo.sequence_tools.io;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class UnsupportedDatabaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public UnsupportedDatabaseException() {	}

	/**
	 * @param message
	 */
	public UnsupportedDatabaseException(String message) {
		super(message);
	}
}
