package net.cellingo.sequence_tools.io;
/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class UnknownSequenceFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3636958827522484996L;
	private String errorMessage;
	
	/**
	 * default constructor sets error message to  "unknown sequence format"
	 *
	 */
	public UnknownSequenceFormatException(){
		this.errorMessage = "unknown sequence format";
	}
	/**
	 * this constructor sets error message to provided errorMessage
	 */
	public UnknownSequenceFormatException(String errorMessage){
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
}
