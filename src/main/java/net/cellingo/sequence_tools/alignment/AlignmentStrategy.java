package net.cellingo.sequence_tools.alignment;
/**
 * Defines an alignment strategy.
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public enum AlignmentStrategy {

	INTRAMOLECULAR_SLIDING_WINDOW("sliding window analysis"),
	INTRAMOLECULAR("intramolecular"),
	INTERMOLECULAR_FIRST_TO_ALL("intermolecular alignment of first against all others"),
	INTERMOLECULAR_ALL_TO_ALL("intermolecular alignment of all against all");

	private String type;

	private AlignmentStrategy(String type){
		this.type = type;
	}
	
	public String toString(){
		return type;
	}


}
