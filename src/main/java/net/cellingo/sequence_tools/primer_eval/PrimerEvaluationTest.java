package net.cellingo.sequence_tools.primer_eval;

/**
 * @author Michiel Noback (m.a.noback@pl.hanze.nl)
 * @version 0.1
 */
public enum PrimerEvaluationTest {
	SINGLE_BASE_RUNS("Single base runs"),
	DINUCLEOTIDE_BASE_RUNS("Dinucleotide base runs"),
	LENGTH("Length"),
	PERCENT_GC("Percent GC"),
	NEAREST_NEIGHBOR_TM("Tm (Nearest neighbor)"),
	GC_CLAMP("GC clamp"),
	SELF_ANNEALING("Self-annealing"),
	HAIRPIN_FORMATION("Hairpin formation");

	
	String type;
	private PrimerEvaluationTest(String type){
		this.type = type;
	}
	
	public String toString(){
		return type;
	}

}
