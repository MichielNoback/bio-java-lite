/**
 * 
 */
package net.cellingo.sequence_tools.sequences;

/**
 * objects of this class are thrown when an illegal operation is performed on a sequence,
 * such as requesting a subsequence that is out of bounds
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class IllegalSequenceOperation extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public IllegalSequenceOperation() {
		super();
	}

	/**
	 * @param arg0
	 */
	public IllegalSequenceOperation(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public IllegalSequenceOperation(Throwable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public IllegalSequenceOperation(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
