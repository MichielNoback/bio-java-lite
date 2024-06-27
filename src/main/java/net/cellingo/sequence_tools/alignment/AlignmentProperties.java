package net.cellingo.sequence_tools.alignment;

import java.io.Serializable;
import java.util.HashMap;

/**
 * class encapsulates the properties of alignments; can be used to filter for
 * alignments with correct properties
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class AlignmentProperties implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5899822002481097542L;
//	public static final int ALIGNMENT_PROPERTY_SW_SCORE = 0;
//	public static final int ALIGNMENT_PROPERTY_RELATIVE_SCORE = 1;
//	public static final int ALIGNMENT_PROPERTY_HAIRPIN_LENGTH = 2;
//	public static final int ALIGNMENT_PROPERTY_ALIGNMENT_LENGTH = 3;
//	public static final int ALIGNMENT_PROPERTY_LOOP_LENGTH = 4;
//	public static final int ALIGNMENT_PROPERTY_GAP_NUMBER = 5;
//	public static final int ALIGNMENT_PROPERTY_MISMATCH_NUMBER = 6;
//	public static final int ALIGNMENT_PROPERTY_GC_NUMBER = 7;
//	public static final int ALIGNMENT_PROPERTY_AU_NUMBER = 8;
//	public static final int ALIGNMENT_PROPERTY_GU_NUMBER = 9;
//	
//	public static final int ALIGNMENT_PROPERTY_VALUE_TYPE_INTEGER = 0;
//	public static final int ALIGNMENT_PROPERTY_VALUE_TYPE_DOUBLE = 1;
//	
//	public static final String[] ALIGNMENT_PROPERTIES;
//	public static final int[] ALIGNMENT_PROPERTIES_VALUE_TYPES;
	
	//private HashMap<String, Double> propertyValues;
	private HashMap<AlignmentProperty, Double> propertyValues;
	
//	static{
//		ALIGNMENT_PROPERTIES = new String[10];
//		ALIGNMENT_PROPERTIES_VALUE_TYPES = new int[10];
//		for(int i=0; i<10; i++){
//			switch(i){
//			case ALIGNMENT_PROPERTY_SW_SCORE:
//				ALIGNMENT_PROPERTIES[i] = "swScore";
//				ALIGNMENT_PROPERTIES_VALUE_TYPES[i] = ALIGNMENT_PROPERTY_VALUE_TYPE_INTEGER;
//				break;
//			case ALIGNMENT_PROPERTY_RELATIVE_SCORE:
//				ALIGNMENT_PROPERTIES[i] = "relativeScore";
//				ALIGNMENT_PROPERTIES_VALUE_TYPES[i] = ALIGNMENT_PROPERTY_VALUE_TYPE_DOUBLE;
//				break;
//			case ALIGNMENT_PROPERTY_HAIRPIN_LENGTH:
//				ALIGNMENT_PROPERTIES[i] = "hairpinLength";
//				ALIGNMENT_PROPERTIES_VALUE_TYPES[i] = ALIGNMENT_PROPERTY_VALUE_TYPE_INTEGER;
//				break;
//			case ALIGNMENT_PROPERTY_ALIGNMENT_LENGTH:
//				ALIGNMENT_PROPERTIES[i] = "alignmentLength";
//				ALIGNMENT_PROPERTIES_VALUE_TYPES[i] = ALIGNMENT_PROPERTY_VALUE_TYPE_INTEGER;
//				break;
//			case ALIGNMENT_PROPERTY_LOOP_LENGTH:
//				ALIGNMENT_PROPERTIES[i] = "loopLength";
//				ALIGNMENT_PROPERTIES_VALUE_TYPES[i] = ALIGNMENT_PROPERTY_VALUE_TYPE_INTEGER;
//				break;
//			case ALIGNMENT_PROPERTY_GAP_NUMBER:
//				ALIGNMENT_PROPERTIES[i] = "gapNumber";
//				ALIGNMENT_PROPERTIES_VALUE_TYPES[i] = ALIGNMENT_PROPERTY_VALUE_TYPE_INTEGER;
//				break;
//			case ALIGNMENT_PROPERTY_MISMATCH_NUMBER:
//				ALIGNMENT_PROPERTIES[i] = "mismatchNumber";
//				ALIGNMENT_PROPERTIES_VALUE_TYPES[i] = ALIGNMENT_PROPERTY_VALUE_TYPE_INTEGER;
//				break;
//			case ALIGNMENT_PROPERTY_GC_NUMBER:
//				ALIGNMENT_PROPERTIES[i] = "gcNumber";
//				ALIGNMENT_PROPERTIES_VALUE_TYPES[i] = ALIGNMENT_PROPERTY_VALUE_TYPE_INTEGER;
//				break;
//			case ALIGNMENT_PROPERTY_AU_NUMBER:
//				ALIGNMENT_PROPERTIES[i] = "auNumber";
//				ALIGNMENT_PROPERTIES_VALUE_TYPES[i] = ALIGNMENT_PROPERTY_VALUE_TYPE_INTEGER;
//				break;
//			case ALIGNMENT_PROPERTY_GU_NUMBER:
//				ALIGNMENT_PROPERTIES[i] = "guNumber";
//				ALIGNMENT_PROPERTIES_VALUE_TYPES[i] = ALIGNMENT_PROPERTY_VALUE_TYPE_INTEGER;
//				break;
//			}//end switch()
//		}//end for()
//	}
	/**
	 * create new instance with empty properties Map
	 */
	public AlignmentProperties(){
		propertyValues = new HashMap<AlignmentProperty, Double>();
	}
	
	/**
	 * set the HashMap of name -> Double value pairs for defined properties
	 * @param propertyValues
	 */
	public void setProperties(HashMap<AlignmentProperty, Double> propertyValues){
		this.propertyValues = propertyValues;
	}
	/**
	 * get the defined properties and their Double values
	 * @return proprtyValues - HashMap of name -> Doubles
	 */
	public HashMap<AlignmentProperty, Double> getProperties(){
		return propertyValues;
	}
	/**
	 * get the value of a certain property
	 * @param name
	 * @return value - as a double
	 */
	public double getPropertyValue(AlignmentProperty property){
		return propertyValues.get(property);
	}
	/**
	 * add/set a particular property and its value
	 * @param propertyName
	 * @param value - as a double
	 */
	public void setProperty(AlignmentProperty property, double value){
		propertyValues.put(property, value);
	}
	/**
	 * check whether the property is present in the collection
	 * @param propertyName
	 * @return boolean - present
	 */
	public boolean hasProperty(String propertyName){
		if(propertyValues.containsKey(propertyName)){
			return true;
		}
		return false;
		
	}
	/**
	 * remove the property from the collection
	 * @param propertyName
	 */
	public void removeProperty(AlignmentProperty property) {
		propertyValues.remove(property);
	}
	
	/**
	 * provide a Set of criteria as minimum values and check whether this
	 * Set of values are all above those minima (if existing in the Set)
	 * @param minimumValues
	 * @return boolean - all values above provided minimum
	 */
	public boolean valuesAboveMinimum(AlignmentProperties minimumValues){
		for(AlignmentProperty property : minimumValues.getProperties().keySet()){
			double minimumValue = minimumValues.getProperties().get(property);
			if(propertyValues.containsKey(property) && propertyValues.get(property) < minimumValue){
				return false;
			}
		}
		return true;
	}
	/**
	 * provide a Set of criteria as maximum values and check whether this
	 * Set of values are all below those maxima (if existing in the Set)
	 * @param maximumValues
	 * @return boolean - all values above provided minimum
	 */
	public boolean valuesBelowMaximum(AlignmentProperties maximumValues){
		for(AlignmentProperty property : maximumValues.getProperties().keySet()){
			double maximumValue = maximumValues.getProperties().get(property);
			if(propertyValues.containsKey(property) && propertyValues.get(property) > maximumValue){
				return false;
			}
		}
		return true;
	}
}