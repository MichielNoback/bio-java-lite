package net.cellingo.sequence_tools.annotation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * this is the superclass for bacterial and eukaryote transcription
 * units. Extends SequenceElement.
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class Gene extends NucleicAcidSequenceElement implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3014544199808745432L;
	private HashMap<SequenceElementType, ArrayList<NucleicAcidSequenceElement>> geneElements;

	/**
	 * default constructor
	 */
	public Gene(String id){
		super(id);
		setSequenceElementType(SequenceElementType.GENE);
		geneElements = new HashMap<SequenceElementType, ArrayList<NucleicAcidSequenceElement>>(); 
	}
	
	/**
	 * define the ORF(s) that make up this gene
	 * @param orfs
	 */
	public void setOrfs( List<OpenReadingFrame> orfs ) {
		for(OpenReadingFrame orf : orfs){
			NucleicAcidSequenceElement element = (NucleicAcidSequenceElement)orf;
			addGeneElement(element);
		}
	}
	
	/**
	 * add an ORF that is element of this gene
	 * @param orf
	 */
	public void addOrf(OpenReadingFrame orf){
		NucleicAcidSequenceElement element = (NucleicAcidSequenceElement)orf;
		addGeneElement(element);	
	}

	/**
	 * get all the elements of a particular SequenceElementType
	 * @param type
	 * @return list of elements of one type
	 */
	public Iterator<NucleicAcidSequenceElement> getElementIterator(SequenceElementType type){
		if(geneElements.containsKey(type)){
			return geneElements.get(type).iterator();
		}
		else{//return an "empty" iterator
			return new ArrayList<NucleicAcidSequenceElement>().iterator();
		}
	}

	/**
	 * returns only the first element of a particular type, if it exists
	 * @param type
	 * @return nucleicAcidSequenceElement
	 */
	public NucleicAcidSequenceElement getFirstElementOf(SequenceElementType type){
		if(geneElements.containsKey(type)){
			return geneElements.get(type).get(0);
		}
		return null;
	}
	/**
	 * returns whether a type of element is specified on this gene
	 * @param type
	 * @return element of this type exists
	 */
	public boolean hasElementType(SequenceElementType type){
		return geneElements.containsKey(type);
	}

	/**
	 * add an element to this gene
	 * @param geneElement
	 */
	public void addGeneElement(NucleicAcidSequenceElement geneElement){
		SequenceElementType type = geneElement.getSequenceElementType();
		if(geneElements.containsKey(type)){
			geneElements.get(type).add(geneElement);
		}
		else{
			ArrayList<NucleicAcidSequenceElement> elementList = new ArrayList<NucleicAcidSequenceElement>();
			elementList.add(geneElement);
			geneElements.put(type, elementList);
		}
	}
	
	/**
	 * get an iterator of all lists of element types
	 * @return iterator of lists of sequence elements 
	 */
	public Iterator<ArrayList<NucleicAcidSequenceElement>> getElementIterator(){
		return geneElements.values().iterator();
	}
	/**
	 * get all the types of elements that are annotated on this gene
	 * @return annotated elements
	 */
	public Iterator<SequenceElementType> getElementTypes(){
		return geneElements.keySet().iterator();
	}
	/**
	 * returns a list of all elements associated with this gene
	 * @return all gene elements
	 */
	public List<SequenceElement> getElements(){
		List<SequenceElement> elements = new ArrayList<SequenceElement>();
		for( SequenceElementType type : geneElements.keySet() ){
			elements.addAll( geneElements.get(type) );
		}
		return elements;
	}

//	/**
//	 * add an annotation to this element
//	 * @param type
//	 * @param content
//	 */
//	public void addAttribute(String type, String content){
//		if( type == AnnotationType.GENE_NAME ){
//			setName( content );
//		}
//		getAttributes().addAttribute(type, content);
//	}

	/**
	 * get the name of this element
	 * @return name
	 */
//	public String getName() {
//		String name = null;
//		name = getAttributes().getFirstAttributeOfType( AnnotationType.GENE_NAME );
//		if(name == null){
//			name = "ID=" + getId();
//		}
//		return name;
//	}
//
//	/**
//	 * set the name of this element
//	 * @param name
//	 */
//	public void setName(String name) {
//		getAttributes().addAttribute(AnnotationType.GENE_NAME, name);
//		super.setName(name);
//	}
}
