/**
 * 
 */
package net.cellingo.sequence_tools.io;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class UnsupportedFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public UnsupportedFormatException() { }

	/**
	 * @param message
	 */
	public UnsupportedFormatException(String message) {
		super( message );
	}


}
