package net.cellingo.sequence_tools.annotation;

/**
 * Possible types for sequence objects. Sequence (sub)types are all SIMPLE_SEQUENCE_ATTRIBUTES
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public enum SequenceObjectType {
	SIMPLE_SEQUENCE("Fasta"),
	GFF3_SEQUENCE("GFF3 annotated sequence"),
	ANNOTATED_SEQUENCE("Annotated sequence"),
	GENBANK_SEQUENCE("GenBank sequence");

	private String type;

	private SequenceObjectType(String type){
		this.type = type;
	}
	
	public String toString(){
		return type;
	}

}
