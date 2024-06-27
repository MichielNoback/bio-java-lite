/**
 * 
 */
package net.cellingo.sequence_tools.sequences;

import java.util.HashMap;

/**
 * enum to represent the type of sequence: DNA, RNA, protein, custom
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public enum SequenceType {
	DNA("DNA"),
	RNA("RNA"),
	PROTEIN("protein"),
	STRUCTURE("structure");

	private String type;
	private static HashMap<String, SequenceType> types;
	
	static{
		types = new HashMap<String, SequenceType>();
		types.put("DNA", SequenceType.DNA);
		types.put("RNA", SequenceType.RNA);
		types.put("protein", SequenceType.PROTEIN);
		types.put("structure", SequenceType.STRUCTURE);
	}
	
	private SequenceType(String type){
		this.type = type;
	}
	
	public String toString(){
		return type;
	}
	
	public static SequenceType getType( String typeString ){
		if( types.containsKey( typeString )){
			return types.get(typeString);
		}
		return null;
	}
	
}
