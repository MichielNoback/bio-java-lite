/**
 * 
 */
package net.cellingo.sequence_tools.sequences;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class SequenceCreationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SequenceCreationException() {
	}

	/**
	 * @param message
	 */
	public SequenceCreationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SequenceCreationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SequenceCreationException(String message, Throwable cause) {
		super(message, cause);
	}

}
