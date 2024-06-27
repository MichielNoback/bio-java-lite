package net.cellingo.sequence_tools.alignment;
/**
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public enum AlignmentMatrixType {
	SIMPLE_MATCH_ALIGNMENT("simple match/mismatch alignment"),
	RNA_STRUCTURE_ALIGNMENT("RNA structure alignment"),
	DNA_STRUCTURE_ALIGNMENT("DNA structure alignment"),
	PROTEIN_PAM100("PAM-100"),
	PROTEIN_PAM250("PAM-250"),
	PROTEIN_BLOSUM45("BLOSUM-45"),
	PROTEIN_BLOSUM62("BLOSUM-62"),
	STRUCTURE_WEIGHTED_ALIGNMENT("alignment with conserved character scoring");

	private String type;

	private AlignmentMatrixType(String type){
		this.type = type;
	}
	
	public String toString(){
		return type;
	}

}
