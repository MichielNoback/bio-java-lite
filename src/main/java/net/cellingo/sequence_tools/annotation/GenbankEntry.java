package net.cellingo.sequence_tools.annotation;

import net.cellingo.sequence_tools.sequences.Sequence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * class to represent a GenBank sequence file
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class GenbankEntry extends AnnotatedSequence implements SequenceObject{
	private GenBankDivision genbankDivision;
	private String chromosome;
	private String mapID;
	private Attributes annotations;
	
	public GenbankEntry(){
		this.genbankDivision = GenBankDivision.UNK; //unknown GenBank division
		this.sequenceObjectType = SequenceObjectType.GENBANK_SEQUENCE;
	}

	/**
	 * get the annotations object for this sequence; it encapsulates a variety of annotated properties
	 * @return annotations the annotations associated with this GenBank sequence
	 */
	public Attributes getAttributes(){
		if(annotations == null){
			annotations = new Attributes();
		}
		return annotations;
	}
	
	public void setSequence(Sequence sequence) {
		if(annotations != null) sequence.setAttributes(annotations);
		super.setSequence(sequence);
	}
	
	/**
	 * set the GenBank genbankDivision to which this entry belongs. If unknown,
	 * this will stay the default GenBankDivision.UNK
	 * @param division of GenBank
	 */
	public void setGenBankDivision(String division){
		this.genbankDivision = GenBankDivision.getType(division);
	}
	/**
	 * get the GenBank division
	 * @return GenBank division string
	 */
	public GenBankDivision getGenBankDivision(){
		return genbankDivision;
	}
	
	/**
	 * set the chromosome from which this entry originates
	 * @param chromosome name
	 */
	public void setChromosome(String chromosome){
		this.chromosome = chromosome;
	}
	/**
	 * get the chromosome name
	 * @return chromosome
	 */
	public String getChromosome(){
		return chromosome;
	}
	/**
	 * set the GenBank mapID
	 * @param mapID
	 */
	public void setMapID(String mapID) {
		this.mapID = mapID;
	}
	/**
	 * get the GenBank mapID
	 * @return mapID
	 */
	public String getMapID() {
		return mapID;
	}

	/**
	 * add an element to the annotation 
	 * @param element
	 */
//	public void addElement(SequenceElement element){
//
//		SequenceElementType type = element.getSequenceElementType();
//		/*all elements -including genes- are stored here*/
//		if(sequenceElements.containsKey(type)){
//			sequenceElements.get(type).put(element.getId(), element);
//		}
//		else{
//			HashMap<String, SequenceElement> elements = new HashMap<>();
//			elements.put(element.getId(), element);
//			sequenceElements.put(element.getSequenceElementType(), elements);
//		}
//	}

	/**
	 * Returns a List of SequenceElement objects overlapping with the given coordinate.
	 * If includedElementTypes is null, all element types selected and scanned against.
	 * If excludedElementTypes is null, no element types are scanned against for exclusion
	 * If overlapping with an included element and also with an excluded element, it will  
	 * not be added to the returned list: both should be true, not any one of them.
	 * If strandSpecific is true, elements should lie on the same nucleic acid strand to be included
	 * If no elements overlap, an empty list is returned. 
	 * @param coordinate the coordinate to scan against
	 * @param includedElementTypes only return when overlapping with these element types
	 * @param excludedElementTypes only return when not overlapping with these element types
	 * @return sequence elements
	 */
	public List<SequenceElement> getOverlappingElements( SequenceCoordinates coordinate, 
			List<SequenceElementType> includedElementTypes, 
			List<SequenceElementType> excludedElementTypes ) {

		List<SequenceElement> list = new ArrayList<SequenceElement>();
		
		/*if no included set is provided, all are selected*/
		if( includedElementTypes == null ){
			includedElementTypes = new ArrayList<SequenceElementType>();
			for( SequenceElementType t : SequenceElementType.values() ){
				includedElementTypes.add( t );
			}
		}

		/*create a convenience exclude hash set*/
		HashSet<SequenceElementType> exclTypesSet = new HashSet<SequenceElementType>();
		if(excludedElementTypes != null) exclTypesSet.addAll(excludedElementTypes);
		
		/*scan for elements*/
		for( SequenceElementType type : includedElementTypes ){
			//System.out.println( "checking type " + type.toString());
			if( this.containsElementType( type )  ){//&& type != SequenceElementType.GENE
				for( SequenceElement element : sequenceElements.get(type).values() ){
					if( ! exclTypesSet.contains( element.getSequenceElementType() ) && coordinate.overlaps( element.getSequenceCoordinates() ) ){
						//System.out.println( coordinate + " overlaps with element of type " + type.toString());
						list.add(element);
					}
				}
			}
		}
		return list;
	}

	@Override
	public String toString() {
		return "GenbankEntry{" +
				"genbankDivision=" + genbankDivision +
				", chromosome='" + chromosome + '\'' +
				", sequenceObjectType=" + sequenceObjectType +
				", mapID='" + mapID + '\'' +
				", annotations=" + annotations +
				'}';
	}
}
