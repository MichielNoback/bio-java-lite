/**
 * 
 */
package net.cellingo.sequence_tools.io;

/**
 * Class specifies possible enum objects representing sequence database types
 * for different hosts
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public enum SequenceDatabase {
	NCBI_PROTEIN("protein"),
	NCBI_NUCLEOTIDE("nucleotide"),
	NCBI_GENOME("genome");
	
	private String type;
	
	private SequenceDatabase( String type ){
		this.type = type;
	}
	
	public String toString(){
		return type;
	}
	
}
