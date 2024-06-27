package net.cellingo.sequence_tools.sequence_matching;

import java.util.HashMap;

public enum KnownProteinSequencePattern {

	UNDEF("undefined"),
	HIT("Histidine triad"),
	LEUCINE_ZIPPER("Leucine zipper");
	
	private String pattern;
	private static HashMap<KnownProteinSequencePattern, String> patterns;

	private KnownProteinSequencePattern( String pattern ){
		this.pattern = pattern;
	}
	
	static{
		patterns = new HashMap<KnownProteinSequencePattern, String>();
		patterns.put(UNDEF, "");
		patterns.put(HIT, "HXHXH");
		patterns.put(LEUCINE_ZIPPER, "LXXXXXXLXXXXXXLXXXXXXL");
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
