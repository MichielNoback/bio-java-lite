package net.cellingo.sequence_tools.alignment;

public enum AlignmentProperty {
/*
 * 	public static final int ALIGNMENT_PROPERTY_SW_SCORE = 0;
	public static final int ALIGNMENT_PROPERTY_RELATIVE_SCORE = 1;
	public static final int ALIGNMENT_PROPERTY_HAIRPIN_LENGTH = 2;
	public static final int ALIGNMENT_PROPERTY_ALIGNMENT_LENGTH = 3;
	public static final int ALIGNMENT_PROPERTY_LOOP_LENGTH = 4;
	public static final int ALIGNMENT_PROPERTY_GAP_NUMBER = 5;
	public static final int ALIGNMENT_PROPERTY_MISMATCH_NUMBER = 6;
	public static final int ALIGNMENT_PROPERTY_GC_NUMBER = 7;
	public static final int ALIGNMENT_PROPERTY_AU_NUMBER = 8;
	public static final int ALIGNMENT_PROPERTY_GU_NUMBER = 9;
	
	public static final int ALIGNMENT_PROPERTY_VALUE_TYPE_INTEGER = 0;
	public static final int ALIGNMENT_PROPERTY_VALUE_TYPE_DOUBLE = 1;
	
	public static final String[] ALIGNMENT_PROPERTIES;
	public static final int[] ALIGNMENT_PROPERTIES_VALUE_TYPES;*/
	
	SW_SCORE("Smith-Waterman score"),
	RELATIVE_SCORE("Relative alignment score"){
		public int propertyType(){return ALIGNMENT_PROPERTY_VALUE_TYPE_DOUBLE;}
	},
	HAIRPIN_LENGTH("Hairpin length"),
	ALIGNMENT_LENGTH("Alignment length"),
	LOOP_LENGTH("Loop length"),
	GAP_NUMBER("Gap number"),
	MISMATCH_NUMBER("Mismatch number"),
	GC_PAIR_NUMBER("GC pairs"),
	AU_PAIR_NUMBER("AT/AU pairs"),
	GU_PAIR_NUMBER("GU/GT pairs");
	
	
	public static final int ALIGNMENT_PROPERTY_VALUE_TYPE_INTEGER = 0;
	public static final int ALIGNMENT_PROPERTY_VALUE_TYPE_DOUBLE = 1;
	String type;
	
	private AlignmentProperty(String type){
		this.type = type;
	}
	
	public String toString(){
		return type;
	}
	
	public int propertyType(){
		return ALIGNMENT_PROPERTY_VALUE_TYPE_INTEGER;
	}
	
	
}
