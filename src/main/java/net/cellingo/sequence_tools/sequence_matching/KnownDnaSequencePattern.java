package net.cellingo.sequence_tools.sequence_matching;

import java.util.HashMap;

public enum KnownDnaSequencePattern {
	UNDEF("undefined"),
	RBS("Ribosomal Binding Site"),
	TATA_BOX("TATA box"),
	MINUS_35("-35 sequence"),
	MINUS_15("-15 sequence"),
	TELOMERIC_REPEAT("telomeric repeat");
	
	private String pattern;
	private static HashMap<KnownDnaSequencePattern, String> patterns;

	private KnownDnaSequencePattern( String pattern ){
		this.pattern = pattern;
	}
	
	static{
		patterns = new HashMap<KnownDnaSequencePattern, String>();
		patterns.put(UNDEF, "");
		patterns.put(RBS, "AGAAAGGAGGTGATC");
		patterns.put(TATA_BOX, "TATA");
		patterns.put(MINUS_35, "TTGACA");
		patterns.put(MINUS_15, "TATAAT");
		patterns.put(TELOMERIC_REPEAT, "TTAGGG");
	}
	
	/**
	 * returns the pattern of this type
	 * @return pattern
	 */
	public String getPattern(){
		return patterns.get(this);
	}
	
	public String toString(){
		return this.pattern;
	}
}
