/**
 * 
 */
package net.cellingo.sequence_tools.alphabets;

/**
 * Exception is thrown when an alphabet character is constructed with an illegal character
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class IllegalCharacterException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public IllegalCharacterException() {
	}

	/**
	 * @param arg0
	 */
	public IllegalCharacterException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public IllegalCharacterException(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public IllegalCharacterException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
