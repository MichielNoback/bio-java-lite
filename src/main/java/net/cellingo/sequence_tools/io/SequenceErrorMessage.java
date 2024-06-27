/**
 * 
 */
package net.cellingo.sequence_tools.io;

/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public enum SequenceErrorMessage {
	NO_SEQUENCE("no sequence entered"),
	ILLEGAL_CHARACTER("illegal character in sequence"),
	SEQUENCE_OK("legal sequence for this type"),
	NO_MATCH_FOUND("no match found"),
	MATCHES_FOUND("matches were found");
	

	private String type;

	private SequenceErrorMessage(String type){
		this.type = type;
	}
	
	public String toString(){
		return type;
	}


	
}
