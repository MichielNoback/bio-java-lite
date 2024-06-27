package net.cellingo.sequence_tools.alignment;
/**
 * 
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public enum AlignmentAlgorithm {

	LOCAL("local alignment"),
	SEMIGLOBAL("semiglobal alignment"),
	GLOBAL("global alignment"),
	STRUCTURE("structure analysis");

	private String type;

	private AlignmentAlgorithm(String type){
		this.type = type;
	}
	
	public String toString(){
		return type;
	}


}
