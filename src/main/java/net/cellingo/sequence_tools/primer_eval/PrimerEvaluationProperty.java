package net.cellingo.sequence_tools.primer_eval;
/**
 * @author Michiel Noback (m.a.noback@pl.hanze.nl)
 * @version 0.1
 */
public enum PrimerEvaluationProperty {
	NAME("name"),
	SEQUENCE("sequence"),
	LENGTH("length"),
	BASE_COUNTS("Base counts"),
	GC_CONTENT("GC_content"),
	MOLECULAR_WEIGHT("Molecular weight"),
	//NMOL_A260("nmol/A260"),
	//MICROGRAMS_A260("micrograms/A260"),
	BASIC_TM("Basic Tm (degrees C)"),
	SALT_ADJUSTED_TM("Salt adjusted Tm (degrees C)"),
	NEAREST_NEIGHBOR_TM("Nearest neighbor Tm (degrees C)");
	
	String type;
	private PrimerEvaluationProperty(String type){
		this.type = type;
	}
	
	public String toString(){
		return type;
	}
}
