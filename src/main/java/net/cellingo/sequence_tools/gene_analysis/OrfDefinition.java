package net.cellingo.sequence_tools.gene_analysis;

/**
 * specifies the definition of ORFs
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public enum OrfDefinition {
	ATG_TO_STOP("ATG start to stop codon"),
	STOP_TO_STOP("stop codon to stop codon"),
	START_TO_STOP("any start codon to stop codon"),
	UNKNOWN("unknown definition");
	
	private String definition;
	
	private OrfDefinition(String definition){
		this.definition = definition;
	}
	
	public String toString(){
		return definition;
	}


}
